package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.constants.Constants;
import by.dvorkin.recording.entities.Disk;
import by.dvorkin.recording.interfaces.DiskList;
import by.dvorkin.recording.menu.AbstractMenu;
import by.dvorkin.recording.utils.TrackListUtils;

import java.util.Arrays;

public class SongsGeneratingMenu extends AbstractMenu {
    public SongsGeneratingMenu(DiskList diskList) {
        super(diskList);
    }

    @Override
    public void printMenu() {
        getMenuScanner().nextLine(); // skip \n after scanner.nextInt()
        Disk disk = new Disk();
        addToDiskLibrary(disk);
        setCurrentDisk(disk);
        String reqDiskName;
        String[] menuOption = {"1", "2", "3"}; // Array of available options in this menu
        do {
            System.out.print("\nEnter disk name: ");
            reqDiskName = getMenuScanner().nextLine();
        } while ("".equals(reqDiskName));
        getCurrentDisk().setName(reqDiskName);
        while (true) {
            System.out.println("\n1. Create empty disk");
            System.out.println("2. Generate the specified number of songs");
            System.out.println("3. Generate songs for a given disc duration");
            System.out.print("Please, select an option: ");
            String userInput = getMenuScanner().next();
            switch (userInput) {
                case "1":
                    System.out.println("\n\tDisc named [" + reqDiskName + "] has been created empty");
                    break;
                case "2":
                    int reqNumberOfSongs = reqSongsCount();
                    System.out.println("\nDISK NAME [" + reqDiskName + "] (sorted by name)");
                    TrackListUtils.generateTracksByCount(getCurrentDisk()
                                    .getTracklist()
                                    .getTracks(),
                            reqNumberOfSongs);
                    TrackListUtils.printTracklist(getCurrentDisk().getTracklist());
                    break;
                case "3":
                    int reqDiskDuration = reqDuration();
                    System.out.println("\nDISK NAME [" + reqDiskName + "]");
                    TrackListUtils.generateTracksByDiskDuration(
                            getCurrentDisk()
                                    .getTracklist()
                                    .getTracks(), reqDiskDuration);
                    TrackListUtils.printTracklist(getCurrentDisk()
                            .getTracklist());
                    break;
                default:
                    System.out.println("\n\tWrong command number!");
                    break;
            }
            if (Arrays.asList(menuOption)
                    .contains(userInput)) {
                break;
            }
        }
    }

    public int reqSongsCount() {
        int songsCount = 0;
        while (songsCount <= 0) {
            System.out.print("Enter the number of songs: ");
            if (getMenuScanner().hasNextInt()) {
                songsCount = getMenuScanner().nextInt();
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                getMenuScanner().next();
            }
        }
        return songsCount;
    }

    public int reqDuration() {
        int duration;
        while (true) {
            System.out.print("\nEnter disk duration in seconds: ");
            if (getMenuScanner().hasNextInt()) {
                duration = getMenuScanner().nextInt();
                if (duration <= Constants.MIN_SONG_DURATION) {
                    System.out.println(
                            "\n\tDisk duration cannot be less than " + Constants.MIN_SONG_DURATION + " seconds!");
                } else {
                    break;
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                getMenuScanner().next();
            }
        }
        return duration;
    }
}
