package by.dvorkin.recording.console.menu;

import by.dvorkin.recording.model.Disk;
import by.dvorkin.recording.model.DiskList;

public class MergeDiskMenu {
    public static void printSubmenu(Disk disk) {
        int reqDiskDonor;
        MenuUtils.printAllExistingDisk();
        while (true) {
            System.out.print("\nSelect the disc number where the songs will come from: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                reqDiskDonor = MainMenu.menuScanner.nextInt();
                if (reqDiskDonor <= 0 || reqDiskDonor > DiskList.getDiskList().size()) {
                    System.out.println("\n\tWrong disk number!");
                } else {
                    disk.getTracklist().addAll(DiskList.getDiskList().get(reqDiskDonor - 1).getTracklist());
                    System.out.println(
                            "\n\tThe tracklist from disc [" + DiskList.getDiskList().get(reqDiskDonor - 1).getName()
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
