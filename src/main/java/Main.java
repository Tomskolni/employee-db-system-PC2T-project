import db.DatabaseManager;
import db.EmployeeDatabase;
import ui.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        EmployeeDatabase db = new EmployeeDatabase();
        DatabaseManager dbManager = new DatabaseManager();

        ConsoleMenu menu = new ConsoleMenu(db, dbManager);
        menu.start();

        dbManager.disconnect();
    }
}
