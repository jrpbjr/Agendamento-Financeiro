package com.jrpbjr.agendamentofinanceiro.payload.controller;

import com.jrpbjr.agendamentofinanceiro.payload.exception.NegocioException;
import com.jrpbjr.agendamentofinanceiro.payload.job.EmailJob;
import com.jrpbjr.agendamentofinanceiro.payload.model.Dtos.OperacaoDto;
import com.jrpbjr.agendamentofinanceiro.payload.model.EmailRequest;
import com.jrpbjr.agendamentofinanceiro.payload.model.EmailResponse;

import com.jrpbjr.agendamentofinanceiro.payload.model.OperacaoModel;
import com.jrpbjr.agendamentofinanceiro.payload.service.OperacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;


@RestController
public class SchedulerController {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerController.class);

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private OperacaoService operacaoService;

    @PostMapping("/scheduleEmail")
    public ResponseEntity<EmailResponse> scheduleEmail(@Valid @RequestBody EmailRequest EmailRequest) {
        try {
            ZonedDateTime dateTime = ZonedDateTime.of(EmailRequest.getDateTime(), EmailRequest.getTimeZone());
            if(dateTime.isBefore(ZonedDateTime.now())) {
                EmailResponse EmailResponse = new EmailResponse(false,
                        "dateTime must be after current time");
                return ResponseEntity.badRequest().body(EmailResponse);
            }

            JobDetail jobDetail = buildJobDetail(EmailRequest);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime);
            scheduler.scheduleJob(jobDetail, trigger);

            EmailResponse EmailResponse = new EmailResponse(true,
                    jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Email Scheduled Successfully!");
            return ResponseEntity.ok(EmailResponse);
        } catch (SchedulerException ex) {
            logger.error("Error scheduling email", ex);

           EmailResponse EmailResponse = new EmailResponse(false,
                    "Error scheduling email. Please try later!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(EmailResponse);
        }
    }

    @PostMapping("/calcularTaxa")
    public BigDecimal calcularTaxa(@Valid @RequestBody OperacaoDto operacaoDto) {
        OperacaoModel operacaoModel = new OperacaoModel(operacaoDto);
        return this.operacaoService.calcularTaxa(operacaoModel);
    }

    @PostMapping("/salvarOperacao")
    public ResponseEntity<OperacaoDto> salvarOperacao(@Valid @RequestBody OperacaoDto operacaoDto) throws NegocioException {
       OperacaoModel operacaoModel = new OperacaoModel(operacaoDto);
       this.operacaoService.salvarOperacao(operacaoModel);
       URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(operacaoModel.getId()).toUri();
       return ResponseEntity.created(uri).build();
    }

    @GetMapping("/get")
    public ResponseEntity<String> getApiTest(){
        return ResponseEntity.ok("Get API Test - Pass");
    }

    private JobDetail buildJobDetail(EmailRequest EmailRequest) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("email", EmailRequest.getEmail());
        jobDataMap.put("subject", EmailRequest.getSubject());
        jobDataMap.put("body", EmailRequest.getBody());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
