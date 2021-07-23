package by.dvorkin.recording.main;

import by.dvorkin.recording.creator.MenuCreator;
import by.dvorkin.recording.entities.DiskListImpl;

import java.util.Scanner;

public class Runner {
    private static final Scanner menuScanner = new Scanner(System.in);

    public static Scanner getMenuScanner() {
        return menuScanner;
    }

    public static void main(String[] args) {
        new MenuCreator(DiskListImpl.getInstance());
        menuScanner.close();
    }
}
