package edu.unl.cc.ama.domain;

public class User {
    private String name;
    private int age;
    private int schoolGrade;

    public User(String name, int age, int schoolGrade) {
        this.name = name;
        this.age = age;
        this.schoolGrade = schoolGrade;
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
