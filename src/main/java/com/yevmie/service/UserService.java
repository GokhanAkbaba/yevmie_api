package com.yevmie.service;

import com.yevmie.dto.UserDto;
import com.yevmie.model.User;
import com.yevmie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Cacheable(value = "userCache", key = "#username")
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    }
    
    @CacheEvict(value = "userCache", key = "#result.username")
    public User createUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Bu kullanıcı adı zaten kullanılıyor");
        }
        
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setCity(userDto.getCity());
        user.setCompany(userDto.getCompany());
        user.setPhone(userDto.getPhone());
        
        log.info("Yeni kullanıcı oluşturuldu: {}", userDto.getUsername());
        return userRepository.save(user);
    }
} 