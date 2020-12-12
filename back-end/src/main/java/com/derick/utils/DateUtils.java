package com.derick.utils;

import com.derick.entities.PaymentSlip;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static void addWeek(PaymentSlip paymentSlip, Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        paymentSlip.setExpiryDate(cal.getTime());
    }
}
