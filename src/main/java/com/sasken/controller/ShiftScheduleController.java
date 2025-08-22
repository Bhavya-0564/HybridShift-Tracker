package com.sasken.controller;

import com.sasken.model.ShiftSchedule;
import com.sasken.service.EmployeeService;
import com.sasken.service.ShiftScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ShiftScheduleController {

    @Autowired
    private ShiftScheduleService scheduleService;

    @Autowired
    private EmployeeService employeeService;

    // Show form to add new schedule
    @GetMapping("/schedules/new")
    public String showScheduleForm(Model model) {
        model.addAttribute("schedule", new ShiftSchedule());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "schedule-form";  // make sure file is named schedule-form.html and in src/main/resources/templates
    }

    // Save the schedule
    @PostMapping("/schedules")
    public String saveSchedule(@ModelAttribute("schedule") ShiftSchedule schedule) {
        scheduleService.saveSchedule(schedule); // employee is set by Thymeleaf binding to employee.id
        return "redirect:/calendar"; // or another page like /shift-schedule-list
    }

    // Show all schedules (optional)
    @GetMapping("/shift-schedule-list")
    public String showScheduleList(Model model) {
        List<ShiftSchedule> schedules = scheduleService.getAllSchedules();
        model.addAttribute("schedules", schedules);
        return "schedule-list";  // make sure to create schedule-list.html
    }
}
