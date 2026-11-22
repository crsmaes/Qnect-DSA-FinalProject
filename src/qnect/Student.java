
package qnect;

public class Student {

    private String studentId;
    private String name;
    private String transactionType;

    // CONSTRUCTOR
    public Student(String studentId, String name, String transactionType) {
        this.studentId = studentId;
        this.name = name;
        this.transactionType = transactionType;
    }

    // GET STUDENT ID
    public String getStudentId() {
        return studentId;
    }

    // UPDATE STUDENT ID
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    // GET NAME
    public String getName() {
        return name;
    }

    // UPDATE NAME
    public void setName(String name) {
        this.name = name;
    }

    // GET TRANSACTION TYPE
    public String getTransactionType() {
        return transactionType;
    }

    // UPDATE TRANSACTION TYPE
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    // DISPLAY STUDENT INFORMATION
    @Override
    public String toString() {
        return "\nStudent ID: " + studentId
                + "\nStudent Name: " + name
                + "\nTransaction Type: " + transactionType;
    }
}
