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
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

/**
 * The type Slippage form activity.
 */
public class SlippageFormActivity extends AppCompatActivity {

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
    private EditText icessubareaedit;
    private EditText slippagepositionedit;
    private EditText targetfisheriesedit;
    private EditText speciesslippededit;
    private EditText slippagequantityedit;
    private EditText slippagesizeedit;
    private EditText slippagereasonedit;
    private Spinner sampletakenspinner;
    private View mProgressView;
    private View mTempFormView;
    private TripUploadTask mTripTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slippage_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.temptoolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle(R.string.slippageformlabel);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SlippageFormActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        confclass = new Configure_Class();
        dateedit = (EditText) findViewById(R.id.editdpicker);
        icessubareaedit = (EditText) findViewById(R.id.edit_text_icessubarea);
        slippagepositionedit = (EditText) findViewById(R.id.edit_text_position);
        targetfisheriesedit = (EditText) findViewById(R.id.edit_target_fisheries);
        speciesslippededit = (EditText) findViewById(R.id.edit_species_slipped);
        slippagequantityedit = (EditText) findViewById(R.id.edit_text_quantity);
        slippagereasonedit = (EditText) findViewById(R.id.edit_text_slippage_reason);
        slippagesizeedit = (EditText) findViewById(R.id.edit_text_size);
        sampletakenspinner = (Spinner) findViewById(R.id.sampletakenspinner);
        addformdata = (Button) findViewById(R.id.addformdatabutton);

        mTempFormView = findViewById(R.id.temp_form);
        mProgressView = findViewById(R.id.login_progress);
        dateedit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                confclass.hideSoftKeyboard(SlippageFormActivity.this);
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(SlippageFormActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    confclass.hideSoftKeyboard(SlippageFormActivity.this);
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
        Intent intent = new Intent(SlippageFormActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    private void submit_current_form() {
        if (mTripTask != null) {
            return;
        }

        // Reset errors.
        dateedit.setError(null);
        icessubareaedit.setError(null);
        slippagepositionedit.setError(null);
        targetfisheriesedit.setError(null);
        speciesslippededit.setError(null);
        slippagequantityedit.setError(null);
        slippagesizeedit.setError(null);

        // Store values at the time of the login attempt.
        String datepicker = dateedit.getText().toString();
        String ices_sub_area = icessubareaedit.getText().toString();
        String slippage_position = slippagepositionedit.getText().toString();
        String target_fisheries = targetfisheriesedit.getText().toString();
        String species_slipped = speciesslippededit.getText().toString();
        String slippage_quantity = slippagequantityedit.getText().toString();
        String slippage_reason = slippagereasonedit.getText().toString();
        String slippage_size = slippagesizeedit.getText().toString();
        String sampleTaken = sampletakenspinner.getSelectedItem().toString();


        boolean cancel = false;
        View focusView = null;

        // Check if entity is empty.
        if (TextUtils.isEmpty(ices_sub_area)) {
            icessubareaedit.setError(getString(R.string.error_field_required));
            focusView = icessubareaedit;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(slippage_position)) {
            slippagepositionedit.setError(getString(R.string.error_field_required));
            focusView = slippagepositionedit;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(target_fisheries)) {
            targetfisheriesedit.setError(getString(R.string.error_field_required));
            focusView = targetfisheriesedit;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(species_slipped)) {
            speciesslippededit.setError(getString(R.string.error_field_required));
            focusView = speciesslippededit;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(slippage_quantity)) {
            slippagequantityedit.setError(getString(R.string.error_field_required));
            focusView = slippagequantityedit;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(slippage_reason)) {
            slippagequantityedit.setError(getString(R.string.error_field_required));
            focusView = slippagereasonedit;
            cancel = true;
        }

        // Check if entity is empty.
        if (TextUtils.isEmpty(slippage_size)) {
            slippagesizeedit.setError(getString(R.string.error_field_required));
            focusView = slippagesizeedit;
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
            mTripTask = new TripUploadTask(newdatepicker, ices_sub_area, slippage_position, target_fisheries, species_slipped, slippage_quantity, slippage_size, slippage_reason, sampleTaken);
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
        private final String micessubarea;
        private final String mposition;
        private final String mtargetfisheries;
        private final String mspecieslipped;
        private final String mslippagequantity;
        private final String mslippagesize;
        private final String mslippagereason;
        private final String msampletaken;
        /**
         * The Insertdb.
         */
        InsertDB_Class insertdb = null;

        /**
         * Instantiates a new Trip upload task.
         *
         * @param datepick         the datepick
         * @param icesSubArea      the ices sub area
         * @param Position         the position
         * @param targetFisheries  the target fisheries
         * @param SpeciesSlipped   the species slipped
         * @param slippageQuantity the slippage quantity
         * @param slippageSize     the slippage size
         * @param slippageReason   the slippage reason
         * @param SampleTaken      the sample taken
         */
        TripUploadTask(String datepick, String icesSubArea, String Position, String targetFisheries, String SpeciesSlipped, String slippageQuantity, String slippageSize, String slippageReason, String SampleTaken) {
            mdate = datepick;
            micessubarea = icesSubArea;
            mposition = Position;
            mtargetfisheries = targetFisheries;
            mspecieslipped = SpeciesSlipped;
            mslippagequantity = slippageQuantity;
            mslippagesize = slippageSize;
            mslippagereason = slippageReason;
            msampletaken = SampleTaken;
            confclass = new Configure_Class();
            insertdb = new InsertDB_Class();
            sharedPreference = new SharedPreference();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {
            Boolean insertresult = false;
            try {
                String user_id = sharedPreference.getValue(context, TAG_USER);
                String trip_id = sharedPreference.getValue(context, TAG_TRIPID);
                String online_trip_id = sharedPreference.getValue(context, TAG_TRIP_ONLINE_ID) != null ? sharedPreference.getValue(context, TAG_TRIP_ONLINE_ID) : trip_id;
                if (confclass.isConnectedToInternet()) {
                    String LOGIN_URL = "https://login.marineapps.net/api_check.php";
                    HashMap<String, String> params = new HashMap<>();
                    params.put("system_request", "upload_slippage");
                    params.put("trip_id", online_trip_id);
                    params.put("ices_sub_area", micessubarea);
                    params.put("position", mposition);
                    params.put("target_fishery", mtargetfisheries);
                    params.put("species_slipped", mspecieslipped);
                    params.put("slippage_quantity", mslippagequantity);
                    params.put("slippage_size", mslippagesize);
                    params.put("reason", mslippagereason);
                    params.put("sample_taken", msampletaken);
                    params.put("date_recorded", mdate);

                    Log.d("request", "starting");

                    JSONObject json = insertdb.insertPOSTRecordOnline(LOGIN_URL, params);

                    if (json != null) {
                        return json;
                    }
                } else {
                    JSONObject json = new JSONObject();

                    try {
                        insertresult = insertdb.SlippageRecordInsertDB(context, mdate, micessubarea, mposition, mtargetfisheries, mspecieslipped, mslippagequantity, mslippagesize, mslippagereason, msampletaken, trip_id, user_id);

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
                        Intent intent = new Intent(SlippageFormActivity.this, DashboardActivity.class);
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