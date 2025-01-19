package com.yevmie.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SantiyeDto {
    @NotBlank(message = "Şantiye adı boş olamaz")
    private String santiyeAdi;
    
    @NotBlank(message = "İl bilgisi boş olamaz")
    private String il;
} 