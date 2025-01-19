package com.yevmie.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.yevmie.exception.ValidationException;
import com.yevmie.model.enums.ParaBirimi;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MoneyUtil {
    
    private static final DecimalFormat MONEY_FORMAT = 
        new DecimalFormat("#,##0.00");
    
    public static String formatMoney(BigDecimal amount) {
        return amount != null ? MONEY_FORMAT.format(amount) : "0.00";
    }

    public static String formatMoneyWithCurrency(BigDecimal amount, ParaBirimi paraBirimi) {
        return String.format("%s %s", formatMoney(amount), paraBirimi.name());
    }

    public static BigDecimal parseMoney(String moneyStr) {
        try {
            return new BigDecimal(moneyStr.replaceAll("[^\\d.]", ""));
        } catch (NumberFormatException e) {
            throw new ValidationException("Geçersiz para formatı");
        }
    }

    public static BigDecimal calculateKDV(BigDecimal amount, int kdvOrani) {
        return amount.multiply(BigDecimal.valueOf(kdvOrani))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
} 