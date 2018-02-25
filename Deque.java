
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first      = null;
    private Node<Item> last       = null;
    private int        size       = 0;
    
    private class Node<E>{
        private Node<E> next;
        private Node<E> previous;
        private E       item;
    }
    
    private class DequeIterator implements Iterator<Item>{
        private Node<Item> current;
        
        private DequeIterator(){
            current = first;
        }
        
        public boolean hasNext() { return current != null; }
        public void     remove() { throw new UnsupportedOperationException(); }
        public Item       next()
        {
            if (current==null) { throw new NoSuchElementException(); }
            Item item   = current.item;
            current     = current.next;
            return item;
        }        
    }
    
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    public boolean isEmpty(){
        return size==0;
    }
    
    public int      size(){
        return size;
    }
    
    public void addFirst(Item item){
        if(item==null)      { throw new IllegalArgumentException(); }
        Node<Item> newNode  = new Node<>();
        newNode.item        = item;
        newNode.next        = first;
        if(size==0){        //Empty
            first           = newNode;
            last            = newNode;
        }else{
            first.previous  = newNode;
            first           = newNode;
        }
        size++;
    }
    
    public void addLast(Item item){
        if(item==null)      { throw new IllegalArgumentException(); }
        Node<Item> newNode  = new Node<>();
        newNode.item        = item;
        newNode.previous    = last;
        if(size==0){        //Empty
            first           = newNode;
            last            = newNode;
        }else{
            last.next       = newNode;
            last            = newNode;
        }
        size++;
    }
    
    public Item removeFirst(){
        if(size==0)         { throw new NoSuchElementException(); }
        Node<Item> getNode  = first;
        if(getNode.next==null){
            first           = null;
            last            = null;
        }else{
            first           =getNode.next;
            first.previous  =null;
        }
        size--;
        return getNode.item;
    }
    
    public Item removeLast(){
        if(size==0)         { throw new NoSuchElementException(); }
        Node<Item> getNode  = last;
        if(getNode.previous==null){
            first           = null;
            last            = null;
        }else{
            last           =getNode.previous;
            last.next      =null;
        }
        size--;
        return getNode.item;
    }
    
    public static void main(String[] args) {
        Deque<Integer> example = new Deque<>();
        
        for(int i=0; i<50; i++){
            example.addFirst(i);
        }
        
        Iterator<Integer> it = example.iterator();
        for(Integer a : example){
            System.out.println(a);
        }
    }
}
