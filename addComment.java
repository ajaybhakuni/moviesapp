package tds.com.moviezlub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.json.JSONObject;

public class addComment extends AppCompatActivity {
    private String comment;
    private EditText commentBox;
    private TextView emptylist;
    private String mid;
    private ProgressBar pb;
    private RecyclerView rv;

    @SuppressLint({"StaticFieldLeak"})
    private class postcomment extends AsyncTask<String, Void, String> {
        private postcomment() {
        }

        /* synthetic */ postcomment(addComment addcomment, AnonymousClass1 anonymousClass1) {
            this();
        }

        /* Access modifiers changed, original: protected */
        public void onPreExecute() {
            addComment.this.pb.setVisibility(View.VISIBLE);
        }

        /* Access modifiers changed, original: protected|varargs */
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public String doInBackground(String... strArr) {
            StringBuilder stringBuilder;
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("uid", Constants.UUID(addComment.this));
                jSONObject.put("mid", addComment.this.mid);
                jSONObject.put("comment", addComment.this.comment);
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://moviezfun.com/json/addComment.php").openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(1500);
                httpURLConnection.setReadTimeout(1500);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                bufferedWriter.write(jsonString.getPostDataString(jSONObject));
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    stringBuilder = new StringBuilder();
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        stringBuilder.append(readLine);
                    }
                    return stringBuilder.toString();
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append("false : ");
                stringBuilder.append(responseCode);
                return stringBuilder.toString();
            } catch (Exception e) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Exepetion : ");
                stringBuilder.append(e.getMessage());
                return stringBuilder.toString();
            }
        }

        /* Access modifiers changed, original: protected */
        public void onPostExecute(String str) {
            addComment.this.pb.setVisibility(View.GONE);
            Toast.makeText(addComment.this, str, Toast.LENGTH_SHORT).show();
            if (str.trim().equals("YOUR COMMENT ADDED")) {
                addComment.this.emptylist.setVisibility(View.GONE);
                addComment.this.rv.setVisibility(View.VISIBLE);
                addComment.this.commentBox.setText(BuildConfig.FLAVOR);
                addComment.this.getComment();
            }
        }
    }

    /* Access modifiers changed, original: protected */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.add_comment_activity);
        if (!new noNetwork(getApplicationContext()).isNetworkConnected()) {
            finish();
            startActivity(new Intent(this, networkError.class));
        }
        ((ActionBar) Objects.requireNonNull(getSupportActionBar())).hide();
        this.mid = getIntent().getStringExtra("mid");
        this.commentBox = (EditText) findViewById(R.id.public_comment);
        this.rv = (RecyclerView) findViewById(R.id.commentBox);
        this.rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        this.pb = (ProgressBar) findViewById(R.id.progressBar);
        getComment();
        addComments();
    }

    private void addComments() {
        ((ImageButton) findViewById(R.id.post_comment)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                addComment addcomment = addComment.this;
                addcomment.comment = addcomment.commentBox.getText().toString().trim();
                if (addComment.this.comment.equals(BuildConfig.FLAVOR)) {
                    Toast.makeText(addComment.this, "Cant Post Blank Comment", Toast.LENGTH_SHORT).show();
                } else {
                    new postcomment(addComment.this, null).execute(new String[0]);
                }
            }
        });
    }

    public void onBackPressed() {
        finish();
    }

    private void getComment() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://moviezfun.com/json/getComments.php?mid=");
        stringBuilder.append(this.mid);
        stringBuilder.append("&from=comment");
        Volley.newRequestQueue(this).add(new StringRequest(stringBuilder.toString(), new Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onResponse(String str) {
                addComment.this.pb.setVisibility(View.GONE);
                if (str.trim().equals("[]")) {
                    addComment addcomment = addComment.this;
                    addcomment.emptylist = (TextView) addcomment.findViewById(R.id.noComments);
                    addComment.this.emptylist.setVisibility(View.VISIBLE);
                    addComment.this.rv.setVisibility(View.GONE);
                    return;
                }
                addComment.this.rv.setAdapter(new commentViewAdapter((commentData[]) new GsonBuilder().create().fromJson(str, commentData[].class)));
                addComment.this.rv.smoothScrollToPosition(((Adapter) Objects.requireNonNull(addComment.this.rv.getAdapter())).getItemCount() - 1);
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }
        }));
    }

    private class AnonymousClass1 {
    }
}
