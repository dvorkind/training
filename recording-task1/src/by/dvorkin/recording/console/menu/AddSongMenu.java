package by.dvorkin.recording.console.menu;

import by.dvorkin.recording.model.Disk;
import by.dvorkin.recording.model.Genres;
import by.dvorkin.recording.model.Track;

public class AddSongMenu {
    public static void printSubmenu(Disk disk) {
        String reqSongName = "";
        int reqSongDuration;
        while (reqSongName.equals("")) {
            System.out.print("\nEnter song name: ");
            reqSongName = MainMenu.menuScanner.next();
        }
        while (true) {
            System.out.print("\nEnter song duration in seconds: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                reqSongDuration = MainMenu.menuScanner.nextInt();
                if (reqSongDuration <= 0) {
                    System.out.println("\n\tSong duration cannot be less than or equal to 0!");
                } else {
                    break;
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                MainMenu.menuScanner.next();
            }
        }
        while (true) {
            System.out.println("\nAvailable genres: ");
            int num = 0;
            for (Genres genre : Genres.values()) {
                num++;
                System.out.println(num + ". " + genre.toString());
            }
            System.out.print("Select genre number: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                int reqGenreNumber = MainMenu.menuScanner.nextInt();
                if (reqGenreNumber > 0 && reqGenreNumber <= Genres.values().length) {
                    String reqGenre = Genres.values()[reqGenreNumber - 1].toString();
                    disk.getTracklist().add(new Track(reqSongName, reqSongDuration, reqGenre));
                    System.out.println("\n\tThe song has been added!");
                    break;
                } else {
                    System.out.println("\n\tWrong genre number!");
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                MainMenu.menuScanner.next();
            }
        }
    }
}
