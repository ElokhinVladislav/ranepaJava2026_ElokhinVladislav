package ru.ranepa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new EmployeeRepositoryImpl();
        employeeService = new EmployeeService(repository);
    }

    @Test
    void shouldCalculateAverageSalary() {
        repository.save(new Employee("Ivan Ivanov", "Developer", 100.0, LocalDate.now()));
        repository.save(new Employee("Petr Petrov", "Developer", 200.0, LocalDate.now()));
        repository.save(new Employee("Sidor Sidorov", "Developer", 300.0, LocalDate.now()));

        BigDecimal average = employeeService.calculateAverageSalary();

        assertEquals(200.0, average.doubleValue());
    }

    @Test
    void shouldFindHighestPaidEmployee() {
        repository.save(new Employee("Low Salary", "Intern", 500.0, LocalDate.now()));
        Employee expected = new Employee("High Salary", "Manager", 5000.0, LocalDate.now());
        repository.save(expected);
        repository.save(new Employee("Medium Salary", "Developer", 1000.0, LocalDate.now()));

        Employee highest = employeeService.findHighestPaidEmployee().orElse(null);

        assertNotNull(highest);
        assertEquals(expected.getSalary(), highest.getSalary());
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoEmployees() {
        var result = employeeService.findHighestPaidEmployee();

        assertTrue(result.isEmpty());
    }
}