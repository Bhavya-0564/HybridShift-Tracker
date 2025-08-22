package com.sasken.controller;

import com.sasken.model.ProductivityLog;
import com.sasken.service.EmployeeService;
import com.sasken.service.ProductivityLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/productivity")
public class ProductivityLogController {

    @Autowired
    private ProductivityLogService logService;

    @Autowired
    private EmployeeService employeeService;

    // Show form to add a new productivity log
    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("log", new ProductivityLog());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "productivity-form";
    }

    // Save or update a productivity log
    @PostMapping("/save")
    public String saveLog(@ModelAttribute("log") ProductivityLog log) {
        logService.save(log);
        return "redirect:/productivity/list";
    }

    // List all productivity logs along with late login flags
    @GetMapping("/list")
    public String listLogs(Model model) {
        List<ProductivityLog> logs = logService.getAllLogs();
        Map<Long, Boolean> lateLoginMap = logService.getLateLoginMap();
        model.addAttribute("log", new ProductivityLog());
        model.addAttribute("logs", logs);
        model.addAttribute("lateLoginMap", lateLoginMap);
        return "productivity-list";
    }

    // Load form for editing a specific log by ID
    @GetMapping("/edit/{id}")
    public String editLog(@PathVariable("id") Long id, Model model) {
        ProductivityLog log = logService.getLogById(id);
        model.addAttribute("log", log);
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "productivity-form";
    }

    // Delete a productivity log by ID
    @GetMapping("/delete/{id}")
    public String deleteLog(@PathVariable("id") Long id) {
        logService.deleteLogById(id);
        return "redirect:/productivity/list";
    }
}
