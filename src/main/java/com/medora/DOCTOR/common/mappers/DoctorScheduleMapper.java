package com.medora.DOCTOR.common.mappers;

import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.DOCTOR.persistence.entity.DoctorScheduleJpaEntity;
import com.medora.DOCTOR.processing.models.DoctorSchedule;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DoctorScheduleMapper {

    @Mapping(target = "doctorId", source = "doctor.id")
    DoctorSchedule toModel(DoctorScheduleJpaEntity entity);

    @Mapping(target = "doctor", source = "doctorId", qualifiedByName = "mapDoctorIdToEntity")
    DoctorScheduleJpaEntity toEntity(DoctorSchedule doctorSchedule);

    @Named("mapDoctorIdToEntity")
    default DoctorJpaEntity mapDoctorIdToEntity(Long doctorId) {
        if (doctorId == null) return null;
        DoctorJpaEntity doctorJpaEntity = new DoctorJpaEntity();
        doctorJpaEntity.setId(doctorId);
        return doctorJpaEntity;
    }
}
