package com.example.workflow.restController;
import com.example.workflow.dto.ApiResponse;
import com.example.workflow.dto.user.request.LoginRequest;
import com.example.workflow.dto.user.response.LoginResponse;
import com.example.workflow.services.impl.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/otp")
public class OtpController {

    @Autowired
    private EmailService emailService;


    @PostMapping("/send/{email}")
    ApiResponse<String> sendOtp(@PathVariable String email) {
        return ApiResponse.<String>builder()
                .code(200)
                .message("Ok")
                .result(emailService.sendOtp(email))
                .build();
    }

    @PostMapping("/verify/{email}/{otp}")
    ApiResponse<Boolean> verifyOtp(@PathVariable String email, @PathVariable String otp) {
        return ApiResponse.<Boolean>builder()
                .code(200)
                .message("Ok")
                .result(emailService.verifyOtp(email, otp))
                .build();
    }
}
