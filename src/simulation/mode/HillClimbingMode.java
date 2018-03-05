package simulation.mode;

import math.Vector2d;
import simulation.Simulation;
import simulation.curve.CircularArc;

@SuppressWarnings("FieldCanBeLocal")
public class HillClimbingMode extends SimulationMode {
    private final int maxDepth = 10;
    private int depth;
    private Vector2d currentCenter;
    private double currentBreadth;

    @Override
    public void init() {
        super.init();

        // Initial hill climbing seed
        currentCenter = new Vector2d(A.x + 2.5d, A.y - 1.97872d); // limit at the circle tangent to the vertical at A
        currentBreadth = 1.3d;
        sims = setupNewTrek(A, B, currentCenter, currentBreadth);
        depth = 0;
    }

    @Override
    protected Simulation[] initSims() {
        return new Simulation[2];
    }

    private static Simulation[] setupNewTrek(Vector2d A, Vector2d B, Vector2d center, double breadth) {
        Simulation upper = new Simulation(A, B, new CircularArc(A, B, new Vector2d(center.x, center.y + breadth)));
        Simulation lower = new Simulation(A, B, new CircularArc(A, B, new Vector2d(center.x, center.y - breadth)));
        return new Simulation[] { upper, lower };
    }

    @Override
    protected void completeSims() {
        if(depth > maxDepth) {
            int index = t[0] < t[1] ? 0 : 1;
            System.out.println(String.format("Fastest: %s; t=%f", sims[index].getCurve().getLabel(), t[index]));

            // Reset to the initial hill climbing seed
            currentCenter = new Vector2d(A.x + 2.5d, A.y - 1.97872d); // limit at the circle tangent to the vertical at A
            currentBreadth = 1.3d;
            sims = setupNewTrek(A, B, currentCenter, currentBreadth);
            depth = 0;
        }

        // Reduce the breadth for the next trek
        currentBreadth /= 2d;

        // Determine the next center based off which path was faster
        if(t[0] < t[1]) { // upper was faster than lower
            currentCenter = new Vector2d(currentCenter.x, currentCenter.y + currentBreadth);
        } else { // lower was faster than upper
            currentCenter = new Vector2d(currentCenter.x, currentCenter.y - currentBreadth);
        }

        sims = setupNewTrek(A, B, currentCenter, currentBreadth);
        ++depth;
        t = new double[sims.length];
    }
}
