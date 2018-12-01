package shereno.andishesaz.com.shere_nov1.ReciverClass;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import shereno.andishesaz.com.shere_nov1.MainActivity;
import shereno.andishesaz.com.shere_nov1.R;

/**
 * Created by Babak on 8/6/2017.
 */

public class ApplicationReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
         showNotification(context);
    }

    //-------------------------Create Notification-----------------------------
    private void showNotification(Context context) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(bitmap)
                        .setContentTitle("اشعار زیبای فریدون مشیری")
                        .setContentText("ما را از نظرات خود برای رفع ایرادات نرم افزار بهره مند کنید");
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

    }
}
