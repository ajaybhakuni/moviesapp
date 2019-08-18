package tds.com.moviezlub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
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

public class search extends AppCompatActivity {
    ImageButton back;
    ImageButton doSearch;
    ProgressBar pb;
    RecyclerView recyclerView;
    EditText searchBox;
    String searchQ;

    /* Access modifiers changed, original: protected */
    @RequiresApi(api = 19)
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_search);
        if (!new noNetwork(getApplicationContext()).isNetworkConnected()) {
            finish();
            startActivity(new Intent(this, networkError.class));
        }
        ((ActionBar) Objects.requireNonNull(getSupportActionBar())).hide();
        this.searchBox = (EditText) findViewById(R.id.searchQuery);
        this.doSearch = (ImageButton) findViewById(R.id.doSearch);
        this.recyclerView = (RecyclerView) findViewById(R.id.searchView);
        this.pb = (ProgressBar) findViewById(R.id.progressBar);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        this.doSearch.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                search.this.pb.setVisibility(0);
                search search = search.this;
                search.searchQ = search.searchBox.getText().toString().trim();
                if (search.this.searchQ.equals(BuildConfig.FLAVOR)) {
                    Toast.makeText(search.this, "Please Input Search Text", 0).show();
                } else {
                    search.this.searchQ();
                }
            }
        });
        this.back = (ImageButton) findViewById(R.id.back);
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                search search = search.this;
                search.startActivity(new Intent(search, MainActivity.class));
            }
        });
        MobileAds.initialize(this, getResources().getString(R.string.app_ad_id));
        new bannerAds((AdView) findViewById(R.id.adViews)).loadAds();
    }

    private void searchQ() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://moviezfun.com/json/search.php?q=");
        stringBuilder.append(this.searchQ);
        String stringBuilder2 = stringBuilder.toString();
        final Intent intent = new Intent(this, playMovies.class);
        intent.setFlags(268435456);
        Volley.newRequestQueue(this).add(new StringRequest(stringBuilder2, new Listener<String>() {
            public void onResponse(String str) {
                search.this.pb.setVisibility(8);
                search.this.recyclerView.setAdapter(new movieViewAdapter(search.this.getApplicationContext(), (movieData[]) new GsonBuilder().create().fromJson(str, movieData[].class), intent));
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }
        }));
    }
}
