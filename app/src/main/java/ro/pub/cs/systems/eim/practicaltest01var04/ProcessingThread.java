package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;

public class ProcessingThread extends Thread {

    private boolean isRunning = true;
    private Context context = null;
    private String name = null;
    private String group = null;

    private boolean toggle = false;


    public ProcessingThread(Context context, String name, String group) {
        this.context = context;
        this.name = name;
        this.group = group;
    }

    @Override
    public void run() {
        while (isRunning) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = simpleDateFormat.format(System.currentTimeMillis());

            String message = toggle ? name : group;

            context.sendBroadcast(new Intent(Constants.action1)
                    .putExtra(Constants.SERVICE_LOG,
                            date + " " + message));

            toggle = !toggle;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
