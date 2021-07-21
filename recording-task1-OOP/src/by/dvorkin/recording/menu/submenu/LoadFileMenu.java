package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.menu.AbstractMenu;
import by.dvorkin.recording.utils.DiskUtils;
import by.dvorkin.recording.utils.Helper;
import by.dvorkin.recording.utils.MenuUtils;
import by.dvorkin.recording.utils.TrackListUtils;

import java.io.FileNotFoundException;

public class LoadFileMenu extends AbstractMenu {
    @Override
    public void printMenu() {
        setCurrentDisk(MenuUtils.createNewDisk());
        System.out.print("\nEnter file path to load (for example \"D:\\MyDisk.txt\"): ");
        String reqOpenFile = getMenuScanner().next();
        try {
            getCurrentDisk()
                    .setName(Helper.extractDiskNameFromFilename(reqOpenFile));
            DiskUtils.loadFile(getCurrentDisk(), reqOpenFile);
            if (MenuUtils.isTracklistNotEmpty()) {
                System.out.println("\nDISK NAME [" + getCurrentDisk()
                        .getName() + "]");
                TrackListUtils.printTracklist(getCurrentDisk()
                        .getTracklist());
            } else {
                System.out.println("\n\tThe current disk contains no songs!");
            }
        } catch (FileNotFoundException e) {
            System.out.println("\n\tFile doesn't exist!");
            DiskUtils.deleteCurrentDisk();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\n\tIncorrect file format!");
            DiskUtils.deleteCurrentDisk();
        }
    }
}