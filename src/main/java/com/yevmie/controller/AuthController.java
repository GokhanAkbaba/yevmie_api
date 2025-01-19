package com.yevmie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yevmie.dto.LoginRequest;
import com.yevmie.dto.UserDto;
import com.yevmie.model.User;
import com.yevmie.security.JwtTokenProvider;
import com.yevmie.security.dto.JwtResponse;
import com.yevmie.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Kimlik Doğrulama", description = "Kullanıcı kimlik doğrulama işlemleri")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(
        summary = "Kullanıcı girişi",
        description = "Kullanıcı adı ve şifre ile giriş yaparak JWT token alır",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Başarılı giriş",
                content = @Content(schema = @Schema(implementation = JwtResponse.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Geçersiz kimlik bilgileri"
            )
        }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);

            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(
        summary = "Yeni kullanıcı kaydı",
        description = "Yeni bir kullanıcı hesabı oluşturur",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Başarılı kayıt",
                content = @Content(schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Geçersiz kullanıcı bilgileri"
            )
        }
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) {
        try {
            User user = userService.createUser(userDto);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 