package by.dvorkin.recording.console.menu;

import by.dvorkin.recording.model.Constants;
import by.dvorkin.recording.model.Disk;
import by.dvorkin.recording.model.DiskList;
import by.dvorkin.recording.model.DiskUtils;

public class GenerateSongsMenu {
    public static void printSubmenu() {
        Disk disk = new Disk();
        DiskList.setCurrentDisk(disk);
        DiskList.addToDiskList(disk);
        String reqDiskName = "";
        while (reqDiskName.equals("")) {
            System.out.print("\nEnter disk name: ");
            reqDiskName = MainMenu.menuScanner.next();
        }
        DiskUtils.setDiskNameFromFilename(DiskList.getCurrentDisk(), reqDiskName);
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
                int reqNumberOfSongs = 0;
                while (reqNumberOfSongs <= 0) {
                    System.out.print("Enter the number of songs: ");
                    if (MainMenu.menuScanner.hasNextInt()) {
                        reqNumberOfSongs = MainMenu.menuScanner.nextInt();
                    } else {
                        System.out.println("\n\tOnly numbers can be entered!\n");
                        MainMenu.menuScanner.next();
                    }
                }
                System.out.println("\nDISK NAME [" + reqDiskName + "] (sorted by name)");
                DiskUtils.generateTracksByCount(DiskList.getCurrentDisk(), reqNumberOfSongs);
                MenuUtils.printTracklist(DiskList.getCurrentDisk().getTracklist());
                break;
            case "3":
                while (true) {
                    System.out.print("\nEnter disk duration in seconds: ");
                    if (MainMenu.menuScanner.hasNextInt()) {
                        int reqSongDuration = MainMenu.menuScanner.nextInt();
                        if (reqSongDuration <= Constants.MIN_SONG_DURATION) {
                            System.out.println(
                                    "\n\tDisk duration cannot be less than " + Constants.MIN_SONG_DURATION + " seconds!");
                        } else {
                            System.out.println("\nDISK NAME [" + reqDiskName + "]");
                            DiskUtils.generateTracksByDiskDuration(DiskList.getCurrentDisk(), reqSongDuration);
                            MenuUtils.printTracklist(DiskList.getCurrentDisk().getTracklist());
                            break;
                        }
                    } else {
                        System.out.println("\n\tOnly numbers can be entered!\n");
                        MainMenu.menuScanner.next();
                    }
                }
                break;
            }
            if (userInput.equals("1") || userInput.equals("2") || userInput.equals("3")) {
                break;
            }
        }
    }
}
