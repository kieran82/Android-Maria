package marineapplications.marineapplicationwifi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
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
 * The type Whale dolphin form activity.
 */
public class WhaleDolphinFormActivity extends AppCompatActivity {

    /**
     * The Addformdata.
     */
    Button addformdata;
    /**
     * The Shared preference.
     */
    SharedPreference sharedPreference;
    /**
     * The Context.
     */
    Activity context = this;
    /**
     * The Confclass.
     */
    Configure_Class confclass;
    private EditText gpslat;
    private EditText gpslong;
    private EditText dateedit;
    private EditText timeedit;
    private EditText speciesedit;
    private EditText groupsizedit;
    private View mProgressView;
    private View mTempFormView;
    private TripUploadTask mTripTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whale_dolphin_form);

        gpslat = (EditText) findViewById(R.id.edit_text_gpslat);
        gpslong = (EditText) findViewById(R.id.edit_text_gpslong);
        speciesedit = (EditText) findViewById(R.id.edit_text_species);
        groupsizedit = (EditText) findViewById(R.id.edit_text_groupsize);

        Toolbar toolbar = (Toolbar) findViewById(R.id.temptoolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle(R.string.whaledolphintext);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WhaleDolphinFormActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        confclass = new Configure_Class();
        dateedit = (EditText) findViewById(R.id.editdpicker);
        dateedit.setInputType(InputType.TYPE_NULL);
        timeedit = (EditText) findViewById(R.id.edit_time);
        timeedit.setInputType(InputType.TYPE_NULL);
        addformdata = (Button) findViewById(R.id.addformdatabutton);
        mTempFormView = findViewById(R.id.temp_form);
        mProgressView = findViewById(R.id.login_progress);
        dateedit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                confclass.hideSoftKeyboard(WhaleDolphinFormActivity.this);
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(WhaleDolphinFormActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    confclass.hideSoftKeyboard(WhaleDolphinFormActivity.this);
                }
            }
        });

        timeedit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                confclass.hideSoftKeyboard(WhaleDolphinFormActivity.this);
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(WhaleDolphinFormActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                    confclass.hideSoftKeyboard(WhaleDolphinFormActivity.this);
                }
            }
        });

        addformdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_current_form();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WhaleDolphinFormActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    private void submit_current_form() {
        if (mTripTask != null) {
            return;
        }

        // Reset errors.
        dateedit.setError(null);
        timeedit.setError(null);
        gpslat.setError(null);
        gpslong.setError(null);
        speciesedit.setError(null);
        groupsizedit.setError(null);

        // Store values at the time of the login attempt.
        String datepicker = dateedit.getText().toString();
        String timepicker = timeedit.getText().toString();
        String gpslatitute = gpslat.getText().toString();
        String gpslongtitude = gpslong.getText().toString();
        String species = speciesedit.getText().toString();
        String group_size = groupsizedit.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check if entity is empty.
        if (TextUtils.isEmpty(gpslatitute)) {
            gpslat.setError(getString(R.string.error_field_required));
            focusView = gpslat;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(gpslongtitude)) {
            gpslong.setError(getString(R.string.error_field_required));
            focusView = gpslong;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(species)) {
            speciesedit.setError(getString(R.string.error_field_required));
            focusView = speciesedit;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(group_size)) {
            groupsizedit.setError(getString(R.string.error_field_required));
            focusView = groupsizedit;
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
            String[] separated = datepicker.split("/");
            String newdatepicker = separated[2] + "-" + separated[1] + "-" + separated[0];
            String newTimepicker = timepicker + ":00";
            mTripTask = new TripUploadTask(newdatepicker, newTimepicker, gpslatitute, gpslongtitude, species, group_size);
            mTripTask.execute();
        }
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
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mTempFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * The type Trip upload task.
     */
    public class TripUploadTask extends AsyncTask<Void, Void, JSONObject> {
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_TRIPID = "trip_id";
        private static final String TAG_USER = "user_id";
        private static final String TAG_TRIP_ONLINE_ID = "online_trip_id";
        private final String mdate;
        private final String mtime;
        private final String mgpslat;
        private final String mgpslong;
        private final String mspecies;
        private final String mgroupsize;
        /**
         * The Insertdb.
         */
        InsertDB_Class insertdb;
        private ProgressDialog dialog;
        private Boolean insertresult;

        /**
         * Instantiates a new Trip upload task.
         *
         * @param datepick     the datepick
         * @param timepick     the timepick
         * @param gpsLat       the gps lat
         * @param gpsLong      the gps long
         * @param species_name the species name
         * @param groupsize    the groupsize
         */
        TripUploadTask(String datepick, String timepick, String gpsLat, String gpsLong, String species_name, String groupsize) {
            mdate = datepick;
            mtime = timepick;
            mgpslat = gpsLat;
            mgpslong = gpsLong;
            mspecies = species_name;
            mgroupsize = groupsize;
            dialog = new ProgressDialog(context);
            insertdb = new InsertDB_Class();
            sharedPreference = new SharedPreference();
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Saving Whale and Dolphin information, please wait.");
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {
            try {
                String user_id = sharedPreference.getValue(context, TAG_USER);
                String trip_id = sharedPreference.getValue(context, TAG_TRIPID);
                String online_trip_id = sharedPreference.getValue(context, TAG_TRIP_ONLINE_ID) != null ? sharedPreference.getValue(context, TAG_TRIP_ONLINE_ID) : trip_id;
                if (confclass.isConnectedToInternet()) {
                    String LOGIN_URL = "https://login.marineapps.net/api_check.php";
                    HashMap<String, String> params = new HashMap<>();
                    params.put("system_request", "upload_whale_and_dolphin");
                    params.put("trip_id", online_trip_id);
                    params.put("view_date", mdate);
                    params.put("view_time", mtime);
                    params.put("position", mgpslat + " " + mgpslong);
                    params.put("species", mspecies);
                    params.put("group_size", mgroupsize);

                    Log.d("request", "starting");

                    JSONObject json = insertdb.insertPOSTRecordOnline(LOGIN_URL, params);

                    if (json != null) {
                        return json;
                    }
                } else {
                    JSONObject json = new JSONObject();

                    try {
                        insertdb = new InsertDB_Class();
                        insertresult = insertdb.WhaleDolphinRecordInsertDB(context, mdate, mtime, mgpslat, mgpslong, mspecies, mgroupsize, trip_id, user_id);
                        json.put("success", insertresult ? 1 : 0);
                        return json;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
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
                        Intent intent = new Intent(WhaleDolphinFormActivity.this, DashboardActivity.class);
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
            mTripTask
                    = null;
            showProgress(false);
        }
    }
}