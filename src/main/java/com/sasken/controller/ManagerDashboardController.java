package com.sasken.controller;

import com.sasken.model.ShiftSchedule;
import com.sasken.model.Attendance;
import com.sasken.model.Employee;
import com.sasken.model.WorkLog;
import com.sasken.service.AttendanceService;
import com.sasken.service.EmployeeService;
import com.sasken.service.ShiftScheduleService;
import com.sasken.service.WorkLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manager")
public class ManagerDashboardController {

    @Autowired
    private ShiftScheduleService scheduleService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WorkLogService workLogService;

    // ✅ Manager Dashboard
    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        Boolean isManager = Optional.ofNullable((Boolean) session.getAttribute("isManager")).orElse(false);

        if (!isManager) {
            return "redirect:/manager-pin";
        }

        LocalDate today = LocalDate.now();

        // Fetch today's schedules safely
        List<ShiftSchedule> todaySchedules = Optional.ofNullable(scheduleService.getTodaySchedules())
                .orElse(Collections.emptyList());

        // Fetch today's attendance safely
        List<Attendance> todayAttendance = Optional.ofNullable(attendanceService.getAttendanceByDate(today))
                .orElse(Collections.emptyList());

        // Fetch today's work logs safely
        List<WorkLog> todayWorkLogs = Optional.ofNullable(workLogService.getAllWorkLogs())
                .orElse(Collections.emptyList())
                .stream()
                .filter(log -> today.equals(log.getWorkDate()))
                .collect(Collectors.toList());

        // Late login map
        Map<Long, Boolean> lateLoginMap = workLogService.getLateStatusMap(todayWorkLogs);

        // Counts with safe defaults
        long remoteCount = todaySchedules.stream()
                .filter(s -> "Remote".equalsIgnoreCase(s.getPlannedMode()))
                .count();

        long onsiteCount = todaySchedules.stream()
                .filter(s -> "On-site".equalsIgnoreCase(s.getPlannedMode()))
                .count();

        long hybridCount = todaySchedules.stream()
                .filter(s -> "Hybrid".equalsIgnoreCase(s.getPlannedMode()))
                .count();

        // ✅ Add attributes to model
        model.addAttribute("today", today);
        model.addAttribute("todaySchedules", todaySchedules);
        model.addAttribute("totalScheduled", todaySchedules.size());

        model.addAttribute("remoteCount", remoteCount);
        model.addAttribute("onsiteCount", onsiteCount);
        model.addAttribute("hybridCount", hybridCount);

        model.addAttribute("todayAttendance", todayAttendance);
        model.addAttribute("attendanceCount", todayAttendance.size());
        model.addAttribute("todayWorkLogs", todayWorkLogs);
        model.addAttribute("lateLoginMap", lateLoginMap);

        // ✅ Corrected: use 'shiftSchedule' as the attribute for Thymeleaf form binding
        model.addAttribute("shiftSchedule", new ShiftSchedule());

        // ✅ Employee form placeholder (needed if Thymeleaf page uses it)
        model.addAttribute("employeeForm", new Employee());

        return "manager-dashboard";
    }

    // ✅ Attendance filter page
    @GetMapping("/attendance")
    public String showAttendanceFilterPage(Model model) {
        model.addAttribute("employeeForm", new Employee());
        return "attendance-filter";
    }

    // ✅ Attendance table by date
    @GetMapping("/attendance/view")
    public String showAttendanceTable(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        List<Attendance> list = Optional.ofNullable(attendanceService.getAttendanceByDate(date))
                .orElse(Collections.emptyList());

        model.addAttribute("attendanceList", list);
        model.addAttribute("selectedDate", date);
        model.addAttribute("employeeForm", new Employee());
        return "attendance-table";
    }

    // ✅ Employee list
    @GetMapping("/employees")
    public String showEmployeeList(Model model) {
        List<Employee> employees = Optional.ofNullable(employeeService.getAllEmployees())
                .orElse(Collections.emptyList());

        model.addAttribute("employees", employees);
        model.addAttribute("employeeForm", new Employee());
        return "employee-list";
    }
}
