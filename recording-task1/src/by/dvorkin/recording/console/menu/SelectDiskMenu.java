package by.dvorkin.recording.console.menu;

import by.dvorkin.recording.console.Runner;

public class SelectDiskMenu {
    public static void printSubmenu() {
        while (true) {
            System.out.print("\nEnter the number of disk: ");
            if (MainMenu.menuScanner.hasNextInt()) {
                int reqDiskNumber = MainMenu.menuScanner.nextInt();
                if (reqDiskNumber <= Runner.getDiskList().size()) {
                    Runner.setCurrentDisk(Runner.getDiskList().get(reqDiskNumber - 1));
                    System.out.println(
                            "\nCURRENT OPEN DISK NAME [" + Runner.getCurrentDisk().getName() + "]");
                    MenuUtils.printTracklist(Runner.getCurrentDisk().getTracklist());
                    break;
                } else {
                    System.out.println("\n\tWrong disk number!");
                }
            } else {
                System.out.println("\n\tOnly numbers can be entered!\n");
                MainMenu.menuScanner.next();
            }
        }
    }
}
