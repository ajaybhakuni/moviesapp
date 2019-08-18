package tds.com.moviezlub;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;

class ImageAdapter extends PagerAdapter {
    private movieData[] data;
    private Context mContext;
    private Intent zoom;

    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == obj;
    }

    ImageAdapter(Context context, movieData[] moviedataArr, Intent intent) {
        this.mContext = context;
        this.data = moviedataArr;
        this.zoom = intent;
    }

    @NonNull
    public Object instantiateItem(@NonNull ViewGroup viewGroup, int i) {
        final movieData moviedata = this.data[i];
        View inflate = View.inflate(this.mContext, R.layout.slider, null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.slider);
        viewGroup.addView(inflate, 0);
        Glide.with(this.mContext).load(moviedata.getImg()).into(imageView);
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ImageAdapter.this.zoom.putExtra("type", moviedata.getName());
                ImageAdapter.this.mContext.startActivity(ImageAdapter.this.zoom);
            }
        });
        return inflate;
    }

    public void destroyItem(@NonNull ViewGroup viewGroup, int i, @NonNull Object obj) {
        viewGroup.removeView((ViewGroup) obj);
    }

    public int getCount() {
        return this.data.length;
    }
}
