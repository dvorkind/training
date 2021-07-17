package by.dvorkin.recording.console.menu;

import by.dvorkin.recording.console.Runner;
import by.dvorkin.recording.model.DiskUtils;

public class GenerateSongsMenu {
    public static void printSubmenu() {
        MenuUtils.createNewDisk();
        String reqDiskName = "";
        while (reqDiskName.equals("")) {
            System.out.print("\nEnter disk name: ");
            reqDiskName = MainMenu.menuScanner.next();
        }
        DiskUtils.setDiskNameFromFilename(Runner.getCurrentDisk(), reqDiskName);
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
        DiskUtils.generateRandomTracks(Runner.getCurrentDisk(), reqNumberOfSongs);
        MenuUtils.printTracklist(Runner.getCurrentDisk().getTracklist());
    }
}
