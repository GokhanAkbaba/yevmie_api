package com.yevmie.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SmsService {
    public void sendSms(String to, String content) {
        log.info("SMS gönderiliyor: {}", to);
        // SMS gönderme mantığı
    }
} 