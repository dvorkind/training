package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.entities.Disk;
import by.dvorkin.recording.interfaces.DiskList;
import by.dvorkin.recording.menu.AbstractMenu;
import by.dvorkin.recording.utils.DiskUtils;
import by.dvorkin.recording.utils.Helper;
import by.dvorkin.recording.utils.TrackListUtils;

import java.io.FileNotFoundException;

public class LoadFileMenu extends AbstractMenu {
    public LoadFileMenu(DiskList diskList) {
        super(diskList);
    }

    @Override
    public void printMenu() {
        Disk disk = new Disk();
        addToDiskLibrary(disk);
        setCurrentDisk(disk);
        System.out.print("\nEnter file path to load (for example \"D:\\MyDisk.txt\"): ");
        String reqOpenFile = getMenuScanner().next();
        try {
            getCurrentDisk()
                    .setName(Helper.extractDiskNameFromFilename(reqOpenFile));
            DiskUtils.loadFile(getCurrentDisk(), reqOpenFile);
            if (isTracklistNotEmpty()) {
                System.out.println("\nDISK NAME [" + getCurrentDisk()
                        .getName() + "]");
                TrackListUtils.printTracklist(getCurrentDisk()
                        .getTracklist());
            } else {
                System.out.println("\n\tThe current disk contains no songs!");
            }
        } catch (FileNotFoundException e) {
            System.out.println("\n\tFile doesn't exist!");
            deleteCurrentDisk();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\n\tIncorrect file format!");
            deleteCurrentDisk();
        }
    }
}