package edu.unl.cc.ama.domain;

public class UserRegistrationForm {

    private String name;
    private String age;
    private String schoolGrade;
    private int selectedField;

    public UserRegistrationForm() {
        name = "";
        age = "";
        schoolGrade = "";
        selectedField = 0;
    }
public void reset() {
    name = "";
    age = "";
    schoolGrade = "";
    selectedField = 0;
}
    public void addCharacter(char character) {
        if (selectedField == 0) {
            addCharacterToName(character);
            return;
        }

        if (selectedField == 1) {
            addCharacterToAge(character);
            return;
        }

        addCharacterToSchoolGrade(character);
    }

    private void addCharacterToName(char character) {
        if (Character.isLetter(character) || character == ' ') {
            name += character;
        }
    }

    private void addCharacterToAge(char character) {
        if (Character.isDigit(character)) {
            age += character;
        }
    }

    private void addCharacterToSchoolGrade(char character) {
        if (Character.isDigit(character)) {
            schoolGrade += character;
        }
    }

    public void deleteCharacter() {
        if (selectedField == 0) {
            name = removeLastCharacter(name);
            return;
        }

        if (selectedField == 1) {
            age = removeLastCharacter(age);
            return;
        }

        schoolGrade = removeLastCharacter(schoolGrade);
    }

    private String removeLastCharacter(String text) {
        if (text.isEmpty()) {
            return text;
        }

        return text.substring(0, text.length() - 1);
    }

    public void selectNextField() {
        selectedField++;

        if (selectedField > 2) {
            selectedField = 0;
        }
    }

    public void selectPreviousField() {
        selectedField--;

        if (selectedField < 0) {
            selectedField = 2;
        }
    }

    public boolean isComplete() {
        return !name.trim().isEmpty()
                && !age.trim().isEmpty()
                && !schoolGrade.trim().isEmpty();
    }

    public User createUser() {
        return new User(
                name.trim(),
                Integer.parseInt(age),
                Integer.parseInt(schoolGrade)
        );
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getSchoolGrade() {
        return schoolGrade;
    }

    public int getSelectedField() {
        return selectedField;
    }
}