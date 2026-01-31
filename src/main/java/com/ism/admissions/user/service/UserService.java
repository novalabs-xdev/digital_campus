package com.ism.admissions.user.service;

import com.ism.admissions.user.domain.User;
import com.ism.admissions.user.dto.ChangePasswordRequest;
import com.ism.admissions.user.dto.UpdateUserRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {
    User createUser(User user);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    User updateProfile(User user, UpdateUserRequest request);

    void changePassword(User user, ChangePasswordRequest request);

    String updateAvatar(User user, MultipartFile file);

    void deleteUser(User user);

}

