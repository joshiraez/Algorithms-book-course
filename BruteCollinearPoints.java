
import java.util.Arrays;
import java.util.LinkedList;

public class BruteCollinearPoints {
    private LineSegment[] lines;
    
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        //Corner cases
        if ( points == null )                               throw new IllegalArgumentException(); 
        for ( int i = 0; i < points.length; i++ ){
            if ( points[i] == null )                        throw new IllegalArgumentException(); 
            for ( int j = i - 1; j >=0; j-- )
                if ( points[i].compareTo(points[j]) == 0 )  throw new IllegalArgumentException(); 
        }
        
        //Algorithm
        LinkedList<LineSegment> linesFound     = new LinkedList<>();
        Point[]                 sortedPoints    = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);
        
        for             ( int p = 0;     p < sortedPoints.length; p++ ){
            for         ( int q = p + 1; q < sortedPoints.length; q++ ){
                for     ( int r = q + 1; r < sortedPoints.length; r++ ){
                    for ( int s = r + 1; s < sortedPoints.length; s++ ){
                        double slope = sortedPoints[p].slopeTo(sortedPoints[q]);
                        if ( slope == sortedPoints[p].slopeTo(sortedPoints[r] ) &&
                             slope == sortedPoints[p].slopeTo(sortedPoints[s] ) ){
                             linesFound.add(new LineSegment(sortedPoints[p], sortedPoints[s]));
                        }
                    }
                }
            }
        }
        
        lines = linesFound.toArray(new LineSegment[linesFound.size()]);
    }

    public           int numberOfSegments()        // the number of line segments
    { return lines.length;
    }
    public LineSegment[] segments()                // the line segments
    { return lines.clone(); }

}
