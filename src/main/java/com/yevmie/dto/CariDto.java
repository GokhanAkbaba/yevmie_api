package com.yevmie.dto;

import java.math.BigDecimal;

import com.yevmie.model.enums.CariTur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CariDto {
    private String cariAdi;
    private String il;
    private Long santiyeId;
    private CariTur tur;
    private BigDecimal tutar;
    private String aciklama;
} 