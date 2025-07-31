package com.sasken.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PinAccessController {

    private static final String MANAGER_PIN = "1234";

    @GetMapping("/manager-pin")
    public String showPinPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid PIN! Please try again.");
        }
        return "manager-pin"; // loads manager-pin.html
    }

    @PostMapping("/manager-pin")
    public String verifyPin(
            @RequestParam("pin1") String pin1,
            @RequestParam("pin2") String pin2,
            @RequestParam("pin3") String pin3,
            @RequestParam("pin4") String pin4,
            HttpSession session) {

        String enteredPin = pin1 + pin2 + pin3 + pin4;

        if (MANAGER_PIN.equals(enteredPin)) {
            session.setAttribute("isManager", true);
            return "redirect:/manager/dashboard";
        } else {
            return "redirect:/manager-pin?error=true";
        }
    }
}
