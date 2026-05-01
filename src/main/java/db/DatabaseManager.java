package db;

import model.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;
    private boolean isAvailable;
    private static final String DB_URL = "jdbc:sqlite:employee_database.db";

    public DatabaseManager() {
        this.isAvailable = false;
        this.connection = null;
        connect();
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            
            createTablesIfNotExist();
            
            isAvailable = true;
            System.out.println("DatabaseManager: Connected to SQLite database successfully");
        } catch (SQLException e) {
            isAvailable = false;
            System.out.println("DatabaseManager WARNING: Failed to connect to SQL database");
            System.out.println("  Reason: " + e.getMessage());
            System.out.println("  App will continue with in-memory database only");
        }
    }

    private void createTablesIfNotExist() throws SQLException {
        String createEmployeesTable = "CREATE TABLE IF NOT EXISTS employees (" +
            "id INTEGER PRIMARY KEY, " +
            "name TEXT NOT NULL, " +
            "surname TEXT NOT NULL, " +
            "birthYear INTEGER NOT NULL, " +
            "type TEXT NOT NULL" +
            ")";

        String createCollaborationsTable = "CREATE TABLE IF NOT EXISTS collaborations (" +
            "employeeId INTEGER NOT NULL, " +
            "collaboratorId INTEGER NOT NULL, " +
            "cooperationLevel TEXT NOT NULL, " +
            "PRIMARY KEY (employeeId, collaboratorId)" +
            ")";

        Statement statement = connection.createStatement();
        
        try {
            statement.execute(createEmployeesTable);
            statement.execute(createCollaborationsTable);
        } finally {
            statement.close();
        }
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void saveAll(EmployeeDatabase memDb) {
        if (!isAvailable) {
            System.out.println("DatabaseManager: SQL not available, skipping save");
            return;
        }

        try {
            clearTables();
            
            saveEmployees(memDb);
            saveCollaborations(memDb);
            
            System.out.println("DatabaseManager: All data saved to SQL database successfully");
        } catch (SQLException e) {
            System.out.println("DatabaseManager ERROR: Failed to save data to SQL database");
            System.out.println("  Reason: " + e.getMessage());
        }
    }

    private void clearTables() throws SQLException {
        String deleteCollaborations = "DELETE FROM collaborations";
        String deleteEmployees = "DELETE FROM employees";
        
        Statement statement = connection.createStatement();
        
        try {
            statement.execute(deleteCollaborations);
            statement.execute(deleteEmployees);
        } finally {
            statement.close();
        }
    }

    private void saveEmployees(EmployeeDatabase memDb) throws SQLException {
        String insertSQL = "INSERT INTO employees (id, name, surname, birthYear, type) VALUES (?, ?, ?, ?, ?)";
        
        PreparedStatement pstmt = connection.prepareStatement(insertSQL);
        
        try {
            for (Employee emp : memDb.getAll()) {
                pstmt.setInt(1, emp.getId());
                pstmt.setString(2, emp.getName());
                pstmt.setString(3, emp.getSurname());
                pstmt.setInt(4, emp.getBirthYear());
                
                String type = emp instanceof DataAnalyst ? "DataAnalyst" : "SecuritySpecialist";
                pstmt.setString(5, type);
                
                pstmt.executeUpdate();
            }
        } finally {
            pstmt.close();
        }
    }

    private void saveCollaborations(EmployeeDatabase memDb) throws SQLException {
        String insertSQL = "INSERT INTO collaborations (employeeId, collaboratorId, cooperationLevel) VALUES (?, ?, ?)";
        
        PreparedStatement pstmt = connection.prepareStatement(insertSQL);
        
        try {
            for (Employee emp : memDb.getAll()) {
                for (Collaborator collab : emp.getCollaborators()) {
                    pstmt.setInt(1, emp.getId());
                    pstmt.setInt(2, collab.getEmployee().getId());
                    pstmt.setString(3, collab.getLevel().toString());
                    
                    pstmt.executeUpdate();
                }
            }
        } finally {
            pstmt.close();
        }
    }

    public void loadAll(EmployeeDatabase memDb) {
        if (!isAvailable) {
            System.out.println("DatabaseManager: SQL not available, cannot load");
            return;
        }

        try {
            String selectEmployeesSQL = "SELECT id, name, surname, birthYear, type FROM employees ORDER BY id";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectEmployeesSQL);
            
            int loadedEmployees = 0;
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String surname = rs.getString(3);
                int birthYear = rs.getInt(4);
                String type = rs.getString(5);
                
                memDb.addEmployee(name, surname, birthYear, type);
                loadedEmployees++;
            }
            rs.close();
            stmt.close();
            
            String selectCollaborationsSQL = "SELECT employeeId, collaboratorId, cooperationLevel FROM collaborations";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectCollaborationsSQL);
            
            int loadedCollaborations = 0;
            while (rs.next()) {
                int employeeId = rs.getInt(1);
                int collaboratorId = rs.getInt(2);
                String cooperationLevel = rs.getString(3);
                
                CoopLevel level = CoopLevel.valueOf(cooperationLevel);
                memDb.addCollaboration(employeeId, collaboratorId, level);
                loadedCollaborations++;
            }
            rs.close();
            stmt.close();
            
            System.out.println("DatabaseManager: All data loaded from SQL database successfully");
            System.out.println("  - Loaded " + loadedEmployees + " employees");
            System.out.println("  - Loaded " + loadedCollaborations + " collaborations");
        } catch (SQLException e) {
            System.out.println("DatabaseManager ERROR: Failed to load data from SQL database");
            System.out.println("  Reason: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("DatabaseManager: Disconnected from SQLite database");
            } catch (SQLException e) {
                System.out.println("DatabaseManager WARNING: Failed to disconnect properly");
                System.out.println("  Reason: " + e.getMessage());
            }
        }
    }

    public void verifySavedData() {
        if (!isAvailable) {
            System.out.println("DatabaseManager: SQL not available, cannot verify");
            return;
        }

        try {
            int employeeCount = 0;
            int collaborationCount = 0;

            String countEmployeesSQL = "SELECT COUNT(*) FROM employees";
            Statement stmt = connection.createStatement();
            var rs = stmt.executeQuery(countEmployeesSQL);
            if (rs.next()) {
                employeeCount = rs.getInt(1);
            }
            rs.close();
            stmt.close();

            String countCollaborationsSQL = "SELECT COUNT(*) FROM collaborations";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(countCollaborationsSQL);
            if (rs.next()) {
                collaborationCount = rs.getInt(1);
            }
            rs.close();
            stmt.close();

            System.out.println("Verification: Data saved to SQL database");
            System.out.println("  - Employees in database: " + employeeCount);
            System.out.println("  - Collaborations in database: " + collaborationCount);
        } catch (SQLException e) {
            System.out.println("DatabaseManager WARNING: Could not verify saved data");
            System.out.println("  Reason: " + e.getMessage());
        }
    }
}
