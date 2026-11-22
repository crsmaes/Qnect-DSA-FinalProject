
package qnect;

public class QueueNode {

    QueueEntry data;
    QueueNode next;

    public QueueNode(QueueEntry data) {
        this.data = data;
        this.next = null;
    }
}
