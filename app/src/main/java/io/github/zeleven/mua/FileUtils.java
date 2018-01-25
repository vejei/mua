package io.github.zeleven.mua;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileUtils {
    private static String className = FileUtils.class.getName();

    /**
     * Listing the files from specified file path
     * @param filesPath file path used to list files.
     * @return list which contain files.
     */
    public static List<File> listFiles(String filesPath) {
        File file = new File(filesPath);
        List<File> filesList = null;
        if (file.exists()) {
            filesList = Arrays.asList(file.listFiles());
        }
        return filesList;
    }

    /**
     * Create folder according specified file path.
     * @param filesPath file path used to create folder.
     */
    public static void createFolder(String filesPath) {
        File file = new File(filesPath);
        file.mkdirs();
    }

    /**
     * Save content to specified file.
     * @param filePath file path indicate the file which be written content.
     * @param content
     * @return if save success, return true, otherwise return false.
     */
    public static boolean saveFile(String filePath, String content) {
        boolean success;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveContent(file, content);
            success = true;
        } else {
            success = false;
        }
        return success;
    }

    /**
     * Rename file
     * @param oldFile the file which be renamed.
     * @param newFile target file.
     */
    public static void renameFile(File oldFile, File newFile) {
        if (!oldFile.exists() || !newFile.exists()) {
            Log.i(className, "File not found.");
            return;
        }
        oldFile.renameTo(newFile);
    }

    /**
     * Writing content to file.
     * @param file
     * @param content
     */
    public static void saveContent(File file, String content) {
        try {
            FileWriter fileWriter;
            fileWriter = new FileWriter(file.getAbsolutePath());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get filename which has no extension behind.
     * @param fileName
     * @return
     */
    public static String stripExtension(String fileName) {
        if (fileName == null) {
            return "";
        }

        // Get position of last '.'.
        int pos = fileName.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.
        if (pos == -1) {
            return fileName;
        }

        // Otherwise return the string, up to the dot.
        return fileName.substring(0, pos);
    }

    /**
     * Read content from specified file.
     * @param filePath
     * @return
     */
    public static String readContentFromPath(String filePath) {
        File file = new File(filePath);
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                content.append(line);
                content.append('\n');
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static String readContentFromFile(File file) {
        StringBuilder textContent = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                textContent.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(className, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(className, e.getMessage());
        }
        return textContent.toString();
    }

    public static void deleteFiles(List<File> fileList) {
        for (File file : fileList) {
            deleteFile(file);
        }
    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
            Log.i(className, "Delete success.");
        } else {
            Log.i(className, "File not found.");
        }
    }
}
