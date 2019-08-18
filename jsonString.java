package tds.com.moviezlub;

import java.net.URLEncoder;
import java.util.Iterator;
import org.json.JSONObject;

class jsonString {
    jsonString() {
    }

    static String getPostDataString(JSONObject jSONObject) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator keys = jSONObject.keys();
        Object obj = 1;
        while (keys.hasNext()) {
            String str = (String) keys.next();
            Object obj2 = jSONObject.get(str);
            if (obj != null) {
                obj = null;
            } else {
                stringBuilder.append("&");
            }
            String str2 = "UTF-8";
            stringBuilder.append(URLEncoder.encode(str, str2));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(obj2.toString(), str2));
        }
        return stringBuilder.toString();
    }
}
