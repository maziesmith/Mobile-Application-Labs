package com.spencer.wille.quizws;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by wille on 4/21/2017.
 */

public class MyService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        // START YOUR TASKS
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // STOP YOUR TASKS
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
}
