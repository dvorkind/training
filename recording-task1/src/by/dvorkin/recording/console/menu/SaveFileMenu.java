package by.dvorkin.recording.console.menu;

import by.dvorkin.recording.model.Disk;
import by.dvorkin.recording.model.DiskUtils;

public class SaveFileMenu {
    public static void printSubmenu(Disk disk) {
        String reqSaveFile = "";
        while (reqSaveFile.equals("")) {
            System.out.print("\nEnter file path to save (for example \"D:\\MyDisk.txt\"): ");
            reqSaveFile = MainMenu.menuScanner.next();
        }
        DiskUtils.saveFile(disk, reqSaveFile);
        System.out.println("\n\tDisk saved successfully!");
    }
}
