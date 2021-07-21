package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.menu.AbstractMenu;
import by.dvorkin.recording.utils.TrackListUtils;

public class SaveFileMenu extends AbstractMenu {
    @Override
    public void printMenu() {
        String reqSaveFile;
        do {
            System.out.print("\nEnter file path to save (for example \"D:\\MyDisk.txt\"): ");
            reqSaveFile = getMenuScanner().next();
        } while ("".equals(reqSaveFile));
        TrackListUtils.saveFile(getCurrentDisk().getTracklist()
                .getTracks(), reqSaveFile);
        System.out.println("\n\tDisk saved successfully!");
    }
}
