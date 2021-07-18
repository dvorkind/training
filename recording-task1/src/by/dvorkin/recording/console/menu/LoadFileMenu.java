package by.dvorkin.recording.console.menu;

import by.dvorkin.recording.model.DiskList;
import by.dvorkin.recording.model.DiskUtils;

public class LoadFileMenu {
    public static void printSubmenu() {
        MenuUtils.createNewDisk();
        System.out.print("\nEnter file path to load (for example \"D:\\MyDisk.txt\"): ");
        String reqOpenFile = MainMenu.menuScanner.next();
        if (DiskUtils.loadFile(DiskList.getCurrentDisk(), reqOpenFile)) {
            if (MenuUtils.isTracklistNotEmpty()) {
                System.out.println("\nDISK NAME [" + DiskList.getCurrentDisk().getName() + "]");
                MenuUtils.printTracklist(DiskList.getCurrentDisk().getTracklist());
            }
        } else {
            DiskUtils.deleteCurrentDisk();
        }
    }
}
