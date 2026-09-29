package com.medora.APPOINTMENT.persistence.repository;

import com.medora.APPOINTMENT.common.enums.AppointmentStatus;
import com.medora.APPOINTMENT.persistence.entity.AppointmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentJpaEntity, Long> {

    List<AppointmentJpaEntity> findByQueueIdOrderByTokenNumberAsc(Long queueId);

    List<AppointmentJpaEntity> findByPatientIdAndStatusIn(
            Long patientId,
            List<AppointmentStatus> statusList
    );

    @Query("""
        SELECT COUNT(a) > 0 FROM AppointmentJpaEntity a
        WHERE a.queue.id = :queueId
          AND a.patient.id = :patientId
          AND a.status NOT IN ('CANCELLED', 'NO_SHOW')
    """)
    boolean existsActiveBookingInQueue(
            @Param("queueId") Long queueId,
            @Param("patientId") Long patientId
    );

    Optional<AppointmentJpaEntity> findByQueueIdAndTokenNumber(Long queueId, Integer tokenNumber);
}
