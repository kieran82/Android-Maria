package marineapplications.marineapplicationwifi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Frank on 02/03/2016.
 */
public class JSONParser {

    /**
     * The constant DO_NOT_VERIFY.
     */
    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };
    /**
     * The Charset.
     */
    String charset = "UTF-8";
    /**
     * The Conn.
     */
    HttpsURLConnection conn;
    /**
     * The Wr.
     */
    DataOutputStream wr;
    /**
     * The Result.
     */
    StringBuilder result;
    /**
     * The Url obj.
     */
    URL urlObj;
    /**
     * The J obj.
     */
    JSONObject jObj = null;
    /**
     * The Sb params.
     */
    StringBuilder sbParams;
    /**
     * The Params string.
     */
    String paramsString;
    /**
     * The Convert result.
     */
    String convertResult = "";

    private static void trustAllHosts() {

        X509TrustManager easyTrustManager = new X509TrustManager() {

            public void checkClientTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
                // Oh, I am easy!
            }

            public void checkServerTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
                // Oh, I am easy!
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

        };

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{easyTrustManager};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");

            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Make http request json object.
     *
     * @param url    the url
     * @param method the method
     * @param params the params
     * @return the json object
     */
    public JSONObject makeHttpRequest(String url, String method, HashMap<String, String> params) {
        sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);

                trustAllHosts();
                conn = (HttpsURLConnection) urlObj.openConnection();

                conn.setUseCaches(false);

                conn.setDoOutput(true);
                conn.setDoInput(true);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

                conn.setReadTimeout(30000);
                conn.setConnectTimeout(15000);
                conn.setHostnameVerifier(DO_NOT_VERIFY);

                conn.connect();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(params));
                writer.flush();
                writer.close();
                int responseCode = conn.getResponseCode();
                if (responseCode >= 400) {
                    Log.d("JSON Error", "result: error not connected ");
                } else {
                    Log.d("JSON Error", "result: connected to page");
                }
                //Receive the response from the server
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                convertResult = result.toString();
                Log.d("JSON Parser", "result: " + convertResult);

                conn.disconnect();

                // try parse the string to a JSON object
                try {
                    jObj = new JSONObject(convertResult);
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                }

                // return JSON Object
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jObj;
    }

    /**
     * Gets json from url.
     *
     * @param url the url
     * @return the json from url
     */
    public JSONObject getJSONFromURL(String url) {
        trustAllHosts();
        HttpsURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpsURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setDoInput(true);
            c.setAllowUserInteraction(false);
            c.setReadTimeout(10000);
            c.setConnectTimeout(15000);
            c.setHostnameVerifier(DO_NOT_VERIFY);
            c.connect();
            int status = c.getResponseCode();
            String strconvert;
            switch (status) {
                case 200:
                case 201:

                    char[] buffer = new char[4096];
                    InputStreamReader reader = new InputStreamReader(c.getInputStream());
                    try {
                        StringWriter writer = new StringWriter();
                        while (true) {
                            int numRead = reader.read(buffer);
                            if (numRead < 0) {
                                break;
                            }
                            writer.write(buffer, 0, numRead);
                        }
                        strconvert = writer.toString();
                        c.disconnect();
                        try {
                            String stripwhiteresult = strconvert.replaceAll("\\s+", "");
                            jObj = new JSONObject(stripwhiteresult);
                        } catch (JSONException e) {
                            Log.e("JSON Parser", "Error parsing data " + e.toString());
                        }
                    } finally {
                        reader.close();
                    }
                    return jObj;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    /**
     * Gets json.
     *
     * @param url the url
     * @return the json
     */
    public String getJSON(String url) {
        trustAllHosts();
        HttpsURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpsURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setDoInput(true);
            c.setAllowUserInteraction(false);
            c.setReadTimeout(50000);
            c.setConnectTimeout(15000);
            c.setHostnameVerifier(DO_NOT_VERIFY);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    String strconvert;
                    char[] buffer = new char[4096];
                    InputStreamReader reader = new InputStreamReader(c.getInputStream());
                    try {
                        StringWriter writer = new StringWriter();
                        while (true) {
                            int numRead = reader.read(buffer);
                            if (numRead < 0) {
                                break;
                            }
                            writer.write(buffer, 0, numRead);
                        }
                        strconvert = writer.toString();
                    } finally {
                        reader.close();
                    }
                    return strconvert;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
