package tds.com.moviezlub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;

public class movieViewAdapter extends Adapter<movieViewAdapter.viewadapter> {
    private final Context context;
    private final movieData[] data;
    private final Intent intent;

    class viewadapter extends ViewHolder {
        final ImageView imageView;
        final TextView title;

        viewadapter(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.gridImg);
            this.title = (TextView) view.findViewById(R.id.title_movie);
        }
    }

    movieViewAdapter(Context context, movieData[] moviedataArr, Intent intent) {
        this.context = context;
        this.data = moviedataArr;
        this.intent = intent;
    }

    @NonNull
    public viewadapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewadapter(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_grid, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull viewadapter viewadapter, int i) {
        final movieData moviedata = this.data[i];
        Glide.with(this.context).load(moviedata.getImg()).into(viewadapter.imageView);
        viewadapter.title.setText(moviedata.getName());
        viewadapter.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                movieViewAdapter.this.intent.putExtra("id", moviedata.getId());
                movieViewAdapter.this.intent.putExtra("name", moviedata.getName());
                movieViewAdapter.this.intent.putExtra("playlink", moviedata.getPlaylink());
                movieViewAdapter.this.intent.putExtra("year", moviedata.getYear());
                movieViewAdapter.this.intent.putExtra("desc", moviedata.getDescription());
                movieViewAdapter.this.intent.putExtra("genres", moviedata.getGenres());
                movieViewAdapter.this.intent.putExtra("language", moviedata.getLanguage());
                movieViewAdapter.this.intent.putExtra("type", moviedata.getType());
                movieViewAdapter.this.intent.putExtra("uploaddate", moviedata.getUploaddate());
                movieViewAdapter.this.intent.putExtra("releasedate", moviedata.getReleasedate());
                movieViewAdapter.this.intent.putExtra("views", moviedata.getViews());
                movieViewAdapter.this.context.startActivity(movieViewAdapter.this.intent);
            }
        });
    }

    public int getItemCount() {
        return this.data.length;
    }
}
