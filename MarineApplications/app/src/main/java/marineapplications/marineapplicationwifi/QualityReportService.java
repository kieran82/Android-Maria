package marineapplications.marineapplicationwifi;

/**
 * Created by kieranmoroney on 06/05/2017.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * The type Quality report service.
 */
public class QualityReportService extends Service {
    /**
     * The constant notify.
     */
    public static final int notify = 860400;  //interval between two services(Here Service run every 5 seconds)
    private static final String TAG_USER = "user_id";
    private static final String TAG_TRIPID = "trip_id";

    /**
     * The Confclass.
     */
//Using Classes
    Configure_Class confclass = null;
    /**
     * The Shared preference.
     */
    SharedPreference sharedPreference;
    /**
     * The Db helper.
     */
    DatabaseHelper dbHelper;

    /**
     * The Readwrite.
     */
    Read_Write_Class readwrite;
    /**
     * The Context.
     */
    Context context = this;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("JSON SERVICE", "starting quality report");
        new UserQualityTask().execute();
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * The type User quality task.
     */
    private class UserQualityTask extends AsyncTask<Void, Void, JSONObject> {
        private static final String TAG_QREPORT = "qreport";
        private static final String TAG_QRCOUNT = "qrcount";
        /**
         * The Insertdb.
         */
        InsertDB_Class insertdb;
        /**
         * The Readwrite.
         */
        Read_Write_Class readwrite;
        /**
         * The Confclass.
         */
        Configure_Class confclass;

        /**
         * Instantiates a new User quality task.
         */
        UserQualityTask() {
            sharedPreference = new SharedPreference();
            insertdb = new InsertDB_Class();
            readwrite = new Read_Write_Class();
            confclass = new Configure_Class();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {
            String user_id = sharedPreference.getValue(context, TAG_USER);
            if (sharedPreference.getValue(context, TAG_TRIPID) != null && !sharedPreference.getValue(context, TAG_TRIPID).equals("")) {
                String quality_check_time = "";
                String quality_check_status = "";
                JSONObject qualityfileobj = null;

                try {
                    qualityfileobj = readwrite.getJSONData(context, "Raspberry_Quality_Report_info.json");
                    Log.d("JSON quality", qualityfileobj.toString());
                    quality_check_time = qualityfileobj.getString("checking_time");
                    quality_check_status = qualityfileobj.getString("check");
                    Log.d("JSON checking_time", quality_check_time);
                    if (!quality_check_time.equals("")) {
                        if (confclass.isConnectedToInternet()) {
                            String LOGIN_URL = "https://login.marineapps.net/api_check.php";
                            HashMap<String, String> params = new HashMap<>();
                            params.put("system_request", "get_quality_report");
                            params.put("userid", user_id);
                            JSONObject json = insertdb.insertPOSTRecordOnline(LOGIN_URL, params);
                            if (json != null) {
                                return json;
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            if (json != null) {
                try {
                    if (json.has(TAG_QRCOUNT)) {
                        int qrcount = json.getInt(TAG_QRCOUNT);
                        if (qrcount > 0) {
                            String quality_record = "net|" + json.getString(TAG_QREPORT);
                            createNotification("Quality Reports", "Quality report found", quality_record);
                        }
                    } else {
                        if (json.has(TAG_QRCOUNT)) {
                            String qreport = json.getString(TAG_QREPORT);
                            if (!qreport.equals("")) {
                                String quality_record = "net|" + json.getString(TAG_QREPORT);
                                createNotification("Quality Reports", "Quality report found", quality_record);
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onCancelled() {
        }

        private void createNotification(String contentTitle, String contentText, String extra) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.sym_def_app_icon)
                            .setAutoCancel(true)
                            .setContentTitle(contentTitle)
                            .setContentText(contentText);

            Intent resultIntent = new Intent(context, QualityReportActivity.class);
            resultIntent.putExtra("quality_report_id", extra);
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            context,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            // Sets an ID for the notification
            int mNotificationId = 001;
            // Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
            //Show the notification
        }
    }
}
