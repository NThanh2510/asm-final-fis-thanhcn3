package com.example.workflow.services.impl;

import com.example.workflow.entity.OTP;
import com.example.workflow.entity.User;
import com.example.workflow.exception.AppException;
import com.example.workflow.exception.ErrorCode;
import com.example.workflow.reponsitory.OtpRepository;
import com.example.workflow.reponsitory.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Slf4j
@Service
public class EmailService {
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    UserRepository userRepository;

    private final Random random = new Random();

    private String lastOtp;
    private String lastEmail;


    public String sendOtp(String toEmail) {

        Optional<User> users = Optional.ofNullable(userRepository.findByEmail(toEmail).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)));

        String otp = String.format("%06d", random.nextInt(1000000)); // Tạo OTP 6 số
        lastOtp = otp;
        lastEmail = toEmail;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Mã OTP xác thực của bạn");
            helper.setText("<h3>Mã OTP của bạn là: <b>" + otp + "</b></h3>", true);

            mailSender.send(message);

            OTP request = new OTP();
            request.setEmail(lastEmail);
            request.setOtpCode(lastOtp);
            request.setCreatedAt(LocalDateTime.now());
            LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);
            request.setExpirationTime(expirationTime);
            request.setUsed(false);
            otpRepository.save(request);
            return "OTP đã được gửi đến email: " + toEmail;
        } catch (MessagingException e) {
            return "Gửi email thất bại: " + e.getMessage();
        }
    }

    public boolean verifyOtp(String email, String otp) {
        List<OTP> optRecord = otpRepository.findByEmail(email);
        log.info(String.valueOf(optRecord));
        LocalDateTime currentTime = LocalDateTime.now();

        if (!optRecord.isEmpty()) {
            for (OTP otpRecord : optRecord) {
                if (otp.equals(otpRecord.getOtpCode())
                        && currentTime.isAfter(otpRecord.getCreatedAt())
                        && currentTime.isBefore(otpRecord.getExpirationTime())
                        && !otpRecord.isUsed() ) {
                    otpRecord.setUsed(true);
                    otpRepository.save(otpRecord);
                    return true;
                }
            }
           return false;
        }
        return false;
    }
}
