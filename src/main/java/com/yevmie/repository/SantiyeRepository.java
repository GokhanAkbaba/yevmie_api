package com.yevmie.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yevmie.model.Santiye;

@Repository
public interface SantiyeRepository extends JpaRepository<Santiye, Long> {
    List<Santiye> findByIl(String il);
    Optional<Santiye> findBySantiyeAdi(String santiyeAdi);
    
    @Query("SELECT s FROM Santiye s WHERE s.toplamHarcanan > :limit")
    List<Santiye> findByToplamHarcananGreaterThan(BigDecimal limit);
} 