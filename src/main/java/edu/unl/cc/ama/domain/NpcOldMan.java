package edu.unl.cc.ama.domain;

import edu.unl.cc.ama.view.GamePanel;

import java.util.Random;

public class NpcOldMan extends Entity{
    public NpcOldMan(GamePanel gp) {
        super(gp);
        this.gp = gp;
        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }
    public void getImage(){
        down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
        up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
    }
    public void setDialogue(){
        dialogues[0] = "Hola, aventurero";
        dialogues[1] = "Deseas Encontrar el tesoro perdido de esta isla??";
        dialogues[2] = "Puedes moverte con A, W, S, D o con las flechas";
        dialogues[3] = "Con ESC puedes pausar el juego";
        dialogues[4] = "Con enter atacaras a enemigos y con espacio podras hacer un dash";
        dialogues[5] = "Tienes que tener cuidado con los slimes\n aunque estos te pueden dar objetos utiles para tu proposito";
        dialogues[6] = "Buena suerte!!";
    }
    public void setAction(){

        actionLockCounter++;
        if(actionLockCounter == 120){
            Random random = new Random();
            // un numero aleatorio de 1 a 100
            int i = random.nextInt(100)+1;
            // sistema de ia muy simple
            // 25% de posibilidad para cada posicion
            if(i <= 25){
                direction = "up";
            }
            if(i > 25 && i <= 50){
                direction = "down";
            }
            if(i > 50 && i <= 75){
                direction = "left";
            }
            if(i >75 && i <= 100){
                direction = "right";
            }
            actionLockCounter = 0;
        }

    }
    public void speak(){
        super.speak();
    }
}
