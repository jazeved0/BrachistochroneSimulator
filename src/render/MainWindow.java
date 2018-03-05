package render;

import org.lwjgl.input.Keyboard;
import simulation.mode.HillClimbingMode;
import simulation.mode.SimulationMode;
import simulation.mode.TimeCollectionMode;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import simulation.mode.VarietyMode;

public class MainWindow {
    private static boolean paused = false;
    private static int speedExponent = 0;

    public static void main(String[] args) {
        try {
            Display.setDisplayMode(new DisplayMode(800, 640));
            Display.setTitle("The Brachistochrone Problem: A Computational Model");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }

        SimulationMode tcm = new TimeCollectionMode();
        tcm.init();
        SimulationMode hcm = new HillClimbingMode();
        hcm.init();
        SimulationMode vm = new VarietyMode();
        vm.init();
        SimulationMode simulationMode = tcm;

        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        while(!Display.isCloseRequested()) {
            // Clear the screen and depth buffer
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            while (Keyboard.next()) {
                if (Keyboard.getEventKeyState()) {
                    if (Keyboard.getEventKey() == Keyboard.KEY_1) {
                        // Switch to tcm
                        simulationMode = tcm;
                    } else if (Keyboard.getEventKey() == Keyboard.KEY_2) {
                        // Switch to hcm
                        simulationMode = hcm;
                    } else if (Keyboard.getEventKey() == Keyboard.KEY_3) {
                        // Switch to vm
                        simulationMode = vm;
                    } else if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
                        // Swap the pause state
                        paused = !paused;
                    } else if (Keyboard.getEventKey() == Keyboard.KEY_R) {
                        // Reset the simulations
                        tcm.init();
                        hcm.init();
                        vm.init();
                    } else if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
                        // Speed up by a power of two
                        ++speedExponent;
                        double newSpeed = Math.pow(2d, speedExponent);
                        tcm.setSpeed(newSpeed);
                        hcm.setSpeed(newSpeed);
                        vm.setSpeed(newSpeed);
                    } else if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
                        // slow down by a power of two
                        --speedExponent;
                        double newSpeed = Math.pow(2d, speedExponent);
                        tcm.setSpeed(newSpeed);
                        hcm.setSpeed(newSpeed);
                        vm.setSpeed(newSpeed);
                    }
                }
            }

            // Update the current simulation mode if not paused
            if(!paused) simulationMode.updateTick();

            GL11.glPushMatrix();
            {
                GL11.glScalef(0.35f, 0.45f, 1f);
                // Allow the current simulation mode to draw to the screen
                simulationMode.renderTick();
            }
            GL11.glPopMatrix();

            Display.update();
            Display.sync(60);
        }
        Display.destroy();
        System.exit(0);
    }
}