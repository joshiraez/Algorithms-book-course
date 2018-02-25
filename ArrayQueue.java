public class ArrayQueue<T>  {
    
    T[]     items;
    int     first       =0;
    int     last        =0;
    int     size        =0;
    
    public ArrayQueue(){
        items           = (T[])new Object[1];
    }
    
    public boolean isEmpty(){
        return size==0;
    }
    
    public void queue(T item){
        if(size==items.length)
            resize(2*items.length);
        items[last]     =item;
        last            = (last +1)%items.length;
        size++;
    }
    
    public T dequeue(){
        T item          =items[first];
        items[first]    =null;
        first           =(first+1)%items.length;
        size--;
        if(size==items.length/4)
            resize(items.length/2);
        return item;
    }
    
    public void resize(int capacity){
        T[] copy = (T[])new Object[capacity];
        for(int i=0; i<size; i++){
            copy[i] = items[(first+i)%items.length];
        }
        first   =0;
        last    =first+size;
    }
    
}
