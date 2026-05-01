import db.*;
import model.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== EmployeeDatabase Manual Test ===\n");

        EmployeeDatabase db = new EmployeeDatabase();
        System.out.println("Step 1: Created new EmployeeDatabase\n");

        System.out.println("Step 2: Adding 5 employees...");
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

        System.out.println("Step 3: Total employees in database: " + db.getTotalEmployees());
        System.out.println("Employees:");
        for (Employee emp : db.getAll()) {
            String role = emp instanceof DataAnalyst ? "DataAnalyst" : "SecuritySpecialist";
            System.out.println("  - ID: " + emp.getId() + " | Name: " + emp.getName() + " " + emp.getSurname() + " | Role: " + role);
        }
        System.out.println();

        System.out.println("Step 4: Number of DataAnalysts: " + db.countDataAnalysts());
        System.out.println("Step 4: Number of SecuritySpecialists: " + db.countSecuritySpecialists() + "\n");

        System.out.println("Step 5: Adding multiple collaborations...");
        db.addCollaboration(id1, id2, CoopLevel.GOOD);
        System.out.println("- John collaborates with Alice (GOOD)");
        
        db.addCollaboration(id1, id3, CoopLevel.AVERAGE);
        System.out.println("- John collaborates with Bob (AVERAGE)");
        
        db.addCollaboration(id2, id3, CoopLevel.GOOD);
        System.out.println("- Alice collaborates with Bob (GOOD)");
        
        db.addCollaboration(id4, id1, CoopLevel.GOOD);
        System.out.println("- Charlie collaborates with John (GOOD)");
        
        db.addCollaboration(id5, id2, CoopLevel.POOR);
        System.out.println("- Diana collaborates with Alice (POOR)\n");

        System.out.println("Step 6: Employees sorted by surname (alphabetically):");
        for (Employee emp : db.getAllSortedBySurname()) {
            System.out.println("  - " + emp.getSurname() + ", " + emp.getName() + " (ID: " + emp.getId() + ")");
        }
        System.out.println();

        System.out.println("Step 7: DataAnalysts sorted by surname:");
        for (Employee emp : db.getDataAnalystsSortedBySurname()) {
            System.out.println("  - " + emp.getSurname() + ", " + emp.getName() + " (ID: " + emp.getId() + ")");
        }
        System.out.println();

        System.out.println("Step 8: SecuritySpecialists sorted by surname:");
        for (Employee emp : db.getSecuritySpecialistsSortedBySurname()) {
            System.out.println("  - " + emp.getSurname() + ", " + emp.getName() + " (ID: " + emp.getId() + ")");
        }
        System.out.println();

        System.out.println("Step 9: Statistics");
        Employee mostConnected = db.getEmployeeWithMostConnections();
        if (mostConnected != null) {
            System.out.println("- Employee with most connections: " + mostConnected.getName() + " " + mostConnected.getSurname() 
                + " (ID: " + mostConnected.getId() + ") with " + db.getCountMostConnections() + " connections");
        } else {
            System.out.println("- No employees with connections");
        }
        
        CoopLevel prevailing = db.getPrevailingCooperationQuality();
        if (prevailing != null) {
            System.out.println("- Prevailing cooperation quality: " + prevailing);
        } else {
            System.out.println("- No cooperation data available");
        }
        System.out.println();

        System.out.println("Step 10: Removing employee ID 1 (John Doe)...");
        boolean removed = db.removeEmployee(id1);
        System.out.println("- Employee removed: " + (removed ? "SUCCESS" : "FAILED") + "\n");

        System.out.println("Step 11: Total employees after deletion: " + db.getTotalEmployees());
        System.out.println("Remaining employees:");
        for (Employee emp : db.getAll()) {
            String role = emp instanceof DataAnalyst ? "DataAnalyst" : "SecuritySpecialist";
            System.out.println("  - ID: " + emp.getId() + " | Name: " + emp.getName() + " " + emp.getSurname() + " | Role: " + role);
        }
        System.out.println();

        System.out.println("=== Test Complete ===");
    }
}