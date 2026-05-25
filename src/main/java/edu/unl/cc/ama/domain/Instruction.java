package edu.unl.cc.ama.domain;

public class Instruction {
    private String visualMessage;
    private int targetScore;
    private double timeLimit;

    public Instruction(String visualMessage, int targetScore, double timeLimit) {
        this.visualMessage = visualMessage;
        this.targetScore = targetScore;
        this.timeLimit = timeLimit;
    }

    public boolean winCondition(int currentScore) {
        return currentScore >= targetScore;
    }

    public String getVisualMessage() {
        return visualMessage;
    }

    public void setVisualMessage(String visualMessage) {
        this.visualMessage = visualMessage;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }

    public double getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(double timeLimit) {
        this.timeLimit = timeLimit;
    }
}
