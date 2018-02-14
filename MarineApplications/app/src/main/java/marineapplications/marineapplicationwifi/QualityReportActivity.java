package marineapplications.marineapplicationwifi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * The type Quality report activity.
 */
public class QualityReportActivity extends AppCompatActivity {
    private static final String TAG_USER = "user_id";
    private static final String TAG_DATABASE_CONNECTION = "current_database_connection";
    /**
     * The Ll.
     */
    TableLayout ll;
    /**
     * The Context.
     */
    Activity context = this;
    /**
     * The Qualityid.
     */
    String qualityid = "";
    /**
     * The User id.
     */
    String user_id = "";
    /**
     * The Shared preference.
     */
    SharedPreference sharedPreference;
    private UserQualityTask mQualityTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.temptoolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle(R.string.qualityreportlabel);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QualityReportActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        sharedPreference = new SharedPreference();
        user_id = sharedPreference.getValue(context, TAG_USER);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.i("dd", "Extra:" + extras.getString("quality_report_id"));
            qualityid = extras.getString("quality_report_id");
            Toast.makeText(getApplicationContext(),
                    extras.getString("quality_report_id"), Toast.LENGTH_LONG).show();
        } else {
            qualityid = "-1";
        }

        ll = (TableLayout) findViewById(R.id.qualitytable);
        mQualityTask = new UserQualityTask(qualityid);
        mQualityTask.execute();
    }

    private String getSpecies(int speciesint) {
        String species = "";

        switch (speciesint) {
            case 1:
                species = "Cod";
                break;
            case 2:
                species = "Haddock";
                break;
            case 3:
                species = "Pollock";
                break;
            case 4:
                species = "Mackerel";
                break;
            case 0:
            default:
                break;
        }
        return species;
    }

    /**
     * The type User quality task.
     */
    public class UserQualityTask extends AsyncTask<Void, Void, JSONObject> {
        private static final String TAG_QREPORT = "qreport";
        private static final String TAG_QRCOUNT = "qrcount";
        private final String mquality;
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
         * Instantiates a new User quality task.
         *
         * @param quality_report_id the quality report id
         */
        UserQualityTask(String quality_report_id) {
            mquality = quality_report_id;
            insertdb = new InsertDB_Class();
            confclass = new Configure_Class();
            readwrite = new Read_Write_Class();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {
            String result = "";
            if (!mquality.equals("-1") && mquality.contains("net|")) {
                JSONObject json = new JSONObject();
                String[] separated = mquality.split("|");
                result = separated[1];
                Log.d("quality_report", result);
                try {
                    json.put("success", 0);
                    json.put("qreport", separated[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return json;
            } else if (confclass.isConnectedToInternet()) {
                try {
                    String LOGIN_URL = "https://login.marineapps.net/api_check.php";
                    HashMap<String, String> params = new HashMap<>();
                    params.put("system_request", "get_quality_report");
                    params.put("userid", user_id);
                    JSONObject json = insertdb.insertPOSTRecordOnline(LOGIN_URL, params);
                    if (json != null) {
                        return json;
                    }
                } catch (Exception e) {
                    Log.d("quality_error", e.toString());
                }
            } else if (confclass.pingURL("http://" + sharedPreference.getValue(context, TAG_DATABASE_CONNECTION) + "/config.php", 1000)) {
                String dbserver = sharedPreference.getValue(context, TAG_DATABASE_CONNECTION);

                String url_address = "http://" + dbserver + "/config.php";
                HashMap<String, String> params = new HashMap<>();
                params.put("system_request", "get_quality_report");

                Log.d("request", "starting");

                JSONObject json = insertdb.insertPOSTRecordLocal(url_address, params);

                if (json != null) {
                    return json;
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            mQualityTask = null;
            if (json != null) {
                try {
                    int qrcount = json.getInt(TAG_QRCOUNT);
                    String report = json.getString(TAG_QREPORT);

                    if (qrcount > 0) {
                        String species = "";
                        String temp = "";
                        String skin = "";
                        String damages = "";
                        String eyes = "";
                        String gills = "";
                        String gutting = "";
                        String newresult = report.substring(2);
                        Log.d("changed", newresult);

                        TableRow tbrow0 = new TableRow(context);
                        TextView tv0 = new TextView(context);
                        tv0.setText(" Species ");
                        tv0.setTextColor(Color.BLACK);
                        tv0.setGravity(Gravity.CENTER);
                        tv0.setTypeface(Typeface.DEFAULT_BOLD);
                        tbrow0.addView(tv0);
                        TextView tv1 = new TextView(context);
                        tv1.setText(" Temp ");
                        tv1.setTextColor(Color.BLACK);
                        tv1.setGravity(Gravity.CENTER);
                        tv1.setTypeface(Typeface.DEFAULT_BOLD);
                        tbrow0.addView(tv1);
                        TextView tv2 = new TextView(context);
                        tv2.setText(" Skin ");
                        tv2.setTextColor(Color.BLACK);
                        tv2.setGravity(Gravity.CENTER);
                        tv2.setTypeface(Typeface.DEFAULT_BOLD);
                        tbrow0.addView(tv2);
                        TextView tv3 = new TextView(context);
                        tv3.setText(" Damage ");
                        tv3.setTextColor(Color.BLACK);
                        tv3.setGravity(Gravity.CENTER);
                        tv3.setTypeface(Typeface.DEFAULT_BOLD);
                        tbrow0.addView(tv3);
                        TextView tv4 = new TextView(context);
                        tv4.setText(" Eyes ");
                        tv4.setTextColor(Color.BLACK);
                        tv4.setGravity(Gravity.CENTER);
                        tv4.setTypeface(Typeface.DEFAULT_BOLD);
                        tbrow0.addView(tv4);
                        TextView tv5 = new TextView(context);
                        tv5.setText(" Gills ");
                        tv5.setTextColor(Color.BLACK);
                        tv5.setGravity(Gravity.CENTER);
                        tv5.setTypeface(Typeface.DEFAULT_BOLD);
                        tbrow0.addView(tv5);
                        TextView tv6 = new TextView(context);
                        tv6.setText(" Gutting ");
                        tv6.setTextColor(Color.BLACK);
                        tv6.setGravity(Gravity.CENTER);
                        tv6.setTypeface(Typeface.DEFAULT_BOLD);
                        tbrow0.addView(tv6);
                        ll.addView(tbrow0);
                        if (newresult.contains("&")) {
                            String[] separated = newresult.split("&");
                            for (int i = 0; i < separated.length; i++) {
                                TableRow tbrow = new TableRow(context);
                                String[] innerseparated = separated[i].split(",");
                                TextView t1v = new TextView(context);
                                int speciesno = Integer.parseInt(innerseparated[0]);
                                species = getSpecies(speciesno);
                                t1v.setText(species);
                                t1v.setTextColor(Color.BLACK);
                                t1v.setGravity(Gravity.CENTER);
                                tbrow.addView(t1v);
                                TextView t2v = new TextView(context);
                                temp = innerseparated[1];
                                t2v.setText(temp);
                                t2v.setTextColor(Color.BLACK);
                                t2v.setGravity(Gravity.CENTER);
                                tbrow.addView(t2v);
                                TextView t3v = new TextView(context);
                                skin = innerseparated[2];
                                t3v.setText(skin);
                                t3v.setTextColor(Color.BLACK);
                                t3v.setGravity(Gravity.CENTER);
                                tbrow.addView(t3v);
                                TextView t4v = new TextView(context);
                                damages = innerseparated[3];
                                t4v.setText(damages);
                                t4v.setTextColor(Color.BLACK);
                                t4v.setGravity(Gravity.CENTER);
                                tbrow.addView(t4v);
                                TextView t5v = new TextView(context);
                                eyes = innerseparated[4];
                                t5v.setText(eyes);
                                t5v.setTextColor(Color.BLACK);
                                t5v.setGravity(Gravity.CENTER);
                                tbrow.addView(t5v);
                                TextView t6v = new TextView(context);
                                gills = innerseparated[4];
                                t6v.setText(gills);
                                t6v.setTextColor(Color.BLACK);
                                t6v.setGravity(Gravity.CENTER);
                                tbrow.addView(t6v);
                                TextView t7v = new TextView(context);
                                gutting = innerseparated[4];
                                t7v.setText(gutting);
                                t7v.setTextColor(Color.BLACK);
                                t7v.setGravity(Gravity.CENTER);
                                tbrow.addView(t7v);
                                ll.addView(tbrow);
                            }
                        } else {
                            TableRow tbrow = new TableRow(context);
                            String[] innerseparated = newresult.split(",");
                            TextView t1v = new TextView(context);
                            int speciesno = Integer.parseInt(innerseparated[0]);
                            species = getSpecies(speciesno);
                            t1v.setText(species);
                            t1v.setTextColor(Color.BLACK);
                            t1v.setGravity(Gravity.CENTER);
                            tbrow.addView(t1v);
                            TextView t2v = new TextView(context);
                            temp = innerseparated[1];
                            t2v.setText(temp);
                            t2v.setTextColor(Color.BLACK);
                            t2v.setGravity(Gravity.CENTER);
                            tbrow.addView(t2v);
                            TextView t3v = new TextView(context);
                            skin = innerseparated[2];
                            t3v.setText(skin);
                            t3v.setTextColor(Color.BLACK);
                            t3v.setGravity(Gravity.CENTER);
                            tbrow.addView(t3v);
                            TextView t4v = new TextView(context);
                            damages = innerseparated[3];
                            t4v.setText(damages);
                            t4v.setTextColor(Color.BLACK);
                            t4v.setGravity(Gravity.CENTER);
                            tbrow.addView(t4v);
                            TextView t5v = new TextView(context);
                            eyes = innerseparated[4];
                            t5v.setText(eyes);
                            t5v.setTextColor(Color.BLACK);
                            t5v.setGravity(Gravity.CENTER);
                            tbrow.addView(t5v);
                            TextView t6v = new TextView(context);
                            gills = innerseparated[4];
                            t6v.setText(gills);
                            t6v.setTextColor(Color.BLACK);
                            t6v.setGravity(Gravity.CENTER);
                            tbrow.addView(t6v);
                            TextView t7v = new TextView(context);
                            gutting = innerseparated[4];
                            t7v.setText(gutting);
                            t7v.setTextColor(Color.BLACK);
                            t7v.setGravity(Gravity.CENTER);
                            tbrow.addView(t7v);
                            ll.addView(tbrow);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "No quality reports available", Toast.LENGTH_LONG).show();
                    }
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
            mQualityTask = null;
        }
    }
}
