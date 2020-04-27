package com.waitty.kitchen.utility;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import com.waitty.kitchen.R;
import com.waitty.kitchen.constant.constant;

public class NotificationHelper extends ContextWrapper {

    private NotificationManager manager;

    // Class constructor
    public NotificationHelper(Context ctx) {
        super(ctx);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel chan1 = new NotificationChannel(constant.PRIMARY_CHANNEL, getString(R.string.txt_notifications), NotificationManager.IMPORTANCE_HIGH);
            getManager().createNotificationChannel(chan1);
            Utility.setSharedPreferencesBoolean(ctx, constant.IS_CHANNEL_PREPARED,true);
        }
    }

    // Get notification manager
    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}