package simulation.curve;

import math.Vector2d;

public class StraightTrack extends Curve {
    private double cachedAngle;

    public StraightTrack(Vector2d A, Vector2d B) {
        super(A, B);
        cachedAngle = Math.atan(Math.abs((B.y - A.y)) / Math.abs((B.x - A.x)));
    }

    @Override
    public double getTangentLineAngle(Vector2d pos) {
        return cachedAngle;
    }

    @Override
    public Vector2d[] getDisplayPoints() {
        return new Vector2d[]{A, B};
    }

    @Override
    public String getLabel() {
        return "simulation.curve.StraightTrack";
    }
}
