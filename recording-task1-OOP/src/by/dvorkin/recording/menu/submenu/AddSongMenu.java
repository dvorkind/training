package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.entities.Track;
import by.dvorkin.recording.enums.Genres;
import by.dvorkin.recording.interfaces.DiskList;
import by.dvorkin.recording.menu.AbstractMenu;

import java.util.Scanner;

public class AddSongMenu extends AbstractMenu {
    public AddSongMenu(DiskList diskList, Scanner scanner) {
        super(diskList, scanner);
    }

    @Override
    public void printMenu() {
        scanner.nextLine(); // skip \n after scanner.nextInt()
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
            singerName = scanner.nextLine();
        } while ("".equals(singerName));
        return singerName;
    }

    public String reqTitleName() {
        String title;
        do {
            System.out.print("\nEnter song title: ");
            title = scanner.nextLine();
        } while ("".equals(title));
        return title;
    }

    public int reqSongDuration() {
        int duration;
        while (true) {
            System.out.print("\nEnter song duration in seconds: ");
            if (scanner.hasNextInt()) {
                duration = scanner.nextInt();
                if (duration <= 0) {
                    System.out.println("\n\tSong duration cannot be less than or equal to 0!");
                } else {
                    break;
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                scanner.next();
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
            if (scanner.hasNextInt()) {
                int reqGenreNumber = scanner.nextInt();
                if (reqGenreNumber > 0 && reqGenreNumber <= Genres.values().length) {
                    genre = Genres.values()[reqGenreNumber - 1].toString();
                    break;
                } else {
                    System.out.println("\n\tWrong genre number!");
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                scanner.next();
            }
        }
        return genre;
    }
}
