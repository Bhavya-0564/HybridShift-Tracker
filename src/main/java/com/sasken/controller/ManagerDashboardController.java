package com.sasken.controller;

import com.sasken.model.ShiftSchedule;
import com.sasken.model.Attendance;
import com.sasken.service.AttendanceService;
import com.sasken.service.ShiftScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerDashboardController {

    @Autowired
    private ShiftScheduleService scheduleService;

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        Boolean isManager = (Boolean) session.getAttribute("isManager");

        if (isManager == null || !isManager) {
            return "redirect:/manager-pin";
        }

        LocalDate today = LocalDate.now();

        List<ShiftSchedule> todaySchedules = scheduleService.getTodaySchedules();
        List<Attendance> todayAttendance = attendanceService.getAttendanceByDate(today);

        long remoteCount = todaySchedules.stream().filter(s -> "Remote".equalsIgnoreCase(s.getPlannedMode())).count();
        long onsiteCount = todaySchedules.stream().filter(s -> "On-site".equalsIgnoreCase(s.getPlannedMode())).count();
        long hybridCount = todaySchedules.stream().filter(s -> "Hybrid".equalsIgnoreCase(s.getPlannedMode())).count();

        model.addAttribute("todaySchedules", todaySchedules);
        model.addAttribute("totalScheduled", todaySchedules.size());
        model.addAttribute("remoteCount", remoteCount);
        model.addAttribute("officeCount", onsiteCount);
        model.addAttribute("hybridCount", hybridCount);
        model.addAttribute("todayAttendance", todayAttendance);
        model.addAttribute("attendanceCount", todayAttendance.size());

        return "manager-dashboard";
    }

    @GetMapping("/attendance")
    public String showAttendanceFilterPage() {
        return "attendance-filter";
    }

    @GetMapping("/attendance/view")
    public String showAttendanceTable(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                      Model model) {
        List<Attendance> list = attendanceService.getAttendanceByDate(date);
        model.addAttribute("attendanceList", list);
        model.addAttribute("selectedDate", date);
        return "attendance-table";
    }
}
