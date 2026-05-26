package edu.unl.cc.ama.view;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Game{
    public static void main(String[] args) {
        // Crear una ventana
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("AMA");
        // Cambiar el icono de la ventana
        ImageIcon logo = new ImageIcon("imagen.png");
        Image iconImage = logo.getImage();
        window.setIconImage(iconImage); // la asigna
        GamePanel gamepanel = new GamePanel();

        window.add(gamepanel);
        window.pack();// la ventana encajara en los estandares que le hemos dado
        window.setLocationRelativeTo(null); // null indica que siempre se ejecutara al centro
        window.setVisible(true);
        gamepanel.setUpGame();
        gamepanel.startGameThread();
    }
}
