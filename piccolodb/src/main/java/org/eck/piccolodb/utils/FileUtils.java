package org.eck.piccolodb.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

import org.eck.piccolodb.exceptions.PiccoloDBFileNotFoundException;

public class FileUtils {
    public static String readFile(String path) {
        StringBuilder ret = new StringBuilder();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                ret.append(line).append("\n\r");
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new PiccoloDBFileNotFoundException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }

        return ret.toString();
    }

    public static void writeFile(String content, File file) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (FileNotFoundException e) {
            throw new PiccoloDBFileNotFoundException();
        }
    }

    public static File createFile(Path path) {
    	File file = path.toFile();

        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (FileNotFoundException e) {
                throw new PiccoloDBFileNotFoundException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }
}
