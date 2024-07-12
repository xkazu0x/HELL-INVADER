package object.wall;

import main.GamePanel;
import object.GameObject;
import object.player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Wall extends GameObject {
    private final GamePanel gp;
    private BufferedImage image;
    private int tick = 0;

    public Wall(int x, GamePanel gp) {
        super(x, -gp.TILE_SIZE, gp.SCREEN_WIDTH, gp.TILE_SIZE, 0, 5);
        this.gp = gp;
        getImage();
    }

    public void getImage() {
        try {
            BufferedImage sprite = ImageIO.read(getClass().getResourceAsStream("/sprite/wall_sprite.png"));
            image = sprite.getSubimage(0, 0, 96, 8);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        y += vy;

        // speed velocity update
        if(vy < 15) {
            tick++;
            if (tick >= 30 * 60) {
                vy++;
                tick = 0;
            }
        }
    }

    public void draw(Graphics g) {
        g.drawImage(image, (int)x, (int)y, width, height, null);
    }

    public void checkPassage(Player player) {
        if(this.intersects(player)) {
            if(player.hitable) {
                gp.playSE(8);
                player.hp--;
            }
            player.hitable = false;
        }
    }
}