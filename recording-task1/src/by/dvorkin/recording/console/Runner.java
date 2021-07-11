package by.dvorkin.recording.console;

import java.util.Scanner;

import by.dvorkin.recording.model.Composition;
import by.dvorkin.recording.model.Disk;

public class Runner {
    private static Scanner scanner = new Scanner(System.in);
    private static Scanner scanner2 = new Scanner(System.in);

    public static void invoice() {
        System.out.print("\nPlease, enter the number of the required action: ");
    }

    public static void showMenu() {
        System.out.println("1. Generate disk");
        System.out.println("2. Load existing disk");
        System.out.println("3. Save current disk");
        System.out.println("4. Sort songs by genre");
        System.out.println("5. Sort songs by name");
        System.out.println("6. Sort songs by duration");
        System.out.println("7. Calculate total duration");
        System.out.println("0. Exit");
        invoice();
    }

    public static void getTracklist(Disk tracklist, String msg) {
        int num = 0;
        System.out.println("\nDisk [" + tracklist.getName() + "] " + msg);
        for (Composition t : tracklist) {
            num++;
            System.out.println(num + ". " + t.toString());
        }
    }

    public static void sort(Disk disk, String msg, int sortItem) {
        if (!disk.isEmpty()) {
            switch (sortItem) {
            case 1:
                disk.sortByGenre();
                break;
            case 2:
                disk.sortByName();
                break;
            case 3:
                disk.sortByDuration();
                break;
            }
            getTracklist(disk, msg);
            invoice();
        } else {
            System.out.print("\n\tFirst you need to generate or open the disk! \n");
            invoice();
        }
    }

    public static void userInput() {
        boolean waitForInput = true;
        Disk disk = new Disk();
        while (waitForInput) {
            String userInput = "";
            userInput = scanner.next();
            switch (userInput) {
            case "1":
                String reqDiskName = "";
                while (reqDiskName == "") {
                    System.out.print("\nEnter disk name: ");
                    reqDiskName = scanner2.next();
                }
                disk.setName(reqDiskName);
                int reqNumberOfSongs = 0;
                while (reqNumberOfSongs <= 0) {
                    System.out.print("Enter the number of songs: ");
                    if (scanner2.hasNextInt()) {
                        reqNumberOfSongs = scanner2.nextInt();
                    } else {
                        System.out.println("\n\tOnly numbers can be entered!\n");
                        scanner2.next();
                    }
                }
                disk.generate(reqNumberOfSongs);
                getTracklist(disk, "");
                invoice();
                break;
            case "2":
                System.out.print("\nEnter file path to load (for example \"D:\\MyDisk.txt\"): ");
                String reqOpenFile = "";
                reqOpenFile = scanner2.next();
                if (disk.load(reqOpenFile)) {
                    getTracklist(disk, "");
                    invoice();
                } else {
                    invoice();
                }
                break;
            case "3":
                if (!disk.isEmpty()) {
                    String reqSaveFile = "";
                    while (reqSaveFile == "") {
                        System.out.print("\nEnter file path to save (for example \"D:\\MyDisk.txt\"): ");
                        reqSaveFile = scanner2.next();
                    }

                    if (disk.save(reqSaveFile)) {
                        System.out.println("\n\tDisk saved successfully!");
                        invoice();
                    } else {
                        invoice();
                    }
                } else {
                    System.out.print("\n\tFirst you need to generate or open the disk! \n");
                    invoice();
                }
                break;
            case "4":
                sort(disk, "(sorted by genre)", 1);
                break;
            case "5":
                sort(disk, "(sorted by name)", 2);
                break;
            case "6":
                sort(disk, "(sorted by duration)", 3);
                break;
            case "7":
                if (!disk.isEmpty()) {
                    System.out.println("\nTotal duration: " + disk.totalDuration());
                    invoice();
                } else {
                    System.out.print("\n\tFirst you need to generate or open the disk! \n");
                    invoice();
                }
                break;
            case "0":
                System.out.println("\n\tProgram completed!");
                waitForInput = false;
                break;
            case "?":
                showMenu();
                break;
            default:
                System.out.println("\n\tWrong command number!");
                showMenu();
                break;
            }
        }
        scanner.close();
        scanner2.close();
    }

    public static void main(String[] args) {
        showMenu();
        userInput();
    }

}
