package com.yevmie.controller;

import com.yevmie.dto.CariDto;
import com.yevmie.model.Cari;
import com.yevmie.service.CariService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cari")
@RequiredArgsConstructor
@Tag(name = "Cari İşlemleri", description = "Cari kayıt ve listeleme işlemleri")
public class CariController {
    private final CariService cariService;

    @Operation(
        summary = "Yeni cari oluştur",
        description = "Yeni bir cari kaydı oluşturur",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping
    public ResponseEntity<Cari> createCari(
            @Valid @RequestBody CariDto request,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        Cari cari = cariService.createCari(request, userDetails.getUsername());
        return ResponseEntity.ok(cari);
    }

    @Operation(
        summary = "Son eklenen carileri listele",
        description = "En son eklenen 10 cari kaydını listeler",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/latest")
    public ResponseEntity<List<Cari>> getLatestCaris() {
        return ResponseEntity.ok(cariService.getLatestCaris());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cari kaydını sil")
    public ResponseEntity<Void> deleteCari(@PathVariable Long id) {
        cariService.deleteCari(id);
        return ResponseEntity.noContent().build();
    }
} 