package com.tvmsoftware.batchpaymentapi.model;

public enum BatchStatus {
    PENDING("PENDING"),
    PROCESSING("PROCESSING"),
    COMPLETED_SUCCESS("COMPLETED_SUCCESS"),
    COMPLETED_WITH_ERRORS("COMPLETED_WITH_ERRORS");

    private final String value;

    /**
     * @param value
     */
    BatchStatus(final String value) {
        this.value = value;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return value;
    }
}
