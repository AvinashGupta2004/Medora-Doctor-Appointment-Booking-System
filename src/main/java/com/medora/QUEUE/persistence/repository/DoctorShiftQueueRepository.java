package com.medora.QUEUE.persistence.repository;

import com.medora.QUEUE.common.enums.QueueStatus;
import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorShiftQueueRepository extends JpaRepository<DoctorShiftQueueJpaEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT q FROM DoctorShiftQueueJpaEntity q WHERE q.id = :id")
    Optional<DoctorShiftQueueJpaEntity> findByIdWithPessimisticLock(@Param("id") Long id);

    List<DoctorShiftQueueJpaEntity> findByDoctorIdAndShiftDateAndStatus(
            Long doctorId,
            LocalDate shiftDate,
            QueueStatus status
    );

    boolean existsByDoctorIdAndShiftDateAndStartTime(
            Long doctorId,
            LocalDate shiftDate,
            LocalTime startTime
    );

    Optional<DoctorShiftQueueJpaEntity> findByDoctorIdAndShiftDateAndStartTime(
            Long doctorId,
            LocalDate shiftDate,
            LocalTime startTime
    );

    List<DoctorShiftQueueJpaEntity> findByDoctorIdAndShiftDateBetween(Long doctorId, LocalDate startDate, LocalDate endDate);

    List<DoctorShiftQueueJpaEntity> findByDoctorIdAndShiftDate(Long doctorId, LocalDate shiftDate);
}
