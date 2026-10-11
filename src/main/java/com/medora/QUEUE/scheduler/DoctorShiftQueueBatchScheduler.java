package com.medora.QUEUE.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DoctorShiftQueueBatchScheduler {

    private final JobParameters generateShiftQueueBatchJobParameters;

    @Qualifier("generateShiftQueueJob")
    private final Job generateShiftQueueJob;
    private final JobOperator jobOperator;

    @Scheduled(cron = "30 * * * * *")
    public void generateShiftQueuesForAllDoctors() {
        log.info("Starting daily doctor shift queue generation job...");
        try {
            JobExecution jobExecution = jobOperator.start(generateShiftQueueJob, generateShiftQueueBatchJobParameters);

            log.info("Daily doctor shift queue batch job launched successfully. Execution ID: {}, Status: {}",
                    jobExecution.getId(), jobExecution.getStatus());

        } catch (JobInstanceAlreadyCompleteException e) {
            log.warn("Job instance has already completed!");
        }
        catch (Exception e) {
            log.error("Failed to execute daily doctor shift queue batch job", e);
        }
    }
}
