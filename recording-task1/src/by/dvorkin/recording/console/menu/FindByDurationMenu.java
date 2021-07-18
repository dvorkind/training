package by.dvorkin.recording.console.menu;

import by.dvorkin.recording.model.Disk;
import by.dvorkin.recording.model.Track;
import java.util.Comparator;

public class FindByDurationMenu {
    public static void printSubmenu(Disk disk) {
        Track maxTrack = disk.getTracklist().stream().max(Comparator.comparingInt(Track::getDuration)).get();
        Track minTrack = disk.getTracklist().stream().min(Comparator.comparingInt(Track::getDuration)).get();
        int minDuration = minTrack.getDuration();
        int maxDuration = maxTrack.getDuration();
        int reqMinTrackDuration;
        int reqMaxTrackDuration;
        while (true) {
            System.out.print("\nEnter min length in seconds (between " + minDuration + " and " + maxDuration + "): ");
            if (MainMenu.menuScanner.hasNextInt()) {
                reqMinTrackDuration = MainMenu.menuScanner.nextInt();
                if (reqMinTrackDuration < minDuration || reqMinTrackDuration > maxDuration) {
                    System.out.println("\n\tThe specified duration is not within the valid range!");
                } else {
                    break;
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                MainMenu.menuScanner.next();
            }
        }
        while (true) {
            System.out.print(
                    "\nEnter max length in seconds (between " + reqMinTrackDuration + " and " + maxDuration + "): ");
            if (MainMenu.menuScanner.hasNextInt()) {
                reqMaxTrackDuration = MainMenu.menuScanner.nextInt();
                if (reqMaxTrackDuration > maxDuration || reqMaxTrackDuration < reqMinTrackDuration) {
                    System.out.println("\n\tThe specified duration is not within the valid range!");
                } else {
                    break;
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                MainMenu.menuScanner.next();
            }
        }
        MenuUtils.findTrackByRange(reqMinTrackDuration, reqMaxTrackDuration);
    }

}
