package edu.unl.cc.ama.domain.objects;

import edu.unl.cc.ama.domain.Entity;
import edu.unl.cc.ama.view.GamePanel;

public class ObjectHeart extends Entity {
    public ObjectHeart(GamePanel gp){
        super(gp);
        name = "Heart";
        image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/heart_blank", gp.tileSize, gp.tileSize);

    }
}
