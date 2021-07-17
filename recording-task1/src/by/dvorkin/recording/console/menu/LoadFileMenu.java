package by.dvorkin.recording.console.menu;

import by.dvorkin.recording.console.Runner;
import by.dvorkin.recording.model.DiskUtils;

public class LoadFileMenu {
    public static void printSubmenu() {
        MenuUtils.createNewDisk();
        System.out.print("\nEnter file path to load (for example \"D:\\MyDisk.txt\"): ");
        String reqOpenFile;
        reqOpenFile = MainMenu.menuScanner.next();
        DiskUtils.loadFile(Runner.getCurrentDisk(), reqOpenFile);
        if (!Runner.getCurrentDisk().isTracklistEmpty() && Runner.getCurrentDisk().getTracklist() != null) {
            System.out.println("\nDISK NAME [" + Runner.getCurrentDisk().getName() + "]");
            MenuUtils.printTracklist(Runner.getCurrentDisk().getTracklist());
        }
    }
}
