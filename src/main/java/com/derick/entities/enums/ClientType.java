package com.derick.entities.enums;

public enum ClientType {
    PERSON(1),
    COMPANY(2);

    private int value;

    private ClientType(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static ClientType toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (ClientType value : ClientType.values()) {
            if (cod.equals(value.getValue())) {
                return value;
            }
        }

        throw new IllegalArgumentException("Invalid value");
    }
}
