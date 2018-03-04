package simulation.curve;

import math.Vector2d;

// Expects w = 5, h = 3
@SuppressWarnings("FieldCanBeLocal")
public class CycloidTrack extends Curve {
    private final int curveSegments = 50;
    private final double tFinal = 3.2341797d;
    private final double radius = 1.5030205d;

    public CycloidTrack(Vector2d A, Vector2d B) {
        super(A, B);
    }

    @Override
    public double getTangentLineAngle(Vector2d pos) {
        double dydx = 1d / Math.tan(Math.acos(clamp(((pos.y - A.y) / radius) + 1, -1d, 1d)) / 2d); // calculate derivative
        return pos.y < B.y ? Math.atan(dydx) : Math.abs(Math.atan(dydx)); // calculate arc tangent of the derivative of the tangent line to the arc
    }

    @Override
    public Vector2d[] getDisplayPoints() {
        double tStep = tFinal / curveSegments;
        Vector2d[] points = new Vector2d[curveSegments + 1];
        for(int i = 0; i < curveSegments + 1; ++i) {
            double t = tStep * i;
            points[i] = new Vector2d(radius * (t - Math.sin(t)) + A.x, radius * (Math.cos(t) - 1) + A.y);
        }
        return points;
    }

    @Override
    public String getLabel() {
        return "CycloidTrack";
    }

    private static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}
