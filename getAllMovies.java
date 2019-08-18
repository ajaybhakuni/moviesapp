package tds.com.moviezlub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.util.Objects;

public class getAllMovies extends AppCompatActivity {
    private String r4;

    /* Access modifiers changed, original: protected */
    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.get_all_movies);
        if (!new noNetwork(getApplicationContext()).isNetworkConnected()) {
            finish();
            startActivity(new Intent(this, networkError.class));
        }
        ((ActionBar) Objects.requireNonNull(getSupportActionBar())).setTitle(String.format("%s Movies", new Object[]{getIntent().getStringExtra("type")}));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movieView);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        Intent intent = new Intent(this, playMovies.class);
        intent.addFlags(268435456);
        new homeRecyclerViews().getDataToSet(getApplicationContext(), recyclerView, r4, "getMovies", progressBar, intent);
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
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
