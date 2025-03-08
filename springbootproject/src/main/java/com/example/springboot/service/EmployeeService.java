package com.example.springboot.service;

import com.example.springboot.model.Employee;
import com.example.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    private static final Map<String, Double> STANDARD_PERCENTAGES = new HashMap<>();

    static {
        STANDARD_PERCENTAGES.put("A", 10.0);
        STANDARD_PERCENTAGES.put("B", 20.0);
        STANDARD_PERCENTAGES.put("C", 40.0);
        STANDARD_PERCENTAGES.put("D", 20.0);
        STANDARD_PERCENTAGES.put("E", 10.0);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Employee saveEmployee(Employee employee) {
        System.out.println("Saving employee: " + employee); // Debugging log
        return repository.save(employee);
    }


    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }

    public Map<String, Double> calculateActualPercentages() {
        List<Employee> employees = repository.findAll();
        int totalEmployees = employees.size();

        Map<String, Long> categoryCounts = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCategory, Collectors.counting()));

        Map<String, Double> actualPercentages = new HashMap<>();
        for (String category : STANDARD_PERCENTAGES.keySet()) {
            actualPercentages.put(category, (categoryCounts.getOrDefault(category, 0L) * 100.0) / totalEmployees);
        }
        return actualPercentages;
    }

    public Map<String, Double> calculateDeviation() {
        Map<String, Double> actualPercentages = calculateActualPercentages();
        Map<String, Double> deviation = new HashMap<>();

        for (String category : STANDARD_PERCENTAGES.keySet()) {
            double deviationValue = actualPercentages.getOrDefault(category, 0.0) - STANDARD_PERCENTAGES.get(category);
            deviation.put(category, deviationValue);
        }
        return deviation;
    }

    public List<Employee> suggestEmployeesForRatingChange() {
        Map<String, Double> deviation = calculateDeviation();
        List<Employee> employees = repository.findAll();

        List<Employee> employeesToChange = new ArrayList<>();

        for (Map.Entry<String, Double> entry : deviation.entrySet()) {
            String category = entry.getKey();
            double devValue = entry.getValue();

            if (devValue > 0) {
                // Reduce employees in this category (move to a lower category)
                List<Employee> categoryEmployees = employees.stream()
                        .filter(e -> e.getCategory().equals(category))
                        .sorted(Comparator.comparingInt(Employee::getRating))
                        .collect(Collectors.toList());

                if (!categoryEmployees.isEmpty()) {
                    employeesToChange.add(categoryEmployees.get(0)); // Suggest the lowest-rated employee to downgrade
                }
            } else if (devValue < 0) {
                // Increase employees in this category (move from a higher category)
                List<Employee> higherCategoryEmployees = employees.stream()
                        .filter(e -> e.getCategory().compareTo(category) > 0)
                        .sorted(Comparator.comparingInt(Employee::getRating).reversed())
                        .collect(Collectors.toList());

                if (!higherCategoryEmployees.isEmpty()) {
                    employeesToChange.add(higherCategoryEmployees.get(0)); // Suggest the highest-rated employee to upgrade
                }
            }
        }
        return employeesToChange;
    }
}
