package by.dvorkin.recording.console.menu;

import by.dvorkin.recording.model.DiskList;

public class SelectDiskMenu {
    public static void printSubmenu() {
        while (true) {
            System.out.print("\nEnter the number of disk: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                int reqDiskNumber = MainMenu.menuScanner.nextInt();
                if (reqDiskNumber <= DiskList.getDiskList().size()) {
                    DiskList.setCurrentDisk(DiskList.getDiskList().get(reqDiskNumber - 1));
                    if (MenuUtils.isTracklistNotEmpty()) {
                        System.out.println("\nCURRENT OPEN DISK NAME [" + DiskList.getCurrentDisk().getName() + "]");
                        MenuUtils.printTracklist(DiskList.getCurrentDisk().getTracklist());
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
