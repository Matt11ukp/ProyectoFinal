package edu.unl.cc.ama.domain.objects;

import edu.unl.cc.ama.domain.Entity;
import edu.unl.cc.ama.view.GamePanel;

public class ObjectChest extends Entity {
    public ObjectChest(GamePanel gp){
        super(gp);
        direction = "down";
        name = "Chest";
        down1 = setup("/objects/chest", gp.tileSize, gp.tileSize);
        collision = true;
    }
}
