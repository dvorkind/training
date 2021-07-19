package by.dvorkin.recording.menu.submenu;

import java.util.List;

import by.dvorkin.recording.entities.Track;
import by.dvorkin.recording.menu.MainMenu;
import by.dvorkin.recording.utils.TrackListUtils;

public class SaveFileMenu {
    public static void printSubmenu(List<Track> tracklist) {
        String reqSaveFile;
        while (true) {
            System.out.print("\nEnter file path to save (for example \"D:\\MyDisk.txt\"): ");
            reqSaveFile = MainMenu.menuScanner.next();
            if (!"".equals(reqSaveFile)) {
                break;
            }
        }
        TrackListUtils.saveFile(tracklist, reqSaveFile);
        System.out.println("\n\tDisk saved successfully!");
    }
}
