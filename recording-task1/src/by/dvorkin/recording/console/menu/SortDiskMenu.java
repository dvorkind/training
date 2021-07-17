package by.dvorkin.recording.console.menu;

import by.dvorkin.recording.model.Disk;
import by.dvorkin.recording.model.SortBy;

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
        if (MenuUtils.sortSongsBy(disk, sortValue) == null) {
            System.out.println("\n\tTracklist is empty!");
        } else {
            MenuUtils.printTracklist(MenuUtils.sortSongsBy(disk, sortValue));
        }
    }
}
