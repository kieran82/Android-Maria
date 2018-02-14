package marineapplications.marineapplicationwifi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.HashMap;

/**
 * The type Splash activity.
 */
public class SplashActivity extends AppCompatActivity {

    private static final String TAG_VESSEL = "vessel_name";
    private static final String TAG_USER = "user_id";
    private static final String TAG_LOGIN_STATUS = "logged_status";
    private static final String TAG_TRIPID = "trip_id";
    private static final String TAG_TRIP_START_DATE = "trip_start_date";
    private static final String TAG_TRIP_LOG_NO = "trip_log_sheet_no";
    private static final String TAG_TRIP_STATUS = "trip_status";
    private static final String TAG_TRIP_ONLINE_ID = "online_trip_id";
    private static final String TAG_SSID_REDIRECT_ACTIVE = "ssid_redirect_active";
    /**
     * The Context.
     */
    Activity context = this;
    /**
     * The Loggedin.
     */
    boolean loggedin = false;
    /**
     * The Vessel name.
     */
    String vessel_name = "";
    /**
     * The User id.
     */
    String user_id = "";
    /**
     * The Vessel trip id.
     */
    String vessel_trip_id = "";
    /**
     * The Confclass.
     */
    Configure_Class confclass;
    /**
     * The Readwrite.
     */
    Read_Write_Class readwrite;
    /**
     * The Shared preference.
     */
    SharedPreference sharedPreference;
    /**
     * The Dialog view.
     */
    View dialogView = null;
    /**
     * The User input.
     */
    EditText userInput = null;
    /**
     * The Ip address.
     */
    String ipAddress = "";
    /**
     * The Wifissid.
     */
    String wifissid = "";
    /**
     * The Wifigateway.
     */
    String wifigateway = "";
    /**
     * The Main wifi.
     */
    WifiManager mainWifi = null;
    private UserConnectionTask mConnectTask = null;

    /**
     * Gets public ip.
     *
     * @return the public ip
     * @throws IOException the io exception
     */
    public static String getPublicIP() throws IOException {
        Document doc = Jsoup.connect("http://www.checkip.org").get();
        return doc.getElementById("yourip").select("h1").first().select("span").text();
    }

    @SuppressLint("WifiManagerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreference = new SharedPreference();
        if (sharedPreference.getValue(context, TAG_SSID_REDIRECT_ACTIVE) == null) {
            sharedPreference.save(context, TAG_SSID_REDIRECT_ACTIVE, "yes");
        }

        if (sharedPreference.getValue(context, TAG_TRIP_STATUS) == null) {
            sharedPreference.save(context, TAG_TRIP_STATUS, "Idle");
        }

        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if (sharedPreference.getValue(context, TAG_TRIPID) != null) {
            vessel_trip_id = sharedPreference.getValue(context, TAG_TRIPID);
        } else {
            vessel_trip_id = "";
        }
        if (!mainWifi.isWifiEnabled()) {
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
            mainWifi.setWifiEnabled(true);
        }

        if (sharedPreference.getValue(context, TAG_USER) != null) {
            if (!sharedPreference.getValue(context, TAG_USER).equals("")) {
                loggedin = true;
            } else {
                loggedin = true;
            }
        }
        if (sharedPreference.getValue(context, TAG_USER) != null) {
            vessel_name = sharedPreference.getValue(context, TAG_VESSEL);
            user_id = sharedPreference.getValue(context, TAG_USER);
        }

        confclass = new Configure_Class();
        try {
            ipAddress = confclass.wifiIpAddress(this);
            wifissid = confclass.getWiFiSSID(this);
            wifigateway = confclass.getWifiGateway(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String devicename = confclass.getDeviceName();
        String publicid = "";

        CreateFiles(ipAddress, devicename, wifigateway, wifissid, user_id, vessel_name, loggedin, publicid);
        mConnectTask = new UserConnectionTask(loggedin);
        mConnectTask.execute();
    }

    /**
     * Check wifi password boolean.
     *
     * @return the boolean
     */
    public boolean checkWifiPassword() {
        Read_Write_Class readwrite;
        readwrite = new Read_Write_Class();
        JSONObject obj = readwrite.getJSONData(context, "WifiInfo.json");
        boolean status = false;
        try {
            String pw = obj.getJSONObject("wifi").getString("pw");
            status = !pw.equals("");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * Update wifi field data.
     *
     * @param field the field
     * @param value the value
     */
    public void updateWifiFieldData(String field, String value) {
        Read_Write_Class readwrite;
        readwrite = new Read_Write_Class();
        JSONObject obj = readwrite.getJSONData(context, "WifiInfo.json");
        try {
            obj.getJSONObject("wifi").put(field, value);
            readwrite.saveData(context, obj.toString(), "WifiInfo.json");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Value exists boolean.
     *
     * @param jsonArray   the json array
     * @param valueToFind the value to find
     * @return the boolean
     */
    boolean valueExists(JSONArray jsonArray, String valueToFind) {
        return jsonArray.toString().contains("\"value\":\"" + valueToFind + "\"");
    }

    /**
     * Create files.
     *
     * @param wifi       the wifi
     * @param device     the device
     * @param gateway    the gateway
     * @param ssid       the ssid
     * @param userid     the userid
     * @param vesselname the vesselname
     * @param logged     the logged
     * @param publicid   the publicid
     */
    public void CreateFiles(String wifi, String device, String gateway, String ssid, String userid, String vesselname, boolean logged, String publicid) {
        Read_Write_Class readwrite;
        readwrite = new Read_Write_Class();
        if (!readwrite.isFilePresent(context, "trip_Information.json")) {
            try {
                String jsontrip = readwrite.AssetJSONFile("trip_details.json", context);
                String jtripresult = jsontrip.replaceAll("\\s+", "");
                readwrite.saveData(context, jtripresult, "trip_Information.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JSONObject obj = readwrite.getJSONData(context, "trip_Information.json");
            try {
                String trip_id = obj.getJSONObject("trip").getString("trip_id");
                String online_trip_id = obj.getJSONObject("trip").getString("online_trip_id");
                String start_trip_date = obj.getJSONObject("trip").getString("start_trip_date");
                String log_sheet_text = obj.getJSONObject("trip").getString("log_sheet_text");
                String current_status = obj.getJSONObject("trip").getString("current_status");
                if (current_status.equals("Idle")) {
                    if (sharedPreference.getValue(context, TAG_TRIPID) != null) {
                        sharedPreference.save(context, TAG_TRIPID, "");
                        sharedPreference.save(context, TAG_TRIP_ONLINE_ID, "");
                        sharedPreference.save(context, TAG_TRIP_STATUS, "Idle");
                        sharedPreference.save(context, TAG_TRIP_LOG_NO, "");
                        sharedPreference.save(context, TAG_TRIP_START_DATE, "");
                    }
                } else {
                    if (sharedPreference.getValue(context, TAG_TRIPID) == null && current_status.equals("Start")) {
                        sharedPreference.save(context, TAG_TRIPID, trip_id);
                        sharedPreference.save(context, TAG_TRIP_ONLINE_ID, online_trip_id);
                        sharedPreference.save(context, TAG_TRIP_STATUS, current_status);
                        sharedPreference.save(context, TAG_TRIP_LOG_NO, log_sheet_text);
                        sharedPreference.save(context, TAG_TRIP_START_DATE, start_trip_date);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (!readwrite.isFilePresent(context, "WifiInfo.json")) {
            try {
                String jsonuser = readwrite.AssetJSONFile("wifi_details.json", context);
                String jvesselresult = jsonuser.replaceAll("\\s+", "");
                JSONObject wobj = new JSONObject(jvesselresult);
                wobj.getJSONObject("wifi").put("device_name", device);
                wobj.getJSONObject("wifi").put("gateway", gateway);
                wobj.getJSONObject("wifi").put("ssid", ssid);
                wobj.getJSONObject("wifi").put("wifi_address", wifi);
                readwrite.saveData(context, wobj.toString(), "WifiInfo.json");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        } else {
            JSONObject wifiobject = null;
            try {
                JSONObject wobj = readwrite.getJSONData(context, "WifiInfo.json");
                if (wobj != null) {
                    wifiobject = readwrite.getJSONData(context, "WifiInfo.json");
                } else {
                    String jsonwifi = readwrite.AssetJSONFile("wifi_details.json", context);
                    wifiobject = new JSONObject(jsonwifi);
                }
                JSONObject wifijo = wifiobject.getJSONObject("wifi");
                Log.d("JSON WIFI found", wifiobject.toString());
                String ssidold = wifiobject.getJSONObject("wifi").getString("ssid");
                if (!wifijo.has("public_ip")) {
                    wifiobject.getJSONObject("wifi").put("public_ip", publicid);
                }
                if (ssidold.equals("")) {
                    wifiobject.getJSONObject("wifi").put("ssid", ssid);
                    wifiobject.getJSONObject("wifi").put("gateway", gateway);
                }
                wifiobject.getJSONObject("wifi").put("device_name", device);
                wifiobject.getJSONObject("wifi").put("wifi_address", wifi);
                readwrite.saveData(context, wifiobject.toString(), "WifiInfo.json");
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }

        if (!readwrite.isFilePresent(context, "MAIN_config.json")) {
            try {
                String jsonmain = readwrite.AssetJSONFile("MAIN_config.json", context);
                String jmailresult = jsonmain.replaceAll("\\s+", "");
                readwrite.saveData(context, jmailresult, "MAIN_config.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!readwrite.isFilePresent(context, "UserInfo.json")) {
            try {
                String jsonuser = readwrite.AssetJSONFile("user_details.json", context);
                String jvesselresult = jsonuser.replaceAll("\\s+", "");
                JSONObject vobj = new JSONObject(jvesselresult);
                Log.d("JSON user found", vobj.toString());
                if (logged) {
                    vobj.getJSONObject("vessel").put("User_id", userid);
                    vobj.getJSONObject("vessel").put("name", vesselname);
                }
                readwrite.saveData(context, vobj.toString(), "UserInfo.json");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        } else {
            JSONObject vobj = readwrite.getJSONData(context, "UserInfo.json");
            try {
                String attributeuserid = vobj.getJSONObject("vessel").getString("User_id");
                String attributevessel = vobj.getJSONObject("vessel").getString("name");

                if (!attributeuserid.equals("")) {
                    if (sharedPreference.getValue(context, TAG_USER) == null) {
                        sharedPreference.save(context, TAG_USER, attributeuserid);
                        sharedPreference.save(context, TAG_VESSEL, attributevessel);
                        sharedPreference.save(context, TAG_LOGIN_STATUS, "logged in");
                    }
                } else {
                    if (logged) {
                        vobj.getJSONObject("vessel").put("User_id", userid);
                        vobj.getJSONObject("vessel").put("name", vesselname);
                        readwrite.saveData(context, vobj.toString(), "UserInfo.json");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (!readwrite.isFilePresent(context, "Fields_Information.json")) {
            JSONObject fieldsobj = null;
            try {
                String jsonuser = readwrite.AssetJSONFile("fields_details.json", context).replaceAll("\\s+", "");
                fieldsobj = new JSONObject(jsonuser);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            readwrite.saveData(context, fieldsobj != null ? fieldsobj.toString() : null, "Fields_Information.json");
        }

        if (!readwrite.isFilePresent(context, "wifi_connection_details.json")) {
            JSONObject fieldsobj = null;
            try {
                String jsonuser = readwrite.AssetJSONFile("wifi_connection_details.json", context).replaceAll("\\s+", "");
                fieldsobj = new JSONObject(jsonuser);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            readwrite.saveData(context, fieldsobj != null ? fieldsobj.toString() : null, "wifi_connection_details.json");
        }

        if (!readwrite.isFilePresent(context, "Recording_Temperature_Information.json")) {
            JSONObject tempobj = null;
            try {
                String jsonuser = readwrite.AssetJSONFile("Raspberry_temperature_details.json", context).replaceAll("\\s+", "");
                tempobj = new JSONObject(jsonuser);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            readwrite.saveData(context, tempobj != null ? tempobj.toString() : null, "Recording_Temperature_Information.json");
        }

        if (!readwrite.isFilePresent(context, "Raspberry_Quality_Report_info.json")) {
            JSONObject raspobj = null;
            try {
                String jsonrspuser = readwrite.AssetJSONFile("Raspberry_quality_check_details.json", context).replaceAll("\\s+", "");
                raspobj = new JSONObject(jsonrspuser);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            readwrite.saveData(context, raspobj != null ? raspobj.toString() : null, "Raspberry_Quality_Report_info.json");
        }

        if (!readwrite.isFilePresent(context, "Raspberry_PI_Information.json")) {
            JSONObject ipsobj = null;
            try {
                String jsonuser = readwrite.AssetJSONFile("raspberry_pi_details.json", context).replaceAll("\\s+", "");
                ipsobj = new JSONObject(jsonuser);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            readwrite.saveData(context, ipsobj != null ? ipsobj.toString() : null, "Raspberry_PI_Information.json");
        } else {
            int orgcount;
            int newcount;
            JSONObject robj = readwrite.getJSONData(context, "Raspberry_PI_Information.json");
            try {
                JSONArray jsr = robj.getJSONArray("rasperry_pi");
                orgcount = jsr.length();
                for (int i = 0; i < jsr.length(); i++) {
                    JSONObject jsrobj = jsr.getJSONObject(i);
                    String jval = jsrobj.getString("value");
                    if (jval.equals("")) {
                        robj.getJSONArray("rasperry_pi").remove(i);
                    }
                }
                newcount = jsr.length();
                if (newcount != orgcount) {
                    readwrite.saveData(context, robj.toString(), "Raspberry_PI_Information.json");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("JSON RP file found", robj.toString());
        }

        if (!readwrite.isFilePresent(context, "File_Information.json")) {
            String empty = "";
            readwrite.saveData(context, empty, "File_Information.json");
        }
    }

    /**
     * The type User connection task.
     */
    public class UserConnectionTask extends AsyncTask<Void, Void, JSONObject> {
        private static final String TAG_CLEANING_SCHEDULE = "trip_cleaning_schedule";
        private static final String TAG_WASTE_ACTION = "trip_waste_action";
        private static final String TAG_WHALE_DOLPHIN_RECORD = "trip_etp";
        private static final String TAG_PRAWN_DIP_RECORD = "trip_prawn_dip_record";
        private static final String TAG_SLIPPAGE_RECORD = "trip_slippage_record";
        private static final String TAG_INCIDENT_RECORD = "trip_incident_record";
        private static final String TAG_PRAWN_DIP_PROCEDURE = "trip_prawn_dip_procedure";
        private static final String TAG_CALIBRATION_OF_SCALES = "trip_calibration_of_scales";
        private static final String TAG_TARGET_SPECIES_FISHING_PROCEDURE = "trip_target_species_fishing_procedure";
        private static final String TAG_LINE_CAUGHT_PROCEDURE = "trip_line_caught_procedure";
        private static final String TAG_VISITOR_RECORD = "trip_visitor_record";
        private static final String TAG_MARINE_LITTER_RECORD = "trip_marine_litter_record";
        private static final String TAG_LOST_GEAR_RECORD = "trip_lost_gear_record";
        private static final String TAG_COMMUNICATION_RECORD = "trip_communication_record";
        private static final String TAG_DISPATCH_RECORD = "trip_dispatch_freezer_record";
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
         * The Db helper.
         */
        DatabaseHelper dbHelper;
        private ProgressDialog dialog;
        private boolean mrloggedin;

        /**
         * Instantiates a new User connection task.
         *
         * @param loggedin the rpipaddress
         */
        UserConnectionTask(boolean loggedin) {
            dialog = new ProgressDialog(context);
            mrloggedin = loggedin;
            sharedPreference = new SharedPreference();
            confclass = new Configure_Class();
            insertdb = new InsertDB_Class();
            readwrite = new Read_Write_Class();
            dbHelper = new DatabaseHelper(context);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Starting Marine Applications, please wait.");
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {

            try {
                String publicid = "";
                try {
                    publicid = getPublicIP();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (readwrite.isFilePresent(context, "WifiInfo.json")) {
                    JSONObject wifiobject = null;
                    try {
                        JSONObject wobj = readwrite.getJSONData(context, "WifiInfo.json");
                        if (wobj != null) {
                            wifiobject = readwrite.getJSONData(context, "WifiInfo.json");
                        } else {
                            String jsonwifi = readwrite.AssetJSONFile("wifi_details.json", context);
                            wifiobject = new JSONObject(jsonwifi);
                        }
                        wifiobject.getJSONObject("wifi").put("public_ip", publicid);
                        readwrite.saveData(context, wifiobject.toString(), "WifiInfo.json");
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }

                String userloggedin = mrloggedin ? "yes" : "no";
                if (mrloggedin) {
                    if (confclass.isInternetconnected(context)) {
                        String current_user_id = sharedPreference.getValue(context, TAG_USER);
                        Log.d("JSON User", current_user_id);
                        String url_address = "https://login.marineapps.net/episensor_api.php";
                        HashMap<String, String> params = new HashMap<>();
                        params.put("system_request", "get_trip_details");
                        params.put("user_id", current_user_id);
                        JSONObject json = insertdb.insertPOSTRecordOnline(url_address, params);
                        if (json != null) {
                            Log.d("JSON FF", json.toString());
                            json.put("user_logged", userloggedin);
                            if (json.has("trip_started")) {
                                Log.d("JSON FD", "Found Trip started");
                                String tripstat = json.getString("trip_started");
                                Log.d("JSON Trip_stat", tripstat);
                                if (tripstat.equals("yes")) {
                                    if (!vessel_trip_id.equals("")) {
                                        int tripcount = dbHelper.getTripRecordByIDCount(vessel_trip_id);
                                        Log.d("JSON trip count", String.valueOf(tripcount));
                                        if (tripcount == 0) {
                                            String sdate = json.getString("start_date");
                                            int newtripid = insertdb.TripRecordInsertDB(json.getString("start_date"), current_user_id, json.getString("logsheet"), "C", "A", json.getString("trip_id"), context);
                                            sharedPreference.save(context, TAG_TRIP_STATUS, "Start");
                                            sharedPreference.save(context, TAG_TRIPID, Integer.toString(newtripid));
                                            sharedPreference.save(context, TAG_TRIP_LOG_NO, json.getString("logsheet"));
                                            sharedPreference.save(context, TAG_TRIP_START_DATE, json.getString("start_date_display"));
                                            sharedPreference.save(context, TAG_TRIP_ONLINE_ID, json.getString("trip_id"));

                                            if (readwrite.isFilePresent(context, "trip_Information.json")) {
                                                try {
                                                    JSONObject tripobj = readwrite.getJSONData(context, "trip_Information.json");

                                                    tripobj.getJSONObject("trip").put("trip_id", Integer.toString(newtripid));
                                                    tripobj.getJSONObject("trip").put("online_trip_id", json.getString("trip_id"));
                                                    tripobj.getJSONObject("trip").put("start_trip_date", json.getString("start_date"));
                                                    tripobj.getJSONObject("trip").put("end_trip_date", "");
                                                    tripobj.getJSONObject("trip").put("log_sheet_text", json.getString("logsheet"));
                                                    tripobj.getJSONObject("trip").put("current_status", "Start");
                                                    tripobj.getJSONObject("forms").put("whale_and_dolphin", json.getString(TAG_WHALE_DOLPHIN_RECORD));
                                                    tripobj.getJSONObject("forms").put("prawn_dip", json.getString(TAG_PRAWN_DIP_RECORD));
                                                    tripobj.getJSONObject("forms").put("slippage", json.getString(TAG_SLIPPAGE_RECORD));
                                                    tripobj.getJSONObject("forms").put("incident", json.getString(TAG_INCIDENT_RECORD));
                                                    tripobj.getJSONObject("forms").put("visitor", json.getString(TAG_VISITOR_RECORD));
                                                    tripobj.getJSONObject("forms").put("marine_litter", json.getString(TAG_MARINE_LITTER_RECORD));
                                                    tripobj.getJSONObject("forms").put("lost_gear", json.getString(TAG_LOST_GEAR_RECORD));
                                                    tripobj.getJSONObject("forms").put("communication", json.getString(TAG_COMMUNICATION_RECORD));
                                                    tripobj.getJSONObject("forms").put("dispatch_freezer", json.getString(TAG_DISPATCH_RECORD));

                                                    tripobj.getJSONObject("questions").put("cleaning_schedule", json.getString(TAG_CLEANING_SCHEDULE));
                                                    tripobj.getJSONObject("questions").put("waste_action", json.getString(TAG_WASTE_ACTION));
                                                    tripobj.getJSONObject("questions").put("prawn_dip_procedure", json.getString(TAG_PRAWN_DIP_PROCEDURE));
                                                    tripobj.getJSONObject("questions").put("calibration_of_scales", json.getString(TAG_CALIBRATION_OF_SCALES));
                                                    tripobj.getJSONObject("questions").put("target_species_fishing_procedure", json.getString(TAG_TARGET_SPECIES_FISHING_PROCEDURE));
                                                    tripobj.getJSONObject("questions").put("line_caught_procedure", json.getString(TAG_LINE_CAUGHT_PROCEDURE));
                                                    readwrite.saveData(context, tripobj.toString(), "trip_Information.json");
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    } else {
                                        String sdate = json.getString("start_date");
                                        int newtripid = insertdb.TripRecordInsertDB(json.getString("start_date"), current_user_id, json.getString("logsheet"), "C", "A", json.getString("trip_id"), context);
                                        sharedPreference.save(context, TAG_TRIP_STATUS, "Start");
                                        sharedPreference.save(context, TAG_TRIPID, Integer.toString(newtripid));
                                        sharedPreference.save(context, TAG_TRIP_LOG_NO, json.getString("logsheet"));
                                        sharedPreference.save(context, TAG_TRIP_START_DATE, json.getString("start_date_display"));
                                        sharedPreference.save(context, TAG_TRIP_ONLINE_ID, json.getString("trip_id"));

                                        if (readwrite.isFilePresent(context, "trip_Information.json")) {
                                            try {
                                                JSONObject tripobj = readwrite.getJSONData(context, "trip_Information.json");

                                                tripobj.getJSONObject("trip").put("trip_id", Integer.toString(newtripid));
                                                tripobj.getJSONObject("trip").put("online_trip_id", json.getString("trip_id"));
                                                tripobj.getJSONObject("trip").put("start_trip_date", json.getString("start_date"));
                                                tripobj.getJSONObject("trip").put("end_trip_date", "");
                                                tripobj.getJSONObject("trip").put("log_sheet_text", json.getString("logsheet"));
                                                tripobj.getJSONObject("trip").put("current_status", "Start");
                                                tripobj.getJSONObject("forms").put("whale_and_dolphin", json.getString(TAG_WHALE_DOLPHIN_RECORD));
                                                tripobj.getJSONObject("forms").put("prawn_dip", json.getString(TAG_PRAWN_DIP_RECORD));
                                                tripobj.getJSONObject("forms").put("slippage", json.getString(TAG_SLIPPAGE_RECORD));
                                                tripobj.getJSONObject("forms").put("incident", json.getString(TAG_INCIDENT_RECORD));
                                                tripobj.getJSONObject("forms").put("visitor", json.getString(TAG_VISITOR_RECORD));
                                                tripobj.getJSONObject("forms").put("marine_litter", json.getString(TAG_MARINE_LITTER_RECORD));
                                                tripobj.getJSONObject("forms").put("lost_gear", json.getString(TAG_LOST_GEAR_RECORD));
                                                tripobj.getJSONObject("forms").put("communication", json.getString(TAG_COMMUNICATION_RECORD));
                                                tripobj.getJSONObject("forms").put("dispatch_freezer", json.getString(TAG_DISPATCH_RECORD));

                                                tripobj.getJSONObject("questions").put("cleaning_schedule", json.getString(TAG_CLEANING_SCHEDULE));
                                                tripobj.getJSONObject("questions").put("waste_action", json.getString(TAG_WASTE_ACTION));
                                                tripobj.getJSONObject("questions").put("prawn_dip_procedure", json.getString(TAG_PRAWN_DIP_PROCEDURE));
                                                tripobj.getJSONObject("questions").put("calibration_of_scales", json.getString(TAG_CALIBRATION_OF_SCALES));
                                                tripobj.getJSONObject("questions").put("target_species_fishing_procedure", json.getString(TAG_TARGET_SPECIES_FISHING_PROCEDURE));
                                                tripobj.getJSONObject("questions").put("line_caught_procedure", json.getString(TAG_LINE_CAUGHT_PROCEDURE));
                                                readwrite.saveData(context, tripobj.toString(), "trip_Information.json");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                } else {
                                    if (!vessel_trip_id.equals("")) {
                                        int tripcount = dbHelper.getTripRecordByIDCount(vessel_trip_id);
                                        Log.d("JSON trip count", String.valueOf(tripcount));
                                        if (tripcount > 0) {
                                            int tempcount = dbHelper.getTemperatureTripRecordCount(vessel_trip_id);
                                            int whalecount = dbHelper.getWhaleDolphinTripRecordsCount(vessel_trip_id);
                                            int incidentcount = dbHelper.getIncidentTripRecordsCount(vessel_trip_id);
                                            int commcount = dbHelper.getCommunicationTripRecordsCount(vessel_trip_id);
                                            int marinecount = dbHelper.getMarineLitterTripRecordsCount(vessel_trip_id);
                                            int visitorcount = dbHelper.getVisitorTripRecordsCount(vessel_trip_id);
                                            int slippagecount = dbHelper.getSlippageTripRecordCount(vessel_trip_id);
                                            int dispatchcount = dbHelper.getDispatchTripRecordsCount(vessel_trip_id);
                                            int lostgearcount = dbHelper.getLostGearRTripRecordsCount(vessel_trip_id);
                                            int prawndipcount = dbHelper.getDPrawnDipRecordsByTripCount(vessel_trip_id);

                                            if (tempcount > 0) {
                                                dbHelper.deleteAllTripTemperatureRecords(vessel_trip_id);
                                            }
                                            if (whalecount > 0) {
                                                dbHelper.deleteAllTripWhaleDolphinRecords(vessel_trip_id);
                                            }

                                            if (incidentcount > 0) {
                                                dbHelper.deleteAllTripIncidentRecords(vessel_trip_id);
                                            }

                                            if (commcount > 0) {
                                                dbHelper.deleteAllTripCommunicationRecords(vessel_trip_id);
                                            }

                                            if (marinecount > 0) {
                                                dbHelper.deleteAllTripMarineRecords(vessel_trip_id);
                                            }

                                            if (visitorcount > 0) {
                                                dbHelper.deleteAllTripVisitorRecords(vessel_trip_id);
                                            }

                                            if (slippagecount > 0) {
                                                dbHelper.deleteAllTripSlippageRecords(vessel_trip_id);
                                            }

                                            if (dispatchcount > 0) {
                                                dbHelper.deleteAllTripDispatchRecords(vessel_trip_id);
                                            }

                                            if (lostgearcount > 0) {
                                                dbHelper.deleteAllTripLostGearRecords(vessel_trip_id);
                                            }

                                            if (prawndipcount > 0) {
                                                dbHelper.deleteAllTripPrawnDipRecords(vessel_trip_id);
                                            }

                                            if (tripcount > 0) {
                                                dbHelper.deleteTripbyID(vessel_trip_id);
                                            }
                                        }
                                    }

                                    if (readwrite.isFilePresent(context, "trip_Information.json")) {
                                        try {
                                            JSONObject tripobj = readwrite.getJSONData(context, "trip_Information.json");

                                            tripobj.getJSONObject("trip").put("trip_id", "");
                                            tripobj.getJSONObject("trip").put("online_trip_id", "");
                                            tripobj.getJSONObject("trip").put("start_trip_date", "");
                                            tripobj.getJSONObject("trip").put("end_trip_date", "");
                                            tripobj.getJSONObject("trip").put("log_sheet_text", "");
                                            tripobj.getJSONObject("trip").put("current_status", "Idle");

                                            readwrite.saveData(context, tripobj.toString(), "trip_Information.json");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    sharedPreference.save(context, TAG_TRIPID, "");
                                    sharedPreference.save(context, TAG_TRIP_STATUS, "Idle");
                                    sharedPreference.save(context, TAG_TRIP_LOG_NO, "");
                                    sharedPreference.save(context, TAG_TRIP_START_DATE, "");
                                    sharedPreference.save(context, TAG_TRIP_ONLINE_ID, "");
                                }
                            } else {
                                if (!vessel_trip_id.equals("")) {
                                    int tripcount = dbHelper.getTripRecordByIDCount(vessel_trip_id);
                                    Log.d("JSON trip count", String.valueOf(tripcount));
                                    if (tripcount > 0) {
                                        int tempcount = dbHelper.getTemperatureTripRecordCount(vessel_trip_id);
                                        int whalecount = dbHelper.getWhaleDolphinTripRecordsCount(vessel_trip_id);
                                        int incidentcount = dbHelper.getIncidentTripRecordsCount(vessel_trip_id);
                                        int commcount = dbHelper.getCommunicationTripRecordsCount(vessel_trip_id);
                                        int marinecount = dbHelper.getMarineLitterTripRecordsCount(vessel_trip_id);
                                        int visitorcount = dbHelper.getVisitorTripRecordsCount(vessel_trip_id);
                                        int slippagecount = dbHelper.getSlippageTripRecordCount(vessel_trip_id);
                                        int dispatchcount = dbHelper.getDispatchTripRecordsCount(vessel_trip_id);
                                        int lostgearcount = dbHelper.getLostGearRTripRecordsCount(vessel_trip_id);
                                        int prawndipcount = dbHelper.getDPrawnDipRecordsByTripCount(vessel_trip_id);

                                        if (tempcount > 0) {
                                            dbHelper.deleteAllTripTemperatureRecords(vessel_trip_id);
                                        }
                                        if (whalecount > 0) {
                                            dbHelper.deleteAllTripWhaleDolphinRecords(vessel_trip_id);
                                        }

                                        if (incidentcount > 0) {
                                            dbHelper.deleteAllTripIncidentRecords(vessel_trip_id);
                                        }

                                        if (commcount > 0) {
                                            dbHelper.deleteAllTripCommunicationRecords(vessel_trip_id);
                                        }

                                        if (marinecount > 0) {
                                            dbHelper.deleteAllTripMarineRecords(vessel_trip_id);
                                        }

                                        if (visitorcount > 0) {
                                            dbHelper.deleteAllTripVisitorRecords(vessel_trip_id);
                                        }

                                        if (slippagecount > 0) {
                                            dbHelper.deleteAllTripSlippageRecords(vessel_trip_id);
                                        }

                                        if (dispatchcount > 0) {
                                            dbHelper.deleteAllTripDispatchRecords(vessel_trip_id);
                                        }

                                        if (lostgearcount > 0) {
                                            dbHelper.deleteAllTripLostGearRecords(vessel_trip_id);
                                        }

                                        if (prawndipcount > 0) {
                                            dbHelper.deleteAllTripPrawnDipRecords(vessel_trip_id);
                                        }

                                        if (tripcount > 0) {
                                            dbHelper.deleteTripbyID(vessel_trip_id);
                                        }
                                    }
                                }

                                if (readwrite.isFilePresent(context, "trip_Information.json")) {
                                    try {
                                        JSONObject tripobj = readwrite.getJSONData(context, "trip_Information.json");

                                        tripobj.getJSONObject("trip").put("trip_id", "");
                                        tripobj.getJSONObject("trip").put("online_trip_id", "");
                                        tripobj.getJSONObject("trip").put("start_trip_date", "");
                                        tripobj.getJSONObject("trip").put("end_trip_date", "");
                                        tripobj.getJSONObject("trip").put("log_sheet_text", "");
                                        tripobj.getJSONObject("trip").put("current_status", "Idle");

                                        readwrite.saveData(context, tripobj.toString(), "trip_Information.json");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                sharedPreference.save(context, TAG_TRIPID, "");
                                sharedPreference.save(context, TAG_TRIP_STATUS, "Idle");
                                sharedPreference.save(context, TAG_TRIP_LOG_NO, "");
                                sharedPreference.save(context, TAG_TRIP_START_DATE, "");
                                sharedPreference.save(context, TAG_TRIP_ONLINE_ID, "");
                            }
                            return json;
                        }
                    } else {
                        JSONObject json = new JSONObject();
                        json.put("user_logged", userloggedin);
                        return json;
                    }
                } else {
                    JSONObject json = new JSONObject();
                    json.put("user_logged", userloggedin);
                    return json;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            mConnectTask = null;
            dialog.dismiss();
            String trip_cleaning_schedule = "";
            String trip_waste_action = "";
            String trip_etp = "";
            String trip_prawn_dip_record = "";
            String trip_slippage_record = "";
            String trip_incident_record = "";
            String trip_prawn_dip_procedure = "";
            String trip_calibration_of_scales = "";
            String trip_target_species_fishing_procedure = "";
            String trip_line_caught_procedure = "";
            String trip_visitor_record = "";
            String trip_marine_litter_record = "";
            String trip_communication_record = "";
            String trip_dispatch_freezer_record = "";
            String trip_lost_gear_record = "";
            if (json != null) {
                String userloggedin = null;
                try {
                    userloggedin = json.getString("user_logged");
                    if (userloggedin.equals("yes")) {
                        if (json.has("trip_started")) {
                            String tripstat = json.getString("trip_started");
                            if (tripstat.equals("yes")) {
                                trip_cleaning_schedule = json.getString(TAG_CLEANING_SCHEDULE);
                                trip_waste_action = json.getString(TAG_WASTE_ACTION);
                                trip_etp = json.getString(TAG_WHALE_DOLPHIN_RECORD);
                                trip_prawn_dip_record = json.getString(TAG_PRAWN_DIP_RECORD);
                                trip_slippage_record = json.getString(TAG_SLIPPAGE_RECORD);
                                trip_incident_record = json.getString(TAG_INCIDENT_RECORD);
                                trip_prawn_dip_procedure = json.getString(TAG_PRAWN_DIP_PROCEDURE);
                                trip_calibration_of_scales = json.getString(TAG_CALIBRATION_OF_SCALES);
                                trip_target_species_fishing_procedure = json.getString(TAG_TARGET_SPECIES_FISHING_PROCEDURE);
                                trip_line_caught_procedure = json.getString(TAG_LINE_CAUGHT_PROCEDURE);
                                trip_visitor_record = json.getString(TAG_VISITOR_RECORD);
                                trip_marine_litter_record = json.getString(TAG_MARINE_LITTER_RECORD);
                                trip_lost_gear_record = json.getString(TAG_LOST_GEAR_RECORD);
                                trip_communication_record = json.getString(TAG_COMMUNICATION_RECORD);
                                trip_dispatch_freezer_record = json.getString(TAG_DISPATCH_RECORD);
                                String trip_id = json.getString("trip_id");
                                sharedPreference.save(context, TAG_CLEANING_SCHEDULE, trip_cleaning_schedule);
                                sharedPreference.save(context, TAG_WASTE_ACTION, trip_waste_action);
                                sharedPreference.save(context, TAG_WHALE_DOLPHIN_RECORD, trip_etp);
                                sharedPreference.save(context, TAG_SLIPPAGE_RECORD, trip_slippage_record);
                                sharedPreference.save(context, TAG_INCIDENT_RECORD, trip_incident_record);
                                sharedPreference.save(context, TAG_PRAWN_DIP_RECORD, trip_prawn_dip_record);
                                sharedPreference.save(context, TAG_PRAWN_DIP_PROCEDURE, trip_prawn_dip_procedure);
                                sharedPreference.save(context, TAG_CALIBRATION_OF_SCALES, trip_calibration_of_scales);
                                sharedPreference.save(context, TAG_TARGET_SPECIES_FISHING_PROCEDURE, trip_target_species_fishing_procedure);
                                sharedPreference.save(context, TAG_LINE_CAUGHT_PROCEDURE, trip_line_caught_procedure);
                                sharedPreference.save(context, TAG_VISITOR_RECORD, trip_visitor_record);
                                sharedPreference.save(context, TAG_MARINE_LITTER_RECORD, trip_marine_litter_record);
                                sharedPreference.save(context, TAG_LOST_GEAR_RECORD, trip_lost_gear_record);
                                sharedPreference.save(context, TAG_COMMUNICATION_RECORD, trip_communication_record);
                                sharedPreference.save(context, TAG_DISPATCH_RECORD, trip_dispatch_freezer_record);
                            }
                        }
                        Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Failure to get details from server", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mConnectTask = null;
        }
    }
}																																					