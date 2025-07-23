package com.sasken.controller;

import com.sasken.model.ProductivityLog;
import com.sasken.service.EmployeeService;
import com.sasken.service.ProductivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productivity")
public class ProductivityLogController {

    @Autowired
    private ProductivityLogService logService;

    @Autowired
    private EmployeeService employeeService;

    // Show form (for create and update)
    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("log", new ProductivityLog());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "productivity-form";
    }

    // Submit form (create or update)
    @PostMapping("/save")
    public String saveLog(@ModelAttribute("log") ProductivityLog log) {
        logService.save(log);
        return "redirect:/productivity/list";
    }

    // List all productivity logs
    @GetMapping("/list")
    public String listLogs(Model model) {
        model.addAttribute("logs", logService.getAllLogs());
        return "productivity-list";
    }

    // Load form for editing a log
    @GetMapping("/edit/{id}")
    public String editLog(@PathVariable("id") Long id, Model model) {
        ProductivityLog log = logService.getLogById(id);
        model.addAttribute("log", log);
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "productivity-form";
    }

    // Delete a productivity log
    @GetMapping("/delete/{id}")
    public String deleteLog(@PathVariable("id") Long id) {
        logService.deleteLogById(id);
        return "redirect:/productivity/list";
    }
}
