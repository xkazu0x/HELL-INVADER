package object.enemy;

import main.GamePanel;
import object.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Enemy extends GameObject {
    private final GamePanel gp;
    private BufferedImage image;
    private final BufferedImage[] sprites;
    public ArrayList<EnemyBullet> enemyBullets;

    public int hp;
    public String state;
    private int cooldown;
    private final int threshhold;

    public Enemy(float x, GamePanel gp) {
        super(x, -gp.TILE_SIZE*2, gp.TILE_SIZE*2, gp.TILE_SIZE*2, 0, 2);
        this.gp = gp;
        sprites = new BufferedImage[2];
        enemyBullets = new ArrayList<>();

        hp = 5;
        state = "idle";
        threshhold = 90;
        cooldown = threshhold;

        getImage();
    }

    public void getImage() {
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprite/enemy_sprite00.png"));
            sprites[0] = spritesheet.getSubimage(0, 0, 16, 16);
            sprites[1] = spritesheet.getSubimage(16, 0, 16, 16);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        y += vy;
        // update bullets
        for(int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).update();
            if(enemyBullets.get(i).y > gp.SCREEN_HEIGHT) {
                enemyBullets.remove(enemyBullets.get(i));
            }
        }
        // control fire rate
        cooldown++;
        if(cooldown >= threshhold) {
            gp.playSE(6);
            enemyBullets.add(new EnemyBullet(x + width/2 - (gp.TILE_SIZE/4)/2, y + height/2 - (gp.TILE_SIZE/4)/2, 1, 5, gp));
            enemyBullets.add(new EnemyBullet(x + width/2 - (gp.TILE_SIZE/4)/2, y + height/2 - (gp.TILE_SIZE/4)/2, -1, 5, gp));
            cooldown = 0;
        }
        if (state.equals("hit")) state = "idle";
    }

    public void draw(Graphics g) {
        // set images
        switch (state) {
            case "idle":
                image = sprites[0];
                break;
            case "hit":
                image = sprites[1];
                break;
        }
        // draw enemy
        g.drawImage(image, (int) x, (int) y, width, height, null);
        // draw enemy bullets
        for(int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).draw(g);
        }
    }
}