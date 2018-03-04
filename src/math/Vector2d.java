package math;

@SuppressWarnings("WeakerAccess, unused")
public class Vector2d {
    public double x, y;
    public Vector2d() { }
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector2d translate(double dx, double dy) {
        x += dx;
        y += dy;
        return this;
    }
    public Vector2d scale(double scaleFactor) {
        x *= scaleFactor;
        y *= scaleFactor;
        return this;
    }

    @Override
    public String toString() {
        return String.format("[%f, %f]", x, y);
    }
}