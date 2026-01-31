package com.ism.admissions.user.service;

import com.ism.admissions.infrastructure.storage.service.FileStorageService;
import com.ism.admissions.user.domain.User;
import com.ism.admissions.user.dto.ChangePasswordRequest;
import com.ism.admissions.user.dto.UpdateUserRequest;
import com.ism.admissions.user.exception.InvalidPasswordException;
import com.ism.admissions.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;


    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User updateProfile(User user, UpdateUserRequest request) {
//        user.setFullName(request.fullName());
//        user.setPhone(request.phone());
//        user.setCountry(request.country());

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void changePassword(User user, ChangePasswordRequest request) {

        if (!passwordEncoder.matches(
                request.currentPassword(),
                user.getPassword()
        )) {
            throw new InvalidPasswordException();
        }

        user.setPassword(
                passwordEncoder.encode(request.newPassword())
        );

        userRepository.save(user);
    }


    @Override
    @Transactional
    public String updateAvatar(User user, MultipartFile file) {
        // 1. Si l'utilisateur a déjà un avatar, on peut supprimer l'ancien sur S3 (optionnel)
        if (user.getAvatarUrl() != null) {
            try {
                fileStorageService.deleteFile(user.getAvatarUrl());
            } catch (Exception e) {
                // On logue l'erreur mais on ne bloque pas l'upload du nouveau
                log.warn("Impossible de supprimer l'ancien avatar : {}", e.getMessage());
            }
        }

        // 2. Upload du nouveau fichier dans le dossier "avatars"
        String newAvatarUrl = fileStorageService.uploadFile(file, "avatars");

        // 3. Mise à jour de l'entité
        user.setAvatarUrl(newAvatarUrl);
        userRepository.save(user);

        return newAvatarUrl;
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        // Soft delete : on désactive le compte au lieu de supprimer la ligne en DB
        // C'est important pour garder l'historique des transactions/trips
//        user.setEnabled(false);
        userRepository.save(user);
    }



}
