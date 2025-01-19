package com.yevmie.service;

import com.yevmie.dto.CariDto;
import com.yevmie.exception.ResourceNotFoundException;
import com.yevmie.model.Cari;
import com.yevmie.model.Santiye;
import com.yevmie.model.User;
import com.yevmie.repository.CariRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CariService {
    private final CariRepository cariRepository;
    private final UserService userService;
    
    @Cacheable(value = "cariCache", key = "#id")
    public Cari getCariById(Long id) {
        return cariRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cari", "id", id));
    }
    
    @CacheEvict(value = "cariCache", key = "#result.id")
    public Cari createCari(CariDto cariDto, String username) {
        if (cariDto.getCariAdi().length() > 236) {
            throw new RuntimeException("Cari adı 236 karakterden uzun olamaz");
        }
        
        User ekleyenKullanici = userService.getUserByUsername(username);
        
        Cari cari = new Cari();
        cari.setCariAdi(cariDto.getCariAdi());
        cari.setIl(cariDto.getIl());
        cari.setSantiye(new Santiye(cariDto.getSantiyeId()));
        cari.setTur(cariDto.getTur());
        cari.setTutar(cariDto.getTutar());
        cari.setAciklama(cariDto.getAciklama());
        cari.setEkleyenKullanici(ekleyenKullanici);
        cari.setEklemeTarihi(LocalDateTime.now());
        
        log.info("Yeni cari oluşturuldu: {}", cariDto.getCariAdi());
        return cariRepository.save(cari);
    }
    
    @Caching(evict = {
        @CacheEvict(value = "cariCache", key = "#id"),
        @CacheEvict(value = "cariCache", key = "'latest'")
    })
    public void deleteCari(Long id) {
        log.info("Cari silindi: {}", id);
        cariRepository.deleteById(id);
    }
    
    @Cacheable(value = "cariCache", key = "'latest'")
    public List<Cari> getLatestCaris() {
        return cariRepository.findAll(PageRequest.of(0, 10, Sort.by("eklemeTarihi").descending()))
            .getContent();
    }
} 