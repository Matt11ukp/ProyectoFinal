package edu.unl.cc.ama.domain.objects;

public class ObjectChest extends Item {
    public ObjectChest(){
        this.setType(Type.CHEST);
        setCollision(true);
    }
}
