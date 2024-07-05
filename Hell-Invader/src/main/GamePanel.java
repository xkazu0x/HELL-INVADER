package main;

import main.utilits.Gui;
import main.utilits.KeyHandler;
import main.utilits.Sound;
import main.utilits.TimerAlive;
import object.enemy.Enemy;
import object.wall.Wall;
import object.player.Player;

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

    public final int FPS = 60;

    // system
    private final KeyHandler keyH = new KeyHandler(this);
    private final Sound sound = new Sound();
    private final Gui ui = new Gui(this);
    private Thread thread;

    // objects
    public Player player;
    public ArrayList<Enemy> enemies;
    public Wall lWall;
    public Wall rWall;

    // game states
    public int gameState;
    public final int TITLE_STATE = 0;
    public final int PLAY_STATE = 1;
    public final int OVER_STATE = 2;

    // stats
    public int score = 0;
    private int timer = 0;
    private int space = TILE_SIZE * 3;

    public TimerAlive timerAlive = new TimerAlive();
    private Random random = new Random();

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void init() {
        gameState = TITLE_STATE;
        score = 0;

        player = new Player(this, keyH);
        enemies = new ArrayList<>();

        int x0 = random.nextInt(3, 12) * TILE_SIZE;
        int x1 = x0 - SCREEN_WIDTH - space;
        rWall = new Wall(x0, this);
        lWall = new Wall(x1, this);
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (thread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (gameState == PLAY_STATE){
            player.update();

            lWall.update();
            rWall.update();
            lWall.checkPassage(player);
            rWall.checkPassage(player);

            if (lWall.y > SCREEN_HEIGHT || rWall.y > SCREEN_HEIGHT) {
                lWall.y = -TILE_SIZE;
                rWall.y = -TILE_SIZE;

                int x0 = random.nextInt(3, 12) * TILE_SIZE;
                int x1 = x0 - SCREEN_WIDTH - space;
                rWall.x = x0;
                lWall.x = x1;
            }

            // enemy spawner
            timer++;
            if (timer % 30 == 0) {
                int x = random.nextInt(SCREEN_WIDTH - TILE_SIZE*2);
                enemies.add(new Enemy(x, this));
                timer = 0;
            }

            // enemy update
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).update();
                // player bullets intersects enemy
                for (int k = 0; k < player.bullets.size(); k++) {
                    if (enemies.get(i).intersects(player.bullets.get(k))) {
                        playSE(5);
                        player.bullets.remove(player.bullets.get(k));
                        enemies.get(i).state = "hit";
                        enemies.get(i).hp--;
                        score += 20;
                    }
                }
                // enemy bullets intersects player
                for (int j = 0; j < enemies.get(i).enemyBullets.size(); j++) {
                    if(player.intersects(enemies.get(i).enemyBullets.get(j))) {
                        enemies.get(i).enemyBullets.remove(enemies.get(i).enemyBullets.get(j));
                        if(player.hitable) {
                            playSE(8);
                            player.hitable = false;
                            player.hp--;
                        }
                    }
                }
                // delete enemy
                if (enemies.get(i).y > SCREEN_HEIGHT || enemies.get(i).hp < 1) {
                    playSE(7);
                    enemies.remove(enemies.get(i));
                    score += 150;
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        if(gameState == TITLE_STATE || gameState == OVER_STATE) ui.draw(g2);
        if(gameState == PLAY_STATE) {
            // draw background
            ui.draw(g2);

            player.draw(g2);
            lWall.draw(g2);
            rWall.draw(g2);
            for(int i = 0; i < enemies.size(); i++) {
                enemies.get(i).draw(g2);
            }

            // draw ui
            ui.drawPlayScreen();
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