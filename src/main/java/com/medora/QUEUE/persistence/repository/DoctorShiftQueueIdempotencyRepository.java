package com.medora.QUEUE.persistence.repository;

import com.medora.QUEUE.persistence.entity.DoctorShiftQueueIdempotencyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorShiftQueueIdempotencyRepository extends JpaRepository<DoctorShiftQueueIdempotencyJpaEntity, Long> {
}
