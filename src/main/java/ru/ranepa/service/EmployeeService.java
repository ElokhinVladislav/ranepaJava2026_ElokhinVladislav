package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

        public BigDecimal calculateAverageSalary() {
        Iterable<Employee> employees = employeeRepository.findAll();

        return StreamSupport.stream(employees.spliterator(), false)
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(getCount()), 2, RoundingMode.HALF_UP);
    }

    private long getCount() {
        return StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
                .count();
    }

    public Optional<Employee> findHighestPaidEmployee() {
        Iterable<Employee> employees = employeeRepository.findAll();

        return StreamSupport.stream(employees.spliterator(), false)
                .max(java.util.Comparator.comparing(Employee::getSalary));
    }

    public List<Employee> findByPosition(String position) {
        if (position == null || position.isBlank()) {
            throw new IllegalArgumentException("Position cannot be empty");
        }

        Iterable<Employee> employees = employeeRepository.findAll();

        return StreamSupport.stream(employees.spliterator(), false)
                .filter(e -> e.getPosition().equalsIgnoreCase(position))
                .collect(Collectors.toList());
    }
}