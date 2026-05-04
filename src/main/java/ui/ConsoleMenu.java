package ui;

import db.DatabaseManager;
import db.EmployeeDatabase;
import model.Employee;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final EmployeeDatabase db;
    private final DatabaseManager dbManager;

    public ConsoleMenu(EmployeeDatabase db, DatabaseManager dbManager) {
        this.db = db;
        this.dbManager = dbManager;
    }

    public void start() {
        dbManager.loadAll(db);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            Integer choice = readIntInput(scanner, "Select option (1-13): ");
            if (choice == null) {
                continue;
            }

            switch (choice) {
                case 1:
                    handleAddEmployee(scanner);
                    break;
                case 2:
                    System.out.println("TODO: Add collaboration (b)");
                    break;
                case 3:
                    handleRemoveEmployee(scanner);
                    break;
                case 4:
                    handleFindEmployeeById(scanner);
                    break;
                case 5:
                    System.out.println("TODO: Execute employee skill (e)");
                    break;
                case 6:
                    printEmployeesBySurnameInGroups();
                    break;
                case 7:
                    System.out.println("TODO: Statistics - prevailing quality and most connected (g)");
                    break;
                case 8:
                    printCountsByGroup();
                    break;
                case 9:
                    System.out.println("TODO: Save employee to file (i)");
                    break;
                case 10:
                    System.out.println("TODO: Load employee from file (j)");
                    break;
                case 11:
                    System.out.println("TODO: Save all to SQL now (k)");
                    break;
                case 12:
                    System.out.println("TODO: Load all from SQL now (l)");
                    break;
                case 13:
                    System.out.println("Exiting: saving data to SQL...");
                    dbManager.saveAll(db);
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Choose 1-13.");
            }

            System.out.println();
        }

        scanner.close();
    }

    private void handleAddEmployee(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter surname: ");
        String surname = scanner.nextLine().trim();

        if (name.isEmpty() || surname.isEmpty()) {
            System.out.println("Warning: Name and surname cannot be empty.");
            return;
        }

        Integer birthYear = readIntInput(scanner, "Enter birth year: ");
        if (birthYear == null) {
            return;
        }

        String role = promptEmployeeRole(scanner);
        if (role == null) {
            System.out.println("Warning: Invalid role. Use 1/2 or role name.");
            return;
        }

        int newId = db.addEmployee(name, surname, birthYear, role);
        if (newId == -1) {
            System.out.println("Warning: Employee could not be added.");
            return;
        }

        System.out.println("Employee added successfully with ID: " + newId);
    }

    private void handleRemoveEmployee(Scanner scanner) {
        Integer employeeId = readIntInput(scanner, "Enter employee ID to remove: ");
        if (employeeId == null) {
            return;
        }

        boolean removed = db.removeEmployee(employeeId);
        if (removed) {
            System.out.println("Employee removed successfully.");
        } else {
            System.out.println("Warning: Employee with this ID was not found.");
        }
    }

    private void handleFindEmployeeById(Scanner scanner) {
        Integer employeeId = readIntInput(scanner, "Enter employee ID to find: ");
        if (employeeId == null) {
            return;
        }

        Employee employee = db.getEmployee(employeeId);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        printEmployeeDetails(employee);
    }

    private void printEmployeesBySurnameInGroups() {
        List<Employee> analysts = db.getDataAnalystsSortedBySurname();
        List<Employee> specialists = db.getSecuritySpecialistsSortedBySurname();

        System.out.println("Data Analysts:");
        if (analysts.isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (Employee employee : analysts) {
                printEmployeeSummary(employee);
            }
        }

        System.out.println("Security Specialists:");
        if (specialists.isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (Employee employee : specialists) {
                printEmployeeSummary(employee);
            }
        }
    }

    private void printCountsByGroup() {
        int analystCount = db.countDataAnalysts();
        int specialistCount = db.countSecuritySpecialists();
        int total = db.getTotalEmployees();

        System.out.println("Employee counts by group:");
        System.out.println("DataAnalyst: " + analystCount);
        System.out.println("SecuritySpecialist: " + specialistCount);
        System.out.println("Total: " + total);
    }

    private Integer readIntInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        try {
            int value = scanner.nextInt();
            scanner.nextLine();
            return value;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Warning: expected a number, input ignored.");
            return null;
        }
    }

    private String promptEmployeeRole(Scanner scanner) {
        System.out.print("Enter role (1=DataAnalyst, 2=SecuritySpecialist): ");
        String input = scanner.nextLine().trim();

        if (input.equals("1") || input.equalsIgnoreCase("DataAnalyst") || input.equalsIgnoreCase("Data Analyst")) {
            return "DataAnalyst";
        }

        if (input.equals("2") || input.equalsIgnoreCase("SecuritySpecialist") || input.equalsIgnoreCase("Security Specialist")) {
            return "SecuritySpecialist";
        }

        return null;
    }

    private void printEmployeeDetails(Employee employee) {
        String role = employee.getClass().getSimpleName();
        System.out.println("Employee found:");
        System.out.println("ID: " + employee.getId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Surname: " + employee.getSurname());
        System.out.println("Birth year: " + employee.getBirthYear());
        System.out.println("Role: " + role);
        System.out.println("Collaborations: " + employee.getCollaborators().size());
    }

    private void printEmployeeSummary(Employee employee) {
        System.out.println(
            "  [ID=" + employee.getId() + "] " + employee.getSurname() + " " + employee.getName()
                + " (" + employee.getBirthYear() + ")"
        );
    }

    private void printMenu() {
        System.out.println("=== Employee DB Menu ===");
        System.out.println("1) Add employee");
        System.out.println("2) Add collaboration");
        System.out.println("3) Remove employee");
        System.out.println("4) Find employee by ID");
        System.out.println("5) Execute employee skill");
        System.out.println("6) List employees alphabetically by surname (by group)");
        System.out.println("7) Statistics: prevailing cooperation quality and most connected");
        System.out.println("8) Count employees in groups");
        System.out.println("9) Save employee to file");
        System.out.println("10) Load employee from file");
        System.out.println("11) Save all to SQL now");
        System.out.println("12) Load all from SQL now");
        System.out.println("13) Exit (save and quit)");
    }
}
