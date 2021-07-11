package by.dvorkin.recording.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("serial")
public class Disk extends ArrayList<Composition> {
    private String name;

    public Disk(String name) {
        this.name = name;
    }

    public Disk() {
        this.name = "Unnamed";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        int i = name.lastIndexOf('.');
        if (i != -1) {
            name = name.substring(0, i);
        }
        this.name = name;
    }

    public Boolean load(String fileName) {
        Scanner libraryScanner = null;
        try {
            this.clear();
            File library = new File(fileName);
            this.setName(library.getName());
            libraryScanner = new Scanner(library);
            while (libraryScanner.hasNextLine()) {
                String lib = libraryScanner.nextLine();
                String[] words = lib.split(",");
                this.add(new Composition(words[0], Integer.parseInt(words[1]), Integer.parseInt(words[2])));
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("\n\tFile doesn't exist!");
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\n\tIncorrect file format!");
            return false;
        } finally {
            if (libraryScanner != null) {
                libraryScanner.close();
            }
        }
    }

    public Boolean save(String fileName) {
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

    public void generate(int col) {
        this.clear();
        for (int i = 1; i <= col; i++) {
            this.add(new Composition("Singer " + i + " — Some song", ThreadLocalRandom.current().nextInt(150, 300 + 1),
                    ThreadLocalRandom.current().nextInt(0, 12 + 1)));
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
