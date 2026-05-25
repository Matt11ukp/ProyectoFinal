package edu.unl.cc.ama.domain;

public class GameResult {
    private int successes;
    private int mistakes;
    private double time;

    public GameResult(int successes, int mistakes, double time) {
        this.successes = successes;
        this.mistakes = mistakes;
        this.time = time;
    }
    public void showReport(){
    }

    public int getSuccesses() {
        return successes;
    }

    public void setSuccesses(int successes) {
        this.successes = successes;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
