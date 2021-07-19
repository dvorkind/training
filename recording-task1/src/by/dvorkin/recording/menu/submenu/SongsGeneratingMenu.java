package by.dvorkin.recording.menu.submenu;

import java.util.Arrays;

import by.dvorkin.recording.constants.Constants;
import by.dvorkin.recording.entities.Disk;
import by.dvorkin.recording.entities.DiskList;
import by.dvorkin.recording.menu.MainMenu;
import by.dvorkin.recording.utils.TrackListUtils;

public class SongsGeneratingMenu {
    public static void printSubmenu() {
        Disk disk = new Disk();
        DiskList.getInstance().setCurrentDisk(disk);
        DiskList.getInstance().addToDiskList(disk);
        String reqDiskName;
        String[] menuOption = { "1", "2", "3" }; // Array of available options in this menu
        while (true) {
            System.out.print("\nEnter disk name: ");
            reqDiskName = MainMenu.menuScanner.next();
            if (!"".equals(reqDiskName)) {
                break;
            }
        }
        DiskList.getInstance().getCurrentDisk().setName(reqDiskName);
        while (true) {
            System.out.println("\n1. Create empty disk");
            System.out.println("2. Generate the specified number of songs");
            System.out.println("3. Generate songs for a given disc duration");
            System.out.print("Please, select an option: ");
            String userInput = MainMenu.menuScanner.next();
            switch (userInput) {
            case "1":
                System.out.println("\n\tDisc named [" + reqDiskName + "] has been created empty");
                break;
            case "2":
                int reqNumberOfSongs = SongsGeneratingMenu.reqSongsCount();
                System.out.println("\nDISK NAME [" + reqDiskName + "] (sorted by name)");
                TrackListUtils.generateTracksByCount(DiskList.getInstance().getCurrentDisk().getTracklist().getTracks(),
                        reqNumberOfSongs);
                TrackListUtils.printTracklist(DiskList.getInstance().getCurrentDisk().getTracklist());
                break;
            case "3":
                int reqDiskDuration = SongsGeneratingMenu.reqDuration();
                System.out.println("\nDISK NAME [" + reqDiskName + "]");
                TrackListUtils.generateTracksByDiskDuration(
                        DiskList.getInstance().getCurrentDisk().getTracklist().getTracks(), reqDiskDuration);
                TrackListUtils.printTracklist(DiskList.getInstance().getCurrentDisk().getTracklist());
                break;
            default:
                System.out.println("\n\tWrong command number!");
                break;
            }
            if (Arrays.asList(menuOption).contains(userInput)) {
                break;
            }
        }
    }

    public static int reqSongsCount() {
        int songsCount = 0;
        while (songsCount <= 0) {
            System.out.print("Enter the number of songs: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                songsCount = MainMenu.menuScanner.nextInt();
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                MainMenu.menuScanner.next();
            }
        }
        return songsCount;
    }

    public static int reqDuration() {
        int duration;
        while (true) {
            System.out.print("\nEnter disk duration in seconds: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                duration = MainMenu.menuScanner.nextInt();
                if (duration <= Constants.MIN_SONG_DURATION) {
                    System.out.println(
                            "\n\tDisk duration cannot be less than " + Constants.MIN_SONG_DURATION + " seconds!");
                } else {
                    break;
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                MainMenu.menuScanner.next();
            }
        }
        return duration;
    }
}
