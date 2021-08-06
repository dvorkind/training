package by.dvorkin.strings.main;

import by.dvorkin.strings.service.FileService;
import by.dvorkin.strings.service.ParserService;
import by.dvorkin.strings.service.TextHandlerService;

public class Main {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        ParserService parser = new ParserService();
        TextHandlerService textHandlerService = new TextHandlerService(parser.parse(fileService.readFile("strings-task2/resources/input.txt")));
        fileService.writeFile(textHandlerService.modifiedText(), "strings-task2/resources/output.txt");
        System.out.println("STATISTICS\n-----------------------\n" + textHandlerService.getStatistics());
    }
}
