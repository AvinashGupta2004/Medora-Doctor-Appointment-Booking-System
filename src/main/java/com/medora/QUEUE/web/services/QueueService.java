package com.medora.QUEUE.web.services;

import com.medora.DOCTOR.persistence.entity.DoctorJpaEntity;
import com.medora.QUEUE.processing.services.DoctorShiftQueueServiceFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueService {
}
