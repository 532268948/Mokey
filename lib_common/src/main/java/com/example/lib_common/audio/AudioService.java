package com.example.lib_common.audio;

import android.app.IntentService;
import android.content.Intent;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2019/4/15
 * @time : 17:10
 * @email : 15869107730@163.com
 * @note :
 */
public class AudioService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public AudioService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

}
