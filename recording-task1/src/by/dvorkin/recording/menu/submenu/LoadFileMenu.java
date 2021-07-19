package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.entities.DiskList;
import by.dvorkin.recording.menu.MainMenu;
import by.dvorkin.recording.utils.DiskUtils;
import by.dvorkin.recording.utils.MenuUtils;
import by.dvorkin.recording.utils.TrackListUtils;

public class LoadFileMenu {
    public static void printSubmenu() {
        MenuUtils.createNewDisk();
        System.out.print("\nEnter file path to load (for example \"D:\\MyDisk.txt\"): ");
        String reqOpenFile = MainMenu.menuScanner.next();
        if (DiskUtils.loadFile(DiskList.getInstance().getCurrentDisk(), reqOpenFile)) {
            if (MenuUtils.isTracklistNotEmpty()) {
                System.out.println("\nDISK NAME [" + DiskList.getInstance().getCurrentDisk().getName() + "]");
                TrackListUtils.printTracklist(DiskList.getInstance().getCurrentDisk().getTracklist());
            }
        } else {
            DiskUtils.deleteCurrentDisk();
        }
    }
}
