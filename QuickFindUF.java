public class QuickFindUF {
    private int[] id;
    
    public QuickFindUF(int N){
        id = new int[N];
        for (int i=0; i<N; i++)
            id[i]=i;
    } //O(n)
    
    public boolean connected (int p, int q){
        return id[p] == id[q];
    }//O(1)
    
    public void union (int p, int q){
        int pid = id[p];
        int qid = id[q];
        for(int i=0; i<id.length; i++){
            if(id[i]==pid)
                id[i]=qid;
        }
    }//O(n) - too slow (up to 2n+2)
}
