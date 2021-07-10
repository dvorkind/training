package by.dvorkin.recording.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {
    private static String trackName;
    private static int trackDuration;
    private static String trackGenre;
    public static List<Composition> tracklist = new ArrayList<Composition>();

    public static void load() throws FileNotFoundException {
        File library = new File("D:/library.txt");
        Scanner libraryScanner = new Scanner(library);
        while (libraryScanner.hasNextLine()) {
            String lib = libraryScanner.nextLine();
            String[] words = lib.split(",");
            trackName = words[0];
            trackDuration = Integer.parseInt(words[1]);
            trackGenre = Styles.values()[Integer.parseInt(words[2])].toString();
            tracklist.add(new Composition(trackName, trackDuration, trackGenre));
        }
        libraryScanner.close();
    }

    public static void create(int col) {

    }
}
