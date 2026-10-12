package com.medora.QUEUE.batch;

import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.database.JpaPagingItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DoctorShiftQueueBatchConfiguration {

    private final EntityManagerFactory entityManagerFactory;
    private final DoctorShiftQueueBatchProcessor doctorShiftQueueBatchProcessor;
    private final DoctorShiftQueueBatchWriter doctorShiftQueueBatchWriter;

    @Bean
    public JpaPagingItemReader<DoctorJpaEntity> doctorItemReader() {
        return new JpaPagingItemReaderBuilder<DoctorJpaEntity>()
                .name("DoctorShiftQueueBatch_doctorItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT DISTINCT doctor FROM DoctorJpaEntity doctor LEFT JOIN FETCH doctor.doctorSchedules")
                .pageSize(50)
                .build();
    }

    @Bean
    public Step generateShiftQueueStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("generateShiftQueueStep", jobRepository)
                .<DoctorJpaEntity, List<DoctorShiftQueueJpaEntity>>chunk(50)
                .reader(doctorItemReader())
                .processor(doctorShiftQueueBatchProcessor)
                .writer(doctorShiftQueueBatchWriter)
                .faultTolerant()
                .retryLimit(3)
                .build();
    }
    @Bean("generateShiftQueueJob")
    public Job generateShiftQueueJob(JobRepository jobRepository, Step generateShiftQueueStep) {
        return new JobBuilder("generateShiftQueueJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
                .start(generateShiftQueueStep)
                .build();
    }

    @Bean
    public JobParameters generateShiftQueueBatchJobParameters() {
        return new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
    }
}
