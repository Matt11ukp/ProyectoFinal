package edu.unl.cc.ama.domain;

import edu.unl.cc.ama.domain.objects.ObjectDoorOpen;
import edu.unl.cc.ama.view.GamePanel;
import edu.unl.cc.ama.view.SoundName;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    public int lostCounter = 0;
    public int winCounter = 0;
    Key keyH;
    boolean hasBoots = false;
    boolean dash = false;
    public int portalCooldown = 120;
    public int portalMonsterCooldown = 120;
    int counter;
    int cooldown = 200;
    public int keyNum = 0;
    // donde se dibuja al jugador en la pantalla
    public int screenX;
    public int screenY;

    public Player(GamePanel gp, Key keyH){
        super(gp); // pasar gp a la clase abstracta entities
        this.gp = gp;
        this.keyH = keyH;
        //indican el centro de la pantalla para el jugador
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2); // resta la mitad de un tile para que quede bien centrado
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32); // hitbox del personaje
        // guardamos los valores default xq luego serzn cambiados
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 36;
        attackArea.height = 36;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }
    public void setDefaultValues(){
        hasBoots = false;
        dash = false;
        keyNum = 0;
        //Posicion del jugador inicial enel mundo
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 25;
        speed = 4;
        direction = "down"; // cualquier direccion
        // estado del jugador
        maxLife = 6; // 3 corazones
        life = maxLife;
    }
    public void getPlayerImage(){
        down = gp.skins.frame(gp.skins.image.getSkins(), gp.skins.image.getShirts(),
                gp.skins.image.getEyes(), gp.skins.image.getHairsMale(), gp.skins.image.getHairsFemale());
        down1 = setup("/player/downMoving1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/downMoving2", gp.tileSize, gp.tileSize);

        left1 = setup("/player/left", gp.tileSize, gp.tileSize);
        left2 = setup("/player/leftMoving", gp.tileSize, gp.tileSize);
        right1 = setup("/player/right", gp.tileSize, gp.tileSize);
        right2 = setup("/player/rightMoving", gp.tileSize, gp.tileSize);
        up = setup("/player/up", gp.tileSize, gp.tileSize);
        up1 = setup("/player/upMoving1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/upMoving2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage(){
        attackUp1 = setup("/player/upAttack1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/player/upAttack2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("/player/attack1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/player/attack2", gp.tileSize, gp.tileSize * 2);
        attackRight1 = setup("/player/rightAttack1", gp.tileSize * 2, gp.tileSize );
        attackRight2 = setup("/player/rightAttack2", gp.tileSize * 2, gp.tileSize );
        attackLeft1 = setup("/player/leftAttack1", gp.tileSize * 2, gp.tileSize );
        attackLeft2 = setup("/player/leftAttack2", gp.tileSize * 2, gp.tileSize );
    }


    // esto se llama 60 veces por segundo ya que esta dentro del loop
    public void update(){
        // System.out.println("Col: " + (worldX / gp.tileSize) + " | Fila: " + (worldY / gp.tileSize));
        if(keyH.shiftPressed == true && hasBoots == true){
            speed = 6;
        } else{
            speed = 4;
        }
        if(cooldown < 200){
            cooldown++;
        }
        if(keyH.spacePressed == true && dash == false && cooldown > 180 ){
            gp.playSE(SoundName.DASH);
            dash = true;
            counter = 0;
            cooldown = 0;
        }
        if(dash == true){
            speed = 20;
            counter++;
            if(counter > 5){
                dash = false;
                counter = 0;
            }
        }
        if(portalCooldown < 200){
            portalCooldown++;
        }
        if(portalMonsterCooldown < 200){
            portalMonsterCooldown++;
        }

        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(attacking == true){
            attacking();
        }else{
            //la actualizacion de los sprites solo se realiza cuando las teclas esten siendo presionadas
            if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true){
                if(keyH.upPressed == false && keyH.downPressed == false && keyH.leftPressed == false && keyH.rightPressed == false && keyH.enterPressed == true){
                    int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                    interactNPC(npcIndex);
                }
                if(keyH.upPressed == true){
                    direction = "up"; // me muevo hacia arriba
                    colissionOn = false;
                    gp.cChecker.checkTile(this);  // checo si es posible moverme hacia esa direccion
                    // chacar la colision de un objeto
                    int objIndex = gp.cChecker.checkObject(this, true);
                    pickUpObject(objIndex);
                    int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                    interactNPC(npcIndex);
                    int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                    contactMonster(monsterIndex);
                    if(colissionOn == false && keyH.enterPressed == false){
                        worldY -= speed; // me muevo si se puede
                    }
                    gp.keyH.enterPressed = false;
                }
                if(keyH.downPressed == true){
                    direction = "down";
                    colissionOn = false;
                    gp.cChecker.checkTile(this);
                    int objIndex = gp.cChecker.checkObject(this, true);
                    pickUpObject(objIndex);
                    int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                    interactNPC(npcIndex);
                    int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                    contactMonster(monsterIndex);
                    if(colissionOn == false && keyH.enterPressed == false){
                        worldY += speed;
                    }
                    gp.keyH.enterPressed = false;
                }
                if(keyH.leftPressed == true){
                    direction = "left";
                    colissionOn = false;
                    gp.cChecker.checkTile(this);
                    int objIndex = gp.cChecker.checkObject(this, true);
                    pickUpObject(objIndex);
                    int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                    interactNPC(npcIndex);
                    int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                    contactMonster(monsterIndex);
                    if(colissionOn == false && keyH.enterPressed == false){
                        worldX -= speed;
                    }
                    gp.keyH.enterPressed = false;
                }
                if(keyH.rightPressed == true){
                    direction = "right";
                    colissionOn = false;
                    gp.cChecker.checkTile(this);
                    int objIndex = gp.cChecker.checkObject(this, true);
                    pickUpObject(objIndex);
                    int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                    interactNPC(npcIndex);
                    int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                    contactMonster(monsterIndex);
                    if(colissionOn == false && keyH.enterPressed == false){
                        worldX += speed;
                    }
                    gp.keyH.enterPressed = false;
                }

                spriteCounter++;
                //velocidad a la que se moveria el personaje
                if(spriteCounter > 10){
                    if(spriteNum == 1){
                        spriteNum = 2;
                    } else if(spriteNum == 2){
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }
        }
    }

    private void attacking() {
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;
            // guardar la posicion actual del persoanej
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            //ajustar la posicion para que sea la del ataque
            switch (direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            //el area de ataque se vuelve el area solida
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);
            // luego de la colision, se vuelve a los datos originales
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    private void damageMonster(int i) {
        if(i != 999){
            if(gp.monster[i].invincible == false){
                gp.playSE(SoundName.HIT_MONSTER);
                gp.monster[i].life -= 1;
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();
                if(gp.monster[i].life <= 0){
                    gp.monster[i].dying = true;
                }

            }
        }
    }
    public void contactMonster(int i) {
        if(i != 999){
            if(invincible == false){
                gp.playSE(SoundName.RECEIVE_DAMAGE);
                life -= 1;
                invincible = true;
                if(life <= 0){
                    gp.stopMusic();
                    gp.playSE(SoundName.DEATH_EFFECT);
                    gp.gameState = gp.lostShow;
                }
            }

        }
    }

    public void interactNPC(int i) {
        if(gp.keyH.enterPressed == true){
            if(i != 999){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            } else {
                gp.playSE(SoundName.SWING);
                attacking = true;
            }
        }
        gp.keyH.enterPressed = false;
    }
    public void pickUpObject(int i){
        if(i != 999){
            String ObjectName = gp.obj[i].name;
            switch(ObjectName){
                case "Key":
                    gp.obj[i] = null;
                    gp.playSE(SoundName.COIN);
                    keyNum++;
                    break;
                case "Boots":
                    gp.playSE(SoundName.POWER_UP);
                    hasBoots = true;
                    gp.obj[i] = null;
                    break;
                case "Door":
                    if(keyNum > 0){
                        int newX = gp.obj[i].worldX;
                        int newY = gp.obj[i].worldY;
                        gp.playSE(SoundName.DOOR);
                        gp.obj[i] = new ObjectDoorOpen(gp);
                        gp.obj[i].worldX = newX;
                        gp.obj[i].worldY = newY;
                        keyNum--;
                        break;
                    }
                    break;
                case "Chest":
                    if(keyNum > 0){
                        gp.playSE(SoundName.FANFARE);
                        keyNum--;
                        gp.gameState = gp.winShow;
                        gp.obj[i] = null;
                    }
                    break;
            }

        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        //Comprobar si alguna tecla esta siendo presionada
        boolean isMoving = keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true;

        // --- 1. SELECCIÓN DE IMAGEN ---
        if(attacking == false){
            if(direction == "up"){
                if(spriteNum == 1){ image = up1; }
                if(spriteNum == 2){ image = up2; }
                if(isMoving == false){ image = up; }
            } else if(direction == "left"){
                if(spriteNum == 1){ image = left1; }
                if(spriteNum == 2){ image = left2; }
                if(isMoving == false){ image = left1; }
            } else if(direction == "right"){
                if(spriteNum == 1){ image = right1; }
                if(spriteNum == 2){ image = right2; }
                if(isMoving == false){ image = right1; }
            } else if(direction == "down"){
                if(spriteNum == 1){ image = down1; }
                if(spriteNum == 2){ image = down2; }
                if(isMoving == false){ image = down; }
            }
        }
        if (attacking == true){
            if(direction == "up"){
                if(spriteNum == 1){ image = attackUp1; }
                if(spriteNum == 2){ image = attackUp2; }
            } else if(direction == "left"){
                if(spriteNum == 1){ image = attackLeft1; }
                if(spriteNum == 2){ image = attackLeft2; }
            } else if(direction == "right"){
                if(spriteNum == 1){ image = attackRight1; }
                if(spriteNum == 2){ image = attackRight2; }
            } else if(direction == "down"){
                if(spriteNum == 1){ image = attackDown1; }
                if(spriteNum == 2){ image = attackDown2; }
            }
        }

        // --- 2. CÁLCULO DE CÁMARA (Para no chocar con paredes invisibles) ---
        int x = screenX;
        int y = screenY;

        if(screenX > worldX){
            x = worldX;
        }
        if(screenY > worldY){
            y = worldY;
        }
        int rightOffset = gp.screenWidth - screenX;
        if(rightOffset > gp.maxWorldCol * gp.tileSize - worldX){
            x = gp.screenWidth - (gp.maxWorldCol * gp.tileSize - worldX);
        }
        int bottomOffset = gp.screenHeight - screenY;
        if(bottomOffset > gp.maxWorldRow * gp.tileSize - worldY){
            y = gp.screenHeight - (gp.maxWorldRow * gp.tileSize - worldY);
        }

        // --- 3. COMPENSACIÓN DEL ESPADAZO ---
        // ¡Ojo aquí! Usamos la "x" y "y" de la cámara, NO screenX directo
        int tempScreenX = x;
        int tempScreenY = y;

        if(attacking == true){
            if(direction == "up"){
                tempScreenY = y - gp.tileSize;
            } else if(direction == "left"){
                tempScreenX = x - gp.tileSize;
            }
        }

        // --- 4. DIBUJAMOS AL JUGADOR ---
        // (Al usar null al final en vez de gp.tileSize, la imagen se dibuja en su tamaño original,
        // ya sea 1 tile normal o 2 tiles de ataque)
        if(invincible == true){
            g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1f));
    }
}
