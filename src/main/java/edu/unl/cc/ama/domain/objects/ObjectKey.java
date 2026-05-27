package edu.unl.cc.ama.domain.objects;

import edu.unl.cc.ama.domain.Entity;
import edu.unl.cc.ama.view.GamePanel;

public class ObjectKey extends Entity {
    public ObjectKey(GamePanel gp){
        super(gp);
        direction = "down";
        name = "Key";
        down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
    }
}
