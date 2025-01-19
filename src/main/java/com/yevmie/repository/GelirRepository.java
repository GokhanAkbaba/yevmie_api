package com.yevmie.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yevmie.model.Gelir;

@Repository
public interface GelirRepository extends JpaRepository<Gelir, Long> {
    List<Gelir> findByTarihBetween(LocalDate baslangic, LocalDate bitis);
    List<Gelir> findByEkleyenKullaniciId(Long userId);
    
    @Query("SELECT SUM(g.tutar) FROM Gelir g WHERE g.tarih BETWEEN :baslangic AND :bitis")
    BigDecimal sumTutarByTarihBetween(LocalDate baslangic, LocalDate bitis);
} 