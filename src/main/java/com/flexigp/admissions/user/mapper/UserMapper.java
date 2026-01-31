package com.flexigp.admissions.user.mapper;

import com.flexigp.admissions.user.domain.User;
import com.flexigp.admissions.user.dto.UserProfileResponse;
import com.flexigp.admissions.user.dto.UserResponse;

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
