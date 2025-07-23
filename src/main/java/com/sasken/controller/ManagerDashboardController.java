package com.sasken.controller;

import com.sasken.model.ShiftSchedule;
import com.sasken.service.ShiftScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ManagerDashboardController {

    @Autowired
    private ShiftScheduleService scheduleService;

    @GetMapping("/manager-dashboard")
    public String showDashboard(Model model) {
        List<ShiftSchedule> todaySchedules = scheduleService.getTodaySchedules();

        long remoteCount = todaySchedules.stream()
                .filter(s -> "Remote".equalsIgnoreCase(s.getPlannedMode()))
                .count();

        long officeCount = todaySchedules.stream()
                .filter(s -> "Office".equalsIgnoreCase(s.getPlannedMode()))
                .count();

        long hybridCount = todaySchedules.stream()
                .filter(s -> "Hybrid".equalsIgnoreCase(s.getPlannedMode()))
                .count();

        model.addAttribute("todaySchedules", todaySchedules);
        model.addAttribute("totalScheduled", todaySchedules.size());
        model.addAttribute("remoteCount", remoteCount);
        model.addAttribute("officeCount", officeCount);
        model.addAttribute("hybridCount", hybridCount);

        return "manager-dashboard"; // should match your HTML file name
    }
}
