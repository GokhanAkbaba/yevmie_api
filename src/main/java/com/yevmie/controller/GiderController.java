package com.yevmie.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yevmie.dto.GiderDto;
import com.yevmie.model.Gider;
import com.yevmie.service.GiderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/gider")
@RequiredArgsConstructor
@Tag(name = "Gider İşlemleri", description = "Gider kayıt ve listeleme işlemleri")
public class GiderController {
    private final GiderService giderService;

    @PostMapping
    @Operation(summary = "Yeni gider kaydı oluştur")
    public ResponseEntity<Gider> createGider(
            @Valid @RequestBody GiderDto request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Gider gider = giderService.createGider(request, userDetails.getUsername());
        return ResponseEntity.ok(gider);
    }

    @GetMapping("/gunluk")
    @Operation(summary = "Günlük gider toplamını getir")
    public ResponseEntity<BigDecimal> getGunlukGider(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tarih) {
        return ResponseEntity.ok(giderService.getGunlukGider(tarih));
    }

    @GetMapping("/latest")
    @Operation(summary = "Son eklenen giderleri listele")
    public ResponseEntity<List<Gider>> getLatestGiderler() {
        return ResponseEntity.ok(giderService.getLatestGiderler());
    }
} 