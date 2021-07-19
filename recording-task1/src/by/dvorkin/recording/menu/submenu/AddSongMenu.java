package by.dvorkin.recording.menu.submenu;

import java.util.List;

import by.dvorkin.recording.entities.Track;
import by.dvorkin.recording.enums.Genres;
import by.dvorkin.recording.menu.MainMenu;

public class AddSongMenu {
    public static void printSubmenu(List<Track> tracklist) {
        MainMenu.menuScanner.nextLine(); // skip \n after scanner.nextInt()
        String reqSinger = AddSongMenu.reqSingerName();
        String reqTitle = AddSongMenu.reqTitleName();
        int reqDuration = AddSongMenu.reqSongDuration();
        String reqGenre = AddSongMenu.reqGenreName();
        tracklist.add(new Track(reqSinger, reqTitle, reqDuration, reqGenre));
        System.out.println("\n\tThe song has been added!");
    }

    public static String reqSingerName() {
        String singerName;
        while (true) {
            System.out.print("\nEnter singer name: ");
            singerName = MainMenu.menuScanner.nextLine();
            if (!"".equals(singerName)) {
                break;
            }
        }
        return singerName;
    }

    public static String reqTitleName() {
        String title;
        while (true) {
            System.out.print("\nEnter song title: ");
            title = MainMenu.menuScanner.nextLine();
            if (!"".equals(title)) {
                break;
            }
        }
        return title;
    }

    public static int reqSongDuration() {
        int duration;
        while (true) {
            System.out.print("\nEnter song duration in seconds: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                duration = MainMenu.menuScanner.nextInt();
                if (duration <= 0) {
                    System.out.println("\n\tSong duration cannot be less than or equal to 0!");
                } else {
                    break;
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                MainMenu.menuScanner.next();
            }
        }
        return duration;
    }

    public static String reqGenreName() {
        String genre;
        while (true) {
            System.out.println("\nAvailable genres: ");
            for (int i = 1; i <= Genres.values().length; i++) {
                System.out.println(i + ". " + Genres.values()[i - 1]);
            }
            System.out.print("Select genre number: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                int reqGenreNumber = MainMenu.menuScanner.nextInt();
                if (reqGenreNumber > 0 && reqGenreNumber <= Genres.values().length) {
                    genre = Genres.values()[reqGenreNumber - 1].toString();
                    break;
                } else {
                    System.out.println("\n\tWrong genre number!");
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                MainMenu.menuScanner.next();
            }
        }
        return genre;
    }
}
