package com.flexigp.admissions.security.current;

import com.flexigp.admissions.user.domain.User;

public interface CurrentUserProvider {
    User getCurrentUser();
}
