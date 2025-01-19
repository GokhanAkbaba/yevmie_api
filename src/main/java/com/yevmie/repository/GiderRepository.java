package com.yevmie.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yevmie.model.Gider;

@Repository
public interface GiderRepository extends JpaRepository<Gider, Long> {
    List<Gider> findByTarihBetween(LocalDate baslangic, LocalDate bitis);
    List<Gider> findByCariId(Long cariId);
    List<Gider> findByEkleyenKullaniciId(Long userId);
    
    @Query("SELECT SUM(g.tutar) FROM Gider g WHERE g.tarih BETWEEN :baslangic AND :bitis")
    BigDecimal sumTutarByTarihBetween(LocalDate baslangic, LocalDate bitis);
} 