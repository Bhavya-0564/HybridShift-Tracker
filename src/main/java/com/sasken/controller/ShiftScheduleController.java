package com.sasken.controller;

import com.sasken.model.Employee;
import com.sasken.model.ShiftSchedule;
import com.sasken.service.EmployeeService;
import com.sasken.service.ShiftScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ShiftScheduleController {

    @Autowired
    private ShiftScheduleService scheduleService;

    @Autowired
    private EmployeeService employeeService;

    // GET form for adding a new shift schedule
    @GetMapping("/schedules/new")
    public String showScheduleForm(Model model) {
        model.addAttribute("schedule", new ShiftSchedule());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "add-schedule";  // Thymeleaf template: add-schedule.html
    }

    // POST form submission to save a new schedule
    @PostMapping("/schedules")
    public String saveSchedule(@RequestParam("employeeId") Long employeeId,
                               @ModelAttribute("schedule") ShiftSchedule schedule) {
        Employee employee = employeeService.getEmployeeById(employeeId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + employeeId));
        
        schedule.setEmployee(employee);
        scheduleService.saveSchedule(schedule);
        return "redirect:/calendar";
    }
}
