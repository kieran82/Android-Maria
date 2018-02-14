package marineapplications.marineapplicationwifi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Temperature table activity.
 */
public class TemperatureTableActivity extends AppCompatActivity {
    private static final String TAG_DATABASE_CONNECTION = "current_database_connection";
    /**
     * The Record list.
     */
    List<Temperature_Class> recordList = new ArrayList<Temperature_Class>();
    /**
     * The Ll.
     */
    TableLayout ll;
    /**
     * The Context.
     */
    Activity context = this;
    /**
     * The Shared preference.
     */
    SharedPreference sharedPreference;
    private boolean selectresult;
    private UserTempTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.temptoolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle(R.string.temptablelabel);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TemperatureTableActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        ll = (TableLayout) findViewById(R.id.table_main);
        sharedPreference = new SharedPreference();
        mAuthTask = new UserTempTask();
        mAuthTask.execute();
    }

    /**
     * Checkconnection boolean.
     *
     * @return the boolean
     */
    public boolean checkconnection() {
        sharedPreference = new SharedPreference();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://" + sharedPreference.getValue(context, TAG_DATABASE_CONNECTION) + ":3306/GLAUCUS", "window", "kieran")) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private boolean doSelectCheck() {

        Connection Db2Conn = null;
        PreparedStatement Db2Stmt = null;
        ResultSet Db2Rs = null;
        String sql = null;
        Boolean result = false;
        sharedPreference = new SharedPreference();

        try {

            // Database connection string
            Class.forName("com.mysql.jdbc.Driver");
            Db2Conn = DriverManager.getConnection("jdbc:mysql://" + sharedPreference.getValue(context, TAG_DATABASE_CONNECTION) + ":3306/GLAUCUS", "window", "kieran");

            // Db2 SQL statement to insert data
            sql = "SELECT * from log";
            Db2Stmt = Db2Conn.prepareStatement(sql);
            Db2Rs = Db2Stmt.executeQuery();

            result = Db2Rs.next();
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Db2Stmt != null) try {
                Db2Stmt.close();
            } catch (Exception e) {
            }
            if (Db2Conn != null) try {
                Db2Conn.close();
            } catch (Exception e) {
            }
        }
        return result;
    }


    /**
     * The type User temp task.
     */
    public class UserTempTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... args) {
            Connection Db2Conn = null;
            PreparedStatement Db2Stmt = null;
            ResultSet Db2Rs = null;
            String sql = null;
            String listString = "";
            if (checkconnection()) {
                ArrayList<String> recordList = new ArrayList<String>();
                try {
                    sharedPreference = new SharedPreference();
                    selectresult = doSelectCheck();
                    if (selectresult) {
                        // Database connection string
                        Class.forName("com.mysql.jdbc.Driver");
                        Db2Conn = DriverManager.getConnection("jdbc:mysql://" + sharedPreference.getValue(context, TAG_DATABASE_CONNECTION) + ":3306/GLAUCUS", "window", "kieran");

                        // Db2 SQL statement to insert data
                        sql = "SELECT gps,temps from log ORDER BY id DESC LIMIT 30";
                        Db2Stmt = Db2Conn.prepareStatement(sql);
                        Db2Rs = Db2Stmt.executeQuery();
                        String gpsdate = "";
                        String gpstime = "";
                        String gpslocation = "";
                        while (Db2Rs.next()) {
                            String[] recordArray = new String[7];
                            String column1 = Db2Rs.getString(1);
                            String column2 = Db2Rs.getString(2);
                            Log.d("Column 1", column1);
                            Log.d("Column 2", column2);
                            if (column1.contains("Bad")) {
                                gpsdate = "Not Available";
                                gpstime = "Not Available";
                                gpslocation = "Not Available";
                            } else if (column1.contains(",V") && column1.substring(0, 1).matches("[0-9]")) {
                                gpsdate = "";
                                gpslocation = "Not Available";
                                String gpstimetenmp = column1.substring(0, 6);
                                String ghour = gpstimetenmp.substring(0, 2);
                                String gminute = gpstimetenmp.substring(2, 4);
                                gpstime = ghour + ":" + gminute;

                                String oldgpsdate = column1.substring(column1.length() - 7);
                                String gpsdatetemp = oldgpsdate.substring(0, oldgpsdate.length() - 1);
                                String gdate = gpsdatetemp.substring(0, 2);
                                String gmonth = gpsdatetemp.substring(2, 4);
                                String gyear = gpsdatetemp.substring(4, 6);
                                gpsdate = gdate + "-" + gmonth + "-" + gyear;
                            } else if (column1.contains(",V") && !column1.substring(0, 1).matches("[0-9]")) {
                                gpsdate = "Not Available";
                                gpstime = "Not Available";
                                gpslocation = "Not Available";
                            } else if (column1.equals("gps not available now")) {
                                gpsdate = "Not Available";
                                gpstime = "Not Available";
                                gpslocation = "Not Available";
                            } else {
                                String resultstripped = column1.substring(0, column1.length() - 1);
                                if (resultstripped.contains(",,")) {
                                    String[] resultstrippedseparated = resultstripped.split(",,");
                                    String gpsco = resultstrippedseparated[0];
                                    String[] resultseparated = gpsco.split(",");

                                    String gpstimetemp = resultseparated[0].substring(0, 6);
                                    String ghour = gpstimetemp.substring(0, 2);
                                    String gminute = gpstimetemp.substring(2, 4);
                                    gpstime = ghour + ":" + gminute;
                                    String gdate = resultstrippedseparated[1].substring(0, 2);
                                    String gmonth = resultstrippedseparated[1].substring(2, 4);
                                    String gyear = resultstrippedseparated[1].substring(4, 6);
                                    String newgpsdate = gdate + "-" + gmonth + "-" + gyear;
                                    gpsdate = newgpsdate;

                                    String lat_N = resultseparated[2];
                                    String lat_W = resultseparated[4];

                                    gpslocation = lat_N + " N " + lat_W + " W";
                                }
                            }

                            String chilled = "";
                            String freezer = "";
                            String blast = "";
                            String core = "";
                            recordArray[0] = gpslocation;
                            recordArray[1] = gpsdate;
                            recordArray[2] = gpstime;
                            if (column2.equals("Bad")) {
                                chilled = "Bad";
                                freezer = "Bad";
                                blast = "Bad";
                                core = "Bad";
                            } else {
                                String[] separated = column2.split(",");
                                chilled = separated[1];
                                freezer = separated[3];
                                blast = separated[2];
                                core = separated[0];
                            }

                            recordArray[3] = chilled;
                            recordArray[4] = freezer;
                            recordArray[5] = blast;
                            recordArray[6] = core;

                            String newString = Arrays.toString(recordArray);

                            recordList.add(newString);
                        }

                        if (recordList.size() == 1) {
                            for (String s : recordList) {
                                listString += s + "";
                            }
                        } else {
                            for (String s : recordList) {
                                listString += s + "&";
                            }
                        }
                    } else {
                        return "empty";
                    }
                }
                // Handle any errors that may have occurred.
                catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (Db2Stmt != null) try {
                        Db2Stmt.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (Db2Conn != null) try {
                        Db2Conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Log.d("recordarray", listString);

                return listString;
            } else {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            mAuthTask = null;
            final GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(5);
            gd.setStroke(1, 0xFF000000);

            TableRow tbrow0 = new TableRow(TemperatureTableActivity.this);
            TextView tv0 = new TextView(TemperatureTableActivity.this);
            tv0.setText(" GPS ");
            tv0.setTextColor(Color.BLACK);
            tv0.setGravity(Gravity.CENTER);
            tv0.setTypeface(Typeface.DEFAULT_BOLD);
            tbrow0.addView(tv0);
            TextView tv1 = new TextView(TemperatureTableActivity.this);
            tv1.setText(" GPS DATE ");
            tv1.setTextColor(Color.BLACK);
            tv1.setGravity(Gravity.CENTER);
            tv1.setTypeface(Typeface.DEFAULT_BOLD);
            tbrow0.addView(tv1);
            TextView tv2 = new TextView(TemperatureTableActivity.this);
            tv2.setText(" GPS TIME ");
            tv2.setTextColor(Color.BLACK);
            tv2.setGravity(Gravity.CENTER);
            tv2.setTypeface(Typeface.DEFAULT_BOLD);
            tbrow0.addView(tv2);
            TextView tv3 = new TextView(TemperatureTableActivity.this);
            tv3.setText(" Chilled ");
            tv3.setTextColor(Color.BLACK);
            tv3.setGravity(Gravity.CENTER);
            tv3.setTypeface(Typeface.DEFAULT_BOLD);
            tbrow0.addView(tv3);
            TextView tv4 = new TextView(TemperatureTableActivity.this);
            tv4.setText(" Freezer ");
            tv4.setTextColor(Color.BLACK);
            tv4.setGravity(Gravity.CENTER);
            tv4.setTypeface(Typeface.DEFAULT_BOLD);
            tbrow0.addView(tv4);
            TextView tv5 = new TextView(TemperatureTableActivity.this);
            tv5.setText(" Blast ");
            tv5.setTextColor(Color.BLACK);
            tv5.setGravity(Gravity.CENTER);
            tv5.setTypeface(Typeface.DEFAULT_BOLD);
            tbrow0.addView(tv5);
            TextView tv6 = new TextView(TemperatureTableActivity.this);
            tv6.setText(" Core ");
            tv6.setTextColor(Color.BLACK);
            tv6.setGravity(Gravity.CENTER);
            tv6.setTypeface(Typeface.DEFAULT_BOLD);
            tbrow0.addView(tv6);
            ll.addView(tbrow0);
            String gpsresult, gpstime, gpsdate;
            String chilled = "";
            String freezer = "";
            String blast = "";
            String corefreeze = "";
            if (!result.equals("") && !result.equals("empty")) {
                if (result.contains("&")) {
                    String[] innerseparated = result.split("&");
                    for (String s : innerseparated) {
                        TableRow tbrow = new TableRow(context);
                        String[] newseparated = s.split(",");
                        gpsresult = newseparated[0].substring(1);
                        gpsdate = newseparated[1];
                        gpstime = newseparated[2];
                        String tempchilled = newseparated[3];
                        tempchilled = tempchilled.trim();
                        if (tempchilled.equals("Bad")) {
                            chilled = "Bad";
                        } else {
                            float chilledno = Float.parseFloat(tempchilled) / 10;
                            chilled = Float.toString(chilledno);
                        }

                        String freezertemp = newseparated[4];
                        freezertemp = freezertemp.trim();
                        if (freezertemp.equals("Bad")) {
                            freezer = "Bad";
                        } else {
                            float freezerno = Float.parseFloat(freezertemp) / 10;
                            freezer = Float.toString(freezerno);
                        }

                        String blasttemp = newseparated[5];
                        blasttemp = blasttemp.trim();
                        if (blasttemp.equals("Bad")) {
                            blast = "Bad";
                        } else {
                            float blastno = Float.parseFloat(blasttemp) / 10;
                            blast = Float.toString(blastno);
                        }
                        String corefreezetemp = newseparated[6].substring(0, newseparated[6].length() - 1);
                        corefreezetemp = corefreezetemp.trim();
                        if (corefreezetemp.equals("Bad")) {
                            corefreeze = "Bad";
                        } else {
                            float coreno = Float.parseFloat(corefreezetemp) / 10;
                            corefreeze = Float.toString(coreno);
                        }


                        TextView t1v = new TextView(context);
                        t1v.setText(gpsresult);
                        t1v.setTextColor(Color.BLACK);
                        t1v.setGravity(Gravity.CENTER);
                        tbrow.addView(t1v);
                        TextView t2v = new TextView(context);
                        t2v.setText(gpsdate);
                        t2v.setTextColor(Color.BLACK);
                        t2v.setGravity(Gravity.CENTER);
                        tbrow.addView(t2v);
                        TextView t3v = new TextView(context);
                        t3v.setText(gpstime);
                        t3v.setTextColor(Color.BLACK);
                        t3v.setGravity(Gravity.CENTER);
                        tbrow.addView(t3v);
                        TextView t4v = new TextView(context);
                        t4v.setText(chilled);
                        t4v.setTextColor(Color.BLACK);
                        t4v.setGravity(Gravity.CENTER);
                        tbrow.addView(t4v);
                        TextView t5v = new TextView(context);
                        t5v.setText(freezer);
                        t5v.setTextColor(Color.BLACK);
                        t5v.setGravity(Gravity.CENTER);
                        tbrow.addView(t5v);
                        TextView t6v = new TextView(context);
                        t6v.setText(blast);
                        t6v.setTextColor(Color.BLACK);
                        t6v.setGravity(Gravity.CENTER);
                        tbrow.addView(t6v);
                        TextView t7v = new TextView(context);
                        t7v.setText(corefreeze);
                        t7v.setTextColor(Color.BLACK);
                        t7v.setGravity(Gravity.CENTER);
                        tbrow.addView(t7v);
                        ll.addView(tbrow);
                    }
                } else {
                    TableRow tbrow = new TableRow(context);
                    String[] newseparated = result.split(",");
                    gpsresult = newseparated[0].substring(1);
                    gpsdate = newseparated[1];
                    gpstime = newseparated[2];
                    String tempchilled = newseparated[3];
                    tempchilled = tempchilled.trim();
                    if (tempchilled.equals("Bad")) {
                        chilled = "Bad";
                    } else {
                        float chilledno = Float.parseFloat(tempchilled) / 10;
                        chilled = Float.toString(chilledno);
                    }

                    String freezertemp = newseparated[4];
                    freezertemp = freezertemp.trim();
                    if (freezertemp.equals("Bad")) {
                        freezer = "Bad";
                    } else {
                        float freezerno = Float.parseFloat(freezertemp) / 10;
                        freezer = Float.toString(freezerno);
                    }

                    String blasttemp = newseparated[5];
                    blasttemp = blasttemp.trim();
                    if (blasttemp.equals("Bad")) {
                        blast = "Bad";
                    } else {
                        float blastno = Float.parseFloat(blasttemp) / 10;
                        blast = Float.toString(blastno);
                    }
                    String corefreezetemp = newseparated[6].substring(0, newseparated[6].length() - 1);
                    corefreezetemp = corefreezetemp.trim();
                    if (corefreezetemp.equals("Bad")) {
                        corefreeze = "Bad";
                    } else {
                        float coreno = Float.parseFloat(corefreezetemp) / 10;
                        corefreeze = Float.toString(coreno);
                    }

                    TextView t1v = new TextView(context);
                    t1v.setText(gpsresult);
                    t1v.setTextColor(Color.BLACK);
                    t1v.setGravity(Gravity.CENTER);
                    tbrow.addView(t1v);
                    TextView t2v = new TextView(context);
                    t2v.setText(gpsdate);
                    t2v.setTextColor(Color.BLACK);
                    t2v.setGravity(Gravity.CENTER);
                    tbrow.addView(t2v);
                    TextView t3v = new TextView(context);
                    t3v.setText(gpstime);
                    t3v.setTextColor(Color.BLACK);
                    t3v.setGravity(Gravity.CENTER);
                    tbrow.addView(t3v);
                    TextView t4v = new TextView(context);
                    t4v.setText(chilled);
                    t4v.setTextColor(Color.BLACK);
                    t4v.setGravity(Gravity.CENTER);
                    tbrow.addView(t4v);
                    TextView t5v = new TextView(context);
                    t5v.setText(freezer);
                    t5v.setTextColor(Color.BLACK);
                    t5v.setGravity(Gravity.CENTER);
                    tbrow.addView(t5v);
                    TextView t6v = new TextView(context);
                    t6v.setText(blast);
                    t6v.setTextColor(Color.BLACK);
                    t6v.setGravity(Gravity.CENTER);
                    tbrow.addView(t6v);
                    TextView t7v = new TextView(context);
                    t7v.setText(corefreeze);
                    t7v.setTextColor(Color.BLACK);
                    t7v.setGravity(Gravity.CENTER);
                    tbrow.addView(t7v);
                    ll.addView(tbrow);
                }

            } else if (result.equals("empty")) {
                Toast.makeText(getApplicationContext(),
                        "No temperatures available in the database yet", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Can't connect to Raspberry Pi", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

}
