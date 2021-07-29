package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.entities.Track;
import by.dvorkin.recording.interfaces.DiskList;
import by.dvorkin.recording.menu.AbstractMenu;
import by.dvorkin.recording.utils.Helper;
import by.dvorkin.recording.utils.TrackListUtils;

import java.util.Comparator;
import java.util.Scanner;

public class FindByDurationMenu extends AbstractMenu {
    public FindByDurationMenu(DiskList diskList, Scanner scanner) {
        super(diskList, scanner);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public void printMenu() {
        scanner.nextLine(); // skip \n after scanner.nextInt()
        Track maxTrack = getCurrentDisk().getTracklist()
                .getTracks()
                .stream()
                .max(Comparator.comparingInt(Track::getDuration))
                .get();
        Track minTrack = getCurrentDisk().getTracklist()
                .getTracks()
                .stream()
                .min(Comparator.comparingInt(Track::getDuration))
                .get();
        int minDuration = minTrack.getDuration();
        int maxDuration = maxTrack.getDuration();
        int reqMinTrackDuration;
        int reqMaxTrackDuration;
        while (true) {
            System.out.print("\nEnter MIN and MAX length in seconds (between " + minDuration + " and " + maxDuration
                    + ") separated by a space: ");
            String str = scanner.nextLine();
            String[] reqRange = str.split(" ");
            if (reqRange.length == 2 && Helper.isNumber(reqRange[0]) && Helper.isNumber(reqRange[1])) {
                reqMinTrackDuration = Integer.parseInt(reqRange[0]);
                reqMaxTrackDuration = Integer.parseInt(reqRange[1]);
                if (reqMinTrackDuration >= minDuration && reqMaxTrackDuration <= maxDuration) {
                    break;
                } else {
                    System.out.println("\n\tThe first number must not be less " + minDuration
                            + " and the second number must not be greater " + maxDuration + "!");
                }
            } else
                System.out.println("\n\tOnly two numbers can be entered, separated by a space!");
        }
        System.out.println("\nLIST OF SONGS MATCHING THE CONDITION: ");
        TrackListUtils.printTracklist(TrackListUtils.findTrackByRange(getCurrentDisk(), reqMinTrackDuration, reqMaxTrackDuration));
    }
}
