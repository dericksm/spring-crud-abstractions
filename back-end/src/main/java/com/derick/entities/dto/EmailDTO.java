package com.derick.entities.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class EmailDTO implements Serializable {

    @NotEmpty(message = "Can not be empty")
    @Email(message = "Invalid email")
    private String email;

    public EmailDTO(String email) {
        this.email = email;
    }

    public EmailDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
