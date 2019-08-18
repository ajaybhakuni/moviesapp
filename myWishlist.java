package tds.com.moviezlub;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.GsonBuilder;
import java.util.Objects;

public class myWishlist extends AppCompatActivity {
    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_wishlist);
        if (!new noNetwork(getApplicationContext()).isNetworkConnected()) {
            finish();
            startActivity(new Intent(this, networkError.class));
        }
        ((ActionBar) Objects.requireNonNull(getSupportActionBar())).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Favourite Movies");
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.wishlistView);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://moviezfun.com/json/getWishlist.php?uid=");
        stringBuilder.append(Constants.UUID(this));
        Volley.newRequestQueue(this).add(new StringRequest(stringBuilder.toString(), new Listener<String>() {
            public void onResponse(String str) {
                progressBar.setVisibility(8);
                if (str.equals("[]")) {
                    ((TextView) myWishlist.this.findViewById(R.id.emptyWishList)).setVisibility(0);
                    return;
                }
                movieData[] moviedataArr = (movieData[]) new GsonBuilder().create().fromJson(str, movieData[].class);
                Intent intent = new Intent(myWishlist.this, playMovies.class);
                intent.addFlags(268435456);
                recyclerView.setAdapter(new movieViewAdapter(myWishlist.this.getApplicationContext(), moviedataArr, intent));
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }
        }));
        MobileAds.initialize(this, getResources().getString(R.string.app_ad_id));
        new bannerAds((AdView) findViewById(R.id.adViews)).loadAds();
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
