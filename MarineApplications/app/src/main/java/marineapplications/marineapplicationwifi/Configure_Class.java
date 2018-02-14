package marineapplications.marineapplicationwifi;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Objects;
import java.util.zip.Deflater;

/**
 * Created by kieranmoroney on 17/02/2017.
 */
class Configure_Class {

    /**
     * Wifi ip address string.
     *
     * @param context the context
     * @return the string
     */
    String wifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endianif needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.e("WIFIIP", "Unable to get host address.");
            ipAddressString = null;
        }

        return ipAddressString;
    }

    /**
     * Check exists string.
     *
     * @param jsonArray   the json array
     * @param ValueToFind the value to find
     * @return the string
     * @throws JSONException the json exception
     */
    String CheckExists(JSONArray jsonArray, String ValueToFind) throws JSONException {
        String returnName = "";
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jchk = jsonArray.getJSONObject(i);
            String jname = jchk.getString("name");
            String jval = jchk.getString("value");
            if (Objects.equals(jval.toLowerCase(), ValueToFind.toLowerCase())) {
                returnName = jname;
            }
        }
        return returnName;
    }

    /**
     * Gets wi fi ssid.
     *
     * @param context the context
     * @return the wi fi ssid
     */
    String getWiFiSSID(Context context) {
        WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (manager.isWifiEnabled()) {
            WifiInfo wifiInfo = manager.getConnectionInfo();
            if (wifiInfo != null) {
                NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                if (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
                    String wifiaddress = wifiInfo.getSSID();
                    return wifiaddress.substring(1, wifiaddress.length() - 1);
                }
            }
        }
        return null;
    }

    /**
     * Gets wifi gateway.
     *
     * @param context the context
     * @return the wifi gateway
     */
    String getWifiGateway(Context context) {
        WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        DhcpInfo d = manager.getDhcpInfo();

        if (manager.isWifiEnabled()) {
            WifiInfo wifiInfo = manager.getConnectionInfo();
            if (wifiInfo != null) {
                NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                if (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
                    return intToIp(d.gateway);
                }
            }
        }
        return null;
    }

    /**
     * Sets ssid and password.
     *
     * @param context      the context
     * @param ssid         the ssid
     * @param ssidPassword the ssid password
     * @return the ssid and password
     */
    boolean setSsidAndPassword(Context context, String ssid, String ssidPassword) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            Method getConfigMethod = wifiManager.getClass().getMethod("getWifiApConfiguration");
            WifiConfiguration wifiConfig = (WifiConfiguration) getConfigMethod.invoke(wifiManager);

            wifiConfig.SSID = ssid;
            wifiConfig.preSharedKey = ssidPassword;

            Method setConfigMethod = wifiManager.getClass().getMethod("setWifiApConfiguration", WifiConfiguration.class);
            setConfigMethod.invoke(wifiManager, wifiConfig);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Convert dp to pixel float.
     *
     * @param dp      the dp
     * @param context the context
     * @return the float
     */
    float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * Int to ip string.
     *
     * @param addr the addr
     * @return the string
     */
    String intToIp(int addr) {
        return ((addr & 0xFF) + "." +
                ((addr >>>= 8) & 0xFF) + "." +
                ((addr >>>= 8) & 0xFF) + "." +
                ((addr >>>= 8) & 0xFF));
    }

    /**
     * Gets current ip.
     *
     * @return the current ip
     */
    InetAddress getCurrentIp() {
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
            // log.error("unable to get current IP " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Check wifi connected boolean.
     *
     * @param context the context
     * @return the boolean
     */
    boolean checkWifiConnected(Context context) {
        WifiManager wifiMgr = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.isWifiEnabled()) { // WiFi adapter is ON
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            return wifiInfo.getNetworkId() != -1;
        } else {
            return false; // WiFi adapter is OFF
        }
    }

    /**
     * Hide soft keyboard.
     *
     * @param activity the activity
     */
    void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Gets subnet.
     *
     * @param currentIP the current ip
     * @return the subnet
     */
    String getSubnet(String currentIP) {
        String ipaddress = "";
        try {
            int firstSeparator = currentIP.lastIndexOf("/");
            int lastSeparator = currentIP.lastIndexOf(".");
            ipaddress = currentIP.substring(firstSeparator + 1, lastSeparator + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ipaddress;
    }

    /**
     * Ping url boolean.
     *
     * @param url     the url
     * @param timeout the timeout
     * @return the boolean
     */
    boolean pingURL(String url, int timeout) {
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
     * Gets device name.
     *
     * @return the device name
     */
    String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    /**
     * Convert dateto display string.
     *
     * @param datetext the datetext
     * @return the string
     */
    String ConvertDatetoDisplay(String datetext) {
        String[] separated = datetext.split("-");
        return separated[2] + "-" + separated[1] + "-" + separated[0];
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    /**
     * Gets date current time zone.
     *
     * @return the date current time zone
     */
    String getDateCurrentTimeZone() {
        String strDate = "";
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
            strDate = sdf.format(c.getTime());
            return strDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Compress string byte [ ].
     *
     * @param data the data
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    byte[] compressString(String data) throws IOException {
        byte[] compressed = null;
        byte[] byteData = data.getBytes();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(byteData.length);
        Deflater compressor = new Deflater();
        compressor.setLevel(Deflater.BEST_COMPRESSION);
        compressor.setInput(byteData, 0, byteData.length);
        compressor.finish();

        // Compress the data
        final byte[] buf = new byte[1024];
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            bos.write(buf, 0, count);
        }
        compressor.end();
        compressed = bos.toByteArray();
        bos.close();
        return compressed;
    }

    /**
     * Byte array to string string.
     *
     * @param data the data
     * @return the string
     */
    String byteArrayToString(byte[] data) {
        String response = Arrays.toString(data);

        String[] byteValues = response.substring(1, response.length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length];

        for (int i = 0, len = bytes.length; i < len; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }

        String str = new String(bytes);
        return str.toLowerCase();
    }

    /**
     * Is numeric boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    /**
     * Check exists array boolean.
     *
     * @param key   the key
     * @param value the value
     * @param js    the js
     * @return the boolean
     * @throws JSONException the json exception
     */
    public boolean checkExistsArray(String key, String value, JSONArray js) throws JSONException {
        boolean result = false;
        for (int i = 0; i < js.length(); i++) {
            JSONObject jsobj = js.getJSONObject(i);
            String jkey = jsobj.getString(key);
            if (jkey.equals(value)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Is internetconnected boolean.
     *
     * @param ct the ct
     * @return the boolean
     */
    public boolean isInternetconnected(Context ct) {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) ct.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                connected = true;
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                connected = true;
        } else {
            connected = false;
        }
        return connected;
    }

    /**
     * Is internet working boolean.
     *
     * @return the boolean
     */
    public boolean isInternetWorking() {
        boolean success = false;
        try {
            URL url = new URL("https://google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.connect();
            success = connection.getResponseCode() == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Is connected to internet boolean.
     *
     * @return the boolean
     */
    public boolean isConnectedToInternet() {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}