package render;

import math.Vector2d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@SuppressWarnings("WeakerAccess")
public class RenderUtils {
    public static Color getColorForHue(float hue) {
        return Color.getHSBColor(hue, 1.0f, 0.9f);
    }

    public static void drawDot(Vector2d pointMassPosition, float radius, Color color, boolean hasStroke) {
        GL11.glColor3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        drawCircle((float)pointMassPosition.x, (float)pointMassPosition.y, radius, 12, true, 0.0f);

        if(hasStroke) {
            // Darker border
            GL11.glColor3f(color.getRed() / 510f, color.getGreen() / 510f, color.getBlue() / 510f);
            drawCircle((float)pointMassPosition.x, (float)pointMassPosition.y, radius, 12, false, 2.0f);
        }
    }

    public static void drawLineLoop(Vector2d[] points, Color color, float width) {
        GL11.glColor3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        GL11.glLineWidth(width);
        GL11.glBegin(GL11.GL_LINE_STRIP);

        for(Vector2d p : points)
        {
            GL11.glVertex2f((float)p.x, (float)p.y);
        }
        GL11.glEnd();
    }

    public static void drawCircle(float cx, float cy, float r, int num_segments, boolean filled, float strokeWidth)
    {
        float theta = 2 * 3.1415926f / (float)num_segments;
        float tan_factor = (float) Math.tan(theta);//calculate the tangential factor
        float radial_factor = (float)Math.cos(theta);//calculate the radial factor

        float x = r;//we start at angle = 0
        float y = 0;

        if(filled) GL11.glBegin(GL11.GL_POLYGON);
        else {
            GL11.glBegin(GL11.GL_LINE_LOOP);
            GL11.glLineWidth(strokeWidth);
        }

        for(int ii = 0; ii < num_segments; ii++)
        {
            GL11.glVertex2f(x + cx, y + cy);

            float tx = -y;
            float ty = x;

            x += tx * tan_factor;
            y += ty * tan_factor;

            x *= radial_factor;
            y *= radial_factor;
        }
        GL11.glEnd();
    }


    public static void renderTexture(Texture tex, float x, float y, float w, float h) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        tex.bind();
        float u1 = 0f;
        float v1 = 1f;
        float u2 = 1f;
        float v2 = 0f;
        GL11.glBegin(GL11.GL_QUADS); {
            GL11.glTexCoord2f(u2, v1);
            GL11.glVertex2f(x + w, y);
            GL11.glTexCoord2f(u2, v2);
            GL11.glVertex2f(x + w, y + h);
            GL11.glTexCoord2f(u1, v2);
            GL11.glVertex2f(x, y + h);
            GL11.glTexCoord2f(u1, v1);
            GL11.glVertex2f(x, y);
        } GL11.glEnd();
    }
}
