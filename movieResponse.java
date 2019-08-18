package tds.com.moviezlub;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;
import java.util.Objects;

public class movieResponse extends AppCompatActivity {
    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_wishlist);
        if (!new noNetwork(getApplicationContext()).isNetworkConnected()) {
            finish();
            startActivity(new Intent(this, networkError.class));
        }
        ((ActionBar) Objects.requireNonNull(getSupportActionBar())).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Request Movies Response");
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.wishlistView);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, 1, false));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://moviezfun.com/json/movieresponse.php?uid=");
        stringBuilder.append(Constants.UUID(this));
        stringBuilder.append("&get");
        Volley.newRequestQueue(this).add(new StringRequest(stringBuilder.toString(), new Listener<String>() {
            public void onResponse(String str) {
                progressBar.setVisibility(8);
                if (str.equals("[]")) {
                    TextView textView = (TextView) movieResponse.this.findViewById(R.id.emptyWishList);
                    textView.setVisibility(0);
                    textView.setCompoundDrawables(null, movieResponse.this.getResources().getDrawable(R.drawable.ic_notifications_black_24dp), null, null);
                    textView.setText(String.format("%s\n%s", new Object[]{"No Response For Your", "Any Request Now!"}));
                    return;
                }
                movieData[] moviedataArr = (movieData[]) new GsonBuilder().create().fromJson(str, movieData[].class);
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addFlags(268435456);
                Intent intent2 = new Intent(movieResponse.this.getIntent());
                intent2.addFlags(268435456);
                recyclerView.setAdapter(new responseViewAdapter(movieResponse.this.getApplicationContext(), moviedataArr, intent, intent2));
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }
        }));
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
