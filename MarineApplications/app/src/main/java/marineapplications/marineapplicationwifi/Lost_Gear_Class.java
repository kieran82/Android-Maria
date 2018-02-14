package marineapplications.marineapplicationwifi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kieranmoroney on 22/10/2016.
 */
class Lost_Gear_Class {

    private int id;
    private String _date_added;
    private String _gear_name;
    private String _position;
    private int _gear_quantity;
    private String _reason;
    private String _gear_replacement;
    private String _replacement_details;
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
     * Gets gear name.
     *
     * @return the gear name
     */
    String get_gear_name() {
        return _gear_name;
    }

    /**
     * Sets gear name.
     *
     * @param _gear_name the gear name
     */
    void set_gear_name(String _gear_name) {
        this._gear_name = _gear_name;
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    String get_position() {
        return _position;
    }

    /**
     * Sets position.
     *
     * @param _position the position
     */
    void set_position(String _position) {
        this._position = _position;
    }

    /**
     * Gets gear quantity.
     *
     * @return the gear quantity
     */
    int get_gear_quantity() {
        return _gear_quantity;
    }

    /**
     * Sets gear quantity.
     *
     * @param _gear_quantity the gear quantity
     */
    void set_gear_quantity(int _gear_quantity) {
        this._gear_quantity = _gear_quantity;
    }

    /**
     * Gets reason.
     *
     * @return the reason
     */
    String get_reason() {
        return _reason;
    }

    /**
     * Sets reason.
     *
     * @param _reason the reason
     */
    void set_reason(String _reason) {
        this._reason = _reason;
    }

    /**
     * Gets gear replacement.
     *
     * @return the gear replacement
     */
    String get_gear_replacement() {
        return _gear_replacement;
    }

    /**
     * Sets gear replacement.
     *
     * @param _gear_replacement the gear replacement
     */
    void set_gear_replacement(String _gear_replacement) {
        this._gear_replacement = _gear_replacement;
    }

    /**
     * Gets replacement details.
     *
     * @return the replacement details
     */
    String get_replacement_details() {
        return _replacement_details;
    }

    /**
     * Sets replacement details.
     *
     * @param _replacement_details the replacement details
     */
    void set_replacement_details(String _replacement_details) {
        this._replacement_details = _replacement_details;
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
        String data[] = new String[9];
        try {
            data[0] = "l";
            data[1] = _date_added;
            data[2] = _gear_name;
            data[3] = _position;
            data[4] = Integer.toString(_gear_quantity);
            data[5] = _reason;
            data[6] = _gear_replacement;
            data[7] = _replacement_details;
            data[8] = Integer.toString(_trip);

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
            data += "l," + _date_added + ","
                    + _gear_name + ","
                    + _position + ","
                    + Integer.toString(_gear_quantity) + ","
                    + _reason + ","
                    + _gear_replacement + ","
                    + _replacement_details;
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
            json.put("gear_name", _gear_name);
            json.put("position", _position);
            json.put("gear_quantity", Integer.toString(_gear_quantity));
            json.put("reason", _reason);
            json.put("gear_replacement", _gear_replacement);
            json.put("replacement_details", _replacement_details);
            json.put("trip_id", Integer.toString(_trip));
            json.put("user_id", Integer.toString(_user));
        } catch (JSONException e) {
            Log.d("Error: ", e.getMessage());
        }
        return json;
    }
}
