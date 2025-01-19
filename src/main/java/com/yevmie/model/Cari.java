package com.yevmie.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.yevmie.model.enums.CariTur;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cariler")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cari {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String cariAdi;
    private String il;
    
    @ManyToOne
    private Santiye santiye;
    
    @Enumerated(EnumType.STRING)
    private CariTur tur;
    
    private BigDecimal tutar;
    
    @Column(columnDefinition = "TEXT")
    private String aciklama;
    
    @ManyToOne
    private User ekleyenKullanici;
    
    private LocalDateTime eklemeTarihi;
}