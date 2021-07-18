package by.dvorkin.recording.console.menu;

import by.dvorkin.recording.model.Disk;
import by.dvorkin.recording.model.Genres;
import by.dvorkin.recording.model.Track;

public class AddSongMenu {
    public static void printSubmenu(Disk disk) {
        String reqSinger = "";
        String reqTitle = "";
        int reqDuration;
        while (reqSinger.equals("")) {
            System.out.print("\nEnter singer name: ");
            reqSinger = MainMenu.menuScanner.next();
        }
        while (reqTitle.equals("")) {
            System.out.print("\nEnter song title: ");
            reqTitle = MainMenu.menuScanner.next();
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
                    disk.getTracklist().add(new Track(reqSinger, reqTitle, reqDuration, reqGenre));
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
