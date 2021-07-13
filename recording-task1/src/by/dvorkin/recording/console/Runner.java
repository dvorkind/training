package by.dvorkin.recording.console;

import java.util.ArrayList;
import java.util.Scanner;

import by.dvorkin.recording.model.Disk;

public class Runner {

    private static Disk currentDisk;
    private static ArrayList<Disk> diskList = new ArrayList<Disk>();

    public static Disk getCurrentDisk() {
        return currentDisk;
    }

    public static void setCurrentDisk(Disk currentDisk) {
        Runner.currentDisk = currentDisk;
    }

    public static ArrayList<Disk> getDiskList() {
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
                Menu.saveFileMenu(currentDisk);
                break;
            case "4":
                Menu.sortDiskMenu(currentDisk);
                MenuUtils.printTracklist(currentDisk);
                break;
            case "5":
                System.out.println("\nTOTAL OPEN (CREATED) DISKS: " + diskList.size());
                MenuUtils.printAllExistingDisk();
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
