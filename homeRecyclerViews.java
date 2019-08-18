package tds.com.moviezlub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;

class homeRecyclerViews {
    homeRecyclerViews() {
    }

    /* Access modifiers changed, original: 0000 */
    public void getDataToSet(Context context, RecyclerView recyclerView, String str, String str2, ProgressBar progressBar, Intent intent) {
        Context context2 = context;
        String str3 = str;
        String str4 = str2;
        String str5 = "home";
        boolean equals = str4.equals(str5);
        String str6 = "Latest";
        String str7 = BuildConfig.FLAVOR;
        str5 = (equals && str.equals(str6)) ? "&limit=9" : (!str4.equals(str5) || str.equals(str6)) ? str7 : "&limit=6";
        SharedPreferences sharedPreferences = context.getSharedPreferences("online.movieone.movieone.HOME_DATA_STRING", 0);
        Log.e("url1", sharedPreferences.toString());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str4);
        stringBuilder.append(str);
        String string = sharedPreferences.getString(stringBuilder.toString(), str7);
        if (string.equals(str7)) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("https://moviezfun.com/json/movie_json.php?type=");
            stringBuilder2.append(str);
            stringBuilder2.append(str5);
            String stringBuilder3 = stringBuilder2.toString();
            final ProgressBar progressBar2 = progressBar;
            final RecyclerView recyclerView2 = recyclerView;
            final Context context3 = context;
            final Intent intent2 = intent;
            str4 = str2;
            str3 = str;
            final String finalStr = str4;
            final String finalStr1 = str3;
            Listener anonymousClass1 = new Listener<String>() {
                public void onResponse(String str) {
                    progressBar2.setVisibility(View.GONE);
                    recyclerView2.setAdapter(new movieViewAdapter(context3, (movieData[]) new GsonBuilder().create().fromJson(str, movieData[].class), intent2));
                    Editor edit = context3.getSharedPreferences("online.movieone.movieone.HOME_DATA_STRING", 0).edit();
                    StringBuilder stringBuilder = new StringBuilder();
                   stringBuilder.append(finalStr);
                    stringBuilder.append(finalStr1);
                    edit.putString(stringBuilder.toString(), str);
                    edit.apply();
                }
            };
            Volley.newRequestQueue(context).add(new StringRequest(stringBuilder3, (Listener<String>) anonymousClass1, new ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                }
            }));
            return;
        }
        progressBar.setVisibility(8);
        movieViewAdapter movieviewadapter = new movieViewAdapter(context, (movieData[]) new GsonBuilder().create().fromJson(string, movieData[].class), intent);
        RecyclerView recyclerView3 = recyclerView;
        recyclerView.setAdapter(movieviewadapter);
    }

    private class AnonymousClass {
    }
}
