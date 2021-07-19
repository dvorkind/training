package by.dvorkin.recording.menu.submenu;

import java.util.List;

import by.dvorkin.recording.entities.Track;
import by.dvorkin.recording.enums.Genres;
import by.dvorkin.recording.menu.MainMenu;

public class AddSongMenu {
    public static void printSubmenu(List<Track> tracklist) {
        MainMenu.menuScanner.nextLine(); // skip \n after scanner.nextInt()
        String reqSinger;
        String reqTitle;
        String reqGenre;
        int reqDuration;
        while (true) {
            System.out.print("\nEnter singer name: ");
            reqSinger = MainMenu.menuScanner.nextLine();
            if (!"".equals(reqSinger)) {
                break;
            }
        }
        while (true) {
            System.out.print("\nEnter song title: ");
            reqTitle = MainMenu.menuScanner.nextLine();
            if (!"".equals(reqTitle)) {
                break;
            }
        }
        while (true) {
            System.out.print("\nEnter song duration in seconds: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                reqDuration = MainMenu.menuScanner.nextInt();
                if (reqDuration <= 0) {
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
            for (int i = 1; i <= Genres.values().length; i++) {
                System.out.println(i + ". " + Genres.values()[i - 1]);
            }
            System.out.print("Select genre number: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                int reqGenreNumber = MainMenu.menuScanner.nextInt();
                if (reqGenreNumber > 0 && reqGenreNumber <= Genres.values().length) {
                    reqGenre = Genres.values()[reqGenreNumber - 1].toString();
                    break;
                } else {
                    System.out.println("\n\tWrong genre number!");
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                MainMenu.menuScanner.next();
            }
        }
        tracklist.add(new Track(reqSinger, reqTitle, reqDuration, reqGenre));
        System.out.println("\n\tThe song has been added!");
    }
}
