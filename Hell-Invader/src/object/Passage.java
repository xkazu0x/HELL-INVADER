package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Passage extends GameObject {

    GamePanel gp;
    BufferedImage image;

    int speed;

    public Passage(int x, int y, int height, int speed, GamePanel gp) {
        super(x, y, gp.TILE_SIZE*3, height);
        this.gp = gp;
        this.speed = speed;

        getImage();
    }

    public void update() {

        y += speed;
    }

    public void draw(Graphics g) {

        g.drawImage(image, x, y, width, height, null);
    }

    public void getImage() {

        try {

            BufferedImage sprite = ImageIO.read(getClass().getResourceAsStream("/sprite/passage_sprite.png"));
            image = sprite.getSubimage(0, 0, 24, 8);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
