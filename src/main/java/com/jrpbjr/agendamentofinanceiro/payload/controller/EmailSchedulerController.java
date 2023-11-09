package com.jrpbjr.agendamentofinanceiro.payload.controller;

import com.jrpbjr.agendamentofinanceiro.payload.job.EmailJob;
import com.jrpbjr.agendamentofinanceiro.payload.model.EmailRequest;
import org.quartz.*;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

public class EmailSchedulerController {


    private JobDetail buildJobDetail(EmailRequest scheduleEmailRequest){
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("email",scheduleEmailRequest.getEmail());
        jobDataMap.put("subject",scheduleEmailRequest.getSuject());
        jobDataMap.put("body",scheduleEmailRequest.getBody());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(),"email-jobs")
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
