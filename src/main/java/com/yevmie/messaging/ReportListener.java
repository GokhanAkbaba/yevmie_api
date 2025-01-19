package com.yevmie.messaging;

import java.time.LocalDateTime;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.yevmie.config.RabbitMQConfig;
import com.yevmie.service.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportListener {

    private final ReportService reportService;
    private final NotificationSender notificationSender;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_REPORT)
    public void handleReportGeneration(ReportMessage message) {
        log.info("Rapor talebi alındı: {}", message);
        
        try {
            // Raporu oluştur
            String reportPath = reportService.generateReport(
                message.getReportType(),
                message.getParameters(),
                message.getOutputFormat()
            );
            
            // Rapor hazır bildirimi gönder
            NotificationMessage notification = new NotificationMessage(
                message.getRequestedBy(),
                "Rapor Hazır",
                "Talep ettiğiniz rapor hazırlandı: " + reportPath,
                NotificationMessage.NotificationType.EMAIL,
                LocalDateTime.now()
            );
            
            notificationSender.sendNotification(notification);
            
            log.info("Rapor başarıyla oluşturuldu: {}", reportPath);
        } catch (Exception e) {
            log.error("Rapor oluşturulurken hata oluştu", e);
            
            // Hata bildirimi gönder
            NotificationMessage errorNotification = new NotificationMessage(
                message.getRequestedBy(),
                "Rapor Hatası",
                "Rapor oluşturulurken hata oluştu: " + e.getMessage(),
                NotificationMessage.NotificationType.EMAIL,
                LocalDateTime.now()
            );
            
            notificationSender.sendNotification(errorNotification);
            
            throw new AmqpRejectAndDontRequeueException("Rapor oluşturulamadı", e);
        }
    }
} 