package com.example.lib_common.util;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/23
 * @time : 14:36
 * @email : 15869107730@163.com
 * @note :存储相关工具类
 */
public class StorageUtil {

    private static final Object sLock = new Object();

    /**
     * 获取sd卡（外置存储）的可用容量
     *
     * @return 可用容量
     */
    public static long getSDAvailableStore() {
        long availableSpace = -1L;
        if (isSDCardValid()) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize;
            long availCount;
            if (Build.VERSION.SDK_INT >= 18) {
                blockSize = sf.getBlockSizeLong();
                availCount = sf.getAvailableBlocksLong();
            } else {
                blockSize = sf.getBlockSize();
                availCount = sf.getAvailableBlocks();
            }

            availableSpace = blockSize * availCount;
        }

        return availableSpace;
    }

    public static boolean hasStorage(boolean requireWriteAccess) {
        try {
            String state = Environment.getExternalStorageState();

            if (MEDIA_MOUNTED.equals(state)) {
                if (requireWriteAccess) {
                    return checkFsWritable();
                } else {
                    return true;
                }
            } else if (!requireWriteAccess && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 检查外置存储是否存在(挂载)
     */
    public static boolean isSDCardValid() {
        return hasStorage(true);
    }

    private static boolean checkFsWritable() {
        // Create a temporary file to see whether a volume is really writeable.
        // It's important not to put it in the root directory which may have a
        // limit on the number of files.
        String directoryName = Environment.getExternalStorageDirectory().toString() + "/DCIM";
        File directory = new File(directoryName);
        if (!directory.isDirectory()) {
            synchronized (sLock) {
                if (!directory.mkdirs()) {
                    return false;
                }
            }
        }
        File f = new File(directoryName, ".probe");
        try {
            // Remove stale file if any
            synchronized (sLock) {
                if (f.exists()) {
                    f.delete();
                }
                if (!f.createNewFile()) {
                    return false;
                }
                f.delete();
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}
