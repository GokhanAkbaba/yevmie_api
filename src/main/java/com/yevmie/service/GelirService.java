package com.yevmie.service;

import com.yevmie.dto.GelirDto;
import com.yevmie.model.Gelir;
import com.yevmie.model.User;
import com.yevmie.repository.GelirRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GelirService {
    private final GelirRepository gelirRepository;
    private final UserService userService;
    
    @CacheEvict(value = "gelirCache", allEntries = true)
    public Gelir createGelir(GelirDto gelirDto, String username) {
        User ekleyenKullanici = userService.getUserByUsername(username);
        
        Gelir gelir = new Gelir();
        gelir.setTarih(gelirDto.getTarih());
        gelir.setAciklama(gelirDto.getAciklama());
        gelir.setTutar(gelirDto.getTutar());
        gelir.setParaBirimi(gelirDto.getParaBirimi());
        gelir.setEkleyenKullanici(ekleyenKullanici);
        
        log.info("Yeni gelir kaydedildi: {}", gelirDto.getAciklama());
        return gelirRepository.save(gelir);
    }
    
    @Cacheable(value = "gelirCache", key = "#tarih")
    public BigDecimal getGunlukGelir(LocalDate tarih) {
        return gelirRepository.sumTutarByTarihBetween(
            tarih, tarih.plusDays(1));
    }
    
    @Cacheable(value = "gelirCache", key = "'latest'")
    public List<Gelir> getLatestGelirler() {
        return gelirRepository.findAll(
            PageRequest.of(0, 10, Sort.by("tarih").descending()))
            .getContent();
    }
} 