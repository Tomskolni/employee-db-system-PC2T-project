package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAnalyst extends Employee {

    public DataAnalyst(int id, String name, String surname, int birthYear) {
        super(id, name, surname, birthYear);
    }

    @Override
    public void executeSkill() {
        Employee mostSharedCollaborator = findMostSharedCollaborator();
        if (mostSharedCollaborator != null) {
            System.out.println("Data Analyst " + this.getName() + " identified that " + 
                mostSharedCollaborator.getName() + " " + mostSharedCollaborator.getSurname() + 
                " has the most shared connections.");
        } else {
            System.out.println("Data Analyst " + this.getName() + " has no collaborators to analyze.");
        }
    }

    private Employee findMostSharedCollaborator() {
        if (this.getCollaborators().isEmpty()) {
            return null;
        }

        Map<Integer, Integer> sharedConnectionCount = new HashMap<>();

        for (Collaborator collab : this.getCollaborators()) {
            Employee collaborator = collab.getEmployee();
            int sharedCount = 0;

            for (Collaborator myCollab : this.getCollaborators()) {
                for (Collaborator theirCollab : collaborator.getCollaborators()) {
                    if (myCollab.getEmployee().getId() == theirCollab.getEmployee().getId()) {
                        sharedCount++;
                    }
                }
            }

            sharedConnectionCount.put(collaborator.getId(), sharedCount);
        }

        int maxSharedConnections = 0;
        int mostSharedId = -1;

        for (Map.Entry<Integer, Integer> entry : sharedConnectionCount.entrySet()) {
            if (entry.getValue() > maxSharedConnections) {
                maxSharedConnections = entry.getValue();
                mostSharedId = entry.getKey();
            }
        }

        for (Collaborator collab : this.getCollaborators()) {
            if (collab.getEmployee().getId() == mostSharedId) {
                return collab.getEmployee();
            }
        }

        return null;
    }
}
