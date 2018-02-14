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
 * The type Communication form activity.
 */
public class CommunicationFormActivity extends AppCompatActivity {

    /**
     * The Submitbutton.
     */
    Button submitbutton;
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
    private EditText vesselfromedit;
    private EditText vesseltoedit;
    private EditText vesselcommentedit;
    private View mProgressView;
    private View mTempFormView;
    private TripUploadTask mTripTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_form);
        confclass = new Configure_Class();
        //Toolbar of Page
        Toolbar toolbar = (Toolbar) findViewById(R.id.temptoolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle(R.string.communicationtext);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunicationFormActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        //Assigning Variables to Objects
        dateedit = (EditText) findViewById(R.id.editdpicker);
        vesselfromedit = (EditText) findViewById(R.id.edit_text_vessel_from);
        vesseltoedit = (EditText) findViewById(R.id.edit_text_vessel_to);
        vesselcommentedit = (EditText) findViewById(R.id.edit_text_vessel_comment);
        submitbutton = (Button) findViewById(R.id.addformdatabutton);
        mTempFormView = findViewById(R.id.temp_form);
        mProgressView = findViewById(R.id.login_progress);
        dateedit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Hide the keyboard
                confclass.hideSoftKeyboard(CommunicationFormActivity.this);

                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(CommunicationFormActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        //datepicker focus
        dateedit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    confclass.hideSoftKeyboard(CommunicationFormActivity.this);
                }
            }
        });

        //datepicker onclick
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_current_form();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CommunicationFormActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    //Form submission function
    private void submit_current_form() {
        if (mTripTask != null) {
            return;
        }

        // Reset errors.
        dateedit.setError(null);
        vesselfromedit.setError(null);
        vesseltoedit.setError(null);
        vesselcommentedit.setError(null);

        // Store values at the time of the login attempt.
        String datepicker = dateedit.getText().toString();
        String vessel_from = vesselfromedit.getText().toString();
        String vessel_to = vesseltoedit.getText().toString();
        String vessel_comment = vesselcommentedit.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check if entity is empty.
        if (TextUtils.isEmpty(vessel_from)) {
            vesselfromedit.setError(getString(R.string.error_field_required));
            focusView = vesselfromedit;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(vessel_to)) {
            vesseltoedit.setError(getString(R.string.error_field_required));
            focusView = vesseltoedit;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(vessel_comment)) {
            vesselcommentedit.setError(getString(R.string.error_field_required));
            focusView = vesselcommentedit;
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

            //Start Trip Upload Asynctask
            String[] separated = datepicker.split("/");
            String newdatepicker = separated[2] + "-" + separated[1] + "-" + separated[0];
            mTripTask = new TripUploadTask(newdatepicker, vessel_from, vessel_to, vessel_comment);
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
        private final String mvesselfrom;
        private final String mvesselto;
        private final String mvesselcomment;
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
         * @param datepick      the datepick
         * @param vesselFrom    the vessel from
         * @param vesselTo      the vessel to
         * @param vesselComment the vessel comment
         */
        TripUploadTask(String datepick, String vesselFrom, String vesselTo, String vesselComment) {
            mdate = datepick;
            mvesselfrom = vesselFrom;
            mvesselto = vesselTo;
            mvesselcomment = vesselComment;
            confclass = new Configure_Class();
            insertdb = new InsertDB_Class();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {
            try {
                sharedPreference = new SharedPreference();
                String user_id = sharedPreference.getValue(context, TAG_USER);
                String trip_id = sharedPreference.getValue(context, TAG_TRIPID);
                String online_trip_id = sharedPreference.getValue(context, TAG_TRIP_ONLINE_ID) != null ? sharedPreference.getValue(context, TAG_TRIP_ONLINE_ID) : trip_id;
                if (confclass.isConnectedToInternet()) {
                    String LOGIN_URL = "https://login.marineapps.net/api_check.php";
                    HashMap<String, String> params = new HashMap<>();
                    params.put("system_request", "upload_communication");
                    params.put("trip_id", online_trip_id);
                    params.put("date_added", mdate);
                    params.put("vessel_from", mvesselfrom);
                    params.put("vessel_to", mvesselto);
                    params.put("comments", mvesselcomment);

                    Log.d("request", "starting");

                    JSONObject json = insertdb.insertPOSTRecordOnline(LOGIN_URL, params);

                    if (json != null) {
                        return json;
                    }
                } else {
                    JSONObject json = new JSONObject();

                    try {
                        insertresult = insertdb.CommunicationRecordInsertDB(context, mdate, mvesselfrom, mvesselto, mvesselcomment, trip_id, user_id);
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
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Toast.makeText(getApplicationContext(),
                                "Success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CommunicationFormActivity.this, DashboardActivity.class);
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