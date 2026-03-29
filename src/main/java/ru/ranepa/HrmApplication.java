package ru.ranepa;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import ru.ranepa.service.EmployeeService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class HrmApplication {

    private static final EmployeeRepository repository = new EmployeeRepositoryImpl();
    private static final EmployeeService service = new EmployeeService(repository);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = readIntInput();

            switch (choice) {
                case 1 -> showAllEmployees();
                case 2 -> addEmployee();
                case 3 -> deleteEmployee();
                case 4 -> searchEmployeeById();
                case 5 -> showStatistics();
                case 6 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== HRM System Menu ===");
        System.out.println("1. Show all employees");
        System.out.println("2. Add employee");
        System.out.println("3. Delete employee");
        System.out.println("4. Search employee by ID");
        System.out.println("5. Show statistics");
        System.out.println("6. Exit");
    }

    private static void showAllEmployees() {
        System.out.println("\n=== All Employees ===");
        var employees = repository.findAll();
        employees.forEach(System.out::println);
        if (!employees.iterator().hasNext()) {
            System.out.println("No employees found.");
        }
    }

    private static void addEmployee() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Error: Name cannot be empty");
            return;
        }

        System.out.print("Enter position: ");
        String position = scanner.nextLine().trim();

        if (position.isEmpty()) {
            System.out.println("Error: Position cannot be empty");
            return;
        }

        double salary;
        while (true) {
            System.out.print("Enter salary: ");
            try {
                salary = Double.parseDouble(scanner.nextLine().trim());
                if (salary <= 0) {
                    System.out.println("Error: Salary must be positive");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number for salary");
            }
        }

        LocalDate hireDate;
        while (true) {
            System.out.print("Enter hire date (YYYY-MM-DD): ");
            try {
                hireDate = LocalDate.parse(scanner.nextLine().trim());
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format. Please use YYYY-MM-DD");
            }
        }

        try {
            Employee employee = new Employee(name, position, salary, hireDate);
            System.out.println(repository.save(employee));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteEmployee() {
        long id = readLongInput();
        System.out.println(repository.delete(id));
    }

    private static void searchEmployeeById() {
        long id = readLongInput();
        repository.findById(id)
                .ifPresentOrElse(
                        emp -> System.out.println("Found: " + emp),
                        () -> System.out.println("Employee with ID " + id + " not found")
                );
    }

    private static void showStatistics() {
        System.out.println("\n=== Statistics ===");
        System.out.println("Average salary: " + service.calculateAverageSalary());

        service.findHighestPaidEmployee()
                .ifPresentOrElse(
                        emp -> System.out.println("Highest paid: " + emp.getName() + " (" + emp.getSalary() + ")"),
                        () -> System.out.println("No employees in the system")
                );
    }

    private static int readIntInput() {
        System.out.print("Choose an option: ");
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private static long readLongInput() {
        System.out.print("Enter ID: ");
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}