package com.example.lib_common.util;

import java.io.File;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/2/6
 * @time : 14:20
 * @email : 15869107730@163.com
 * @note :
 */
public class FileUtil {
    public static void deleteFile(final String path) {
        try {
            File rootFile = new File(path);
            if (!rootFile.exists()) return;
            if (rootFile.isDirectory())// 如果是文件夹
            {
                File file[] = rootFile.listFiles();
                for (File file2 : file) {
                    deleteFile(file2.getAbsolutePath());
                }
                rootFile.delete();
            } else {
                rootFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean checkFileExist(String path) {
        try {
            File file = new File(path);
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
