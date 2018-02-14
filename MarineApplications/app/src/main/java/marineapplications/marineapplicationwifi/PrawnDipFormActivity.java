package marineapplications.marineapplicationwifi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * The type Prawn dip form activity.
 */
public class PrawnDipFormActivity extends AppCompatActivity {
    private static final String TAG_TRIP_ONLINE_ID = "online_trip_id";
    private static final String TAG_USER = "user_id";
    /**
     * The User id.
     */
    String user_id = "";
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
    private EditText dateedit;
    private EditText timeedit;
    private EditText editwatervolume;
    private EditText editamountused;
    private Spinner prawndipproductspinner;
    private Spinner crewresponsiblespinner;
    private View mProgressView;
    private View mTempFormView;
    private TripUploadTask mTripTask = null;
    private GetPrawnDipTask mDipRecordTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prawn_dip_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.temptoolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle(R.string.prawndiptext);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrawnDipFormActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        sharedPreference = new SharedPreference();
        user_id = sharedPreference.getValue(context, TAG_USER);
        dateedit = (EditText) findViewById(R.id.editdpicker);
        timeedit = (EditText) findViewById(R.id.edit_time);
        editwatervolume = (EditText) findViewById(R.id.edit_water_volume);
        editamountused = (EditText) findViewById(R.id.edit_amount_used);
        crewresponsiblespinner = (Spinner) findViewById(R.id.crewresponsiblespinner);
        prawndipproductspinner = (Spinner) findViewById(R.id.prawndipproductspinner);
        addformdata = (Button) findViewById(R.id.addprawndipsubmitbutton);
        mTempFormView = findViewById(R.id.temp_form);
        mProgressView = findViewById(R.id.login_progress);
        dateedit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                confclass.hideSoftKeyboard(PrawnDipFormActivity.this);
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(PrawnDipFormActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    confclass.hideSoftKeyboard(PrawnDipFormActivity.this);
                }
            }
        });

        addformdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_current_form();
            }
        });

        mDipRecordTask = new GetPrawnDipTask(user_id);
        mDipRecordTask.execute();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PrawnDipFormActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    private void submit_current_form() {
        if (mTripTask != null) {
            return;
        }

        // Reset errors.
        dateedit.setError(null);
        timeedit.setError(null);
        editwatervolume.setError(null);
        editamountused.setError(null);

        // Store values at the time of the login attempt.
        String datepicker = dateedit.getText().toString();
        String timepicker = timeedit.getText().toString();
        String water_volume = editwatervolume.getText().toString();
        String amount_used = editamountused.getText().toString();
        String crew_responsible = crewresponsiblespinner.getSelectedItem().toString();
        String dipproduct = prawndipproductspinner.getSelectedItem().toString();

        boolean cancel = false;
        View focusView = null;

        // Check if entity is empty.
        if (TextUtils.isEmpty(datepicker)) {
            dateedit.setError(getString(R.string.error_field_required));
            focusView = dateedit;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(timepicker)) {
            timeedit.setError(getString(R.string.error_field_required));
            focusView = timeedit;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(water_volume)) {
            editwatervolume.setError(getString(R.string.error_field_required));
            focusView = editwatervolume;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(amount_used)) {
            editamountused.setError(getString(R.string.error_field_required));
            focusView = editamountused;
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
            String datetimepicker = newdatepicker + " " + newTimepicker;
            mTripTask = new TripUploadTask(datetimepicker, water_volume, dipproduct, amount_used, crew_responsible);
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
        private static final String TAG_TRIPID = "trip_id";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_USER = "user_id";
        private final String mdate;
        private final String mwatervolume;
        private final String mdipproduct;
        private final String mamountused;
        private final String mcrewresponsible;
        /**
         * The Insertdb.
         */
        InsertDB_Class insertdb = null;
        /**
         * The Confclass.
         */
        Configure_Class confclass;


        /**
         * Instantiates a new Trip upload task.
         *
         * @param datepick        the datepick
         * @param watervolume     the watervolume
         * @param dipproduct      the dipproduct
         * @param amountused      the amountused
         * @param crewresponsible the crewresponsible
         */
        TripUploadTask(String datepick, String watervolume, String dipproduct, String amountused, String crewresponsible) {
            mdate = datepick;
            mwatervolume = watervolume;
            mdipproduct = dipproduct;
            mamountused = amountused;
            mcrewresponsible = crewresponsible;
            confclass = new Configure_Class();
            insertdb = new InsertDB_Class();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {
            Boolean insertresult = false;

            try {
                sharedPreference = new SharedPreference();
                String user_id = sharedPreference.getValue(context, TAG_USER);
                String trip_id = sharedPreference.getValue(context, TAG_TRIPID);
                String online_trip_id = sharedPreference.getValue(context, TAG_TRIP_ONLINE_ID) != null ? sharedPreference.getValue(context, TAG_TRIP_ONLINE_ID) : trip_id;
                if (confclass.isConnectedToInternet()) {
                    String LOGIN_URL = "https://login.marineapps.net/api_check.php";
                    HashMap<String, String> params = new HashMap<>();
                    params.put("system_request", "upload_prawn_dip");
                    params.put("trip_id", online_trip_id);
                    params.put("volume_water", mwatervolume);
                    params.put("dip_product", mdipproduct);
                    params.put("amount_used", mamountused);
                    params.put("crew_responsible", mcrewresponsible);
                    params.put("date_recorded", mdate);

                    Log.d("request", "starting");

                    JSONObject json = insertdb.insertPOSTRecordOnline(LOGIN_URL, params);

                    if (json != null) {
                        return json;
                    }
                } else {
                    JSONObject json = new JSONObject();

                    try {
                        insertresult = insertdb.PrawnDipRecordInsertDB(context, mdate, mwatervolume, mdipproduct, mamountused, mcrewresponsible, trip_id, user_id);

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
                try {
                    String result = "";
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PrawnDipFormActivity.this, DashboardActivity.class);
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
            showProgress(false);
        }
    }

    /**
     * The type Get prawn dip task.
     */
    public class GetPrawnDipTask extends AsyncTask<Void, Void, JSONObject> {
        private static final String TAG_PRAWNDIP = "prawndip";
        private static final String TAG_CREWNAMES = "crew";
        private final String muser;
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
         * Instantiates a new Get prawn dip task.
         *
         * @param user_id the user id
         */
        GetPrawnDipTask(String user_id) {
            muser = user_id;
            dialog = new ProgressDialog(context);
            insertdb = new InsertDB_Class();
            sharedPreference = new SharedPreference();
            confclass = new Configure_Class();
            readwrite = new Read_Write_Class();
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Recieving Prawn Dip Chemicals, please wait.");
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {
            if (confclass.isConnectedToInternet()) {
                try {
                    String LOGIN_URL = "https://login.marineapps.net/api_check.php";
                    HashMap<String, String> params = new HashMap<>();
                    params.put("system_request", "get_prawn_dip_chemicals");
                    params.put("userid", muser);
                    JSONObject json = insertdb.insertPOSTRecordOnline(LOGIN_URL, params);
                    if (json != null) {
                        return json;
                    }
                } catch (Exception e) {
                    Log.d("prawn_error", e.toString());
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            mDipRecordTask = null;
            if (json != null) {
                dialog.dismiss();
                try {
                    String prawndipstring = json.getString(TAG_PRAWNDIP);
                    String crewstring = json.getString(TAG_CREWNAMES);
                    List<String> plist = new ArrayList<String>(Arrays.asList(prawndipstring.split(" , ")));
                    List<String> clist = new ArrayList<String>(Arrays.asList(crewstring.split(" , ")));
                    ArrayAdapter<String> pdataAdapter = new ArrayAdapter<String>
                            (context, android.R.layout.simple_spinner_item, plist);
                    ArrayAdapter<String> cdataAdapter = new ArrayAdapter<String>
                            (context, android.R.layout.simple_spinner_item, clist);
                    prawndipproductspinner.setAdapter(pdataAdapter);
                    crewresponsiblespinner.setAdapter(cdataAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Unable to connect to raspberry pi database", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mDipRecordTask = null;
        }
    }
}


