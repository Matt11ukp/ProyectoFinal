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
        }
    }

    private void resetCooldownIfFarEnough() {
        int dx = Math.abs(gp.player.getWorldX() - previousEventX);
        int dy = Math.abs(gp.player.getWorldY() - previousEventY);
        if (Math.max(dx, dy) > gp.tileSize) {
            canTouchEvent = true;
        }
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
