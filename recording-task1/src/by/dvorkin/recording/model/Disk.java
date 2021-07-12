package by.dvorkin.recording.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

@SuppressWarnings("serial")
public class Disk extends ArrayList<Composition> {
    private String name;

    public String getName() {
        return name;
    }

    // retrieves filename without extension
    public void setNameFromFilename(String name) {
        int i = name.lastIndexOf('.');
        if (i != -1) {
            name = name.substring(0, i);
        }
        this.name = name;
    }

    public Boolean loadFile(String fileName) {
        Scanner fileScanner = null;
        try {
            this.clear();
            File file = new File(fileName);
            this.setNameFromFilename(file.getName());
            fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String fileLine = fileScanner.nextLine();
                String[] words = fileLine.split(",");
                this.add(new Composition(words[0], Integer.parseInt(words[1]), words[2]));
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

    public Boolean saveFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (Composition track : this) {
                writer.write(
                        track.getTrackName() + "," + track.getTrackDuration() + "," + track.getTrackGenre() + "\n");
            }
            writer.close();
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public void generate(int count) {
        this.clear();
        int countGenres = Genres.values().length;
        for (int i = 1; i <= count; i++) {
            this.add(new Composition("Singer " + i + " - Some song", (int) (150 + Math.random() * 150),
                    Genres.values()[(int) (Math.random() * countGenres)].toString()));
        }
    }

    public void sortByGenre() {
        this.sort(Comparator.comparing(Composition::getTrackGenre).reversed()
                .thenComparing(Composition::getTrackDuration).reversed());
    }

    public void sortByName() {
        this.sort(Comparator.comparing(Composition::getTrackName).reversed().thenComparing(Composition::getTrackGenre)
                .reversed());
    }

    public void sortByDuration() {
        this.sort(Comparator.comparing(Composition::getTrackDuration).thenComparing(Composition::getTrackName)
                .reversed());
    }

    public String totalDuration() {
        int total = 0;
        for (Composition track : this) {
            total += track.getTrackDuration();
        }
        if (total >= 3600) {
            return (total / 3600) + " hours " + ((total / 60) % 60) + " min " + (total % 60) + " sec";
        } else {
            return total / 60 + " min " + total % 60 + " sec ";
        }
    }
}
