package io.github.zeleven.mua;

import android.os.Environment;

public class StorageHelper {
    // The state of external storage
    private static String state = Environment.getExternalStorageState();

    /**
     * Checks if external storage is available for read and write
     * @return If writable return true, otherwise return false
     */
    public static boolean isExternalStorageWritable() {
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if external storage is available to at least read
     * @return If readable return true, otherwise return false
     */
    public static boolean isExternalStorageReadable() {
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
