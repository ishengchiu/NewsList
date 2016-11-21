package news.agoda.com.sample.util;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

/**
 * Created by chiu_mac on 2016/11/21.
 */

public class NetworkUtil {
    @VisibleForTesting
    public static boolean INTERNET = true;

    public static boolean hasInternetConnection(Context context) {
        return INTERNET;
    }
}
