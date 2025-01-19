package com.yevmie.service;

import com.yevmie.dto.GiderDto;
import com.yevmie.model.Gider;
import com.yevmie.model.User;
import com.yevmie.repository.CariRepository;
import com.yevmie.repository.GiderRepository;
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
public class GiderService {
    private final GiderRepository giderRepository;
    private final UserService userService;
    private final CariRepository cariRepository;
    
    @CacheEvict(value = "giderCache", allEntries = true)
    public Gider createGider(GiderDto giderDto, String username) {
        User ekleyenKullanici = userService.getUserByUsername(username);
        
        Gider gider = new Gider();
        gider.setTarih(giderDto.getTarih());
        gider.setCari(cariRepository.findById(giderDto.getCariId())
            .orElseThrow(() -> new RuntimeException("Cari bulunamadı")));
        gider.setAciklama(giderDto.getAciklama());
        gider.setMiktar(giderDto.getMiktar());
        gider.setBirim(giderDto.getBirim());
        gider.setBirimFiyat(giderDto.getBirimFiyat());
        gider.setParaBirimi(giderDto.getParaBirimi());
        gider.setTutar(giderDto.getMiktar().multiply(giderDto.getBirimFiyat()));
        gider.setEkleyenKullanici(ekleyenKullanici);
        
        log.info("Yeni gider kaydedildi: {}", giderDto.getAciklama());
        return giderRepository.save(gider);
    }
    
    @Cacheable(value = "giderCache", key = "#tarih")
    public BigDecimal getGunlukGider(LocalDate tarih) {
        return giderRepository.sumTutarByTarihBetween(
            tarih, tarih.plusDays(1));
    }
    
    @Cacheable(value = "giderCache", key = "'latest'")
    public List<Gider> getLatestGiderler() {
        return giderRepository.findAll(
            PageRequest.of(0, 10, Sort.by("tarih").descending()))
            .getContent();
    }
} 