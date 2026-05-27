package edu.unl.cc.ama.domain.objects;

import edu.unl.cc.ama.domain.Entity;
import edu.unl.cc.ama.view.GamePanel;

public class ObjectBoots extends Entity {
    public ObjectBoots(GamePanel gp){
        super(gp);
        name = "Boots";
        direction = "down";
        down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);

    }
}
