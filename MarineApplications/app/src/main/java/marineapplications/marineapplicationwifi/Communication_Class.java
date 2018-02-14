package marineapplications.marineapplicationwifi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kieranmoroney on 20/04/2016.
 * Trip Communications Class
 */
class Communication_Class {
    private int id;
    private String _date_added;
    private String _vessel_from;
    private String _vessel_to;
    private String _vessel_comment;
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
     * Gets vessel from.
     *
     * @return the vessel from
     */
    String get_vessel_from() {
        return _vessel_from;
    }

    /**
     * Sets vessel from.
     *
     * @param _vessel_from the vessel from
     */
    void set_vessel_from(String _vessel_from) {
        this._vessel_from = _vessel_from;
    }

    /**
     * Gets vessel to.
     *
     * @return the vessel to
     */
    String get_vessel_to() {
        return _vessel_to;
    }

    /**
     * Sets vessel to.
     *
     * @param _vessel_to the vessel to
     */
    void set_vessel_to(String _vessel_to) {
        this._vessel_to = _vessel_to;
    }

    /**
     * Gets vessel comment.
     *
     * @return the vessel comment
     */
    String get_vessel_comment() {
        return _vessel_comment;
    }

    /**
     * Sets vessel comment.
     *
     * @param _vessel_comment the vessel comment
     */
    void set_vessel_comment(String _vessel_comment) {
        this._vessel_comment = _vessel_comment;
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
            data[0] = "c";
            data[1] = _date_added;
            data[2] = _vessel_from;
            data[3] = _vessel_to;
            data[4] = _vessel_comment;
            data[5] = Integer.toString(_trip);

        } catch (Exception e) {
            Log.d("Error: ", e.getMessage());
        }
        return data;
    }

    /**
     * Used for sending over wi-fi.
     * Gets string object.
     *
     * @return the string object
     */
    String getStringObject() {
        String data = "";
        try {
            data += "c," + _date_added + "," + _vessel_from + ","
                    + _vessel_to + ","
                    + _vessel_comment;
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
            json.put("vessel_from", _vessel_from);
            json.put("vessel_to", _vessel_to);
            json.put("vessel_comment", _vessel_comment);
            json.put("trip_id", Integer.toString(_trip));
            json.put("user_id", Integer.toString(_user));
        } catch (JSONException e) {
            Log.d("Error: ", e.getMessage());
        }
        return json;
    }
}