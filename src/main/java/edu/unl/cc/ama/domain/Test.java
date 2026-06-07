package edu.unl.cc.ama.domain;

public abstract class Test {

    protected int successes;
    protected int mistakes;
    protected boolean testCompleted;

    private long startTime;
    private long endTime;

    public Test() {
        successes = 0;
        mistakes = 0;
        testCompleted = false;
    }

    public void startTest() {
        successes = 0;
        mistakes = 0;
        testCompleted = false;
        startTime = System.currentTimeMillis();
        onStart();
    }

    public GameResult endTest() {
        endTime = System.currentTimeMillis();
        testCompleted = true;

        return new GameResult(successes, mistakes, getElapsedTime());
    }

    protected void addSuccess() {
        successes++;
    }

    protected void addMistake() {
        mistakes++;
    }

    private double getElapsedTime() {
        return (endTime - startTime) / 1000.0;
    }

    public boolean isTestCompleted() {
        return testCompleted;
    }

    public int getSuccesses() {
        return successes;
    }

    public int getMistakes() {
        return mistakes;
    }

    protected abstract void onStart();

    public abstract void update();
}