package ui;

import db.DatabaseManager;
import db.EmployeeDatabase;
import model.CoopLevel;
import model.Employee;

import java.util.Scanner;

public class ConsoleMenu {
    private final EmployeeDatabase db;
    private final DatabaseManager dbManager;

    public ConsoleMenu(EmployeeDatabase db, DatabaseManager dbManager) {
        this.db = db;
        this.dbManager = dbManager;
    }

    public void start() {
        // Load SQL backup at startup
        dbManager.loadAll(db);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Select option (1-13): ");
            String line = scanner.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("TODO: Add employee (a)");
                    break;
                case 2:
                    System.out.println("TODO: Add collaboration (b)");
                    break;
                case 3:
                    System.out.println("TODO: Remove employee (c)");
                    break;
                case 4:
                    System.out.println("TODO: Find employee by ID (d)");
                    break;
                case 5:
                    System.out.println("TODO: Execute employee skill (e)");
                    break;
                case 6:
                    System.out.println("TODO: Alphabetical listing by surname in groups (f)");
                    break;
                case 7:
                    System.out.println("TODO: Statistics - prevailing quality and most connected (g)");
                    break;
                case 8:
                    System.out.println("TODO: Count employees in groups (h)");
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
                    // Exit: save and break
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
