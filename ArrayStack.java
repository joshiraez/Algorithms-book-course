public class ArrayStack<T> {
    private T[] items;
    private int N       = 0;
    
    public ArrayStack(){
        items = (T[]) new Object[1];
    }
    
    public boolean isEmpty(){
        return N==0;
    }
    
    public void push(T item){
        if( N == items.length ) 
            resize (2 * items.length);
        items[N++] = item;
    }
    
    public T pop(){
        T item      = items[--N];
        items[N]    = null;
        if ( N > 0 && N == items.length/4 ) 
            resize (items.length / 2);
        return item;
    }
    
    private void resize(int newCapacity){
        T[] copy = (T[]) new Object[newCapacity];
        for (int i=0; i<N; i++)
            copy[i] = items[i];
        items = copy;
    }
    
}
