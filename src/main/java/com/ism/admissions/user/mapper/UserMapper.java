package com.ism.admissions.user.mapper;

import com.ism.admissions.user.domain.User;
import com.ism.admissions.user.dto.UserProfileResponse;
import com.ism.admissions.user.dto.UserResponse;

public class UserMapper {
    private UserMapper() {
    }

    public static UserResponse toAuthResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                null,
                user.getRole()
        );
    }

    public static UserProfileResponse toProfileResponse(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                null,
                null,
                null,
                user.getRole(),
                null
        );
    }
}
