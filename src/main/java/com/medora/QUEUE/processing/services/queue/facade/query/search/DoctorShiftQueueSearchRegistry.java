package com.medora.QUEUE.processing.services.queue.facade.query.search;

import com.medora.QUEUE.common.enums.DoctorShiftQueueSearchContext;
import com.medora.QUEUE.common.exceptions.NoQueuesAvailableException;
import com.medora.QUEUE.common.exceptions.NoSuitableStrategyFoundException;
import com.medora.QUEUE.processing.models.DoctorShiftQueue;
import com.medora.QUEUE.processing.services.queue.facade.query.search.strategies.DoctorShiftQueueSearchStrategy;
import com.medora.common.models.EntityModelSearchQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DoctorShiftQueueSearchRegistry {

    private final List<DoctorShiftQueueSearchStrategy> searchStrategies;

    public EntityModelSearchQueryResult<DoctorShiftQueue> search(DoctorShiftQueue queue, DoctorShiftQueueSearchContext context) {
        String contextValue = context.name();
        String prefix = contextValue.substring(0, contextValue.indexOf('_'));
        boolean isSingle = prefix.equalsIgnoreCase("SINGLE");

        if (isSingle) {
            DoctorShiftQueue fetchedQueue = resolveStrategy(queue, context)
                    .searchDoctorShiftQueue(queue)
                    .orElseThrow(() -> new NoQueuesAvailableException(
                            "No Queues found for provided data!"
                    ));

            return EntityModelSearchQueryResult.ofSingle(fetchedQueue);
        }

        List<DoctorShiftQueue> fetchedQueues = resolveStrategy(queue, context)
                .searchDoctorShiftQueues(queue);

        if (fetchedQueues.isEmpty()) {
            throw new NoQueuesAvailableException(
                    "No Queues found for provided data!"
            );
        }
        return EntityModelSearchQueryResult.ofList(fetchedQueues);
    }

    private DoctorShiftQueueSearchStrategy resolveStrategy(DoctorShiftQueue queue, DoctorShiftQueueSearchContext context) {
        return searchStrategies.stream()
                .filter(strategy -> strategy.supports(queue, context))
                .findFirst()
                .orElseThrow(() -> new NoSuitableStrategyFoundException("No Suitable strategy found for Doctor Shift Queue search!"));
    }
}
