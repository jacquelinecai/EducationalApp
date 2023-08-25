package com.example.schoolgame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class User {
    private String firstName, lastName, username, encryptedPass;
    private int points;

    public User() {
        firstName = "";
        lastName = "";
        username = "";
        encryptedPass = "";
        points = 0;
    }

    public User(String firstName, String lastName, String username, String password, int points) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.encryptedPass = Encryption.encrypt(password);
        this.points = points;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getEncryptedPass() {
        return encryptedPass;
    }

    public void setEncryptedPass(String encryptedPass) {
        this.encryptedPass = encryptedPass;
    }

//    public String getPassword() {
//        return Encryption.decrypt(encryptedPass);
//    }

    public static class Encryption {

        private static Map<Character, Character> key;
        private static Map<Character, Character> reverseKey;

        public static String encrypt(String password) {
            key = new HashMap<>();
            reverseKey = new HashMap<>();
            createMap();
            StringBuilder pw = new StringBuilder();
            for (int i = 0; i < password.length(); i++) {
                pw.append(key.get(password.charAt(i)));
            }
            return pw.toString();
        }

        private static void createMap() {
            Set<Character> set = new HashSet<>();
            for (int i = 32; i < 127; i++) {
                set.add((char) i);
            }
            for (int i = 32; i < 127; i++) {
                char first = (char) i;
                int random = (int) (Math.random() * 127 + 32);
                while (!set.contains((char) random)) {
                    random = (int) (Math.random() * 127 + 32);
                }
                char value = (char) random;
                key.put(first, value);
                reverseKey.put(value, first);
                set.remove(value);
            }
        }

        public static String decrypt(String encrypted) {
            StringBuilder pw = new StringBuilder();
            for (int i = 0; i < encrypted.length(); i++) {
                pw.append(reverseKey.get(encrypted.charAt(i)));
            }
            return pw.toString();
        }
    }
}
