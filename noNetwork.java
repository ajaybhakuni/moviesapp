package tds.com.moviezlub;



import android.content.Context;
import android.net.ConnectivityManager;

class noNetwork {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Context context;

    noNetwork(Context context) {
        this.context = context;
    }

    /* Access modifiers changed, original: 0000 */
    public boolean isNetworkConnected() {
        return ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }
}

