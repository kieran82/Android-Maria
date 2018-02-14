package marineapplications.marineapplicationwifi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kieranmoroney on 21/04/2016.
 */
class Marine_Litter_Class {

    private int id;
    private String _date_added;
    private String _vessel_port;
    private String _marine_litter_quantity;
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
     * Gets vessel port.
     *
     * @return the vessel port
     */
    String get_vessel_port() {
        return _vessel_port;
    }

    /**
     * Sets vessel port.
     *
     * @param _vessel_port the vessel port
     */
    void set_vessel_port(String _vessel_port) {
        this._vessel_port = _vessel_port;
    }

    /**
     * Gets marine litter quantity.
     *
     * @return the marine litter quantity
     */
    String get_marine_litter_quantity() {
        return _marine_litter_quantity;
    }

    /**
     * Sets marine litter quantity.
     *
     * @param _marine_litter_quantity the marine litter quantity
     */
    void set_marine_litter_quantity(String _marine_litter_quantity) {
        this._marine_litter_quantity = _marine_litter_quantity;
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
        String data[] = new String[5];
        try {
            data[0] = "m";
            data[1] = _date_added;
            data[2] = _vessel_port;
            data[3] = _marine_litter_quantity;
            data[4] = Integer.toString(_trip);

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
            data += "m," + _date_added + "," + _vessel_port + ","
                    + _marine_litter_quantity;
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
            json.put("vessel_port", _vessel_port);
            json.put("marine_litter_quantity", _marine_litter_quantity);
            json.put("trip_id", Integer.toString(_trip));
            json.put("user_id", Integer.toString(_user));
        } catch (JSONException e) {
            Log.d("Error: ", e.getMessage());
        }
        return json;
    }
}
