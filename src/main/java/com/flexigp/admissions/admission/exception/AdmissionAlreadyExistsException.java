package com.flexigp.admissions.admission.exception;

public class AdmissionAlreadyExistsException extends RuntimeException {
    public AdmissionAlreadyExistsException(String message) {
        super(message);
    }

    public AdmissionAlreadyExistsException(Long candidatureId) {
        super("Une admission existe déjà pour la candidature id=" + candidatureId);
    }
}
