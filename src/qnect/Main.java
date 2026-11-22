
package qnect;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static RegistrarQueue registrarQueue = new RegistrarQueue();
    private static StudentManager studentManager = new StudentManager();

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n======================================");
            System.out.println("       QNECT REGISTRAR QUEUE SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Register New Queue");
            System.out.println("2. Add Student to Existing Queue");
            System.out.println("3. Call Next Queue");
            System.out.println("4. Complete Current Transaction");
            System.out.println("5. Search Student or Queue");
            System.out.println("6. Cancel Queue");
            System.out.println("7. Display Waiting Queue");
            System.out.println("8. Manage Student Records");
            System.out.println("0. Exit");
            System.out.println("======================================");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerNewQueue();
                    break;
                case 2:
                    addStudentToExistingQueue();
                    break;
                case 3:
                    callNextQueue();
                    break;
                case 4:
                    completeCurrentTransaction();
                    break;
                case 5:
                    searchMenu();
                    break;
                case 6:
                    cancelQueue();
                    break;
                case 7:
                    registrarQueue.displayQueue();
                    break;
                case 8:
                    studentRecordsMenu();
                    break;
                case 0:
                    System.out.println("Thank you for using QNECT!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 0-8.");
            }

        } while (choice != 0);

        scanner.close();
    }

    // REGISTER NEW QUEUE
    private static void registerNewQueue() {

        System.out.println("\n========== REGISTER NEW QUEUE ==========");

        String studentId = readText("Enter Student ID: ");

        if (studentManager.findById(studentId) != null) {
            System.out.println("Student ID already exists.");
            return;
        }

        String name = readText("Enter Student Name: ");
        String transaction = chooseTransactionType();

        Student student = new Student(studentId, name, transaction);

        registrarQueue.enqueue(student);
        studentManager.addStudent(student);

        System.out.println("\nRegistration successful!");
        System.out.println("Your queue number is: Q" +
                registrarQueue.getLastQueueNumber());
        System.out.println("Status: Waiting");
    }

    // ADD STUDENT TO EXISTING QUEUE
    private static void addStudentToExistingQueue() {

        System.out.println("\n========== ADD STUDENT TO QUEUE ==========");

        int queueNumber = readInt("Enter Queue Number: ");

        QueueEntry entry = registrarQueue.findByQueueNumber(queueNumber);

        if (entry == null) {
            System.out.println("Queue not found.");
            return;
        }

        if (!entry.getStatus().equals("Waiting")) {
            System.out.println("Students can only join a waiting queue.");
            return;
        }

        if (entry.getStudentCount() >= 3) {
            System.out.println("This queue already has the maximum of 3 students.");
            return;
        }

        String studentId = readText("Enter Student ID: ");

        if (studentManager.findById(studentId) != null) {
            System.out.println("Student ID already exists.");
            return;
        }

        String name = readText("Enter Student Name: ");
        String transaction = chooseTransactionType();

        if (!entry.getStudents()[0].getTransactionType().equals(transaction)) {
            System.out.println("Transaction type does not match this queue.");
            return;
        }

        Student student = new Student(studentId, name, transaction);

        if (registrarQueue.addStudentToQueue(queueNumber, student)) {
            studentManager.addStudent(student);
            System.out.println("Student successfully added to Q" + queueNumber);
        } else {
            System.out.println("Unable to add student.");
        }
    }

    // CALL NEXT QUEUE
    private static void callNextQueue() {

        QueueEntry entry = registrarQueue.peek();

        if (entry == null) {
            System.out.println("There are no waiting queues.");
            return;
        }

        if (entry.getStatus().equals("Processing")) {
            System.out.println("The current queue is already being processed.");
            return;
        }

        registrarQueue.updateStatus(entry.getQueueNumber(), "Processing");

        System.out.println("\nNow serving: Q" + entry.getQueueNumber());
        System.out.println("Student/s:");
        entry.displayEntry();
        System.out.println("Status: Processing");
    }

    // COMPLETE CURRENT TRANSACTION
    private static void completeCurrentTransaction() {

        QueueEntry entry = registrarQueue.peek();

        if (entry == null) {
            System.out.println("There are no active queues.");
            return;
        }

        if (!entry.getStatus().equals("Processing")) {
            System.out.println("Call the next queue first.");
            return;
        }

        System.out.println("\nQueue Q" + entry.getQueueNumber() + " completed!");

        registrarQueue.updateStatus(entry.getQueueNumber(), "Completed");
        registrarQueue.dequeue();

        System.out.println("The next queue may now be called.");
    }

    // SEARCH MENU
    private static void searchMenu() {

        System.out.println("\n========== SEARCH MENU ==========");
        System.out.println("1. Search by Student ID");
        System.out.println("2. Search by Queue Number");
        System.out.println("0. Back");

        int choice = readInt("Choose an option: ");

        switch (choice) {
            case 1:
                String id = readText("Enter Student ID: ");
                Student student = studentManager.findById(id);

                if (student != null) {
                    System.out.println(student);
                    QueueEntry studentQueue =
                            registrarQueue.searchByStudentId(id);

                    if (studentQueue != null) {
                        System.out.println("Queue Number: Q" +
                                studentQueue.getQueueNumber());
                        System.out.println("Queue Status: " +
                                studentQueue.getStatus());
                    } else {
                        System.out.println("Student is not in an active queue.");
                    }
                } else {
                    System.out.println("Student not found.");
                }
                break;

            case 2:
                int queueNumber = readInt("Enter Queue Number: ");
                QueueEntry entry =
                        registrarQueue.findByQueueNumber(queueNumber);

                if (entry != null) {
                	entry.displayEntry();
                } else {
                    System.out.println("Queue not found.");
                }
                break;

            case 0:
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    // CANCEL QUEUE
    private static void cancelQueue() {

        System.out.println("\n========== CANCEL QUEUE ==========");

        int queueNumber = readInt("Enter Queue Number to cancel: ");

        if (registrarQueue.cancelQueue(queueNumber)) {
            System.out.println("Queue Q" + queueNumber + " cancelled.");
        } else {
            System.out.println("Queue not found or cannot be cancelled.");
        }
    }

    // STUDENT RECORDS MENU
    private static void studentRecordsMenu() {

        int choice;

        do {
            System.out.println("\n========== STUDENT RECORDS ==========");
            System.out.println("1. Add Student Record");
            System.out.println("2. View All Student Records");
            System.out.println("3. Search Student Record");
            System.out.println("4. Update Student Record");
            System.out.println("5. Delete Student Record");
            System.out.println("0. Back");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudentRecord();
                    break;
                case 2:
                    studentManager.displayAllStudents();
                    break;
                case 3:
                    searchStudentRecord();
                    break;
                case 4:
                    updateStudentRecord();
                    break;
                case 5:
                    deleteStudentRecord();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    // ADD STUDENT RECORD
    private static void addStudentRecord() {

        System.out.println("\n========== ADD STUDENT RECORD ==========");

        String id = readText("Enter Student ID: ");

        if (studentManager.findById(id) != null) {
            System.out.println("Student ID already exists.");
            return;
        }

        String name = readText("Enter Student Name: ");
        String transaction = chooseTransactionType();

        Student student = new Student(id, name, transaction);
        studentManager.addStudent(student);

        System.out.println("Student record added successfully!");
    }

    // SEARCH STUDENT RECORD
    private static void searchStudentRecord() {

        String id = readText("Enter Student ID: ");
        Student student = studentManager.findById(id);

        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student record not found.");
        }
    }

    // UPDATE STUDENT RECORD
    private static void updateStudentRecord() {

        System.out.println("\n========== UPDATE STUDENT RECORD ==========");

        String id = readText("Enter Student ID: ");
        Student student = studentManager.findById(id);

        if (student == null) {
            System.out.println("Student record not found.");
            return;
        }

        if (registrarQueue.containsStudentId(id)) {
            System.out.println("Cannot update a student while they are in an active queue.");
            return;
        }

        String newName = readText("Enter New Name: ");
        System.out.println("Select new transaction type:");

        String newTransaction = chooseTransactionType();

        student.setName(newName);
        student.setTransactionType(newTransaction);

        System.out.println("Student record updated successfully!");
    }

    // DELETE STUDENT RECORD
    private static void deleteStudentRecord() {

        System.out.println("\n========== DELETE STUDENT RECORD ==========");

        String id = readText("Enter Student ID: ");

        if (registrarQueue.containsStudentId(id)) {
            System.out.println("Cannot delete a student who is in an active queue.");
            return;
        }

        if (studentManager.deleteStudent(id)) {
            System.out.println("Student record deleted successfully!");
        } else {
            System.out.println("Student record not found.");
        }
    }

    // TRANSACTION TYPE SELECTION
    private static String chooseTransactionType() {

        System.out.println("\n========== TRANSACTION TYPE ==========");
        System.out.println("1. Enrollment");
        System.out.println("2. Transcript of Records");
        System.out.println("3. Certificate of Registration");
        System.out.println("4. Other Document Requests");

        while (true) {

            int choice = readInt("Select transaction type (1-4): ");

            switch (choice) {
                case 1:
                    return "Enrollment";
                case 2:
                    return "Transcript of Records";
                case 3:
                    return "Certificate of Registration";
                case 4:
                    return "Other Document Requests";
                default:
                    System.out.println("Invalid choice. Please select 1-4.");
            }
        }
    }

    // INPUT HELPERS
    private static String readText(String prompt) {

        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {

        while (true) {

            System.out.print(prompt);

            try {
                int number = Integer.parseInt(scanner.nextLine().trim());
                return number;

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
