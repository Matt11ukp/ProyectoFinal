package edu.unl.cc.ama.view;

import edu.unl.cc.ama.domain.Entity;
import edu.unl.cc.ama.domain.objects.ObjectHeart;
import edu.unl.cc.ama.domain.objects.ObjectKey;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Ui {
    Avatar skins;
    GamePanel gp;
    Graphics2D g2;
    Font pixel;
    BufferedImage heartFull, heartHalf, heartBlank, keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue;
    public int commandNumber = 0;
    public int commandNumber1 = 0;

    public Ui(GamePanel gp){
        this.gp = gp;
        // lo hacemos antes del draw para optimizar
        InputStream is = getClass().getResourceAsStream("/fonts/LowresPixel-Regular.otf");
        try{
            pixel = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch(FontFormatException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        // HUD object
        Entity heart = new ObjectHeart(gp);
        heartFull = heart.image;
        heartHalf = heart.image2;
        heartBlank = heart.image3;
        Entity key = new ObjectKey(gp);
        keyImage = key.down1;
    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(pixel);
        g2.setColor(Color.white);
        //Menu
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        if(gp.gameState == gp.playState){
            drawPlayerLife();
            if(gp.player.keyNum > 0){
                drawKeys();
            }
        }
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
            if(gp.player.keyNum > 0){
                drawKeys();
            }
        }
        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
            if(gp.player.keyNum > 0){
                drawKeys();
            }
        }
        if(gp.gameState == gp.winShow){
            gp.stopMusic();
            winShowing();
        }
        if(gp.gameState == gp.winState){
            gp.stopMusic();
            winScreen();
        }
        if(gp.gameState == gp.lostShow){
            lostShowing();
        }
        if(gp.gameState == gp.lostGame){
            lostScreen();
        }
        if(gp.gameState == gp.skinSelection){
            skinCustomization();
        }
        if(gp.gameState == gp.skinHairSelection){
            hairSelection();
        }
        if(gp.gameState == gp.skinShirtSelection){
            shirtSelection();
        }
        if(gp.gameState == gp.skinEyesSelection){
            eyesSelection();
        }
    }
    public void drawKeys(){
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 13;
        g2.drawImage(keyImage, x, y, null);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F));
        // Sombra de las letras
        g2.drawString(gp.player.keyNum + "", x + 50, y + 50 );
    }
    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;
        //Dibujar corazones en blanco
        while(i < gp.player.maxLife / 2){
            g2.drawImage(heartBlank, x, y, null);
            i++;
            // luego d dibujar un corazon, se desplaza x para que el otro corazon se dibuje
            x += gp.tileSize;
        }
        // resetiar
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;
        //Dibujar vida actual
        while(i < gp.player.life){
            g2.drawImage(heartHalf, x, y, null);
            i++;
            // para dibujar corazones enteros
            if(i < gp.player.life){
                g2.drawImage(heartFull, x, y, null);
            }
            i++;
            // luego d dibujar un corazon, se desplaza x para que el otro corazon se dibuje
            x += gp.tileSize;
        }
    }
    public void drawTitleScreen(){
        // Cambiar el color del fondo
        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        //nombre del titulo
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 128F));
        String text = "AMA";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        // Sombra de las letras
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);
        // las letras del titulo
        g2.setColor(Color.pink);
        g2.drawString(text, x, y);
        // Imagen
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize * 2;
        g2.drawImage(gp.skins.image.getSkins()[gp.skins.actualSkin],  x, y, gp.tileSize * 2, gp.tileSize * 2, null);
        g2.drawImage(gp.skins.image.getShirts()[gp.skins.actualShirt],  x, y, gp.tileSize * 2, gp.tileSize * 2, null);
        if(!gp.skins.gender){
            g2.drawImage(gp.skins.image.getHairsMale()[gp.skins.actualHair],  x, y, gp.tileSize * 2, gp.tileSize * 2, null);
        } else{
            g2.drawImage(gp.skins.image.getHairsFemale()[gp.skins.actualHair],  x, y, gp.tileSize * 2, gp.tileSize * 2, null);
        }
        g2.drawImage(gp.skins.image.getEyes()[gp.skins.actualEye],  x, y, gp.tileSize * 2, gp.tileSize * 2, null);
        // El menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "NUEVO JUEGO";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if(commandNumber == 0){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.drawString(">", x - gp.tileSize, y - 5);
        }
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "PERSONALIZAR AVATAR";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        if(commandNumber == 1){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.drawString(">", x - gp.tileSize, y - 5);
        }
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "SALIR";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        if(commandNumber == 2){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.drawString(">", x - gp.tileSize, y - 5);
        }
    }
    public void drawDialogueScreen() {
        //ventana de dialogo
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32));
        x += gp.tileSize;
        y += gp.tileSize;
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }

    }
    public void drawSubWindow(int x, int y, int width, int height){
        // El cuarto numero indica transparencia
        Color c = new Color(0, 0, 0, 210); // rgb en negro
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height,35, 35);
        c = new Color(255, 255, 255, 210); // blanco
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
    public void drawPauseScreen(){
        // Cambiar el color del fondo
        g2.setColor(new Color(0, 0, 0, 210));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        //nombre del titulo
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 128F));
        String text = "!!PAUSADO!!";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        // Sombra de las letras
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);
        // las letras del titulo
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        // El menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "VOLVER AL JUEGO";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if(commandNumber == 0){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.drawString(">", x - gp.tileSize, y - 5);
        }
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "MENU PRINCIPAL";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        if(commandNumber == 1){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.drawString(">", x - gp.tileSize, y - 5);
        }
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "SALIR";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        if(commandNumber == 2){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.drawString(">", x - gp.tileSize, y - 5);
        }
    }
    // metodo para obtener la X centrada
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
    public void winScreen(){
        // Cambiar el color del fondo
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        //nombre del titulo
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 128F));
        String text = "!!HAS GANADO!!";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        // Sombra de las letras
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);
        // las letras del titulo
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        // El menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "INTENTAR OTRA VEZ";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if(commandNumber == 0){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.drawString(">", x - gp.tileSize, y - 5);
        }
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "MENU PRINCIPAL";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        if(commandNumber == 1){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.drawString(">", x - gp.tileSize, y - 5);
        }
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "SALIR";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        if(commandNumber == 2){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.drawString(">", x - gp.tileSize, y - 5);
        }
    }
    public void winShowing(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100F));
        String text = "!!HAS ABIERTO EL TESORO!!";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        // Sombra de las letras
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);
        // las letras del titulo
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
    }
    public void lostShowing(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100F));
        String text = "!!TE HAN MATADO!!";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        // Sombra de las letras
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);
        // las letras del titulo
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
    }
    public void lostScreen(){
        // Cambiar el color del fondo
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        //nombre del titulo
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 128F));
        String text = "!!HAS PERDIDO!!";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        // Sombra de las letras
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);
        // las letras del titulo
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        // El menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "INTENTAR OTRA VEZ";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if(commandNumber == 0){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.drawString(">", x - gp.tileSize, y - 5);
        }
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "MENU PRINCIPAL";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        if(commandNumber == 1){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.drawString(">", x - gp.tileSize, y - 5);
        }
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "SALIR";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        if(commandNumber == 2){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.drawString(">", x - gp.tileSize, y - 5);
        }
    }
    public void skinCustomization(){
        // Cambiar el color del fondo

        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));

        String text = "PERSONALIZA TU PERSONAJE";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        g2.setColor(Color.pink);
        g2.drawString(text, x, y);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "PELO";
        g2.drawString(text, gp.tileSize * 5, gp.tileSize * 7);
        text = "CAMISA";
        g2.drawString(text, gp.tileSize * 5, gp.tileSize * 9);
        text = "OJOS";
        g2.drawString(text, gp.tileSize * 5, gp.tileSize * 11);
        text = "COLOR";
        g2.drawString(text, gp.tileSize * 23, gp.tileSize * 5);
        text = "VOLVER";
        g2.drawString(text, gp.tileSize * 14, gp.tileSize * 15);
        if(commandNumber1 != 1){
            if(commandNumber == 0){
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
                g2.drawString(">", gp.tileSize * 4, gp.tileSize * 7);
            }
            if(commandNumber == 1){
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
                g2.drawString(">", gp.tileSize * 4, gp.tileSize * 9);
            }
            if(commandNumber == 2){
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
                g2.drawString(">", gp.tileSize * 4, gp.tileSize * 11);
            }
            if(commandNumber == 3){
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
                g2.drawString(">", gp.tileSize * 12, gp.tileSize * 13);
            }
            if(commandNumber == 4){
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
                g2.drawString("<", gp.tileSize * 18, gp.tileSize * 13);
            }
            if(commandNumber == 5){
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
                g2.drawString(">", gp.tileSize * 13, gp.tileSize * 15);
            }
        }
        g2.drawImage(gp.skins.image.getSkins()[gp.skins.actualSkin], gp.tileSize * 12, gp.tileSize * 4, gp.tileSize * 7, gp.tileSize * 7, null);
        g2.drawImage(gp.skins.image.getShirts()[gp.skins.actualShirt], gp.tileSize * 12, gp.tileSize * 4, gp.tileSize * 7, gp.tileSize * 7, null);
        if(!gp.skins.gender){
            g2.drawImage(gp.skins.image.getHairsMale()[gp.skins.actualHair], gp.tileSize * 12, gp.tileSize * 4, gp.tileSize * 7, gp.tileSize * 7, null);
        } else{
            g2.drawImage(gp.skins.image.getHairsFemale()[gp.skins.actualHair], gp.tileSize * 12, gp.tileSize * 4, gp.tileSize * 7, gp.tileSize * 7, null);
        }
        g2.drawImage(gp.skins.image.getEyes()[gp.skins.actualEye], gp.tileSize * 12, gp.tileSize * 4, gp.tileSize * 7, gp.tileSize * 7, null);
        g2.drawImage(gp.skins.image.getFemale(), gp.tileSize * 13, gp.tileSize * 12, gp.tileSize * 2, gp.tileSize * 2, null);
        g2.drawImage(gp.skins.image.getMale(), gp.tileSize * 16, gp.tileSize * 12, gp.tileSize * 2, gp.tileSize * 2, null);

        gp.skins.skinsMenu(g2);
        if(commandNumber1 == 1){
            gp.skins.selectDrawSkins(g2, gp.skins.skinI);
        }

    }
    public void hairSelection(){
        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.setColor(Color.pink);
        String text = "VOLVER";
        g2.drawString(text, gp.tileSize * 14, gp.tileSize * 15);

        if(commandNumber == 1){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.drawString(">", gp.tileSize * 12, gp.tileSize * 15);
        }
        if(gp.skins.gender == false){
            gp.skins.hairMenuMale(g2);
        } else{
            gp.skins.hairMenuFemale(g2);
        }
        if(commandNumber == 0){
            gp.skins.selectDrawHair(g2, gp.skins.hairI);
        }
    }
    public void shirtSelection(){
        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        String text = "VOLVER";
        g2.setColor(Color.pink);
        g2.drawString(text, gp.tileSize * 14, gp.tileSize * 15);
        if(commandNumber == 0){
            gp.skins.selectDrawShirt(g2, gp.skins.shirtI);
        }
        if(commandNumber == 1){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.drawString(">", gp.tileSize * 12, gp.tileSize * 15);
        }
        gp.skins.shirtMenu(g2);
    }
    public void eyesSelection(){
        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        String text = "VOLVER";
        g2.setColor(Color.pink);
        g2.drawString(text, gp.tileSize * 14, gp.tileSize * 15);
        if(commandNumber == 0){
            gp.skins.selectDrawEyes(g2, gp.skins.eyesI);
        }
        if(commandNumber == 1){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.drawString(">", gp.tileSize * 12, gp.tileSize * 15);
        }
        gp.skins.eyesMenu(g2);
    }
}
