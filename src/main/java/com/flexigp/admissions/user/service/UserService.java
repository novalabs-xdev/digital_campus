package com.flexigp.admissions.user.service;

import com.flexigp.admissions.user.domain.User;
import com.flexigp.admissions.user.dto.ChangePasswordRequest;
import com.flexigp.admissions.user.dto.UpdateUserRequest;
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

