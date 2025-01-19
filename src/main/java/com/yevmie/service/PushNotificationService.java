package com.yevmie.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PushNotificationService {
    public void sendPushNotification(String to, String title, String content) {
        log.info("Push bildirimi gönderiliyor: {} - {}", to, title);
        // Push notification gönderme mantığı
    }
} 