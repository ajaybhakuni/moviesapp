package tds.com.moviezlub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

public class historyViewAdapter extends Adapter<historyViewAdapter.viewadapter> {
    private final Context context;
    private final movieData[] data;
    private final Intent intent;
    private final Intent refresh;

    class viewadapter extends ViewHolder {
        final TextView date;
        final ImageButton delete;
        final TextView link;
        final ImageView movieImg;
        final CardView response;
        final TextView title;

        viewadapter(@NonNull View view) {
            super(view);
            this.date = (TextView) view.findViewById(R.id.date);
            this.title = (TextView) view.findViewById(R.id.title_movie);
            this.link = (TextView) view.findViewById(R.id.movie_link);
            this.movieImg = (ImageView) view.findViewById(R.id.movieImg);
            this.response = (CardView) view.findViewById(R.id.response);
            this.delete = (ImageButton) view.findViewById(R.id.delete);
        }
    }

    historyViewAdapter(Context context, movieData[] moviedataArr, Intent intent, Intent intent2) {
        this.context = context;
        this.data = moviedataArr;
        this.intent = intent;
        this.refresh = intent2;
    }

    @NonNull
    public viewadapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewadapter(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.response_grid, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull viewadapter viewadapter, int i) {
        final movieData moviedata = this.data[i];
        viewadapter.title.setText(moviedata.getName());
        String[] split = moviedata.getUploaddate().split("-");
        viewadapter.link.setText(String.format("%s : %s-%s-%s", new Object[]{"Date", split[2], split[1], split[0]}));
        Glide.with(this.context).load(moviedata.getImg()).into(viewadapter.movieImg);
        viewadapter.date.setText(String.format("%s : %s", new Object[]{"Time", moviedata.getGenres()}));
        viewadapter.response.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                historyViewAdapter.this.intent.putExtra("id", moviedata.getId());
                historyViewAdapter.this.intent.putExtra("name", moviedata.getName());
                historyViewAdapter.this.intent.putExtra("playlink", moviedata.getPlaylink());
                historyViewAdapter.this.intent.putExtra("year", moviedata.getYear());
                historyViewAdapter.this.intent.putExtra("desc", moviedata.getDescription());
                historyViewAdapter.this.intent.putExtra("genres", moviedata.getGenres());
                historyViewAdapter.this.intent.putExtra("language", moviedata.getLanguage());
                historyViewAdapter.this.intent.putExtra("type", moviedata.getType());
                historyViewAdapter.this.intent.putExtra("uploaddate", moviedata.getUploaddate());
                historyViewAdapter.this.intent.putExtra("releasedate", moviedata.getReleasedate());
                historyViewAdapter.this.intent.putExtra("views", moviedata.getViews());
                historyViewAdapter.this.context.startActivity(historyViewAdapter.this.intent);
            }
        });
        viewadapter.delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("https://moviezfun.com/json/history.php?mid=");
                stringBuilder.append(moviedata.getId());
                stringBuilder.append("&delete&uid=");
                stringBuilder.append(Constants.UUID(historyViewAdapter.this.context));
                Volley.newRequestQueue(historyViewAdapter.this.context).add(new StringRequest(stringBuilder.toString(), new Listener<String>() {
                    public void onResponse(String str) {
                        Toast.makeText(historyViewAdapter.this.context, str, 0).show();
                        historyViewAdapter.this.context.startActivity(historyViewAdapter.this.refresh);
                    }
                }, new ErrorListener() {
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(historyViewAdapter.this.context, volleyError.getMessage(), 0).show();
                    }
                }));
            }
        });
    }

    public int getItemCount() {
        return this.data.length;
    }
}
