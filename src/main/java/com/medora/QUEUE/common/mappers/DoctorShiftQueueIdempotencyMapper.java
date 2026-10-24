package com.medora.QUEUE.common.mappers;

import com.medora.QUEUE.persistence.entity.DoctorShiftQueueIdempotencyJpaEntity;
import com.medora.QUEUE.persistence.entity.DoctorShiftQueueJpaEntity;
import com.medora.QUEUE.processing.models.DoctorShiftQueueIdempotency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.validation.annotation.Validated;

@Mapper(componentModel = "spring")
@Validated
public interface DoctorShiftQueueIdempotencyMapper {

    @Mapping(
            target = "queueId",
            source = "queue.id"
    )
    DoctorShiftQueueIdempotency toModel(DoctorShiftQueueIdempotencyJpaEntity entity);

    @Mapping(
            target = "queue",
            source = "queueId",
            qualifiedByName = "queueReference"
    )
    DoctorShiftQueueIdempotencyJpaEntity toEntity(DoctorShiftQueueIdempotency model);

    @Named("queueReference")
    default DoctorShiftQueueJpaEntity queueReference(Long queueId) {
        if (queueId == null)
            return null;

        DoctorShiftQueueJpaEntity queue = new DoctorShiftQueueJpaEntity();
        queue.setId(queueId);
        return queue;
    }
}
