package tds.com.moviezlub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public  class commentViewAdapter extends Adapter<commentViewAdapter.viewadapter> {
    private final commentData[] data;

    class viewadapter extends ViewHolder {
        final TextView comment;
        final TextView date;
        final TextView user;

        viewadapter(@NonNull View view) {
            super(view);
            this.user = (TextView) view.findViewById(R.id.user);
            this.comment = (TextView) view.findViewById(R.id.comment);
            this.date = (TextView) view.findViewById(R.id.date);
        }
    }

    commentViewAdapter(commentData[] commentdataArr) {
        this.data = commentdataArr;
    }

    @NonNull
    public viewadapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewadapter(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_box, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull viewadapter viewadapter, int i) {
        commentData commentdata = this.data[i];
        viewadapter.user.setText(String.format("User : %s", new Object[]{commentdata.getUid()}));
        viewadapter.date.setText(commentdata.getDate());
        viewadapter.comment.setText(commentdata.getComment());
    }

    public int getItemCount() {
        return this.data.length;
    }
}
