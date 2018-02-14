package marineapplications.marineapplicationwifi;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by kieranmoroney on 18/07/2016.
 */
public class IPService extends Service {
    private static final String TAG_DATABASE_CONNECTION = "current_database_connection";
    private static final String TAG_WIFI_CONNECTION = "wifi_connection";
    private static final String TAG_MARIA_CONNECTION = "maria_connection";
    private static final String TAG_BACKGROUND_ACTIVE = "background_active";
    private static final String TAG_SSID_REDIRECT_ACTIVE = "ssid_redirect_active";
    private static List<BroadcastReceiver> receivers = new ArrayList<BroadcastReceiver>();
    /**
     * The Main wifi.
     */
    WifiManager mainWifi;
    /**
     * The Receiver wifi.
     */
    WifiReceiver receiverWifi;
    /**
     * The Wifi list.
     */
    List<ScanResult> wifiList;
    /**
     * The Wifidisplay.
     */
    ArrayList<String> wifidisplay;
    /**
     * The Ip address.
     */
    String ipAddress = "";
    /**
     * The Wifissid.
     */
    String wifissid = "";
    /**
     * The Wifigateway.
     */
    String wifigateway = "";
    /**
     * indicates how to behave if the service is killed
     */
    int mStartMode;
    /**
     * interface for clients that bind
     */
    IBinder mBinder;
    /**
     * indicates whether onRebind should be used
     */
    boolean mAllowRebind;
    /**
     * The Context.
     */
    IPService context = this;
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
     * The Backgroundactivity.
     */
    String backgroundactivity = "";
    /**
     * The Redirectactivity.
     */
    String redirectactivity = "";
    private UserConnectionTask mConnectTask = null;

    /**
     * Gets subnet.
     *
     * @param currentIP the current ip
     * @return the subnet
     */
    public static String getSubnet(String currentIP) {
        int firstSeparator = currentIP.lastIndexOf("/");
        int lastSeparator = currentIP.lastIndexOf(".");
        return currentIP.substring(firstSeparator + 1, lastSeparator + 1);
    }

    /**
     * Sets ssid and password.
     *
     * @param context      the context
     * @param ssid         the ssid
     * @param ssidPassword the ssid password
     * @return the ssid and password
     */
    public static boolean setSsidAndPassword(Context context, String ssid, String ssidPassword) {
        try {
            WifiConfiguration wifiConfig = new WifiConfiguration();
            wifiConfig.SSID = "\"" + ssid + "\"";
            wifiConfig.preSharedKey = "\"" + ssidPassword + "\"";
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            wifiManager.addNetwork(wifiConfig);

            int netId = wifiManager.addNetwork(wifiConfig);
            wifiManager.disconnect();
            wifiManager.enableNetwork(netId, true);
            wifiManager.reconnect();


            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Ping url boolean.
     *
     * @param url     the url
     * @param timeout the timeout
     * @return the boolean
     */
    public static boolean pingURL(String url, int timeout) {
        url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException exception) {
            return false;
        }
    }

    /**
     * Wifi ip address string.
     *
     * @param context the context
     * @return the string
     */
    protected static String wifiIpAddress(Context context) {


        String ipAddressString;
        try {
            @SuppressLint("WifiManagerPotentialLeak")
            WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
            int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

            // Convert little-endian to big-endianif needed
            if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
                ipAddress = Integer.reverseBytes(ipAddress);
            }

            byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.e("WIFIIP", "Unable to get host address.");
            ipAddressString = null;
        }

        return ipAddressString;
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

    /**
     * Called when the service is being created.
     */
    @SuppressLint("WifiManagerLeak")
    @Override
    public void onCreate() {
    }

    /**
     * Is receiver registered boolean.
     *
     * @param receiver the receiver
     * @return the boolean
     */
    public boolean isReceiverRegistered(BroadcastReceiver receiver) {
        boolean registered = receivers.contains(receiver);
        Log.i(getClass().getSimpleName(), "is receiver " + receiver + " registered? " + registered);
        return registered;
    }

    /**
     * Check wifi password boolean.
     *
     * @return the boolean
     */
    public boolean checkWifiPassword() {
        Read_Write_Class readwrite;
        boolean status = false;
        readwrite = new Read_Write_Class();
        if (!readwrite.isFilePresent(context, "WifiInfo.json")) {
            JSONObject obj = readwrite.getJSONData(context, "WifiInfo.json");

            try {
                String pw = obj.getJSONObject("wifi").getString("pw");
                status = !pw.equals("");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return status;
    }

    /**
     * Get wifi ssid string.
     *
     * @return the string
     */
    public String GetWifiSSID() {
        Read_Write_Class readwrite;
        String ssid = "";
        readwrite = new Read_Write_Class();
        if (!readwrite.isFilePresent(context, "WifiInfo.json")) {
            JSONObject obj = readwrite.getJSONData(context, "WifiInfo.json");

            try {
                ssid = obj.getJSONObject("wifi").getString("ssid");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return ssid;
    }

    /**
     * Get wifi pw string.
     *
     * @return the string
     */
    public String GetWifiPW() {
        Read_Write_Class readwrite;
        readwrite = new Read_Write_Class();
        JSONObject obj = readwrite.getJSONData(context, "WifiInfo.json");
        String pw = "";
        try {
            pw = obj.getJSONObject("wifi").getString("pw");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pw;
    }

    /**
     * Sets wifi results.
     *
     * @param wifiResults the wifi results
     */
    public void setWifiResults(final ArrayList<String> wifiResults) {
        confclass = new Configure_Class();
        sharedPreference = new SharedPreference();
        String redirect_activity = sharedPreference.getValue(context, TAG_SSID_REDIRECT_ACTIVE);
        if (isReceiverRegistered(receiverWifi)) {
            receivers.remove(receiverWifi);
            context.unregisterReceiver(receiverWifi);
        }
        wifidisplay = wifiResults;
        ArrayList<String> lists = wifidisplay;
        String displays = TextUtils.join(",", lists);
        Log.d("JSON new wifi found", displays);
        boolean wificheck = confclass.checkWifiConnected(context);
        Log.d("JSON wifi connect", String.valueOf(wificheck));
        final String ssidstr = GetWifiSSID();
        boolean checkssidnotempty = !ssidstr.equals("");
        Log.d("JSON wifi ssid", ssidstr);
        String wifissid = confclass.getWiFiSSID(context);
        final boolean checkssid = wifissid.equals(ssidstr);
        final boolean check_ssid_array = wifiResults.contains(ssidstr);
        if (redirect_activity.equals("yes")) {
            if (wificheck) {
                Log.d("JSON wifi conn", "yes");
                if (checkssidnotempty) {
                    if (!checkssid && check_ssid_array) {
                        String wifipw = GetWifiPW();
                        Toast.makeText(getApplicationContext(),
                                "Wifi not connected, Network found", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),
                                ssidstr + " Wifi not connected, Network found trying to connect", Toast.LENGTH_LONG).show();
                        //setSsidAndPassword(context, ssidstr, wifipw);
                        mConnectTask = new UserConnectionTask();
                        mConnectTask.execute();
                    } else if (!checkssid && !check_ssid_array) {
                        Toast.makeText(getApplicationContext(),
                                ssidstr + " Wifi Network not found", Toast.LENGTH_LONG).show();
                    } else {
                        mConnectTask = new UserConnectionTask();
                        mConnectTask.execute();
                    }
                }
            } else {
                Log.d("JSON wifi conn", "no");
                if (checkssidnotempty) {
                    if (!checkssid && check_ssid_array) {
                        String wifipw = GetWifiPW();
                        Toast.makeText(getApplicationContext(),
                                "Wifi not connected, Network found", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),
                                ssidstr + " Wifi not connected, Network found trying to connect", Toast.LENGTH_LONG).show();
                        //setSsidAndPassword(context, ssidstr, wifipw);
                        mConnectTask = new UserConnectionTask();
                        mConnectTask.execute();
                    } else if (!checkssid && !check_ssid_array) {
                        Toast.makeText(getApplicationContext(),
                                ssidstr + " Wifi Network not found", Toast.LENGTH_LONG).show();
                    } else {
                        mConnectTask = new UserConnectionTask();
                        mConnectTask.execute();
                    }
                }
            }
        } else {
            mConnectTask = new UserConnectionTask();
            mConnectTask.execute();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sharedPreference = new SharedPreference();
        confclass = new Configure_Class();
        backgroundactivity = sharedPreference.getValue(context, TAG_BACKGROUND_ACTIVE);
        if (sharedPreference.getValue(context, TAG_SSID_REDIRECT_ACTIVE) == null) {
            sharedPreference.save(context, TAG_SSID_REDIRECT_ACTIVE, "yes");
            redirectactivity = "no";
        } else {
            redirectactivity = sharedPreference.getValue(context, TAG_SSID_REDIRECT_ACTIVE);
        }

        if (isReceiverRegistered(receiverWifi)) {
            receivers.remove(receiverWifi);
            context.unregisterReceiver(receiverWifi);
        }
        mainWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (!mainWifi.isWifiEnabled()) {
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
            mainWifi.setWifiEnabled(true);
        }

        try {
            ipAddress = confclass.wifiIpAddress(this);
            wifissid = confclass.getWiFiSSID(this);
            wifigateway = confclass.getWifiGateway(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        receiverWifi = new WifiReceiver();
        registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        mainWifi.startScan();

        return Service.START_NOT_STICKY;
    }

    /**
     * A client is binding to the service with bindService()
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Called when all clients have unbound with unbindService()
     */
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    /**
     * Called when a client is binding to the service with bindService()
     */
    @Override
    public void onRebind(Intent intent) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isReceiverRegistered(receiverWifi)) {
            receivers.remove(receiverWifi);
            this.unregisterReceiver(receiverWifi);
        }
    }

    /**
     * Gets current ip.
     *
     * @return the current ip
     */
    public InetAddress getCurrentIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) networkInterfaces
                        .nextElement();
                Enumeration<InetAddress> nias = ni.getInetAddresses();
                while (nias.hasMoreElements()) {
                    InetAddress ia = (InetAddress) nias.nextElement();
                    if (!ia.isLinkLocalAddress()
                            && !ia.isLoopbackAddress()
                            && ia instanceof Inet4Address) {
                        return ia;
                    }
                }
            }
        } catch (SocketException e) {
        }
        return null;
    }

    /**
     * The type Wifi receiver.
     */
    class WifiReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
            ArrayList<String> lists = new ArrayList<>();

            wifiList = mainWifi.getScanResults();
            for (int i = 0; i < wifiList.size(); i++) {

                lists.add(wifiList.get(i).SSID);
            }
            context.unregisterReceiver(receiverWifi);
            setWifiResults(lists);
        }
    }

    /**
     * The type User connection task.
     */
    public class UserConnectionTask extends AsyncTask<Void, Void, Boolean> {
        /**
         * The Insertdb.
         */
        InsertDB_Class insertdb;
        /**
         * The Readwrite.
         */
        Read_Write_Class readwrite;
        /**
         * The Confclass.
         */
        Configure_Class confclass;

        /**
         * Instantiates a new User connection task.
         */
        UserConnectionTask() {
            sharedPreference = new SharedPreference();
            confclass = new Configure_Class();
            insertdb = new InsertDB_Class();
            readwrite = new Read_Write_Class();
        }

        @Override
        protected Boolean doInBackground(Void... args) {
            boolean rpfound;
            String ipaddress = "";
            try {
                ipaddress = confclass.getCurrentIp().toString().substring(1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String subnet = confclass.getSubnet(ipaddress);
            String wifi_connection = "no";
            boolean wificheck = confclass.checkWifiConnected(context);
            if (wificheck) {
                wifi_connection = "yes";
            }

            try {
                String ip_address = "";
                Log.d("JSON subnet", subnet);
                String internal_ip = subnet + 1;
                String newwifi_internal_ip = subnet + 139;
                String wifi_internal_ip = subnet + 157;
                String old_ip = subnet + 10;
                String fixed_ip = subnet + 112;
                String fixed_new_ip = subnet + 104;
                if (sharedPreference.getValue(context, TAG_DATABASE_CONNECTION) != null && pingURL("http://" + sharedPreference.getValue(context, TAG_DATABASE_CONNECTION) + "/config.php", 1000)) {
                    ip_address = sharedPreference.getValue(context, TAG_DATABASE_CONNECTION);
                    Log.d("JSON s ip", sharedPreference.getValue(context, TAG_DATABASE_CONNECTION));
                } else if (pingURL("http://" + internal_ip + "/config.php", 5000)) {
                    ip_address = internal_ip;
                    Log.d("JSON in ip", internal_ip);
                } else if (pingURL("http://" + newwifi_internal_ip + "/config.php", 1000)) {
                    ip_address = newwifi_internal_ip;
                    Log.d("JSON f ip", newwifi_internal_ip);
                } else if (pingURL("http://" + fixed_ip + "/config.php", 1000)) {
                    ip_address = fixed_ip;
                    Log.d("JSON f ip", fixed_ip);
                } else if (pingURL("http://" + wifi_internal_ip + "/config.php", 1000)) {
                    ip_address = wifi_internal_ip;
                    Log.d("JSON w ip", wifi_internal_ip);
                } else if (pingURL("http://" + old_ip + "/config.php", 1000)) {
                    ip_address = old_ip;
                    Log.d("JSON o ip", old_ip);
                } else if (pingURL("http://" + fixed_new_ip + "/config.php", 1000)) {
                    ip_address = fixed_new_ip;
                    Log.d("JSON fn ip", fixed_new_ip);
                }
                Log.d("JSON ip rt", ip_address);

                String ipfound = "no";
                rpfound = !ip_address.equals("");
                Log.d("JSON rp return", String.valueOf(rpfound));
                Log.d("JSON rp string", ip_address);
                if (!ip_address.equals("")) {
                    sharedPreference.save(context, TAG_DATABASE_CONNECTION, ip_address);
                    ipfound = "yes";
                } else {
                    String rpip = "";
                    JSONObject robj = readwrite.getJSONData(context, "Raspberry_PI_Information.json");
                    try {
                        JSONArray jsr = robj.getJSONArray("rasperry_pi");

                        for (int i = 0; i < jsr.length(); i++) {
                            JSONObject jsrobj = jsr.getJSONObject(i);
                            String jval = jsrobj.getString("value");
                            if (!jval.equals("")) {
                                if (pingURL("http://" + jval + "/config.php", 1000)) {
                                    ip_address = jval;
                                    sharedPreference.save(context, TAG_DATABASE_CONNECTION, jval);
                                    ipfound = "yes";
                                    break;
                                }
                            }
                        }
                        boolean checkrp = valueExists(jsr, ip_address);
                        Log.d("JSON rpchecker", String.valueOf(checkrp));
                        if (!checkrp) {
                            if (!ip_address.equals("")) {
                                JSONObject list = new JSONObject();
                                list.put("value", ip_address);
                                robj.getJSONArray("rasperry_pi").put(list);
                                Log.d("JSON rp res", robj.toString());
                                readwrite.saveData(context, robj.toString(), "Raspberry_PI_Information.json");
                            }
                        }
                        Log.d("JSON rps string", ip_address);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.d("JSON rp bool", String.valueOf(rpfound));

                //Wifi Check for Raspberry PI
                if (!rpfound) {
                    for (int i = 1; i < 254; i++) {
                        String host = subnet + i;
                        System.out.println("Checking :" + host);
                        Process p1 = Runtime.getRuntime().exec("ping -c 1 " + host);
                        int returnVal = p1.waitFor();
                        boolean reachable = (returnVal == 0);

                        if (reachable) {
                            System.out.println(host + " is reachable");
                            String url = "http://" + host + "/test.php";

                            if (pingURL(url, 1000)) {
                                rpfound = true;
                                ip_address = host;
                                System.out.println(host + " is reachable and connected");
                                Log.d("JSON ip saved found", host);
                                sharedPreference.save(context, TAG_DATABASE_CONNECTION, host);
                                if (readwrite.isFilePresent(context, "Raspberry_PI_Information.json")) {
                                    JSONObject obj = readwrite.getJSONData(context, "Raspberry_PI_Information.json");
                                    try {
                                        JSONArray jsr = obj.getJSONArray("rasperry_pi");
                                        if (jsr.length() > 0) {
                                            boolean checkrp = confclass.checkExistsArray("value", host, jsr);
                                            Log.d("JSON chk", String.valueOf(checkrp));
                                            if (!checkrp) {
                                                JSONObject list = new JSONObject();
                                                list.put("value", host);
                                                obj.getJSONArray("rasperry_pi").put(list);
                                                readwrite.saveData(context, obj.toString(), "Raspberry_PI_Information.json");
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                ipfound = "yes";
                                break;
                            } else {
                                System.out.println(host + " is reachable and not connected");
                            }
                        } else {
                            System.out.println(host + " is not reachable");
                        }
                    }
                }

                if (readwrite.isFilePresent(context, "wifi_connection_details.json")) {
                    JSONObject obj = readwrite.getJSONData(context, "wifi_connection_details.json");
                    try {
                        obj.put("wifi_connection", wifi_connection);
                        obj.put("maria_connection", ipfound);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                sharedPreference.save(context, TAG_WIFI_CONNECTION, wifi_connection);
                sharedPreference.save(context, TAG_MARIA_CONNECTION, ipfound);

                if (!ip_address.equals("")) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            mConnectTask = null;
            if (result) {
            } else {
                Toast.makeText(getApplicationContext(),
                        "Unable to find the Raspberry Pi's ip address", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mConnectTask = null;
        }
    }
}

