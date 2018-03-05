package simulation.mode;

import math.Vector2d;
import org.lwjgl.opengl.GL11;
import render.RenderUtils;
import render.Texture;
import simulation.Simulation;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public abstract class SimulationMode {
    protected final double w = 5.0d;
    protected final double h = 3.0d;
    protected final double simsPerSecond = 12000;
    protected final int fps = 60;
    protected double speed = 1.0f;

    protected Simulation[] sims;
    protected double[] t;

    protected Vector2d A;
    protected Vector2d B;
    protected Texture A_label;
    protected Texture B_label;

    @SuppressWarnings("deprecation")
    public void init() {
        A = new Vector2d(-w / 2d, h / 2d);
        B = new Vector2d(w / 2d, -h / 2d);

        sims = initSims();
        t = new double[sims.length];

        try {
            A_label = new Texture(new File("resources/a.png").toURL());
            B_label = new Texture(new File("resources/b.png").toURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTick() {
        int simsPerFrame = (int)(simsPerSecond / fps);
        double dt = speed / simsPerSecond;
        for(int i = 0; i < simsPerFrame; ++i)
        {
            if(!allDone(sims)) {
                for(int j = 0; j < sims.length; ++j)
                {
                    if(sims[j].isRunning()) {
                        sims[j].advance(dt);
                        t[j] += dt;
                    }
                }
            } else {
                completeSims();
                t = new double[sims.length];
            }
        }
    }

    protected abstract void completeSims();
    protected abstract Simulation[] initSims();

    private static boolean allDone(Simulation[] sims) {
        for(Simulation sim : sims) if(sim.isRunning()) return false;
        return true;
    }

    public void renderTick() {
        float hueStep = 0.5f / sims.length;
        for (int i = 0; i < sims.length; ++i) {
            RenderUtils.drawLineLoop(sims[i].getCurve().getDisplayPoints(), RenderUtils.getColorForHue(0.5f + i * hueStep), 4f);
        }

        RenderUtils.drawDot(A, 0.06f, Color.BLACK);
        RenderUtils.drawDot(B, 0.06f, Color.BLACK);

        for (int i = 0; i < sims.length; ++i) {
            RenderUtils.drawDot(sims[i].getPointMassPosition(), 0.1f, RenderUtils.getColorForHue(i * hueStep));
        }

        GL11.glPushMatrix(); {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            RenderUtils.renderTexture(A_label, (float)A.x - 0.1f, (float)A.y + 0.14f, 0.2f, 0.2f);
            RenderUtils.renderTexture(B_label, (float)B.x - 0.1f, (float)B.y - 0.32f, 0.2f, 0.2f);
            GL11.glDisable(GL11.GL_BLEND);
        } GL11.glPopMatrix();
    }

    public void setSpeed(double newSpeed) {
        speed = newSpeed;
    }
}
