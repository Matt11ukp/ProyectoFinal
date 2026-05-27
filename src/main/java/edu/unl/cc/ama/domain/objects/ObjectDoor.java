package edu.unl.cc.ama.domain.objects;

import edu.unl.cc.ama.domain.Entity;
import edu.unl.cc.ama.view.GamePanel;

public class ObjectDoor extends Entity {
    public ObjectDoor(GamePanel gp){
        super(gp);
        name = "Door";
        direction = "down";
        down1 = setup("/objects/door", gp.tileSize, gp.tileSize);
        collision = true;
    }
}
