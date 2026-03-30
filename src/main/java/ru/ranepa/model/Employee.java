package ru.ranepa.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private Long id;
    private final String name;
    private final String position;
    private final BigDecimal salary;
    private final LocalDate hireDate;

    public Employee(String name, String position, double salary, LocalDate hireDate) {
        // Валидация имени
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (!name.matches("[a-zA-Zа-яА-Я\\s-]+")) {
            throw new IllegalArgumentException("Name can only contain letters, spaces and hyphens");
        }

        // Валидация должности
        if (position == null || position.isBlank()) {
            throw new IllegalArgumentException("Position cannot be empty");
        }
        if (!position.matches("[a-zA-Zа-яА-Я\\s-]+")) {
            throw new IllegalArgumentException("Position can only contain letters, spaces and hyphens");
        }

        // Валидация зарплаты
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be positive");
        }

        // Валидация даты
        if (hireDate == null) {
            throw new IllegalArgumentException("Hire date cannot be null");
        }

        this.name = name;
        this.position = position;
        this.salary = BigDecimal.valueOf(salary);
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}