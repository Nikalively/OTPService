package controller;

import model.User;
import service.EmailService;
import service.OtpService;
import service.TelegramService;
import service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {
    private final OtpService otpService;
    private final UserService userService;
    private final EmailService emailService;
    private final TelegramService telegramService;

    public OtpController(
            OtpService otpService,
            UserService userService,
            EmailService emailService,
            TelegramService telegramService
    ) {
        this.otpService = otpService;
        this.userService = userService;
        this.emailService = emailService;
        this.telegramService = telegramService;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateOtp(
            @RequestParam String username,
            @RequestParam String operationId
    ) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        int codeLength = 6;
        int lifetimeSeconds = 300;

        var otpCode = otpService.createOtpCode(user, operationId, codeLength, lifetimeSeconds);

        // Отправка по Email
        emailService.sendOtp(user.getUsername(), otpCode.getCode());

        // Отправка через Telegram
        telegramService.sendOtp(otpCode.getCode());

        return ResponseEntity.ok("OTP сгенерирован и отправлен.");
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateOtp(
            @RequestParam String username,
            @RequestParam String operationId,
            @RequestParam String code
    ) {
        boolean isValid = otpService.validateOtpCode(username, operationId, code);
        if (isValid) {
            return ResponseEntity.ok("OTP валиден!");
        } else {
            return ResponseEntity.status(400).body("OTP невалиден или истёк.");
        }
    }
}
