package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.entities.Disk;
import by.dvorkin.recording.enums.SortBy;
import by.dvorkin.recording.menu.MainMenu;
import by.dvorkin.recording.utils.TrackListUtils;

public class SortDiskMenu {
    public static void printSubmenu(Disk disk) {
        System.out.println("\n1. Sort songs by genre");
        System.out.println("2. Sort songs by name");
        System.out.println("3. Sort songs by duration");
        SortBy sortValue;
        while (true) {
            System.out.print("\nPlease select an option: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                int reqSortNumber = MainMenu.menuScanner.nextInt();
                if (1 <= reqSortNumber && reqSortNumber <= 3) {
                    sortValue = SortBy.values()[reqSortNumber - 1];
                    System.out.println("\nDISK NAME [" + disk.getName() + "] " + "(sorted by "
                            + sortValue.toString().toLowerCase() + ")");
                    break;
                } else {
                    System.out.println("\n\tWrong sort number!");
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                MainMenu.menuScanner.next();
            }
        }
        TrackListUtils.printTracklist(TrackListUtils.sortSongsBy(disk.getTracklist(), sortValue));
    }
}
