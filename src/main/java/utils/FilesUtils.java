package utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Utility class for performing common file system operations.
 */
public class FilesUtils {
    //Private constructor to prevent instantiation of this utility class.
    private FilesUtils(){}
    /**
     * Retrieves the most recently modified file within a specified directory.
     *
     * @param folderPath The path to the directory to search within.
     * @return The File object representing the latest modified file, or null if the directory
     * does not exist or is empty.
     */
    public static File getLatestFile(String folderPath) {

        File dir = new File(folderPath);
        File[] files = dir.listFiles();
        // Check if the directory exists and contains files
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        // Iterate through the files to find the one with the latest modification time
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        return lastModifiedFile;
    }

    /**
     * Recursively deletes all files and subdirectories within a given directory.
     * Logs warnings if the directory does not exist or if listing files fails,
     * and logs errors if individual file deletion fails.
     *
     * @param dirPath The File object representing the directory to delete.
     */
    public static void deleteFiles(File dirPath) {

            // Check if the directory path is null or the directory does not exist
            if (dirPath == null || !dirPath.exists()) {
                LogsUtils.warn(" Directory does not exist : " + dirPath);
                return;
            }

        File[] filesList = dirPath.listFiles();
        // Check if listing files in the directory was successful
        if (filesList == null) {
            LogsUtils.warn(" Failed to list files in : " + dirPath);
            return;
        }

        // Iterate through the list of files and subdirectories
        for (File file : filesList) {
            if (file.isDirectory()) {
                deleteFiles(file);
            } else {
                // If it's a file, attempt to delete it
                try {
                    Files.delete(file.toPath());
                } catch (IOException e) {
                    LogsUtils.error(" Failed to delete file : " + file);
                }
            }
        }
    }

    /**
     * Deletes all files and subdirectories within a specified directory using Apache Commons IO's FileUtils.cleanDirectory method.
     * Logs any IOException that occurs during the cleaning process.
     *
     * @param file The File object representing the directory to clean.
     */
    public static void cleanDirectory(File file) { // 1 usage
        try {
            // Use FileUtils.cleanDirectory from Apache Commons IO to safely delete directory contents
            FileUtils.cleanDirectory(file);
        } catch (IOException exception) {
            LogsUtils.error(exception.getMessage());
        }
    }

}
