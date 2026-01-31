package com.ism.admissions.candidature.exception;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(Long id) {
        super("Document introuvable (id=" + id + ")");
    }
}
