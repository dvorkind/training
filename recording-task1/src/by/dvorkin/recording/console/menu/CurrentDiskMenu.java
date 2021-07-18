package by.dvorkin.recording.console.menu;

import by.dvorkin.recording.model.Disk;
import by.dvorkin.recording.model.DiskList;
import by.dvorkin.recording.model.DiskUtils;

public class CurrentDiskMenu {
    public static void printSubmenu() {
        System.out.println("\n1. Show disk contents");
        System.out.println("2. Sort songs by something");
        System.out.println("3. Find songs by duration range");
        System.out.println("4. Add song");
        System.out.println("5. Remove song");
        System.out.println("6. Clear the tracklist");
        System.out.println("7. Merge with another disk");
        System.out.println("8. Delete current disk");
        System.out.println("9. Save current disk as...");
        System.out.println("0. Back to Main Menu");
        System.out.print("Please, select an option: ");
    }

    public static void userInput(Disk disk) {
        while (true) {
            String userInput = MainMenu.menuScanner.next();
            switch (userInput) {
            case "1":
                if (MenuUtils.isAnyDisk() && MenuUtils.isTracklistNotEmpty()) {
                    System.out.println("\nCURRENT OPEN DISK NAME [" + disk.getName() + "]");
                    MenuUtils.printTracklist(disk.getTracklist());
                }
                break;
            case "2":
                if (MenuUtils.isAnyDisk() && MenuUtils.isTracklistNotEmpty()) {
                    SortDiskMenu.printSubmenu(disk);
                }
                break;
            case "3":
                if (MenuUtils.isAnyDisk() && MenuUtils.isTracklistNotEmpty()) {
                    FindByDurationMenu.printSubmenu(disk);
                }
                break;
            case "4":
                AddSongMenu.printSubmenu(disk);
                break;
            case "5":
                if (MenuUtils.isAnyDisk() && MenuUtils.isTracklistNotEmpty()) {
                    RemoveSongMenu.printSubmenu(disk);
                }
                break;
            case "6":
                disk.getTracklist().clear();
                System.out.println("\n\tThe disc tracklist has been cleared!");
                break;
            case "7":
                if (DiskList.getDiskList().size() < 2) {
                    System.out.println("\n\tCurrently only the current disc is available!");
                    System.out.println("\tPlease go back to the main menu and open or create other discs.");
                } else {
                    MergeDiskMenu.printSubmenu(disk);
                }
                break;
            case "8":
                String diskName = disk.getName();
                DiskUtils.deleteCurrentDisk();
                System.out.println("\n\tThe disk [" + diskName + "] has been deleted!");
                userInput = "0";
                break;
            case "9":
                SaveFileMenu.printSubmenu(disk);
                break;
            default:
                System.out.println("\n\tWrong command number!");
                break;
            }
            if (userInput.equals("0")) {
                break;
            } else {
                CurrentDiskMenu.printSubmenu();
            }
        }
    }
}
