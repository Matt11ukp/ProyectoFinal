package edu.unl.cc.ama.domain;

import edu.unl.cc.ama.view.GamePanel;
import edu.unl.cc.ama.view.SoundName;
import edu.unl.cc.ama.view.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Entity {

    protected GamePanel gp;

    private DeathListener deathListener;

    protected int worldX, worldY;
    protected int speed;
    protected String direction;

    protected BufferedImage down, down1, down2;
    protected BufferedImage up, up1, up2;
    protected BufferedImage left1, left2;
    protected BufferedImage right1, right2;
    protected BufferedImage attackUp1, attackUp2;
    protected BufferedImage attackDown1, attackDown2;
    protected BufferedImage attackLeft1, attackLeft2;
    protected BufferedImage attackRight1, attackRight2;

    protected int spriteCounter = 0;
    protected int spriteNum     = 1;

    protected Rectangle solidArea = new Rectangle(0, 0, 32, 32);
    protected Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    protected int solidAreaDefaultX;
    protected int solidAreaDefaultY;
    protected boolean colissionOn = false;

    private int maxLife;
    private int life;
    private boolean invincible = false;
    private boolean attacking  = false;
    private boolean alive = true;
    private boolean dying = false;
    private boolean dropsBoots = false;
    private int invincibleCounter = 0;
    private int dyingCounter = 0;
    private boolean hpBarOn = false;

    private EntityType type;
    private String name;

    private boolean collision = false;

    private final String[] dialogues = new String[20];
    private int dialogueIndex = 0;

    protected int actionLockCounter = 0;

    protected Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setDeathListener(DeathListener listener) {
        this.deathListener = listener;
    }

    public void setAction() { }
    public void damageReaction() { }

    public void update() {
        setAction();
        colissionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);

        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (type == EntityType.MONSTER && contactPlayer && !gp.player.isInvincible()) {
            gp.playSE(SoundName.RECEIVE_DAMAGE);
            gp.player.decreaseLife(1);
            gp.player.setInvincible(true);
        }

        if (!colissionOn) {
            switch (direction) {
                case "up"    -> worldY -= speed;
                case "down"  -> worldY += speed;
                case "left"  -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum     = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible        = false;
                invincibleCounter = 0;
            }
        }
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) dialogueIndex = 0;
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.getDirection()) {
            case "up"    -> direction = "down";
            case "down"  -> direction = "up";
            case "left"  -> direction = "right";
            case "right" -> direction = "left";
        }
    }

    public void incrementDyingCounter() {
        dyingCounter++;
    }

    public void triggerDeath() {
        dying = false;
        alive = false;
        notifyDeath();
    }

    private void notifyDeath() {
        if (deathListener != null) {
            deathListener.onEntityDied(this, dropsBoots, worldX, worldY);
        }
    }

    protected BufferedImage setup(String imageName, int width, int height) {
        try {
            BufferedImage raw = ImageIO.read(
                getClass().getResourceAsStream(imageName + ".png"));
            return UtilityTool.scaleImage(raw, width, height);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int  getWorldX() { return worldX; }
    public void setWorldX(int v) { this.worldX = v; }
    public int  getWorldY() { return worldY; }
    public void setWorldY(int v) { this.worldY = v; }

    public int getSpeed() { return speed; }
    public void setSpeed(int v) { this.speed = v; }
    public String getDirection() { return direction; }
    public void setDirection(String v) { this.direction = v; }

    public int  getMaxLife() { return maxLife; }
    public void setMaxLife(int v) { this.maxLife = v; }
    public int  getLife() { return life; }
    public void setLife(int v) { this.life = v; }
    public void decreaseLife(int amt) { this.life -= amt; }

    public boolean isInvincible() { return invincible; }
    public void setInvincible(boolean v) { this.invincible = v; }
    public boolean isAttacking() { return attacking; }
    public void setAttacking(boolean v) { this.attacking = v; }
    public boolean isAlive() { return alive; }
    public void setAlive(boolean v) { this.alive = v; }
    public boolean isDying() { return dying; }
    public void setDying(boolean v) { this.dying = v; }
    public boolean isDropsBoots() { return dropsBoots; }
    public void setDropsBoots(boolean v) { this.dropsBoots = v; }

    public int  getInvincibleCounter() { return invincibleCounter; }
    public void setInvincibleCounter(int v) { this.invincibleCounter = v; }
    public int  getDyingCounter() { return dyingCounter; }

    public boolean isHpBarOn() { return hpBarOn; }
    public void setHpBarOn(boolean v) { this.hpBarOn = v; }

    public EntityType getType() { return type; }
    public void setType(EntityType v) { this.type = v; }
    public String getName() { return name; }
    public void setName(String v) { this.name = v; }

    public boolean isCollision() { return collision; }
    public void setCollision(boolean v) { this.collision = v; }
    public Rectangle getSolidArea() { return solidArea; }
    public Rectangle getAttackArea() { return attackArea; }
    public int getSolidAreaDefaultX() { return solidAreaDefaultX; }
    public int getSolidAreaDefaultY() { return solidAreaDefaultY; }
    public boolean isColissionOn() { return colissionOn; }
    public void setColissionOn(boolean v) { this.colissionOn = v; }

    public int getSpriteNum() { return spriteNum; }
    public int getSpriteCounter() { return spriteCounter; }

    public BufferedImage getDown() { return down; }
    public BufferedImage getDown1() { return down1; }
    public BufferedImage getDown2() { return down2; }
    public BufferedImage getUp() { return up; }
    public BufferedImage getUp1() { return up1; }
    public BufferedImage getUp2() { return up2; }
    public BufferedImage getLeft1() { return left1; }
    public BufferedImage getLeft2() { return left2; }
    public BufferedImage getRight1() { return right1; }
    public BufferedImage getRight2() { return right2; }

    public BufferedImage getAttackUp1() { return attackUp1; }
    public BufferedImage getAttackUp2() { return attackUp2; }
    public BufferedImage getAttackDown1() { return attackDown1; }
    public BufferedImage getAttackDown2() { return attackDown2; }
    public BufferedImage getAttackLeft1() { return attackLeft1; }
    public BufferedImage getAttackLeft2() { return attackLeft2; }
    public BufferedImage getAttackRight1() { return attackRight1; }
    public BufferedImage getAttackRight2() { return attackRight2; }

    protected void setDialogue(int index, String text) {
        if (index >= 0 && index < dialogues.length) dialogues[index] = text;
    }
}
