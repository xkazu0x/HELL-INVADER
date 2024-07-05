package main;

import javax.swing.*;

public class GameFrame {
    public GameFrame() {
        GamePanel gp = new GamePanel();
        JFrame frame = new JFrame("HELL INVADER");
        frame.add(gp);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gp.playMusic(0);
        gp.start();
    }
}
