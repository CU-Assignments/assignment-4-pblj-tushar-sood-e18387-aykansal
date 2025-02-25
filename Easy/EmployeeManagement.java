import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeManagementSystem {
    // ArrayList to store Employee objects
    private ArrayList<Employee> employees = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    // Main method to run the program
    public static void main(String[] args) {
        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        ems.run();
    }

    // Method to run the employee management system
    public void run() {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    updateEmployee();
                    break;
                case 3:
                    removeEmployee();
                    break;
                case 4:
                    searchEmployee();
                    break;
                case 5:
                    displayAllEmployees();
                    break;
                case 6:
                    System.out.println("Exiting the Employee Management System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Display the menu options
    private void displayMenu() {
        System.out.println("\n=== Employee Management System ===");
        System.out.println("1. Add Employee");
        System.out.println("2. Update Employee");
        System.out.println("3. Remove Employee");
        System.out.println("4. Search Employee");
        System.out.println("5. Display All Employees");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    // Employee class with encapsulation
    static class Employee {
        private int id;
        private String name;
        private double salary;

        public Employee(int id, String name, double salary) {
            this.id = id;
            this.name = name;
            this.salary = salary;
        }

        // Getters and setters for encapsulation
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public double getSalary() { return salary; }
        public void setSalary(double salary) { this.salary = salary; }

        @Override
        public String toString() {
            return "Employee[ID=" + id + ", Name=" + name + ", Salary=" + salary + "]";
        }
    }

    // Method to add an employee
    private void addEmployee() {
        System.out.print("Enter Employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (Employee emp : employees) {
            if (emp.getId() == id) {
                System.out.println("Employee with ID " + id + " already exists!");
                return;
            }
        }

        System.out.print("Enter Employee Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Employee Salary: ");
        double salary = scanner.nextDouble();

        employees.add(new Employee(id, name, salary));
        System.out.println("Employee added successfully!");
    }

    // Method to update employee details
    private void updateEmployee() {
        System.out.print("Enter Employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (Employee emp : employees) {
            if (emp.getId() == id) {
                System.out.print("Enter new Name (or press Enter to keep current): ");
                String name = scanner.nextLine().trim();
                if (!name.isEmpty()) {
                    emp.setName(name);
                }

                System.out.print("Enter new Salary (or press Enter to keep current): ");
                String salaryInput = scanner.nextLine().trim();
                if (!salaryInput.isEmpty()) {
                    try {
                        emp.setSalary(Double.parseDouble(salaryInput));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid salary format!");
                        return;
                    }
                }
                System.out.println("Employee updated successfully!");
                return;
            }
        }
        System.out.println("Employee with ID " + id + " not found!");
    }

    // Method to remove an employee
    private void removeEmployee() {
        System.out.print("Enter Employee ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (Employee emp : employees) {
            if (emp.getId() == id) {
                employees.remove(emp);
                System.out.println("Employee removed successfully!");
                return;
            }
        }
        System.out.println("Employee with ID " + id + " not found!");
    }

    // Method to search for an employee
    private void searchEmployee() {
        System.out.println("Search by: 1. ID 2. Name 3. Salary");
        int searchType = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (searchType) {
            case 1:
                System.out.print("Enter Employee ID to search: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                for (Employee emp : employees) {
                    if (emp.getId() == id) {
                        System.out.println("Found: " + emp);
                        return;
                    }
                }
                System.out.println("Employee with ID " + id + " not found!");
                break;

            case 2:
                System.out.print("Enter Employee Name to search: ");
                String name = scanner.nextLine();
                boolean found = false;
                for (Employee emp : employees) {
                    if (emp.getName().equalsIgnoreCase(name)) {
                        System.out.println("Found: " + emp);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No employee found with name " + name);
                }
                break;

            case 3:
                System.out.print("Enter Salary to search: ");
                double salary = scanner.nextDouble();
                found = false;
                for (Employee emp : employees) {
                    if (emp.getSalary() == salary) {
                        System.out.println("Found: " + emp);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No employee found with salary " + salary);
                }
                break;

            default:
                System.out.println("Invalid search type!");
        }
    }

    // Method to display all employees
    private void displayAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees in the system!");
            return;
        }
        System.out.println("\n=== All Employees ===");
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }
}