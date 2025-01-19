package com.yevmie.messaging;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.yevmie.config.RabbitMQConfig;
import com.yevmie.service.EmailService;
import com.yevmie.service.PushNotificationService;
import com.yevmie.service.SmsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationListener {

    private final EmailService emailService;
    private final SmsService smsService;
    private final PushNotificationService pushService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NOTIFICATION)
    public void handleNotification(NotificationMessage message) {
        log.info("Bildirim alındı: {}", message);
        
        try {
            switch (message.getType()) {
                case EMAIL -> emailService.sendEmail(
                    message.getTo(),
                    message.getSubject(),
                    message.getContent()
                );
                case SMS -> smsService.sendSms(
                    message.getTo(),
                    message.getContent()
                );
                case PUSH -> pushService.sendPushNotification(
                    message.getTo(),
                    message.getSubject(),
                    message.getContent()
                );
            }
            
            log.info("Bildirim başarıyla işlendi: {}", message);
        } catch (Exception e) {
            log.error("Bildirim işlenirken hata oluştu", e);
            // Hata durumunda yeniden kuyruğa eklenebilir
            throw new AmqpRejectAndDontRequeueException("Bildirim işlenemedi", e);
        }
    }
} 