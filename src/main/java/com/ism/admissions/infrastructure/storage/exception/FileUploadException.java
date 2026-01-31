package com.ism.admissions.infrastructure.storage.exception;

public class FileUploadException extends RuntimeException {
    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
