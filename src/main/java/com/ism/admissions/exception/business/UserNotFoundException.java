package com.ism.admissions.exception.business;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("Aucun utilisateur trouvé avec l'email : " + email);    }
}
