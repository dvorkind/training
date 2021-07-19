package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.entities.DiskList;
import by.dvorkin.recording.entities.TrackList;
import by.dvorkin.recording.menu.MainMenu;
import by.dvorkin.recording.utils.MenuUtils;

public class MergeDiskMenu {
    public static void printSubmenu(TrackList tracklist) {
        int reqDiskDonor;
        MenuUtils.printAllExistingDisk();
        while (true) {
            System.out.print("\nSelect the disc number where the songs will come from: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                reqDiskDonor = MainMenu.menuScanner.nextInt();
                if (reqDiskDonor <= 0 || reqDiskDonor > DiskList.getInstance().getDiskList().size()) {
                    System.out.println("\n\tWrong disk number!");
                } else {
                    tracklist.getTracks().addAll(
                            DiskList.getInstance().getDiskList().get(reqDiskDonor - 1).getTracklist().getTracks());
                    System.out.println("\n\tThe tracklist from disc ["
                            + DiskList.getInstance().getDiskList().get(reqDiskDonor - 1).getName()
                            + "] has been added to the current disc!");
                    break;
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                MainMenu.menuScanner.next();
            }
        }
    }
}
