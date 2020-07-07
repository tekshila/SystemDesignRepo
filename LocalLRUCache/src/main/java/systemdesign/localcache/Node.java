package systemdesign.localcache;

public class Node {
    Node next;
    Node prev;
    int value;
    int key;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }

    public void print() {
        System.out.println("{ value : " + value);
        if(null != next) System.out.println("  next : " + next.value);
        if(null != prev) System.out.println(" prev : " + prev.value + " } ");
    }
}
