package utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FilesUtils {
    private FilesUtils(){}

    public static File getLatestFile(String folderPath) { // 1 usage
        File dir = new File(folderPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }
        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        return lastModifiedFile;
    }

    public static void deleteFiles(File dirPath) { // 1 usage

            if (dirPath == null || !dirPath.exists()) {
                LogsUtils.warn(" Directory does not exist : " + dirPath);
                return;
            }

            File[] filesList = dirPath.listFiles();
            if (filesList == null) {
                LogsUtils.warn(" Failed to list files in : " + dirPath);
                return;
            }

            for (File file : filesList) {
                if (file.isDirectory()) {
                    deleteFiles(file);
                } else {
                    try {
                        Files.delete(file.toPath());
                    } catch (IOException e) {
                        LogsUtils.error(" Failed to delete file : " + file);
                    }
                }
            }
    }

    public static void cleanDirectory(File file) { // 1 usage
        try {
            FileUtils.cleanDirectory(file);
        } catch (IOException exception) {
            LogsUtils.error(exception.getMessage());
        }
    }

}
