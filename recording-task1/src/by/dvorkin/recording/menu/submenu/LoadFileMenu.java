package by.dvorkin.recording.menu.submenu;

import java.io.FileNotFoundException;
import java.io.IOException;

import by.dvorkin.recording.entities.DiskList;
import by.dvorkin.recording.menu.MainMenu;
import by.dvorkin.recording.utils.DiskUtils;
import by.dvorkin.recording.utils.Helper;
import by.dvorkin.recording.utils.MenuUtils;
import by.dvorkin.recording.utils.TrackListUtils;

public class LoadFileMenu {

    public static void printSubmenu() {
        MenuUtils.createNewDisk();
        System.out.print("\nEnter file path to load (for example \"D:\\MyDisk.txt\"): ");
        String reqOpenFile = MainMenu.menuScanner.next();
        try {
            DiskList.getInstance().getCurrentDisk().setName(Helper.extractDiskNameFromFilename(reqOpenFile));
            DiskUtils.loadFile(DiskList.getInstance().getCurrentDisk(), reqOpenFile);
            if (MenuUtils.isTracklistNotEmpty()) {
                System.out.println("\nDISK NAME [" + DiskList.getInstance().getCurrentDisk().getName() + "]");
                TrackListUtils.printTracklist(DiskList.getInstance().getCurrentDisk().getTracklist());
            } else {
                System.out.println("\n\tThe current disk contains no songs!");
            }
        } catch (FileNotFoundException e) {
            System.out.println("\n\tFile doesn't exist!");
            DiskUtils.deleteCurrentDisk();
        } catch (IOException e) {
            System.out.println("\n\tIncorrect file format!");
            DiskUtils.deleteCurrentDisk();
        }
    }
}
