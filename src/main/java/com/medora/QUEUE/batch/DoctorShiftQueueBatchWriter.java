package com.medora.QUEUE.batch;

import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import com.medora.QUEUE.persistence.repository.DoctorShiftQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DoctorShiftQueueBatchWriter implements ItemWriter<List<DoctorShiftQueueJpaEntity>> {

    private final DoctorShiftQueueRepository doctorShiftQueueRepository;

    @Override
    public void write(Chunk<? extends List<DoctorShiftQueueJpaEntity>> chunk) throws Exception {

        for (List<DoctorShiftQueueJpaEntity> queues: chunk) {
            if (!queues.isEmpty()) {
                doctorShiftQueueRepository.saveAll(queues);
            }
        }
    }
}
