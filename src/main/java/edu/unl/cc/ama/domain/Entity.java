package edu.unl.cc.ama.domain;

import edu.unl.cc.ama.view.GamePanel;

import java.awt.Graphics2D;

public abstract class Entity {
    private int worldX;
    private int worldY;
    private int speed;
    GamePanel gp;

    public Entity(int worldX, int worldY, int speed) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = speed;
    }

    public Entity(GamePanel gp) {
    }

    public void update(){

    }

    public void draw(Graphics2D g2){

    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWolrdY() {
        return worldY;
    }

    public void setWolrdY(int wolrdY) {
        this.worldY = wolrdY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
