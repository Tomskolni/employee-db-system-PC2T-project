package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Employee {
    private int id;
    private String name;
    private String surname;
    private int birthYear;
    private List<Collaborator> collaborators;

    public Employee(int id, String name, String surname, int birthYear) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthYear = birthYear;
        this.collaborators = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void addCollaborator(Collaborator collaborator) {
        collaborators.add(collaborator);
    }

    public void removeCollaborator(Employee employee) {
        collaborators.removeIf(c -> c.getEmployee().getId() == employee.getId());
    }

    public abstract void executeSkill();
}
