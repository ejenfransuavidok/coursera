import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private int count = 0;

    private LineSegment [] segments = new LineSegment [10];

    public BruteCollinearPoints(Point[] points) {  // finds all line segments containing 4 points
        for (int i=0; i<points.length-3; i++) {
            Point one = points [i];
            for (int j=i+1; j<points.length-2; j++) {
                Point two = points [j];
                double slope_1_2 = two.slopeTo(one);
                for (int k=j+1; k<points.length-1; k++) {
                    Point three = points [k];
                    double slope_1_3= three.slopeTo(one);
                    if (slope_1_2 != slope_1_3)
                        continue;
                    for (int l=k+1; l<points.length; l++) {
                        Point four = points [l];
                        double slope_1_4 = four.slopeTo(one);
                        if (slope_1_2 == slope_1_4) {
                            addSegment(new LineSegment(getMin(one, two, three, four), getMax(one, two,three, four)));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {                // the number of line segments
        return count;
    }

    public LineSegment[] segments() {              // the line segments
        return segments;
    }

    private void addSegment(LineSegment segment) {
        segments [count++] = segment;
        if (count == segments.length) {
            segments = Arrays.copyOf(segments, count * 2);
        }
    }

    private Point getMax(Point p1, Point p2, Point p3, Point p4) {
        Point [] arr = {p2, p3, p4};
        Point max = p1;
        for (Point point : arr)
            if (max.compareTo(point) < 0)
                max = point;
        return max;
    }

    private Point getMin(Point p1, Point p2, Point p3, Point p4) {
        Point [] arr = {p2, p3, p4};
        Point min = p1;
        for (Point point : arr)
            if (min.compareTo(point) > 0)
                min = point;
        return min;
    }

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
