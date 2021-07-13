package by.dvorkin.recording.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DiskUtils {
    // retrieves filename without extension
    public static void setDiskNameFromFilename(Disk disk, String name) {
        int i = name.lastIndexOf('.');
        if (i != -1) {
            name = name.substring(0, i);
        }
        disk.setName(name);
    }

    public static void loadFile(Disk disk, String fileName) {
        Scanner fileScanner = null;
        try {
            File file = new File(fileName);
            setDiskNameFromFilename(disk, file.getName());
            fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String fileLine = fileScanner.nextLine();
                String[] words = fileLine.split(",");
                disk.getDisk().add(new Track(words[0], Integer.parseInt(words[1]), words[2]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("\n\tFile doesn't exist!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\n\tIncorrect file format!");
        } finally {
            if (fileScanner != null) {
                fileScanner.close();
            }
        }
    }

    public static void saveFile(Disk disk, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (Track track : disk.getDisk()) {
                writer.write(
                        track.getTrackName() + "," + track.getTrackDuration() + "," + track.getTrackGenre() + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void generateRandomTracks(Disk disk, int count) {
        int countGenres = Genres.values().length;
        for (int i = 1; i <= count; i++) {
            disk.getDisk().add(new Track("Singer " + i + " - Some song", (int) (150 + Math.random() * 150),
                    Genres.values()[(int) (Math.random() * countGenres)].toString()));
        }
    }

    public static String getTotalDuration(Disk disk) {
        int total = 0;
        for (Track track : disk.getDisk()) {
            total += track.getTrackDuration();
        }
        if (total >= 3600) {
            return (total / 3600) + " hours " + ((total / 60) % 60) + " min " + (total % 60) + " sec";
        } else {
            return total / 60 + " min " + total % 60 + " sec ";
        }
    }
}
