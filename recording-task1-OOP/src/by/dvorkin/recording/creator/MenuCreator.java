package by.dvorkin.recording.creator;

import by.dvorkin.recording.menu.MainMenu;
import by.dvorkin.recording.menu.submenu.*;

public class MenuCreator {
    public MenuCreator() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setSongsGeneratingMenu(new SongsGeneratingMenu());
        mainMenu.setLoadFileMenu(new LoadFileMenu());
        mainMenu.setSelectDiskMenu(new SelectDiskMenu());
        mainMenu.setCurrentDiskMenu(new CurrentDiskMenu());

        mainMenu.getCurrentDiskMenu()
                .setSortDiskMenu(new SortDiskMenu());
        mainMenu.getCurrentDiskMenu()
                .setSortDiskMenu(new SortDiskMenu());
        mainMenu.getCurrentDiskMenu()
                .setFindByDurationMenu(new FindByDurationMenu());
        mainMenu.getCurrentDiskMenu()
                .setAddSongMenu(new AddSongMenu());
        mainMenu.getCurrentDiskMenu()
                .setRemoveSongMenu(new RemoveSongMenu());
        mainMenu.getCurrentDiskMenu()
                .setMergeDiskMenu(new MergeDiskMenu());
        mainMenu.getCurrentDiskMenu()
                .setSaveFileMenu(new SaveFileMenu());

        mainMenu.printMenu();
        mainMenu.userInput();
    }
}
