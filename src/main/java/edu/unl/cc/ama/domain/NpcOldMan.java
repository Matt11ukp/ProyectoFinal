package edu.unl.cc.ama.domain;

import edu.unl.cc.ama.view.GamePanel;

import java.util.Random;

public class NpcOldMan extends Entity {

    private static final Random RANDOM = new Random();

    public NpcOldMan(GamePanel gp) {
        super(gp);

        setDirection("down");
        setSpeed(1);
        setType(EntityType.NPC);

        loadImages();
        loadDialogues();
    }

    private void loadImages() {
        down1  = setup("/npc/oldman_down_1",  gp.tileSize, gp.tileSize);
        down2  = setup("/npc/oldman_down_2",  gp.tileSize, gp.tileSize);
        left1  = setup("/npc/oldman_left_1",  gp.tileSize, gp.tileSize);
        left2  = setup("/npc/oldman_left_2",  gp.tileSize, gp.tileSize);
        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
        up1    = setup("/npc/oldman_up_1",    gp.tileSize, gp.tileSize);
        up2    = setup("/npc/oldman_up_2",    gp.tileSize, gp.tileSize);
    }

    private void loadDialogues() {
        setDialogue(0, "Hola, aventurero");
        setDialogue(1, "¿Deseas encontrar el tesoro perdido de esta isla?");
        setDialogue(2, "Puedes moverte con A, W, S, D o con las flechas");
        setDialogue(3, "Con ESC puedes pausar el juego");
        setDialogue(4, "Con Enter atacarás a enemigos y con Espacio podrás hacer un dash");
        setDialogue(5, "Ten cuidado con los slimes — aunque te pueden dar objetos útiles");
        setDialogue(6, "¡Buena suerte!");
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
    public void speak() {
        super.speak();
    }
}
