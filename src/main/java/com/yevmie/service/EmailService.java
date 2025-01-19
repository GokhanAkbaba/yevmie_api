package com.yevmie.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {
    public void sendEmail(String to, String subject, String content) {
        log.info("Email gönderiliyor: {} - {}", to, subject);
        // Email gönderme mantığı
    }
} 