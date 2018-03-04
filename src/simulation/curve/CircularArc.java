package simulation.curve;

import math.Vector2d;

@SuppressWarnings("WeakerAccess")
public class CircularArc extends Curve {
    private Vector2d C;
    private Vector2d center;
    private double radius;
    private Vector2d[] cachedDisplayPoints;

    public CircularArc(Vector2d a, Vector2d b, Vector2d c) {
        super(a, b);
        C = c;
        this.center = calculateCenter();
        double thetaA = wrapTheta(Math.atan2((A.y - center.y), (A.x - center.x)));
        double thetaB = wrapTheta(Math.atan2((B.y - center.y), (B.x - center.x)));
        radius = Math.sqrt(Math.pow((center.x - a.x),2)+ Math.pow((center.y - a.y),2));
        cachedDisplayPoints = getArcPoints(center.x, center.y, radius, thetaA, thetaB - thetaA, 50);
    }

    private double wrapTheta(double thetaIn) {
        while(thetaIn < 0) thetaIn += 2d * Math.PI;
        return thetaIn;
    }

    @Override
    public double getTangentLineAngle(Vector2d pos) {
        double correctPosY = Math.sqrt(radius * radius - Math.pow(pos.x - center.x, 2)) + center.y;
        double phi = Math.atan2(correctPosY - center.y, pos.x - center.x); // calculate angular position
        return pos.y < B.y ? Math.atan(-Math.cos(phi) / Math.sin(phi)) : Math.abs(Math.atan(-Math.cos(phi) / Math.sin(phi))); // calculate arc tangent of the derivative of the tangent line to the arc
    }

    @Override
    public Vector2d[] getDisplayPoints() {
        return cachedDisplayPoints;
    }

    @Override
    public String getLabel() {
        return String.format("simulation.curve.CircularArc(%f,%f)", C.x, C.y);
    }

    private Vector2d calculateCenter() {
        double a = A.x;
        double b = A.y;
        double c = B.x;
        double d = B.y;
        double f = C.x;
        double g = C.y;
        double q = (d-b)*(b-g)*(g-d) - (f-a)*(f+a)*(d-b) + (a-c)*(a+c)*(b-g);
        double p = (g+b)*(b-g)*(c-a) + (f-c)*(a-f)*(c-a) - (b+d)*(d-b)*(a-f);
        double k = 2f * ((a-c)*(b-g) - (f-a)*(d-b));
        return new Vector2d(q / k, p / -k);
    }

    @SuppressWarnings("SameParameterValue")
    private Vector2d[] getArcPoints(double cx, double cy, double r, double start_angle, double arc_angle, int num_segments) {
        double thetaStep = arc_angle / num_segments;
        Vector2d[] points = new Vector2d[num_segments + 1];
        for(int i = 0; i < num_segments + 1; ++i) {
            double theta = start_angle + (thetaStep * i);
            points[i] = new Vector2d(cx + r * Math.cos(theta), cy + r * Math.sin(theta));
        }
        return points;
    }
}
