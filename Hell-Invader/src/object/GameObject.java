package object;

import java.awt.*;

public class GameObject {

    public int x, y;
    public int width, height;
    public int vx, vy;

    public GameObject(int x, int y, int width, int height, int vx, int vy) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vx = vx;
        this.vy = vy;
    }

    public boolean intersects(GameObject other) {

        int left = x;
        int right = x + width;
        int top = y;
        int bottom = y + height;

        int oleft = other.x;
        int oright = other.x + other.width;
        int otop = other.y;
        int obottom = other.y + other.height;

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