package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.menu.AbstractMenu;
import by.dvorkin.recording.utils.MenuUtils;
import by.dvorkin.recording.utils.TrackListUtils;

public class SelectDiskMenu extends AbstractMenu {
    @Override
    public void printMenu() {
        while (true) {
            System.out.print("\nEnter the number of disk: ");
            if (getMenuScanner().hasNextInt()) {
                int reqDiskNumber = getMenuScanner().nextInt();
                if (reqDiskNumber <= getDiskList().size()) {
                    setCurrentDisk(getDiskList().get(reqDiskNumber - 1));
                    if (MenuUtils.isTracklistNotEmpty()) {
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
                getMenuScanner().next();
            }
        }
    }
}
