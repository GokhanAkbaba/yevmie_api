package com.yevmie.controller;

import com.yevmie.dto.GelirDto;
import com.yevmie.model.Gelir;
import com.yevmie.service.GelirService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/gelir")
@RequiredArgsConstructor
@Tag(name = "Gelir İşlemleri", description = "Gelir kayıt ve listeleme işlemleri")
public class GelirController {
    private final GelirService gelirService;

    @Operation(
        summary = "Yeni gelir kaydı oluştur",
        description = "Yeni bir gelir kaydı oluşturur",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping
    public ResponseEntity<Gelir> createGelir(
            @Valid @RequestBody GelirDto request,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        Gelir gelir = gelirService.createGelir(request, userDetails.getUsername());
        return ResponseEntity.ok(gelir);
    }

    @Operation(
        summary = "Günlük gelir toplamını getir",
        description = "Belirtilen tarihteki toplam geliri hesaplar",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/gunluk")
    public ResponseEntity<BigDecimal> getGunlukGelir(
            @Parameter(description = "Tarih (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tarih) {
        return ResponseEntity.ok(gelirService.getGunlukGelir(tarih));
    }

    @GetMapping("/latest")
    @Operation(summary = "Son eklenen gelirleri listele")
    public ResponseEntity<List<Gelir>> getLatestGelirler() {
        return ResponseEntity.ok(gelirService.getLatestGelirler());
    }
} 