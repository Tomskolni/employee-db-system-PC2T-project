import db.*;
import model.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== DatabaseManager Test ===\n");

        System.out.println("Step 1: Creating DatabaseManager...");
        DatabaseManager dbManager = new DatabaseManager();
        System.out.println();

        System.out.println("Step 2: Checking SQL Availability...");
        if (dbManager.isAvailable()) {
            System.out.println("- SQL Database: AVAILABLE (tables created successfully)");
        } else {
            System.out.println("- SQL Database: UNAVAILABLE (will run in-memory only)");
        }
        System.out.println();

        boolean runIdPreservationTest = true;
        if (runIdPreservationTest) {
            System.out.println("Preparing ID test: clearing SQL database");
            EmployeeDatabase clearDb = new EmployeeDatabase();
            dbManager.saveAll(clearDb);
            System.out.println();
        }

        System.out.println("Step 3: Creating in-memory EmployeeDatabase...");
        EmployeeDatabase db = new EmployeeDatabase();
        System.out.println("- EmployeeDatabase created successfully\n");

        System.out.println("Step 4: Loading previously saved data from SQL...");
        dbManager.loadAll(db);
        int existingEmployees = db.getTotalEmployees();
        EmployeeDatabase dbToShow = db;
        System.out.println();

        if (existingEmployees == 0) {
            System.out.println("Step 5: No previous data found. Adding new employees...");
            int id1 = db.addEmployee("John", "Doe", 1990, "DataAnalyst");
            System.out.println("- Added DataAnalyst: John Doe (ID: " + id1 + ")");
            
            int id2 = db.addEmployee("Alice", "Johnson", 1992, "DataAnalyst");
            System.out.println("- Added DataAnalyst: Alice Johnson (ID: " + id2 + ")");
            
            int id3 = db.addEmployee("Bob", "Smith", 1988, "SecuritySpecialist");
            System.out.println("- Added SecuritySpecialist: Bob Smith (ID: " + id3 + ")");
            
            int id4 = db.addEmployee("Charlie", "Brown", 1995, "DataAnalyst");
            System.out.println("- Added DataAnalyst: Charlie Brown (ID: " + id4 + ")");
            
            int id5 = db.addEmployee("Diana", "Anderson", 1991, "SecuritySpecialist");
            System.out.println("- Added SecuritySpecialist: Diana Anderson (ID: " + id5 + ")\n");

            System.out.println("Step 6: Adding collaborations...");
            db.addCollaboration(id1, id2, CoopLevel.GOOD);
            db.addCollaboration(id1, id3, CoopLevel.AVERAGE);
            db.addCollaboration(id2, id3, CoopLevel.GOOD);
            System.out.println("- Added 3 collaborations");
            System.out.println();

            System.out.println("Step 7: Saving all data to SQL database...");
            dbManager.saveAll(db);

            System.out.println();
            System.out.println("Step 8: ID preservation test (delete 1-4, save, reload)...");
            db.removeEmployee(id1);
            db.removeEmployee(id2);
            db.removeEmployee(id3);
            db.removeEmployee(id4);
            dbManager.saveAll(db);

            EmployeeDatabase dbReload = new EmployeeDatabase();
            dbManager.loadAll(dbReload);
            Employee remaining = dbReload.getEmployee(id5);

            if (remaining != null && dbReload.getTotalEmployees() == 1 && remaining.getId() == id5) {
                System.out.println("- ID preservation test: PASS (remaining ID " + remaining.getId() + ")");
            } else {
                System.out.println("- ID preservation test: FAIL");
            }

            dbToShow = dbReload;
        } else {
            System.out.println("Step 5: Loaded " + existingEmployees + " employees from previous session");
            System.out.println("Step 6: Skipping new employee creation (data already loaded)");
            System.out.println("Step 7: Skipping collaboration creation (data already loaded)");
            System.out.println("Step 8: Skipping save and ID test (data already loaded)");
        }
        System.out.println();

        System.out.println("Step 9: Current database status...");
        System.out.println("- Total employees: " + dbToShow.getTotalEmployees());
        System.out.println("- DatabaseManager status: " + (dbManager.isAvailable() ? "ACTIVE" : "INACTIVE"));
        System.out.println();

        System.out.println("Step 10: All employees currently in database:");
        for (Employee emp : dbToShow.getAll()) {
            String type = emp instanceof DataAnalyst ? "DataAnalyst" : "SecuritySpecialist";
            System.out.println("  [" + emp.getId() + "] " + emp.getName() + " " + emp.getSurname() + 
                             " (born " + emp.getBirthYear() + ") - " + type);
        }
        System.out.println();

        System.out.println("Step 11: Disconnecting from database...");
        dbManager.disconnect();
        System.out.println();

        System.out.println("=== Test Complete ===");
    }
}