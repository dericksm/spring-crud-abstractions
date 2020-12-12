package com.derick.entities;

import com.derick.entities.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
//@JsonTypeName("paymentCard")
public class PaymentCard extends Payment implements Serializable {

    private Integer installmentNumber;

    public PaymentCard(){}

    public PaymentCard(Integer id, PaymentStatus paymentStatus, Order order, Integer installmentNumber) {
        super(id, paymentStatus, order);
        this.installmentNumber = installmentNumber;
    }

    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }
}
