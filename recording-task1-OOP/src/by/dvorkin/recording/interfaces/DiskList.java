package by.dvorkin.recording.interfaces;

import by.dvorkin.recording.entities.Disk;

import java.util.List;

public interface DiskList {
    Disk getCurrentDisk();

    void setCurrentDisk(Disk currentDisk);

    List<Disk> getDiskList();

    void addToDiskList(Disk disk);

    Boolean isTracklistNotEmpty();

    void deleteCurrentDisk();
}
