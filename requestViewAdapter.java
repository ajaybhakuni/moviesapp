package tds.com.moviezlub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class requestViewAdapter extends Adapter<requestViewAdapter.viewadapter> {
    private final Context context;
    private final movieData[] data;
    private final Intent intent;
    private final Intent refresh;

    class viewadapter extends ViewHolder {
        final TextView date;
        final ImageButton delete;
        final TextView title;

        viewadapter(@NonNull View view) {
            super(view);
            this.date = (TextView) view.findViewById(R.id.date);
            this.title = (TextView) view.findViewById(R.id.title_movie);
            this.delete = (ImageButton) view.findViewById(R.id.delete);
        }
    }

    requestViewAdapter(Context context, movieData[] moviedataArr, Intent intent, Intent intent2) {
        this.context = context;
        this.data = moviedataArr;
        this.intent = intent;
        this.refresh = intent2;
    }

    @NonNull
    public viewadapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewadapter(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.request_grid, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull viewadapter viewadapter, int i) {
        final movieData moviedata = this.data[i];
        viewadapter.title.setText(moviedata.getName());
        String[] split = moviedata.getUploaddate().split("-");
        viewadapter.date.setText(String.format("%s : %s-%s-%s", new Object[]{"Request Date", split[2], split[1], split[0]}));
        viewadapter.title.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                requestViewAdapter.this.context.startActivity(requestViewAdapter.this.intent);
            }
        });
        viewadapter.delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("https://moviezfun.com/json/movierequest.php?from=del&uid=");
                stringBuilder.append(Constants.UUID(requestViewAdapter.this.context));
                stringBuilder.append("&id=");
                stringBuilder.append(moviedata.getId());
                Volley.newRequestQueue(requestViewAdapter.this.context).add(new StringRequest(stringBuilder.toString(), new Listener<String>() {
                    public void onResponse(String str) {
                        Toast.makeText(requestViewAdapter.this.context, str, 0).show();
                        requestViewAdapter.this.context.startActivity(requestViewAdapter.this.refresh);
                    }
                }, new ErrorListener() {
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(requestViewAdapter.this.context, volleyError.getMessage(), 0).show();
                    }
                }));
            }
        });
    }

    public int getItemCount() {
        return this.data.length;
    }
}
