package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.entities.DiskList;
import by.dvorkin.recording.menu.MainMenu;
import by.dvorkin.recording.utils.MenuUtils;
import by.dvorkin.recording.utils.TrackListUtils;

public class SelectDiskMenu {
    public static void printSubmenu() {
        while (true) {
            System.out.print("\nEnter the number of disk: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                int reqDiskNumber = MainMenu.menuScanner.nextInt();
                if (reqDiskNumber <= DiskList.getInstance().getDiskList().size()) {
                    DiskList.getInstance().setCurrentDisk(DiskList.getInstance().getDiskList().get(reqDiskNumber - 1));
                    if (MenuUtils.isTracklistNotEmpty()) {
                        System.out.println(
                                "\nCURRENT OPEN DISK NAME [" + DiskList.getInstance().getCurrentDisk().getName() + "]");
                        TrackListUtils.printTracklist(DiskList.getInstance().getCurrentDisk().getTracklist());
                    }
                    break;
                } else {
                    System.out.println("\n\tWrong disk number!");
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                MainMenu.menuScanner.next();
            }
        }
    }
}
