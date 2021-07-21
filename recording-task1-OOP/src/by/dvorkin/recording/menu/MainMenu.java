package by.dvorkin.recording.menu;

import by.dvorkin.recording.menu.submenu.CurrentDiskMenu;
import by.dvorkin.recording.menu.submenu.LoadFileMenu;
import by.dvorkin.recording.menu.submenu.SelectDiskMenu;
import by.dvorkin.recording.menu.submenu.SongsGeneratingMenu;
import by.dvorkin.recording.utils.MenuUtils;

public class MainMenu extends AbstractMenu {
    private SongsGeneratingMenu songsGeneratingMenu;
    private LoadFileMenu loadFileMenu;
    private SelectDiskMenu selectDiskMenu;
    private CurrentDiskMenu currentDiskMenu;

    public void setSongsGeneratingMenu(SongsGeneratingMenu songsGeneratingMenu) {
        this.songsGeneratingMenu = songsGeneratingMenu;
    }

    public CurrentDiskMenu getCurrentDiskMenu() {
        return currentDiskMenu;
    }

    public void setLoadFileMenu(LoadFileMenu loadFileMenu) {
        this.loadFileMenu = loadFileMenu;
    }

    public void setSelectDiskMenu(SelectDiskMenu selectDiskMenu) {
        this.selectDiskMenu = selectDiskMenu;
    }

    public void setCurrentDiskMenu(CurrentDiskMenu currentDiskMenu) {
        this.currentDiskMenu = currentDiskMenu;
    }

    @Override
    public void printMenu() {
        System.out.println("\n1. Generate new disk");
        System.out.println("2. Load existing disk");
        System.out.println("3. Show existing disks");
        System.out.println("4. Select a disk from existing ones");
        if (getCurrentDisk() != null) {
            System.out.println("5. Work with current disk");
        }
        System.out.println("0. Exit");
        System.out.print("Please, select an option: ");
    }

    public void userInput() {
        while (true) {
            String userInput = getMenuScanner().next();
            switch (userInput) {
                case "1":
                    songsGeneratingMenu.printMenu();
                    break;
                case "2":
                    loadFileMenu.printMenu();
                    break;
                case "3":
                    MenuUtils.printAllExistingDisk();
                    break;
                case "4":
                    if (MenuUtils.isAnyDiskOpened()) {
                        MenuUtils.printAllExistingDisk();
                        selectDiskMenu.printMenu();
                    } else {
                        System.out.print("\n\tFirst you need to generate or open any disk! \n");
                    }
                    break;
                case "5":
                    if (MenuUtils.isAnyDiskOpened()) {
                        currentDiskMenu.printMenu();
                        currentDiskMenu.userInput();
                    }
                    break;
                case "0":
                    System.out.println("\n\tProgram completed!");
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
