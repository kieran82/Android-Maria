package marineapplications.marineapplicationwifi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * The type End trip questions activity.
 */
public class EndTripQuestionsActivity extends AppCompatActivity {
    private static final String TAG_CLEANING_SCHEDULE = "trip_cleaning_schedule";
    private static final String TAG_WASTE_ACTION = "trip_waste_action";
    private static final String TAG_PRAWN_DIP_PROCEDURE = "trip_prawn_dip_procedure";
    private static final String TAG_CALIBRATION_OF_SCALES = "trip_calibration_of_scales";
    private static final String TAG_TARGET_SPECIES_FISHING_PROCEDURE = "trip_target_species_fishing_procedure";
    private static final String TAG_LINE_CAUGHT_PROCEDURE = "trip_line_caught_procedure";
    private static final String TAG_TRIP_START_DATE = "trip_start_date";
    private static final String TAG_USER = "user_id";
    private static final String TAG_TRIP_STATUS = "trip_status";
    private static final String TAG_TRIP_LOG_NO = "trip_log_sheet_no";
    private static final String TAG_TRIPID = "trip_id";
    private static final String TAG_TRIP_ONLINE_ID = "online_trip_id";
    /**
     * The Cleaninglabel.
     */
    TextView cleaninglabel;
    /**
     * The Cleaningschedulespinner.
     */
    Spinner cleaningschedulespinner;
    /**
     * The Wasteactionlabel.
     */
    TextView wasteactionlabel;
    /**
     * The Wasteactionspinner.
     */
    Spinner wasteactionspinner;
    /**
     * The Prawnlabel.
     */
    TextView prawnlabel;
    /**
     * The Prawndipspinner.
     */
    Spinner prawndipspinner;
    /**
     * The Calibratelabel.
     */
    TextView calibratelabel;
    /**
     * The Calibrateofscalesspinner.
     */
    Spinner calibrateofscalesspinner;
    /**
     * The Targetproclabel.
     */
    TextView targetproclabel;
    /**
     * The Targetspeciesspinner.
     */
    Spinner targetspeciesspinner;
    /**
     * The Linecaughtproclabel.
     */
    TextView linecaughtproclabel;
    /**
     * The Linecaughtspinner.
     */
    Spinner linecaughtspinner;
    /**
     * The Addformdata.
     */
    Button addformdata;
    /**
     * The Shared preference.
     */
    SharedPreference sharedPreference;
    /**
     * The Db helper.
     */
    DatabaseHelper dbHelper;
    /**
     * The Confclass.
     */
    Configure_Class confclass;
    /**
     * The Insertdb.
     */
    InsertDB_Class insertdb;
    /**
     * The User id.
     */
    String user_id = "";
    /**
     * The Vessel trip id.
     */
    String vessel_trip_id = "";
    /**
     * The Context.
     */
    Activity context = this;
    /**
     * The Readwrite.
     */
    Read_Write_Class readwrite;
    /**
     * The M rockblock trip report task.
     */
    /**
     * The M wi fi trip upload task.
     */
//End Trip Online Remote Server Task
    WiFiTripUploadTask mWiFiTripUploadTask = null;
    /**
     * The Wificheck.
     */
    boolean wificheck = false;
    /**
     * The Trip cleaning schedule.
     */
    String trip_cleaning_schedule = "";
    /**
     * The Trip waste action.
     */
    String trip_waste_action = "";
    /**
     * The Trip prawn dip procedure.
     */
    String trip_prawn_dip_procedure = "";
    /**
     * The Trip calibration of scales.
     */
    String trip_calibration_of_scales = "";
    /**
     * The Trip target species fishing procedure.
     */
    String trip_target_species_fishing_procedure = "";
    /**
     * The Trip line caught procedure.
     */
    String trip_line_caught_procedure = "";
    /**
     * The Connectionstatus.
     */
    String connectionstatus = null;
    /**
     * The Vessel start date.
     */
    private View mProgressView;
    private View mTempFormView;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_trip_questions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.temptoolbar);
        toolbar.setTitle(R.string.endtripquestions);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndTripQuestionsActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        readwrite = new Read_Write_Class();
        sharedPreference = new SharedPreference();
        trip_cleaning_schedule = sharedPreference.getValue(context, TAG_CLEANING_SCHEDULE);
        trip_waste_action = sharedPreference.getValue(context, TAG_WASTE_ACTION);
        trip_prawn_dip_procedure = sharedPreference.getValue(context, TAG_PRAWN_DIP_PROCEDURE);
        trip_calibration_of_scales = sharedPreference.getValue(context, TAG_CALIBRATION_OF_SCALES);
        trip_target_species_fishing_procedure = sharedPreference.getValue(context, TAG_TARGET_SPECIES_FISHING_PROCEDURE);
        trip_line_caught_procedure = sharedPreference.getValue(context, TAG_LINE_CAUGHT_PROCEDURE);
        Log.d("JSON waste", trip_waste_action != null ? "waste found" : "No waste here");
        Log.d("JSON prawn", trip_prawn_dip_procedure != null ? "prawn found" : "No prawn here");
        Log.d("JSON scales", trip_calibration_of_scales != null ? "scales found" : "No scales here");
        vessel_trip_id = sharedPreference.getValue(context, TAG_TRIPID);
        user_id = sharedPreference.getValue(context, TAG_USER);
        cleaninglabel = (TextView) findViewById(R.id.cleaninglabel);
        cleaningschedulespinner = (Spinner) findViewById(R.id.cleaningschedulespinner);
        wasteactionlabel = (TextView) findViewById(R.id.wasteactionlabel);
        wasteactionspinner = (Spinner) findViewById(R.id.wasteactionspinner);
        prawnlabel = (TextView) findViewById(R.id.prawnlabel);
        prawndipspinner = (Spinner) findViewById(R.id.prawndipspinner);
        calibratelabel = (TextView) findViewById(R.id.calibratelabel);
        calibrateofscalesspinner = (Spinner) findViewById(R.id.calibrateofscalesspinner);
        targetproclabel = (TextView) findViewById(R.id.targetproclabel);
        targetspeciesspinner = (Spinner) findViewById(R.id.targetspeciesspinner);
        linecaughtproclabel = (TextView) findViewById(R.id.linecaughtproclabel);
        linecaughtspinner = (Spinner) findViewById(R.id.linecaughtspinner);
        confclass = new Configure_Class();
        insertdb = new InsertDB_Class();
        dbHelper = new DatabaseHelper(this);
        wificheck = confclass.checkWifiConnected(context);
        Log.d("JSON Wifi connected ", String.valueOf(wificheck));
        addformdata = (Button) findViewById(R.id.addformdatabutton);
        mTempFormView = findViewById(R.id.temp_form);
        mProgressView = findViewById(R.id.login_progress);

        addformdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wificheck) {
                    Log.d("JSON Wifi accepted ", "yes");
                    String cleaningscheduleSpinner = "";
                    String wasteactionSpinner = "";
                    String prawndipSpinner = "";
                    String calibrateofscalesSpinner = "";
                    String targetspeciesSpinner = "";
                    String lineprocedureSpinner = "";
                    cleaningscheduleSpinner = trip_cleaning_schedule.equals("yes") ? cleaningschedulespinner.getSelectedItem().toString() : "empty";
                    Log.d("JSON cleaning settings", cleaningscheduleSpinner);
                    wasteactionSpinner = trip_waste_action.equals("yes") ? wasteactionspinner.getSelectedItem().toString() : "empty";
                    Log.d("JSON waste settings", wasteactionSpinner);
                    prawndipSpinner = trip_prawn_dip_procedure.equals("yes") ? prawndipspinner.getSelectedItem().toString() : "empty";
                    Log.d("JSON prawn settings", prawndipSpinner);
                    calibrateofscalesSpinner = trip_calibration_of_scales.equals("yes") ? calibrateofscalesspinner.getSelectedItem().toString() : "empty";
                    Log.d("JSON scales settings", calibrateofscalesSpinner);
                    targetspeciesSpinner = trip_target_species_fishing_procedure.equals("yes") ? targetspeciesspinner.getSelectedItem().toString() : "empty";
                    Log.d("JSON target settings", targetspeciesSpinner);
                    lineprocedureSpinner = trip_line_caught_procedure.equals("yes") ? linecaughtspinner.getSelectedItem().toString() : "empty";
                    Log.d("JSON line settings", lineprocedureSpinner);

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
                    String endstrDate = sdf.format(c.getTime());
                    Log.d("JSON tripid settings", vessel_trip_id);
                    Log.d("JSON enddate settings", endstrDate);
                    boolean updatecurrenttrip = doInsertTripRecord(vessel_trip_id, endstrDate, cleaningscheduleSpinner, prawndipSpinner, calibrateofscalesSpinner, targetspeciesSpinner, lineprocedureSpinner, wasteactionSpinner);
                    //boolean updatecurrenttrip = insertdb.TripRecordUpdateDB(context, vessel_trip_id, endstrDate, cleaningscheduleSpinner, prawndipSpinner, calibrateofscalesSpinner, targetspeciesSpinner, lineprocedureSpinner, wasteactionSpinner);
                    int newtripcount = dbHelper.getTripRecordByIDCount(String.valueOf(vessel_trip_id));
                    Log.d("JSON trip count stat", String.valueOf(newtripcount));
                    Log.d("JSON updating stat", String.valueOf(updatecurrenttrip));
                    if (updatecurrenttrip) {
                        Toast.makeText(getApplicationContext(),
                                "trip has been updated", Toast.LENGTH_LONG).show();
                        if (readwrite.isFilePresent(context, "trip_Information.json")) {
                            String results = readwrite.getData(context, "trip_Information.json");
                            try {
                                JSONObject obj = new JSONObject(results);
                                obj.getJSONObject("trip").put("end_trip_date", endstrDate);
                                obj.getJSONObject("trip").put("current_status", "Idle");
                                readwrite.saveData(context, obj.toString(), "trip_Information.json");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (confclass.isInternetconnected(context)) {
                            mWiFiTripUploadTask = new WiFiTripUploadTask(vessel_trip_id, user_id);
                            mWiFiTripUploadTask.execute();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "There is no internet available to end the Trip", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EndTripQuestionsActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "There is no Wi-Fi Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (!trip_cleaning_schedule.equals("yes")) {
            cleaninglabel.setVisibility(View.GONE);
            cleaningschedulespinner.setVisibility(View.GONE);
        }

        // UserTripCheckTask mTripCheckTask = new UserTripCheckTask();
        // mTripCheckTask.execute();

        if (!trip_waste_action.equals("yes")) {
            wasteactionlabel.setVisibility(View.GONE);
            wasteactionspinner.setVisibility(View.GONE);
        }

        if (!trip_prawn_dip_procedure.equals("yes")) {
            prawnlabel.setVisibility(View.GONE);
            prawndipspinner.setVisibility(View.GONE);
        }

        if (!trip_calibration_of_scales.equals("yes")) {
            calibratelabel.setVisibility(View.GONE);
            calibrateofscalesspinner.setVisibility(View.GONE);
        }

        if (!trip_target_species_fishing_procedure.equals("yes")) {
            targetproclabel.setVisibility(View.GONE);
            targetspeciesspinner.setVisibility(View.GONE);
        }

        if (!trip_line_caught_procedure.equals("yes")) {
            linecaughtproclabel.setVisibility(View.GONE);
            linecaughtspinner.setVisibility(View.GONE);
        }
    }

    /**
     * Quit.
     */
    public void quit() {
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(0);
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder
                .setMessage("Do yo want to start a new trip?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(EndTripQuestionsActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                        moveTaskToBack(true);
                    }
                })
                .show();
    }

    private Boolean doInsertTripRecord(String tripid, String endtrip, String cleaningschedule, String prawndip, String scaletest, String targetproc, String lineproc, String wasteAshore) {

        Trip_Class record;
        DatabaseHelper dbHelper;
        Boolean result = false;

        try {
            dbHelper = new DatabaseHelper(context);
            int tripcount = dbHelper.getTripRecordByIDCount(tripid);
            Log.d("JSON trip count", String.valueOf(tripcount));
            record = new Trip_Class();
            record.setId(Integer.parseInt(tripid));
            record.set_end_date(endtrip);
            record.set_cleaning_schedule_completed(cleaningschedule);
            record.set_prawn_dip(prawndip);
            record.set_scales_test(scaletest);
            record.set_target_procedure(targetproc);
            record.set_line_caught_procedure(lineproc);
            record.set_waste_ashore(wasteAshore);
            result = dbHelper.updateTripRecord(record);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Show snackbox.
     *
     * @param message the message
     */
    public void showSnackbox(String message) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onBackPressed() {

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mTempFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mTempFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mTempFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
    }

    /**
     * The type User trip check task.
     */
    public class UserTripCheckTask extends AsyncTask<Void, Void, JSONObject> {

        /**
         * The Insertdb.
         */
        InsertDB_Class insertdb;
        /**
         * The Confclass.
         */
        Configure_Class confclass;

        /**
         * The Read write.
         */
        Read_Write_Class readWrite;

        /**
         * Instantiates a new User trip check task.
         */
        public UserTripCheckTask() {
            insertdb = new InsertDB_Class();
            confclass = new Configure_Class();
            readWrite = new Read_Write_Class();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {
            String trip_status = "";
            if (readwrite.isFilePresent(context, "trip_Information.json")) {
                String results = readwrite.getData(context, "trip_Information.json");
                JSONObject obj = null;
                try {
                    obj = new JSONObject(results);
                    trip_status = obj.getJSONObject("trip").getString("current_status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.d("JSON c trip status", trip_status);
            boolean wificheck = confclass.checkWifiConnected(context);
            if (!wificheck) {
                JSONObject json = new JSONObject();
                try {
                    json.put("success", 0);
                    json.put("error", "wifi");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return json;
            } else if (confclass.isInternetconnected(context)) {
                String current_user_id = sharedPreference.getValue(context, TAG_USER);
                Log.d("JSON User", current_user_id);
                String url_address = "https://login.marineapps.net/episensor_api.php";
                HashMap<String, String> params = new HashMap<>();
                params.put("system_request", "get_trip_details");
                params.put("user_id", current_user_id);

                JSONObject json = insertdb.insertPOSTRecordOnline(url_address, params);
                if (json != null) {
                    Log.d("JSON checked", String.valueOf(json));
                    return json;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            showProgress(false);
            if (json != null) {
                try {
                    String tstatus = json.getString("trip_started");
                    if (tstatus.equals("no")) {
                        sharedPreference.save(context, TAG_TRIPID, "");
                        sharedPreference.save(context, TAG_TRIP_STATUS, "Idle");
                        sharedPreference.save(context, TAG_TRIP_LOG_NO, "");
                        sharedPreference.save(context, TAG_TRIP_START_DATE, "");
                        Toast.makeText(getApplicationContext(),
                                "There is a problem, the trip has already ended", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(EndTripQuestionsActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Failure to connect to raspberry pi", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }
    }

    /**
     * The type Wi fi trip upload task.
     */
    public class WiFiTripUploadTask extends AsyncTask<Void, Void, JSONObject> {
        private static final String TAG_SUCCESS = "success";
        private final String mtripid;
        private final String muserid;

        /**
         * The Db helper.
         */
        DatabaseHelper dbHelper;
        /**
         * The Errorlist.
         */
        JSONArray errorlist = new JSONArray();
        /**
         * The Templist.
         */
        JSONArray templist = new JSONArray();
        /**
         * The Whalelist.
         */
        JSONArray whalelist = new JSONArray();
        /**
         * The Incidentlist.
         */
        JSONArray incidentlist = new JSONArray();
        /**
         * The Communicationlist.
         */
        JSONArray communicationlist = new JSONArray();
        /**
         * The Marinelist.
         */
        JSONArray marinelist = new JSONArray();
        /**
         * The Visitorlist.
         */
        JSONArray visitorlist = new JSONArray();
        /**
         * The Slippagelist.
         */
        JSONArray slippagelist = new JSONArray();
        /**
         * The Dispatchlist.
         */
        JSONArray dispatchlist = new JSONArray();
        /**
         * The Lostgearlist.
         */
        JSONArray lostgearlist = new JSONArray();
        /**
         * The Prawndiplist.
         */
        JSONArray prawndiplist = new JSONArray();
        /**
         * The Triplist.
         */
        JSONArray triplist = new JSONArray();
        /**
         * The Tripobj.
         */
        JSONObject tripobj = new JSONObject();
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
        private ProgressDialog dialog;

        /**
         * Instantiates a new Wi fi trip upload task.
         *
         * @param tripid the tripid
         * @param userid the userid
         */
        WiFiTripUploadTask(String tripid, String userid) {
            mtripid = tripid;
            muserid = userid;
            dialog = new ProgressDialog(context);
            sharedPreference = new SharedPreference();
            dbHelper = new DatabaseHelper(context);
            insertdb = new InsertDB_Class();
            confclass = new Configure_Class();
            readwrite = new Read_Write_Class();
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Ending the trip and Syncing with Remote Server, please wait.");
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {
            int errorcount = dbHelper.getUserErrorRecordCount(muserid);
            int tempcount = dbHelper.getTemperatureTripRecordCount(mtripid);
            int whalecount = dbHelper.getWhaleDolphinTripRecordsCount(mtripid);
            int incidentcount = dbHelper.getIncidentTripRecordsCount(mtripid);
            int commcount = dbHelper.getCommunicationTripRecordsCount(mtripid);
            int marinecount = dbHelper.getMarineLitterTripRecordsCount(mtripid);
            int visitorcount = dbHelper.getVisitorTripRecordsCount(mtripid);
            int slippagecount = dbHelper.getSlippageTripRecordCount(mtripid);
            int dispatchcount = dbHelper.getDispatchTripRecordsCount(mtripid);
            int lostgearcount = dbHelper.getLostGearRTripRecordsCount(mtripid);
            int prawndipcount = dbHelper.getDPrawnDipRecordsByTripCount(mtripid);
            int tripcount = dbHelper.getTripRecordByIDCount(mtripid);

            if (tempcount > 0) {
                List<Temperature_Class> temp = dbHelper.getAllCurrentTripTemperatureRecords(mtripid);
                for (int i = 0; i < temp.size(); i++) {
                    templist.put(temp.get(i).getJSONObject());
                }
                try {
                    tripobj.put("temperature_record", templist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (errorcount > 0) {
                List<Error_Log_Class> errorclass = dbHelper.getAllErrorRecords();
                for (int i = 0; i < errorclass.size(); i++) {
                    errorlist.put(errorclass.get(i).getJSONObject());
                }
                try {
                    tripobj.put("error_record", errorlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (whalecount > 0) {
                List<WhaleDolphin_Class> wrecord = dbHelper.getAllCurrentTripWhaleDolphinRecords(mtripid);
                for (int i = 0; i < wrecord.size(); i++) {
                    whalelist.put(wrecord.get(i).getJSONObject());
                }
                try {
                    tripobj.put("whale_dolphin_record", whalelist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (incidentcount > 0) {
                List<Incident_Class> irecord = dbHelper.getAllCurrentTripIncidentRecords(mtripid);
                for (int i = 0; i < irecord.size(); i++) {
                    incidentlist.put(irecord.get(i).getJSONObject());
                }
                try {
                    tripobj.put("incident_record", incidentlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (commcount > 0) {
                List<Communication_Class> crecord = dbHelper.getAllCurrentTripCommunicationRecords(mtripid);
                for (int i = 0; i < crecord.size(); i++) {
                    communicationlist.put(crecord.get(i).getJSONObject());
                }
                try {
                    tripobj.put("communication_record", communicationlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (marinecount > 0) {
                List<Marine_Litter_Class> mrecord = dbHelper.getAllCurrentTripMarineRecords(mtripid);
                for (int i = 0; i < mrecord.size(); i++) {
                    marinelist.put(mrecord.get(i).getJSONObject());
                }
                try {
                    tripobj.put("marine_litter_record", marinelist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (visitorcount > 0) {
                List<Visitor_Class> vrecord = dbHelper.getAllCurrentTripVisitorRecords(mtripid);
                for (int i = 0; i < vrecord.size(); i++) {
                    visitorlist.put(vrecord.get(i).getJSONObject());
                }
                try {
                    tripobj.put("visitor_record", visitorlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (slippagecount > 0) {
                List<Slippage_Class> srecord = dbHelper.getAllCurrentTripSlippageRecords(mtripid);
                for (int i = 0; i < srecord.size(); i++) {
                    slippagelist.put(srecord.get(i).getJSONObject());
                }
                try {
                    tripobj.put("slippage_record", slippagelist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (dispatchcount > 0) {
                List<Dispatch_Class> drecord = dbHelper.getAllCurrentTripDispatchRecords(mtripid);
                for (int i = 0; i < drecord.size(); i++) {
                    dispatchlist.put(drecord.get(i).getJSONObject());
                }
                try {
                    tripobj.put("dispatch_record", dispatchlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (lostgearcount > 0) {
                List<Lost_Gear_Class> lorecord = dbHelper.getAllCurrentTripLostGearRecords(mtripid);
                for (int i = 0; i < lorecord.size(); i++) {
                    lostgearlist.put(lorecord.get(i).getJSONObject());
                }
                try {
                    tripobj.put("lost_gear_record", lostgearlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (prawndipcount > 0) {
                List<Prawn_Dip_Class> pdrecord = dbHelper.getAllCurrentTripPrawnDipRecords(mtripid);
                for (int i = 0; i < pdrecord.size(); i++) {
                    prawndiplist.put(pdrecord.get(i).getJSONObject());
                }
                try {
                    tripobj.put("prawn_dip_record", prawndiplist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Log.d("JSON trip count", Integer.toString(tripcount));
            if (tripcount > 0) {
                List<Trip_Class> trecord = dbHelper.getAllCurrentTripRecords(mtripid);
                for (int i = 0; i < trecord.size(); i++) {
                    triplist.put(trecord.get(i).getJSONObject());
                }
                try {
                    tripobj.put("trip_record", triplist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            String tripdata = tripobj.toString();
            String LOGIN_URL = "https://login.marineapps.net/api_check.php";
            HashMap<String, String> rsparams = new HashMap<>();
            rsparams.put("system_request", "upload_end_trip_report");
            rsparams.put("tripdata", tripdata);
            JSONObject json = insertdb.insertPOSTRecordOnline(LOGIN_URL, rsparams);
            if (json != null) {
                Log.d("JSON trip return", String.valueOf(json));
                int endonlinestatus = 0;
                try {
                    endonlinestatus = json.getInt(TAG_SUCCESS);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (endonlinestatus == 1) {
                    if (tempcount > 0) {
                        dbHelper.deleteAllTripTemperatureRecords(mtripid);
                    }

                    if (errorcount > 0) {
                        dbHelper.deleteAllUserErrorRecords(muserid);
                    }

                    if (whalecount > 0) {
                        dbHelper.deleteAllTripWhaleDolphinRecords(mtripid);
                    }

                    if (incidentcount > 0) {
                        dbHelper.deleteAllTripIncidentRecords(mtripid);
                    }

                    if (commcount > 0) {
                        dbHelper.deleteAllTripCommunicationRecords(mtripid);
                    }

                    if (marinecount > 0) {
                        dbHelper.deleteAllTripMarineRecords(mtripid);
                    }

                    if (visitorcount > 0) {
                        dbHelper.deleteAllTripVisitorRecords(mtripid);
                    }

                    if (slippagecount > 0) {
                        dbHelper.deleteAllTripSlippageRecords(mtripid);
                    }

                    if (dispatchcount > 0) {
                        dbHelper.deleteAllTripDispatchRecords(mtripid);
                    }

                    if (lostgearcount > 0) {
                        dbHelper.deleteAllTripLostGearRecords(mtripid);
                    }

                    if (prawndipcount > 0) {
                        dbHelper.deleteAllTripPrawnDipRecords(mtripid);
                    }

                    if (tripcount > 0) {
                        dbHelper.deleteTripbyID(mtripid);
                    }
                }
                Log.d("JSON result rp", json.toString());
                return json;
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            mWiFiTripUploadTask = null;
            showProgress(false);
            if (json != null) {
                try {
                    int tstatus = json.getInt(TAG_SUCCESS);
                    if (tstatus == 1) {
                        dialog.dismiss();
                        sharedPreference.save(context, TAG_TRIPID, "");
                        sharedPreference.save(context, TAG_TRIP_STATUS, "Idle");
                        sharedPreference.save(context, TAG_TRIP_LOG_NO, "");
                        sharedPreference.save(context, TAG_TRIP_START_DATE, "");
                        sharedPreference.save(context, TAG_TRIP_ONLINE_ID, "");
                        confirmDialog();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Failed to stop current trip", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Failure to connect to raspberry pi", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mWiFiTripUploadTask = null;
            showProgress(false);
        }
    }
}	