package simulation.curve;

import math.Vector2d;

@SuppressWarnings("WeakerAccess")
public abstract class Curve {
    protected Vector2d A, B;
    protected Curve(Vector2d A, Vector2d B) {
        this.A = A;
        this.B = B;
    }

    public abstract double getTangentLineAngle(Vector2d pos);
    public abstract Vector2d[] getDisplayPoints();
    public abstract String getLabel();
}
