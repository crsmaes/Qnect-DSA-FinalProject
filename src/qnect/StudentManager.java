
package qnect;

import java.util.ArrayList;

public class StudentManager {

    private ArrayList<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
    }

    // CREATE
    public boolean addStudent(Student student) {

        if (findById(student.getStudentId()) != null) {
            return false;
        }

        students.add(student);
        return true;
    }

    // READ / SEARCH
    public Student findById(String studentId) {

        for (Student student : students) {

            if (student.getStudentId().equalsIgnoreCase(studentId)) {
                return student;
            }
        }

        return null;
    }

    // DISPLAY ALL STUDENTS
    public void displayAllStudents() {

        if (students.isEmpty()) {
            System.out.println("\nNo student records found.");
            return;
        }

        System.out.println("\n========== STUDENT RECORDS ==========");

        for (Student student : students) {
            System.out.println("\n" + student);
            System.out.println("-------------------------------------");
        }
    }

    // UPDATE
    public boolean updateStudent(String studentId, String newName,
                                 String newTransactionType) {

        Student student = findById(studentId);

        if (student == null) {
            return false;
        }

        student.setName(newName);
        student.setTransactionType(newTransactionType);

        return true;
    }

    // DELETE
    public boolean deleteStudent(String studentId) {

        Student student = findById(studentId);

        if (student == null) {
            return false;
        }

        students.remove(student);
        return true;
    }
}
