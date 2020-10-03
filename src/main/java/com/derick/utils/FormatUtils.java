package com.derick.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtils {

    private static NumberFormat nb = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static String formatToBrazilianMonetaryPattern(Double value) {
        return nb.format(value);
    }

    public static String formatDate(Date date) {
        return sdf.format(date);
    }
}
