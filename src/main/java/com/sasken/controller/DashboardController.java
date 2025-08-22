package com.sasken.controller;

import com.sasken.model.Employee;
import com.sasken.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        // Spring Security username (in your case, probably email)
        String email = authentication.getName();  

        // Add just the email by default
        model.addAttribute("username", email);

        // ✅ Fetch employee by email (instead of username)
        Employee emp = employeeService.getEmployeeByEmail(email);
        if (emp != null) {
            model.addAttribute("username", emp.getName()); // show full name
        }

        return "dashboard"; // Loads dashboard.html
    }
}
