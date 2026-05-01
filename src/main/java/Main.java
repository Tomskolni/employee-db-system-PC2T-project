import db.*;
import model.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== EmployeeDatabase Manual Test ===\n");

        EmployeeDatabase db = new EmployeeDatabase();
        System.out.println("Step 1: Created new EmployeeDatabase\n");

        System.out.println("Step 2: Adding 3 employees...");
        int id1 = db.addEmployee("John", "Doe", 1990, "DataAnalyst");
        System.out.println("- Added DataAnalyst: John Doe (ID: " + id1 + ")");
        
        int id2 = db.addEmployee("Alice", "Johnson", 1992, "DataAnalyst");
        System.out.println("- Added DataAnalyst: Alice Johnson (ID: " + id2 + ")");
        
        int id3 = db.addEmployee("Bob", "Smith", 1988, "SecuritySpecialist");
        System.out.println("- Added SecuritySpecialist: Bob Smith (ID: " + id3 + ")\n");

        System.out.println("Step 3: Total employees in database: " + db.getTotalEmployees());
        System.out.println("Employees:");
        for (Employee emp : db.getAll()) {
            String role = emp instanceof DataAnalyst ? "DataAnalyst" : "SecuritySpecialist";
            System.out.println("  - ID: " + emp.getId() + " | Name: " + emp.getName() + " " + emp.getSurname() + " | Role: " + role);
        }
        System.out.println();

        System.out.println("Step 4: Number of DataAnalysts: " + db.countDataAnalysts());
        System.out.println("Step 4: Number of SecuritySpecialists: " + db.countSecuritySpecialists() + "\n");

        System.out.println("Step 5: Adding collaboration between John (ID 1) and Alice (ID 2)...");
        boolean collabAdded = db.addCollaboration(id1, id2, CoopLevel.GOOD);
        System.out.println("- Collaboration added: " + (collabAdded ? "SUCCESS" : "FAILED") + "\n");

        System.out.println("Step 6: Removing employee ID 1 (John Doe)...");
        boolean removed = db.removeEmployee(id1);
        System.out.println("- Employee removed: " + (removed ? "SUCCESS" : "FAILED") + "\n");

        System.out.println("Step 7: Total employees after deletion: " + db.getTotalEmployees());
        System.out.println("Remaining employees:");
        for (Employee emp : db.getAll()) {
            String role = emp instanceof DataAnalyst ? "DataAnalyst" : "SecuritySpecialist";
            System.out.println("  - ID: " + emp.getId() + " | Name: " + emp.getName() + " " + emp.getSurname() + " | Role: " + role);
        }
        System.out.println("Step 7: Number of DataAnalysts after deletion: " + db.countDataAnalysts() + "\n");

        System.out.println("=== Test Complete ===");
    }
}