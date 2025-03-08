package com.example.springboot.controller;
import com.example.springboot.model.Employee;
import com.example.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        System.out.println("Fetching employee with ID: " + id); // Debugging log

        Employee employee = employeeService.getEmployeeById(id);

        if (employee == null) {
            System.out.println("Employee with ID " + id + " not found."); // Debugging log
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with ID: " + id);
        }

        return ResponseEntity.ok(employee);
    }
    //  Add a new employee
    @PostMapping
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    // get actual percentage distribution of categories
    @GetMapping("/actual-percentages")
    public Map<String, Double> getActualPercentages() {
        return employeeService.calculateActualPercentages();
    }

    // Get deviation from standard percentages
    @GetMapping("/deviation")
    public Map<String, Double> getDeviation() {
        return employeeService.calculateDeviation();
    }

    // Suggest employees for rating change
    @GetMapping("/suggest-rating-change")
    public List<Employee> suggestEmployeesForRatingChange() {
        return employeeService.suggestEmployeesForRatingChange();
    }
}
