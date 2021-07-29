package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.enums.SortBy;
import by.dvorkin.recording.interfaces.DiskList;
import by.dvorkin.recording.menu.AbstractMenu;
import by.dvorkin.recording.utils.TrackListUtils;

import java.util.Scanner;

public class SortDiskMenu extends AbstractMenu {
    public SortDiskMenu(DiskList diskList, Scanner scanner) {
        super(diskList, scanner);
    }

    @Override
    public void printMenu() {
        System.out.println("\n1. Sort songs by genre");
        System.out.println("2. Sort songs by name");
        System.out.println("3. Sort songs by duration");
        SortBy sortValue;
        while (true) {
            System.out.print("\nPlease select an option: ");
            if (scanner.hasNextInt()) {
                int reqSortNumber = scanner.nextInt();
                if (1 <= reqSortNumber && reqSortNumber <= 3) {
                    sortValue = SortBy.values()[reqSortNumber - 1];
                    System.out.println("\nDISK NAME [" + getCurrentDisk().getName() + "] " + "(sorted by "
                            + sortValue.toString()
                            .toLowerCase() + ")");
                    break;
                } else {
                    System.out.println("\n\tWrong sort number!");
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                scanner.next();
            }
        }
        TrackListUtils.printTracklist(TrackListUtils.sortSongsBy(getCurrentDisk().getTracklist(), sortValue));
    }
}
