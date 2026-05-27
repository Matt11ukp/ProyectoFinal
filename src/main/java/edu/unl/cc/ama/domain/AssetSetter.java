package edu.unl.cc.ama.domain;

import edu.unl.cc.ama.domain.objects.ObjectChest;
import edu.unl.cc.ama.domain.objects.ObjectDoor;
import edu.unl.cc.ama.domain.objects.ObjectKey;
import edu.unl.cc.ama.domain.objects.ObjectPortal;
import edu.unl.cc.ama.view.GamePanel;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    //colocar y crear objetos
    public void setObject(){
        // crear una llave y indicar donde debe aparecer
        int i = 0;
        gp.obj[i] = new ObjectKey(gp);
        gp.obj[i].worldX = 30 * gp.tileSize;
        gp.obj[i].worldY = 29 * gp.tileSize;
        i++;
        gp.obj[i] = new ObjectPortal(gp);
        gp.obj[i].worldX = 30 * gp.tileSize;
        gp.obj[i].worldY = 28 * gp.tileSize;
        i++;
        gp.obj[i] = new ObjectPortal(gp);
        gp.obj[i].worldX = 37 * gp.tileSize;
        gp.obj[i].worldY = 37 * gp.tileSize;
        i++;
        gp.obj[i] = new ObjectDoor(gp);
        gp.obj[i].worldX = 12 * gp.tileSize;
        gp.obj[i].worldY = 22 * gp.tileSize;
        i++;
        gp.obj[i] = new ObjectDoor(gp);
        gp.obj[i].worldX = 8 * gp.tileSize;
        gp.obj[i].worldY = 28 * gp.tileSize;
        i++;
        gp.obj[i] = new ObjectDoor(gp);
        gp.obj[i].worldX = 10 * gp.tileSize;
        gp.obj[i].worldY = 11 * gp.tileSize;
        i++;
        gp.obj[i] = new ObjectDoor(gp);
        gp.obj[i].worldX = 8 * gp.tileSize;
        gp.obj[i].worldY = 17 * gp.tileSize;
        i++;
        gp.obj[i] = new ObjectPortal(gp);
        gp.obj[i].worldX = 26 * gp.tileSize;
        gp.obj[i].worldY = 7 * gp.tileSize;
        i++;
        gp.obj[i] = new ObjectPortal(gp);
        gp.obj[i].worldX = 21 * gp.tileSize;
        gp.obj[i].worldY = 3 * gp.tileSize;
        i++;
        gp.obj[i] = new ObjectChest(gp);
        gp.obj[i].worldX = 10 * gp.tileSize;
        gp.obj[i].worldY = 7 * gp.tileSize;
    }
    public void setNPC(){
        int i = 0;
        gp.npc[i] = new NpcOldMan(gp);
        gp.npc[i].worldX = gp.tileSize * 21;
        gp.npc[i].worldY = gp.tileSize * 21;
    }
    public void setMonster(){
        int i = 0;
        gp.monster[i] = new GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 23;
        gp.monster[i].worldY = gp.tileSize * 36;
        i++;
        gp.monster[i] = new GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 37;
        gp.monster[i].worldY = gp.tileSize * 9;
        i++;
        gp.monster[i] = new GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 11;
        gp.monster[i].worldY = gp.tileSize * 31;
        i++;
        gp.monster[i] = new GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 23;
        gp.monster[i].worldY = gp.tileSize * 4;
        i++;
        gp.monster[i] = new GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 38;
        gp.monster[i].worldY = gp.tileSize * 39;
        gp.monster[i].dropsBoots = true;
    }
}
