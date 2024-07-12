package main;

import javax.swing.*;

public class GameFrame {
    public GameFrame() {
        ImageIcon icon = new ImageIcon("res/icon/icon.png");

        GamePanel gp = new GamePanel();
        JFrame frame = new JFrame("HELL INVADER");

        frame.setIconImage(icon.getImage());

        frame.add(gp);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gp.playMusic(2);
        gp.start();
    }
}
