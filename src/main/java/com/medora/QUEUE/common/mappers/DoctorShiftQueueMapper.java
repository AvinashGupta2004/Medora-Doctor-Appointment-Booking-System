package com.medora.QUEUE.common.mappers;

import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.DOCTOR.persistence.entity.DoctorScheduleJpaEntity;
import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DoctorShiftQueueMapper {

    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "scheduleId", source = "doctorSchedule.id")
    @Mapping(
            target = "doctorRegistrationNumber",
            source = "doctor.doctorRegistrationNumber"
    )
    DoctorShiftQueue toModel(DoctorShiftQueueJpaEntity entity);

    @Mapping(target = "doctor", source = "doctorId", qualifiedByName = "mapDoctorIdToEntity")
    @Mapping(target = "doctorSchedule", source = "scheduleId", qualifiedByName = "mapScheduleIdToEntity")
    DoctorShiftQueueJpaEntity toEntity(DoctorShiftQueue entity);

    @Named("mapDoctorIdToEntity")
    default DoctorJpaEntity mapDoctorIdToEntity(Long doctorId) {
        if (doctorId == null) {
            return null;
        }
        DoctorJpaEntity doctorJpaEntity = new DoctorJpaEntity();
        doctorJpaEntity.setId(doctorId);
        return doctorJpaEntity;
    }

    @Named("mapScheduleIdToEntity")
    default DoctorScheduleJpaEntity mapScheduleIdToEntity(Long scheduleId) {
        if (scheduleId == null) {
            return null;
        }
        DoctorScheduleJpaEntity doctorScheduleJpaEntity = new DoctorScheduleJpaEntity();
        doctorScheduleJpaEntity.setId(scheduleId);
        return doctorScheduleJpaEntity;
    }
}
