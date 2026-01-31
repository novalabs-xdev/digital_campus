package com.ism.admissions.candidature.exception;

public class DocumentsManquantsException extends RuntimeException {
    public DocumentsManquantsException(String message) {
        super(message);
    }

    public DocumentsManquantsException() {
        super("Impossible de soumettre la candidature : documents manquants");
    }
}
