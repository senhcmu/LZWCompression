package Project5;

import java.sql.ClientInfoStatus;

public class ListNode {

    private String key;

    private int value;
    private ListNode link;

    public ListNode(String key, int value, ListNode initialLink) {
        this.key = key;
        this.link = initialLink;
        this.value = value;
    }

    public void addNodeAfter(String key, int value) {
        this.link = new ListNode(key, value, link);
    }

    public String getKey() {
        return this.key;
    }

    public int getValue() {
        return this.value;
    }


    public ListNode getLink() {
        return this.link;
    }

    public static int listLength(ListNode head) {
        ListNode cursor;
        int answer;

        answer = 0;
        for (cursor = head; cursor != null; cursor = cursor.link)
            answer++;

        return answer;
    }

    public static ListNode listPosition(ListNode head, int position) {
        ListNode cursor;
        int i;
        if (position <= 0)
            throw new IllegalArgumentException("position is not positive");
        cursor = head;
        for (i = 0; (i < position) && (cursor != null); i++)
            cursor = cursor.link;

        return cursor;
    }


    public static ListNode listSearch(ListNode head, String target) {
        ListNode cursor;

        for (cursor = head; cursor != null; cursor = cursor.link)
            if (target.equals(cursor.key))
                return cursor;

        return null;
    }

    public void removeNodeAfter() {
        link = link.link;
    }


    public void setLink(ListNode newLink) {
        this.link = newLink;
    }

    @Override
    public String toString() {
        String nodes = new String();
        ListNode newHead = this;
        if (this == null) {
            return null;
        }
        while (newHead != null) {
            nodes += String.valueOf(newHead.getKey());
            newHead = newHead.link;
        }
        return nodes;

    }


}
