package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.entities.Disk;
import by.dvorkin.recording.entities.DiskList;
import by.dvorkin.recording.menu.MainMenu;
import by.dvorkin.recording.utils.DiskUtils;
import by.dvorkin.recording.utils.MenuUtils;
import by.dvorkin.recording.utils.TrackListUtils;

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
                if (MenuUtils.isAnyDiskOpened() && MenuUtils.isTracklistNotEmpty()) {
                    System.out.println("\nCURRENT OPEN DISK NAME [" + disk.getName() + "]");
                    TrackListUtils.printTracklist(disk.getTracklist());
                }
                break;
            case "2":
                if (MenuUtils.isAnyDiskOpened() && MenuUtils.isTracklistNotEmpty()) {
                    SortDiskMenu.printSubmenu(disk);
                }
                break;
            case "3":
                if (MenuUtils.isAnyDiskOpened() && MenuUtils.isTracklistNotEmpty()) {
                    FindByDurationMenu.printSubmenu(disk.getTracklist().getTracks());
                }
                break;
            case "4":
                AddSongMenu.printSubmenu(disk.getTracklist().getTracks());
                break;
            case "5":
                if (MenuUtils.isAnyDiskOpened() && MenuUtils.isTracklistNotEmpty()) {
                    RemoveSongMenu.printSubmenu(disk.getTracklist().getTracks());
                }
                break;
            case "6":
                disk.getTracklist().getTracks().clear();
                System.out.println("\n\tThe disc tracklist has been cleared!");
                break;
            case "7":
                if (DiskList.getInstance().getDiskList().size() < 2) {
                    System.out.println("\n\tCurrently only the current disc is available!");
                    System.out.println("\tPlease go back to the main menu and open or create other discs.");
                } else {
                    MergeDiskMenu.printSubmenu(disk.getTracklist());
                }
                break;
            case "8":
                String diskName = disk.getName();
                DiskUtils.deleteCurrentDisk();
                System.out.println("\n\tThe disk [" + diskName + "] has been deleted!");
                userInput = "0";
                break;
            case "9":
                SaveFileMenu.printSubmenu(disk.getTracklist().getTracks());
                break;
            case "0":
                break;
            default:
                System.out.println("\n\tWrong command number!");
                break;
            }
            if ("0".equals(userInput)) {
                break;
            } else {
                CurrentDiskMenu.printSubmenu();
            }
        }
    }
}
