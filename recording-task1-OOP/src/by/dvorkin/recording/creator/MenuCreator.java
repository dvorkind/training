package by.dvorkin.recording.creator;

import by.dvorkin.recording.interfaces.DiskList;
import by.dvorkin.recording.menu.MainMenu;
import by.dvorkin.recording.menu.submenu.*;

public class MenuCreator {
    public MenuCreator(DiskList instance) {
        MainMenu mainMenu = new MainMenu(instance);
        mainMenu.setSongsGeneratingMenu(new SongsGeneratingMenu(instance));
        mainMenu.setLoadFileMenu(new LoadFileMenu(instance));
        mainMenu.setSelectDiskMenu(new SelectDiskMenu(instance));
        mainMenu.setCurrentDiskMenu(new CurrentDiskMenu(instance));

        mainMenu.getCurrentDiskMenu()
                .setSortDiskMenu(new SortDiskMenu(instance));
        mainMenu.getCurrentDiskMenu()
                .setSortDiskMenu(new SortDiskMenu(instance));
        mainMenu.getCurrentDiskMenu()
                .setFindByDurationMenu(new FindByDurationMenu(instance));
        mainMenu.getCurrentDiskMenu()
                .setAddSongMenu(new AddSongMenu(instance));
        mainMenu.getCurrentDiskMenu()
                .setRemoveSongMenu(new RemoveSongMenu(instance));
        mainMenu.getCurrentDiskMenu()
                .setMergeDiskMenu(new MergeDiskMenu(instance));
        mainMenu.getCurrentDiskMenu()
                .setSaveFileMenu(new SaveFileMenu(instance));

        mainMenu.printMenu();
        mainMenu.userInput();
    }
}
