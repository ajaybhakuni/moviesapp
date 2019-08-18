package tds.com.moviezlub;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build.VERSION;
import android.util.Log;
import androidx.core.app.NotificationCompat.Builder;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.RemoteMessage.Notification;
import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            genrelNotificaion(((Notification) Objects.requireNonNull(remoteMessage.getNotification())).getBody(), remoteMessage.getNotification().getTitle());
        }
    }

    private void genrelNotificaion(String str, String str2) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(67108864);
        String str3 = "MOVIEZFUN_MAIN_CHANNEL";
        Builder contentIntent = new Builder(this, str3).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(str2).setContentText(str).setAutoCancel(true).setSound(RingtoneManager.getDefaultUri(2)).setChannelId(str3).setContentIntent(PendingIntent.getActivity(this, 1, intent, 1073741824));
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        if (VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(new NotificationChannel(str3, "MOVIEZFUN UPDATES", 3));
        }
        notificationManager.notify(1, contentIntent.build());
    }

    public void onNewToken(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Refreshed token: ");
        stringBuilder.append(str);
        Log.d("TAG", stringBuilder.toString());
    }
}
