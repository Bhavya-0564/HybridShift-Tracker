package com.sasken.controller;

import com.sasken.model.WorkLog;
import com.sasken.service.EmployeeService;
import com.sasken.service.WorkLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WorkLogController {

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/log-work")
    public String showWorkLogForm(Model model) {
        model.addAttribute("workLog", new WorkLog());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "work-log-form";
    }

    @PostMapping("/log-work")
    public String logWork(@ModelAttribute WorkLog workLog) {
        // ❌ No need to manually set employee
        workLogService.save(workLog);
        return "redirect:/log-work";
    }

    @GetMapping("/work-logs")
    public String viewLogs(Model model) {
        model.addAttribute("logs", workLogService.getAllWorkLogs());
        return "work-log-list";
    }
}
