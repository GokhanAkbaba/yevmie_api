package com.yevmie.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "santiyeler")
@Data
@NoArgsConstructor
public class Santiye {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String santiyeAdi;
    
    private String il;
    private BigDecimal toplamHarcanan;
    private LocalDateTime eklemeTarihi;
    
    public Santiye(Long id) {
        this.id = id;
    }
} 