package by.dvorkin.recording.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import by.dvorkin.recording.model.Disk;

public class Runner {

    private static Disk currentDisk;
    private static List<Disk> diskList = new ArrayList<>();

    public static Disk getCurrentDisk() {
        return currentDisk;
    }

    public static void setCurrentDisk(Disk currentDisk) {
        Runner.currentDisk = currentDisk;
    }

    public static List<Disk> getDiskList() {
        return diskList;
    }

    public static void addToDiskList(Disk disk) {
        diskList.add(disk);
    }

    public static void userInput() {
        Scanner menuScanner = new Scanner(System.in);
        while (true) {
            String userInput = menuScanner.next();
            switch (userInput) {
            case "1":
                Menu.generateSongsMenu();
                break;
            case "2":
                Menu.loadFileMenu();
                break;
            case "3":
                if (MenuUtils.isAnyDisk()) {
                    Menu.saveFileMenu(currentDisk);
                }
                break;
            case "4":
                if (MenuUtils.isAnyDisk()) {
                    MenuUtils.printTracklist(Menu.sortDiskMenu(currentDisk));
                }
                break;
            case "5":
                MenuUtils.printAllExistingDisk();
                break;
            case "6":
                if (MenuUtils.isAnyDisk()) {
                    System.out.println("\nCURRENT OPEN DISK NAME [" + currentDisk.getName() + "] (sorted by name)");
                    MenuUtils.printTracklist(currentDisk.getTracklist());
                }
                break;
            case "7":
                if (MenuUtils.isAnyDisk()) {
                    MenuUtils.printAllExistingDisk();
                    Menu.selectDiskMenu();
                }
                break;
            case "8":
                if (MenuUtils.isAnyDisk()) {
                    MenuUtils.printTracklist(Menu.findByDurationMenu());
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
                Menu.showMenu();
            }
        }
        menuScanner.close();
        Menu.submenuScanner.close();
    }

    public static void main(String[] args) {
        Menu.showMenu();
        userInput();
    }
}
