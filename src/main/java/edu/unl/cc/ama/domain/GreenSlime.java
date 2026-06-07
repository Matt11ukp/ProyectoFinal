package edu.unl.cc.ama.domain;

import edu.unl.cc.ama.view.GamePanel;

import java.util.Random;

public class GreenSlime extends Entity {

    private static final Random RANDOM = new Random();

    public GreenSlime(GamePanel gp) {
        super(gp);

        setName("Green Slime");
        setSpeed(1);
        setMaxLife(4);
        setLife(getMaxLife());
        setDirection("down");
        setType(EntityType.MONSTER);

        solidArea.x      = 3;
        solidArea.y      = 18;
        solidArea.width  = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        loadImages();
    }

    private void loadImages() {
        up1    = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        up2    = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        down1  = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        down2  = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        left1  = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        left2  = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
    }

    @Override
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter >= 120) {
            int roll = RANDOM.nextInt(100) + 1;
            if      (roll <= 25)  setDirection("up");
            else if (roll <= 50)  setDirection("down");
            else if (roll <= 75)  setDirection("left");
            else                  setDirection("right");
            actionLockCounter = 0;
        }
    }

    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        setDirection(gp.player.getDirection());
    }
}
