package com.ism.admissions.security.current;

import com.ism.admissions.user.domain.User;

public interface CurrentUserProvider {
    User getCurrentUser();
}
