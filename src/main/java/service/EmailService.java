package service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtp(String toEmail, String otpCode) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Ваш одноразовый код (OTP)");
            helper.setText("Ваш одноразовый код подтверждения: " + otpCode + ". Пожалуйста, используйте его в течение установленного времени.", true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Не удалось отправить одноразовый код на email", e);
        }
    }
}