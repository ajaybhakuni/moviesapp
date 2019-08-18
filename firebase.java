package tds.com.moviezlub;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

class firebase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Context c;

    firebase(Context context) {
        this.c = context;
    }

    /* Access modifiers changed, original: 0000 */
    public void getFirebaseToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                String str = "online.movieone.movieone.LOGIN_KEY";
                SharedPreferences sharedPreferences = firebase.this.c.getSharedPreferences(str, 0);
                String str2 = BuildConfig.FLAVOR;
                String string = sharedPreferences.getString("fmctokenstatus", str2);
                String str3 = "fmctoken";
                if (sharedPreferences.getString(str3, str2).equals(str2)) {
                    Editor edit = firebase.this.c.getSharedPreferences(str, 0).edit();
                    edit.putString(str3, token);
                    edit.apply();
                }
                if (string.equals(str2)) {
                    firebase.this.tokenreg(token);
                }
            }
        });
    }

    /* Access modifiers changed, original: 0000 */
    public void channelSub() {
        if (VERSION.SDK_INT >= 26) {
            NotificationManager notificationManager = (NotificationManager) this.c.getSystemService("notification");
            NotificationChannel notificationChannel = new NotificationChannel("MOVIEZFUN_MAIN_CHANNEL", "MOVIEZFUN UPDATES", 4);
            notificationChannel.setDescription("MOVIEZ FUN NEW UPDATES DETAILS");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(-65536);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("global");
    }

    private void tokenreg(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://4shopae.online/app_json/fmc_token.php?uid=");
        stringBuilder.append(Constants.UUID(this.c));
        stringBuilder.append("&token=");
        stringBuilder.append(str);
        Volley.newRequestQueue(this.c).add(new StringRequest(stringBuilder.toString(), new Listener<String>() {
            public void onResponse(String str) {
                if (str.equals("SUCCESS")) {
                    Editor edit = firebase.this.c.getSharedPreferences("online.movieone.movieone.LOGIN_KEY", 0).edit();
                    edit.putString("fmctokenstatus", "WRITTEN");
                    edit.apply();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }
        }));
    }
}
