package com.java5.demoJV5.controller;

import com.java5.demoJV5.bean.UserBean;
import com.java5.demoJV5.entity.UserEntity;
import com.java5.demoJV5.jpa.UserJPA;
import com.java5.demoJV5.service.EmailRegisterService;
import com.java5.demoJV5.service.EmailService;
import com.java5.demoJV5.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;
    
    @Autowired
    UserJPA userJPA;
    
    @Autowired
    private EmailService emailService;
    
    @GetMapping("/register")
    public String register(HttpSession session, Model model) {
        model.addAttribute("userBean", new UserBean());

        // Lấy email từ session và đưa vào model để hiển thị trong giao diện
        String verifiedEmail = (String) session.getAttribute("verifiedEmail");
        model.addAttribute("verifiedEmail", verifiedEmail);

        return "user/register.html";
    }

    
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userBean") UserBean userBean,
    		Errors errors,
			Model model)
    {
    	if (userJPA.existsByEmail(userBean.getEmail())) {
            model.addAttribute("errorEmail", "Email đã tồn tại!");
            return "user/register.html"; 
        }
    	if (errors.hasErrors()) {
    		
        	return "user/register";
        }
        userService.saveUser(userBean);
        return "redirect:/login";
    }
    
    @GetMapping("/register/otp")
    public String otpForm(Model model) {
        model.addAttribute("email", "");
        return "user/otp-form";
    }

    @PostMapping("/register/otp")
    public String handleOtp(@RequestParam("email") String email,
                            @RequestParam(value = "otp", required = false) String userOtp,
                            HttpSession session,
                            HttpServletResponse response,
                            Model model,
                            @CookieValue(value = "otp", defaultValue = "") String storedOtp) {
        // Nếu không có OTP, xử lý gửi OTP
        if (userOtp == null || userOtp.isEmpty()) {
            Optional<UserEntity> userOpt = userJPA.findByEmail(email);

            if (userOpt.isPresent()) {
                model.addAttribute("error", "Email này đã được sử dụng. Vui lòng sử dụng email khác!");
                return "user/otp-form";
            }

            // Tạo OTP và gửi email
            String otp = generateOtp();
            Cookie otpCookie = new Cookie("otp", otp);
            otpCookie.setMaxAge((int) TimeUnit.MINUTES.toSeconds(3));
            otpCookie.setHttpOnly(true);
            response.addCookie(otpCookie);

            try {
                emailService.sendEmail(email, "Mã OTP của bạn", "Mã OTP của bạn là: " + otp + ". Có hiệu lực trong 3 phút.");
                model.addAttribute("message", "OTP đã được gửi đến email của bạn.");
                session.setAttribute("verifiedEmail", email);
            } catch (Exception e) {
                model.addAttribute("error", "Gửi email thất bại. Vui lòng thử lại.");
            }
            return "user/otp-form";
        }

        // Nếu có OTP, xử lý xác thực OTP
        if (storedOtp.isEmpty()) {
            model.addAttribute("error", "OTP đã hết hạn hoặc không tồn tại!");
            return "user/otp-form";
        }

        if (!userOtp.equals(storedOtp)) {
            model.addAttribute("error", "Mã OTP không hợp lệ!");
            return "user/otp-form";
        }

        // Xóa OTP khỏi cookie sau khi xác thực thành công
        Cookie otpCookie = new Cookie("otp", "");
        otpCookie.setMaxAge(0);
        otpCookie.setHttpOnly(true);
        response.addCookie(otpCookie);

        // Chuyển hướng đến trang đăng ký
        return "redirect:/register";
    }

    // Hàm tạo OTP 6 chữ số
    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(1000000));
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
