package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Wall extends GameObject {
    private GamePanel gp;
    private BufferedImage image;
    private Random random;
    private Passage passage;

    public int pX = 0;
    private int xIndex = 0;
    private int timer = 0;

    public Wall(GamePanel gp) {
        super(0, -gp.TILE_SIZE, gp.SCREEN_WIDTH, gp.TILE_SIZE, 0, 5);
        this.gp = gp;
        getImage();
        createPassage();
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

        passage.update();

        loopWall();
        // speed timer
        if(vy < 15) {
            timer++;
            if(timer == 30*gp.FPS) {
                vy++;
                passage.vy++;
                timer = 0;
            }
        }
    }

    public void draw(Graphics g) {
        g.drawImage(image, (int)x, (int)y, width, height, null);
        passage.draw(g);
    }

    public void createPassage() {
        random = new Random();
        xIndex = random.nextInt(9);
        pX = xIndex * gp.TILE_SIZE;
        passage = new Passage(pX, (int)y, height, (int)vy, gp);
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
        boolean ok = false;
        if(passage.intersects(player)) {
            ok = true;
        }

        if(this.intersects(player)) {
            if(!ok) {
                if(player.hitable) {
                    gp.playSE(8);
                    player.hp--;
                }
                player.hitable = false;
            }
        }
    }
}