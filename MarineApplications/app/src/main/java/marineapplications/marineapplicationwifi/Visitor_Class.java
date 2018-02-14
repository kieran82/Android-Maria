package marineapplications.marineapplicationwifi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kieranmoroney on 20/04/2016.
 */
class Visitor_Class {

    private int id;
    private String _date_added;
    private String _visitor_name;
    private String _visitor_reason;
    private String _visitor_comment;
    private int _trip;
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
     * Gets visitor name.
     *
     * @return the visitor name
     */
    String get_visitor_name() {
        return _visitor_name;
    }

    /**
     * Sets visitor name.
     *
     * @param _visitor_name the visitor name
     */
    void set_visitor_name(String _visitor_name) {
        this._visitor_name = _visitor_name;
    }

    /**
     * Gets visitor reason.
     *
     * @return the visitor reason
     */
    String get_visitor_reason() {
        return _visitor_reason;
    }

    /**
     * Sets visitor reason.
     *
     * @param _visitor_reason the visitor reason
     */
    void set_visitor_reason(String _visitor_reason) {
        this._visitor_reason = _visitor_reason;
    }

    /**
     * Gets visitor comment.
     *
     * @return the visitor comment
     */
    String get_visitor_comment() {
        return _visitor_comment;
    }

    /**
     * Sets visitor comment.
     *
     * @param _visitor_comment the visitor comment
     */
    void set_visitor_comment(String _visitor_comment) {
        this._visitor_comment = _visitor_comment;
    }

    /**
     * Gets trip.
     *
     * @return the trip
     */
    int get_trip() {
        return _trip;
    }

    /**
     * Sets trip.
     *
     * @param _trip the trip
     */
    void set_trip(int _trip) {
        this._trip = _trip;
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
        String data[] = new String[6];

        try {
            data[0] = "v";
            data[1] = _date_added;
            data[2] = _visitor_name;
            data[3] = _visitor_reason;
            data[4] = _visitor_comment;
            data[5] = Integer.toString(_trip);

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
            data += "v," + _date_added + "," + _visitor_name + ","
                    + _visitor_reason + ","
                    + _visitor_comment;
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
            json.put("visitor_name", _visitor_name);
            json.put("visitor_reason", _visitor_reason);
            json.put("visitor_comment", _visitor_comment);
            json.put("trip_id", Integer.toString(_trip));
            json.put("user_id", Integer.toString(_user));
        } catch (JSONException e) {
            Log.d("Error: ", e.getMessage());
        }
        return json;
    }
}