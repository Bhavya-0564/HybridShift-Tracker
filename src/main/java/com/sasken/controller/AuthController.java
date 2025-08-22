package com.sasken.controller;

import com.sasken.model.User;
import com.sasken.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute("user") User user, Model model) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Removed user.setRole("employee");

        boolean success = userService.registerUser(user);
        if (!success) {
            model.addAttribute("error", "Username already exists. Please choose another.");
            return "signup";
        }

        return "redirect:/login";
    }

    @PostMapping("/validate-manager-pin")
    public String validatePin(@RequestParam String pin, HttpSession session) {
        if ("123456".equals(pin)) {
            session.setAttribute("isManager", true);
            return "redirect:/manager/dashboard";
        } else {
            return "redirect:/manager-pin?error=true";
        }
    }
}
