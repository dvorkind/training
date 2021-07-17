package by.dvorkin.recording.console.menu;

import java.util.Scanner;

import by.dvorkin.recording.console.Runner;

public class MainMenu {
    static final Scanner menuScanner = new Scanner(System.in);

    public static void printMenu() {
        System.out.println("\n1. Generate disk");
        System.out.println("2. Load existing disk");
        System.out.println("3. Save current disk");
        System.out.println("4. Sort songs by something");
        System.out.println("5. Show existing disks");
        System.out.println("6. Show current disk");
        System.out.println("7. Select a disk from existing ones");
        System.out.println("8. Find songs by duration range");
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
                if (MenuUtils.isAnyDisk()) {
                    SaveFileMenu.printSubmenu(Runner.getCurrentDisk());
                }
                break;
            case "4":
                if (MenuUtils.isAnyDisk()) {
                    SortDiskMenu.printSubmenu(Runner.getCurrentDisk());
                }
                break;
            case "5":
                MenuUtils.printAllExistingDisk();
                break;
            case "6":
                if (MenuUtils.isAnyDisk()) {
                    System.out.println(
                            "\nCURRENT OPEN DISK NAME [" + Runner.getCurrentDisk().getName() + "]");
                    MenuUtils.printTracklist(Runner.getCurrentDisk().getTracklist());
                }
                break;
            case "7":
                if (MenuUtils.isAnyDisk()) {
                    MenuUtils.printAllExistingDisk();
                    SelectDiskMenu.printSubmenu();
                }
                break;
            case "8":
                if (MenuUtils.isAnyDisk()) {
                    FindByDurationMenu.printSubmenu();
                }
                break;
            case "0":
                System.out.println("\n\tProgram completed!");
                break;
            default:
                System.out.println("\n\tWrong command number!");
                break;
            }
            if ("0".equals(userInput)) {
                break;
            } else {
                MainMenu.printMenu();
            }
        }
        menuScanner.close();
    }
}
