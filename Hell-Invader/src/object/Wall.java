package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Wall extends GameObject {

    GamePanel gp;
    BufferedImage image;
    Passage passage;
    Random random;

    public int speed;
    int xIndex = 0;
    public int pX = 0;
    int timer = 0;

    public Wall(GamePanel gp) {
        super(0, -gp.TILE_SIZE, gp.SCREEN_WIDTH, gp.TILE_SIZE);
        this.gp = gp;

        speed = 5;

        getImage();

        createPassage();
    }

    public void update() {

        y += speed;

        passage.update();

        loopWall();

        // SPEED TIMER
        if(speed < 15) {
            timer++;
            if(timer == 30*gp.FPS) {
                speed++;
                passage.speed++;
                timer = 0;
            }
        }
    }

    public void draw(Graphics g) {

        g.drawImage(image, x, y, width, height, null);

        passage.draw(g);
    }

    public void getImage() {

        try {

            BufferedImage sprite = ImageIO.read(getClass().getResourceAsStream("/sprite/wall_sprite.png"));
            image = sprite.getSubimage(0, 0, 96, 8);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void createPassage() {

        random = new Random();
        xIndex = random.nextInt(9);
        pX = xIndex * gp.TILE_SIZE;
        passage = new Passage(pX, y, height, speed, gp);
    }

    public void loopWall() {

        if(y > gp.SCREEN_HEIGHT) {

            xIndex = random.nextInt(9);
            pX = xIndex * gp.TILE_SIZE;
            passage.x = pX;

            y = -gp.TILE_SIZE;
            passage.y = -gp.TILE_SIZE;
        }
    }

    public void checkPassage(Player player) {

        boolean ok = passage.intersects(player);

        if(this.intersects(player)) {
            if(!ok) {
                gp.playSE(8);
                player.hp--;
            }
        }
    }
}
