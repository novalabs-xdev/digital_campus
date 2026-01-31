package com.flexigp.admissions.security.current;

import com.flexigp.admissions.exception.business.UnauthenticatedException;
import com.flexigp.admissions.exception.business.UserNotFoundException;
import com.flexigp.admissions.user.domain.User;
import com.flexigp.admissions.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringSecurityCurrentUserProvider implements CurrentUserProvider {
    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthenticatedException("Utilisateur non authentifié");
        }

        String email = authentication.getName();

        // Utiliser une méthode repository qui ramène le profil si nécessaire pour éviter le N+1
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable : " + email));
    }
}
