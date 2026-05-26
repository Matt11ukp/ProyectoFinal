package edu.unl.cc.ama.domain;

import edu.unl.cc.ama.view.GamePanel;

import java.awt.*;

public class Player extends Entity{
    private int screenX;
    private int screenY;

    public Player(GamePanel gp) {
        super(gp);
        this.gp = gp;
        //indican el centro de la pantalla para el jugador
        screenX = gp.getScreenWidth() / 2 - (gp.getTileSize() / 2); // resta la mitad de un tile para que quede bien centrado
        screenY = gp.getScreenHeight() / 2 - (gp.getTileSize() / 2);
    }

    public void update() {
    }

    public void draw(Graphics2D g2) {
    }
}
