package by.dvorkin.recording.console.menu;

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
        int reqNumberOfSongs = -1;
        while (reqNumberOfSongs < 0) {
            System.out.print("Enter the number of songs: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                reqNumberOfSongs = MainMenu.menuScanner.nextInt();
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                MainMenu.menuScanner.next();
            }
        }
        if (reqNumberOfSongs == 0) {
            System.out.println("\n\tDisc named [" + reqDiskName + "] has been created empty");
        } else {
            System.out.println("\nDISK NAME [" + reqDiskName + "] (sorted by name)");
            DiskUtils.generateRandomTracks(DiskList.getCurrentDisk(), reqNumberOfSongs);
            MenuUtils.printTracklist(DiskList.getCurrentDisk().getTracklist());
        }
    }
}
