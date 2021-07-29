package by.dvorkin.recording.creator;

import by.dvorkin.recording.interfaces.DiskList;
import by.dvorkin.recording.menu.MainMenu;
import by.dvorkin.recording.menu.submenu.AddSongMenu;
import by.dvorkin.recording.menu.submenu.CurrentDiskMenu;
import by.dvorkin.recording.menu.submenu.FindByDurationMenu;
import by.dvorkin.recording.menu.submenu.LoadFileMenu;
import by.dvorkin.recording.menu.submenu.MergeDiskMenu;
import by.dvorkin.recording.menu.submenu.RemoveSongMenu;
import by.dvorkin.recording.menu.submenu.SaveFileMenu;
import by.dvorkin.recording.menu.submenu.SelectDiskMenu;
import by.dvorkin.recording.menu.submenu.SongsGeneratingMenu;
import by.dvorkin.recording.menu.submenu.SortDiskMenu;

import java.util.Scanner;

public class MenuCreator {
    public MenuCreator(DiskList instance, Scanner scanner) {
        MainMenu mainMenu = new MainMenu(instance, scanner);
        mainMenu.setSongsGeneratingMenu(new SongsGeneratingMenu(instance, scanner));
        mainMenu.setLoadFileMenu(new LoadFileMenu(instance, scanner));
        mainMenu.setSelectDiskMenu(new SelectDiskMenu(instance, scanner));
        mainMenu.setCurrentDiskMenu(new CurrentDiskMenu(instance, scanner));

        mainMenu.getCurrentDiskMenu()
                .setSortDiskMenu(new SortDiskMenu(instance, scanner));
        mainMenu.getCurrentDiskMenu()
                .setSortDiskMenu(new SortDiskMenu(instance, scanner));
        mainMenu.getCurrentDiskMenu()
                .setFindByDurationMenu(new FindByDurationMenu(instance, scanner));
        mainMenu.getCurrentDiskMenu()
                .setAddSongMenu(new AddSongMenu(instance, scanner));
        mainMenu.getCurrentDiskMenu()
                .setRemoveSongMenu(new RemoveSongMenu(instance, scanner));
        mainMenu.getCurrentDiskMenu()
                .setMergeDiskMenu(new MergeDiskMenu(instance, scanner));
        mainMenu.getCurrentDiskMenu()
                .setSaveFileMenu(new SaveFileMenu(instance, scanner));

        mainMenu.printMenu();
        mainMenu.userInput();
    }
}
