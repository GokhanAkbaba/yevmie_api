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
public class GiderDto {
    private LocalDate tarih;
    private Long cariId;
    private String aciklama;
    private BigDecimal miktar;
    private String birim;
    private BigDecimal birimFiyat;
    private ParaBirimi paraBirimi;
} 