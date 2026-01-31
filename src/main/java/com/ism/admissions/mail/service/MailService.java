package com.ism.admissions.mail.service;

import java.util.Map;

public interface MailService {

    void sendRegistrationEmail(
            String to,
            String fullName,
            String phone,
            String country,
            String rawPassword
    );

    void sendPasswordResetOtp(String email, String otp);

    void sendEmail(String to, String subject, String templateName, Map<String, Object> variables);

}
