package com.derick.entities.enums;

public enum PaymentStatus {
    WAITING(1),
    PAID(2),
    CANCELED(3);

    private int value;

    private PaymentStatus(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static PaymentStatus toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (PaymentStatus value : PaymentStatus.values()) {
            if (cod.equals(value.getValue())) {
                return value;
            }
        }

        throw new IllegalArgumentException("Invalid value");
    }
}
