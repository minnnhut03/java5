package com.java5.demoJV5.controller;

import com.java5.demoJV5.entity.UserEntity;
import com.java5.demoJV5.jpa.UserJPA;
import com.java5.demoJV5.service.EmailService;
import com.java5.demoJV5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    @Autowired
    private UserJPA userJPA;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showForgotPasswordForm() {
        return "user/ForgotPassword";
    }

    @PostMapping
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        Optional<UserEntity> userOpt = userJPA.findByEmail(email);

        if (userOpt.isEmpty()) {
            model.addAttribute("error", "Email không tồn tại trong hệ thống.");
            return "user/ForgotPassword";
        }

        // Tạo OTP và thời gian hết hạn
        String otp = generateOTP();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(3);

        // Lưu OTP vào database
        UserEntity user = userOpt.get();
        user.setResetToken(otp);
        user.setOtpExpiry(expiryTime);
        userJPA.save(user);

        // Gửi OTP qua email
        try {
            emailService.sendEmail(email, "Mã OTP của bạn", "Mã OTP của bạn là: " + otp + ". Có hiệu lực trong 3 phút.");
            model.addAttribute("message", "OTP đã được gửi đến email của bạn.");
            model.addAttribute("email", email);
            return "user/VerifyOTP";
        } catch (Exception e) {
            model.addAttribute("error", "Gửi email thất bại. Vui lòng thử lại.");
            return "user/ForgotPassword";
        }
    }

    @PostMapping("/verify-otp")
    public String verifyOTP(@RequestParam("email") String email, @RequestParam("otp") String otp, Model model) {
        Optional<UserEntity> userOpt = userJPA.findByEmail(email);

        if (userOpt.isEmpty()) {
            model.addAttribute("error", "Email không hợp lệ.");
            return "user/VerifyOTP";
        }

        UserEntity user = userOpt.get();

        if (!otp.equals(user.getResetToken()) || user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "OTP không hợp lệ hoặc đã hết hạn.");
            return "user/VerifyOTP";
        }

        model.addAttribute("email", email);
        return "user/ResetPassword";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("email") String email, Model model) {
        model.addAttribute("email", email);
        return "user/ResetPassword";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("newPassword") String newPassword,
                                Model model) {
        Optional<UserEntity> userOpt = userJPA.findByEmail(email);

        if (userOpt.isEmpty()) {
            model.addAttribute("error", "Email không tồn tại.");
            return "user/ResetPassword";
        }

        UserEntity user = userOpt.get();

        // Cập nhật mật khẩu mới
        userService.updatePassword(email, newPassword);

        // Xóa OTP sau khi đặt lại mật khẩu
        user.setResetToken(null);
        user.setOtpExpiry(null);
        userJPA.save(user);

        return "redirect:/login"; // Chuyển hướng đến trang đăng nhập
    }


    private String generateOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
