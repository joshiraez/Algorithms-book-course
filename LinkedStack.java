import java.util.Iterator;

public class LinkedStack<T> implements Iterable<T>{
    private Node<T> first = null;
    
    private class Node<E>{
        private E       item;
        private Node    next;
    }
    
    private class LinkedStackIterator implements Iterator<T>{
        private Node<T> current = first;
        
        public boolean hasNext() { return current.next == null; }
        public void     remove() { throw new UnsupportedOperationException(); }
        public T        next()
        {
            T item      = current.item;
            current     = current.next;
            return item;
        }
    }
    
    public boolean isEmpty(){
        return first==null;
    }
    
    public void push(T item){
        Node oldFirst = first;
        first       = new Node();
        first.item  = item;
        first.next  = oldFirst;
    }
    
    public T pop (){
        T item  = first.item;
        first   = first.next;
        return item;
    }
    
    public Iterator<T> iterator() {
        return new LinkedStackIterator();
    }
    

}
