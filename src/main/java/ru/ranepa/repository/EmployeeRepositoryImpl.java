package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final Map<Long, Employee> employees = new HashMap<>();
    private static Long nextid = 1L;

    @Override
    public String save(Employee employee) {
        employee.setId(nextid++);
        employees.put(employee.getId(), employee);
        return "Employee " + employee.getId() + " was saved successful";
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public Iterable<Employee> findAll() {
        return employees.values();
    }

    @Override
    public String delete(Long id) {
        if (employees.remove(id) != null) {
            return "Employee with ID" + id + "was deleted";
        }
        return "Employee with ID" + id + "not found";
    }
}
