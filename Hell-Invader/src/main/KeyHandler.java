package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean up, down, left, right, boost;
    public boolean shoot;
    public boolean hitbox = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // TITLE STATE
        if(gp.gameState == gp.TITLE_STATE) {

            if(code == KeyEvent.VK_ENTER) {
                gp.setupGame();
                gp.timerAlive.createTimer();
                gp.gameState = gp.PLAY_STATE;
            }
            if(code == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        }

        // PLAY STATE
        if(gp.gameState == gp.PLAY_STATE) {

            if(code == KeyEvent.VK_UP) {
                up = true;
            }
            if(code == KeyEvent.VK_DOWN) {
                down = true;
            }
            if(code == KeyEvent.VK_LEFT) {
                left = true;
            }
            if(code == KeyEvent.VK_RIGHT) {
                right = true;
            }
            if(code == KeyEvent.VK_SPACE) {
                boost = true;
            }
            if(code == KeyEvent.VK_SHIFT) {
                shoot = true;
            }
            if(code == KeyEvent.VK_O) {
                if(!hitbox) {
                    hitbox = true;
                }
                else if(hitbox = true) {
                    hitbox = false;
                }
            }
        }

        // GAME OVER STATE
        if(gp.gameState == gp.OVER_STATE) {
            if(code == KeyEvent.VK_ENTER) {
                gp.setupGame();
                gp.timerAlive.createTimer();
                gp.gameState = gp.PLAY_STATE;
            }
            if(code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.TITLE_STATE;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_UP) {
            up = false;
        }
        if(code == KeyEvent.VK_DOWN) {
            down = false;
        }
        if(code == KeyEvent.VK_LEFT) {
            left = false;
        }
        if(code == KeyEvent.VK_RIGHT) {
            right = false;
        }
        if(code == KeyEvent.VK_SPACE) {
            boost = false;
        }
        if(code == KeyEvent.VK_SHIFT) {
            shoot = false;
        }
    }
}