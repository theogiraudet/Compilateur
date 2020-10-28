package TP2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class UtilsFile {

    public static String getFileContent(String path) throws IOException {
        final String url = Objects.requireNonNull(UtilsFile.class.getClassLoader().getResource(".")).getPath();
        final String completePath = url.substring(1, url.length() - 8).replaceAll("%20", " ") + "resources/" + path;
        return String.join("\n", Files.readAllLines(Paths.get(completePath)));
    }

}
