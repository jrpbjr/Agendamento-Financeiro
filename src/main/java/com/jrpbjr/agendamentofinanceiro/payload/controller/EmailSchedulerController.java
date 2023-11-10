package com.jrpbjr.agendamentofinanceiro.payload.controller;

import com.jrpbjr.agendamentofinanceiro.payload.job.EmailJob;
import com.jrpbjr.agendamentofinanceiro.payload.model.EmailRequest;
import com.jrpbjr.agendamentofinanceiro.payload.model.EmailResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;


@RestController
public class EmailSchedulerController {

    private static final Logger logger = LoggerFactory.getLogger(EmailSchedulerController.class);

    @Autowired
    private Scheduler scheduler;

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
