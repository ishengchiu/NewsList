package news.agoda.com.sample.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class handle Http Connection to fetch data
 */

public class JsonStringFetcher {
    private static final String TAG = JsonStringFetcher.class.getSimpleName();
    private static final int READ_TIMEOUT = 10000;//10 sec.
    private static final int CONNECT_TIMEOUT = 20000;//20 sec.

    @Nullable
    public static String fetch(@NonNull String apiUrl) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            //Starts fetching
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(TAG, "The response:" + response);
            if (response == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                // Convert the InputStream into a string
                return readStream(is);
            }
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return null;
    }

    private static String readStream(InputStream stream) throws IOException{
        StringBuilder sb = new StringBuilder();
        InputStreamReader isr = null;
        BufferedReader reader = null;
        try {
            isr = new InputStreamReader(stream);
            reader = new BufferedReader(isr);
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } finally {
            if (isr != null) isr.close();
            if (reader != null) reader.close();
        }
        return sb.toString();
    }
}