package com.clarity.user_service.service;

import com.clarity.user_service.dto.*;
import com.clarity.user_service.model.User;
import com.clarity.user_service.repository.UserRepository;
import com.clarity.user_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;
    
    public String register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        
        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAgeBracket(request.getAgeBracket());
        user.setOnboardingComplete(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        User savedUser = userRepository.save(user);
        
        return savedUser.getId();
    }
    
    public LoginResponse login(LoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        
        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        
        // Store token in Redis (expires in 1 hour)
        redisTemplate.opsForValue().set("token:" + user.getId(), token, 1, TimeUnit.HOURS);
        
        // Create user DTO
        UserDTO userDTO = new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getAgeBracket(),
            user.isOnboardingComplete()
        );
        
        return new LoginResponse(token, userDTO);
    }
    
    public UserDTO getProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getAgeBracket(),
            user.isOnboardingComplete()
        );
    }
    
    public void logout(String userId) {
        redisTemplate.delete("token:" + userId);
    }
}