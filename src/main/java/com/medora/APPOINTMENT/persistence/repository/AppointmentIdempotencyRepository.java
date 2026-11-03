package com.medora.APPOINTMENT.persistence.repository;

import com.medora.APPOINTMENT.persistence.entity.AppointmentIdempotencyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentIdempotencyRepository extends JpaRepository<AppointmentIdempotencyJpaEntity, Long> {


    Optional<AppointmentIdempotencyJpaEntity> findAppointmentIdempotencyJpaEntityByIdempotencyKey(String idempotencyKey);
}
