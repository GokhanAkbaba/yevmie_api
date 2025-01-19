package com.yevmie.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.amqp.AmqpException;
import org.springframework.messaging.MessagingException;
import com.yevmie.config.RabbitMQConfig;
import com.yevmie.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationSender {

    private final RabbitTemplate rabbitTemplate;

    public void sendNotification(NotificationMessage message) {
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NOTIFICATION,
                RabbitMQConfig.ROUTING_KEY_NOTIFICATION,
                message
            );
            log.info("Bildirim kuyruğa eklendi: {}", message);
        } catch (AmqpException | IllegalArgumentException | MessagingException e) {
            log.error("Bildirim gönderilemedi", e);
            throw new BusinessException("Bildirim gönderilemedi: " + e.getMessage());
        }
    }
} 