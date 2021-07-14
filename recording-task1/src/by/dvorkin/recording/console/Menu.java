package by.dvorkin.recording.console;

import java.util.ArrayList;
import java.util.List;
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
        System.out.println("8. Find songs by duration");
        System.out.println("0. Exit");
        System.out.print("Please, select an option: ");
    }

    public static List<Track> sortDiskMenu(Disk disk) {
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
        if (!Runner.getCurrentDisk().isDiskEmpty()) {
            System.out.println("\nDISK NAME [" + Runner.getCurrentDisk().getName() + "] (sorted by name)");
            MenuUtils.printTracklist(Runner.getCurrentDisk().getTracklist());
        }
    }

    public static void selectDiskMenu() {
        while (true) {
            System.out.print("\nEnter the number of disk: ");
            if (submenuScanner.hasNextInt()) {
                int reqDiskNumber = submenuScanner.nextInt();
                if (reqDiskNumber <= Runner.getDiskList().size()) {
                    Runner.setCurrentDisk(Runner.getDiskList().get(reqDiskNumber - 1));
                    System.out.println(
                            "\nCURRENT OPEN DISK NAME [" + Runner.getCurrentDisk().getName() + "] (sorted by name)");
                    MenuUtils.printTracklist(Runner.getCurrentDisk().getTracklist());
                    break;
                } else {
                    System.out.println("\n\tWrong disk number!");
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                submenuScanner.next();
            }
        }
    }

    public static List<Track> findByDurationMenu() {
        List<Track> foundTracklist = new ArrayList<>();
        Track maxTrack = Runner.getCurrentDisk().getTracklist().stream()
                .max((fc1, fc2) -> fc1.getTrackDuration() - fc2.getTrackDuration()).get();
        Track minTrack = Runner.getCurrentDisk().getTracklist().stream()
                .min((fc1, fc2) -> fc1.getTrackDuration() - fc2.getTrackDuration()).get();
        int minDuration = minTrack.getTrackDuration();
        int maxDuration = maxTrack.getTrackDuration();
        while (true) {
            System.out.print("\nEnter the length of the song in seconds (between " + minDuration + " and " + maxDuration
                    + "): ");
            if (submenuScanner.hasNextInt()) {
                int reqTrackDuration = submenuScanner.nextInt();
                if (minDuration <= reqTrackDuration && reqTrackDuration <= maxDuration) {
                    for (int i = 0; i < Runner.getCurrentDisk().getTracklist().size(); i++) {
                        Track track = Runner.getCurrentDisk().getTracklist().get(i);
                        if (track.getTrackDuration() >= reqTrackDuration) {
                            foundTracklist.add(track);
                        }
                    }
                    System.out.println("\nLIST OF SONGS MATCHING THE CONDITION ");
                    return foundTracklist;
                } else {
                    System.out.println("\n\tThe specified duration is not within the valid range!");
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                submenuScanner.next();
            }
        }
    }

}
