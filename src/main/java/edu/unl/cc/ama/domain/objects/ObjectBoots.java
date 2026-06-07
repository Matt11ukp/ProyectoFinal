package edu.unl.cc.ama.domain.objects;

import edu.unl.cc.ama.domain.Entity;
import edu.unl.cc.ama.view.GamePanel;

public class ObjectBoots extends Item {
    public ObjectBoots(){
        this.setType(Type.BOOTS);
        setCollision(false);
    }
}
