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

    // Add new employee
    @PostMapping("/employee/save")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeService.addEmployee(employee);
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

    // Update an existing employee
    @PostMapping("/employee/update")
    public String updateEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee); // assumes update based on ID
        return "redirect:/employee-list";
    }
    @GetMapping("/employees")
public String redirectToEmployeeList() {
    return "redirect:/employee-list";
}

}
