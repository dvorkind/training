package by.dvorkin.recording.main;

import by.dvorkin.recording.creator.MenuCreator;
import by.dvorkin.recording.entities.DiskListImpl;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new MenuCreator(DiskListImpl.getInstance(), scanner);
        scanner.close();
    }
}
