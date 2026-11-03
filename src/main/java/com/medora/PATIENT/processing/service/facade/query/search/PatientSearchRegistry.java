package com.medora.PATIENT.processing.service.facade.query.search;

import com.medora.PATIENT.common.enums.PatientSearchContext;
import com.medora.PATIENT.common.exceptions.PatientNotRegisteredException;
import com.medora.PATIENT.processing.models.Patient;
import com.medora.PATIENT.processing.service.facade.query.search.strategies.PatientSearchStrategy;
import com.medora.QUEUE.common.exceptions.NoSuitableStrategyFoundException;
import com.medora.common.models.EntityModelSearchQueryResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Component
@RequiredArgsConstructor
@Validated
public class PatientSearchRegistry {

    private final List<PatientSearchStrategy> searchStrategies;

    public EntityModelSearchQueryResult<Patient> search(@NonNull Patient patient, PatientSearchContext context) {
        String contextValue = context.name();
        String prefix = contextValue.substring(0, contextValue.indexOf('_'));
        boolean isSingle = prefix.equalsIgnoreCase("SINGLE");

        Patient fetchedPatient = resolveStrategy(patient, context)
                .searchPatient(patient)
                .orElse(null);

        return EntityModelSearchQueryResult.ofSingle(fetchedPatient);
    }

    private PatientSearchStrategy resolveStrategy(Patient patient, PatientSearchContext context) {
        return searchStrategies.stream()
                .filter(strategy -> strategy.supports(patient, context))
                .findFirst()
                .orElseThrow(() -> new NoSuitableStrategyFoundException("No Suitable strategy found for Patient!"));
    }
}
