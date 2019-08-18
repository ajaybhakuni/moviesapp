package tds.com.moviezlub;

import android.content.Context;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

class users {
    users(Context context, String str) {
        Volley.newRequestQueue(context).add(new StringRequest(str, new Listener<String>() {
            public void onResponse(String str) {
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }
        }));
    }
}
