public class QuickUnionUF {
 
    private int[] id;
    private int[] sz;
    
    public QuickUnionUF(int N){
        id=new int[N];
        sz=new int[N];
        for (int i=0; i<N; i++){
            id[i]=i;
            sz[i]=0;
        }
    } //O(N)
    
    private int root(int i){
        int j = i;
        int nextNode;
        //First pass: find root.
        while( i!= id[i] ) 
            i= id[i];
        //Assign every node the root - path comprenhension
        while( j!= i ){
            nextNode = id[j];
            id[j]=i;
            j= nextNode;
        }
        return i;
    } //O(length of i), can be at most lg(N) 
    
    public boolean connected (int p, int q){
        return root(p) == root(q);
    } //O(length of p and length of q), can be at most lg(N)
    
    public void union(int p, int q){
        int i = root(p);
        int j = root(q);
        if (i==j) return;
        //Weighted tree
        if (sz[i] < sz[j]){id[i] = j; sz[j]+=sz[i];}
        else              {id[j] = i; sz[i]+=sz[j];}
    } //O(length of p and length of q), can be at most lg(N)
}
