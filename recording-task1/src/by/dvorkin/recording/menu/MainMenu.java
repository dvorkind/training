package by.dvorkin.recording.menu;

import java.util.Scanner;

import by.dvorkin.recording.entities.DiskList;
import by.dvorkin.recording.menu.submenu.CurrentDiskMenu;
import by.dvorkin.recording.menu.submenu.LoadFileMenu;
import by.dvorkin.recording.menu.submenu.SelectDiskMenu;
import by.dvorkin.recording.menu.submenu.SongsGeneratingMenu;
import by.dvorkin.recording.utils.MenuUtils;

public class MainMenu {
    public static final Scanner menuScanner = new Scanner(System.in);

    public static void printMenu() {
        System.out.println("\n1. Generate new disk");
        System.out.println("2. Load existing disk");
        System.out.println("3. Show existing disks");
        System.out.println("4. Select a disk from existing ones");
        if (DiskList.getInstance().getCurrentDisk() != null) {
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
                SongsGeneratingMenu.printSubmenu();
                break;
            case "2":
                LoadFileMenu.printSubmenu();
                break;
            case "3":
                MenuUtils.printAllExistingDisk();
                break;
            case "4":
                if (MenuUtils.isAnyDiskOpened()) {
                    MenuUtils.printAllExistingDisk();
                    SelectDiskMenu.printSubmenu();
                }
                break;
            case "5":
                if (MenuUtils.isAnyDiskOpened()) {
                    CurrentDiskMenu.printSubmenu();
                    CurrentDiskMenu.userInput(DiskList.getInstance().getCurrentDisk());
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
