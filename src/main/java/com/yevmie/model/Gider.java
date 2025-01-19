package com.yevmie.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.yevmie.model.enums.ParaBirimi;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "giderler")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate tarih;
    
    @ManyToOne
    private Cari cari;
    
    @ManyToOne
    private User ekleyenKullanici;
    
    @Column(columnDefinition = "TEXT")
    private String aciklama;
    
    private BigDecimal miktar;
    
    private String birim; // m3, m2 vb.
    
    private BigDecimal birimFiyat;
    
    @Enumerated(EnumType.STRING)
    private ParaBirimi paraBirimi;
    
    private BigDecimal tutar;
} 