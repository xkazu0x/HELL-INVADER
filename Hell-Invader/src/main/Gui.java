package main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Gui {

    GamePanel gp;
    Graphics2D g2;
    Font strongGamer;

    int timer = 0;

    public Gui(GamePanel gp) {

        this.gp = gp;

        try {

            InputStream is = getClass().getResourceAsStream("/font/TheStrongGamer.ttf");
            strongGamer = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch(IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        if(gp.gameState == gp.TITLE_STATE) {
            drawTitleScreen();
        }

        if(gp.gameState == gp.PLAY_STATE) {
            drawBackground();
        }

        if(gp.gameState == gp.OVER_STATE) {
            drawOverScreen();
        }
    }

    public void drawTitleScreen() {

        // BACKGROUND
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

        // TITLE NAME
        g2.setFont(strongGamer.deriveFont(Font.PLAIN, 80F));
        String text = "HELL";
        int x = getXForCentered(text);
        int y = gp.SCREEN_HEIGHT/2 - gp.SCREEN_HEIGHT/3;
        g2.setColor(new Color(40, 43, 51));
        g2.drawString(text , x, y + gp.TILE_SIZE/2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        text = "INVADER";
        x = getXForCentered(text);
        y = gp.SCREEN_HEIGHT/2 - gp.SCREEN_HEIGHT/4;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text , x, y + gp.TILE_SIZE/2);

        // INSTRUCTIONS
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "AVOID THE WALLS";
        x = getXForCentered(text);
        y = gp.SCREEN_HEIGHT/2 - gp.TILE_SIZE*2 + gp.TILE_SIZE/2;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text , x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "SURVIVE";
        x = getXForCentered(text);
        y += gp.TILE_SIZE;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text , x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "SPREAD THE PAIN";
        x = getXForCentered(text);
        y += gp.TILE_SIZE;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text , x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "ARROWS: MOVEMENT";
        x = getXForCentered(text);
        y += gp.TILE_SIZE*2 - gp.TILE_SIZE/4;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text , x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "SHIFT: SHOOT";
        x = getXForCentered(text);
        y += gp.TILE_SIZE/2;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text , x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "SPACE: BOOST";
        x = getXForCentered(text);
        y += gp.TILE_SIZE/2;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text , x, y);

        // PLAY
        timer++;
        if(timer > 20) {

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
            text = "PRESS ENTER TO PLAY";
            x = getXForCentered(text);
            y = gp.SCREEN_HEIGHT/2 + gp.SCREEN_HEIGHT/3;
            g2.setColor(new Color(228,223,205));
            g2.drawString(text , x, y);
            if(timer > 60) {
                timer = 0;
            }
        }
    }

    public void drawBackground() {

        g2.setFont(strongGamer.deriveFont(Font.PLAIN, 320F));
        String text = String.valueOf(gp.wall.speed - 5);
        int x = getXForCentered(text);
        int y = gp.SCREEN_HEIGHT/2 + gp.TILE_SIZE/2;
        g2.setColor(new Color(40, 43, 51));
        g2.drawString(text, x, y);

        /*
        g2.setFont(strongGamer.deriveFont(Font.PLAIN, 48F));
        text = "SCORE";
        x = getXForCentered(text);
        y += gp.TILE_SIZE*2 - gp.TILE_SIZE/2;
        g2.setColor(new Color(40, 43, 51));
        g2.drawString(text, x, y);

        g2.setFont(strongGamer.deriveFont(Font.PLAIN, 48F));
        text = String.valueOf(gp.score);
        x = getXForCentered(text);
        y += gp.TILE_SIZE;
        g2.setColor(new Color(40, 43, 51));
        g2.drawString(text, x, y);
         */
    }

    public void drawPlayScreen() {

        // timer
        g2.setFont(strongGamer.deriveFont(Font.PLAIN, 20F));
        String text = String.valueOf(gp.timerAlive.time);
        int x = gp.player.x - gp.TILE_SIZE + gp.ORIGINAL_TILE_SIZE/2;
        int y = gp.player.y + gp.TILE_SIZE + gp.TILE_SIZE/2;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text, x, y);

        // hp
        g2.setFont(strongGamer.deriveFont(Font.PLAIN, 20F));
        text = String.valueOf(gp.player.hp);
        x = gp.player.x + gp.TILE_SIZE - gp.ORIGINAL_TILE_SIZE;
        y = gp.player.y - gp.TILE_SIZE/4;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text + "HP", x, y);

        // score
        g2.setFont(strongGamer.deriveFont(Font.PLAIN, 24F));
        text = String.valueOf(gp.score);
        x = 0;
        y = 20;
        g2.setColor(new Color(228,223,205));
        g2.drawString("SCORE: " + text, x, y);
    }

    public void drawOverScreen() {

        // BACKGROUND
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

        // GAME OVER
        g2.setFont(strongGamer.deriveFont(Font.PLAIN, 80F));
        String text = "GAME";
        int x = getXForCentered(text);
        int y = gp.SCREEN_HEIGHT/2 - gp.SCREEN_HEIGHT/3;
        g2.setColor(new Color(40, 43, 51));
        g2.drawString(text , x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        text = "GAME";
        x = getXForCentered(text);
        y += gp.TILE_SIZE + gp.TILE_SIZE/4;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text , x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        text = "OVER";
        x = getXForCentered(text);
        y += gp.TILE_SIZE + gp.TILE_SIZE/4;
        g2.setColor(new Color(40, 43, 51));
        g2.drawString(text , x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        text = "OVER";
        x = getXForCentered(text);
        y += gp.TILE_SIZE + gp.TILE_SIZE/4;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text , x, y);

        // SCORE
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "SCORE: " + gp.score;
        x = getXForCentered(text);
        y = gp.SCREEN_HEIGHT/2 - gp.TILE_SIZE/2;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text , x, y);

        // TIME ALIVE
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "TIME ALIVE: " + gp.timerAlive.time;
        x = getXForCentered(text);
        y += gp.TILE_SIZE/2;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text , x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "YOU LIVE TO DIE";
        x = getXForCentered(text);
        y += gp.TILE_SIZE + gp.TILE_SIZE/2;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text , x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "REBORN";
        x = getXForCentered(text);
        y += gp.TILE_SIZE;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text , x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "AND DIE AGAIN";
        x = getXForCentered(text);
        y += gp.TILE_SIZE;
        g2.setColor(new Color(228,223,205));
        g2.drawString(text , x, y);

        // RETRY
        timer++;
        if(timer > 20) {

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
            text = "PRESS ENTER TO RETRY";
            x = getXForCentered(text);
            y = gp.SCREEN_HEIGHT/2 + gp.SCREEN_HEIGHT/3;
            g2.setColor(new Color(228,223,205));
            g2.drawString(text , x, y);
            if(timer > 60) {
                timer = 0;
            }
        }
    }

    public int getXForCentered(String text) {

        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.SCREEN_WIDTH/2 - textLength/2;
    }
}
