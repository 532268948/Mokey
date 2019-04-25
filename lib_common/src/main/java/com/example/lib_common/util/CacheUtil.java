package com.example.lib_common.util;

import android.content.Context;
import android.os.Environment;

import com.example.lib_common.common.Constant;

import java.io.File;
import java.util.Date;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/17
 * @time : 13:39
 * @email : 15869107730@163.com
 * @note :
 */
public class CacheUtil {

    public static String getRecordSavePCMFilePath(Context context, Date date) {
        return getRecordPCMSavePath(context) + File.separator + Constant.USER_ID + File.separator + DateUtil.formatOne(date.getTime()) + File.separator + date.getTime() + ".pcm";
    }

    private static String getRecordPCMSavePath(Context context) {
        return Environment.getExternalStorageDirectory() + File.separator + "record" + File.separator + "pcm";
    }

    public static String getRecordPcmDatePath(String dir) {
        return Environment.getExternalStorageDirectory() + File.separator + "record" + File.separator + "pcm" + File.separator + Constant.USER_ID + File.separator + dir;
    }

    public static String getRecordSaveWAVFilePath(Context context, String pcmFileName) {
        return getRecordWAVSavePath(context) + File.separator + Constant.USER_ID + File.separator + DateUtil.formatOne((new Date()).getTime()) + File.separator + getFileName(pcmFileName) + ".wav";
    }

    public static String getRecordWavDatePath(String dir) {
        return Environment.getExternalStorageDirectory() + File.separator + "record" + File.separator + "wav" + File.separator + Constant.USER_ID + File.separator + dir;
    }

    private static String getRecordWAVSavePath(Context context) {
        return Environment.getExternalStorageDirectory() + File.separator + "record" + File.separator + "wav";
    }

    public static String getFileName(String path) {
        int start = path.lastIndexOf("/");
        int end = path.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return path.substring(start + 1, end);
        } else {
            return null;
        }
    }
}
