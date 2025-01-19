package com.yevmie.messaging;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage implements Serializable {
    private String to;
    private String subject;
    private String content;
    private NotificationType type;
    private LocalDateTime timestamp;

    public enum NotificationType {
        EMAIL,
        SMS,
        PUSH
    }
} 