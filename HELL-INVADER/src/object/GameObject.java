package object;

import java.awt.*;

public class GameObject {
    public float x, y;
    public float vx, vy;
    public int width, height;

    public GameObject(float x, float y, int width, int height, float vx, float vy) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vx = vx;
        this.vy = vy;
    }

    public boolean intersects(GameObject other) {
        float left = x;
        float right = x + width;
        float top = y;
        float bottom = y + height;

        float oleft = other.x;
        float oright = other.x + other.width;
        float otop = other.y;
        float obottom = other.y + other.height;

        return !(left >= oright ||
                right <= oleft ||
                top >= obottom ||
                bottom <= otop);
    }

    public void drawHitbox(Color color, int x, int y, int width, int height, Graphics g) {
        g.setColor(color);
        g.drawRect(x, y, width, height);
    }
}