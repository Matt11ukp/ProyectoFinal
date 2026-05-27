package edu.unl.cc.ama.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageGetter {
    GamePanel gp;

    private BufferedImage skin,  whiteSkin, whiteSkinBlock, skinBlock, female, male,
            eyesBlue, eyesBrown, eyesGreen, eyesGreener, eyesHoney, eyesPurple,
            eyesRed, eyesYellow, eyesPink, blondeFemale, blueShortFemale, pinkFemale, whiteFemale,
            brownFemale, blondeJellyfish, pinkJellyfish, greenJellyfish, BlondeShort, pinkShort, femaleHair, lightBlueFemale,
            deepBlueEyes, lightBlueEyes, honeyEyes, darkGreenEyes, blackEyes,
            eyesBlueSky, wolfcutBlue,  wolfcutOrange, wolfcutHoney,skin1, skin1block, skin2, skin2block, skin3, skin3block,
            skin4, skin4block, skin5, skin5block, skin6, skin6block, skin7, skin7block, skin8, skin8block, skin9, skin9block,
            skin10, skin10block;

    private BufferedImage skins[];
    private BufferedImage hairsMale[] = new BufferedImage[15];
    private BufferedImage shirtImages[][] = new BufferedImage[15][10];
    private BufferedImage skinImages[][] = new BufferedImage[12][10];
    private BufferedImage hairMaleImages[][] = new BufferedImage[15][4];
    private BufferedImage hairsFemale[];
    private BufferedImage shirts[] = new BufferedImage[15];
    private BufferedImage eyes[];
    private BufferedImage skinBlocks[];

    public ImageGetter(GamePanel gp){
        this.gp = gp;
        getSkin();
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

    public void getSkin(){
        getImages(15, 4, "Hair", "hair", hairMaleImages);
        getImages(15, 10, "Shirt", "shirt", shirtImages);
        getImages(12, 10, "Skin", "skin", skinImages);
        skin =  setup("/skin/skinMolde", gp.tileSize, gp.tileSize);
        skinBlock = setup("/skin/blockMolde", gp.tileSize, gp.tileSize);
        whiteSkin = setup("/skin/whiteSkin", gp.tileSize, gp.tileSize);
        whiteSkinBlock = setup("/skin/whiteSkinBlock", gp.tileSize, gp.tileSize);
        skin1 = setup("/skin/skin1", gp.tileSize, gp.tileSize);
        skin1block = setup("/skin/skin1block", gp.tileSize, gp.tileSize);
        skin2 = setup("/skin/skin2", gp.tileSize, gp.tileSize);
        skin2block = setup("/skin/skin2block", gp.tileSize, gp.tileSize);
        skin3 = setup("/skin/skin3", gp.tileSize, gp.tileSize);
        skin3block = setup("/skin/skin3block", gp.tileSize, gp.tileSize);
        skin4 = setup("/skin/skin4", gp.tileSize, gp.tileSize);
        skin4block = setup("/skin/skin4block", gp.tileSize, gp.tileSize);
        skin5 = setup("/skin/skin5", gp.tileSize, gp.tileSize);
        skin5block = setup("/skin/skin5block", gp.tileSize, gp.tileSize);
        skin6 = setup("/skin/skin6", gp.tileSize, gp.tileSize);
        skin6block = setup("/skin/skin6block", gp.tileSize, gp.tileSize);
        skin7 = setup("/skin/skin7", gp.tileSize, gp.tileSize);
        skin7block = setup("/skin/skin7block", gp.tileSize, gp.tileSize);
        skin8 = setup("/skin/skin8", gp.tileSize, gp.tileSize);
        skin8block = setup("/skin/skin8block", gp.tileSize, gp.tileSize);
        skin9 = setup("/skin/skin9", gp.tileSize, gp.tileSize);
        skin9block = setup("/skin/skin9block", gp.tileSize, gp.tileSize);
        skin10 = setup("/skin/skin10", gp.tileSize, gp.tileSize);
        skin10block = setup("/skin/skin10block", gp.tileSize, gp.tileSize);
        female = setup("/skin/female", gp.tileSize, gp.tileSize);
        male = setup("/skin/male", gp.tileSize, gp.tileSize);
        blondeFemale = setup("/skin/blondeFemale", gp.tileSize, gp.tileSize);
        blueShortFemale = setup("/skin/blueShortFemale", gp.tileSize, gp.tileSize);
        pinkFemale = setup("/skin/pinkFemale", gp.tileSize, gp.tileSize);
        whiteFemale = setup("/skin/whiteFemale", gp.tileSize, gp.tileSize);
        brownFemale = setup("/skin/brownFemale", gp.tileSize, gp.tileSize);
        blondeJellyfish = setup("/skin/blondeJellyfish", gp.tileSize, gp.tileSize);
        pinkJellyfish = setup("/skin/pinkJellyfish", gp.tileSize, gp.tileSize);
        greenJellyfish = setup("/skin/greenJellyfish", gp.tileSize, gp.tileSize);
        BlondeShort = setup("/skin/BlondeShort", gp.tileSize, gp.tileSize);
        pinkShort = setup("/skin/pinkShort", gp.tileSize, gp.tileSize);
        femaleHair = setup("/skin/femaleHair", gp.tileSize, gp.tileSize);
        lightBlueFemale = setup("/skin/lightBlueFemale", gp.tileSize, gp.tileSize);
        wolfcutBlue = setup("/skin/wolfcutBlue", gp.tileSize, gp.tileSize);
        wolfcutOrange = setup("/skin/wolfcutOrange", gp.tileSize, gp.tileSize);
        wolfcutHoney = setup("/skin/wolfcutHoney", gp.tileSize, gp.tileSize);

        eyesBlue = setup("/skin/eyesBlue", gp.tileSize, gp.tileSize);
        eyesBrown = setup("/skin/eyesBrown", gp.tileSize, gp.tileSize);
        eyesGreen = setup("/skin/eyesGreen", gp.tileSize, gp.tileSize);
        eyesGreener = setup("/skin/eyesGreener", gp.tileSize, gp.tileSize);
        eyesHoney = setup("/skin/eyesHoney", gp.tileSize, gp.tileSize);
        eyesPink = setup("/skin/eyesPink", gp.tileSize, gp.tileSize);
        eyesPurple = setup("/skin/eyesPurple", gp.tileSize, gp.tileSize);
        eyesRed = setup("/skin/eyesRed", gp.tileSize, gp.tileSize);
        eyesYellow = setup("/skin/eyesYellow", gp.tileSize, gp.tileSize);
        deepBlueEyes = setup("/skin/deepBlueEyes", gp.tileSize, gp.tileSize);
        lightBlueEyes = setup("/skin/lightBlueEyes", gp.tileSize, gp.tileSize);
        honeyEyes = setup("/skin/honeyEyes", gp.tileSize, gp.tileSize);
        darkGreenEyes = setup("/skin/darkGreenEyes", gp.tileSize, gp.tileSize);
        blackEyes = setup("/skin/blackEyes", gp.tileSize, gp.tileSize);
        eyesBlueSky = setup("/skin/eyesBlueSky", gp.tileSize, gp.tileSize);

        skins = new BufferedImage[]{skin, whiteSkin, skin1, skin2, skin3, skin4, skin5, skin6, skin7, skin8, skin9, skin10};
        skinBlocks = new BufferedImage[]{skinBlock, whiteSkinBlock, skin1block, skin2block, skin3block, skin4block, skin5block,
                skin6block, skin7block, skin8block, skin9block, skin10block};


        hairsFemale = new BufferedImage[]{blondeFemale, blueShortFemale, pinkFemale, whiteFemale, brownFemale, blondeJellyfish, pinkJellyfish,
                greenJellyfish, BlondeShort, pinkShort, femaleHair, lightBlueFemale, wolfcutBlue, wolfcutHoney, wolfcutOrange};
        for(int i = 0; i < 15; i++){
            hairsMale[i] = hairMaleImages[i][0];
        }
        for(int i = 0; i < 15; i++){
            shirts[i] = shirtImages[i][0];
        }

        eyes = new BufferedImage[]{eyesBlue, eyesBrown, eyesGreen, eyesGreener, eyesHoney, eyesPink, eyesPurple, eyesRed, eyesYellow,
                deepBlueEyes, lightBlueEyes, honeyEyes, darkGreenEyes, blackEyes, eyesBlueSky};
    }

    public void getImages(int images, int directions, String folder, String type, BufferedImage arrayImages[][]){
        for(int i = 0; i < images; i++){
            for(int j = 0; j < directions; j++){
                arrayImages[i][j] = setup("/skin/" + folder + "/" + type + "_" + i + "_" + j, gp.tileSize, gp.tileSize);
            }
        }
    }
    public BufferedImage[] getSkins() {
        return skins;
    }
    public BufferedImage[] getHairsFemale() {
        return hairsFemale;
    }
    public BufferedImage[] getHairsMale() {
        return hairsMale;
    }
    public BufferedImage[] getShirts() {
        return shirts;
    }
    public BufferedImage[] getEyes() {
        return eyes;
    }
    public BufferedImage[] getSkinBlocks() {
        return skinBlocks;
    }
    public BufferedImage getFemale() {
        return female;
    }
    public BufferedImage getMale() {
        return male;
    }
}
