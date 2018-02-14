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
 * The type Temperature download service.
 */
public class TemperatureDownloadService extends Service {
    private static final String TAG_DATABASE_CONNECTION = "current_database_connection";
    private static final String TAG_TRIPID = "trip_id";
    private static final String TAG_USER = "user_id";
    private static final String TAG_PRAWNDIP = "prawn_dips";

    /**
     * The Confclass.
     */
    Configure_Class confclass = null;
    /**
     * The Context.
     */
    Context context = this;
    /**
     * The Db helper.
     */
    DatabaseHelper dbHelper;
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
        Log.d("JSON SERVICE", "starting temperature downloading");
        new GetTemperatureTask().execute();
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private class GetTemperatureTask extends AsyncTask<Void, Void, JSONObject> {
        private static final String TAG_RECORD_DATETIME = "record_DateTime";
        private static final String TAG_START_RECORDING = "start_recording";
        private static final String TAG_TEMP_STRING = "temp_string";
        private static final String TAG_DATETIME = "datetime";
        private static final String TAG_GPS = "gps";
        /**
         * The Insertdb.
         */
        InsertDB_Class insertdb;
        /**
         * The Confclass.
         */
        Configure_Class confclass;

        /**
         * The Readwrite.
         */
        Read_Write_Class readwrite;

        /**
         * Instantiates a new Get temperature task.
         */
        GetTemperatureTask() {
            readwrite = new Read_Write_Class();
            insertdb = new InsertDB_Class();
            confclass = new Configure_Class();
            sharedPreference = new SharedPreference();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {
            try {
                if (sharedPreference.getValue(context, TAG_DATABASE_CONNECTION) != null && confclass.pingURL("http://" + sharedPreference.getValue(context, TAG_DATABASE_CONNECTION) + "/config.php", 1000)) {
                    String current_trip_id = "";
                    if (sharedPreference.getValue(context, TAG_USER) != null && !sharedPreference.getValue(context, TAG_USER).equals("")) {
                        if (sharedPreference.getValue(context, TAG_TRIPID) != null && !sharedPreference.getValue(context, TAG_TRIPID).equals("")) {
                            Log.d("JSON current tripid", sharedPreference.getValue(context, TAG_TRIPID));
                            current_trip_id = sharedPreference.getValue(context, TAG_TRIPID);
                        }
                        String current_user_id = sharedPreference.getValue(context, TAG_USER);
                        JSONParserHttp jsonParser_http = new JSONParserHttp();
                        String shareddb = sharedPreference.getValue(context, TAG_DATABASE_CONNECTION);
                        JSONObject tempfileobj = readwrite.getJSONData(context, "Recording_Temperature_Information.json");
                        String recording_time = tempfileobj.getString("recording_time");
                        Log.d("JSON trip ip", shareddb);
                        Log.d("JSON trip record", recording_time);
                        String url_rp_address = "http://" + shareddb + "/get_temperature_record_file.php";
                        HashMap<String, String> params = new HashMap<>();
                        params.put("system_request", "get_temperatures");
                        params.put("recording_time", recording_time);
                        params.put("trip_id", current_trip_id);
                        params.put("user_id", current_user_id);

                        Log.d("request", "starting");

                        JSONObject json = jsonParser_http.makeHttpRequest(
                                url_rp_address, "POST", params);

                        if (json != null) {
                            Log.d("JSON FF", json.toString());
                            String start_recording = json.getString(TAG_START_RECORDING);
                            String gps = json.getString(TAG_GPS);
                            int prawncount = json.getInt(TAG_PRAWNDIP);
                            String insert_gps = gps.equals("Not Available") ? "" : gps;
                            String fullDateTime = json.getString(TAG_DATETIME);
                            String tempstring = json.getString(TAG_TEMP_STRING);
                            String record_date_time = json.getString(TAG_RECORD_DATETIME);

                            if (recording_time.equals("")) {
                                tempfileobj.put("recording_time", record_date_time);
                                readwrite.saveData(context, tempfileobj.toString(), "Recording_Temperature_Information.json");
                            }

                            if (!current_trip_id.equals("")) {
                                if (start_recording.equals("yes")) {
                                    tempfileobj.put("recording_time", record_date_time);
                                    readwrite.saveData(context, tempfileobj.toString(), "Recording_Temperature_Information.json");

                                    String[] tempseparated = tempstring.split(",");
                                    insertdb.TemperatureRecordInsertDB(context, fullDateTime, tempseparated[1], tempseparated[2], tempseparated[3], tempseparated[0], insert_gps, current_trip_id, current_user_id, "A");
                                }

                                if (prawncount > 0) {
                                    insertdb.UpdateTripPrawnDipCounter(context, current_trip_id, prawncount);
                                }
                            }

                            return json;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            if (json != null) {

            } else {
            }
        }

        @Override
        protected void onCancelled() {
        }
    }
}							