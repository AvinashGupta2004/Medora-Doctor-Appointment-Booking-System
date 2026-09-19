package com.medora.DOCTOR.persistence.repository;

import com.medora.DOCTOR.persistence.entity.DoctorScheduleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorScheduleJpaEntity, Long> {

    List<DoctorScheduleJpaEntity> findByDoctorIdAndActiveTrue(Long doctorId);

    List<DoctorScheduleJpaEntity> findByDayOfWeekAndActiveTrue(DayOfWeek dayOfWeek);
}
