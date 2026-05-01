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

        System.out.println("Step 3: Creating in-memory EmployeeDatabase...");
        EmployeeDatabase db = new EmployeeDatabase();
        System.out.println("- EmployeeDatabase created successfully\n");

        System.out.println("Step 4: Adding 5 employees...");
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

        System.out.println("Step 5: Total employees: " + db.getTotalEmployees());
        System.out.println("Step 6: DatabaseManager status: " + (dbManager.isAvailable() ? "ACTIVE" : "INACTIVE"));
        System.out.println();

        System.out.println("Step 7: Disconnecting from database...");
        dbManager.disconnect();
        System.out.println();

        System.out.println("=== Test Complete ===");
    }
}