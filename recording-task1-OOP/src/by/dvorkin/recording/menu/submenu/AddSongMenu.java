package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.entities.Track;
import by.dvorkin.recording.enums.Genres;
import by.dvorkin.recording.menu.AbstractMenu;

public class AddSongMenu extends AbstractMenu {
    @Override
    public void printMenu() {
        getMenuScanner().nextLine(); // skip \n after scanner.nextInt()
        String reqSinger = reqSingerName();
        String reqTitle = reqTitleName();
        int reqDuration = reqSongDuration();
        String reqGenre = reqGenreName();
        getCurrentDisk().getTracklist()
                .getTracks()
                .add(new Track(reqSinger, reqTitle, reqDuration, reqGenre));
        System.out.println("\n\tThe song has been added!");
    }

    public String reqSingerName() {
        String singerName;
        do {
            System.out.print("\nEnter singer name: ");
            singerName = getMenuScanner().nextLine();
        } while ("".equals(singerName));
        return singerName;
    }

    public String reqTitleName() {
        String title;
        do {
            System.out.print("\nEnter song title: ");
            title = getMenuScanner().nextLine();
        } while ("".equals(title));
        return title;
    }

    public int reqSongDuration() {
        int duration;
        while (true) {
            System.out.print("\nEnter song duration in seconds: ");
            if (getMenuScanner().hasNextInt()) {
                duration = getMenuScanner().nextInt();
                if (duration <= 0) {
                    System.out.println("\n\tSong duration cannot be less than or equal to 0!");
                } else {
                    break;
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                getMenuScanner().next();
            }
        }
        return duration;
    }

    public String reqGenreName() {
        String genre;
        while (true) {
            System.out.println("\nAvailable genres: ");
            for (int i = 1; i <= Genres.values().length; i++) {
                System.out.println(i + ". " + Genres.values()[i - 1]);
            }
            System.out.print("Select genre number: ");
            if (getMenuScanner().hasNextInt()) {
                int reqGenreNumber = getMenuScanner().nextInt();
                if (reqGenreNumber > 0 && reqGenreNumber <= Genres.values().length) {
                    genre = Genres.values()[reqGenreNumber - 1].toString();
                    break;
                } else {
                    System.out.println("\n\tWrong genre number!");
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                getMenuScanner().next();
            }
        }
        return genre;
    }
}
