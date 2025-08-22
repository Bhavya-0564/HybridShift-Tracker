package com.sasken.controller;

import com.sasken.model.Employee;
import com.sasken.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeUIController {

    @Autowired
    private EmployeeService employeeService;

    // Show the form to add a new employee
    @GetMapping("/employee-form")
    public String showForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form";
    }

    // Save or update employee (handles both Add & Edit)
    @PostMapping("/employees")
    public String saveOrUpdateEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee); // Works for both new and existing
        return "redirect:/employee-list";
    }

    // View all employees
    @GetMapping("/employee-list")
    public String viewEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employee-list";
    }

    // Delete an employee
    @GetMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employee-list";
    }

    // Show the edit form for an employee
    @GetMapping("/employee/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + id));
        model.addAttribute("employee", employee);
        return "employee-form";
    }
}
