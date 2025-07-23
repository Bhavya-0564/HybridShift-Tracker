package com.sasken.controller;

import com.sasken.model.Attendance;
import com.sasken.model.Employee;
import com.sasken.service.AttendanceService;
import com.sasken.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/attendance-form")
    public String showForm(Model model) {
        model.addAttribute("attendance", new Attendance());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "attendance-form";
    }

    @PostMapping("/attendance-form")
    public String submit(@ModelAttribute Attendance attendance) {
        Employee emp = employeeService.getEmployeeById(attendance.getEmployee().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));
        attendance.setEmployee(emp);
        attendanceService.save(attendance);
        return "redirect:/attendance-form";
    }

    @GetMapping("/attendance")
    public String viewAttendance(Model model) {
        model.addAttribute("records", attendanceService.getAll());
        return "attendance-list";
    }
}
