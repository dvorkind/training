package by.dvorkin.recording.utils;

import by.dvorkin.recording.entities.Disk;
import by.dvorkin.recording.entities.DiskList;
import by.dvorkin.recording.entities.Track;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DiskUtils {

    public static void loadFile(Disk disk, String fileName) throws FileNotFoundException, ArrayIndexOutOfBoundsException {
        File file = new File(fileName);
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String fileLine = fileScanner.nextLine();
                String[] words = fileLine.split(",");
                disk.getTracklist()
                        .getTracks()
                        .add(new Track(words[0], words[1], Integer.parseInt(words[2]), words[3]));
            }
        }
    }

    public static void deleteCurrentDisk() {
        DiskList disklist = DiskList.getInstance();
        disklist.getDiskList()
                .remove(disklist.getCurrentDisk());
        if (disklist.getDiskList()
                .size() > 0) {
            disklist.setCurrentDisk(disklist.getDiskList()
                    .get(disklist.getDiskList()
                            .size() - 1));
        } else {
            disklist.setCurrentDisk(null);
        }
    }
}
