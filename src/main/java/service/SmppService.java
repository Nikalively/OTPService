package service;

import org.springframework.stereotype.Service;

@Service
public class SmppService {
    public void sendOtp(String phoneNumber, String otpCode) {

        // на данный момент реализована только эмуляция отправки смс

        System.out.printf("SMS OTP на %s: %s%n", phoneNumber, otpCode);
    }
}
