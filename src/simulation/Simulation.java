package simulation;

import simulation.curve.Curve;
import config.Configuration;
import math.Vector2d;

@SuppressWarnings("WeakerAccess")
public class Simulation {
    public Simulation(Vector2d a, Vector2d b, Curve curve) {
        A = a;
        B = b;
        this.curve = curve;
        m = new PointMass();
        m.r.x = A.x + 0.0001d;
        m.r.y = A.y - 0.0001d;
    }
    private Curve curve;
    private Vector2d A;
    private Vector2d B;
    private PointMass m;

    public void advance(double dt) {
        // Translate m's position by half of its velocity
        move(m, 0.5, dt);

        // Calculate the new velocity
        m.setV(calculateVelocity(m.r, curve, A.y - m.r.y));

        // Translate m's position by the second half of its velocity
        move(m, 0.5, dt);

        //System.out.println(String.format("V system:  r(%f,%f)  v(%f,%f)  theta(%f)", m.r.x, m.r.y, m.v.x, m.v.y, curve.getTangentLineAngle(m.r)));
    }

    public boolean isRunning() { return m.r.x < B.x; }

    @SuppressWarnings("SameParameterValue")
    private static void move(PointMass pm, double scaleFactor, double dt) {
        pm.r.translate((pm.v.x * dt) * scaleFactor, (pm.v.y * dt) * scaleFactor);
    }

    private static Vector2d calculateVelocity(Vector2d pos, Curve curve, double height) {
        double theta = curve.getTangentLineAngle(pos);
        double magnitude = Math.sqrt(2f * Configuration.G * height);
        return new Vector2d(magnitude * Math.cos(theta), magnitude * Math.sin(theta) * -1f);
    }

    public Curve getCurve() {
        return curve;
    }

    public Vector2d getPointMassPosition() {
        return m.r;
    }

    @SuppressWarnings("WeakerAccess, unused")
    public class PointMass {
        private Vector2d v = new Vector2d();
        private Vector2d r = new Vector2d();

        public Vector2d getV() {
            return v;
        }

        public void setV(Vector2d v) {
            this.v = v;
        }

        public Vector2d getR() {
            return r;
        }

        public void setR(Vector2d r) {
            this.r = r;
        }
    }
}
