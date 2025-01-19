package com.yevmie.util;

import java.util.regex.Pattern;

import com.yevmie.exception.ValidationException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationUtil {
    
    private static final String EMAIL_REGEX = 
        "^[A-Za-z0-9+_.-]+@(.+)$";
    
    private static final String PHONE_REGEX = 
        "^(\\+90|0)?[0-9]{10}$";

    public static void validateEmail(String email) {
        if (!Pattern.compile(EMAIL_REGEX).matcher(email).matches()) {
            throw new ValidationException("Geçersiz email formatı");
        }
    }

    public static void validatePhone(String phone) {
        if (!Pattern.compile(PHONE_REGEX).matcher(phone).matches()) {
            throw new ValidationException("Geçersiz telefon numarası formatı");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new ValidationException("Şifre en az 8 karakter olmalıdır");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new ValidationException("Şifre en az bir büyük harf içermelidir");
        }

        if (!password.matches(".*[a-z].*")) {
            throw new ValidationException("Şifre en az bir küçük harf içermelidir");
        }

        if (!password.matches(".*\\d.*")) {
            throw new ValidationException("Şifre en az bir rakam içermelidir");
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            throw new ValidationException("Şifre en az bir özel karakter içermelidir");
        }
    }

    public static void validateTCKN(String tckn) {
        if (tckn == null || tckn.length() != 11 || !tckn.matches("\\d+")) {
            throw new ValidationException("Geçersiz TC Kimlik Numarası");
        }

        int[] digits = tckn.chars().map(c -> c - '0').toArray();
        
        int sum1 = 0, sum2 = 0;
        for (int i = 0; i < 9; i++) {
            sum1 += digits[i];
            if (i % 2 == 0) sum2 += digits[i];
        }
        
        if ((sum1 * 7 - sum2 * 2) % 10 != digits[9] || 
            sum1 % 10 != digits[10]) {
            throw new ValidationException("Geçersiz TC Kimlik Numarası");
        }
    }
} 