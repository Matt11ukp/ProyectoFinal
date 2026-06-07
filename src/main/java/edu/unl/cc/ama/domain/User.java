package edu.unl.cc.ama.domain;

public class User {
    private String name;
    private int age;
    private int schoolGrade;
    private int skinIndex;
    private int hairIndex;
    private int shirtIndex;
    private int eyeIndex;
    private boolean female;

    private int coins;
    private int keys;
    private int life;
  public User(String name, int age, int schoolGrade) {
    this.name = name;
    this.age = age;
    this.schoolGrade = schoolGrade;

    skinIndex = 0;
    hairIndex = 0;
    shirtIndex = 0;
    eyeIndex = 0;
    female = false;

    coins = 0;
    keys = 0;
    life = 6;
}

public int getSkinIndex() {
    return skinIndex;
}
public void setSkinIndex(int skinIndex) {
    this.skinIndex = skinIndex;
}

public int getHairIndex() {
    return hairIndex;
}

public void setHairIndex(int hairIndex) {
    this.hairIndex = hairIndex;
}

public int getShirtIndex() {
    return shirtIndex;
}

public void setShirtIndex(int shirtIndex) {
    this.shirtIndex = shirtIndex;
}

public int getEyeIndex() {
    return eyeIndex;
}

public void setEyeIndex(int eyeIndex) {
    this.eyeIndex = eyeIndex;
}

public boolean isFemale() {
    return female;
}

public void setFemale(boolean female) {
    this.female = female;
}

public int getCoins() {
    return coins;
}

public void setCoins(int coins) {
    this.coins = coins;
}

public int getKeys() {
    return keys;
}

public void setKeys(int keys) {
    this.keys = keys;
}

public int getLife() {
    return life;
}

public void setLife(int life) {
    this.life = life;
}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSchoolGrade() {
        return schoolGrade;
    }

    public void setSchoolGrade(int schoolGrade) {
        this.schoolGrade = schoolGrade;
    }
}
