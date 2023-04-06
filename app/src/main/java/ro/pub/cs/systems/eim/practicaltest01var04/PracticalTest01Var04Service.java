package ro.pub.cs.systems.eim.practicaltest01var04;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTest01Var04Service extends Service {

    ProcessingThread processingThread = null;

    public PracticalTest01Var04Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        String name = intent.getStringExtra("name");
        String group = intent.getStringExtra("group");

        if (processingThread != null) {
            processingThread.stopThread();
        }
        processingThread = new ProcessingThread(this, name, group);
        processingThread.start();

        return START_STICKY;
    }
}