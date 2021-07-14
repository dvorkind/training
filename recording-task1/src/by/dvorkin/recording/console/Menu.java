package by.dvorkin.recording.console;

import java.util.ArrayList;
import java.util.Scanner;

import by.dvorkin.recording.model.Disk;
import by.dvorkin.recording.model.DiskUtils;
import by.dvorkin.recording.model.SortBy;
import by.dvorkin.recording.model.Track;

public class Menu {
    static Scanner submenuScanner = new Scanner(System.in);

    public static void showMenu() {
        System.out.println("\n1. Generate disk");
        System.out.println("2. Load existing disk");
        System.out.println("3. Save current disk");
        System.out.println("4. Sort songs by something");
        System.out.println("5. Show existing disks");
        System.out.println("6. Show current disk");
        System.out.println("7. Select a disk from existing ones");
        System.out.println("0. Exit");
        System.out.print("Please, select an option: ");
    }

    public static ArrayList<Track> sortDiskMenu(Disk disk) {
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
                    return MenuUtils.sortSongsBy(disk, sortValue);
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
        MenuUtils.createAndAddNewDiskToList();
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
        MenuUtils.printTracklist(Runner.getCurrentDisk().getTracklist());
    }

    public static void saveFileMenu(Disk disk) {
        String reqSaveFile = "";
        while (reqSaveFile.equals("")) {
            System.out.print("\nEnter file path to save (for example \"D:\\MyDisk.txt\"): ");
            reqSaveFile = submenuScanner.next();
        }
        DiskUtils.saveFile(disk, reqSaveFile);
    }

    public static void loadFileMenu() {
        MenuUtils.createAndAddNewDiskToList();
        System.out.print("\nEnter file path to load (for example \"D:\\MyDisk.txt\"): ");
        String reqOpenFile;
        reqOpenFile = submenuScanner.next();
        DiskUtils.loadFile(Runner.getCurrentDisk(), reqOpenFile);
        System.out.println("\nDISK NAME [" + Runner.getCurrentDisk().getName() + "] (sorted by name)");
        MenuUtils.printTracklist(Runner.getCurrentDisk().getTracklist());
    }

    public static void selectDiskMenu() {
        System.out.print("\nEnter the number of disk: ");
        if (submenuScanner.hasNextInt()) {
            int reqDiskNumber = submenuScanner.nextInt();
            if (reqDiskNumber <= Runner.getDiskList().size()) {
                Runner.setCurrentDisk(Runner.getDiskList().get(reqDiskNumber - 1));
                System.out.println(
                        "\nCUREENT OPEN DISK NAME [" + Runner.getCurrentDisk().getName() + "] (sorted by name)");
                MenuUtils.printTracklist(Runner.getCurrentDisk().getTracklist());
            } else {
                System.out.println("\n\tWrong disk number!");
            }
        } else {
            System.out.println("\n\tOnly numbers can be entered!\n");
            submenuScanner.next();
        }
    }

}
