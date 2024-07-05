package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EnemyBullet extends GameObject {
    private GamePanel gp;
    private BufferedImage[] sprites;
    private BufferedImage image;

    private int spriteCounter = 0;
    private int spriteNum = 1;

    public EnemyBullet(float x, float y, float vx, float vy, GamePanel gp) {
        super(x, y, gp.TILE_SIZE/4, gp.TILE_SIZE/4, vx, vy);
        this.gp = gp;
        sprites = new BufferedImage[4];
        getImage();
    }

    public void getImage() {
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprite/enemy_bullet_sprite.png"));
            sprites[0] = spritesheet.getSubimage(0, 0, 8, 8);
            sprites[1] = spritesheet.getSubimage(8, 0, 8, 8);
            sprites[2] = spritesheet.getSubimage(16, 0, 8, 8);
            sprites[3] = spritesheet.getSubimage(24, 0, 8, 8);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        y += vy;
        x += vx;

        // update image
        spriteCounter++;
        if(spriteCounter > 4) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 3;
            }
            else if(spriteNum == 3) {
                spriteNum = 4;
            }
            else if(spriteNum == 4) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics g) {
        // set image
        if(spriteNum == 1) {
            image = sprites[0];
        }
        if(spriteNum == 2) {
            image = sprites[1];
        }
        if(spriteNum == 3) {
            image = sprites[2];
        }
        if(spriteNum == 4) {
            image = sprites[3];
        }

        // draw enemy bullet
        g.drawImage(image, (int) x - gp.TILE_SIZE/3, (int) y - gp.TILE_SIZE/3, gp.TILE_SIZE, gp.TILE_SIZE, null);

        // draw hitbox
        //g.setColor(Color.magenta);
        //g.drawRect((int) x, (int) y, width, height);
    }
}