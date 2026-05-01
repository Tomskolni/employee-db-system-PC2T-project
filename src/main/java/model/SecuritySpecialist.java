package model;

public class SecuritySpecialist extends Employee {

    public SecuritySpecialist(int id, String name, String surname, int birthYear) {
        super(id, name, surname, birthYear);
    }

    @Override
    public void executeSkill() {
        double riskScore = calculateRiskScore();
        System.out.println("Security Specialist " + this.getName() + " calculated risk score: " + 
            String.format("%.2f", riskScore));
    }

    private double calculateRiskScore() {
        if (this.getCollaborators().isEmpty()) {
            return 0.0;
        }

        int connectionCount = this.getCollaborators().size();
        double totalQuality = 0;

        for (Collaborator collab : this.getCollaborators()) {
            switch (collab.getLevel()) {
                case POOR:
                    totalQuality += 1;
                    break;
                case AVERAGE:
                    totalQuality += 2;
                    break;
                case GOOD:
                    totalQuality += 3;
                    break;
            }
        }

        double averageQuality = totalQuality / connectionCount;
        double riskScore = connectionCount * (4 - averageQuality);

        return riskScore;
    }
}
