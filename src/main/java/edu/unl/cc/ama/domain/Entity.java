package edu.unl.cc.ama.domain;

import java.awt.Graphics2D;

public abstract class Entity {
    private int worldX;
    private int wolrdY;
    private int speed;

    public Entity(int worldX, int wolrdY, int speed) {
        this.worldX = worldX;
        this.wolrdY = wolrdY;
        this.speed = speed;
    }
    // este update iria hacia el de GamePanel, que si tendra el thread para funcionar
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
        return wolrdY;
    }

    public void setWolrdY(int wolrdY) {
        this.wolrdY = wolrdY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
