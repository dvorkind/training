package by.dvorkin.recording.menu.submenu;

import by.dvorkin.recording.menu.AbstractMenu;
import by.dvorkin.recording.utils.DiskUtils;
import by.dvorkin.recording.utils.MenuUtils;
import by.dvorkin.recording.utils.TrackListUtils;

public class CurrentDiskMenu extends AbstractMenu {
    private SortDiskMenu sortDiskMenu;
    private FindByDurationMenu findByDurationMenu;
    private AddSongMenu addSongMenu;
    private RemoveSongMenu removeSongMenu;
    private MergeDiskMenu mergeDiskMenu;
    private SaveFileMenu saveFileMenu;

    public void setSortDiskMenu(SortDiskMenu sortDiskMenu) {
        this.sortDiskMenu = sortDiskMenu;
    }

    public void setFindByDurationMenu(FindByDurationMenu findByDurationMenu) {
        this.findByDurationMenu = findByDurationMenu;
    }

    public void setAddSongMenu(AddSongMenu addSongMenu) {
        this.addSongMenu = addSongMenu;
    }

    public void setRemoveSongMenu(RemoveSongMenu removeSongMenu) {
        this.removeSongMenu = removeSongMenu;
    }

    public void setMergeDiskMenu(MergeDiskMenu mergeDiskMenu) {
        this.mergeDiskMenu = mergeDiskMenu;
    }

    public void setSaveFileMenu(SaveFileMenu saveFileMenu) {
        this.saveFileMenu = saveFileMenu;
    }

    @Override
    public void printMenu() {
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

    public void userInput() {
        while (true) {
            String userInput = getMenuScanner().next();
            switch (userInput) {
                case "1":
                    if (MenuUtils.isTracklistNotEmpty()) {
                        System.out.println("\nCURRENT OPEN DISK NAME [" + getCurrentDisk().getName() + "]");
                        TrackListUtils.printTracklist(getCurrentDisk().getTracklist());
                    } else {
                        System.out.println("\n\tThe current disk contains no songs!");
                    }
                    break;
                case "2":
                    if (MenuUtils.isTracklistNotEmpty()) {
                        sortDiskMenu.printMenu();
                    } else {
                        System.out.println("\n\tThe current disk contains no songs!");
                    }
                    break;
                case "3":
                    if (MenuUtils.isTracklistNotEmpty()) {
                        findByDurationMenu.printMenu();
                    } else {
                        System.out.println("\n\tThe current disk contains no songs!");
                    }
                    break;
                case "4":
                    addSongMenu.printMenu();
                    break;
                case "5":
                    if (MenuUtils.isTracklistNotEmpty()) {
                        removeSongMenu.printMenu();
                    } else {
                        System.out.println("\n\tThe current disk contains no songs!");
                    }
                    break;
                case "6":
                    getCurrentDisk().getTracklist()
                            .getTracks()
                            .clear();
                    System.out.println("\n\tThe disc tracklist has been cleared!");
                    break;
                case "7":
                    if (getDiskList()
                            .size() < 2) {
                        System.out.println("\n\tCurrently only the current disc is available!");
                        System.out.println("\tPlease go back to the main menu and open or create other discs.");
                    } else {
                        mergeDiskMenu.printMenu();
                    }
                    break;
                case "8":
                    String diskName = getCurrentDisk().getName();
                    DiskUtils.deleteCurrentDisk();
                    System.out.println("\n\tThe disk [" + diskName + "] has been deleted!");
                    userInput = "0";
                    break;
                case "9":
                    saveFileMenu.printMenu();
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
                printMenu();
            }
        }
    }
}
