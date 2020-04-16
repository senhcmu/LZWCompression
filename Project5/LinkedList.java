package Project5;

public class LinkedList {
    private ListNode head;
    private ListNode tail;
    private int countNodes;
    private ListNode iterator;


    public LinkedList() {
        head = null;
        tail = null;
        countNodes = 0;
    }

    public void addAtEndNode(String key, int value) {
        if (key == null) {
            return;
        }
        if (head == null) {
            head = new ListNode(key, value, null);
            tail = head;
            countNodes++;
            return;
        }
        ListNode newTail = new ListNode(key, value, null);
        tail.setLink(newTail);
        tail = newTail;
        countNodes++;
    }


    public void addAtFrontNode(String key, int value) {
        if (key == null) {
            return;
        }
        if (head == null) {
            ListNode head = new ListNode(key, value, null);
            tail = head;
            countNodes++;
            return;
        }
        ListNode newHead = new ListNode(key, value, null);
        newHead.setLink(head);
        head = newHead;
        countNodes++;
    }


    public int countNodes() {
        return countNodes;
    }


    public ListNode getLast() {
        return tail;
    }


    public int getNodeValue(String key) throws Exception {
        ListNode node = head;
        while (node != null) {
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
            node = node.getLink();
        }
        return -1;
    }


    public boolean hasNext() {
        if (iterator == null) {
            return false;
        }
        return true;
    }


    public int next() throws Exception {
        if (iterator == null) {
            throw new Exception("No next node!");
        }
        int res = iterator.getValue();
        iterator = iterator.getLink();
        return res;
    }


    public void reset() {
        iterator = head;
    }


    @Override
    public String toString() {
        ListNode newHead = head;
        if (head == null) {
            return null;
        }
        return newHead.toString();
    }

}
