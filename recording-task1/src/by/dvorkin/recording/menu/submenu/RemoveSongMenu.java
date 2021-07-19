package by.dvorkin.recording.menu.submenu;

import java.util.List;

import by.dvorkin.recording.entities.Track;
import by.dvorkin.recording.menu.MainMenu;

public class RemoveSongMenu {
    public static void printSubmenu(List<Track> tracklist) {
        while (true) {
            System.out.print("\nEnter the number of the song to be deleted: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                int reqSongNumber = MainMenu.menuScanner.nextInt();
                if (reqSongNumber <= tracklist.size()) {
                    String trackName = tracklist.get(reqSongNumber - 1).getSinger() + " - "
                            + tracklist.get(reqSongNumber - 1).getTitle();
                    tracklist.remove(reqSongNumber - 1);
                    System.out.println("\n\tThe song [" + trackName + "] has been deleted!");
                    break;
                } else {
                    System.out.println("\n\tWrong song number!");
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                MainMenu.menuScanner.next();
            }
        }
    }
}
