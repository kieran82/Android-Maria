package marineapplications.marineapplicationwifi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kieranmoroney on 20/04/2016.
 * Incident Class
 */
class Incident_Class {
    private int id;
    private String _date_added;
    private String _incident_type;
    private String _incident_details;
    private String _incident_action;
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
     * Gets incident type.
     *
     * @return the incident type
     */
    String get_incident_type() {
        return _incident_type;
    }

    /**
     * Sets incident type.
     *
     * @param _incident_type the incident type
     */
    void set_incident_type(String _incident_type) {
        this._incident_type = _incident_type;
    }

    /**
     * Gets incident details.
     *
     * @return the incident details
     */
    String get_incident_details() {
        return _incident_details;
    }

    /**
     * Sets incident details.
     *
     * @param _incident_details the incident details
     */
    void set_incident_details(String _incident_details) {
        this._incident_details = _incident_details;
    }

    /**
     * Gets incident action.
     *
     * @return the incident action
     */
    String get_incident_action() {
        return _incident_action;
    }

    /**
     * Sets incident action.
     *
     * @param _incident_action the incident action
     */
    void set_incident_action(String _incident_action) {
        this._incident_action = _incident_action;
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
            data[0] = "i";
            data[1] = _date_added;
            data[2] = _incident_type;
            data[3] = _incident_details;
            data[4] = _incident_action;
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
            data += "i," + _date_added + "," + _incident_type + ","
                    + _incident_details + ","
                    + _incident_action;
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
            json.put("incident_type", _incident_type);
            json.put("incident_details", _incident_details);
            json.put("incident_action", _incident_action);
            json.put("trip_id", Integer.toString(_trip));
            json.put("user_id", Integer.toString(_user));
        } catch (JSONException e) {
            Log.d("Error: ", e.getMessage());
        }
        return json;
    }
}