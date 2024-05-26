package main;

import javax.swing.*;

public class GameFrame {

    public GameFrame() {

        JFrame window = new JFrame("HELL INVADER");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.playMusic(0);
        gp.startGameThread();
    }
}
