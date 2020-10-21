package TP2;

import TP2.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class UtilsFile {

    private static String toPath(String name) {
        return "../../../tests/" + name;
    }

    public static String getFileContent(String path) throws IOException {
        final String url = Thread.currentThread().getContextClassLoader().getResource(".").getPath();
        final String completePath = url.substring(1, url.length() - 8).replaceAll("%20", " ") + "resources/" + path;
        return Files.readAllLines(Paths.get(completePath)).stream().collect(Collectors.joining("\n"));
    }

}
