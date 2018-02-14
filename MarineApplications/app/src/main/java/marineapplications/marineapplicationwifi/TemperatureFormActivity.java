package marineapplications.marineapplicationwifi;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

/**
 * The type Temperature form activity.
 */
public class TemperatureFormActivity extends AppCompatActivity {
    private static final String TAG_TRIP_ONLINE_ID = "online_trip_id";
    /**
     * The Dateedit.
     */
    EditText dateedit;
    /**
     * The Timeedit.
     */
    EditText timeedit;
    /**
     * The Chillededit.
     */
    EditText chillededit;
    /**
     * The Freezeredit.
     */
    EditText freezeredit;
    /**
     * The Blastedit.
     */
    EditText blastedit;
    /**
     * The Coreedit.
     */
    EditText coreedit;
    /**
     * The Gpsedit.
     */
    EditText gpsedit;
    /**
     * The Addtemp.
     */
    Button addtemp;
    /**
     * The Shared preference.
     */
    SharedPreference sharedPreference;
    /**
     * The Context.
     */
    Activity context = this;
    /**
     * The M temp form view.
     */
    View mTempFormView;
    /**
     * The Confclass.
     */
    Configure_Class confclass;
    private TripUploadTask mTripTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.temptoolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle(R.string.temperatureformlabel);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TemperatureFormActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        confclass = new Configure_Class();
        dateedit = (EditText) findViewById(R.id.editdpicker);
        dateedit.setInputType(InputType.TYPE_NULL);
        timeedit = (EditText) findViewById(R.id.edit_time);
        timeedit.setInputType(InputType.TYPE_NULL);
        chillededit = (EditText) findViewById(R.id.edit_text_chilled);
        freezeredit = (EditText) findViewById(R.id.edit_text_freezer);
        blastedit = (EditText) findViewById(R.id.edit_text_blast);
        coreedit = (EditText) findViewById(R.id.edit_text_core);
        gpsedit = (EditText) findViewById(R.id.edit_text_gps);

        addtemp = (Button) findViewById(R.id.addformdatabutton);
        mTempFormView = findViewById(R.id.temp_form);
        dateedit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                confclass.hideSoftKeyboard(TemperatureFormActivity.this);
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(TemperatureFormActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        dateedit.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        dateedit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    confclass.hideSoftKeyboard(TemperatureFormActivity.this);
                }
            }
        });

        timeedit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                confclass.hideSoftKeyboard(TemperatureFormActivity.this);
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TemperatureFormActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeedit.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        timeedit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    confclass.hideSoftKeyboard(TemperatureFormActivity.this);
                }
            }
        });

        addtemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_current_form();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TemperatureFormActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    private void submit_current_form() {
        if (mTripTask != null) {
            return;
        }

        // Reset errors.
        dateedit.setError(null);
        timeedit.setError(null);
        chillededit.setError(null);
        freezeredit.setError(null);
        blastedit.setError(null);
        coreedit.setError(null);
        gpsedit.setError(null);

        // Store values at the time of the login attempt.
        String datepicker = dateedit.getText().toString();
        String timepicker = timeedit.getText().toString();
        String chilledtext = chillededit.getText().toString();
        String freezertext = freezeredit.getText().toString();
        String blasttext = blastedit.getText().toString();
        String coretext = coreedit.getText().toString();
        String gpstext = gpsedit.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check if entity is empty.
        if (TextUtils.isEmpty(chilledtext)) {
            chillededit.setError(getString(R.string.error_field_required));
            focusView = chillededit;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(freezertext)) {
            freezeredit.setError(getString(R.string.error_field_required));
            focusView = freezeredit;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(blasttext)) {
            blastedit.setError(getString(R.string.error_field_required));
            focusView = blastedit;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(coretext)) {
            coreedit.setError(getString(R.string.error_field_required));
            focusView = coreedit;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(gpstext)) {
            gpsedit.setError(getString(R.string.error_field_required));
            focusView = gpsedit;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            String[] separated = datepicker.split("/");
            String newdatepicker = separated[2] + "-" + separated[1] + "-" + separated[0];
            String newTimepicker = timepicker + ":00";
            mTripTask = new TripUploadTask(newdatepicker, newTimepicker, chilledtext, freezertext, blasttext, coretext, gpstext);
            mTripTask.execute();
        }
    }

    /**
     * The type Trip upload task.
     */
    public class TripUploadTask extends AsyncTask<Void, Void, JSONObject> {
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_TRIPID = "trip_id";
        private static final String TAG_USER = "user_id";
        private final String mdate;
        private final String mtime;
        private final String mchilled;
        private final String mfreezer;
        private final String mblast;
        private final String mcore;
        private final String mgps;
        /**
         * The Insertdb.
         */
        InsertDB_Class insertdb = null;
        /**
         * The Shared preference.
         */
        SharedPreference sharedPreference = null;
        private Boolean insertresult;
        private ProgressDialog dialog;

        /**
         * Instantiates a new Trip upload task.
         *
         * @param datepick   the datepick
         * @param timepick   the timepick
         * @param strchilled the strchilled
         * @param freezer    the freezer
         * @param blast      the blast
         * @param strcore    the strcore
         * @param gps        the gps
         */
        TripUploadTask(String datepick, String timepick, String strchilled, String freezer, String blast, String strcore, String gps) {
            mdate = datepick;
            mtime = timepick;
            mchilled = strchilled;
            mfreezer = freezer;
            mblast = blast;
            mcore = strcore;
            mgps = gps;
            dialog = new ProgressDialog(context);
            confclass = new Configure_Class();
            insertdb = new InsertDB_Class();
            sharedPreference = new SharedPreference();
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Saving Temperature information, please wait.");
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {

            String online_trip_id = "";
            String user_id = sharedPreference.getValue(context, TAG_USER);
            String trip_id = sharedPreference.getValue(context, TAG_TRIPID);
            if (sharedPreference.getValue(context, TAG_TRIP_ONLINE_ID) != null) {
                if (!sharedPreference.getValue(context, TAG_TRIP_ONLINE_ID).equals("")) {
                    online_trip_id = sharedPreference.getValue(context, TAG_TRIP_ONLINE_ID);
                }
            }

            String online_trip = !online_trip_id.equals("") ? online_trip_id : "";
            if (confclass.isConnectedToInternet()) {
                String LOGIN_URL = "https://login.marineapps.net/api_check.php";
                HashMap<String, String> params = new HashMap<>();
                params.put("system_request", "upload_temperature");
                params.put("user_id", user_id);
                params.put("trip_id", online_trip);
                params.put("gps", mgps);
                params.put("chilled", mchilled);
                params.put("freezer", mfreezer);
                params.put("blast", mblast);
                params.put("core", mcore);
                params.put("input_type", "MANUAL");
                params.put("date_recorded", mdate);
                params.put("time_recorded", mtime);

                Log.d("request", "starting");

                JSONObject json = insertdb.insertPOSTRecordOnline(LOGIN_URL, params);

                if (json != null) {
                    return json;
                }
            } else {
                JSONObject json = new JSONObject();
                String datetimepick = mdate + " " + mtime;
                try {
                    insertresult = insertdb.TemperatureRecordInsertDB(context, datetimepick, mchilled, mfreezer, mblast, mcore, mgps, trip_id, user_id, "M");
                    Log.d("JSON save rec", String.valueOf(insertresult));
                    json.put("success", insertresult ? 1 : 0);
                    return json;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            mTripTask = null;

            if (json != null) {
                dialog.dismiss();
                try {
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Toast.makeText(getApplicationContext(),
                                "Success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(TemperatureFormActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Failure to insert record", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Failure to insert record", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mTripTask = null;
        }
    }
}