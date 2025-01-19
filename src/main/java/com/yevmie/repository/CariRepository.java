package com.yevmie.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yevmie.model.Cari;

@Repository
public interface CariRepository extends JpaRepository<Cari, Long> {
    List<Cari> findBySantiyeId(Long santiyeId);
    List<Cari> findByEkleyenKullaniciId(Long userId);
    List<Cari> findByIl(String il);
    
    @Query("SELECT c FROM Cari c WHERE c.eklemeTarihi >= :baslangic AND c.eklemeTarihi <= :bitis")
    List<Cari> findByTarihAraligi(LocalDateTime baslangic, LocalDateTime bitis);
} 