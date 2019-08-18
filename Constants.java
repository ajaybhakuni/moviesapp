package tds.com.moviezlub;

import android.content.Context;

class Constants {
    static final String CHANNEL_DESCRIPTION = "MOVIEZ FUN NEW UPDATES DETAILS";
    static final String CHANNEL_ID = "MOVIEZFUN_MAIN_CHANNEL";
    static final String CHANNEL_NAME = "MOVIEZFUN UPDATES";
    static final String DEVELOPER_KEY = "AIzaSyBjzF_quCozcJWrhsvgBz-ZvGhnZsbpeSk";
    static final String DEVICE_TOKEN = "fmctoken";
    static final String DEVICE_TOKEN_STATUS = "fmctokenstatus";
    static final String HOME_DATA_STRING = "online.movieone.movieone.HOME_DATA_STRING";
    static final String LOGIN_ID = "userid";
    static final String LOGIN_KEY = "online.movieone.movieone.LOGIN_KEY";
    static final String[] MOVIE_TYPE = new String[]{"Bollywood", "Hollywood", "Tollywood", "Indian-English", "Hollywood-Hindi", "Tollywood-Hindi", "Marathi", "Punjabi", "Bhojpuri", "Gujarati"};

    Constants() {
    }

    static String UUID(Context context) {
        return context.getSharedPreferences(LOGIN_KEY, 0).getString(LOGIN_ID, BuildConfig.FLAVOR);
    }
}
