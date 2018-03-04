package simulation.mode;

import simulation.Simulation;
import simulation.curve.CircularArc;
import simulation.curve.StraightTrack;
import math.Vector2d;

public class TimeCollectionMode extends SimulationMode {
    @Override
    protected Simulation[] initSims() {
        return new Simulation[] {
                circular(A, B, 2.5d, -3.25d),
                circular(A, B, 2.5d, -2.95d),
                circular(A, B, 2.5d, -2.65),
                circular(A, B, 2.5d, -2.35),
                circular(A, B, 2.5d, -2.05d),
                circular(A, B, 2.5d, -1.8d),
                new Simulation(A, B, new StraightTrack(A, B)),
                circular(A, B, 2.5d, -1.5d),
                circular(A, B, 2.5d, -1.25d),
                circular(A, B, 2.5d, -0.95d),
                circular(A, B, 2.5d, -0.65d)
        };
    }

    @SuppressWarnings("SameParameterValue")
    private static Simulation circular(Vector2d a, Vector2d b, double dcx, double dcy) {
        return new Simulation(a, b, new CircularArc(a, b, new Vector2d(a.x, a.y).translate(dcx, dcy)));
    }

    @Override
    protected void completeSims() {
        System.out.println(formatResults(sims, t));
        sims = initSims();
    }


    private static String formatResults(Simulation[] sims, double[] t) {
        StringBuilder text = new StringBuilder("Results: ");
        for(int i = 0; i < sims.length; ++i) {
            text.append(sims[i].getCurve().getLabel());
            text.append(": ");
            text.append(String.format("%f", t[i]));
            text.append("; ");
        }
        return text.toString();
    }
}
