package com.yevmie.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.yevmie.model.enums.ParaBirimi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GelirDto {
    private LocalDate tarih;
    private String aciklama;
    private BigDecimal tutar;
    private ParaBirimi paraBirimi;
} 