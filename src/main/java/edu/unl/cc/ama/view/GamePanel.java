package edu.unl.cc.ama.view;

import javax.swing.*;
import java.awt.Graphics;

public class GamePanel extends JPanel implements Runnable{
    private int screenWidth;
    private int screenHeigth;
    private int gameState;
    private Thread thread;

    public GamePanel(int screenWidth, int screenHeigth, int gameState, Thread thread) {
        this.screenWidth = screenWidth;
        this.screenHeigth = screenHeigth;
        this.gameState = gameState;
        this.thread = thread;
    }

    public GamePanel(){
    }

    public void setUpGame(){

    }

    public void update(){

    }

    public void startGameThread(){

    }

    public void run(){

    }
    public void paintComponent(Graphics g){

    }
    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeigth() {
        return screenHeigth;
    }

    public void setScreenHeigth(int screenHeigth) {
        this.screenHeigth = screenHeigth;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
