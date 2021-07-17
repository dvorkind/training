package by.dvorkin.recording.console.menu;

import by.dvorkin.recording.console.Runner;
import by.dvorkin.recording.model.Track;

public class FindByDurationMenu {
    public static void printSubmenu() {
        Track maxTrack = Runner.getCurrentDisk().getTracklist().stream()
                .max((fc1, fc2) -> fc1.getTrackDuration() - fc2.getTrackDuration()).get();
        Track minTrack = Runner.getCurrentDisk().getTracklist().stream()
                .min((fc1, fc2) -> fc1.getTrackDuration() - fc2.getTrackDuration()).get();
        int minDuration = minTrack.getTrackDuration();
        int maxDuration = maxTrack.getTrackDuration();
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
