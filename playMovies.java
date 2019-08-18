package tds.com.moviezlub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.GsonBuilder;

public class playMovies extends YouTubeBaseActivity implements OnInitializedListener, OnClickListener {
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private RecyclerView commentBox;
    private String desc;
    private ImageButton fav;
    private String genres;
    private String id;
    private String langugage;
    private Drawable later;
    private Drawable liked;
    private String name;
    private ImageButton playlater;
    private String playlink;
    private SharedPreferences preferences;
    private String releasedate;
    private String reportEmail;
    private String reportReason;
    private String type;
    private Drawable ulater;
    private Drawable unliked;
    private String uploaddate;
    private String[] videoId;
    private String views;
    private String year;

    private static boolean isValidEmail(CharSequence charSequence) {
        return !TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_play_movies);
        if (!new noNetwork(getApplicationContext()).isNetworkConnected()) {
            finish();
            startActivity(new Intent(this, networkError.class));
        }
        this.preferences = getSharedPreferences("online.movieone.movieone.LOGIN_KEY", 0);
        this.unliked = getResources().getDrawable(R.drawable.ic_favorite_border);
        this.liked = getResources().getDrawable(R.drawable.ic_favorite);
        this.ulater = getResources().getDrawable(R.drawable.ic_watch_later);
        this.later = getResources().getDrawable(R.drawable.ic_watch_later_red);
        getIntentData();
        setPlayer();
        defineViews();
        defineButtons();
        setViews();
        setCommentBox();
        extraMoviesBox();
        setHistory();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            finish();
        }
        return true;
    }

    private void getIntentData() {
        this.id = getIntent().getStringExtra("id");
        this.name = getIntent().getStringExtra("name");
        this.playlink = getIntent().getStringExtra("playlink");
        this.year = getIntent().getStringExtra("year");
        this.desc = getIntent().getStringExtra("desc");
        this.genres = getIntent().getStringExtra("genres");
        this.langugage = getIntent().getStringExtra("language");
        this.type = getIntent().getStringExtra("type");
        this.uploaddate = getIntent().getStringExtra("uploaddate");
        this.releasedate = getIntent().getStringExtra("releasedate");
        this.views = getIntent().getStringExtra("views");
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void setPlayer() {
        this.videoId = this.playlink.split("=");
        ((YouTubePlayerView) findViewById(R.id.moviePlayer)).initialize("AIzaSyBjzF_quCozcJWrhsvgBz-ZvGhnZsbpeSk", this);
    }

    private void defineViews() {
        TextView textView = (TextView) findViewById(R.id.movieDetails);
        TextView textView2 = (TextView) findViewById(R.id.movieDesc);
        TextView textView3 = (TextView) findViewById(R.id.movieViews);
        ((TextView) findViewById(R.id.movieName)).setText(this.name);
        textView.setText(String.format("Release Date : %s, Movie Year : %s, Movie Type : %s, Movie Genres : %s\nMovie Language : %s, Upload Date : %s", new Object[]{this.releasedate, this.year, this.type, this.genres, this.langugage, this.uploaddate}));
        textView2.setText(this.desc);
        if (this.desc.equals(BuildConfig.FLAVOR)) {
            textView2.setVisibility(8);
        }
        textView3.setText(String.format("%s Views", new Object[]{this.views}));
    }

    private void defineButtons() {
        ImageButton imageButton = (ImageButton) findViewById(R.id.share);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.report);
        ((TextView) findViewById(R.id.addComment)).setOnClickListener(this);
        this.playlater = (ImageButton) findViewById(R.id.playlater);
        this.fav = (ImageButton) findViewById(R.id.fav);
        imageButton.setOnClickListener(this);
        this.playlater.setOnClickListener(this);
        this.fav.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        buttonSet();
    }

    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean z) {
        if (!z) {
            youTubePlayer.loadVideo(this.videoId[1]);
            youTubePlayer.setPlayerStyle(PlayerStyle.DEFAULT);
        }
    }

    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, 1).show();
            return;
        }
        Toast.makeText(this, String.format(getString(R.string.error_player), new Object[]{youTubeInitializationResult.toString()}), 1).show();
    }

    /* Access modifiers changed, original: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1) {
            getYouTubePlayerProvider().initialize("AIzaSyBjzF_quCozcJWrhsvgBz-ZvGhnZsbpeSk", this);
        }
    }

    private Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.moviePlayer);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addComment /*2131230786*/:
                Intent intent = new Intent(this, addComment.class);
                intent.putExtra("mid", this.id);
                startActivity(intent);
                return;
            case R.id.fav /*2131230887*/:
                setLikeLaterButton("fav", "liked", this.fav, "like.php", this.unliked, this.liked);
                return;
            case R.id.playlater /*2131230992*/:
                setLikeLaterButton("later", "later", this.playlater, "later.php", this.ulater, this.later);
                return;
            case R.id.report /*2131231008*/:
                reportLink();
                return;
            case R.id.share /*2131231049*/:
                share();
                return;
            default:
                return;
        }
    }

    private void share() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Watch ");
        stringBuilder.append(this.name);
        stringBuilder.append(" on Moviez Fun Download App : https://play.google.com/store/apps/details?id=online.movieone.movieone");
        intent.putExtra("android.intent.extra.TEXT", stringBuilder.toString());
        intent.setType("text/plain");
        startActivity(intent);
    }

    private void reportLink() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        linearLayout.setPadding(10, 0, 10, 0);
        Spinner spinner = new Spinner(this);
        final EditText editText = new EditText(this);
        editText.setHint("Report Email");
        linearLayout.addView(spinner);
        linearLayout.addView(editText);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367049, new String[]{"Movie Not Playing", "Copyright Content", "Inappropriate Content", "Other"});
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                playMovies.this.reportReason = (String) arrayAdapter.getItem(i);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                playMovies.this.reportReason = (String) arrayAdapter.getItem(0);
            }
        });
        Builder builder = new Builder(this);
        builder.setTitle("Report This Movie!");
        builder.setIcon(getResources().getDrawable(R.drawable.ic_report));
        builder.setView(linearLayout);
        String str = "Cancel";
        builder.setPositiveButton("Report", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                playMovies.this.reportEmail = editText.getText().toString();
                if (playMovies.this.reportEmail.equals(BuildConfig.FLAVOR)) {
                    Toast.makeText(playMovies.this, "Report Email Required!", 0).show();
                    playMovies.this.reportLink();
                } else if (playMovies.isValidEmail(playMovies.this.reportEmail)) {
                    playMovies.this.reportMovies();
                } else {
                    Toast.makeText(playMovies.this, "Input Valid Email", 0).show();
                    playMovies.this.reportLink();
                }
            }
        }).setNegativeButton(str, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

    private void setViews() {
        SharedPreferences sharedPreferences = this.preferences;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("views");
        stringBuilder.append(this.id);
        if (!sharedPreferences.getString(stringBuilder.toString(), BuildConfig.FLAVOR).equals("viewed")) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("https://moviezfun.com/json/views.php?mid=");
            stringBuilder.append(this.id);
            Volley.newRequestQueue(this).add(new StringRequest(stringBuilder.toString(), new Listener<String>() {
                public void onResponse(String str) {
                    Editor edit = playMovies.this.getSharedPreferences("online.movieone.movieone.LOGIN_KEY", 0).edit();
                    if (str.equals("1")) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("views");
                        stringBuilder.append(playMovies.this.id);
                        edit.putString(stringBuilder.toString(), "viewed");
                        edit.apply();
                    }
                }
            }, new ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                }
            }));
        }
    }

    private void setHistory() {
        Context applicationContext = getApplicationContext();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://moviezfun.com/json/history.php?mid=");
        stringBuilder.append(this.id);
        stringBuilder.append("&add&uid=");
        stringBuilder.append(Constants.UUID(this));
        users users = new users(applicationContext, stringBuilder.toString());
    }

    private void setLikeLaterButton(String str, String str2, ImageButton imageButton, String str3, Drawable drawable, Drawable drawable2) {
        SharedPreferences sharedPreferences = this.preferences;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(this.id);
        String stringBuilder2 = stringBuilder.toString();
        String str4 = BuildConfig.FLAVOR;
        String string = sharedPreferences.getString(stringBuilder2, str4);
        Editor edit = getSharedPreferences("online.movieone.movieone.LOGIN_KEY", 0).edit();
        StringBuilder stringBuilder3;
        if (string.equals(str2)) {
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append(str);
            stringBuilder3.append(this.id);
            edit.putString(stringBuilder3.toString(), str4);
            edit.apply();
            imageButton.setImageDrawable(drawable);
        } else {
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append(str);
            stringBuilder3.append(this.id);
            edit.putString(stringBuilder3.toString(), str2);
            edit.apply();
            imageButton.setImageDrawable(drawable2);
        }
        string = Constants.UUID(this);
        stringBuilder = new StringBuilder();
        stringBuilder.append("https://moviezfun.com/json/");
        stringBuilder.append(str3);
        stringBuilder.append("?mid=");
        stringBuilder.append(this.id);
        stringBuilder.append("&uid=");
        stringBuilder.append(string);
        final String str5 = str;
        final String str6 = str2;
        final ImageButton imageButton2 = imageButton;
        final Drawable drawable3 = drawable2;
        final Drawable drawable4 = drawable;
        Volley.newRequestQueue(this).add(new StringRequest(stringBuilder.toString(), new Listener<String>() {
            public void onResponse(String str) {
                Editor edit = playMovies.this.getSharedPreferences("online.movieone.movieone.LOGIN_KEY", 0).edit();
                StringBuilder stringBuilder;
                if (str.equals("1")) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str5);
                    stringBuilder.append(playMovies.this.id);
                    edit.putString(stringBuilder.toString(), str6);
                    edit.apply();
                    imageButton2.setImageDrawable(drawable3);
                } else if (str.equals("0")) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str5);
                    stringBuilder.append(playMovies.this.id);
                    edit.putString(stringBuilder.toString(), BuildConfig.FLAVOR);
                    edit.apply();
                    imageButton2.setImageDrawable(drawable4);
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }
        }));
    }

    private void reportMovies() {
        String UUID = Constants.UUID(this);
        this.reportReason = this.reportReason.replace(" ", "-");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://moviezfun.com/json/report.php?mid=");
        stringBuilder.append(this.id);
        stringBuilder.append("&uid=");
        stringBuilder.append(UUID);
        stringBuilder.append("&reason=");
        stringBuilder.append(this.reportReason);
        stringBuilder.append("&email=");
        stringBuilder.append(this.reportEmail);
        Volley.newRequestQueue(this).add(new StringRequest(stringBuilder.toString(), new Listener<String>() {
            public void onResponse(String str) {
                Toast.makeText(playMovies.this, str, 0).show();
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(playMovies.this, volleyError.toString(), 0).show();
            }
        }));
    }

    private void buttonSet() {
        SharedPreferences sharedPreferences = this.preferences;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fav");
        stringBuilder.append(this.id);
        String stringBuilder2 = stringBuilder.toString();
        String str = BuildConfig.FLAVOR;
        String string = sharedPreferences.getString(stringBuilder2, str);
        SharedPreferences sharedPreferences2 = this.preferences;
        StringBuilder stringBuilder3 = new StringBuilder();
        String str2 = "later";
        stringBuilder3.append(str2);
        stringBuilder3.append(this.id);
        stringBuilder2 = sharedPreferences2.getString(stringBuilder3.toString(), str);
        if (string.equals("liked")) {
            this.fav.setImageDrawable(this.liked);
        }
        if (stringBuilder2.equals(str2)) {
            this.playlater.setImageDrawable(this.later);
        }
    }

    private void setCommentBox() {
        this.commentBox = (RecyclerView) findViewById(R.id.commentBox);
        this.commentBox.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.commentBox.setNestedScrollingEnabled(false);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://moviezfun.com/json/getComments.php?mid=");
        stringBuilder.append(this.id);
        stringBuilder.append("&limit=3");
        Volley.newRequestQueue(this).add(new StringRequest(stringBuilder.toString(), new Listener<String>() {
            public void onResponse(String str) {
                if (str.trim().equals("[]")) {
                    ((TextView) playMovies.this.findViewById(R.id.noComments)).setVisibility(0);
                    playMovies.this.commentBox.setVisibility(8);
                }
                playMovies.this.commentBox.setAdapter(new commentViewAdapter((commentData[]) new GsonBuilder().create().fromJson(str, commentData[].class)));
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }
        }));
    }

    private void extraMoviesBox() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.extraMovies);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setNestedScrollingEnabled(false);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://moviezfun.com/json/movie_json.php?type=");
        stringBuilder.append(this.type);
        stringBuilder.append("&limit=6");
        Volley.newRequestQueue(this).add(new StringRequest(stringBuilder.toString(), new Listener<String>() {
            public void onResponse(String str) {
                movieData[] moviedataArr = (movieData[]) new GsonBuilder().create().fromJson(str, movieData[].class);
                Intent intent = new Intent(playMovies.this, playMovies.class);
                intent.addFlags(268435456);
                recyclerView.setAdapter(new movieViewAdapter(playMovies.this.getApplicationContext(), moviedataArr, intent));
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }
        }));
    }

    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, getAllMovies.class);
        intent.putExtra("type", this.type);
        startActivity(intent);
    }
}
