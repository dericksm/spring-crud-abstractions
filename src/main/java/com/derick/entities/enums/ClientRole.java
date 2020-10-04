package com.derick.entities.enums;

public enum ClientRole {
    ADMIN(1, "ROLE_ADMIN"),
    CLIENT(2, "ROLE_CLIENT");

    private int value;
    private String description;

    private ClientRole(int i, String description) {
        this.value = i;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static ClientRole toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (ClientRole value : ClientRole.values()) {
            if (cod.equals(value.getValue())) {
                return value;
            }
        }

        throw new IllegalArgumentException("Invalid role");
    }
}
