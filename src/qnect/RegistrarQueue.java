
package qnect;

public class RegistrarQueue {

    private QueueNode front;
    private QueueNode rear;
    private int nextQueueNumber;

    public RegistrarQueue() {
        front = null;
        rear = null;
        nextQueueNumber = 1;
    }

    // ENQUEUE: Add a new queue entry at the rear
    public int enqueue(Student student) {

        QueueEntry entry = new QueueEntry(nextQueueNumber, student);
        QueueNode newNode = new QueueNode(entry);

        if (front == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }

        return nextQueueNumber++;
    }

    // Get the most recently assigned queue number
    public int getLastQueueNumber() {
        return nextQueueNumber - 1;
    }

    // Add another student to an existing queue number
    public boolean addStudentToQueue(int queueNumber, Student student) {

        QueueEntry entry = findByQueueNumber(queueNumber);

        if (entry == null) {
            return false;
        }

        return entry.addStudent(student);
    }

    // DEQUEUE: Remove the front queue entry
    public QueueEntry dequeue() {

        if (front == null) {
            return null;
        }

        QueueEntry removedEntry = front.data;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        return removedEntry;
    }

    // PEEK: View the front queue entry
    public QueueEntry peek() {

        if (front == null) {
            return null;
        }

        return front.data;
    }

    // Search by queue number
    public QueueEntry findByQueueNumber(int queueNumber) {

        QueueNode current = front;

        while (current != null) {
            if (current.data.getQueueNumber() == queueNumber) {
                return current.data;
            }

            current = current.next;
        }

        return null;
    }

    // LINEAR SEARCH: Search by student ID
    public QueueEntry searchByStudentId(String studentId) {

        QueueNode current = front;

        while (current != null) {
            if (current.data.containsStudentId(studentId)) {
                return current.data;
            }

            current = current.next;
        }

        return null;
    }

    // Check if a student is currently in the queue
    public boolean containsStudentId(String studentId) {
        return searchByStudentId(studentId) != null;
    }

    // CANCEL: Remove a queue entry using its queue number
    public boolean cancelQueue(int queueNumber) {

        if (front == null) {
            return false;
        }

        if (front.data.getQueueNumber() == queueNumber) {
            dequeue();
            return true;
        }

        QueueNode previous = front;
        QueueNode current = front.next;

        while (current != null) {

            if (current.data.getQueueNumber() == queueNumber) {

                previous.next = current.next;

                if (current == rear) {
                    rear = previous;
                }

                return true;
            }

            previous = current;
            current = current.next;
        }

        return false;
    }

    // Update queue status
    public boolean updateStatus(int queueNumber, String newStatus) {

        QueueEntry entry = findByQueueNumber(queueNumber);

        if (entry == null) {
            return false;
        }

        entry.setStatus(newStatus);
        return true;
    }

    // Display all queue entries
    public void displayQueue() {

        if (front == null) {
            System.out.println("\nThe queue is currently empty.");
            return;
        }

        QueueNode current = front;

        System.out.println("\n========== QNECT WAITING QUEUE ==========");

        while (current != null) {
            current.data.displayEntry();
            System.out.println("-----------------------------------------");
            current = current.next;
        }
    }

    // Count entries with Waiting status
    public int getWaitingCount() {

        int count = 0;
        QueueNode current = front;

        while (current != null) {

            if (current.data.getStatus().equalsIgnoreCase("Waiting")) {
                count++;
            }

            current = current.next;
        }

        return count;
    }

    public boolean isEmpty() {
        return front == null;
    }
}
