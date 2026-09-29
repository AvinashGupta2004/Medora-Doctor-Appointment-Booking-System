package com.medora.APPOINTMENT.common.enums;

import java.util.Set;

public enum AppointmentStatus {
    BOOKED {
        @Override
        public Set<AppointmentStatus> nextValidStates() {
            return Set.of(CHECKED_IN, CANCELLED, NO_SHOW);
        }
    },
    CHECKED_IN {
        @Override
        public Set<AppointmentStatus> nextValidStates() {
            return Set.of(IN_CONSULTATION, CANCELLED, NO_SHOW);
        }
    },
    IN_CONSULTATION {
        @Override
        public Set<AppointmentStatus> nextValidStates() {
            return Set.of(COMPLETED);
        }
    },
    COMPLETED {
        @Override
        public Set<AppointmentStatus> nextValidStates() {
            return Set.of();
        }
    },
    CANCELLED {
        @Override
        public Set<AppointmentStatus> nextValidStates() {
            return Set.of();
        }
    },
    NO_SHOW {
        @Override
        public Set<AppointmentStatus> nextValidStates() {
            return Set.of();
        }
    };

    public abstract Set<AppointmentStatus> nextValidStates();

    public boolean canTransitionTo(AppointmentStatus status) {
        return this.nextValidStates().contains(status);
    }
}
