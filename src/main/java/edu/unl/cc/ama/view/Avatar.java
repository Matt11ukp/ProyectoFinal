package edu.unl.cc.ama.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Avatar {
    GamePanel gp;
    public ImageGetter image;
    public int hairI = 0;
    public int actualHair;
    public int shirtI = 0;
    public int actualShirt;
    public int skinI = 0;
    public int actualSkin;
    public int eyesI = 0;
    public int actualEye;

    public boolean gender = false;

    public Avatar(GamePanel gp) {
        this.gp = gp;
        image = new ImageGetter(gp);
    }

    public void hairMenuMale(Graphics2D g2){
        int p = 0;
        int multiplierY = 2;
        for(int i = 0; i < image.getHairsMale().length; i++){
            if(p == 5){
                multiplierY = multiplierY + 4;
                p = 0;
            }
            int multiplierX = 2 + (p * 6);
            g2.drawImage(image.getHairsMale()[i], gp.tileSize * multiplierX - 25, gp.tileSize * multiplierY , gp.tileSize * 4, gp.tileSize * 4, null);
            p++;
        }
    }
    public void hairMenuFemale(Graphics2D g2){
        int p = 0;
        int multiplierY = 2;
        for(int i = 0; i < image.getHairsFemale().length; i++){
            if(p == 5){
                multiplierY = multiplierY + 4;
                p = 0;
            }
            int multiplierX = 2 + (p * 6);
            g2.drawImage(image.getHairsFemale()[i], gp.tileSize * multiplierX - 25, gp.tileSize * multiplierY , gp.tileSize * 4, gp.tileSize * 4, null);
            p++;
        }
    }
    public void selectDrawHair(Graphics2D g2, int  hairI){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));

        int multiplierY = 2;
        int posicionX = hairI;
        while(posicionX >= 5){
            multiplierY = multiplierY + 5;
            posicionX -= 5;
        }
        int multiplier = 2 + (posicionX * 6);
        g2.drawString(">", gp.tileSize * multiplier -30, gp.tileSize * multiplierY + 40);

    }

    public void shirtMenu(Graphics2D g2){
        int p = 0;
        int multiplierY = 2;
        for(int i = 0; i < image.getShirts().length; i++){
            if(p == 5){
                multiplierY = multiplierY + 4;
                p = 0;
            }
            int multiplierX = 2 + (p * 6);
            g2.drawImage(image.getShirts()[i], gp.tileSize * multiplierX - 25, gp.tileSize * multiplierY - 100, gp.tileSize * 4, gp.tileSize * 4, null);
            p++;
        }
    }
    public void selectDrawShirt(Graphics2D g2, int  shirtI){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        int multiplierY = 2;
        int posicionX = shirtI;
        while(posicionX >= 5){
            multiplierY = multiplierY + 4;
            posicionX -= 5;
        }
        int multiplier = 2 + (posicionX * 6);
        g2.drawString(">", gp.tileSize * multiplier -50, gp.tileSize * multiplierY + 40);
    }
    public void eyesMenu(Graphics2D g2){
        int p = 0;
        int multiplierY = 2;
        for(int i = 0; i < image.getEyes().length; i++){
            if(p == 5){
                multiplierY = multiplierY + 4;
                p = 0;
            }
            int multiplierX = 2 + (p * 6);
            g2.drawImage(image.getEyes()[i], gp.tileSize * multiplierX - 25, gp.tileSize * multiplierY , gp.tileSize * 4, gp.tileSize * 4, null);
            p++;
        }
    }
    public void selectDrawEyes(Graphics2D g2, int  eyesI){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));

        int multiplierY = 2;
        int posicionX = eyesI;
        while(posicionX >= 5){
            multiplierY = multiplierY + 4;
            posicionX -= 5;
        }
        int multiplier = 2 + (posicionX * 6);
        g2.drawString(">", gp.tileSize * multiplier -30, gp.tileSize * multiplierY + 40);
    }
    public void skinsMenu(Graphics2D g2){
        int p = 0;
        int multiplierY = 5;
        for(int i = 0; i < image.getSkinBlocks().length; i++){
            if(p == 4){
                multiplierY = multiplierY + 2;
                p = 0;
            }
            int multiplierX = 21 + (p * 2);
            g2.drawImage(image.getSkinBlocks()[i], gp.tileSize * multiplierX - 25, gp.tileSize * multiplierY , gp.tileSize * 2, gp.tileSize * 2, null);
            p++;
        }
    }
    public void selectDrawSkins(Graphics2D g2, int  skinsI){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
        int multiplierY = 6;
        int posicionX = skinI;
        while(posicionX >= 4){
            multiplierY = multiplierY + 2;
            posicionX -= 4;
        }
        int multiplier = 21 + (posicionX * 2);
        g2.drawString(">", gp.tileSize * multiplier -25, gp.tileSize * multiplierY);
    }

    public BufferedImage setup(String imageName, int width, int Height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            image = uTool.scaleImage(image, width, Height);
        } catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public BufferedImage avatar(BufferedImage actualskin, BufferedImage actualShirt, BufferedImage actualEye, BufferedImage actualHair, int width, int height){
        BufferedImage avatar = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D pincel = avatar.createGraphics();
        pincel.drawImage(actualskin, 0, 0, width, height, null);
        pincel.drawImage(actualShirt, 0, 0, width, height, null);
        pincel.drawImage(actualEye, 0, 0, width, height, null);
        pincel.drawImage(actualHair, 0, 0, width, height, null);
        pincel.dispose();
        return avatar;
    }
    public BufferedImage frame(BufferedImage[] skins, BufferedImage[] shirts, BufferedImage[] eyes, BufferedImage[] hairsMale, BufferedImage[] hairsFemale){
        BufferedImage actualHair;
        if(!gp.skins.gender){
            actualHair = hairsMale[gp.skins.actualHair];
        } else{
            actualHair = hairsFemale[gp.skins.actualHair];
        }
        BufferedImage actualShirt = shirts[gp.skins.actualShirt];
        BufferedImage actualEye =  eyes[gp.skins.actualEye];
        BufferedImage actualSkin = skins[gp.skins.actualSkin];
        return avatar(actualSkin, actualShirt, actualEye, actualHair, gp.tileSize, gp.tileSize);
    }

}
