import jakarta.persistence.TypedQuery;
import model.Department;
import model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class manyToOneInteractive {
    public static void main(String[] args) {
        manyToOneInteractive();
    }

    public static void manyToOneInteractive() {
        System.out.println("Welcome to ManyToOneInteractive!");
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
//        Transaction transaction = session.beginTransaction();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n0. Exit");
                System.out.println("1. Manage Departments");
                System.out.println("2. Manage Teachers");
                System.out.println("3. Assign Teacher to Department");
                System.out.println("4. List Teachers");
                System.out.println("5. List Department");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    case 1:
                        manageDepartments(scanner, factory);
                        break;
                    case 2:
                        manageTeachers(scanner, factory);
                        break;
                    case 3:
                        assignTeacherToDepartment(scanner, session);
                        break;
                    case 4:
                        listTeachers(session);
                        break;
                    case 5:
                        listDepts(session);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            session.close();
            factory.close();

        }



    }

    private static void manageDepartments(Scanner scanner, SessionFactory factory) {

        // YOUR CODE HERE
    while (true) {
        System.out.println("\n1. Add Departments");
        System.out.println("2. Delete Departments");
        System.out.println("3. Modify Department");
        System.out.println("4. Go back to menu");

        int deptChoice = scanner.nextInt();
        scanner.nextLine(); //new line

        switch (deptChoice) {
            case 1:
                addDepartment(scanner, factory);
                break;
            case 2:
                deleteDepartment(scanner, factory);
                break;
            case 3:
                modifyDepartment(scanner, factory);
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    }

    private static void addDepartment(Scanner scanner, SessionFactory factory) {
        System.out.print("Enter department name: ");
        String name = scanner.nextLine();

        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Department department = new Department(name);
            session.save(department);
            transaction.commit();
            System.out.println("Department added successfully.");
        }
    }

    private static void deleteDepartment(Scanner scanner, SessionFactory factory) {
        System.out.println("Enter department ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Department department = session.get(Department.class, id);
            if (department != null) {
                session.delete(department);
                transaction.commit();
                System.out.println("Department deleted successfully.");
            } else {
                System.out.println("Department not found.");
            }
        }
    }

    private static void modifyDepartment(Scanner scanner, SessionFactory factory) {
        System.out.println("Enter department ID to modify: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try (Session session = factory.openSession()){
            Transaction transaction = session.beginTransaction();
            Department department = session.get(Department.class, id);
            if (department != null) {
                System.out.println("Enter new department name: ");
                String newName = scanner.nextLine();
                department.setDeptName(newName);
                session.update(department);
                transaction.commit();
                System.out.println("Department modified successfully.");} else {
                System.out.println("Department not found.");

            }
        }
    }


    private static void manageTeachers(Scanner scanner, SessionFactory factory) {

        // YOUR CODE HERE
    while (true) {
        System.out.println("\n1. Add Teachers");
        System.out.println("2. Delete Teacher");
        System.out.println("3. Modify Teacher");
        System.out.println("4. Go back to menu");

        int teacherChoice = scanner.nextInt();
        scanner.nextLine();

        switch (teacherChoice) {
            case 1:
                addTeacher(scanner, factory);
                break;
            case 2:
                deleteTeacher(scanner, factory);
                break;
            case 3:
                modifyTeacher(scanner, factory);
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}
    private static void addTeacher(Scanner scanner, SessionFactory factory) {
        System.out.println("Enter teacher name: ");
        String name = scanner.nextLine();

        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Teacher teacher = new Teacher();
            teacher.setTeacherName(name);
            session.save(teacher);
            transaction.commit();
            System.out.println("Teacher added successfully.");
        }
    }

    private static void deleteTeacher(Scanner scanner, SessionFactory factory) {
        System.out.println("Enter teacher ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Teacher teacher = session.get(Teacher.class, id);
            if (teacher != null) {
                session.delete(teacher);
                transaction.commit();
                System.out.println("Teacher deleted successfully.");} else {
                System.out.println("Teacher not found.");
            }
        }
    }

    private static void modifyTeacher(Scanner scanner, SessionFactory factory) {
        System.out.println("Enter teacher ID to modify: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try (Session session = factory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Teacher teacher = session.get(Teacher.class, id);
            if (teacher != null) {
                System.out.println("Enter new teacher name: ");
                String newName = scanner.nextLine();
                teacher.setTeacherName(newName);
                session.update(teacher);
                transaction.commit();
                System.out.println("Teacher modified successfully.");} else {
                System.out.println("Teacher not found.");
            }
        }
    }

    private static void assignTeacherToDepartment(Scanner scanner, Session session) {

        // YOUR CODE
        System.out.println("Which Teacher would you like to modify? Enter ID: ");
        int teacherId = scanner.nextInt();
        System.out.println("Which department would you like to assign to Teacher? Enter ID: ");
        int deptId = scanner.nextInt();
        scanner.nextLine();

        Transaction transaction = session.beginTransaction();
        Teacher teacher = session.get(Teacher.class, teacherId);
        Department department = session.get(Department.class, deptId);

        if (teacher != null && department != null) {
            teacher.setDepartment(department);
            session.update(teacher);
            transaction.commit();
            System.out.println("Teacher assigned to department successfully.");} else {
            System.out.println("Teacher or Department not found.");
        }
    }

    private static void listDepts(Session session) {
        List<Department> departments = session.createQuery("FROM Department", Department.class).list();
        for (Department dept : departments) {
            System.out.println(dept.getDeptName() + " - Teachers: " + dept.getTeacherList().size());
        }
    }


    private static void listTeachers(Session session) {
        List<Teacher> teachers = session.createQuery("FROM Teacher", Teacher.class).list();
        for (Teacher teacher : teachers) {
            System.out.println(teacher.getTeacherName() + " - Department: " + (teacher.getDepartment() != null ? teacher.getDepartment().getDeptName() : "Not Assigned"));
        }

    }


}
