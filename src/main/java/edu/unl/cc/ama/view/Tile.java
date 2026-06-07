package edu.unl.cc.ama.view;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image;
    private boolean collision = false;

    public Tile(BufferedImage image, boolean collision) {
        this.image = image;
        this.collision = collision;
    }


    public BufferedImage getImage() {
        return image;
    }

    public boolean isCollision() {
        return collision;
    }
}
