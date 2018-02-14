package marineapplications.marineapplicationwifi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kieranmoroney on 23/04/2016.
 * Error Class
 */
class Error_Log_Class {
    private int id;
    private String _date_added;
    private String _error_type;
    private String _error_text;
    private int _user;

    /**
     * Gets id.
     *
     * @return the id
     */
    int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    void setId(int id) {
        this.id = id;
    }

    /**
     * Gets date added.
     *
     * @return the date added
     */
    String get_date_added() {
        return _date_added;
    }

    /**
     * Sets date added.
     *
     * @param _date_added the date added
     */
    void set_date_added(String _date_added) {
        this._date_added = _date_added;
    }

    /**
     * Gets error text.
     *
     * @return the error text
     */
    String get_error_text() {
        return _error_text;
    }

    /**
     * Sets error text.
     *
     * @param _error_text the error text
     */
    void set_error_text(String _error_text) {
        this._error_text = _error_text;
    }

    /**
     * Gets error type.
     *
     * @return the error type
     */
    String get_error_type() {
        return _error_type;
    }

    /**
     * Sets error type.
     *
     * @param _error_type the error type
     */
    void set_error_type(String _error_type) {
        this._error_type = _error_type;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    int get_user() {
        return _user;
    }

    /**
     * Sets user.
     *
     * @param _user the user
     */
    void set_user(int _user) {
        this._user = _user;
    }

    /**
     * Get array object string [ ].
     *
     * @return the string [ ]
     */
//Array object used for Rockblock
    String[] getArrayObject() {
        String data[] = new String[4];
        try {
            data[0] = "e";
            data[1] = _date_added;
            data[2] = _error_text;
            data[3] = _error_type;
        } catch (Exception e) {
            Log.d("Error: ", e.getMessage());
        }
        return data;
    }

    /**
     * Gets string object.
     *
     * @return the string object
     */
//String used for sending over wi-fi
    String getStringObject() {
        String data = "";
        try {
            data += "e," + _date_added + ","
                    + _error_text + ","
                    + _error_type;
        } catch (Exception e) {
            Log.d("Error: ", e.getMessage());
        }
        return data;
    }

    /**
     * Gets json object.
     *
     * @return the json object
     */
    JSONObject getJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("date_added", _date_added);
            json.put("error_text", _error_text);
            json.put("error_type", _error_type);
            json.put("user_id", Integer.toString(_user));
        } catch (JSONException e) {
            Log.d("Error: ", e.getMessage());
        }
        return json;
    }
}