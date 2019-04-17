package com.example.module_habit.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.lib_common.bean.MusicItem;
import com.example.lib_common.music.MusicHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tianhuaye
 * @date: 2019/1/18 13:48
 * @description:
 */
public class AlarmBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String ring=intent.getStringExtra("ringPath");
        MusicItem musicItem=new MusicItem();
        musicItem.setMusicId(100000);
        musicItem.setLocalFile(ring);
        musicItem.setDownloadWhenPlaying(false);
        List<MusicItem> musicItemList=new ArrayList<>();
        musicItemList.add(musicItem);
        MusicHelper.getInstance().initMusicItem(musicItemList,100000,true,null);
        
//        ToastUtil.showShortToastMessage(context, intent.getStringExtra("msg"));
    }
}
