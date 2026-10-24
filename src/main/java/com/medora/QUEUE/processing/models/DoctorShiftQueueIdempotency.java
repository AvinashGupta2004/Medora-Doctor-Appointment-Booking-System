package com.medora.QUEUE.processing.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorShiftQueueIdempotency {
    private Long id;
    private String idempotencyKey;
    private String requestHash;
    private Long queueId;
    private Integer allocatedToken;
}
