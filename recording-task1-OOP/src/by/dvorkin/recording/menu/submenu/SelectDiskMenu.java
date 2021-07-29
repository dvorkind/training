package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.interfaces.DiskList;
import by.dvorkin.recording.menu.AbstractMenu;
import by.dvorkin.recording.utils.TrackListUtils;

import java.util.Scanner;

public class SelectDiskMenu extends AbstractMenu {
    public SelectDiskMenu(DiskList diskList, Scanner scanner) {
        super(diskList, scanner);
    }

    @Override
    public void printMenu() {
        while (true) {
            System.out.print("\nEnter the number of disk: ");
            if (scanner.hasNextInt()) {
                int reqDiskNumber = scanner.nextInt();
                if (reqDiskNumber <= getDiskLibrary().size()) {
                    setCurrentDisk(getDiskLibrary().get(reqDiskNumber - 1));
                    if (isTracklistNotEmpty()) {
                        System.out.println(
                                "\nCURRENT OPEN DISK NAME [" + getCurrentDisk().getName() + "]");
                        TrackListUtils.printTracklist(getCurrentDisk().getTracklist());
                    } else {
                        System.out.println("\n\tThe current disk contains no songs!");
                    }
                    break;
                } else {
                    System.out.println("\n\tWrong disk number!");
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                scanner.next();
            }
        }
    }
}
