package db;

import model.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
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
}
