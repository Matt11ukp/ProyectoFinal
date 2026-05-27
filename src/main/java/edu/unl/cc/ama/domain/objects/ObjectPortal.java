package edu.unl.cc.ama.domain.objects;

import edu.unl.cc.ama.domain.Entity;
import edu.unl.cc.ama.view.GamePanel;

public class ObjectPortal extends Entity {
    public ObjectPortal(GamePanel gp){
        super(gp);
        name = "Portal";
        direction = "down";
        down1 = setup("/objects/portal", gp.tileSize, gp.tileSize);
    }
}
