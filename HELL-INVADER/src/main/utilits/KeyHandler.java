package main.utilits;

import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private final GamePanel gp;
    public boolean up, down, left, right, shoot, boost;
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
                gp.init();
                gp.timerAlive.createTimer();
                gp.gameState = gp.PLAY_STATE;
            }
            if(code == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        }
        // PLAY STATE
        if(gp.gameState == gp.PLAY_STATE) {
            toggle(e, true);

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
                gp.init();
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
        toggle(e, false);
    }

    public void toggle(KeyEvent e, boolean pressed) {
        if (e.getKeyCode() == KeyEvent.VK_UP) up = pressed;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down = pressed;
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = pressed;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = pressed;
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) shoot = pressed;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) boost = pressed;
    }
}