package tds.com.moviezlub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;

public class movieRequest extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private EditText email;
    private TextView emptylist;
    private String movieemail;
    private String moviename;
    private String movietype;
    private EditText name;
    private Button newrequest;
    private ProgressBar pb;
    private RecyclerView recyclerView;
    private Button request;
    private LinearLayout showRequestForm;
    private Button showrequest;
    private Spinner type;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_request);
        if (!new noNetwork(getApplicationContext()).isNetworkConnected()) {
            finish();
            startActivity(new Intent(this, networkError.class));
        }
        this.name = (EditText) findViewById(R.id.requestMovieNmae);
        this.email = (EditText) findViewById(R.id.requestMovieEmail);
        this.emptylist = (TextView) findViewById(R.id.emptyWishList);
        this.type = (Spinner) findViewById(R.id.movieType);
        this.adapter = new ArrayAdapter(this, 17367050, Constants.MOVIE_TYPE);
        this.type.setAdapter(this.adapter);
        this.showrequest = (Button) findViewById(R.id.showRequest);
        this.newrequest = (Button) findViewById(R.id.newRequest);
        this.request = (Button) findViewById(R.id.requestMovie);
        this.showRequestForm = (LinearLayout) findViewById(R.id.requestForm);
        this.recyclerView = (RecyclerView) findViewById(R.id.requestedMovies);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.recyclerView.setNestedScrollingEnabled(true);
        this.pb = (ProgressBar) findViewById(R.id.progressBar);
        requestButton();
        getType();
        setRequest();
        getList();
    }

    private void setRequest() {
        this.request.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                movieRequest movierequest = movieRequest.this;
                movierequest.moviename = movierequest.name.getText().toString().trim();
                movierequest = movieRequest.this;
                movierequest.movieemail = movierequest.email.getText().toString().trim();
                String access$000 = movieRequest.this.moviename;
                String str = BuildConfig.FLAVOR;
                if (access$000.equals(str) && movieRequest.this.movieemail.equals(str)) {
                    Toast.makeText(movieRequest.this, "Movie Name & Email can't blank", 0).show();
                    return;
                }
                movieRequest.this.pb.setVisibility(0);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("https://moviezfun.com/json/movierequest.php?name=");
                stringBuilder.append(movieRequest.this.moviename);
                stringBuilder.append("&type=");
                stringBuilder.append(movieRequest.this.movietype);
                stringBuilder.append("&email=");
                stringBuilder.append(movieRequest.this.movieemail);
                stringBuilder.append("&uid=");
                stringBuilder.append(Constants.UUID(movieRequest.this));
                Volley.newRequestQueue(movieRequest.this).add(new StringRequest(stringBuilder.toString(), new Listener<String>() {
                    public void onResponse(String str) {
                        movieRequest.this.pb.setVisibility(8);
                        EditText access$100 = movieRequest.this.name;
                        String str2 = BuildConfig.FLAVOR;
                        access$100.setText(str2);
                        movieRequest.this.email.setText(str2);
                        Toast.makeText(movieRequest.this, str, 0).show();
                    }
                }, new ErrorListener() {
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(movieRequest.this, volleyError.getMessage(), 0).show();
                    }
                }));
            }
        });
    }

    private void getType() {
        this.type.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                movieRequest movierequest = movieRequest.this;
                movierequest.movietype = (String) movierequest.adapter.getItem(i);
            }
        });
    }

    private void requestButton() {
        this.showrequest.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                movieRequest.this.pb.setVisibility(0);
                movieRequest.this.recyclerView.setVisibility(0);
                movieRequest.this.showRequestForm.setVisibility(8);
                movieRequest.this.showrequest.setBackgroundColor(movieRequest.this.getResources().getColor(R.color.colorAccent));
                movieRequest.this.showrequest.setTextColor(movieRequest.this.getResources().getColor(R.color.white));
                movieRequest.this.newrequest.setTextColor(movieRequest.this.getResources().getColor(R.color.white));
                movieRequest.this.newrequest.setBackgroundColor(movieRequest.this.getResources().getColor(R.color.white));
                movieRequest.this.newrequest.setTextColor(movieRequest.this.getResources().getColor(R.color.colorPrimary));
                movieRequest.this.getList();
            }
        });
        this.newrequest.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                movieRequest.this.recyclerView.setVisibility(8);
                movieRequest.this.showRequestForm.setVisibility(0);
                movieRequest.this.emptylist.setVisibility(8);
                movieRequest.this.pb.setVisibility(8);
                movieRequest.this.newrequest.setBackgroundColor(movieRequest.this.getResources().getColor(R.color.colorAccent));
                movieRequest.this.newrequest.setTextColor(movieRequest.this.getResources().getColor(R.color.white));
                movieRequest.this.showrequest.setBackgroundColor(movieRequest.this.getResources().getColor(R.color.white));
                movieRequest.this.showrequest.setTextColor(movieRequest.this.getResources().getColor(R.color.colorPrimary));
            }
        });
    }

    private void getList() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://moviezfun.com/json/movierequest.php?from=read&uid=");
        stringBuilder.append(Constants.UUID(this));
        Volley.newRequestQueue(this).add(new StringRequest(stringBuilder.toString(), new Listener<String>() {
            public void onResponse(String str) {
                movieRequest.this.pb.setVisibility(8);
                if (str.trim().equals("[]")) {
                    movieRequest.this.emptylist.setVisibility(0);
                    movieRequest.this.recyclerView.setVisibility(8);
                    return;
                }
                movieRequest.this.emptylist.setVisibility(8);
                movieRequest.this.recyclerView.setVisibility(0);
                movieData[] moviedataArr = (movieData[]) new GsonBuilder().create().fromJson(str, movieData[].class);
                Intent intent = new Intent(movieRequest.this, movieResponse.class);
                intent.addFlags(268435456);
                Intent intent2 = new Intent(movieRequest.this.getIntent());
                intent2.addFlags(268435456);
                movieRequest.this.recyclerView.setAdapter(new requestViewAdapter(movieRequest.this.getApplicationContext(), moviedataArr, intent, intent2));
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }
        }));
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
