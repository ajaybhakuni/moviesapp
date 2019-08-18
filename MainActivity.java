package tds.com.moviezlub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    private TextView bollywood_more;
    private int c;
    private DrawerLayout drawer;
    private TextView gujarati_more;
    private TextView hollywood_more;
    private TextView latest_more;
    private InterstitialAd mInterstitialAd;
    private ViewPager mViewPager;
    private TextView tollywood_more;

    private class viewPagerPageListner implements OnPageChangeListener {
        public void onPageScrollStateChanged(int i) {
        }

        public void onPageSelected(int i) {
        }

        private viewPagerPageListner() {
        }

        /* synthetic */ viewPagerPageListner(MainActivity mainActivity, AnonymousClass1 anonymousClass1) {
            this();
        }

        public void onPageScrolled(int i, float f, int i2) {
            i++;
            ((TextView) MainActivity.this.findViewById(R.id.text_slider)).setText(String.format("%s/%s", new Object[]{Integer.valueOf(i), Integer.valueOf(MainActivity.this.c)}));
        }
    }

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        if (!new noNetwork(getApplicationContext()).isNetworkConnected()) {
            finish();
            startActivity(new Intent(this, networkError.class));
        }
        MobileAds.initialize(this, getResources().getString(R.string.app_ad_id));
        new bannerAds((AdView) findViewById(R.id.adViews)).loadAds();
        InterstitialAd();
        assignViews();
        loginCheck();
        this.mViewPager = (ViewPager) findViewById(R.id.viewPage);
        this.mViewPager.setOffscreenPageLimit(4);
        mainBannerImage();
        this.mViewPager.addOnPageChangeListener(new viewPagerPageListner(this, null));
        new firebase(this).getFirebaseToken();
        new firebase(this).channelSub();
    }

    public void onBackPressed() {
        if (this.drawer.isDrawerOpen(8388611)) {
            this.drawer.closeDrawer(8388611);
            return;
        }
        getSharedPreferences("online.movieone.movieone.HOME_DATA_STRING", 0).edit().clear().apply();
        finishAffinity();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        MenuItem findItem = menu.findItem(R.id.info);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("App version : ");
        stringBuilder.append(BuildConfig.VERSION_NAME);
        findItem.setTitle(stringBuilder.toString());
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.share) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.TEXT", "Watch Latest Movies on Moviez Fun Download App : https://play.google.com/store/apps/details?id=online.movieone.movieone");
            intent.setType("text/plain");
            startActivity(intent);
        } else if (itemId == R.id.search) {
            showAdByIntent(new Intent(this, search.class));
        } else if (itemId == R.id.rateus) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=online.movieone.movieone")));
        } else if (itemId == R.id.privacy) {
            showAdByIntent(new Intent(this, privacy.class));
        } else if (itemId == R.id.disclaimer) {
            showAdByIntent(new Intent(this, disclaimer.class));
        }
        return true;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.indianeng) {
            showAd("Indian-English");
        } else if (itemId == R.id.bollywood) {
            showAd("Bollywood");
        } else if (itemId == R.id.hollywood) {
            showAd("Hollywood");
        } else if (itemId == R.id.tollywood) {
            showAd("Tollywood");
        } else if (itemId == R.id.gujarati) {
            showAd("Gujarati");
        } else if (itemId == R.id.marathi) {
            showAd("Marathi");
        } else if (itemId == R.id.hollyhindi) {
            showAd("Hollywood-Hindi");
        } else if (itemId == R.id.tollyhindi) {
            showAd("Tollywood-Hindi");
        } else if (itemId == R.id.punjabi) {
            showAd("Punjabi");
        } else if (itemId == R.id.bhojpuri) {
            showAd("Bhojpuri");
        } else if (itemId == R.id.fav) {
            showAdByIntent(new Intent(this, myWishlist.class));
        } else if (itemId == R.id.request) {
            showAdByIntent(new Intent(this, movieRequest.class));
        } else if (itemId == R.id.requestresponse) {
            showAdByIntent(new Intent(this, movieResponse.class));
        } else if (itemId == R.id.later) {
            showAdByIntent(new Intent(this, playlater.class));
        } else if (itemId == R.id.history) {
            showAdByIntent(new Intent(this, history.class));
        }
        this.drawer.closeDrawer(8388611);
        return true;
    }

    @SuppressLint("WrongConstant")
    private void assignViews() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.bolly_pb);
        ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.holly_pb);
        ProgressBar progressBar3 = (ProgressBar) findViewById(R.id.tolly_pb);
        ProgressBar progressBar4 = (ProgressBar) findViewById(R.id.latest_pb);
        ProgressBar progressBar5 = (ProgressBar) findViewById(R.id.guj_pb);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 3);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(this, 3);
        GridLayoutManager gridLayoutManager4 = new GridLayoutManager(this, 3);
        GridLayoutManager gridLayoutManager5 = new GridLayoutManager(this, 3);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.latest_view);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.bollywood_view);
        recyclerView2.setLayoutManager(gridLayoutManager2);
        recyclerView2.setNestedScrollingEnabled(false);
        RecyclerView recyclerView3 = (RecyclerView) findViewById(R.id.hollywood_view);
        recyclerView3.setLayoutManager(gridLayoutManager3);
        recyclerView3.setNestedScrollingEnabled(false);
        RecyclerView recyclerView4 = (RecyclerView) findViewById(R.id.tollywood_view);
        recyclerView4.setLayoutManager(gridLayoutManager4);
        recyclerView4.setNestedScrollingEnabled(false);
        RecyclerView recyclerView5 = (RecyclerView) findViewById(R.id.gujarati_view);
        recyclerView5.setLayoutManager(gridLayoutManager5);
        recyclerView5.setNestedScrollingEnabled(false);
        this.latest_more = (TextView) findViewById(R.id.latest_more);
        this.bollywood_more = (TextView) findViewById(R.id.bollywood_more);
        this.hollywood_more = (TextView) findViewById(R.id.hollywood_more);
        this.tollywood_more = (TextView) findViewById(R.id.tollywood_more);
        this.gujarati_more = (TextView) findViewById(R.id.gujarati_more);
        Intent intent = new Intent(this, playMovies.class);
        intent.addFlags(268435456);
        new homeRecyclerViews().getDataToSet(getApplicationContext(), recyclerView, "Latest", "home", progressBar4, intent);
        new homeRecyclerViews().getDataToSet(getApplicationContext(), recyclerView2, "Bollywood", "home", progressBar, intent);
        RecyclerView recyclerView6 = recyclerView5;
        new homeRecyclerViews().getDataToSet(getApplicationContext(), recyclerView3, "Hollywood-Hindi", "home", progressBar2, intent);
        RecyclerView recyclerView7 = recyclerView4;
        new homeRecyclerViews().getDataToSet(getApplicationContext(), recyclerView7, "Tollywood-Hindi", "home", progressBar3, intent);
        new homeRecyclerViews().getDataToSet(getApplicationContext(), recyclerView6, "Gujarati", "home", progressBar5, intent);
        setMoreButton();
    }

    private void setMoreButton() {
        this.latest_more.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.showAd("Latest");
            }
        });
        this.bollywood_more.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.showAd("Bollywood");
            }
        });
        this.hollywood_more.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.showAd("Hollywood-Hindi");
            }
        });
        this.tollywood_more.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.showAd("Tollywood-Hindi");
            }
        });
        this.gujarati_more.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.showAd("Gujarati");
            }
        });
    }

    private void outPutMovieData(String str) {
        Intent intent = new Intent(this, getAllMovies.class);
        intent.putExtra("type", str);
        startActivity(intent);
    }

    private void loginCheck() {
        String UUID = Constants.UUID(this);
        if (UUID.equals(BuildConfig.FLAVOR)) {
            Editor edit = getSharedPreferences("online.movieone.movieone.LOGIN_KEY", 0).edit();
            edit.putString("userid", random_string.userId());
            edit.apply();
        }
        Context applicationContext = getApplicationContext();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://moviezfun.com/json/user.php?uid=");
        stringBuilder.append(UUID);
        users users = new users(applicationContext, stringBuilder.toString());
        applicationContext = getApplicationContext();
        stringBuilder = new StringBuilder();
        stringBuilder.append("https://moviezfun.com/json/pageview.php?uid=");
        stringBuilder.append(UUID);
        users = new users(applicationContext, stringBuilder.toString());
    }

    private void mainBannerImage() {
        SharedPreferences sharedPreferences = getSharedPreferences("online.movieone.movieone.HOME_DATA_STRING", 0);
        String str = BuildConfig.FLAVOR;
        String string = sharedPreferences.getString("mainBanner", str);
        if (string.equals(str)) {
            Volley.newRequestQueue(this).add(new StringRequest("https://moviezfun.com/json/mainbanner.php", new Listener<String>() {
                public void onResponse(String str) {
                    Editor edit = MainActivity.this.getSharedPreferences("online.movieone.movieone.HOME_DATA_STRING", 0).edit();
                    edit.putString("mainBanner", str);
                    edit.apply();
                    MainActivity.this.setMainBanner(str);

                }
            }, new ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                }
            }));
            return;
        }
        setMainBanner(string);
    }

    private void setMainBanner(String str) {
        movieData[] moviedataArr = (movieData[]) new GsonBuilder().create().fromJson(str, movieData[].class);
        Intent intent = new Intent(this, getAllMovies.class);
        intent.addFlags(268435456);
        ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext(), moviedataArr, intent);
        this.mViewPager.setAdapter(imageAdapter);
        this.c = imageAdapter.getCount();
    }

    private void InterstitialAd() {
        this.mInterstitialAd = new InterstitialAd(this);
        this.mInterstitialAd.setAdUnitId(getResources().getString(R.string.int_ad_id));
        this.mInterstitialAd.loadAd(new Builder().build());
    }

    private void showAd(final String str) {
        if (this.mInterstitialAd.isLoaded()) {
            this.mInterstitialAd.show();
        } else {
            outPutMovieData(str);
        }
        this.mInterstitialAd.setAdListener(new AdListener() {
            public void onAdClosed() {
                MainActivity.this.outPutMovieData(str);
                MainActivity.this.mInterstitialAd.loadAd(new Builder().build());
            }
        });
    }

    private void showAdByIntent(final Intent intent) {
        if (this.mInterstitialAd.isLoaded()) {
            this.mInterstitialAd.show();
        } else {
            startActivity(intent);
        }
        this.mInterstitialAd.setAdListener(new AdListener() {
            public void onAdClosed() {
                MainActivity.this.mInterstitialAd.loadAd(new Builder().build());
                MainActivity.this.startActivity(intent);
            }
        });
    }

    private class AnonymousClass1 {
    }
}
