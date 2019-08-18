package tds.com.moviezlub;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.util.Objects;

public class privacy extends AppCompatActivity {
    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_privacy);
        ((ActionBar) Objects.requireNonNull(getSupportActionBar())).setTitle("Privacy");
        WebView webView = (WebView) findViewById(R.id.privacyView);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/privacy.html");
        MobileAds.initialize(this, getResources().getString(R.string.app_ad_id));
        new bannerAds((AdView) findViewById(R.id.adViews)).loadAds();
    }

    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
