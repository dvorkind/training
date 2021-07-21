package by.dvorkin.recording.main;

import by.dvorkin.recording.creator.MenuCreator;

import java.util.Scanner;

public class Runner {
    private static Scanner menuScanner = new Scanner(System.in);

    public static Scanner getMenuScanner() {
        return menuScanner;
    }

    public static void main(String[] args) {
        new MenuCreator();
        menuScanner.close();
    }
}
