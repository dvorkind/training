package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.interfaces.DiskList;
import by.dvorkin.recording.menu.AbstractMenu;
import by.dvorkin.recording.utils.TrackListUtils;

import java.util.Scanner;

public class SaveFileMenu extends AbstractMenu {
    public SaveFileMenu(DiskList diskList, Scanner scanner) {
        super(diskList, scanner);
    }

    @Override
    public void printMenu() {
        String reqSaveFile;
        do {
            System.out.print("\nEnter file path to save (for example \"D:\\MyDisk.txt\"): ");
            reqSaveFile = scanner.next();
        } while ("".equals(reqSaveFile));
        TrackListUtils.saveFile(getCurrentDisk().getTracklist()
                .getTracks(), reqSaveFile);
        System.out.println("\n\tDisk saved successfully!");
    }
}
