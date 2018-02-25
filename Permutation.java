
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int             numberTimes = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomQueue = new RandomizedQueue<>();
        
        while(!StdIn.isEmpty()){
            randomQueue.enqueue(StdIn.readString());
        }
        
        for(int i=0; i<numberTimes; i++){
            StdOut.println(randomQueue.dequeue());
        }
        
    }
}
