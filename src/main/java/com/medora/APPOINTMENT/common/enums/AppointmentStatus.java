package com.medora.APPOINTMENT.common.enums;

import java.util.Set;

public enum AppointmentStatus {
    PENDING {
        @Override
        public Set<AppointmentStatus> nextValidStates() {
            return Set.of(BOOKED, CANCELLED, NO_SHOW);
        }
    },
    BOOKED {
        @Override
        public Set<AppointmentStatus> nextValidStates() {
            return Set.of(CHECKED_IN, CANCELLED, NO_SHOW);
        }
    },
    CHECKED_IN {
        @Override
        public Set<AppointmentStatus> nextValidStates() {
            return Set.of(COMPLETED, CANCELLED, NO_SHOW);
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
