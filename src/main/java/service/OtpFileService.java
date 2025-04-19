package service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class OtpFileService {
    public void writeOtpToFile(String otpCode, String username) {
        String fileName = "otp_codes.txt";
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(String.format("User: %s, OTP: %s%n", username, otpCode));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось записать OTP в файл", e);
        }
    }
}