package edu.unl.cc.ama.view;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    private int gameState;
    private Thread gameThread;
    private int originalTileSize = 16; // 16x16 de resolucion
    // se debe hacer un escalado para que se pueda ver bien en la pantalla
    private int scale = 3;
    // es como multiplicar 16 x 3, para que se vea mas grande pero con la misma resolucion
    private int tileSize = originalTileSize * scale;
    // tamanio de la pantalla
    private int screenWidth = tileSize * 30; // 1440
    private int screenHeight = tileSize * 16; // 768

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.CYAN);
        // doble buffer dibujo graficos primero en memoria y luego a la pantalla, evitando parpadeos
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    public void setUpGame(){

    }

    public void update(){

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run(){
        int fps = 60;
        // cuanto debe durar 1 frame
        double drawInterval = (double) 1000000000 /fps; //1 segundo en nanos dividido en 60
        // momento en el que se dibuja el siguiente fotograma
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(gameThread != null){
            update();
            repaint();
            try {
                // tiempo sobrante
                double remainingTime = nextDrawTime - System.nanoTime();
                // de nanosegundos a milisegundos (para usar Thread.sleep)
                remainingTime = remainingTime/1000000;
                // evitar numeros negativos
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                // duerme el hilo en el tiempo de sobra (para que no vaya muy rapido)
                Thread.sleep((long) remainingTime);
                // nuevo intervalo para saber a que hora se despertara en el proximo bucle
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }
}
