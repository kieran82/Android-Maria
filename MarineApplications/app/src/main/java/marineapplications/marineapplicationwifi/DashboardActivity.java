package marineapplications.marineapplicationwifi;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
	* The type Dashboard activity.
*/
public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Constants Variables - Never change
    private static final String TAG_TEMPERATURE_VARIABLES = "temperature_variables";
    private static final String TAG_VESSEL = "vessel_name";
    private static final String TAG_USER = "user_id";
    private static final String TAG_LOGIN = "logged_status";
    private static final String TAG_TRIPID = "trip_id";
    private static final String TAG_TRIP_ONLINE_ID = "online_trip_id";
    private static final String TAG_TRIP_STATUS = "trip_status";
    private static final String TAG_TRIP_START_DATE = "trip_start_date";
    private static final String TAG_TRIP_LOG_NO = "trip_log_sheet_no";
    private static final String TAG_DATABASE_CONNECTION = "current_database_connection";
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
    private static final String TAG_WIFI_CONNECTION = "wifi_connection";
    private static final String TAG_MARIA_CONNECTION = "maria_connection";
    private static final String TAG_BACKGROUND_ACTIVE = "background_active";
    private static final String TAG_SSID_REDIRECT_ACTIVE = "ssid_redirect_active";
	
    /**
		* The M get connection status task.
	*/
    GetConnectionStatusTask mGetConnectionStatusTask = null;
    /**
		* The Db connection ip.
	*/
    String db_connection_ip;
    /**
		* The Logged status.
	*/
    String logged_status = "";
    /**
		* The Trip status.
	*/
    String trip_status = "";
    /**
		* The Vessel name.
	*/
    String vessel_name = "";
    /**
		* The User id.
	*/
    String user_id = "";
    /**
		* The Triplogno.
	*/
    String triplogno = "";
    /**
		* The Tripstartdate.
	*/
    String tripstartdate = "";
    /**
		* The Triprecorddatetime.
	*/
    String triprecorddatetime = "";
    /**
		* The Textchilledhold.
	*/
    TextView textchilledhold;
    /**
		* The Textfreezerhold.
	*/
    TextView textfreezerhold;
    /**
		* The Textblastfreezer.
	*/
    TextView textblastfreezer;
    /**
		* The Phleveltext.
	*/
    TextView phleveltext;
    /**
		* The Textcore.
	*/
    TextView textcore;
    /**
		* The Wifistatustext.
	*/
    TextView wifistatustext;
    /**
		* The Bilgeimage.
	*/
    ImageView bilgeimage;
    /**
		* The Prawnimage.
	*/
    ImageView phsensorimage;
    /**
		* The Prawnimage.
	*/
    ImageView prawnimage;
    /**
		* The Currentwifiimage.
	*/
    ImageView currentwifiimage;
    /**
		* The Currentmariaimage.
	*/
    ImageView currentmariaimage;
    //TableLayout bl;
    /**
		* The M trip task.
	*/
    /**
		* The TempTableLayout.
	*/
    TableLayout TempTableLayout;
    /**
		* The M user trip task.
	*/
	//Start Trip Task
    UserTripTask mUserTripTask = null;
    /**
		* The M get temp task.
	*/
    GetTemperatureTask mGetTempTask = null;
    /**
		* The M user sync data task.
	*/
	//Sync Data Task
    UserSyncDataTask mUserSyncDataTask = null;
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
		* The Context.
	*/
	//Using the Activity Functions inside external and Internal Functions
    Activity context = this;
    /**
		* The Trip cleaning schedule.
	*/
	//Instantiate String Variables
    String trip_cleaning_schedule = "";
    /**
		* The Trip waste action.
	*/
    String trip_waste_action = "";
    /**
		* The Trip etp.
	*/
    String trip_etp = "";
    /**
		* The Trip slippage record.
	*/
    String trip_slippage_record = "";
    /**
		* The Trip prawn dip record.
	*/
    String trip_prawn_dip_record = "";
    /**
		* The Trip incident record.
	*/
    String trip_incident_record = "";
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
		* The Trip visitor record.
	*/
    String trip_visitor_record = "";
    /**
		* The Trip marine litter record.
	*/
    String trip_marine_litter_record = "";
    /**
		* The Trip communication record.
	*/
    String trip_communication_record = "";
    /**
		* The Trip lost gear record.
	*/
    String trip_lost_gear_record = "";
    /**
		* The Trip dispatch freezer record.
	*/
    String trip_dispatch_freezer_record = "";
    /**
		* The Addformdatabutton.
	*/
	//Instantiate Screen Interaction
    Button addformdatabutton;
    /**
		* The Addwhaledolphinbutton.
	*/
    Button addwhaledolphinbutton;
    /**
		* The Resetbasketbutton.
	*/
    Button resetbasketbutton;
    /**
		* The Helpbutton.
	*/
    Button helpbutton;
    /**
		* The Starttripbutton.
	*/
    Button starttripbutton;
    /**
		* The Endtripbutton.
	*/
    Button endtripbutton;
    /**
		* The Logsheetedit.
	*/
    EditText logsheetedit;
    /**
		* The Coordinator layout.
	*/
    CoordinatorLayout coordinatorLayout;
    /**
		* The Db helper.
	*/
    DatabaseHelper dbHelper;
    /**
		* The Bilgerow.
	*/
    TableRow bilgerow;
    /**
		* The Tankonerow.
	*/
    TableRow tankonerow;
    /**
		* The Pbasketrow.
	*/
    TableRow pbasketrow;
    /**
		* The Phlevelrow.
	*/
    TableRow phlevelrow;
	
    /**
		* The Prawn dip handler.
	*/
    /**
		* The Readwrite.
	*/
    Read_Write_Class readwrite;
    /**
		* The Thread sleep conn.
	*/
    long THREAD_SLEEP_CONN = 300000;
    /**
		* The Thread sleep temp.
	*/
    long THREAD_SLEEP_TEMP = 5000000;
    /**
		* The Temperature record handler.
		* <p>
		* /**
		* The Display handler.
	*/
	// Create the Handler object (on the main thread by default)
    Handler DisplayHandler = new Handler();
    /**
		* The Alarmqualityreport mgr.
	*/
    AlarmManager alarmqualityreportMgr;
    /**
		* The Alarmbilgecheck mgr.
	*/
    AlarmManager alarmbilgecheckMgr;
    private LinearLayout tripstatuslinearLayout;
    private LinearLayout logsheetlinear;
    private TextView texttripstatus;
    private TextView texttripstartdate;
    private TextView texttriplogno;
    private TextView textgpscoordinates;
    private TextView textgpsdate;
    private TextView textgpstime;
    private TextView textprawnbasketno;
    private Handler mtempHandler = new Handler();
    private Handler mconnHandler = new Handler();
    private Runnable temprunnableCode = new Runnable() {
        @Override
        public void run() {
            mGetTempTask = new GetTemperatureTask();
            mGetTempTask.execute();
            mtempHandler.postDelayed(temprunnableCode, THREAD_SLEEP_TEMP);
		}
	};
    private Runnable connectionrunnableCode = new Runnable() {
        @Override
        public void run() {
            mGetConnectionStatusTask = new GetConnectionStatusTask();
            mGetConnectionStatusTask.execute();
            mconnHandler.postDelayed(connectionrunnableCode, THREAD_SLEEP_CONN);
		}
	};
	
    private PendingIntent qualityreportpendingIntent;
    private PendingIntent bilgependingIntent;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_dashboard);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
		this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        sharedPreference = new SharedPreference();
        confclass = new Configure_Class();
        readwrite = new Read_Write_Class();
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        logged_status = sharedPreference.getValue(context, TAG_LOGIN);
        vessel_name = sharedPreference.getValue(context, TAG_VESSEL);
        user_id = sharedPreference.getValue(context, TAG_USER);
        trip_status = sharedPreference.getValue(context, TAG_TRIP_STATUS);
        trip_cleaning_schedule = sharedPreference.getValue(context, TAG_CLEANING_SCHEDULE);
        trip_waste_action = sharedPreference.getValue(context, TAG_WASTE_ACTION);
        trip_etp = sharedPreference.getValue(context, TAG_WHALE_DOLPHIN_RECORD);
        trip_slippage_record = sharedPreference.getValue(context, TAG_SLIPPAGE_RECORD);
        trip_prawn_dip_record = sharedPreference.getValue(context, TAG_PRAWN_DIP_RECORD);
        trip_incident_record = sharedPreference.getValue(context, TAG_INCIDENT_RECORD);
        trip_prawn_dip_procedure = sharedPreference.getValue(context, TAG_PRAWN_DIP_PROCEDURE);
        trip_calibration_of_scales = sharedPreference.getValue(context, TAG_CALIBRATION_OF_SCALES);
        trip_target_species_fishing_procedure = sharedPreference.getValue(context, TAG_TARGET_SPECIES_FISHING_PROCEDURE);
        trip_line_caught_procedure = sharedPreference.getValue(context, TAG_LINE_CAUGHT_PROCEDURE);
        trip_visitor_record = sharedPreference.getValue(context, TAG_VISITOR_RECORD);
        trip_marine_litter_record = sharedPreference.getValue(context, TAG_MARINE_LITTER_RECORD);
        trip_lost_gear_record = sharedPreference.getValue(context, TAG_LOST_GEAR_RECORD);
        trip_communication_record = sharedPreference.getValue(context, TAG_COMMUNICATION_RECORD);
        trip_dispatch_freezer_record = sharedPreference.getValue(context, TAG_DISPATCH_RECORD);
        db_connection_ip = sharedPreference.getValue(context, TAG_DATABASE_CONNECTION);
		
        if (sharedPreference.getValue(context, TAG_MARIA_CONNECTION) == null) {
            sharedPreference.save(context, TAG_MARIA_CONNECTION, "no");
            sharedPreference.save(context, TAG_WIFI_CONNECTION, "no");
		}
		
        tripstatuslinearLayout = (LinearLayout) findViewById(R.id.tripstatuslinearLayout);
        logsheetlinear = (LinearLayout) findViewById(R.id.logsheetlinear);
		
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        TempTableLayout = (TableLayout) findViewById(R.id.tablemain);
        String getmethod = null;
        try {
            getmethod = getSendMethod();
			} catch (JSONException e) {
            e.printStackTrace();
		}
		
        bilgerow = (TableRow) findViewById(R.id.bilgerow);
        tankonerow = (TableRow) findViewById(R.id.tankonerow);
        pbasketrow = (TableRow) findViewById(R.id.pbasketrow);
        phlevelrow = (TableRow) findViewById(R.id.phlevelrow);
		
        wifistatustext = (TextView) findViewById(R.id.wifistatustext);
        bilgeimage = (ImageView) findViewById(R.id.bilgeimage);
        phsensorimage = (ImageView) findViewById(R.id.phlevelimg);
        prawnimage = (ImageView) findViewById(R.id.basketimg);
        currentwifiimage = (ImageView) findViewById(R.id.currentwifiimage);
        currentmariaimage = (ImageView) findViewById(R.id.currentmariaimage);
        assert getmethod != null;
        if (getmethod.equals("rockblock")) {
            wifistatustext.setText(getString(R.string.wifi_text_rb));
			} else {
            wifistatustext.setText(getString(R.string.wifi_text_router));
		}
		
        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_dashboard, null);
		
        endtripbutton = (Button) findViewById(R.id.endcurrenttripbtn);
        endtripbutton.setVisibility(View.GONE);
        endtripbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean wificheck = confclass.checkWifiConnected(context);
                if (!wificheck) {
                    Toast.makeText(getApplicationContext(),
					"This device doesn't have a wifi connection, please try again", Toast.LENGTH_SHORT).show();
					} else {
                    Intent intent = new Intent(DashboardActivity.this, EndTripQuestionsActivity.class);
                    startActivity(intent);
				}
			}
		});
		
        starttripbutton = (Button) findViewById(R.id.btnstarttrip);
        starttripbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean wificheck = confclass.checkWifiConnected(context);
                if (!wificheck) {
                    Toast.makeText(getApplicationContext(),
					"This device doesn't have a wifi connection, please try again", Toast.LENGTH_SHORT).show();
					} else {
                    try {
                        String sendmethod = checkSendMethod();
                        confirmDialog(sendmethod);
						} catch (JSONException e) {
                        e.printStackTrace();
					}
				}
			}
		});
		
        logsheetedit = (EditText) findViewById(R.id.edit_logsheet);
        logsheetedit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //confclass.hideSoftKeyboard(DashboardActivity.this);
				}
			}
		});
		
        texttripstatus = (TextView) findViewById(R.id.tripstatus);
        texttripstartdate = (TextView) findViewById(R.id.tripdate);
        texttriplogno = (TextView) findViewById(R.id.tripnumber);
		
        textchilledhold = (TextView) findViewById(R.id.textchilledhold);
        textfreezerhold = (TextView) findViewById(R.id.textfreezerhold);
        textblastfreezer = (TextView) findViewById(R.id.textblastfreezer);
        phleveltext = (TextView) findViewById(R.id.phleveltext);
        textcore = (TextView) findViewById(R.id.textcoretemp);
        textgpscoordinates = (TextView) findViewById(R.id.gpscord);
        textgpsdate = (TextView) findViewById(R.id.gpsdate);
        textgpstime = (TextView) findViewById(R.id.gpstime);
        textprawnbasketno = (TextView) findViewById(R.id.basketno);
		
        addformdatabutton = (Button) findViewById(R.id.addformdatabutton);
        addwhaledolphinbutton = (Button) findViewById(R.id.marinesightingsbtn);
        resetbasketbutton = (Button) findViewById(R.id.resetbasketbutton);
        helpbutton = (Button) findViewById(R.id.helpbtn);
		
        try {
            checkTrip(trip_status);
			
			} catch (Exception e) {
            Log.d("Error: ", e.getMessage());
		}
		
        try {
            if (textchilledhold.getText().equals("")) {
                textgpsdate.setVisibility(View.GONE);
                textgpstime.setVisibility(View.GONE);
                textgpscoordinates.setText(R.string.availablelabel);
                textchilledhold.setText(R.string.notavailablelabel);
                textfreezerhold.setText(R.string.notavailablelabel);
                textblastfreezer.setText(R.string.notavailablelabel);
                textcore.setText(R.string.notavailablelabel);
			}
			} catch (Exception e) {
            Log.d("Error: ", e.getMessage());
		}
		
        //DisplayHandler.post(DisplayRunnable);
		
        if (logged_status.equals("logged in")) {
            navigationView.getMenu().findItem(R.id.logout).setVisible(true);
            TextView usertextView = (TextView) headerView.findViewById(R.id.usertextView);
            usertextView.setText(vessel_name);
			} else {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
		}
		
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.addHeaderView(headerView);
        addformdatabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, TemperatureFormActivity.class);
                startActivity(intent);
			}
		});
		
        addwhaledolphinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, WhaleDolphinFormActivity.class);
                startActivity(intent);
			}
		});
        mtempHandler.post(temprunnableCode);
        mconnHandler.post(connectionrunnableCode);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 10);
		
        Intent qualityreportIntent = new Intent(this, QualityReportService.class);
        qualityreportpendingIntent = PendingIntent.getService(this, 0, qualityreportIntent, 0);
		
        Intent bilgeIntent = new Intent(this, BilgeAlarmService.class);
        bilgependingIntent = PendingIntent.getService(this, 0, bilgeIntent, 0);
		
        alarmqualityreportMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmbilgecheckMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int reportquality_counter = 1440 * 60 * 1000;
        int bilge_counter = 20 * 60 * 1000;
        //alarmqualityreportMgr.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), reportquality_counter, qualityreportpendingIntent);
        //alarmbilgecheckMgr.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),bilge_counter, bilgependingIntent);
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
	
    @Override
    protected void onPause() {
        mtempHandler.removeCallbacks(temprunnableCode);
        mconnHandler.removeCallbacks(connectionrunnableCode);
        sharedPreference.save(context, TAG_BACKGROUND_ACTIVE, "yes");
        super.onPause();
	}
	
    @Override
    protected void onResume() {
        mtempHandler.post(temprunnableCode);
        mconnHandler.post(connectionrunnableCode);
        sharedPreference.save(context, TAG_BACKGROUND_ACTIVE, "no");
        super.onResume();
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
	
    private void confirmDialog(final String sendmethod) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
        builder
		.setMessage("Is your logsheet no correct?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				confclass.hideSoftKeyboard(DashboardActivity.this);
				String logresult = getlogsheetno();
				if (logresult.equals("")) {
					Toast.makeText(getApplicationContext(),
					"Please enter log sheet No", Toast.LENGTH_SHORT).show();
					} else {
					mUserTripTask = new UserTripTask(logresult, "start", "C", sendmethod);
					mUserTripTask.execute();
				}
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		})
		.show();
	}
	
    /**
		* Started trip.
	*/
    public void startedTrip() {
        logsheetlinear.setVisibility(View.GONE);
        tripstatuslinearLayout.setVisibility(View.VISIBLE);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.start_trip).setVisible(false);
        navigationView.getMenu().findItem(R.id.end_trip).setVisible(true);
		
        addformdatabutton.setEnabled(true);
        addwhaledolphinbutton.setEnabled(true);
        helpbutton.setEnabled(true);
        resetbasketbutton.setEnabled(true);
        endtripbutton.setVisibility(View.VISIBLE);
		
        triplogno = sharedPreference.getValue(context, TAG_TRIP_LOG_NO);
        tripstartdate = sharedPreference.getValue(context, TAG_TRIP_START_DATE);
        texttripstatus.setText(R.string.tripstatus);
        String dateformatted = "Start Date: " + tripstartdate;
        texttripstartdate.setText(dateformatted);
        String logsheetstatus = "Logsheet No: " + triplogno + "";
        texttriplogno.setText(logsheetstatus);
		
        if (trip_incident_record.equals("yes")) {
            navigationView.getMenu().findItem(R.id.incident_menu).setVisible(true);
		}
		
        if (trip_communication_record.equals("yes")) {
            navigationView.getMenu().findItem(R.id.communication_menu).setVisible(true);
		}
        if (trip_lost_gear_record.equals("yes")) {
            navigationView.getMenu().findItem(R.id.lost_gear_menu).setVisible(true);
		}
		
        if (trip_marine_litter_record.equals("yes")) {
            navigationView.getMenu().findItem(R.id.marine_litter_menu).setVisible(true);
		}
		
        if (trip_visitor_record.equals("yes")) {
            navigationView.getMenu().findItem(R.id.visitor_menu).setVisible(true);
		}
		
        if (trip_slippage_record.equals("yes")) {
            navigationView.getMenu().findItem(R.id.slippage_menu).setVisible(true);
		}
        if (trip_prawn_dip_record == null) {
            navigationView.getMenu().findItem(R.id.prawndip_menu).setVisible(false);
			} else if (trip_prawn_dip_record.equals("yes")) {
            navigationView.getMenu().findItem(R.id.prawndip_menu).setVisible(true);
		}
		
        if (trip_dispatch_freezer_record.equals("yes")) {
            navigationView.getMenu().findItem(R.id.dispatch_menu).setVisible(true);
		}
	}
	
    /**
		* Idle trip.
	*/
    public void idleTrip() {
        logsheetlinear.setVisibility(View.VISIBLE);
        tripstatuslinearLayout.setVisibility(View.GONE);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.start_trip).setVisible(true);
        navigationView.getMenu().findItem(R.id.end_trip).setVisible(false);
		
        navigationView.getMenu().findItem(R.id.incident_menu).setVisible(false);
        navigationView.getMenu().findItem(R.id.communication_menu).setVisible(false);
        navigationView.getMenu().findItem(R.id.lost_gear_menu).setVisible(false);
        navigationView.getMenu().findItem(R.id.marine_litter_menu).setVisible(false);
        navigationView.getMenu().findItem(R.id.visitor_menu).setVisible(false);
        navigationView.getMenu().findItem(R.id.slippage_menu).setVisible(false);
        navigationView.getMenu().findItem(R.id.prawndip_menu).setVisible(false);
        navigationView.getMenu().findItem(R.id.dispatch_menu).setVisible(false);
        addformdatabutton.setEnabled(false);
        addwhaledolphinbutton.setEnabled(false);
        helpbutton.setEnabled(true);
        resetbasketbutton.setEnabled(true);
        texttripstatus.setText(R.string.tripstatusstop);
        String dateformatted = "Start Date: ";
        texttripstartdate.setText(dateformatted);
        String logsheetstatus = "Logsheet No: ";
        texttriplogno.setText(logsheetstatus);
        endtripbutton.setVisibility(View.GONE);
	}
	
    /**
		* Check trip.
		*
		* @param status the status
	*/
    public void checkTrip(String status) {
        switch (status) {
            case "Start":
			startedTrip();
			break;
            case "Idle":
			idleTrip();
			break;
		}
	}
	
    /**
		* Gets .
		*
		* @return the
	*/
    public String getlogsheetno() {
        String logsheetno = "";
        try {
            logsheetno = logsheetedit.getText().toString();
			} catch (Exception e) {
            e.printStackTrace();
		}
        return logsheetno;
	}
	
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
		}
	}
	
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        confclass = new Configure_Class();
        // Handle navigation view item clicks here.
        int id = item.getItemId();
		
        if (id == R.id.start_trip) {
            boolean wificheck = confclass.checkWifiConnected(context);
            if (!wificheck) {
                Toast.makeText(getApplicationContext(),
				"This device doesn't have a wifi connection, please try again", Toast.LENGTH_SHORT).show();
				} else {
                try {
                    String sendmethod = checkSendMethod();
                    confirmDialog(sendmethod);
					} catch (JSONException e) {
                    e.printStackTrace();
				}
			}
			} else if (id == R.id.end_trip) {
            boolean wificheck = confclass.checkWifiConnected(context);
            if (!wificheck) {
                Toast.makeText(getApplicationContext(),
				"This device doesn't have a wifi connection, please try again", Toast.LENGTH_SHORT).show();
				} else {
                Intent intent = new Intent(DashboardActivity.this, EndTripQuestionsActivity.class);
                startActivity(intent);
			}
			} else if (id == R.id.graph_menu) {
            Intent intent = new Intent(DashboardActivity.this, GraphActivity.class);
            startActivity(intent);
			} else {
            if (id == R.id.logout) {
                killallprocesses();
                sharedPreference.save(context, TAG_SSID_REDIRECT_ACTIVE, "no");
                sharedPreference.save(context, TAG_USER, "");
                sharedPreference.save(context, "logged_status", "");
                if (readwrite.isFilePresent(context, "UserInfo.json")) {
                    try {
                        JSONObject obj = readwrite.getJSONData(context, "UserInfo.json");
						
                        obj.getJSONObject("vessel").put("User_id", "");
                        obj.getJSONObject("vessel").put("name", "");
                        obj.getJSONObject("vessel").put("username", "");
                        obj.getJSONObject("vessel").put("password", "");
                        readwrite.saveData(context, obj.toString(), "UserInfo.json");
						} catch (JSONException e) {
                        e.printStackTrace();
					}
				}
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
				} else if (id == R.id.incident_menu) {
                Intent intent = new Intent(DashboardActivity.this, IncidentFormActivity.class);
                startActivity(intent);
				} else if (id == R.id.communication_menu) {
                Intent intent = new Intent(DashboardActivity.this, CommunicationFormActivity.class);
                startActivity(intent);
				} else if (id == R.id.marine_litter_menu) {
                Intent intent = new Intent(DashboardActivity.this, MarineLitterFormActivity.class);
                startActivity(intent);
				} else if (id == R.id.visitor_menu) {
                Intent intent = new Intent(DashboardActivity.this, VisitorFormActivity.class);
                startActivity(intent);
				} else if (id == R.id.slippage_menu) {
                Intent intent = new Intent(DashboardActivity.this, SlippageFormActivity.class);
                startActivity(intent);
				} else if (id == R.id.prawndip_menu) {
                Intent intent = new Intent(DashboardActivity.this, PrawnDipFormActivity.class);
                startActivity(intent);
				} else if (id == R.id.dispatch_menu) {
                Intent intent = new Intent(DashboardActivity.this, DispatchFormActivity.class);
                startActivity(intent);
				} else if (id == R.id.temptable_menu) {
                Intent intent = new Intent(DashboardActivity.this, TemperatureTableActivity.class);
                startActivity(intent);
				} else if (id == R.id.quality_report_menu) {
                Intent intent = new Intent(DashboardActivity.this, QualityReportActivity.class);
                startActivity(intent);
				} else if (id == R.id.lost_gear_menu) {
                Intent intent = new Intent(DashboardActivity.this, LostGearActivity.class);
                startActivity(intent);
			}
		}
		
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
	}
	
    /**
		* Killallprocesses.
	*/
    public void killallprocesses() {
        alarmqualityreportMgr.cancel(qualityreportpendingIntent);
        alarmbilgecheckMgr.cancel(bilgependingIntent);
	}
	
    /**
		* Gets send method.
		*
		* @return the send method
		* @throws JSONException the json exception
	*/
    public String getSendMethod() throws JSONException {
        String returntype = "";
        boolean rockblock_status;
        Read_Write_Class readwrite = new Read_Write_Class();
        boolean onlinestatus = confclass.isConnectedToInternet();
        if (readwrite.isFilePresent(context, "MAIN_config.json")) {
            JSONObject masterfilejs = readwrite.getJSONData(context, "MAIN_config.json");
            String rockblock_setting = masterfilejs.getJSONObject("vessel").getJSONObject("settings").getString("rock_block");
            Log.d("JSON rb display", rockblock_setting);
            rockblock_status = !rockblock_setting.equals("") && !rockblock_setting.equals("null");
            Log.d("JSON rb bool", String.valueOf(rockblock_status));
            if (rockblock_status) {
                returntype = "rockblock";
				} else {
                returntype = "internet";
			}
			} else {
			
		}
        return returntype;
	}
	
    /**
		* Check send method string.
		*
		* @return the string
		* @throws JSONException the json exception
	*/
    public String checkSendMethod() throws JSONException {
        String returntype = "";
        boolean rockblock_status;
        Read_Write_Class readwrite = new Read_Write_Class();
        boolean onlinestatus = confclass.isConnectedToInternet();
        if (readwrite.isFilePresent(context, "MAIN_config.json")) {
            JSONObject masterfilejs = readwrite.getJSONData(context, "MAIN_config.json");
            String rockblock_setting = masterfilejs.getJSONObject("vessel").getJSONObject("settings").getString("rock_block");
            Log.d("JSON rb display", rockblock_setting);
            rockblock_status = !rockblock_setting.equals("") && !rockblock_setting.equals("null");
            Log.d("JSON rb bool", String.valueOf(rockblock_status));
            if (rockblock_status) {
                returntype = "rockblock";
				} else if (onlinestatus) {
                returntype = "internet";
				} else {
                returntype = "log";
			}
		}
        return returntype;
	}
	
    /**
		* The type Get Connection Status task.
	*/
    public class GetConnectionStatusTask extends AsyncTask<Void, Void, JSONObject> {
        /**
			* The Context.
		*/
		//Using the Activity Functions inside external and Internal Functions
        InsertDB_Class insertdb;
		
        /**
			* Instantiates a new Get connection status task.
		*/
        public GetConnectionStatusTask() {
            readwrite = new Read_Write_Class();
            insertdb = new InsertDB_Class();
            confclass = new Configure_Class();
            sharedPreference = new SharedPreference();
		}
		
        @Override
        protected JSONObject doInBackground(Void... args) {
            JSONObject json = new JSONObject();
            try {
				
                String current_maria_connection = "no";
                String current_wifi_connection = "no";
                boolean wificheck = confclass.checkWifiConnected(context);
                if (wificheck) {
                    current_wifi_connection = "yes";
				}
				
                String internet_connect = confclass.isInternetconnected(context) ? "yes" : "no";
				
                String wifi_connection = sharedPreference.getValue(context, TAG_WIFI_CONNECTION);
                String internet_connection = sharedPreference.getValue(context, TAG_MARIA_CONNECTION);
				
                sharedPreference.save(context, TAG_MARIA_CONNECTION, internet_connect);
                sharedPreference.save(context, TAG_WIFI_CONNECTION, current_wifi_connection);
                json.put("current_maria_connection", internet_connect);
                json.put("maria_connection", internet_connection);
                json.put("current_wifi_connection", current_wifi_connection);
                json.put("wifi_connection", wifi_connection);
                return json;
				} catch (Exception e) {
                e.printStackTrace();
			}
			
            return null;
		}
		
        @Override
        protected void onPostExecute(JSONObject json) {
            mGetTempTask = null;
			
            if (json != null) {
                try {
                    String current_maria_connection = json.getString("current_maria_connection");
                    String maria_connection = json.getString("maria_connection");
                    String current_wifi_connection = json.getString("current_wifi_connection");
                    String wifi_connection = json.getString("wifi_connection");
					
                    if (current_maria_connection.equals("yes")) {
                        currentmariaimage.setBackgroundResource(R.drawable.ic_wifi_black_24dp);
						} else {
                        currentmariaimage.setBackgroundResource(R.drawable.ic_perm_scan_wifi_black_24dp);
					}
					
                    if (current_wifi_connection.equals("yes")) {
                        currentwifiimage.setBackgroundResource(R.drawable.ic_wifi_black_24dp);
						} else {
                        currentwifiimage.setBackgroundResource(R.drawable.ic_perm_scan_wifi_black_24dp);
					}
					
					} catch (JSONException e) {
                    e.printStackTrace();
				}
				} else {
				
			}
		}
		
        @Override
        protected void onCancelled() {
		}
	}
	
    /**
		* The type User sync data task.
	*/
    public class UserSyncDataTask extends AsyncTask<Void, Void, Boolean> {
        private static final String TAG_SUCCESS = "success";
        /**
			* The Db helper.
		*/
        DatabaseHelper dbHelper;
        /**
			* The Insertdb.
		*/
        InsertDB_Class insertdb;
        private ProgressDialog dialog;
		
        /**
			* Instantiates a new User sync data task.
		*/
        UserSyncDataTask() {
            dbHelper = new DatabaseHelper(context);
            dialog = new ProgressDialog(context);
            insertdb = new InsertDB_Class();
		}
		
        @Override
        protected void onPreExecute() {
            dialog.setMessage("Syncing your data with the server, please wait.");
            dialog.show();
		}
		
        @Override
        protected Boolean doInBackground(Void... args) {
            boolean wificheck = confclass.checkWifiConnected(context);
            String onlinestatus = null;
            try {
                onlinestatus = confclass.isConnectedToInternet() ? "yes" : "no";
				} catch (Exception e) {
                e.printStackTrace();
			}
			
            if (!wificheck) {
                JSONObject json = new JSONObject();
                try {
                    json.put("success", 3);
                    json.put("error", "Turn your WiFi on or connect device to a network");
					} catch (JSONException e) {
                    e.printStackTrace();
				}
                return false;
				} else {
                try {
                    JSONArray templist = new JSONArray();
                    JSONArray whalelist = new JSONArray();
                    JSONArray incidentlist = new JSONArray();
                    JSONArray communicationlist = new JSONArray();
                    JSONArray marinelist = new JSONArray();
                    JSONArray visitorlist = new JSONArray();
                    JSONArray slippagelist = new JSONArray();
                    JSONArray dispatchlist = new JSONArray();
                    JSONArray lostgearlist = new JSONArray();
                    JSONArray prawndiplist = new JSONArray();
                    JSONArray triplist = new JSONArray();
                    JSONObject tripobj = new JSONObject();
                    int list_count = 0;
                    int list_sent = 0;
                    if (onlinestatus.equals("yes")) {
                        int endedtripcount = dbHelper.getTripRecordByEndDateCount(user_id);
						
                        if (endedtripcount > 0) {
                            list_count = endedtripcount;
                            List<String> tripclassId = dbHelper.getAllEndedTripRecords(user_id);
                            for (int i = 0; i < tripclassId.size(); i++) {
                                String trip_id = tripclassId.get(i);
								
                                int tempcount = dbHelper.getTemperatureTripRecordCount(trip_id);
                                int whalecount = dbHelper.getWhaleDolphinTripRecordsCount(trip_id);
                                int incidentcount = dbHelper.getIncidentTripRecordsCount(trip_id);
                                int commcount = dbHelper.getCommunicationTripRecordsCount(trip_id);
                                int marinecount = dbHelper.getMarineLitterTripRecordsCount(trip_id);
                                int visitorcount = dbHelper.getVisitorTripRecordsCount(trip_id);
                                int slippagecount = dbHelper.getSlippageTripRecordCount(trip_id);
                                int dispatchcount = dbHelper.getDispatchTripRecordsCount(trip_id);
                                int lostgearcount = dbHelper.getLostGearRTripRecordsCount(trip_id);
                                int prawndipcount = dbHelper.getDPrawnDipRecordsByTripCount(trip_id);
                                int tripcount = dbHelper.getTripRecordByIDCount(trip_id);
								
                                if (tempcount > 0) {
                                    List<Temperature_Class> temp = dbHelper.getAllCurrentTripTemperatureRecords(trip_id);
                                    for (int l = 0; l < temp.size(); l++) {
                                        templist.put(temp.get(l).getJSONObject());
									}
                                    try {
                                        tripobj.put("temperature_record", templist);
										} catch (JSONException e) {
                                        e.printStackTrace();
									}
								}
								
                                if (whalecount > 0) {
                                    List<WhaleDolphin_Class> wrecord = dbHelper.getAllCurrentTripWhaleDolphinRecords(trip_id);
                                    for (int k = 0; k < wrecord.size(); k++) {
                                        whalelist.put(wrecord.get(k).getJSONObject());
									}
                                    try {
                                        tripobj.put("whale_dolphin_record", whalelist);
										} catch (JSONException e) {
                                        e.printStackTrace();
									}
								}
								
                                if (incidentcount > 0) {
                                    List<Incident_Class> irecord = dbHelper.getAllCurrentTripIncidentRecords(trip_id);
                                    for (int j = 0; j < irecord.size(); j++) {
                                        incidentlist.put(irecord.get(j).getJSONObject());
									}
                                    try {
                                        tripobj.put("incident_record", incidentlist);
										} catch (JSONException e) {
                                        e.printStackTrace();
									}
								}
								
                                if (commcount > 0) {
                                    List<Communication_Class> crecord = dbHelper.getAllCurrentTripCommunicationRecords(trip_id);
                                    for (int h = 0; h < crecord.size(); h++) {
                                        communicationlist.put(crecord.get(h).getJSONObject());
									}
                                    try {
                                        tripobj.put("communication_record", communicationlist);
										} catch (JSONException e) {
                                        e.printStackTrace();
									}
								}
								
                                if (marinecount > 0) {
                                    List<Marine_Litter_Class> mrecord = dbHelper.getAllCurrentTripMarineRecords(trip_id);
                                    for (int g = 0; g < mrecord.size(); g++) {
                                        marinelist.put(mrecord.get(g).getJSONObject());
									}
                                    try {
                                        tripobj.put("marine_litter_record", marinelist);
										} catch (JSONException e) {
                                        e.printStackTrace();
									}
								}
								
                                if (visitorcount > 0) {
                                    List<Visitor_Class> vrecord = dbHelper.getAllCurrentTripVisitorRecords(trip_id);
                                    for (int f = 0; f < vrecord.size(); f++) {
                                        visitorlist.put(vrecord.get(f).getJSONObject());
									}
                                    try {
                                        tripobj.put("visitor_record", visitorlist);
										} catch (JSONException e) {
                                        e.printStackTrace();
									}
								}
								
                                if (slippagecount > 0) {
                                    List<Slippage_Class> srecord = dbHelper.getAllCurrentTripSlippageRecords(trip_id);
                                    for (int e = 0; e < srecord.size(); e++) {
                                        slippagelist.put(srecord.get(e).getJSONObject());
									}
                                    try {
                                        tripobj.put("slippage_record", slippagelist);
										} catch (JSONException e) {
                                        e.printStackTrace();
									}
								}
								
                                if (dispatchcount > 0) {
                                    List<Dispatch_Class> drecord = dbHelper.getAllCurrentTripDispatchRecords(trip_id);
                                    for (int d = 0; d < drecord.size(); d++) {
                                        dispatchlist.put(drecord.get(d).getJSONObject());
									}
                                    try {
                                        tripobj.put("dispatch_record", dispatchlist);
										} catch (JSONException e) {
                                        e.printStackTrace();
									}
								}
								
                                if (lostgearcount > 0) {
                                    List<Lost_Gear_Class> lorecord = dbHelper.getAllCurrentTripLostGearRecords(trip_id);
                                    for (int c = 0; c < lorecord.size(); c++) {
                                        lostgearlist.put(lorecord.get(c).getJSONObject());
									}
                                    try {
                                        tripobj.put("lost_gear_record", lostgearlist);
										} catch (JSONException e) {
                                        e.printStackTrace();
									}
								}
								
                                if (prawndipcount > 0) {
                                    List<Prawn_Dip_Class> pdrecord = dbHelper.getAllCurrentTripPrawnDipRecords(trip_id);
                                    for (int b = 0; b < pdrecord.size(); b++) {
                                        prawndiplist.put(pdrecord.get(b).getJSONObject());
									}
                                    try {
                                        tripobj.put("prawn_dip_record", prawndiplist);
										} catch (JSONException e) {
                                        e.printStackTrace();
									}
								}
								
                                if (tripcount > 0) {
                                    List<Trip_Class> trecord = dbHelper.getAllCurrentTripRecords(trip_id);
                                    for (int a = 0; a < trecord.size(); a++) {
                                        triplist.put(trecord.get(a).getJSONObject());
									}
                                    try {
                                        tripobj.put("trip_record", triplist);
										} catch (JSONException e) {
                                        e.printStackTrace();
									}
								}
								
                                String LOGIN_URL = "https://login.marineapps.net/api_check.php";
                                HashMap<String, String> params = new HashMap<>();
                                params.put("system_request", "upload_sync_data");
                                params.put("trip_data", tripobj.toString());
                                JSONObject json = insertdb.insertPOSTRecordOnline(LOGIN_URL, params);
								
                                if (json != null) {
                                    Log.d("JSON return", json.toString());
                                    int sentstatus = 0;
                                    try {
                                        sentstatus = json.getInt(TAG_SUCCESS);
                                        if (sentstatus == 1) {
                                            if (tempcount > 0) {
                                                dbHelper.deleteAllTripTemperatureRecords(trip_id);
											}
											
                                            if (whalecount > 0) {
                                                dbHelper.deleteAllTripWhaleDolphinRecords(trip_id);
											}
											
                                            if (incidentcount > 0) {
                                                dbHelper.deleteAllTripIncidentRecords(trip_id);
											}
											
                                            if (commcount > 0) {
                                                dbHelper.deleteAllTripCommunicationRecords(trip_id);
											}
											
                                            if (marinecount > 0) {
                                                dbHelper.deleteAllTripMarineRecords(trip_id);
											}
											
                                            if (visitorcount > 0) {
                                                dbHelper.deleteAllTripVisitorRecords(trip_id);
											}
											
                                            if (slippagecount > 0) {
                                                dbHelper.deleteAllTripSlippageRecords(trip_id);
											}
											
                                            if (dispatchcount > 0) {
                                                dbHelper.deleteAllTripDispatchRecords(trip_id);
											}
											
                                            if (lostgearcount > 0) {
                                                dbHelper.deleteAllTripLostGearRecords(trip_id);
											}
											
                                            if (prawndipcount > 0) {
                                                dbHelper.deleteAllTripPrawnDipRecords(trip_id);
											}
											
                                            if (tripcount > 0) {
                                                dbHelper.deleteTripbyID(trip_id);
											}
                                            list_sent++;
										}
										} catch (JSONException e) {
                                        e.printStackTrace();
									}
								}
							}
                            return list_count == list_sent;
						}
					}
					} catch (Exception e) {
                    e.printStackTrace();
				}
			}
			
            return null;
		}
		
        @Override
        protected void onPostExecute(Boolean result) {
            mUserSyncDataTask = null;
            dialog.dismiss();
            if (result) {
                Toast.makeText(getApplicationContext(),
				"Data has been synced", Toast.LENGTH_LONG).show();
			}
		}
		
        @Override
        protected void onCancelled() {
            mUserSyncDataTask = null;
		}
	}
	
    /**
		* The type User trip task.
	*/
    public class UserTripTask extends AsyncTask<Void, Void, JSONObject> {
        private static final String TAG_SUCCESS = "success";
        private final String mtripsendtype;
        private final String mtriplogsheet;
        private final String mtriptype;
        /**
			* The Db helper.
		*/
        DatabaseHelper dbHelper;
        /**
			* The Readwrite.
		*/
        Read_Write_Class readwrite;
        /**
			* The Insertdb.
		*/
        InsertDB_Class insertdb;
        private ProgressDialog dialog;
		
        /**
			* Instantiates a new User trip task.
			*
			* @param triplog    the triplog
			* @param tripstatus the tripstatus
			* @param triptype   the triptype
			* @param sendtype   the sendtype
		*/
        UserTripTask(String triplog, String tripstatus, String triptype, String sendtype) {
            mtriplogsheet = triplog;
            mtriptype = triptype;
            mtripsendtype = sendtype;
            readwrite = new Read_Write_Class();
            insertdb = new InsertDB_Class();
            confclass = new Configure_Class();
            dbHelper = new DatabaseHelper(context);
            dialog = new ProgressDialog(context);
		}
		
        @Override
        protected void onPreExecute() {
            String displaytext = mtripsendtype.equals("internet") ? "Starting the trip and Sending data through wifi" : "Starting the trip and Sending data through Satellite";
			
            dialog.setMessage(displaytext + ", please wait.");
            dialog.show();
		}
		
        @Override
        protected JSONObject doInBackground(Void... args) {
            String returnstatus = "";
            ArrayList<String> triplist = new ArrayList<>();
            boolean pingCheck = sharedPreference.getValue(context, TAG_DATABASE_CONNECTION) != null && confclass.pingURL("http://" + sharedPreference.getValue(context, TAG_DATABASE_CONNECTION) + "/config.php", 1000);
            try {
                Log.d("JSON sendmethod", mtripsendtype);
                String user_id = sharedPreference.getValue(context, TAG_USER);
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
                String strDate = sdf.format(c.getTime());
                SimpleDateFormat dbsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
				
                String dbstrDate = dbsdf.format(c.getTime());
                String onlineid = "";
                Date date = dbsdf.parse(dbstrDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.HOUR, 24);
                String quality_check = dbsdf.format(calendar.getTime());
                String internet_connect = confclass.isInternetconnected(context) ? "yes" : "no";
                Log.d("JSON net stat", internet_connect);
                sharedPreference.save(context, TAG_TRIP_STATUS, "Start");
                sharedPreference.save(context, TAG_TRIP_LOG_NO, mtriplogsheet);
                sharedPreference.save(context, TAG_TRIP_START_DATE, strDate);
				
                if (internet_connect.equals("yes")) {
                    String LOGIN_URL = "https://login.marineapps.net/api_check.php";
                    HashMap<String, String> onlineparams = new HashMap<>();
                    onlineparams.put("system_request", "start_trip");
                    onlineparams.put("start_date", dbstrDate);
                    onlineparams.put("userid", user_id);
                    onlineparams.put("triplog", mtriplogsheet);
                    onlineparams.put("trip_type", mtriptype);
                    onlineparams.put("device_type", "A");
                    JSONObject onlinejson = insertdb.insertPOSTRecordOnline(LOGIN_URL, onlineparams);
                    if (onlinejson != null) {
                        Log.d("JSON onl result", onlinejson.toString());
                        int osuccess = onlinejson.getInt(TAG_SUCCESS);
                        if (osuccess == 1) {
                            String online_trip_id = onlinejson.getString("trip_id");
                            onlineid = online_trip_id;
                            int newtripid = insertdb.TripRecordInsertDB(dbstrDate, user_id, mtriplogsheet, "C", "A", onlineid, context);
                            int tripcount = dbHelper.getTripRecordByIDCount(String.valueOf(newtripid));
                            sharedPreference.save(context, TAG_TRIPID, Integer.toString(newtripid));
                            Log.d("JSON trip count", String.valueOf(tripcount));
                            Log.d("JSON new trip", String.valueOf(newtripid));
                            onlinejson.put("online_trip_id", online_trip_id);
                            boolean newupdatetrip = insertdb.UpdateTripIDDB(context, Integer.toString(newtripid), online_trip_id);
                            if (readwrite.isFilePresent(context, "trip_Information.json")) {
                                String results = readwrite.getData(context, "trip_Information.json");
                                JSONObject obj = new JSONObject(results);
                                try {
                                    obj.getJSONObject("trip").put("trip_id", Integer.toString(newtripid));
                                    obj.getJSONObject("trip").put("online_trip_id", online_trip_id);
                                    obj.getJSONObject("trip").put("start_trip_date", dbstrDate);
                                    obj.getJSONObject("trip").put("end_trip_date", "");
                                    obj.getJSONObject("trip").put("log_sheet_text", mtriplogsheet);
                                    obj.getJSONObject("trip").put("current_status", "Start");
                                    readwrite.saveData(context, obj.toString(), "trip_Information.json");
									} catch (JSONException e) {
                                    e.printStackTrace();
								}
							}
						}
                        return onlinejson;
					}
					} else {
                    int newtripid = insertdb.TripRecordInsertDB(dbstrDate, user_id, mtriplogsheet, "C", "A", onlineid, context);
                    int tripcount = dbHelper.getTripRecordByIDCount(String.valueOf(newtripid));
                    sharedPreference.save(context, TAG_TRIPID, Integer.toString(newtripid));
                    Log.d("JSON trip count", String.valueOf(tripcount));
                    Log.d("JSON new trip", String.valueOf(newtripid));
                    if (readwrite.isFilePresent(context, "trip_Information.json")) {
                        String results = readwrite.getData(context, "trip_Information.json");
                        JSONObject obj = new JSONObject(results);
                        try {
                            obj.getJSONObject("trip").put("trip_id", Integer.toString(newtripid));
                            obj.getJSONObject("trip").put("start_trip_date", dbstrDate);
                            obj.getJSONObject("trip").put("end_trip_date", "");
                            obj.getJSONObject("trip").put("log_sheet_text", mtriplogsheet);
                            obj.getJSONObject("trip").put("current_status", "Start");
                            readwrite.saveData(context, obj.toString(), "trip_Information.json");
							} catch (JSONException e) {
                            e.printStackTrace();
						}
					}
                    try {
                        returnstatus = "Unable to connect the Internet";
                        JSONObject json = new JSONObject();
                        json.put("success", 2);
                        json.put("error", returnstatus);
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
            mUserTripTask = null;
            dialog.dismiss();
            String trip_id = "";
            String trip_start_date = "";
			
            if (json != null) {
                Log.d("JSON Trip data", String.valueOf(json));
                try {
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("JSON New Trip", sharedPreference.getValue(context, TAG_TRIPID));
                        trip_id = json.getString(TAG_TRIPID);
                        trip_start_date = json.getString("start_date");
                        if (json.has("display_message")) {
                            String display_message = json.getString("display_message");
                            if (!display_message.equals("")) {
                                Toast.makeText(getApplicationContext(),
								display_message, Toast.LENGTH_SHORT).show();
							}
						}
						
                        if (json.has("start_online")) {
                            Log.d("JSON Trip online id", json.getString("online_trip_id"));
                            String online_trip_id = json.getString("online_trip_id");
                            sharedPreference.save(context, TAG_TRIP_ONLINE_ID, online_trip_id);
						}
						
                        logsheetlinear.setVisibility(View.GONE);
                        tripstatuslinearLayout.setVisibility(View.VISIBLE);
                        startedTrip();
						} else if (success == 2) {
                        trip_id = json.getString(TAG_TRIPID);
                        trip_start_date = json.getString("start_date");
                        if (json.has("display_message")) {
                            String display_message = json.getString("display_message");
                            if (!display_message.equals("")) {
                                Toast.makeText(getApplicationContext(),
								display_message, Toast.LENGTH_SHORT).show();
							}
						}
						
                        Log.d("JSON New Trip", trip_id);
						
                        if (json.has("start_online")) {
                            String online_trip_id = json.getString("online_trip_id");
                            sharedPreference.save(context, TAG_TRIP_ONLINE_ID, online_trip_id);
						}
						
                        logsheetlinear.setVisibility(View.GONE);
                        tripstatuslinearLayout.setVisibility(View.VISIBLE);
                        startedTrip();
						} else if (success == 0) {
                        if (json.has("display_message")) {
                            String display_message = json.getString("display_message");
                            if (!display_message.equals("")) {
                                Toast.makeText(getApplicationContext(),
								display_message, Toast.LENGTH_SHORT).show();
							}
							} else {
                            Toast.makeText(getApplicationContext(),
							"problem occurred", Toast.LENGTH_SHORT).show();
						}
						} else if (success == 3) {
                        String ErrorString = json.getString("error");
                        Toast.makeText(getApplicationContext(),
						ErrorString, Toast.LENGTH_SHORT).show();
						} else if (success == 4) {
                        if (json.has("display_message")) {
                            String display_message = json.getString("display_message");
                            if (!display_message.equals("")) {
                                Toast.makeText(getApplicationContext(),
								display_message, Toast.LENGTH_SHORT).show();
							}
						}
					}
					} catch (JSONException e) {
                    e.printStackTrace();
				}
			}
		}
		
        @Override
        protected void onCancelled() {
            mUserTripTask = null;
		}
	}
	
    /**
		* The type Get temperature task.
	*/
    public class GetTemperatureTask extends AsyncTask<Void, Void, JSONObject> {
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_TEMPERATURE = "temperature";
        private static final String TAG_DATE = "date";
        private static final String TAG_TIME = "time";
        private static final String TAG_DATETIME = "datetime";
        private static final String TAG_GPS = "gps";
        private static final String TAG_BILGE = "bilge";
        private static final String TAG_PRAWN_TANK = "prawn_tank";
        private static final String TAG_PRAWNDIP = "prawn_counter";
        private static final String TAG_PHLEVEL = "ph_level";
        private static final String TAG_PHLEVEL_FROM = "ph_level_from";
        private static final String TAG_PHLEVEL_TO = "ph_level_to";
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
			* Instantiates a new Get temperature task.
		*/
        public GetTemperatureTask() {
            readwrite = new Read_Write_Class();
            insertdb = new InsertDB_Class();
            confclass = new Configure_Class();
            sharedPreference = new SharedPreference();
            dbHelper = new DatabaseHelper(context);
		}
		
        @Override
        protected JSONObject doInBackground(Void... args) {
			String inputvariables = "";
			JSONObject itemobj = new JSONObject();
            try {
				if (sharedPreference.getValue(context, TAG_TEMPERATURE_VARIABLES) != null) {
					inputvariables = sharedPreference.getValue(context, TAG_TEMPERATURE_VARIABLES);
					String[] items = inputvariables.split(";");
					for (String item : items)
					{
						String resulta = sharedPreference.getValue(context, item);
						try {
							itemobj.put(item, resulta);
						}
						catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
                String current_user_id = sharedPreference.getValue(context, TAG_USER);
                Log.d("JSON User", current_user_id);
                String url_address = "https://login.marineapps.net/episensor_api.php";
                HashMap<String, String> params = new HashMap<>();
                params.put("system_request", "get_display_episensor");
                params.put("user_id", current_user_id);
                params.put("item_variables", inputvariables);
                params.put("json_variables", itemobj.toString());
				
				
                JSONObject json = insertdb.insertPOSTRecordOnline(url_address, params);
				
                if (json != null) {
                    Log.d("JSON FF", json.toString());
					
                    return json;
				}
				
				} catch (Exception e) {
                e.printStackTrace();
			}
			
            return null;
		}
		
        @Override
        protected void onPostExecute(JSONObject json) {
            mGetTempTask = null;
			
            if (json != null) {
                Thread t = new Thread() {
                    public void run() {
                        try {
                            int childcount = TempTableLayout.getChildCount();
                            if (childcount > 0) {
                                TempTableLayout.removeAllViewsInLayout();
							}
							} catch (Exception ex) {
                            ex.printStackTrace();
						}
					}
				};
                t.start();
				
                try {
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        JSONArray tempArray = json.getJSONArray(TAG_TEMPERATURE);
                        //JSONArray bilgeArray = json.getJSONArray(TAG_BILGE);
                        String bilge_exists = json.getString("bilge_exists");
                        String prawn_level_exists = json.getString("prawn_level_exists");
                        String ph_level_exists = json.getString("ph_level_exists");
                        String prawn_counter_exists = json.getString("prawn_counter_exists");
						
                        String timestampdate = json.getString(TAG_DATE);
                        String timestamptime = json.getString(TAG_TIME);
                        String gps = json.getString(TAG_GPS);
                        String ph_sensor_level = json.getString(TAG_PHLEVEL);
                        String ph_sensor_level_from = json.getString(TAG_PHLEVEL_FROM);
                        String ph_sensor_level_to = json.getString(TAG_PHLEVEL_TO);
                        phleveltext.setText(ph_sensor_level);
                        String prawndip = json.getString(TAG_PRAWNDIP);
                        String fullDateTime = json.getString(TAG_DATETIME);
                        int childcount = TempTableLayout.getChildCount();
                        if (tempArray.length() > 0) {
                            for (int i = 0; i < childcount; i++) {
                                if (childcount > 0) {
                                    View child = TempTableLayout.getChildAt(i);
                                    if (child instanceof TableRow)
									((ViewGroup) child).removeAllViews();
								}
							}
							
                            if (childcount > 0) {
                                TempTableLayout.removeAllViewsInLayout();
							}
                            for (int j = 0; j < tempArray.length(); j++) {
                                JSONObject jtempobj = tempArray.getJSONObject(j);
                                String jsonname = jtempobj.getString("name");
                                String jsonvalue = jtempobj.getString("value");
								
                                TableRow tbrow = new TableRow(context);
                                final float scale = getResources().getDisplayMetrics().density;
                                TableLayout.LayoutParams tableRowParams =
								new TableLayout.LayoutParams
								(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT);
								
                                int leftMargin = 0;
                                int topMargin = 15;
                                int rightMargin = 0;
                                int bottomMargin = 10;
								
                                tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
                                tbrow.setPadding(0, 15, 0, 15);
                                tbrow.setBackgroundColor(Color.parseColor("#f2f2f2f2"));
                                tbrow.setLayoutParams(tableRowParams);
                                TextView tv0 = new TextView(DashboardActivity.this);
                                TableRow.LayoutParams lParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                                lParams.setMargins(45, 0, 0, 0);
                                tv0.setLayoutParams(lParams);
                                tv0.setTextSize(22);
								
                                tv0.setText(jsonname);
                                tv0.setTextColor(Color.BLACK);
                                tv0.setGravity(Gravity.LEFT);
                                tv0.setTypeface(Typeface.DEFAULT_BOLD);
                                tbrow.addView(tv0);
								
                                TextView tv1 = new TextView(DashboardActivity.this);
                                TableRow.LayoutParams lParams1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                                lParams1.setMargins(0, 0, 40, 0);
                                tv1.setLayoutParams(lParams1);
                                tv1.setText(jsonvalue);
                                tv1.setTextSize(22);
                                tv1.setTextColor(Color.BLACK);
                                tv1.setGravity(Gravity.END);
                                tv1.setTypeface(Typeface.DEFAULT_BOLD);
                                tbrow.addView(tv1);
                                TempTableLayout.addView(tbrow);
							}
						}
						/*
							for (int j = 0; j < bilgeArray.length(); j++) {
							JSONObject jbilgeobj = bilgeArray.getJSONObject(j);
							String jsonbname = jbilgeobj.getString("name");
							String jsonbvalue = jbilgeobj.getString("value");
							String jsonbdirection = jbilgeobj.getString("direction");
							
							TableRow bbrow = new TableRow(context);
							final float scale = getResources().getDisplayMetrics().density;
							TableLayout.LayoutParams tableRowParams =
							new TableLayout.LayoutParams
							(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT);
							
							int leftMargin = 0;
							int topMargin = 20;
							int rightMargin = 0;
							int bottomMargin = 20;
							
							tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
							bbrow.setPadding(0, 15, 0, 15);
							bbrow.setBackgroundColor(Color.parseColor("#f2f2f2f2"));
							bbrow.setLayoutParams(tableRowParams);
							TextView bv0 = new TextView(DashboardActivity.this);
							TableRow.LayoutParams lParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
							lParams.setMargins(45, 0, 0, 0);
							bv0.setLayoutParams(lParams);
							bv0.setTextSize(22);
							
							bv0.setText(jsonbname);
							bv0.setTextColor(Color.BLACK);
							bv0.setGravity(Gravity.LEFT);
							bv0.setTypeface(Typeface.DEFAULT_BOLD);
							bbrow.addView(bv0);
							
							ImageView biv = new ImageView(DashboardActivity.this);
							if (confclass.isNumeric(jsonbvalue)) {
							int bilgeval = Integer.parseInt(jsonbvalue);
							if(jsonbdirection.equals("normal"))
							{
							if (bilgeval <= 2) {
							biv.setImageResource(R.drawable.correct);
							} else {
							biv.setImageResource(R.drawable.error);
							}
							}
							else
							{
							if (bilgeval >= 5) {
							biv.setImageResource(R.drawable.correct);
							} else {
							biv.setImageResource(R.drawable.error);
							}
							}
							} else {
							biv.setImageResource(R.drawable.error);
							Toast.makeText(getApplicationContext(),
							"There is a problem with the bilge alarm. please have a look at it", Toast.LENGTH_SHORT).show();
							}
							TableLayout.LayoutParams tableblRowParams =
							new TableLayout.LayoutParams
							(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT);
							tableblRowParams.gravity = Gravity.END;
							biv.setLayoutParams(tableblRowParams);
							bbrow.addView(biv);
							bl.addView(bbrow);
							}
						*/
						
                        textgpstime.setText(timestamptime);
                        textgpsdate.setText(timestampdate);
                        textgpscoordinates.setText(gps);
                        textgpsdate.setVisibility(View.VISIBLE);
                        textgpstime.setVisibility(View.VISIBLE);
                        String bilge = json.getString(TAG_BILGE);
                        if (bilge_exists.equals("yes")) {
                            if (bilge.equals("good")) {
                                bilgeimage.setBackgroundResource(R.drawable.goodbtn);
								} else {
                                bilgeimage.setBackgroundResource(R.drawable.fullbtn);
							}
							} else {
                            bilgerow.setVisibility(View.GONE);
						}
						
                        if (prawn_level_exists.equals("yes")) {
                            String prawntank = json.getString(TAG_PRAWN_TANK);
                            if (prawntank.equals("good")) {
                                prawnimage.setBackgroundResource(R.drawable.goodbtn);
								} else {
                                prawnimage.setBackgroundResource(R.drawable.lowbtn);
							}
							} else {
                            tankonerow.setVisibility(View.GONE);
						}
						
                        if (prawn_counter_exists.equals("yes")) {
                            textprawnbasketno.setText(prawndip);
							} else {
                            pbasketrow.setVisibility(View.GONE);
						}
						
                        if (ph_level_exists.equals("yes")) {
                            float phlevel = Float.parseFloat(ph_sensor_level);
                            float phlevel_from = Float.parseFloat(ph_sensor_level_from);
                            float phlevel_to = Float.parseFloat(ph_sensor_level_to);
                            if (phlevel >= phlevel_from && phlevel <= phlevel_to) {
                                phsensorimage.setBackgroundResource(R.drawable.goodbtn);
								} else if (phlevel < phlevel_from) {
                                phsensorimage.setBackgroundResource(R.drawable.lowbtn);
								} else if (phlevel > phlevel_to) {
                                phsensorimage.setBackgroundResource(R.drawable.highbtn);
								} else {
                                phsensorimage.setBackgroundResource(R.drawable.lowbtn);
							}
							} else {
                            phlevelrow.setVisibility(View.GONE);
						}
						
						} else {
                        Toast.makeText(getApplicationContext(),
						"Failure to get current temperatures", Toast.LENGTH_SHORT).show();
					}
					
					} catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
				}
			}
		}
		
        @Override
        protected void onCancelled() {
            mGetTempTask = null;
		}
	}
}