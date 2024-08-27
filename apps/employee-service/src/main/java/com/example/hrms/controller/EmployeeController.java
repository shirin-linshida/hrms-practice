package com.example.hrms.controller;

import com.example.hrms.model.Employee;
import com.example.hrms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${notification.service.url}")
    private String notificationServiceUrl;

    @GetMapping
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employees"; // Thymeleaf template name
    }

    @GetMapping("/{id}")
    public String getEmployeeById(@PathVariable Long id, Model model) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "employee-details";
        } else {
            return "redirect:/employees";
        }
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        System.out.println("Employee....................");
        return "employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee) {
        boolean isNewEmployee = (employee.getId() == null);

        employeeService.saveEmployee(employee);

        // Send email only if the employee is new
        if (isNewEmployee) {
            // Create the email request as a Map or JSON directly
            Map<String, String> emailRequest = new HashMap<>();
            emailRequest.put("to", employee.getEmail());
            emailRequest.put("subject", "Welcome to the Company");
            emailRequest.put("body", "Dear " + employee.getFirstName() + ",\n\nWelcome to the company!");

            try {
                restTemplate.postForObject(notificationServiceUrl + "/notifications/send-email", emailRequest, Void.class);
            } catch (Exception e) {
                // Log the error or handle it as needed
                System.err.println("Error sending email: " + e.getMessage());
            }
        }

        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "employee-form";
        } else {
            return "redirect:/employees";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
}
