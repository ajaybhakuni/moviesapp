package tds.com.moviezlub;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class responseViewAdapter extends Adapter<responseViewAdapter.viewadapter> {
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

    responseViewAdapter(Context context, movieData[] moviedataArr, Intent intent, Intent intent2) {
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
        viewadapter.date.setText(String.format("%s : %s-%s-%s", new Object[]{"Response Date", split[2], split[1], split[0]}));
        Glide.with(this.context).load(moviedata.getImg()).into(viewadapter.movieImg);
        viewadapter.link.setText(moviedata.getPlaylink());
        viewadapter.link.setTextColor(this.context.getResources().getColor(R.color.colorAccent));
        viewadapter.response.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                responseViewAdapter.this.intent.setData(Uri.parse(moviedata.getPlaylink()));
                responseViewAdapter.this.context.startActivity(responseViewAdapter.this.intent);
            }
        });
        viewadapter.delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("https://moviezfun.com/json/movieresponse.php?uid=");
                stringBuilder.append(Constants.UUID(responseViewAdapter.this.context));
                stringBuilder.append("&del&id=");
                stringBuilder.append(moviedata.getId());
                Volley.newRequestQueue(responseViewAdapter.this.context).add(new StringRequest(stringBuilder.toString(), new Listener<String>() {
                    public void onResponse(String str) {
                        Toast.makeText(responseViewAdapter.this.context, str, 0).show();
                        responseViewAdapter.this.context.startActivity(responseViewAdapter.this.refresh);
                    }
                }, new ErrorListener() {
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(responseViewAdapter.this.context, volleyError.getMessage(), 0).show();
                    }
                }));
            }
        });
    }

    public int getItemCount() {
        return this.data.length;
    }
}
