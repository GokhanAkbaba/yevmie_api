package com.yevmie.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.yevmie.model.enums.ParaBirimi;

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
@Table(name = "gelirler")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gelir {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate tarih;
    
    @ManyToOne
    private User ekleyenKullanici;
    
    @Column(columnDefinition = "TEXT")
    private String aciklama;
    
    private BigDecimal tutar;
    
    @Enumerated(EnumType.STRING)
    private ParaBirimi paraBirimi;
    
    private BigDecimal toplam;
} 