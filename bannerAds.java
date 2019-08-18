package tds.com.moviezlub;

import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;

class bannerAds {
    private AdView adView;

    bannerAds(AdView adView) {
        this.adView = adView;
    }

    /* Access modifiers changed, original: 0000 */
    public void loadAds() {
        this.adView.loadAd(new Builder().build());
    }
}
