package edu.unl.cc.ama.domain;

public abstract class Test {
    protected Instruction actualInstruction;
    protected boolean testCompleted;
    protected int currentSuccesses = 0;
    protected int currentMistakes = 0;
    protected double currentTime = 0;

    public Test(Instruction actualInstruction, boolean testCompleted) {
        this.actualInstruction = actualInstruction;
        this.testCompleted = testCompleted;
    }

    public void startTest(){

    }
    public GameResult endTest(){
        return new GameResult(currentSuccesses, currentMistakes, currentTime);
    }
    public Instruction getActualInstruction() {
        return actualInstruction;
    }

    public void setActualInstruction(Instruction actualInstruction) {
        this.actualInstruction = actualInstruction;
    }

    public boolean isTestCompleted() {
        return testCompleted;
    }

    public void setTestCompleted(boolean testCompleted) {
        this.testCompleted = testCompleted;
    }
}
