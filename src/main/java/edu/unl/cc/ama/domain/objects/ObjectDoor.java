package edu.unl.cc.ama.domain.objects;


public class ObjectDoor extends Item {
    public ObjectDoor(){
        this.setType(Type.DOOR);
        setCollision(true);
    }
}
