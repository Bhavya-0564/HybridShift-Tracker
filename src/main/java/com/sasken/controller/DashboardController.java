package com.sasken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String userDashboard(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", "Guest");
        }
        return "dashboard"; // Renders dashboard.html
    }

    @GetMapping("/log-work")
    public String logWorkPage() {
        return "log-work"; // You should have log-work.html in templates/
    }

    @GetMapping("/schedule")
    public String schedulePage() {
        return "schedule"; // You should have schedule.html in templates/
    }

    @GetMapping("/attendance")
    public String attendancePage() {
        return "attendance"; // You should have attendance.html in templates/
    }

    @GetMapping("/productivity")
    public String productivityPage() {
        return "productivity"; // You should have productivity.html in templates/
    }

    @GetMapping("/calendar")
    public String calendarPage() {
        return "calendar"; // You should have calendar.html in templates/
    }

    @GetMapping("/manager-dashboard")
    public String managerDashboardPage() {
        return "manager-dashboard"; // You should have manager-dashboard.html in templates/
    }
}
