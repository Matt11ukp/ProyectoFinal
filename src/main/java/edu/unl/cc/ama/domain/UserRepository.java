package edu.unl.cc.ama.domain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static final String FILE_PATH = "data/users.txt";

    public void saveUsers(List<User> users) {
        createDataFolder();

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writeUser(writer, user);
            }
        } catch (IOException e) {
            System.out.println("No se pudieron guardar los usuarios.");
        }
    }

    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            readUsers(reader, users);
        } catch (IOException e) {
            System.out.println("No se pudieron cargar los usuarios.");
        }

        return users;
    }

    private void writeUser(PrintWriter writer, User user) {
        writer.println("nombre: " + user.getName());
        writer.println("edad: " + user.getAge());
        writer.println("grado: " + user.getSchoolGrade());
        writer.println("skin: " + user.getSkinIndex());
        writer.println("cabello: " + user.getHairIndex());
        writer.println("camisa: " + user.getShirtIndex());
        writer.println("ojos: " + user.getEyeIndex());
        writer.println("femenino: " + user.isFemale());
        writer.println("monedas: " + user.getCoins());
        writer.println("llaves: " + user.getKeys());
        writer.println("vida: " + user.getLife());
        writer.println();
    }

    private void readUsers(BufferedReader reader, List<User> users) throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("nombre: ")) {
                User user = readUser(reader, line);

                if (user != null) {
                    users.add(user);
                }
            }
        }
    }

    private User readUser(BufferedReader reader, String nameLine) throws IOException {
        String ageLine = reader.readLine();
        String gradeLine = reader.readLine();
        String skinLine = reader.readLine();
        String hairLine = reader.readLine();
        String shirtLine = reader.readLine();
        String eyeLine = reader.readLine();
        String femaleLine = reader.readLine();
        String coinsLine = reader.readLine();
        String keysLine = reader.readLine();
        String lifeLine = reader.readLine();

        if (ageLine == null || gradeLine == null) {
            return null;
        }

        User user = new User(
                getValue(nameLine),
                parseInt(getValue(ageLine), 0),
                parseInt(getValue(gradeLine), 0)
        );

        user.setSkinIndex(parseInt(getValue(skinLine), 0));
        user.setHairIndex(parseInt(getValue(hairLine), 0));
        user.setShirtIndex(parseInt(getValue(shirtLine), 0));
        user.setEyeIndex(parseInt(getValue(eyeLine), 0));
        user.setFemale(Boolean.parseBoolean(getValue(femaleLine)));

        user.setCoins(parseInt(getValue(coinsLine), 0));
        user.setKeys(parseInt(getValue(keysLine), 0));
        user.setLife(parseInt(getValue(lifeLine), 6));

        return user;
    }

    private String getValue(String line) {
        if (line == null) {
            return "";
        }

        int separatorIndex = line.indexOf(":");

        if (separatorIndex == -1) {
            return "";
        }

        return line.substring(separatorIndex + 1).trim();
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void createDataFolder() {
        File folder = new File("data");

        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}