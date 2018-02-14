package marineapplications.marineapplicationwifi;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by kieranmoroney on 29/05/2017.
 */
public class BilgeAlarmService extends Service {
    private static final String TAG_USER = "user_id";
    /**
     * The Confclass.
     */
    Configure_Class confclass = null;
    /**
     * The Context.
     */
    Context context = this;
    /**
     * The Readwrite.
     */
    Read_Write_Class readwrite;
    /**
     * The Shared preference.
     */
    SharedPreference sharedPreference;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("JSON SERVICE", "starting bilge checking");
        new GetBilgeTask().execute();
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private class GetBilgeTask extends AsyncTask<Void, Void, JSONObject> {
        private static final String TAG_BILGE = "bilge";

        /**
         * The Confclass.
         */
        Configure_Class confclass;

        /**
         * Instantiates a new Get temperature task.
         */
        GetBilgeTask() {
            confclass = new Configure_Class();
            sharedPreference = new SharedPreference();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {
            JSONParser jsonParser = new JSONParser();
            try {
                if (sharedPreference.getValue(context, TAG_USER) != null && !sharedPreference.getValue(context, TAG_USER).equals("")) {
                    String current_user_id = sharedPreference.getValue(context, TAG_USER);
                    JSONParserHttp jsonParser_http = new JSONParserHttp();
                    String url_address = "https://login.marineapps.net/episensor_api.php";
                    HashMap<String, String> params = new HashMap<>();
                    params.put("system_request", "get_bilge_episensor");
                    params.put("user_id", current_user_id);

                    Log.d("request", "starting");

                    JSONObject json = jsonParser_http.makeHttpRequest(
                            url_address, "POST", params);

                    if (json != null) {
                        Log.d("JSON FF", json.toString());
                        String bilge = json.getString(TAG_BILGE);
                        if (bilge.equals("bad")) {
                            if (confclass.isConnectedToInternet()) {
                                String messages_send = "Bilge Level high";
                                String CHECKSENDURL = "https://login.marineapps.net/send_alert_email.php?userid=" + sharedPreference.getValue(context, TAG_USER) + "&messages=" + messages_send + "";
                                jsonParser.getJSON(CHECKSENDURL);
                            }
                        }

                        return json;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

        }

        @Override
        protected void onCancelled() {
        }
    }
}
