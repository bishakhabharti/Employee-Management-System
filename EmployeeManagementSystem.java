import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

// Employee class
class Employee implements Serializable {

    private String id;
    private String name;
    private String department;
    private String position;
    private double salary;
    private Date joinDate;

    public Employee(String id, String name, String department, String position, double salary) {

        this.id = id;
        this.name = name;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.joinDate = new Date();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }

    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setPosition(String position) { this.position = position; }
    public void setSalary(double salary) { this.salary = salary; }

    public String toString() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return id + " | " + name + " | " + department + " | "
                + position + " | ₹" + salary + " | " + sdf.format(joinDate);
    }
}


// Main system class
public class EmployeeManagementSystem {

    static ArrayList<Employee> employees = new ArrayList<>();
    static HashMap<String, Employee> employeeMap = new HashMap<>();

    static Scanner sc = new Scanner(System.in);

    static final String FILE_NAME = "employees.dat";

    public static void main(String[] args) {

        loadFromFile();

        int choice;

        do {

            System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM =====");

            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Save to File");
            System.out.println("7. Exit");

            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {

                case 1: addEmployee(); break;
                case 2: viewEmployees(); break;
                case 3: searchEmployee(); break;
                case 4: updateEmployee(); break;
                case 5: deleteEmployee(); break;
                case 6: saveToFile(); break;
                case 7: System.out.println("Exiting..."); break;

                default: System.out.println("Invalid choice!");
            }

        } while(choice != 7);
    }


    // Add employee
    static void addEmployee() {

        try {

            System.out.print("Enter ID: ");
            String id = sc.nextLine();

            if(employeeMap.containsKey(id)) {

                System.out.println("Employee already exists!");
                return;
            }

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Department: ");
            String dept = sc.nextLine();

            System.out.print("Enter Position: ");
            String pos = sc.nextLine();

            System.out.print("Enter Salary: ");
            double salary = sc.nextDouble();
            sc.nextLine();

            Employee emp = new Employee(id, name, dept, pos, salary);

            employees.add(emp);
            employeeMap.put(id, emp);

            System.out.println("Employee added successfully!");

        } catch(Exception e) {

            System.out.println("Error: Invalid input.");
            sc.nextLine();
        }
    }


    // View employees
    static void viewEmployees() {

        if(employees.isEmpty()) {

            System.out.println("No employees found.");
            return;
        }

        for(Employee emp : employees) {

            System.out.println(emp);
        }
    }


    // Search employee
    static void searchEmployee() {

        System.out.print("Enter ID: ");
        String id = sc.nextLine();

        Employee emp = employeeMap.get(id);

        if(emp != null)
            System.out.println(emp);
        else
            System.out.println("Employee not found.");
    }


    // Update employee
    static void updateEmployee() {

        System.out.print("Enter ID: ");
        String id = sc.nextLine();

        Employee emp = employeeMap.get(id);

        if(emp == null) {

            System.out.println("Employee not found.");
            return;
        }

        System.out.print("Enter new name: ");
        emp.setName(sc.nextLine());

        System.out.print("Enter new department: ");
        emp.setDepartment(sc.nextLine());

        System.out.print("Enter new position: ");
        emp.setPosition(sc.nextLine());

        System.out.print("Enter new salary: ");
        emp.setSalary(sc.nextDouble());
        sc.nextLine();

        System.out.println("Employee updated.");
    }


    // Delete employee
    static void deleteEmployee() {

        System.out.print("Enter ID: ");
        String id = sc.nextLine();

        Employee emp = employeeMap.remove(id);

        if(emp != null) {

            employees.remove(emp);
            System.out.println("Employee deleted.");

        } else {

            System.out.println("Employee not found.");
        }
    }


    // Save file
    static void saveToFile() {

        try {

            ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            oos.writeObject(employees);

            oos.close();

            System.out.println("Data saved.");

        } catch(Exception e) {

            System.out.println("Error saving file.");
        }
    }


    // Load file
    static void loadFromFile() {

        try {

            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(FILE_NAME));

            employees = (ArrayList<Employee>) ois.readObject();

            for(Employee emp : employees)
                employeeMap.put(emp.getId(), emp);

            ois.close();

        } catch(Exception e) {

            System.out.println("No previous data found.");
        }
    }

}
