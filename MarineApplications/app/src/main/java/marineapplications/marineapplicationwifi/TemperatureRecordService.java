package marineapplications.marineapplicationwifi;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kieranmoroney on 06/05/2017.
 */
public class TemperatureRecordService extends Service {
    /**
     * The constant notify.
     */
    public static final int notify = 40350;
    private static final String TAG_TRIPID = "trip_id";
    private static final String TAG_TRIP_ONLINE_ID = "online_trip_id";
    private static final String TAG_DATABASE_CONNECTION = "current_database_connection";
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
    DatabaseHelper dbHelperClass;
    /**
     * The Readwrite.
     */
    Read_Write_Class readwrite;
    /**
     * The Context.
     */
    Context context = this;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("JSON SERVICE", "starting temperature recording");
        new TripRecordTempTask().execute();
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * The type Trip record temp task.
     */
    public class TripRecordTempTask extends AsyncTask<Void, Void, JSONObject> {
        private static final String TAG_SUCCESS = "success";

        /**
         * The Templist.
         */
        JSONArray templist = new JSONArray();
        /**
         * The Tripobj.
         */
        JSONObject tripobj = new JSONObject();
        /**
         * The Record list.
         */
        ArrayList<String> recordList = new ArrayList<String>();
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
         * Instantiates a new Trip record temp task.
         */
        public TripRecordTempTask() {
            insertdb = new InsertDB_Class();
            confclass = new Configure_Class();
            readwrite = new Read_Write_Class();
            dbHelperClass = new DatabaseHelper(context);
            sharedPreference = new SharedPreference();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {
            boolean rockblock_status;
            String sendingmethod = "";
            boolean online_status = confclass.isConnectedToInternet();
            Log.d("JSON internet result", String.valueOf(online_status));
            if (readwrite.isFilePresent(context, "MAIN_config.json")) {
                Log.d("JSON main result", "found");
                JSONObject masterfilejs = readwrite.getJSONData(context, "MAIN_config.json");
                String rockblock_setting = null;
                try {
                    rockblock_setting = masterfilejs.getJSONObject("vessel").getJSONObject("settings").getString("rock_block");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("JSON rb display", rockblock_setting);
                rockblock_status = !rockblock_setting.equals("") && rockblock_setting != null && !rockblock_setting.equals("null");
                Log.d("JSON rb bool", String.valueOf(rockblock_status));
                if (rockblock_status) {
                    sendingmethod = "rockblock";
                } else if (online_status) {
                    sendingmethod = "internet";
                } else if (!online_status && !rockblock_status) {
                    sendingmethod = "log";
                }
            }

            String online_trip_id = "";
            String pingCheck = "no";
            boolean online_trip_status = !sendingmethod.equals("log");
            try {

                if (sharedPreference.getValue(context, TAG_DATABASE_CONNECTION) != null && confclass.pingURL("http://" + sharedPreference.getValue(context, TAG_DATABASE_CONNECTION) + "/config.php", 1000)) {
                    pingCheck = "yes";
                }

                if (sharedPreference.getValue(context, TAG_TRIP_ONLINE_ID) != null) {
                    online_trip_id = sharedPreference.getValue(context, TAG_TRIP_ONLINE_ID);
                }

                if (sharedPreference.getValue(context, TAG_TRIPID) != null && !sharedPreference.getValue(context, TAG_TRIPID).equals("")) {

                    Log.d("JSON FUNNY trip_status", "started");
                    String mtripid = sharedPreference.getValue(context, TAG_TRIPID);
                    Log.d("record trip_id", mtripid);
                    int tempcount = dbHelperClass.getTemperatureTripRecordCount(mtripid);
                    Log.d("JSON temperature_count", Integer.toString(tempcount));
                    Log.d("JSON online status", String.valueOf(online_trip_status));

                    if (tempcount >= 6) {
                        Log.d("JSON temperature result", "greater than 1");
                        List<Temperature_Class> temp = dbHelperClass.getAllTripTemperatureRecords(mtripid);
                        for (int i = 0; i < temp.size(); i++) {
                            templist.put(temp.get(i).getJSONObject());
                            String tempre = temp.get(i).getStringObject();
                            Log.d("JSON temperature re", tempre);
                            Collections.addAll(recordList, temp.get(i).getStringObject());
                        }

                        Trip_Class tripdata = dbHelperClass.getCurrentTripRecords(mtripid);
                        if (online_status) {
                            JSONObject tclassdata = tripdata.getStartTripJSONObject();
                            try {
                                tripobj.put("temperature_record", templist);
                                tripobj.put("trip_record", tclassdata);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String LOGIN_URL = "https://login.marineapps.net/api_check.php";
                            HashMap<String, String> params = new HashMap<>();
                            params.put("system_request", "upload_multiple_temperature");
                            params.put("data", tripobj.toString());
                            params.put("online_trip_id", online_trip_id);

                            Log.d("request", "starting");

                            JSONObject json = insertdb.insertPOSTRecordOnline(LOGIN_URL, params);

                            if (json != null) {
                                Log.d("JSON result", json.toString());
                                dbHelperClass.deleteAllTripTemperatureRecords(mtripid);
                                return json;
                            }
                        } else {
                            try {
                                Log.d("JSON sending result", "rockblock");
                                String rockblocklisttr = "";
                                String rockblock_request = "umtemp";
                                String sendrbString = "~" + rockblock_request;
                                int rcount = 340 - sendrbString.length();
                                String large_message = "";
                                String send_message = "";
                                int rockblockarraysize = recordList.size();
                                Log.d("JSON sending array", String.valueOf(rockblockarraysize));
                                if (rockblockarraysize > 0) {
                                    rockblocklisttr = TextUtils.join("|", recordList);
                                }
                                Log.d("JSON sending length", String.valueOf(rockblocklisttr.length()));
                                Log.d("JSON sending array data", rockblocklisttr);
                                if (rockblocklisttr.length() <= rcount) {
                                    send_message = rockblocklisttr + "~" + rockblock_request;
                                    large_message = "no";
                                } else {
                                    send_message = rockblocklisttr;
                                    large_message = "yes";
                                }

                                String url_address = "http://" + sharedPreference.getValue(context, TAG_DATABASE_CONNECTION) + "/get_rockblock_trip_file.php";
                                Log.d("JSON sending now", "yes");
                                HashMap<String, String> params = new HashMap<>();
                                params.put("system_request", "send_rockblock_message");
                                params.put("rockblock_message", send_message);
                                params.put("rockblock_request", rockblock_request);
                                params.put("large_message", large_message);
                                JSONObject json = insertdb.insertPOSTRecordLocal(url_address, params);
                                if (json != null) {
                                    Log.d("JSON result", json.toString());
                                    int endstatus = 0;
                                    try {
                                        endstatus = json.getInt(TAG_SUCCESS);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (endstatus == 1) {
                                        dbHelperClass.deleteAllTripTemperatureRecords(mtripid);
                                    }
                                    return json;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    Log.d("JSON FUNNY trip_id", "no");
                    Log.d("JSON FUNNY trip_status", "not started");
                }
            }
            // Handle any errors that may have occurred.
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            if (json != null) {
                try {
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("JSON temp upload", "Success");
                    } else {
                        if (json.has("display_message")) {
                            String display_message = json.getString("display_message");
                            if (!display_message.equals("")) {
                                Toast.makeText(getApplicationContext(),
                                        display_message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Failure to upload record", Toast.LENGTH_LONG).show();
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
    }
}
