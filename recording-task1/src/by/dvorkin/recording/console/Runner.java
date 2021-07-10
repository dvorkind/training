package by.dvorkin.recording.console;

import java.io.FileNotFoundException;
import java.util.List;

import by.dvorkin.recording.model.Composition;
import by.dvorkin.recording.model.Library;

public class Runner {
    public static void getTracklist(List<Composition> tracklist) {
        int num = 0;
        for (Composition t : tracklist) {
            num++;
            System.out.println(num + ". " + t.getTrackName());
        }
        System.out.println();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Library.load();
        getTracklist(Library.tracklist);
    }

}
