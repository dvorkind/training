package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.interfaces.DiskList;
import by.dvorkin.recording.menu.AbstractMenu;

public class MergeDiskMenu extends AbstractMenu {
    public MergeDiskMenu(DiskList diskList) {
        super(diskList);
    }

    @Override
    public void printMenu() {
        int reqDiskDonor;
        printAllExistingDisk();
        while (true) {
            System.out.print("\nSelect the disc number where the songs will come from: ");
            if (getMenuScanner().hasNextInt()) {
                reqDiskDonor = getMenuScanner().nextInt();
                if (reqDiskDonor <= 0 || reqDiskDonor > getDiskList().size()) {
                    System.out.println("\n\tWrong disk number!");
                } else {
                    getCurrentDisk().getTracklist()
                            .getTracks()
                            .addAll(
                                    getDiskList().get(reqDiskDonor - 1)
                                            .getTracklist()
                                            .getTracks());
                    System.out.println("\n\tThe tracklist from disc ["
                            + getDiskList().get(reqDiskDonor - 1)
                            .getName()
                            + "] has been added to the current disc!");
                    break;
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                getMenuScanner().next();
            }
        }
    }
}