package edu.unl.cc.ama.domain;

import edu.unl.cc.ama.view.GamePanel;
import edu.unl.cc.ama.view.GameState;
import edu.unl.cc.ama.view.SoundName;

import java.awt.Rectangle;

public class EventHandler {

    private final GamePanel gp;
    private final EventRect[][] eventRect;

    private int previousEventX;
    private int previousEventY;
    private boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        for (int col = 0; col < gp.maxWorldCol; col++) {
            for (int row = 0; row < gp.maxWorldRow; row++) {
                eventRect[col][row] = new EventRect(23, 23, 2, 2);
                eventRect[col][row].x = eventRect[col][row].getEventRectDefaultX();
                eventRect[col][row].y = eventRect[col][row].getEventRectDefaultY();
            }
        }
    }

    public void checkEvent() {
        resetCooldownIfFarEnough();
        if (canTouchEvent) {
            checkPortalEvents();
            checkMonsterPortalEvent();
        }
    }

    private void resetCooldownIfFarEnough() {
        int dx = Math.abs(gp.player.getWorldX() - previousEventX);
        int dy = Math.abs(gp.player.getWorldY() - previousEventY);
        if (Math.max(dx, dy) > gp.tileSize) {
            canTouchEvent = true;
        }
    }

    private void checkPortalEvents() {
        if (gp.player.getPortalCooldown() < 120) return;

        if (hit(gp.player, 30, 28, "any")) {
            portal(gp.player, 37, 37); gp.player.setPortalCooldown(0);
        } else if (hit(gp.player, 37, 37, "any")) {
            portal(gp.player, 30, 28); gp.player.setPortalCooldown(0);
        } else if (hit(gp.player, 26, 7, "any")) {
            portal(gp.player, 21, 3);  gp.player.setPortalCooldown(0);
        } else if (hit(gp.player, 21, 3, "any")) {
            portal(gp.player, 26, 7);  gp.player.setPortalCooldown(0);
        }
    }

    private void checkMonsterPortalEvent() {
        Entity m = gp.monster[3];
        if (m == null) return;
        if (gp.player.getPortalMonsterCooldown() < 120) return;

        if (hit(m, 21, 3, "any")) {
            portal(m, 26, 7);  gp.player.setPortalMonsterCooldown(0);
        } else if (hit(m, 26, 7, "any")) {
            portal(m, 21, 3);  gp.player.setPortalMonsterCooldown(0);
        }
    }

    public void portal(Entity entity, int destCol, int destRow) {
        entity.setWorldX(destCol * gp.tileSize);
        entity.setWorldY(destRow * gp.tileSize);
        gp.playSE(SoundName.PORTAL);
        canTouchEvent = false;
    }

    public void damagePit(int col, int row, GameState targetState) {
        gp.gameState = targetState;
        gp.ui.currentDialogue = "¡Caíste en un pozo!";
        gp.player.decreaseLife(1);
        canTouchEvent = false;
    }

    public boolean hit(Entity entity, int col, int row, String reqDirection) {
        boolean hit = false;

        Rectangle eArea = entity.getSolidArea();
        EventRect  eRect = eventRect[col][row];

        eArea.x  = entity.getWorldX() + entity.getSolidAreaDefaultX();
        eArea.y  = entity.getWorldY() + entity.getSolidAreaDefaultY();
        eRect.x  = col * gp.tileSize + eRect.getEventRectDefaultX();
        eRect.y  = row * gp.tileSize + eRect.getEventRectDefaultY();

        if (eArea.intersects(eRect) && !eRect.isEventDone()) {
            String dir = entity.getDirection();
            if (dir.equals(reqDirection) || reqDirection.equals("any")) {
                hit = true;
                previousEventX = entity.getWorldX();
                previousEventY = entity.getWorldY();
            }
        }

        eArea.x = entity.getSolidAreaDefaultX();
        eArea.y = entity.getSolidAreaDefaultY();
        eRect.x = eRect.getEventRectDefaultX();
        eRect.y = eRect.getEventRectDefaultY();

        return hit;
    }
}
