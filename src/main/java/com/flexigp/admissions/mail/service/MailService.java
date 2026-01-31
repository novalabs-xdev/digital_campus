package com.flexigp.admissions.mail.service;

public interface MailService {

    void sendRegistrationEmail(
            String to,
            String fullName,
            String phone,
            String country,
            String rawPassword
    );

    void sendPasswordResetOtp(String email, String otp);
}
