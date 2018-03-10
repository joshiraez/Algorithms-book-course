
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private LineSegment[] lines;
    
    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        //Corner cases
        if ( points == null )                               throw new IllegalArgumentException(); 
        for ( int i = 0; i < points.length; i++ ){
            if ( points[i] == null )                        throw new IllegalArgumentException(); 
            for ( int j = i - 1; j >= 0; j-- )
                if ( points[i].compareTo(points[j]) == 0 )  throw new IllegalArgumentException(); 
        }

        //Algorithm
        LinkedList<LineSegment> linesFound     = new LinkedList<>();
        Point[]                 sortedPoints    = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);
        
        for ( int p = 0; p < points.length; p++ ){
            Point       actualPoint         = sortedPoints[p];
            Point[]     pointsSortedBySlope = points.clone();
            Arrays.sort(pointsSortedBySlope, sortedPoints[p].slopeOrder());
            LinkedList<Point> collinearPoints = new LinkedList<>();
            collinearPoints.add(actualPoint);
            double      targetSlope;
            int         firstCollinear, lastCollinear;
            
            //Check for 3 with the same.
            firstCollinear = 0;
            while ( firstCollinear < pointsSortedBySlope.length ){
                collinearPoints.add(pointsSortedBySlope[firstCollinear]);
                targetSlope     = actualPoint.slopeTo(pointsSortedBySlope[firstCollinear]);
                lastCollinear   = firstCollinear ++;
                while ( lastCollinear < pointsSortedBySlope.length                             && 
                        actualPoint.slopeTo(pointsSortedBySlope[lastCollinear]) == targetSlope ) {
                    collinearPoints.add(pointsSortedBySlope[lastCollinear]);
                    lastCollinear++;
                }
                
                //Now we check if there is at least 3 collinear
                if( collinearPoints.size() >= 4 ){
                    //Now we check that our Point is the first after sorting.
                    //So we can assure this is the first time we have found this line.
                    Point[] check = collinearPoints.toArray(new Point[collinearPoints.size()]);
                    Arrays.sort(check);
                    if ( check[0].compareTo(actualPoint) == 0 ){
                        //Then we can safely introduce the Line Segment.
                        linesFound.add(new LineSegment(check[0], check[check.length-1]));
                    }
                }
                
                firstCollinear = lastCollinear;
                collinearPoints.clear();
                collinearPoints.add(actualPoint);
            }
            
        }
        
        lines = linesFound.toArray(new LineSegment[linesFound.size()]);
    }
    public           int numberOfSegments()        // the number of line segments
    { return lines.length; }
    
    public LineSegment[] segments()                // the line segments
    { return lines.clone(); }
    
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}