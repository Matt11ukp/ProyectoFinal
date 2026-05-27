package edu.unl.cc.ama.view;

import edu.unl.cc.ama.domain.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{
    // configuraciones de la ventana
    public int originalTileSize = 16; // 16x16 de resolucion
    // se debe hacer un escalado para que se pueda ver bien en la pantalla
    public int scale = 3;
    // es como multiplicar 16 x 3, para que se vea mas grande pero con la misma resolucion
    public int tileSize = originalTileSize * scale;
    // tamanio de la pantalla
    public int maxScreenCol = 30; // horizontal
    public int maxScreenRow = 16; // vertical
    public int screenWidth = tileSize * maxScreenCol; // 1440
    public int screenHeight = tileSize * maxScreenRow; // 768

    //Configuraciones del mapa
    public int maxWorldCol = 50;
    public int maxWorldRow = 50;
    // FPS
    int fps = 60;

    public TileManager tileM = new TileManager(this);

    public Key keyH = new Key(this); // nombre de la clase de key
    //Sonido
    Sound soundEfect = new Sound();
    Sound music = new Sound();
    public CollisionCheck cChecker = new CollisionCheck(this);
    public AssetSetter sett = new AssetSetter(this);
    public Ui ui = new Ui(this);
    public Avatar skins = new Avatar(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;// repite el redibujado de la pantalla 60 veces por segundo (FPS)
    // entifsdes y objetos
    public Player player = new Player(this, this.keyH);
    public Entity obj[] = new Entity[20]; // podemos mostrar 10 objetos al mismo tiempo
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[10];
    // poner todas las entiddes en una lista
    // las q tengan la cordenada y menor van primero y los demas despues
    ArrayList<Entity> entityList = new ArrayList<>();

    // Estado del juego
    // dependiendo del estado, se cambia el estado y se dibujan cosas distitnas
    public int gameState;
    public int titleState = 0;
    public int playState = 1;
    public int pauseState = 2;
    public int dialogueState = 3;
    public int winShow = 4;
    public int winState = 5;
    public int lostShow = 6;
    public int lostGame = 7;
    public int skinSelection = 8;
    public int skinHairSelection = 9;
    public int skinShirtSelection = 10;
    public int skinEyesSelection = 11;
    // constructor del panel del juego
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.CYAN);
        this.setDoubleBuffered(true); // doble buffer dibujo graficos primero en memoria y luego a la pantalla, evitando parpadeos
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame(){
        sett.setObject();
        sett.setNPC();
        sett.setMonster();
        gameState = titleState;
        playMusic(SoundName.MENU);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start(); // para que llame al metodo run
    }
    @Override
    public void run() {
        // game Loop
        // Ponerlo en 60 fps
        double drawInterval = (double) 1000000000 /fps; //1 segundo en nanos dividido en 60
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(gameThread != null){
            // actualizar informacion
            update();
            // redibujar la pantalla con la informacion actualizada
            repaint();// llamada a paint component

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void update(){
        if(gameState == playState){
            eHandler.checkEvent();
            player.update();
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }

            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null){
                    if(monster[i].alive && !monster[i].dying){
                        monster[i].update();
                    }
                    if(!monster[i].alive){
                        monster[i] = null;
                    }
                }
            }
        } else if(gameState == winShow){
            player.winCounter++;
            if(player.winCounter > 280){
                gameState = winState;
                player.winCounter = 0;
            }
        } else if(gameState == lostShow){
            player.lostCounter++;
            if(player.lostCounter > 200){
                player.invincible = false;
                gameState = lostGame;
                playMusic(SoundName.DEATH);
                player.lostCounter = 0;
            }
        }

    }
    public void retry(){
        player.setDefaultValues();
        sett.setObject();
        sett.setNPC();
        sett.setMonster();
        gameState = playState;
    }
    // Dibujar
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; // para tener mas control de las formas
        // Debug
        long drawStart = 0;
        if(keyH.checkDrawTime == true){
            drawStart = System.nanoTime();
        }
        // pantalla de menu
        if(gameState == titleState){
            ui.draw(g2);
        } else{
            tileM.draw(g2);
            //agregar las entidades a la lista
            entityList.add(player);
            for(int i = 0; i < npc.length; i ++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
                }
            }
            // ponerlos en orden
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2){
                    int result = Integer.compare(e1.worldY, e2.worldY);

                    return result;

                }
            });
            //dibujar entidades
            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            //borramos la lista ya que si no se hara cada vez mas grande
            entityList.clear();

            ui.draw(g2);
        }

        // debug
        if(keyH.checkDrawTime == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }
        g2.dispose(); //liberar recursos del sistema (Buena practica)
    }
    public void playMusic(SoundName name){
        music.setFile(name);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    //para efectos de sonido
    public void playSE(SoundName name){
        soundEfect.setFile(name);
        soundEfect.play();
    }
}
