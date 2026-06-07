package edu.unl.cc.ama.domain;

import edu.unl.cc.ama.domain.objects.ObjectDoorOpen;
import edu.unl.cc.ama.domain.objects.Type;
import edu.unl.cc.ama.view.GamePanel;
import edu.unl.cc.ama.view.GameState;
import edu.unl.cc.ama.view.Key;
import edu.unl.cc.ama.view.SoundName;

import java.awt.Rectangle;

public class Player extends Entity {

    private final Key keyH;

    private boolean hasBoots = false;
    private int keyNum = 0;
    private int coinNum = 0;

    private boolean dash = false;
    private int dashCounter = 0;
    private int cooldown = 200;

    private int portalCooldown = 120;
    private int portalMonsterCooldown = 120;

    private int winCounter  = 0;
    private int lostCounter = 0;

    private final int screenX;
    private final int screenY;

    public Player(GamePanel gp, Key keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth  / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea         = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width  = 36;
        attackArea.height = 36;

        setDefaultValues();

    }

    public void setDefaultValues() {
        hasBoots = false;
        dash     = false;
        keyNum   = 0;
        coinNum = 0;
        setWorldX(gp.tileSize * 23);
        setWorldY(gp.tileSize * 25);
        setSpeed(4);
        setDirection("down");
        setMaxLife(6);
        setLife(getMaxLife());
        setType(EntityType.PLAYER);
    }

    public void getPlayerImage() {
        down  = gp.skins.frame(
                    gp.skins.image.getSkins(),
                    gp.skins.image.getShirts(),
                    gp.skins.image.getEyes(),
                    gp.skins.image.getHairsMale(),
                    gp.skins.image.getHairsFemale());
        down1  = gp.skins.frame(
                gp.skins.image.getSkinDown1(),
                gp.skins.image.getShirtDown1(),
                gp.skins.image.getEyeDown1(),
                gp.skins.image.getMaleDown1(),
                null);
        down2  = gp.skins.frame(
                gp.skins.image.getSkinDown2(),
                gp.skins.image.getShirtDown2(),
                gp.skins.image.getEyeDown2(),
                gp.skins.image.getMaleDown2(),
                null);
        left1  = gp.skins.frame(
                gp.skins.image.getSkinLeft1(),
                gp.skins.image.getShirtLeft1(),
                gp.skins.image.getEyeLeft1(),
                gp.skins.image.getMaleLeft1(),
                null);
        left2  = gp.skins.frame(
                gp.skins.image.getSkinLeft2(),
                gp.skins.image.getShirtLeft2(),
                gp.skins.image.getEyeLeft2(),
                gp.skins.image.getMaleLeft2(),
                null);
        right1 = gp.skins.frame(
                gp.skins.image.getSkinRight1(),
                gp.skins.image.getShirtRight1(),
                gp.skins.image.getEyeRight1(),
                gp.skins.image.getMaleRight1(),
                null);
        right2 = gp.skins.frame(
                gp.skins.image.getSkinRight2(),
                gp.skins.image.getShirtRight2(),
                gp.skins.image.getEyeRight2(),
                gp.skins.image.getMaleRight2(),
                null);
        up = gp.skins.frame(gp.skins.image.getSkinUp(),
                     gp.skins.image.getShirtUp(),
                     gp.skins.image.getEyeUp(),
                     gp.skins.image.getMaleUp(), null);
        up1 = gp.skins.frame(gp.skins.image.getSkinUp1(),
                gp.skins.image.getShirtUp1(),
                gp.skins.image.getEyeUp1(),
                gp.skins.image.getMaleUp1(), null);
        up2 = gp.skins.frame(gp.skins.image.getSkinUp2(),
                gp.skins.image.getShirtUp2(),
                gp.skins.image.getEyeUp2(),
                gp.skins.image.getMaleUp2(), null);
    }

    public void getPlayerAttackImage() {
        attackUp1    = setup("/player/upAttack1",    gp.tileSize,     gp.tileSize * 2);
        attackUp2    = setup("/player/upAttack2",    gp.tileSize,     gp.tileSize * 2);
        attackDown1  = setup("/player/attack1",      gp.tileSize,     gp.tileSize * 2);
        attackDown2  = setup("/player/attack2",      gp.tileSize,     gp.tileSize * 2);
        attackRight1 = setup("/player/rightAttack1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/player/rightAttack2", gp.tileSize * 2, gp.tileSize);
        attackLeft1  = setup("/player/leftAttack1",  gp.tileSize * 2, gp.tileSize);
        attackLeft2  = setup("/player/leftAttack2",  gp.tileSize * 2, gp.tileSize);
    }

    @Override
    public void update() {
        updateSpeed();
        updateDash();
        updatePortalCooldowns();
        updateInvincibility();

        if (isAttacking()) {
            performAttack();
        } else {
            handleMovementInput();
        }
    }

    private void updateSpeed() {
        setSpeed((keyH.shiftPressed && hasBoots) ? 6 : 4);
    }

    private void updateDash() {
        if (cooldown < 200) cooldown++;

        if (keyH.spacePressed && !dash && cooldown > 180) {
            gp.playSE(SoundName.DASH);
            dash        = true;
            dashCounter = 0;
            cooldown    = 0;
        }

        if (dash) {
            setSpeed(20);
            dashCounter++;
            if (dashCounter > 5) {
                dash = false;
                dashCounter = 0;
            }
        }
    }

    private void updatePortalCooldowns() {
        if (portalCooldown        < 200) portalCooldown++;
        if (portalMonsterCooldown < 200) portalMonsterCooldown++;
    }

    private void updateInvincibility() {
        if (isInvincible()) {
            setInvincibleCounter(getInvincibleCounter() + 1);
            if (getInvincibleCounter() > 60) {
                setInvincible(false);
                setInvincibleCounter(0);
            }
        }
    }

    private void handleMovementInput() {
        boolean anyKey = keyH.upPressed || keyH.downPressed
                      || keyH.leftPressed || keyH.rightPressed
                      || keyH.enterPressed;

        if (!anyKey) return;

        if (!keyH.upPressed && !keyH.downPressed
                && !keyH.leftPressed && !keyH.rightPressed
                && keyH.enterPressed) {
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
        }

        if (keyH.upPressed)    moveInDirection("up");
        if (keyH.downPressed)  moveInDirection("down");
        if (keyH.leftPressed)  moveInDirection("left");
        if (keyH.rightPressed) moveInDirection("right");

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum     = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    private void moveInDirection(String dir) {
        setDirection(dir);
        colissionOn = false;
        gp.cChecker.checkTile(this);

        int objIndex     = gp.cChecker.checkObject(this, true);
        int npcIndex     = gp.cChecker.checkEntity(this, gp.npc);
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);

        pickUpObject(objIndex);
        interactNPC(npcIndex);
        contactMonster(monsterIndex);

        if (!colissionOn && !keyH.enterPressed) {
            switch (dir) {
                case "up"    -> worldY -= speed;
                case "down"  -> worldY += speed;
                case "left"  -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }
        gp.keyH.enterPressed = false;
    }

    private void performAttack() {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            int savedX = worldX, savedY = worldY;
            int savedW = solidArea.width, savedH = solidArea.height;

            switch (getDirection()) {
                case "up"    -> worldY -= attackArea.height;
                case "down"  -> worldY += attackArea.height;
                case "left"  -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }
            solidArea.width  = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            // Restaurar hitbox
            worldX = savedX; worldY = savedY;
            solidArea.width = savedW; solidArea.height = savedH;
        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            setAttacking(false);
        }
    }

    private void damageMonster(int i) {
        if (i == 999) return;
        Entity m = gp.monster[i];
        if (!m.isInvincible()) {
            gp.playSE(SoundName.HIT_MONSTER);
            m.decreaseLife(1);
            m.setInvincible(true);
            m.damageReaction();
            if (m.getLife() <= 0) m.setDying(true);
        }
    }

    public void contactMonster(int i) {
        if (i == 999) return;
        if (!isInvincible()) {
            gp.playSE(SoundName.RECEIVE_DAMAGE);
            decreaseLife(1);
            setInvincible(true);
            if (getLife() <= 0) {
                gp.stopMusic();
                gp.playSE(SoundName.DEATH_EFFECT);
                gp.gameState = GameState.LOST_SHOW;
            }
        }
    }

    public void interactNPC(int i) {
        if (keyH.enterPressed) {
            if (i != 999) {
                gp.gameState = GameState.DIALOGUE;
                gp.npc[i].speak();
            } else {
                gp.playSE(SoundName.SWING);
                setAttacking(true);
            }
        }
        gp.keyH.enterPressed = false;
    }

    public void pickUpObject(int i) {
        if (i == 999) return;
        Type type = gp.obj[i].getType();
        switch (type) {
            case KEY -> {
                gp.obj[i] = null;
                gp.playSE(SoundName.COIN);
                keyNum++;
            }
            case BOOTS -> {
                gp.playSE(SoundName.POWER_UP);
                hasBoots = true;
                gp.obj[i] = null;
            }
            case DOOR -> {
                if (keyNum > 0) {
                    int nx = gp.obj[i].getWorldX(), ny = gp.obj[i].getWorldY();
                    gp.playSE(SoundName.DOOR);
                    gp.obj[i] = new ObjectDoorOpen();
                    gp.obj[i].setWorldX(nx);
                    gp.obj[i].setWorldY(ny);
                    keyNum--;
                }
            }
            case TEST_PORTAL -> {
                // Objetivo 4: lanza el minijuego desde el Mundo Hub
                gp.launchVisualTest();
                gp.obj[i] = null;    // consume el portal
            }
            case CHEST -> {
                if (keyNum > 0) {
                    gp.playSE(SoundName.FANFARE);
                    keyNum--;
                    gp.gameState = GameState.WIN_SHOW;
                    gp.obj[i] = null;
                }
            }
        }
    }

    public boolean isMoving() {
        return keyH.upPressed || keyH.downPressed
            || keyH.leftPressed || keyH.rightPressed;
    }

    public int getScreenX() { return screenX; }
    public int getScreenY() { return screenY; }
    public int getKeyNum() { return keyNum; }
    public void setKeyNum(int v) { this.keyNum = v; }
    public boolean isHasBoots() { return hasBoots; }
    public int getWinCounter() { return winCounter; }
    public void setWinCounter(int v) { this.winCounter = v; }
    public void incrementWinCounter() { this.winCounter++; }
    public int getLostCounter() { return lostCounter; }
    public void setLostCounter(int v) { this.lostCounter = v; }
    public void incrementLostCounter() { this.lostCounter++; }
    public int getPortalCooldown() { return portalCooldown; }
    public void setPortalCooldown(int v) { this.portalCooldown = v; }
    public int getPortalMonsterCooldown() { return portalMonsterCooldown; }
    public void setPortalMonsterCooldown(int v) { this.portalMonsterCooldown = v; }

    public int getCoinNum() {
        return coinNum;
    }

    public void setCoinNum(int coinNum) {
        this.coinNum = coinNum;
    }
}
