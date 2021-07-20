package by.dvorkin.recording.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import by.dvorkin.recording.menu.MainMenu;

public class Runner {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        MainMenu.printMenu();
        MainMenu.userInput();
    }
}
