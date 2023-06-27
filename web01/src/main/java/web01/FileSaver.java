package ch01;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileSaver {
    private static final String DOWNLOAD_DIRECTORY = "/download/saveResults";

    public static void saveToFile(List<String> contents, String fileName) {
        try (FileWriter writer = new FileWriter(DOWNLOAD_DIRECTORY + "/" + fileName)) {
            for (String content : contents) {
                writer.write(content);
                writer.write(System.lineSeparator());
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
