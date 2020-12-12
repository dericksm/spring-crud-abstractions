package com.derick.entities;

import com.derick.entities.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
//@JsonTypeName("paymentSlip")
public class PaymentSlip extends Payment implements Serializable {


    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date expiryDate;


    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date paymentDate;

    public PaymentSlip() {
    }

    public PaymentSlip(Integer id, PaymentStatus paymentStatus, Order order, Date expiryDate, Date paymentDate) {
        super(id, paymentStatus, order);
        this.expiryDate = expiryDate;
        this.paymentDate = paymentDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
