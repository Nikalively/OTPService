package service;

import model.OtpCode;
import model.User;
import repository.OtpCodeRepository;
import repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {
    private final OtpCodeRepository otpCodeRepository;
    private final UserRepository userRepository;

    public OtpService(OtpCodeRepository otpCodeRepository, UserRepository userRepository) {
        this.otpCodeRepository = otpCodeRepository;
        this.userRepository = userRepository;
    }

    // Генерация
    public String generateOtpCode(int length) {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

    // Создание и сохранение OTP-кода
    public OtpCode createOtpCode(User user, String operationId, int codeLength, int lifetimeSeconds) {
        String code = generateOtpCode(codeLength);

        OtpCode otpCode = new OtpCode();
        otpCode.setUser(user);
        otpCode.setOperationId(operationId);
        otpCode.setCode(code);
        otpCode.setStatus("ACTIVE");
        otpCode.setExpiresAt(LocalDateTime.now().plusSeconds(lifetimeSeconds));

        return otpCodeRepository.save(otpCode);
    }

    // Валидация OTP-кода
    public boolean validateOtpCode(String username, String operationId, String code) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) return false;

        Optional<OtpCode> otpCodeOptional = otpCodeRepository.findByOperationIdAndUserAndStatus(
                operationId, userOpt.get(), "ACTIVE"
        );

        if (otpCodeOptional.isEmpty()) {
            return false;
        }

        OtpCode otpCode = otpCodeOptional.get();

        if (otpCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            otpCode.setStatus("EXPIRED");
            otpCodeRepository.save(otpCode);
            return false;
        }

        if (!otpCode.getCode().equals(code)) {
            return false;
        }

        otpCode.setStatus("USED");
        otpCodeRepository.save(otpCode);

        return true;
    }
}