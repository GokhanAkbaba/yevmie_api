package com.yevmie.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yevmie.dto.SantiyeDto;
import com.yevmie.model.Santiye;
import com.yevmie.service.SantiyeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/santiye")
@RequiredArgsConstructor
@Tag(name = "Şantiye İşlemleri", description = "Şantiye kayıt ve listeleme işlemleri")
public class SantiyeController {
    private final SantiyeService santiyeService;

    @PostMapping
    @Operation(summary = "Yeni şantiye oluştur")
    public ResponseEntity<Santiye> createSantiye(@Valid @RequestBody SantiyeDto request) {
        Santiye santiye = santiyeService.createSantiye(
            request.getSantiyeAdi(),
            request.getIl()
        );
        return ResponseEntity.ok(santiye);
    }

    @PutMapping("/{id}/toplam-harcanan")
    @Operation(summary = "Şantiye toplam harcamasını güncelle")
    public ResponseEntity<Void> updateSantiyeToplamHarcanan(@PathVariable Long id) {
        santiyeService.updateSantiyeToplamHarcanan(id);
        return ResponseEntity.ok().build();
    }
} 