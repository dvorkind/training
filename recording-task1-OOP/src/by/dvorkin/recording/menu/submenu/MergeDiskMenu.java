package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.interfaces.DiskList;
import by.dvorkin.recording.menu.AbstractMenu;

import java.util.Scanner;

public class MergeDiskMenu extends AbstractMenu {
    public MergeDiskMenu(DiskList diskList, Scanner scanner) {
        super(diskList, scanner);
    }

    @Override
    public void printMenu() {
        int reqDiskDonor;
        printAllExistingDisk();
        while (true) {
            System.out.print("\nSelect the disc number where the songs will come from: ");
            if (scanner.hasNextInt()) {
                reqDiskDonor = scanner.nextInt();
                if (reqDiskDonor <= 0 || reqDiskDonor > getDiskLibrary().size()) {
                    System.out.println("\n\tWrong disk number!");
                } else {
                    getCurrentDisk().getTracklist()
                            .getTracks()
                            .addAll(
                                    getDiskLibrary().get(reqDiskDonor - 1)
                                            .getTracklist()
                                            .getTracks());
                    System.out.println("\n\tThe tracklist from disc ["
                            + getDiskLibrary().get(reqDiskDonor - 1)
                            .getName()
                            + "] has been added to the current disc!");
                    break;
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                scanner.next();
            }
        }
    }
}