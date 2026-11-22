
package qnect;

public class QueueEntry {

    private int queueNumber;
    private Student[] students;
    private int studentCount;
    private String status;

    // CONSTRUCTOR
    public QueueEntry(int queueNumber, Student firstStudent) {

        this.queueNumber = queueNumber;
        this.students = new Student[3];

        this.students[0] = firstStudent;
        this.studentCount = 1;
        this.status = "Waiting";
    }

    // GET QUEUE NUMBER
    public int getQueueNumber() {
        return queueNumber;
    }

    // GET STUDENT COUNT
    public int getStudentCount() {
        return studentCount;
    }

    // GET STATUS
    public String getStatus() {
        return status;
    }

    // UPDATE STATUS
    public void setStatus(String status) {
        this.status = status;
    }

    // GET STUDENT BY INDEX
    public Student getStudent(int index) {

        if (index < 0 || index >= studentCount) {
            return null;
        }

        return students[index];
    }

    // GET ALL STUDENTS
    public Student[] getStudents() {
        return students;
    }

    // ADD STUDENT TO QUEUE
    public boolean addStudent(Student student) {

        if (student == null || studentCount >= 3) {
            return false;
        }

        if (!students[0].getTransactionType()
                .equalsIgnoreCase(student.getTransactionType())) {
            return false;
        }

        students[studentCount] = student;
        studentCount++;

        return true;
    }

    // CHECK IF STUDENT ID EXISTS IN THIS QUEUE
    public boolean containsStudentId(String studentId) {

        for (int i = 0; i < studentCount; i++) {

            if (students[i].getStudentId().equalsIgnoreCase(studentId)) {
                return true;
            }
        }

        return false;
    }

    // DISPLAY QUEUE ENTRY
    public void displayEntry() {

        System.out.println("\nQueue Number: Q" + queueNumber);
        System.out.println("Transaction: " +
                students[0].getTransactionType());
        System.out.println("Status: " + status);
        System.out.println("Students:");

        for (int i = 0; i < studentCount; i++) {

            System.out.println((i + 1) + ". "
                    + students[i].getName()
                    + " (" + students[i].getStudentId() + ")");
        }
    }

}
