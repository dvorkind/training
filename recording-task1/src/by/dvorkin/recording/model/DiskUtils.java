package by.dvorkin.recording.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class DiskUtils {
    public static void setDiskNameFromFilename(Disk disk, String name) {
        int i = name.lastIndexOf('.');
        if (i != -1) {
            name = name.substring(0, i);
        }
        disk.setName(name);
    }

    public static Boolean loadFile(Disk disk, String fileName) {
        Scanner fileScanner = null;
        try {
            File file = new File(fileName);
            setDiskNameFromFilename(disk, file.getName());
            fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String fileLine = fileScanner.nextLine();
                String[] words = fileLine.split(",");
                disk.getTracklist().add(new Track(words[0], Integer.parseInt(words[1]), words[2]));
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

    public static void saveFile(Disk disk, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (Track track : disk.getTracklist()) {
                writer.write(
                        track.getTrackName() + "," + track.getTrackDuration() + "," + track.getTrackGenre() + "\n");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void generateRandomTracks(Disk disk, int count) {
        int countGenres = Genres.values().length;
        for (int i = 1; i <= count; i++) {
            disk.getTracklist().add(new Track("Singer " + i + " - Some song", (int) (150 + Math.random() * 150),
                    Genres.values()[(int) (Math.random() * countGenres)].toString()));
        }
    }

    public static String getTotalDuration(List<Track> tracklist) {
        int total = 0;
        for (Track track : tracklist) {
            total += track.getTrackDuration();
        }
        long hours = TimeUnit.SECONDS.toHours(total);
        total -= TimeUnit.HOURS.toSeconds(hours);
        long minutes = TimeUnit.SECONDS.toMinutes(total);
        total -= TimeUnit.MINUTES.toSeconds(minutes);
        long seconds = TimeUnit.SECONDS.toSeconds(total);
        if (hours > 0) {
            return hours + " hours " + minutes + " min " + seconds + " sec";
        } else {
            return minutes + " min " + seconds + " sec";
        }
    }
    
    public static void deleteCurrentDisk () {
        DiskList.getDiskList().remove(DiskList.getCurrentDisk());
        if (DiskList.getDiskList().size() > 0) {
            DiskList.setCurrentDisk(DiskList.getDiskList().get(DiskList.getDiskList().size() - 1));
        } else {
            DiskList.setCurrentDisk(null);
        }
    }
}
