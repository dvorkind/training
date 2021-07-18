package by.dvorkin.recording.console.menu;

import by.dvorkin.recording.model.Disk;

public class RemoveSongMenu {
    public static void printSubmenu(Disk disk) {
        while (true) {
            System.out.print("\nEnter the number of the song to be deleted: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                int reqSongNumber = MainMenu.menuScanner.nextInt();
                if (reqSongNumber <= disk.getTracklist().size()) {

                    String trackName = disk.getTracklist().get(reqSongNumber - 1).getTrackName();
                    disk.getTracklist().remove(reqSongNumber - 1);
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
