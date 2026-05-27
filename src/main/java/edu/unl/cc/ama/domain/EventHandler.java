package edu.unl.cc.ama.domain;

import edu.unl.cc.ama.view.GamePanel;
import edu.unl.cc.ama.view.SoundName;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[col][row] = new EventRect(23, 23, 25, 25);
            eventRect[col][row].x = eventRect[col][row].getEventRectDefaultX();
            eventRect[col][row].y = eventRect[col][row].getEventRectDefaultY();
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }

    }

    public void checkEvent(){
        // ver si el jugafor esta mas de un tile lejos del ultimo evento
        // math.abs retorna valores absolutos (positivos)
        // mat.max agarra dos numeros y da el mas grande, para saber si nos alejamos ya del tile
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        // si el jugador esta 1 tile lejos, puede volver a pasar el evento
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }
        if(canTouchEvent){
            if(gp.player.portalCooldown >= 120){
                if(hit(gp.player, 30, 28, "any") == true){
                    portal(gp.player, 30, 28, 37, 37);
                    gp.player.portalCooldown = 0;
                } else if(hit(gp.player, 37, 37, "any") == true){
                    portal(gp.player, 37, 37, 30, 28);
                    gp.player.portalCooldown = 0;
                } else if(hit(gp.player, 26, 7, "any") == true){
                    portal(gp.player, 26, 7, 21, 3);
                    gp.player.portalCooldown = 0;
                } else if(hit(gp.player, 21, 3, "any") == true){
                    portal(gp.player, 21, 3, 26, 7);
                    gp.player.portalCooldown = 0;
                }
            }
        }
        if(gp.monster[3] != null){
            if(gp.player.portalMonsterCooldown >= 120){
                if(hit(gp.monster[3], 21, 3, "any" ) == true){
                    portal(gp.monster[3], 21, 3, 26, 7);
                    gp.player.portalMonsterCooldown = 0;
                } else if(hit(gp.monster[3], 26, 7, "any" ) == true){
                    portal(gp.monster[3], 26, 7, 21, 3);
                    gp.player.portalMonsterCooldown = 0;
                }
            }
        }
    }
    public void damagePit(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit!";
        gp.player.life -= 1;
        canTouchEvent = false;
    }
    public void portal(Entity entity, int col, int row, int newX, int newY){
        entity.worldX = newX * gp.tileSize;
        entity.worldY = newY * gp.tileSize;
        gp.playSE(SoundName.DASH);
    }

    public boolean hit(Entity entity, int col, int row, String reqDirection){
        boolean hit = false;
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;
        if(entity.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].isEventDone() == false){
            if(entity.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
                previousEventX = entity.worldX;
                previousEventY = entity.worldY;
            }
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].getEventRectDefaultX();
        eventRect[col][row].y = eventRect[col][row].getEventRectDefaultY();
        return hit;
    }
}
