package systemdesign.localcache;

import java.util.HashMap;

/*
 * LRU Implementation using Hashmap and doubly linked linked
 * Double linked list helps in removal in O(1) as compared to
 * normal linkedlist that does this in O(n) time
 */
public class LRUCache {
    Node head;
    Node tail;
    HashMap<Integer, Node> map = null;
    int cap = 0;

    public LRUCache(int capacity) {
        this.cap = capacity;
        this.map = new HashMap<>();
    }

    public int get(int key) {
        if(map.get(key)==null){
            return -1;
        }

        //move to tail
        Node t = map.get(key);

        removeNode(t);
        offerNode(t);

        return t.value;
    }

    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node t = map.get(key);
            t.value = value;

            //move to tail
            removeNode(t);
            offerNode(t);
        }else{
            if(map.size()>=cap){
                //delete head
                map.remove(head.key);
                removeNode(head);
            }

            //add to tail
            Node node = new Node(key, value);
            offerNode(node);
            map.put(key, node);
        }
    }

    private void removeNode(Node n){
        if(n.prev!=null){
            n.prev.next = n.next;
        }else{
            head = n.next;
        }

        if(n.next!=null){
            n.next.prev = n.prev;
        }else{
            tail = n.prev;
        }
    }

    private void offerNode(Node n){
        if(tail!=null){
            tail.next = n;
        }

        n.prev = tail;
        n.next = null;
        tail = n;

        if(head == null){
            head = tail;
        }
    }

    public void displayCache() {
        Node c = head;
        while(c != null) {
            c.print();
            c = c.next;
        }
    }

    public static void main(String[] args) {
        int capacity = 3;
        LRUCache lru = new LRUCache(capacity);
            lru.put(1,1);lru.put(2,2);lru.put(3,3);lru.put(4,4);
            lru.displayCache();
    }

}
