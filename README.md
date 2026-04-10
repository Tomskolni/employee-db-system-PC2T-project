# Employee Database System (PC2T Project)

A Java-based Employee Database System developed as a university project. This application demonstrates strict Object-Oriented Programming (OOP) principles, dynamic data structures, and a dual-layer persistence strategy.

## 📌 Project Overview
This system manages employees within a tech company. Each employee maintains a list of collaborators along with a specific cooperation level (Poor, Average, Good). The system categorizes employees into specialized roles, each possessing unique polymorphic behaviors to analyze collaboration data.

## 🚀 Core Features

* **Employee Management:** Add, remove, and search for employees by their automatically generated ID.
* **Collaboration Tracking:** Register collaborations between employees with specific quality levels.
* **Specialized Roles (Polymorphism):**
    * **Data Analysts:** Can identify which collaborator shares the most mutual connections.
    * **Security Specialists:** Calculate a custom "Risk Score" based on the volume and average quality of a person's collaborations.
* **Statistics & Sorting:** Display alphabetical lists of employees by surname within their groups, find the employee with the most connections, and determine the overall prevailing cooperation quality.
* **Dual-Layer Persistence:**
    * *Local Storage:* Save and load individual employee records to/from local files.
    * *SQL Backup:* Automatic persistence of the entire database to an SQL database on application exit, and automatic loading on startup. **The system is designed to function entirely in-memory if the SQL database is unavailable.**

## 🛠️ Tech Stack & Architecture

* **Language:** Java (LTS)
* **Paradigm:** Strict Object-Oriented Programming (OOP)
* **Database:** SQL (via JDBC)
* **Key Architectural Concepts:**
    * **Abstract Classes & Interfaces:** Used to define the base `Employee` template and enforce role-specific skill implementations.
    * **Dynamic Data Structures:** Utilizes Java Collections (e.g., `HashMap`) for $O(1)$ runtime lookups and efficient in-memory state management.
    * **Enums:** Type-safe representation of cooperation levels.
<img width="1440" height="1524" alt="image" src="https://github.com/user-attachments/assets/3592cff0-0fc9-4606-9843-0fe8dde5e4e5" />

Project structure
src/
├── model/
│   ├── CoopLevel.java          // enum: POOR, AVERAGE, GOOD
│   ├── Collaborator.java       // holds Employee ref + CoopLevel
│   ├── Employee.java           // abstract base class
│   ├── DataAnalyst.java        // extends Employee
│   └── SecuritySpecialist.java // extends Employee
├── db/
│   ├── EmployeeDatabase.java   // runtime store, all CRUD logic
│   └── DatabaseManager.java    // SQL JDBC backup (isolated)
├── io/
│   └── FileManager.java        // save/load individual employees
├── ui/
│   └── ConsoleMenu.java        // all Scanner/System.out logic
└── Main.java
Keeping DatabaseManager completely isolated is key — if the SQL connection fails, the rest of the program never knows.

## ⚙️ Getting Started

### Prerequisites
* Java Development Kit (JDK) 21 or higher.
* An SQL Database (e.g., MySQL, SQLite, or PostgreSQL) running locally for the backup feature.
* JDBC Driver for your chosen SQL database.

### Setup Instructions
1.  Clone the repository:
    ```bash
    git clone [https://github.com/Tomskolni/employee-db-system-PC2T-project.git](https://github.com/Tomskolni/employee-db-system-PC2T-project.git)
    ```
2.  Open the project in your preferred IDE (e.g., VS Code, IntelliJ IDEA).
3.  Configure your SQL database connection details in the `DatabaseConnection` class (or `.properties` file).
4.  Compile and run the main application.

## 👥 Authors

* **Tomáš Kovařík** ([Tomskolni](https://github.com/Tomskolni))
* **Tomáš Nosek** ([Tomáš Nosek](https://github.com/nosek-tomas))

---
*Developed for the PC2T coursework.*
