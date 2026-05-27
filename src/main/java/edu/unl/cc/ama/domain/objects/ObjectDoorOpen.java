package edu.unl.cc.ama.domain.objects;

import edu.unl.cc.ama.domain.Entity;
import edu.unl.cc.ama.view.GamePanel;

public class ObjectDoorOpen extends Entity {
    public ObjectDoorOpen(GamePanel gp){
        super(gp);
        name = "DoorOpen";
        direction = "down";
        down1 = setup("/objects/doorOpen", gp.tileSize, gp.tileSize);
    }
}
