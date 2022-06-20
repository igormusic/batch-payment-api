package com.tvmsoftware.batchpaymentapi.model;

public enum PaymentType {
    ITP("ITP"),
    INTERAC("INTERAC"),
    ACH("ACH"),
    BILL_PAY("BILL_PAY");

    private final String value;

    /**
     * @param value
     */
    PaymentType(final String value) {
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
