package simulation.mode;

import math.Vector2d;
import simulation.Simulation;
import simulation.curve.CircularArc;
import simulation.curve.CycloidTrack;
import simulation.curve.StraightTrack;

public class VarietyMode extends SimulationMode {
    @Override
    protected Simulation[] initSims() {
        return new Simulation[] {
                new Simulation(A, B, new StraightTrack(A, B)), // Straight track
                new Simulation(A, B, new CircularArc(A, B, new Vector2d(A.x, A.y).translate(2.5d, -2.775986d))), // Optimal circular arc
                new Simulation(A, B, new CycloidTrack(A, B))
        };
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
