package marineapplications.marineapplicationwifi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kieranmoroney on 20/04/2016.
 */
class WhaleDolphin_Class {
    private int id;
    private String _date_added;
    private String _gps_lat;
    private String _gps_long;
    private String _species;
    private int _group_size;
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
     * Gets gps lat.
     *
     * @return the gps lat
     */
    String get_gps_lat() {
        return _gps_lat;
    }

    /**
     * Sets gps lat.
     *
     * @param _gps_lat the gps lat
     */
    void set_gps_lat(String _gps_lat) {
        this._gps_lat = _gps_lat;
    }

    /**
     * Gets gps long.
     *
     * @return the gps long
     */
    String get_gps_long() {
        return _gps_long;
    }

    /**
     * Sets gps long.
     *
     * @param _gps_long the gps long
     */
    void set_gps_long(String _gps_long) {
        this._gps_long = _gps_long;
    }

    /**
     * Gets species.
     *
     * @return the species
     */
    String getSpecies() {
        return _species;
    }

    /**
     * Sets species.
     *
     * @param species the species
     */
    void setSpecies(String species) {
        this._species = species;
    }

    /**
     * Gets group size.
     *
     * @return the group size
     */
    int get_group_size() {
        return _group_size;
    }

    /**
     * Sets group size.
     *
     * @param _group_size the group size
     */
    void set_group_size(int _group_size) {
        this._group_size = _group_size;
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
            data[0] = "w";
            data[1] = _date_added;
            data[2] = _gps_lat + " ," + _gps_long;
            data[3] = _species;
            data[4] = Integer.toString(_group_size);
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
        String gps_position = _gps_lat + " ," + _gps_long;
        try {
            data += "w," + _date_added + "," + gps_position + ","
                    + _species + ","
                    + Integer.toString(_group_size);
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
        String gps_position = _gps_lat + " ," + _gps_long;
        try {
            json.put("date_added", _date_added);
            json.put("gps", gps_position);
            json.put("species", _species);
            json.put("group_size", Integer.toString(_group_size));
            json.put("trip_id", Integer.toString(_trip));
            json.put("user_id", Integer.toString(_user));
        } catch (JSONException e) {
            Log.d("Error: ", e.getMessage());
        }
        return json;
    }
}