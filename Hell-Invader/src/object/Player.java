package object;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends GameObject {
    private GamePanel gp;
    private KeyHandler keyH;
    private BufferedImage[] sprites;
    private BufferedImage image;
    private String state;

    public ArrayList<Bullet> bullets = new ArrayList<>();
    public int hp;
    public boolean hitable = true;
    private int time = 0;

    private float boost;
    private int cooldown, threshhold;

    private int spriteCounter = 0;
    private int spriteNum = 1;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp.SCREEN_WIDTH/2 - gp.TILE_SIZE/6, gp.SCREEN_HEIGHT - gp.TILE_SIZE*2,
                gp.TILE_SIZE/4, gp.TILE_SIZE/2, 6, 6);
        this.gp = gp;
        this.keyH = keyH;
        sprites = new BufferedImage[4];
        state = "idle";
        hp = 5;
        boost = 1.2f;
        threshhold = 6;
        cooldown = threshhold;

        getImage();
    }

    public void getImage() {
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprite/player_sprite.png"));
            sprites[0] = spritesheet.getSubimage(0, 0, 16, 16);
            sprites[1] = spritesheet.getSubimage(16, 0, 16, 16);
            sprites[2] = spritesheet.getSubimage(32, 0, 16, 16);
            sprites[3] = spritesheet.getSubimage(48, 0, 16, 16);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        setInputs();
        // invencible
        if (!hitable) {
            time++;
            if (time == 90) {
                hitable = true;
                time = 0;
            }
        }
        // screen limit
        if(x <= 0) x = 0;
        if(y <= 0) y = 0;
        if(x >= gp.SCREEN_WIDTH - width)  x = gp.SCREEN_WIDTH - width;
        if(y >= gp.SCREEN_HEIGHT - height) y = gp.SCREEN_HEIGHT - height;
        // animation
        spriteCounter++;
        if(spriteCounter > 4) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        // update bullets
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update();

            if(bullets.get(i).y < 0) {
                bullets.remove(bullets.get(i));
            }
        }
        // destroy timer
        if(hp == 0) {
            gp.timerAlive.destroyTimer();
            gp.gameState = gp.OVER_STATE;
        }
    }

    public void draw(Graphics g) {
        // set image
        switch (state) {
            case "idle":
                image = sprites[0];
                break;
            case "left":
                image = sprites[1];
                break;
            case "right":
                image = sprites[2];
        }

        if(!hitable) {
            if (spriteNum == 1) {
                image = sprites[3];
            }
            if (spriteNum == 2) {
                image = sprites[0];
            }
        }

        g.drawImage(image, (int)x - gp.TILE_SIZE + gp.TILE_SIZE/6, (int)y - gp.TILE_SIZE + gp.TILE_SIZE/2,
                gp.TILE_SIZE*2, gp.TILE_SIZE*2, null);

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);
        }
    }

    public void setInputs() {
        // movement
        if(keyH.left) {
            state = "left";
            x -= vx;
            if(keyH.boost) {
                x -= vx * boost;
            }
        }
        if(keyH.right) {
            state = "right";
            x += vx;
            if(keyH.boost) {
                x += vx * boost;
            }
        }
        if(keyH.up) {
            state = "idle";
            y -= vy;
        }
        if(keyH.down) {
            state = "idle";
            y += vy;
        }
        if(keyH.up && keyH.left || keyH.down && keyH.left) {
            state = "left";
        }
        if(keyH.up && keyH.right || keyH.down && keyH.right) {
            state = "right";
        }
        if(keyH.left && keyH.right || !keyH.left && !keyH.right) {
            state = "idle";
        }
        // control fire rate
        cooldown++;
        if(keyH.shoot && cooldown >= threshhold) {
            gp.playSE(4);
            int offset = (width*4 / 2);
            bullets.add(new Bullet(x - offset, y, gp));
            bullets.add(new Bullet(x + offset, y, gp));
            cooldown = 0;
        }
    }
}