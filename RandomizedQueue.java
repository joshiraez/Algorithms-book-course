
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item>{

    private Item[]  items;
    private int     size    =0;
    
    public RandomizedQueue(){
        items = (Item[])new Object[1];
    }

    private class RandomizedQueueIterator implements Iterator<Item>{
        Item[] iteratorItems;
        int    index            =0;
        
        private RandomizedQueueIterator(){
            iteratorItems = (Item[])new Object[size];
            for (int i=0; i<size; i++){
                iteratorItems[i] = items[i];
            }
            StdRandom.shuffle(iteratorItems);
        }
        
        public boolean hasNext() { return index<iteratorItems.length; }
        public void     remove() { throw new UnsupportedOperationException(); }
        public Item       next()
        {
            if(index == iteratorItems.length) { throw new NoSuchElementException(); }
            return iteratorItems[index++];
        }        
    }
    
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    public boolean isEmpty(){
        return size==0;
    }
    
    public int size(){
        return size;
    }
    
    private void resize(int newCapacity){
        Item[] copy = (Item[]) new Object[newCapacity];
        for (int i=0; i<size; i++)
            copy[i] = items[i];
        items = copy;
    }
    
    public void enqueue(Item item){
        if (item==null)     { throw new IllegalArgumentException(); }
        if(size==items.length)
            resize(2*items.length);
        items[size++]       =item;
    }
    
    public Item dequeue(){
        if (size==0)        { throw new NoSuchElementException(); }
        if (size==items.length/4)
            resize(items.length/2);
        int random          = StdRandom.uniform(size);
        Item item           = items[random];
        items[random]       = items[--size];
        items[size]         = null;
        return item;
    }
    
    public Item sample(){
        if (size==0)        { throw new NoSuchElementException(); }
        int random          = StdRandom.uniform(size);
        return items[random];
    }
    
}
