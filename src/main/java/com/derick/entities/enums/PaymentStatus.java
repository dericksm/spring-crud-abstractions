package com.derick.entities.enums;

public enum PaymentStatus {
    WAITING(1, "Pendendete"),
    PAID(2, "Pago"),
    CANCELED(3, "Cancelado");

    private int value;
    private String description;

    private PaymentStatus(int i, String description) {
        this.value = i;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return description;
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
