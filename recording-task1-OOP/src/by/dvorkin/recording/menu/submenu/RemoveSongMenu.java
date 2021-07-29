package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.interfaces.DiskList;
import by.dvorkin.recording.menu.AbstractMenu;

import java.util.Scanner;

public class RemoveSongMenu extends AbstractMenu {
    public RemoveSongMenu(DiskList diskList, Scanner scanner) {
        super(diskList, scanner);
    }

    @Override
    public void printMenu() {
        while (true) {
            System.out.print("\nEnter the number of the song to be deleted: ");
            if (scanner.hasNextInt()) {
                int reqSongNumber = scanner.nextInt();
                if (reqSongNumber <= getCurrentDisk().getTracklist()
                        .getTracks()
                        .size()) {
                    String trackName = getCurrentDisk().getTracklist()
                            .getTracks()
                            .get(reqSongNumber - 1)
                            .getSinger() + " - "
                            + getCurrentDisk().getTracklist()
                            .getTracks()
                            .get(reqSongNumber - 1)
                            .getTitle();
                    getCurrentDisk().getTracklist()
                            .getTracks()
                            .remove(reqSongNumber - 1);
                    System.out.println("\n\tThe song [" + trackName + "] has been deleted!");
                    break;
                } else {
                    System.out.println("\n\tWrong song number!");
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                scanner.next();
            }
        }
    }
}
