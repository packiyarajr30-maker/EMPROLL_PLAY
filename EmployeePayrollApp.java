package EmployeePayrollApp;
import java.sql.*;
import java.util.*;

public class EmployeePayrollApp {

    // Database details
    static final String URL = "jdbc:mysql://localhost:3306/payroll";
    static final String USER = "root";
    static final String PASSWORD = "";

    static Scanner sc = new Scanner(System.in);

    // Connect to MySQL
    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // -------------------- ADD EMPLOYEE --------------------
    public static void addEmployee() {
        try {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Role: ");
            String role = sc.nextLine();

            System.out.print("Enter Basic Salary: ");
            double basic = sc.nextDouble();

            System.out.print("Enter Allowance: ");
            double allowance = sc.nextDouble();

            System.out.print("Enter Deduction: ");
            double deduction = sc.nextDouble();
            sc.nextLine();

            String sql = "INSERT INTO employees (name, role, basic_salary, allowance, deduction) VALUES (?,?,?,?,?)";

            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, role);
            ps.setDouble(3, basic);
            ps.setDouble(4, allowance);
            ps.setDouble(5, deduction);

            ps.executeUpdate();
            System.out.println("Employee Added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------- VIEW EMPLOYEES --------------------
    public static void viewEmployees() {
        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM employees");

            while (rs.next()) {
                System.out.println("\nID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Role: " + rs.getString("role"));
                System.out.println("Basic Salary: " + rs.getDouble("basic_salary"));
                System.out.println("Allowance: " + rs.getDouble("allowance"));
                System.out.println("Deduction: " + rs.getDouble("deduction"));
                System.out.println("---------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------- UPDATE EMPLOYEE --------------------
    public static void updateEmployee() {
        try {
            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter New Name: ");
            String name = sc.nextLine();

            System.out.print("Enter New Role: ");
            String role = sc.nextLine();

            System.out.print("Enter New Basic Salary: ");
            double basic = sc.nextDouble();

            System.out.print("Enter New Allowance: ");
            double allowance = sc.nextDouble();

            System.out.print("Enter New Deduction: ");
            double deduction = sc.nextDouble();
            sc.nextLine();

            String sql = "UPDATE employees SET name=?, role=?, basic_salary=?, allowance=?, deduction=? WHERE id=?";

            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, role);
            ps.setDouble(3, basic);
            ps.setDouble(4, allowance);
            ps.setDouble(5, deduction);
            ps.setInt(6, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Employee Updated!");
            else
                System.out.println("Employee Not Found!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------- DELETE EMPLOYEE --------------------
    public static void deleteEmployee() {
        try {
            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "DELETE FROM employees WHERE id=?";

            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Employee Deleted!");
            else
                System.out.println("Employee Not Found!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------- CALCULATE SALARY --------------------
    public static void calculateSalary() {
        try {
            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "SELECT basic_salary, allowance, deduction FROM employees WHERE id=?";

            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double basic = rs.getDouble("basic_salary");
                double allowance = rs.getDouble("allowance");
                double deduction = rs.getDouble("deduction");

                double net = basic + allowance - deduction;
                System.out.println("Net Salary: " + net);
            } else {
                System.out.println("Employee Not Found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------- MAIN MENU --------------------
    public static void main(String[] args) {

        while (true) {
            System.out.println("\n==== Employee Payroll System ====");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Calculate Salary");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> addEmployee();
                case 2 -> viewEmployees();
                case 3 -> updateEmployee();
                case 4 -> deleteEmployee();
                case 5 -> calculateSalary();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid Choice!");
            }
        }
    }
}
