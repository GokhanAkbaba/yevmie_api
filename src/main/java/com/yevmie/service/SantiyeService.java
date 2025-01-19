package com.yevmie.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.yevmie.model.Gider;
import com.yevmie.model.Santiye;
import com.yevmie.repository.GiderRepository;
import com.yevmie.repository.SantiyeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
@RequiredArgsConstructor
public class SantiyeService {
    private final SantiyeRepository santiyeRepository;
    private final GiderRepository giderRepository;
    
    public Santiye createSantiye(String santiyeAdi, String il) {
        if (santiyeRepository.findBySantiyeAdi(santiyeAdi).isPresent()) {
            throw new RuntimeException("Bu şantiye adı zaten kullanılıyor");
        }
        
        Santiye santiye = new Santiye();
        santiye.setSantiyeAdi(santiyeAdi);
        santiye.setIl(il);
        
        log.info("Yeni şantiye oluşturuldu: {}", santiyeAdi);
        return santiyeRepository.save(santiye);
    }
    
    public void updateSantiyeToplamHarcanan(Long santiyeId) {
        Santiye santiye = santiyeRepository.findById(santiyeId)
            .orElseThrow(() -> new RuntimeException("Şantiye bulunamadı"));
            
        BigDecimal toplamHarcanan = giderRepository.findByCariId(santiyeId)
            .stream()
            .map(Gider::getTutar)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        santiye.setToplamHarcanan(toplamHarcanan);
        santiyeRepository.save(santiye);
        
        log.info("Şantiye toplam harcanan güncellendi: {}", santiyeId);
    }
} 