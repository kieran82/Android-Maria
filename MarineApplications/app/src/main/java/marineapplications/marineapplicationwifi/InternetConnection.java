package marineapplications.marineapplicationwifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by kieranmoroney on 13/09/2016.
 */
public class InternetConnection {
    /**
     * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean checkConnection(Context context) {
        // get Connectivity Manager object to check connection
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        // connected to the internet
        return activeNetwork != null;
    }
}
