package edu.unl.cc.ama.domain;

import java.awt.image.BufferedImage;

public class Avatar {

    private BufferedImage[] skins;

    public Avatar(BufferedImage[] skins) {
        this.skins = skins;
    }

    public void getSkin() {

    }

    public BufferedImage[] getSkins() {
        return skins;
    }

    public void setSkins(BufferedImage[] skins) {
        this.skins = skins;
    }
}
