package main;

import object.Enemy;
import object.Wall;
import object.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {

    public final int ORIGINAL_TILE_SIZE = 8;
    public final int SCALE = 6;

    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public final int MAX_SCREEN_COL = 12;
    public final int MAX_SCREEN_ROW = 16;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;

    // FPS
    public final int FPS = 60;

    // SYSTEM
    KeyHandler keyH = new KeyHandler(this);
    Sound sound = new Sound();
    Gui ui = new Gui(this);
    public TimerAlive timerAlive = new TimerAlive();
    Thread gameThread;

    // ENTITY
    public Wall wall;
    public Player player;
    public ArrayList<Enemy> enemies;

    // GAME STATE
    public int gameState;
    public final int TITLE_STATE = 0;
    public final int PLAY_STATE = 1;
    public final int OVER_STATE = 2;

    int score;
    int timer = 0;
    Random random = new Random();

    public GamePanel() {

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        wall = new Wall(this);
        player = new Player(this, keyH);
        enemies = new ArrayList<>();
        enemies.add(new Enemy(10, this));

        score = 0;

        gameState = TITLE_STATE;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        if(gameState == PLAY_STATE){

            timer++;
            if(timer % 30 == 0) {
                int x = random.nextInt(SCREEN_WIDTH - TILE_SIZE*2);
                enemies.add(new Enemy(x, this));
                if(timer % 50 == 0) {
                    int xx = random.nextInt(SCREEN_WIDTH - TILE_SIZE*2);
                    enemies.add(new Enemy(xx, this));
                    timer = 0;
                }
            }

            wall.update();
            wall.checkPassage(player);

            player.update();

            // enemy updates
            for(int i = 0; i < enemies.size(); i++) {
                enemies.get(i).update();

                // player bullets intersects enemy
                for(int k = 0; k < player.bullets.size(); k++) {
                    if(enemies.get(i).intersects(player.bullets.get(k))) {
                        playSE(5);
                        player.bullets.remove(player.bullets.get(k));
                        enemies.get(i).hp--;
                        score += 100;
                    }
                }

                for(int j = 0; j < enemies.get(i).enemyBullets.size(); j++) {
                    if(player.intersects(enemies.get(i).enemyBullets.get(j))) {
                        playSE(8);
                        enemies.get(i).enemyBullets.remove(enemies.get(i).enemyBullets.get(j));
                        player.hp--;
                    }
                }

                if(enemies.get(i).y > SCREEN_HEIGHT || enemies.get(i).hp < 1) {
                    playSE(7);
                    enemies.remove(enemies.get(i));
                }
            }
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if(gameState == TITLE_STATE || gameState == OVER_STATE) {
            ui.draw(g2);
        }
        if(gameState == PLAY_STATE) {

            g2.setColor(Color.black);
            g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

            ui.draw(g2);

            wall.draw(g2);

            ui.drawTimer();

            for(int i = 0; i < enemies.size(); i++) {
                enemies.get(i).draw(g2);
            }

            player.draw(g2);

            if(keyH.hitbox) {
                for(int i = 0; i < enemies.size(); i++) {
                    enemies.get(i).drawHitbox(Color.red, enemies.get(i).x, enemies.get(i).y, enemies.get(i).width, enemies.get(i).height, g2);
                }
                player.drawHitbox(Color.green, player.x , player.y, player.width, player.height, g);
                wall.drawHitbox(Color.red, wall.x, wall.y, wall.width, wall.height, g);
                wall.drawHitbox(Color.yellow, wall.pX, wall.y, TILE_SIZE*3, wall.height, g);
            }
        }

        g2.dispose();
    }

    public void playMusic(int i) {

        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {

        sound.stop();
    }

    public void playSE(int i) {

        sound.setFile(i);
        sound.play();
    }
}
