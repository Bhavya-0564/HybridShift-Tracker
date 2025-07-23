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

    @GetMapping("/employee-form")
    public String showForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form";
    }

    @PostMapping("/employees")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeService.addEmployee(employee);
        return "redirect:/employee-form";
    }

    @GetMapping("/employee-list")
    public String viewEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employee-list";
    }

    @GetMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employee-list";
    }

    @GetMapping("/employee/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        return "employee-form";
    }

    @PostMapping("/employee/update")
    public String updateEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee); // assumes update
        return "redirect:/employee-list";
    }
}
