package by.dvorkin.recording.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import by.dvorkin.recording.entities.Disk;
import by.dvorkin.recording.entities.DiskList;
import by.dvorkin.recording.entities.Track;

public class DiskUtils {

    public static Boolean loadFile(Disk disk, String fileName) {
        Scanner fileScanner = null;
        try {
            File file = new File(fileName);
            disk.setName(Helper.setDiskNameFromFilename(file.getName()));
            fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String fileLine = fileScanner.nextLine();
                String[] words = fileLine.split(",");
                disk.getTracklist().getTracks()
                        .add(new Track(words[0], words[1], Integer.parseInt(words[2]), words[3]));
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("\n\tFile doesn't exist!");
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\n\tIncorrect file format!");
            return false;
        } finally {
            if (fileScanner != null) {
                fileScanner.close();
            }
        }
    }

    public static void deleteCurrentDisk() {
        DiskList disklist = DiskList.getInstance();
        disklist.getDiskList().remove(disklist.getCurrentDisk());
        if (disklist.getDiskList().size() > 0) {
            disklist.setCurrentDisk(disklist.getDiskList().get(disklist.getDiskList().size() - 1));
        } else {
            disklist.setCurrentDisk(null);
        }
    }
}
