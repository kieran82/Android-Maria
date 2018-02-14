package marineapplications.marineapplicationwifi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG_LOGIN_STATUS = "logged_status";
    private static final String TAG_VESSEL = "vessel_name";
    private static final String TAG_TRIPID = "trip_id";
    private static final String TAG_TRIP_START_DATE = "trip_start_date";
    private static final String TAG_TRIP_LOG_NO = "trip_log_sheet_no";
    private static final String TAG_TRIP_STATUS = "trip_status";
    private static final String TAG_TRIP_ONLINE_ID = "online_trip_id";
    /**
     * The Vessel trip id.
     */
    String vessel_trip_id = "";
    /**
     * The Shared preference.
     */
    SharedPreference sharedPreference;
    /**
     * The Confclass.
     */
    Configure_Class confclass;
    /**
     * The Context.
     */
    Activity context = this;
    /**
     * The Readwrite.
     */
    Read_Write_Class readwrite;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    // UI references.
    private EditText mUsernamelView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.temptoolbar);
        toolbar.setTitle(R.string.logintext);
        setSupportActionBar(toolbar);

        sharedPreference = new SharedPreference();
        confclass = new Configure_Class();
        readwrite = new Read_Write_Class();
        if (sharedPreference.getValue(context, TAG_TRIPID) != null) {
            vessel_trip_id = sharedPreference.getValue(context, TAG_TRIPID);
        } else {
            vessel_trip_id = "";
        }
        // Set up the login form
        mUsernamelView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        CheckLoginTask mlogincheck = new CheckLoginTask();
        mlogincheck.execute();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernamelView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernamelView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(username)) {
            mUsernamelView.setError(getString(R.string.error_field_required));
            focusView = mUsernamelView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            if (confclass.isConnectedToInternet()) {
                mAuthTask = new UserLoginTask(username, password);
                mAuthTask.execute();
            } else {
                Toast.makeText(getApplicationContext(), "You must have internet access to continue", Toast.LENGTH_LONG).show();
            }

        }
    }

    /**
     * Is password valid boolean.
     *
     * @param password the password
     * @return the boolean
     */
    public boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Get wifi ssid string.
     *
     * @return the string
     */
    public String GetWifiSSID() {
        Read_Write_Class readwrite;
        readwrite = new Read_Write_Class();
        JSONObject obj = readwrite.getJSONData(context, "WifiInfo.json");
        String ssid = "";
        try {
            ssid = obj.getJSONObject("wifi").getString("ssid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ssid;
    }

    /**
     * Get wifi pw string.
     *
     * @return the string
     */
    public String GetWifiPW() {
        Read_Write_Class readwrite;
        readwrite = new Read_Write_Class();
        JSONObject obj = readwrite.getJSONData(context, "WifiInfo.json");
        String pw = "";
        try {
            pw = obj.getJSONObject("wifi").getString("pw");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pw;
    }

    /**
     * Get wifi pip string.
     *
     * @return the string
     */
    public String GetWifiPIP() {
        Read_Write_Class readwrite;
        readwrite = new Read_Write_Class();
        JSONObject obj = readwrite.getJSONData(context, "WifiInfo.json");
        String pw = "";
        try {
            pw = obj.getJSONObject("wifi").getString("public_ip");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pw;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class CheckLoginTask extends AsyncTask<Void, Void, Boolean> {
        private static final String TAG_USER = "user_id";

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
         * The Shared preference.
         */
        SharedPreference sharedPreference;

        /**
         * Instantiates a new Check login task.
         */
        CheckLoginTask() {
            insertdb = new InsertDB_Class();
            confclass = new Configure_Class();
            readwrite = new Read_Write_Class();
            sharedPreference = new SharedPreference();
        }

        @Override
        protected Boolean doInBackground(Void... args) {
            String attributeuserid = "";
            String attributevessel = "";
            boolean logged = false;
            boolean fileusernotempty = false;

            if (readwrite.isFilePresent(context, "UserInfo.json")) {
                try {
                    JSONObject obj = readwrite.getJSONData(context, "UserInfo.json");
                    if (obj != null) {
                        attributeuserid = obj.getJSONObject("vessel").getString("User_id");
                        attributevessel = obj.getJSONObject("vessel").getString("name");

                        fileusernotempty = !attributeuserid.equals("");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (sharedPreference.getValue(context, TAG_USER) == null) {
                logged = false;
            }
            if (fileusernotempty) {
                sharedPreference.save(context, TAG_USER, attributeuserid);
                sharedPreference.save(context, TAG_VESSEL, attributevessel);
                sharedPreference.save(context, TAG_LOGIN_STATUS, "logged in");
            }

            return logged;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        }

        @Override
        protected void onCancelled() {
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, JSONObject> {
        private static final String LOGIN_URL = "https://login.marineapps.net/episensor_api.php";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";
        private static final String TAG_USERNAME = "username";
        private static final String TAG_VESSEL = "vessel_name";
        private static final String TAG_USER = "user_id";
        private static final String TAG_PASS = "password";
        private static final String TAG_LOGIN_STATUS = "logged_status";
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
        private final String mUsername;
        private final String mPassword;
        /**
         * The Json parser.
         */
        JSONParser jsonParser = new JSONParser();
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
         * The Db helper.
         */
        DatabaseHelper dbHelper;

        /**
         * Instantiates a new User login task.
         *
         * @param username the username
         * @param password the password
         */
        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
            insertdb = new InsertDB_Class();
            confclass = new Configure_Class();
            readwrite = new Read_Write_Class();
            dbHelper = new DatabaseHelper(context);
        }

        @Override
        protected JSONObject doInBackground(Void... args) {
            try {
                String ssidstr = GetWifiSSID();
                String wifipw = GetWifiPW();
                String wifi_public_ip = GetWifiPIP();
                String wifissid = confclass.getWiFiSSID(context);
                boolean checkssid = wifissid.equals(ssidstr);

                String device = android.os.Build.DEVICE;
                String model = android.os.Build.MODEL;
                String device_model = device + " " + model;
                HashMap<String, String> params = new HashMap<>();
                params.put("system_request", "login_form");
                params.put("username", mUsername);
                params.put("password", mPassword);
                params.put("device_model", device_model);
                params.put("public_ip", wifi_public_ip);
                params.put("device_type", "Android");

                Log.d("request", "starting");

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
                //JSONObject json = insertdb.insertPOSTRecordOnline(LOGIN_URL, params);

                if (json != null) {
                    Log.d("JSON result", json.toString());
                    json.put("saved_ssid", ssidstr);
                    json.put("current_ssid", wifissid);
                    json.put("saved_pw", wifipw);
                    json.put("ssid_match", checkssid);
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("login_status", "yes");

                        if (!readwrite.isFilePresent(context, "UserInfo.json")) {
                            try {
                                String jsonuser = readwrite.AssetJSONFile("user_details.json", context);
                                String jvesselresult = jsonuser.replaceAll("\\s+", "");
                                JSONObject vobj = new JSONObject(jvesselresult);
                                vobj.getJSONObject("vessel").put("User_id", json.getString(TAG_USER));
                                vobj.getJSONObject("vessel").put("name", json.getString(TAG_VESSEL));
                                vobj.getJSONObject("vessel").put("username", json.getString(TAG_USERNAME));
                                vobj.getJSONObject("vessel").put("password", json.getString(TAG_PASS));
                                readwrite.saveData(context, vobj.toString(), "UserInfo.json");
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        } else {

                            try {
                                JSONObject Uobj = readwrite.getJSONData(context, "UserInfo.json");
                                Log.d("JSON Main found", Uobj.toString());
                                Uobj.getJSONObject("vessel").put("User_id", json.getString(TAG_USER));
                                Uobj.getJSONObject("vessel").put("name", json.getString(TAG_VESSEL));
                                Uobj.getJSONObject("vessel").put("username", json.getString(TAG_USERNAME));
                                Uobj.getJSONObject("vessel").put("password", json.getString(TAG_PASS));
                                readwrite.saveData(context, Uobj.toString(), "UserInfo.json");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        String current_user_id = json.getString(TAG_USER);
                        if (readwrite.isFilePresent(context, "trip_Information.json")) {
                            try {
                                JSONObject tripobj = readwrite.getJSONData(context, "trip_Information.json");
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

                                            tripobj.getJSONObject("trip").put("trip_id", Integer.toString(newtripid));
                                            tripobj.getJSONObject("trip").put("online_trip_id", json.getString("trip_id"));
                                            tripobj.getJSONObject("trip").put("start_trip_date", json.getString("start_date"));
                                            tripobj.getJSONObject("trip").put("end_trip_date", "");
                                            tripobj.getJSONObject("trip").put("log_sheet_text", json.getString("logsheet"));
                                            tripobj.getJSONObject("trip").put("current_status", "Start");
                                        }
                                    } else {
                                        String sdate = json.getString("start_date");
                                        int newtripid = insertdb.TripRecordInsertDB(json.getString("start_date"), current_user_id, json.getString("logsheet"), "C", "A", json.getString("trip_id"), context);
                                        sharedPreference.save(context, TAG_TRIP_STATUS, "Start");
                                        sharedPreference.save(context, TAG_TRIPID, Integer.toString(newtripid));
                                        sharedPreference.save(context, TAG_TRIP_LOG_NO, json.getString("logsheet"));
                                        sharedPreference.save(context, TAG_TRIP_START_DATE, json.getString("start_date_display"));
                                        sharedPreference.save(context, TAG_TRIP_ONLINE_ID, json.getString("trip_id"));

                                        tripobj.getJSONObject("trip").put("trip_id", Integer.toString(newtripid));
                                        tripobj.getJSONObject("trip").put("online_trip_id", json.getString("trip_id"));
                                        tripobj.getJSONObject("trip").put("start_trip_date", json.getString("start_date"));
                                        tripobj.getJSONObject("trip").put("end_trip_date", "");
                                        tripobj.getJSONObject("trip").put("log_sheet_text", json.getString("logsheet"));
                                        tripobj.getJSONObject("trip").put("current_status", "Start");
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

                                    tripobj.getJSONObject("trip").put("trip_id", "");
                                    tripobj.getJSONObject("trip").put("online_trip_id", "");
                                    tripobj.getJSONObject("trip").put("start_trip_date", "");
                                    tripobj.getJSONObject("trip").put("end_trip_date", "");
                                    tripobj.getJSONObject("trip").put("log_sheet_text", "");
                                    tripobj.getJSONObject("trip").put("current_status", "Idle");

                                    sharedPreference.save(context, TAG_TRIPID, "");
                                    sharedPreference.save(context, TAG_TRIP_STATUS, "Idle");
                                    sharedPreference.save(context, TAG_TRIP_LOG_NO, "");
                                    sharedPreference.save(context, TAG_TRIP_START_DATE, "");
                                    sharedPreference.save(context, TAG_TRIP_ONLINE_ID, "");
                                }

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
                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            mAuthTask = null;
            showProgress(false);
            Log.d("JSON rfunny esult", json.toString());
            int success = 0;
            String message = "";
            String vessel = "";
            String userid = "";
            String user_username = "";
            String user_password = "";
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
            String saved_ssid = "";
            String current_ssid = "";
            String saved_pw = "";
            String ssid_match = "";

            try {
                saved_ssid = json.getString("saved_ssid");
                saved_pw = json.getString("saved_pw");
                current_ssid = json.getString("current_ssid");
                ssid_match = json.getString("ssid_match");
                success = json.getInt(TAG_SUCCESS);
                message = json.getString(TAG_MESSAGE);

                if (success == 1) {
                    String userloggedin = "yes";
                    vessel = json.getString(TAG_VESSEL);
                    userid = json.getString(TAG_USER);
                    user_username = json.getString(TAG_USERNAME);
                    user_password = json.getString(TAG_PASS);
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

                    sharedPreference.save(context, TAG_USER, userid);
                    sharedPreference.save(context, TAG_VESSEL, vessel);
                    sharedPreference.save(context, TAG_LOGIN_STATUS, "logged in");
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
                    Log.d("Success!", message);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("Failure", message);
                    Toast.makeText(getApplicationContext(),
                            message, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}					