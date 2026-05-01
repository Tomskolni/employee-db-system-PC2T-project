import model.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Employee Database System - Manual Test ===\n");

        Employee analyst1 = new DataAnalyst(1, "John", "Doe", 1990);
        Employee analyst2 = new DataAnalyst(2, "Alice", "Johnson", 1992);
        Employee specialist = new SecuritySpecialist(3, "Bob", "Smith", 1988);

        System.out.println("Created 3 employees:");
        System.out.println("- " + analyst1.getName() + " " + analyst1.getSurname() + " (ID: " + analyst1.getId() + ") - DataAnalyst");
        System.out.println("- " + analyst2.getName() + " " + analyst2.getSurname() + " (ID: " + analyst2.getId() + ") - DataAnalyst");
        System.out.println("- " + specialist.getName() + " " + specialist.getSurname() + " (ID: " + specialist.getId() + ") - SecuritySpecialist");
        System.out.println();

        Collaborator collab1 = new Collaborator(analyst2, CoopLevel.GOOD);
        Collaborator collab2 = new Collaborator(specialist, CoopLevel.AVERAGE);
        Collaborator collab3 = new Collaborator(analyst1, CoopLevel.GOOD);
        Collaborator collab4 = new Collaborator(specialist, CoopLevel.GOOD);
        Collaborator collab5 = new Collaborator(analyst1, CoopLevel.AVERAGE);

        analyst1.addCollaborator(collab1);
        analyst1.addCollaborator(collab2);
        analyst2.addCollaborator(collab3);
        analyst2.addCollaborator(collab4);
        specialist.addCollaborator(collab5);

        System.out.println("Added collaborations:");
        System.out.println("- John works with Alice (GOOD) and Bob (AVERAGE)");
        System.out.println("- Alice works with John (GOOD) and Bob (GOOD)");
        System.out.println("- Bob works with John (AVERAGE)");
        System.out.println();

        System.out.println("=== Testing Polymorphic Skills ===\n");

        analyst1.executeSkill();
        System.out.println();
        
        specialist.executeSkill();
        System.out.println();

        System.out.println("=== Test Complete ===");
    }
}