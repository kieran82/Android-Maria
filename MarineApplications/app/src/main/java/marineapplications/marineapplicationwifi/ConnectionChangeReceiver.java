package marineapplications.marineapplicationwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by kieranmoroney on 18/07/2016.
 * Wifi Broadcast Reciever
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    /**
     * The Shared preference.
     */
    SharedPreference sharedPreference;
    /**
     * The Context.
     */
    ConnectionChangeReceiver context = this;
    /**
     * The Tag wifi connection.
     */
    String TAG_WIFI_CONNECTION = "current_wifi_status";
    /**
     * The Wifidisplay.
     */
    ArrayList<String> wifidisplay;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreference = new SharedPreference();
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            String wifistatus = "";
            if (sharedPreference.getValue(context, TAG_WIFI_CONNECTION) != null) {
                wifistatus = sharedPreference.getValue(context, TAG_WIFI_CONNECTION);
            }

            sharedPreference.save(context, TAG_WIFI_CONNECTION, "yes");
            Toast.makeText(context, "Wifi Connected!", Toast.LENGTH_SHORT).show();
            if (wifistatus.equals("no")) {
                intent = new Intent(context, IPService.class);
                intent.putExtra("CHECK_WIFI", "yes");
                context.startService(intent);
            } else {
                intent = new Intent(context, IPService.class);
                intent.putExtra("CHECK_WIFI", "no");
                context.startService(intent);
            }
        } else {
            sharedPreference.save(context, TAG_WIFI_CONNECTION, "no");
            Toast.makeText(context, "Wifi Not Connected!", Toast.LENGTH_SHORT).show();
        }
    }
}