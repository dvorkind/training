package by.dvorkin.recording.console;

import java.util.Scanner;

import by.dvorkin.recording.model.Disk;
import by.dvorkin.recording.model.DiskUtils;

public class Menu {
    static Scanner submenuScanner = new Scanner(System.in);

    public static void showMenu() {
        System.out.println("\n1. Generate disk");
        System.out.println("2. Load existing disk");
        System.out.println("3. Save current disk");
        System.out.println("4. Sort songs by something");
        System.out.println("5. Show exitsing disks");
        System.out.println("0. Exit");
        System.out.print("Please, select an option: ");
    }

    public static void sortDiskMenu(Disk disk) {
        System.out.println("\n1. Sort songs by genre");
        System.out.println("2. Sort songs by name");
        System.out.println("3. Sort songs by duration");
        while (true) {
            System.out.print("\nPlease select an option: ");
            if (submenuScanner.hasNextInt()) {
                int reqSortNumber = submenuScanner.nextInt();
                if (1 <= reqSortNumber && reqSortNumber <= 3) {
                    SortBy sortValue = SortBy.values()[reqSortNumber - 1];
                    System.out.println("\nDISK NAME [" + disk.getName() + "] " + "(sorted by "
                            + sortValue.toString().toLowerCase() + ")");
                    MenuUtils.sortSongsBy(disk, sortValue);
                    break;
                } else {
                    System.out.println("\n\tWrong sort number!");
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                submenuScanner.next();
            }
        }
    }

    public static void generateSongsMenu() {
        MenuUtils.createAndaddNewDiskToList();
        String reqDiskName = "";
        while (reqDiskName.equals("")) {
            System.out.print("\nEnter disk name: ");
            reqDiskName = submenuScanner.next();
        }
        DiskUtils.setDiskNameFromFilename(Runner.getCurrentDisk(), reqDiskName);
        int reqNumberOfSongs = 0;
        while (reqNumberOfSongs <= 0) {
            System.out.print("Enter the number of songs: ");
            if (submenuScanner.hasNextInt()) {
                reqNumberOfSongs = submenuScanner.nextInt();
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                submenuScanner.next();
            }
        }
        System.out.println("\nDISK NAME [" + reqDiskName + "] (sorted by name)");
        DiskUtils.generateRandomTracks(Runner.getCurrentDisk(), reqNumberOfSongs);
        MenuUtils.printTracklist(Runner.getCurrentDisk());
    }

    public static void saveFileMenu(Disk disk) {
        if (!disk.diskIsEmpty()) {
            String reqSaveFile = "";
            while (reqSaveFile.equals("")) {
                System.out.print("\nEnter file path to save (for example \"D:\\MyDisk.txt\"): ");
                reqSaveFile = submenuScanner.next();
            }
            DiskUtils.saveFile(Runner.getCurrentDisk(), reqSaveFile);
        } else {
            System.out.print("\n\tFirst you need to generate or open the disk! \n");
        }
    }

    public static void loadFileMenu() {
        MenuUtils.createAndaddNewDiskToList();
        System.out.print("\nEnter file path to load (for example \"D:\\MyDisk.txt\"): ");
        String reqOpenFile = "";
        reqOpenFile = submenuScanner.next();
        DiskUtils.loadFile(Runner.getCurrentDisk(), reqOpenFile);
        MenuUtils.printTracklist(Runner.getCurrentDisk());
    }

}
