package com.flexigp.admissions.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;


    @Async
    @Override
    public void sendRegistrationEmail(
            String to,
            String fullName,
            String phone,
            String country,
            String rawPassword
    ) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariable("fullName", fullName);
            context.setVariable("email", to);
            context.setVariable("phone", phone);
            context.setVariable("country", country);
            context.setVariable("password", rawPassword);
            context.setVariable(
                    "loginUrl",
                    "http://localhost:4200/login"
            );

            String htmlContent = templateEngine.process(
                    "email/registration",
                    context
            );

            helper.setTo(to);
            helper.setSubject("Bienvenue sur flexiGP");
            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            // volontairement silencieux pour ne pas casser l’inscription
        }
    }

    @Async
    @Override
    public void sendPasswordResetOtp(String email, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Réinitialisation de votre mot de passe FlexiGP");
            message.setText(String.format(
                    "Bonjour,\n\n" +
                            "Vous avez demandé la réinitialisation de votre mot de passe FlexiGP.\n" +
                            "Votre code de vérification est : %s\n\n" +
                            "Ce code est valable pendant 15 minutes.\n\n" +
                            "Si vous n'êtes pas à l'origine de cette demande, ignorez simplement cet email.\n\n" +
                            "Cordialement,\n" +
                            "L'équipe FlexiGP",
                    otp
            ));

            mailSender.send(message);
            log.info("Email OTP envoyé à: {}", email);
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi de l'email OTP à: {}", email, e);
            // Ne pas propager l'erreur pour éviter l'user enumeration
        }
    }
}
