package com.sasken.controller;

import com.sasken.model.Employee;
import com.sasken.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmployeeViewController {

    @Autowired
    private EmployeeService employeeService;

    // View-only employee directory (for regular users)
    @GetMapping("/employee/directory")
    public String showEmployeeDirectory(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employee-directory";
    }
}
