package edu.unl.cc.ama.domain;

import edu.unl.cc.ama.view.GamePanel;
import edu.unl.cc.ama.view.SoundName;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Key implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, shiftPressed, spacePressed, enterPressed;
    //Debug
    public Boolean checkDrawTime = false;
    public Key(GamePanel gp){
        this.gp = gp;
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // returna el numero de la tecla presionada
        if(gp.gameState == gp.titleState){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){ // Si se presiona la tecla W
                gp.ui.commandNumber--;
                gp.playSE(SoundName.SLIDE);
                if(gp.ui.commandNumber < 0){
                    gp.ui.commandNumber = 2;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                gp.ui.commandNumber++;
                gp.playSE(SoundName.SLIDE);
                if(gp.ui.commandNumber > 2){
                    gp.ui.commandNumber = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                gp.playSE(SoundName.SELECT);
                // Nuevo juego
                if(gp.ui.commandNumber == 0){
                    gp.player.getPlayerImage();
                    gp.retry();
                    gp.stopMusic();
                    gp.playMusic(SoundName.GAME);
                }
                // cpersonaliza skin
                if(gp.ui.commandNumber == 1){
                    gp.gameState = gp.skinSelection;
                    gp.ui.commandNumber = 0;
                }
                // Salir
                if(gp.ui.commandNumber == 2){
                    System.exit(0);
                }
            }
        }
        else if(gp.gameState == gp.winState || gp.gameState == gp.lostGame){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){ // Si se presiona la tecla W
                gp.playSE(SoundName.SLIDE);
                gp.ui.commandNumber--;
                if(gp.ui.commandNumber < 0){
                    gp.ui.commandNumber = 2;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                gp.playSE(SoundName.SLIDE);
                gp.ui.commandNumber++;
                if(gp.ui.commandNumber > 2){
                    gp.ui.commandNumber = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                gp.playSE(SoundName.SELECT);
                // Nuevo juego
                if(gp.ui.commandNumber == 0){
                    gp.retry();
                    gp.playMusic(SoundName.GAME);
                }
                // menu
                if(gp.ui.commandNumber == 1){
                    gp.gameState = gp.titleState;
                    gp.stopMusic();
                    gp.playMusic(SoundName.MENU);
                }
                // Salir
                if(gp.ui.commandNumber == 2){
                    System.exit(0);
                }
            }
        }
        else if(gp.gameState == gp.pauseState){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){ // Si se presiona la tecla W
                gp.playSE(SoundName.SLIDE);
                gp.ui.commandNumber--;
                if(gp.ui.commandNumber < 0){
                    gp.ui.commandNumber = 2;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                gp.playSE(SoundName.SLIDE);
                gp.ui.commandNumber++;
                if(gp.ui.commandNumber > 2){
                    gp.ui.commandNumber = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                gp.playSE(SoundName.SELECT);
                // volver al juego
                if(gp.ui.commandNumber == 0){
                    gp.gameState = gp.playState;
                }
                // menu principal
                if(gp.ui.commandNumber == 1){
                    gp.gameState = gp.titleState;
                    gp.stopMusic();
                    gp.playMusic(SoundName.MENU);
                }
                // Salir
                if(gp.ui.commandNumber == 2){
                    System.exit(0);
                }
            }
        } else if(gp.gameState == gp.skinSelection){
            if(gp.ui.commandNumber1 != 1){
                if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){ // Si se presiona la tecla W
                    gp.playSE(SoundName.SLIDE);
                    gp.ui.commandNumber--;
                    if(gp.ui.commandNumber < 0){
                        gp.ui.commandNumber = 3;
                    }

                }
                if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                    gp.playSE(SoundName.SLIDE);
                    gp.ui.commandNumber++;
                    if(gp.ui.commandNumber > 5){
                        gp.ui.commandNumber = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER){
                    gp.playSE(SoundName.SELECT);
                    if(gp.ui.commandNumber == 0){
                        gp.gameState = gp.skinHairSelection;
                        gp.ui.commandNumber = 0;
                    }
                    if(gp.ui.commandNumber == 1){
                        gp.gameState = gp.skinShirtSelection;
                        gp.ui.commandNumber = 0;
                    }
                    if(gp.ui.commandNumber == 2){
                        gp.gameState = gp.skinEyesSelection;
                        gp.ui.commandNumber = 0;
                    }
                    if(gp.ui.commandNumber == 3){
                        gp.skins.gender = true;
                    }
                    if(gp.ui.commandNumber == 4){
                        gp.skins.gender = false;
                    }
                    if(gp.ui.commandNumber == 5){
                        gp.gameState = gp.titleState;
                        gp.ui.commandNumber = 0;
                    }
                }
                // elegir entre accesorios y colores
                if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
                    gp.playSE(SoundName.SLIDE);
                    gp.ui.commandNumber1++;
                    if(gp.ui.commandNumber1 > 1){
                        gp.ui.commandNumber1 = 0;
                        gp.ui.commandNumber = 0;
                    }
                }
            } else{
                if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
                    gp.playSE(SoundName.SLIDE);
                    gp.ui.commandNumber1--;
                    gp.skins.skinI = 0;
                    if(gp.ui.commandNumber1 < 0){
                        gp.ui.commandNumber1 = 1;
                        gp.ui.commandNumber = 0;
                    }
                }
                if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){ // Si se presiona la tecla W
                    gp.playSE(SoundName.SLIDE);
                    gp.skins.skinI--;
                    if(gp.skins.skinI < 0){
                        gp.skins.skinI = 11;
                    }

                }
                if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                    gp.playSE(SoundName.SLIDE);
                    gp.skins.skinI++;
                    if(gp.skins.skinI > 11){
                        gp.skins.skinI = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER){
                    gp.playSE(SoundName.SLIDE);
                    gp.skins.actualSkin = gp.skins.skinI;
                }
            }
        } else if(gp.gameState == gp.skinHairSelection){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){ // Si se presiona la tecla W
                gp.playSE(SoundName.SLIDE);
                gp.ui.commandNumber--;
                if(gp.ui.commandNumber < 0){
                    gp.ui.commandNumber = 1;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                gp.playSE(SoundName.SLIDE);
                gp.ui.commandNumber++;
                if(gp.ui.commandNumber > 1){
                    gp.ui.commandNumber = 0;
                }
            }
            if(gp.ui.commandNumber == 0){
                if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
                    gp.playSE(SoundName.SLIDE);
                    gp.skins.hairI++;
                    if(gp.skins.hairI > 14){
                        gp.skins.hairI = 0;
                    }
                }
                if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
                    gp.playSE(SoundName.SLIDE);
                    gp.skins.hairI--;
                    if(gp.skins.hairI < 0){
                        gp.skins.hairI = 14;
                    }
                }
            }
            if(code == KeyEvent.VK_ENTER){
                gp.playSE(SoundName.SELECT);
                if(gp.ui.commandNumber == 0){
                    gp.skins.actualHair = gp.skins.hairI;
                } else if(gp.ui.commandNumber == 1){
                    gp.gameState = gp.skinSelection;
                    gp.ui.commandNumber = 0;
                }
            }
        } else if(gp.gameState == gp.skinShirtSelection){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){ // Si se presiona la tecla W
                gp.playSE(SoundName.SLIDE);
                gp.ui.commandNumber--;
                if(gp.ui.commandNumber < 0){
                    gp.ui.commandNumber = 1;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                gp.playSE(SoundName.SLIDE);
                gp.ui.commandNumber++;
                if(gp.ui.commandNumber > 1){
                    gp.ui.commandNumber = 0;
                }                }
            if(gp.ui.commandNumber == 0){
                if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
                    gp.playSE(SoundName.SLIDE);
                    gp.skins.shirtI++;
                    if(gp.skins.shirtI > 14){
                        gp.skins.shirtI = 0;
                    }
                }
                if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
                    gp.playSE(SoundName.SLIDE);
                    gp.skins.shirtI--;
                    if(gp.skins.shirtI < 0){
                        gp.skins.shirtI = 14;
                    }
                }
            }
            if(code == KeyEvent.VK_ENTER){
                gp.playSE(SoundName.SELECT);
                if(gp.ui.commandNumber == 0){
                    gp.skins.actualShirt = gp.skins.shirtI;
                } else if(gp.ui.commandNumber == 1){
                    gp.gameState = gp.skinSelection;
                    gp.ui.commandNumber = 0;
                }
            }
        } else if(gp.gameState == gp.skinEyesSelection){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){ // Si se presiona la tecla W
                gp.playSE(SoundName.SLIDE);
                gp.ui.commandNumber--;
                if(gp.ui.commandNumber < 0){
                    gp.ui.commandNumber = 1;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                gp.playSE(SoundName.SLIDE);
                gp.ui.commandNumber++;
                if(gp.ui.commandNumber > 1){
                    gp.ui.commandNumber = 0;
                }                }
            if(gp.ui.commandNumber == 0){
                if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
                    gp.playSE(SoundName.SLIDE);
                    gp.skins.eyesI++;
                    if(gp.skins.eyesI > 14){
                        gp.skins.eyesI = 0;
                    }
                }
                if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
                    gp.playSE(SoundName.SLIDE);
                    gp.skins.eyesI--;
                    if(gp.skins.eyesI < 0){
                        gp.skins.eyesI = 14;
                    }
                }
            }
            if(code == KeyEvent.VK_ENTER){
                gp.playSE(SoundName.SELECT);
                if(gp.ui.commandNumber == 0){
                    gp.skins.actualEye = gp.skins.eyesI;
                } else if(gp.ui.commandNumber == 1){
                    gp.gameState = gp.skinSelection;
                    gp.ui.commandNumber = 0;
                }
            }
        } else if(gp.gameState == gp.pauseState){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){ // Si se presiona la tecla W
                gp.playSE(SoundName.SLIDE);
                gp.ui.commandNumber--;
                if(gp.ui.commandNumber < 0){
                    gp.ui.commandNumber = 2;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                gp.playSE(SoundName.SLIDE);
                gp.ui.commandNumber++;
                if(gp.ui.commandNumber > 2){
                    gp.ui.commandNumber = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                gp.playSE(SoundName.SELECT);
                // volver al juego
                if(gp.ui.commandNumber == 0){
                    gp.gameState = gp.playState;
                }
                // menu principal
                if(gp.ui.commandNumber == 1){
                    gp.gameState = gp.titleState;
                    gp.stopMusic();
                    gp.playMusic(SoundName.MENU);
                }
                // Salir
                if(gp.ui.commandNumber == 2){
                    System.exit(0);
                }
            }
        } else if(gp.gameState == gp.playState){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){ // Si se presiona la tecla W
                upPressed = true;
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                downPressed = true;
            }
            if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
                leftPressed = true;
            }
            if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
                rightPressed = true;
            }
            if(code == KeyEvent.VK_SHIFT){
                shiftPressed = true;
            }
            if(code == KeyEvent.VK_SPACE){
                spacePressed = true;
            }
            if(code == KeyEvent.VK_T){
                if(checkDrawTime == false){
                    checkDrawTime = true;
                } else if(checkDrawTime){
                    checkDrawTime = false;
                }
            }
            if(code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.pauseState;
            }
            if(code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
        }
        //Pause state
        else if(gp.gameState == gp.pauseState){
            if(code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.playState;
            }
        }
        if(gp.gameState == gp.dialogueState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W ||  code == KeyEvent.VK_UP){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_SHIFT){
            shiftPressed = false;
        }
        if(code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
        if(code == KeyEvent.VK_T){
            spacePressed = false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }
}
