package object.player;

import main.GamePanel;
import object.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bullet extends GameObject {
    private final GamePanel gp;
    private BufferedImage image;

    public Bullet(float x, float y, GamePanel gp) {
        super(x, y, gp.TILE_SIZE/4, gp.TILE_SIZE/2, 0, 50);
        this.gp = gp;
        getImage();
    }

    public void getImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/sprite/player_bullet_sprite.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        y -= vy;
    }

    public void draw(Graphics g) {
        // draw bullet
        g.drawImage(image, (int)x - gp.TILE_SIZE/3, (int)y - gp.TILE_SIZE/4, gp.TILE_SIZE, gp.TILE_SIZE, null);

        // draw hitbox
        //g.setColor(Color.yellow);
        //g.drawRect((int)x, (int)y, width, height);
    }
}