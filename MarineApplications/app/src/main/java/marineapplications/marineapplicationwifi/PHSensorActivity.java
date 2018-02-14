package marineapplications.marineapplicationwifi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

/**
 * The type Ph sensor activity.
 */
public class PHSensorActivity extends AppCompatActivity {
    /**
     * The Addformdata.
     */
    Button addformdata;
    /**
     * The Context.
     */
    Activity context = this;
    /**
     * The Shared preference.
     */
    SharedPreference sharedPreference;
    /**
     * The Confclass.
     */
    Configure_Class confclass;
    //Set Variables
    private EditText dateedit;
    private EditText lostgear;
    private EditText lostposition;
    private EditText lostquantity;
    private EditText lostreason;
    private EditText lostreplacement;
    private EditText lostdetails;
    private View mProgressView;
    private View mTempFormView;
    private TripUploadTask mTripTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phsensor);

        //Toolbar of Page
        Toolbar toolbar = (Toolbar) findViewById(R.id.temptoolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle(R.string.phsensortext);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PHSensorActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        confclass = new Configure_Class();
        //Assigning Variables to Objects
        dateedit = (EditText) findViewById(R.id.editdpicker);
        lostgear = (EditText) findViewById(R.id.edit_gear);
        lostposition = (EditText) findViewById(R.id.edit_text_position);
        lostquantity = (EditText) findViewById(R.id.edit_text_quantity);
        lostreason = (EditText) findViewById(R.id.edit_text_reason);
        lostreplacement = (EditText) findViewById(R.id.edit_text_replacement);
        lostdetails = (EditText) findViewById(R.id.edit_text_details);
        addformdata = (Button) findViewById(R.id.addformdatabutton);
        mTempFormView = findViewById(R.id.temp_form);
        mProgressView = findViewById(R.id.login_progress);
        dateedit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Hide the keyboard
                confclass.hideSoftKeyboard(PHSensorActivity.this);

                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(PHSensorActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        /* Code to get date and time */
                        selectedmonth = selectedmonth + 1;
                        String current_date_of_month = selectedday + "/" + selectedmonth + "/" + selectedyear;
                        dateedit.setText(current_date_of_month);
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
                    confclass.hideSoftKeyboard(PHSensorActivity.this);
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
        Intent intent = new Intent(PHSensorActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    private void submit_current_form() {
        if (mTripTask != null) {
            return;
        }

        // Reset errors.
        dateedit.setError(null);
        lostgear.setError(null);
        lostposition.setError(null);
        lostquantity.setError(null);
        lostreason.setError(null);
        lostreplacement.setError(null);
        lostdetails.setError(null);

        // Store values at the time of the login attempt.
        String datepicker = dateedit.getText().toString();
        String lost_gear = lostgear.getText().toString();
        String lost_position = lostposition.getText().toString();
        String lost_quantity = lostquantity.getText().toString();
        String lost_reason = lostreason.getText().toString();
        String lost_replacement = lostreplacement.getText().toString();
        String lost_details = lostdetails.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check if entity is empty.
        if (TextUtils.isEmpty(lost_gear)) {
            lostgear.setError(getString(R.string.error_field_required));
            focusView = lostgear;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(lost_position)) {
            lostposition.setError(getString(R.string.error_field_required));
            focusView = lostposition;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(lost_quantity)) {
            lostquantity.setError(getString(R.string.error_field_required));
            focusView = lostquantity;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(lost_reason)) {
            lostreason.setError(getString(R.string.error_field_required));
            focusView = lostreason;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(lost_replacement)) {
            lostreplacement.setError(getString(R.string.error_field_required));
            focusView = lostreplacement;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(lost_details)) {
            lostdetails.setError(getString(R.string.error_field_required));
            focusView = lostdetails;
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
            mTripTask = new TripUploadTask(newdatepicker, lost_gear, lost_position, lost_quantity, lost_reason, lost_replacement, lost_details);
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
        private static final String TAG_TRIP_ONLINE_ID = "online_trip_id";
        private final String mdate;
        private final String mlostgear;
        private final String mlostposition;
        private final String mlostquantity;
        private final String mlostreason;
        private final String mlostreplacement;
        private final String mlostdetails;
        /**
         * The Insertdb.
         */
        InsertDB_Class insertdb;
        /**
         * The Confclass.
         */
        Configure_Class confclass;
        private Boolean insertresult;

        /**
         * Instantiates a new Trip upload task.
         *
         * @param datepick    the datepick
         * @param lgear       the lgear
         * @param lposition   the lposition
         * @param lqty        the lqty
         * @param lreason     the lreason
         * @param lreplacemen the lreplacemen
         * @param ldetails    the ldetails
         */
        TripUploadTask(String datepick, String lgear, String lposition, String lqty, String lreason, String lreplacemen, String ldetails) {
            mdate = datepick;
            mlostgear = lgear;
            mlostposition = lposition;
            mlostquantity = lqty;
            mlostreason = lreason;
            mlostreplacement = lreplacemen;
            mlostdetails = ldetails;
            confclass = new Configure_Class();
            insertdb = new InsertDB_Class();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {
            try {
                String user_id = sharedPreference.getValue(context, TAG_USER);
                String trip_id = sharedPreference.getValue(context, TAG_TRIPID);
                String online_trip_id = sharedPreference.getValue(context, TAG_TRIP_ONLINE_ID) != null ? sharedPreference.getValue(context, TAG_TRIP_ONLINE_ID) : sharedPreference.getValue(context, TAG_TRIPID);
                if (confclass.isConnectedToInternet()) {
                    String LOGIN_URL = "https://login.marineapps.net/api_check.php";
                    HashMap<String, String> params = new HashMap<>();
                    params.put("system_request", "upload_lost_gear");
                    params.put("trip_id", online_trip_id);
                    params.put("date_added", mdate);
                    params.put("gear", mlostgear);
                    params.put("position", mlostposition);
                    params.put("quantity", mlostquantity);
                    params.put("reason", mlostreason);
                    params.put("replacement", mlostreplacement);
                    params.put("details", mlostdetails);

                    Log.d("request", "starting");

                    JSONObject json = insertdb.insertPOSTRecordOnline(LOGIN_URL, params);

                    if (json != null) {
                        return json;
                    }
                } else {
                    JSONObject json = new JSONObject();

                    try {
                        insertresult = insertdb.LostGearRecordInsertDB(context, mdate, mlostgear, mlostposition, Integer.parseInt(mlostquantity), mlostreason, mlostreplacement, mlostdetails, trip_id, user_id);

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
                        Intent intent = new Intent(PHSensorActivity.this, DashboardActivity.class);
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
}
