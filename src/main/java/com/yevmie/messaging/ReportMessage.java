package com.yevmie.messaging;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportMessage implements Serializable {
    private String reportType;
    private Map<String, Object> parameters;
    private String requestedBy;
    private LocalDateTime requestTime;
    private String outputFormat; // PDF, EXCEL, CSV
} 