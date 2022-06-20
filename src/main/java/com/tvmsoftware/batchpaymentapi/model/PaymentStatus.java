package com.tvmsoftware.batchpaymentapi.model;

public enum PaymentStatus {
    PENDING("PENDING"),
    PROCESSING("PROCESSING"),
    COMPLETED("COMPLETED"),
    ERROR("ERROR");

    private final String value;

    /**
     * @param value
     */
    PaymentStatus(final String value) {
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
