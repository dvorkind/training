package by.dvorkin.strings.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class fileReaderService {
    private String fileName;

    public fileReaderService(String fileName) {
        this.fileName = fileName;
    }

    public String readFile() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "Windows-1251"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
