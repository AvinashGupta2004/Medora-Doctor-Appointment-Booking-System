package com.medora.common.models;

import lombok.Getter;
import lombok.NonNull;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
@Getter
public class EntityModelSearchQueryResult<T> implements Iterable<T> {

    private final List<T> items;
    private final boolean isSingleResult;
    private final boolean isEmpty;

    private EntityModelSearchQueryResult(List<T> items, boolean isSingleResult) {
        this.items = (items != null) ? items : Collections.emptyList();
        this.isSingleResult = isSingleResult;
        this.isEmpty = this.items.isEmpty();
    }

    public static <T> EntityModelSearchQueryResult<T> ofSingle(T item) {
        return new EntityModelSearchQueryResult<>(
                item != null ? List.of(item) : Collections.emptyList(),
                true
        );
    }

    public static <T> EntityModelSearchQueryResult<T> ofList(List<T> items) {
        return new EntityModelSearchQueryResult<>(
                items != null ? items : Collections.emptyList(),
                false
        );
    }

    public Optional<T> getFirst() {
        return items.isEmpty() ? Optional.empty() : Optional.of(items.getFirst());
    }

    @Override
    @NonNull
    public Iterator<T> iterator() {
        return items.iterator();
    }
}
