package edu.unl.cc.ama.domain;

import edu.unl.cc.ama.view.GamePanel;

import java.util.Objects;

public class CollisionCheck {
    GamePanel gp;
    public CollisionCheck(GamePanel gp){
        this.gp = gp;
    }
    public void checkTile(Entity entity){
        // los bordes en coordenadas de la hitbox
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        // convertir esos pixeles en las coordenadas (posiciones de la matriz)
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        if(Objects.equals(entity.direction, "up")){
            entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            if(gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()){
                entity.colissionOn = true;
            }
        }
        if(Objects.equals(entity.direction, "down")){
            entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()){
                entity.colissionOn = true;
            }
        }
        if(Objects.equals(entity.direction, "right")){
            entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()){
                entity.colissionOn = true;
            }
        }
        if(Objects.equals(entity.direction, "left")){
            entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].isCollision() || gp.tileM.tile[tileNum2].isCollision()){
                entity.colissionOn = true;
            }
        }
    }

    public int checkObject(Entity entity, boolean player){
        int index = 999;
        for(int i = 0; i < gp.obj.length; i++){
            if(gp.obj[i] != null){
                // ver el area solida de la entitdad
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // obtener la posicion del area solida del objeto
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
                if(Objects.equals(entity.direction, "up")){
                    entity.solidArea.y -= entity.speed;
                }
                if(Objects.equals(entity.direction, "down")){
                    entity.solidArea.y += entity.speed;
                }
                if(Objects.equals(entity.direction, "left")){
                    entity.solidArea.x -= entity.speed;
                }
                if(Objects.equals(entity.direction, "right")){
                    entity.solidArea.x += entity.speed;
                }
                if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                    if(gp.obj[i].collision){
                        entity.colissionOn = true;
                    }
                    if(player){
                        index = i;
                    }
                }
                // resetiamos los valores
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;

            }
        }

        return index;
    }
    public int checkEntity(Entity entity, Entity[] target){
        int index = 999;
        for(int i = 0; i < target.length; i++){
            if(target[i] != null){
                // ver el area solida de la entitdad
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // obtener la posicion del area solida del objeto
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;
                if(Objects.equals(entity.direction, "up")){
                    entity.solidArea.y -= entity.speed;
                }
                if(Objects.equals(entity.direction, "down")){
                    entity.solidArea.y += entity.speed;
                }
                if(Objects.equals(entity.direction, "left")){
                    entity.solidArea.x -= entity.speed;
                }
                if(Objects.equals(entity.direction, "right")){
                    entity.solidArea.x += entity.speed;
                }
                if(entity.solidArea.intersects(target[i].solidArea)){
                    if(target[i] != entity){
                        entity.colissionOn = true;
                        index = i;
                    }
                }
                // resetiamos los valores
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }
    public boolean checkPlayer(Entity entity){
        // ver el area solida de la entitdad
        boolean contactPlayer = false;
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        // obtener la posicion del area solida del objeto
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        if(entity.direction == "up"){
            entity.solidArea.y -= entity.speed;
            //intersects es para checar si dos aareas se estan chocando
        }
        if(entity.direction == "down"){
            entity.solidArea.y += entity.speed;
        }
        if(entity.direction == "left"){
            entity.solidArea.x -= entity.speed;
        }
        if(entity.direction == "right"){
            entity.solidArea.x += entity.speed;
        }
        if(entity.solidArea.intersects(gp.player.solidArea)){
            entity.colissionOn = true;
            contactPlayer = true;
        }
        // resetiamos los valores
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        return contactPlayer;
    }
}
