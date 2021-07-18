package by.dvorkin.recording.console.menu;

import java.util.Scanner;

import by.dvorkin.recording.model.DiskList;

public class MainMenu {
    static final Scanner menuScanner = new Scanner(System.in);

    public static void printMenu() {
        System.out.println("\n1. Generate new disk");
        System.out.println("2. Load existing disk");
        System.out.println("3. Show existing disks");
        System.out.println("4. Select a disk from existing ones");
        if (DiskList.getCurrentDisk() != null) {
            System.out.println("5. Work with current disk");
        }
        System.out.println("0. Exit");
        System.out.print("Please, select an option: ");
    }

    public static void userInput() {
        while (true) {
            String userInput = menuScanner.next();
            switch (userInput) {
            case "1":
                GenerateSongsMenu.printSubmenu();
                break;
            case "2":
                LoadFileMenu.printSubmenu();
                break;
            case "3":
                MenuUtils.printAllExistingDisk();
                break;
            case "4":
                if (MenuUtils.isAnyDisk()) {
                    MenuUtils.printAllExistingDisk();
                    SelectDiskMenu.printSubmenu();
                }
                break;
            case "5":
                if (MenuUtils.isAnyDisk()) {
                    CurrentDiskMenu.printSubmenu();
                    CurrentDiskMenu.userInput(DiskList.getCurrentDisk());
                }
                break;
            case "0":
                System.out.println("\n\tProgram completed!");
                break;
            default:
                System.out.println("\n\tWrong command number!");
                break;
            }
            if (userInput.equals("0")) {
                break;
            } else {
                MainMenu.printMenu();
            }
        }
        menuScanner.close();
    }
}
