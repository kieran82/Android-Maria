package marineapplications.marineapplicationwifi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The type Temperature class.
 */
class Temperature_Class {
    private int id;
    private String _chilled;
    private String _freezer;
    private String _blast;
    private String _core;
    private String _gps;
    private String _input_type;
    private String _date_added;
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
     * Gets chilled.
     *
     * @return the chilled
     */
    String get_chilled() {
        return _chilled;
    }

    /**
     * Sets chilled.
     *
     * @param _chilled the chilled
     */
    void set_chilled(String _chilled) {
        this._chilled = _chilled;
    }

    /**
     * Gets freezer.
     *
     * @return the freezer
     */
    String get_freezer() {
        return _freezer;
    }

    /**
     * Sets freezer.
     *
     * @param _freezer the freezer
     */
    void set_freezer(String _freezer) {
        this._freezer = _freezer;
    }

    /**
     * Gets blast.
     *
     * @return the blast
     */
    String get_blast() {
        return _blast;
    }

    /**
     * Sets blast.
     *
     * @param _blast the blast
     */
    void set_blast(String _blast) {
        this._blast = _blast;
    }

    /**
     * Gets core.
     *
     * @return the core
     */
    String get_core() {
        return _core;
    }

    /**
     * Sets core.
     *
     * @param _core the core
     */
    void set_core(String _core) {
        this._core = _core;
    }

    /**
     * Gets gps.
     *
     * @return the gps
     */
    String get_gps() {
        return _gps;
    }

    /**
     * Sets gps.
     *
     * @param _gps the gps
     */
    void set_gps(String _gps) {
        this._gps = _gps;
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
     * Gets input type.
     *
     * @return the input type
     */
    String get_input_type() {
        return _input_type;
    }

    /**
     * Sets input type.
     *
     * @param _input_type the input type
     */
    void set_input_type(String _input_type) {
        this._input_type = _input_type;
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
        String data[] = new String[10];
        String newgps = _gps.equals("Not Available") ? "" : _gps;
        try {
            data[0] = "t";
            data[1] = _date_added;
            data[2] = _chilled.equals("N/A") ? "E" : _chilled;
            data[3] = _freezer.equals("N/A") ? "E" : _freezer;
            data[4] = _blast.equals("N/A") ? "E" : _blast;
            data[5] = _core.equals("N/A") ? "E" : _core;
            data[6] = newgps;
            data[7] = _input_type;
            data[8] = Integer.toString(_trip);
            data[9] = Integer.toString(_user);

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
        String newgps = _gps.equals("Not Available") ? "" : _gps;
        String newchill = _chilled.equals("N/A") ? "E" : _chilled;
        String newfreezer = _freezer.equals("N/A") ? "E" : _freezer;
        String newblast = _blast.equals("N/A") ? "E" : _blast;
        String newcore = _core.equals("N/A") ? "E" : _core;
        try {
            data += "t," + _date_added + "," + newchill + ","
                    + newfreezer + ","
                    + newblast + ","
                    + newcore + ","
                    + newgps + ","
                    + _input_type;
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
        String newgps = _gps.equals("Not Available") ? "" : _gps;
        try {
            json.put("date_added", _date_added);
            json.put("chilled", _chilled);
            json.put("freezer", _freezer);
            json.put("blast", _blast);
            json.put("core", _core);
            json.put("gps", newgps);
            json.put("input_type", _input_type);
            json.put("trip_id", Integer.toString(_trip));
            json.put("user_id", Integer.toString(_user));
        } catch (JSONException e) {
            Log.d("Error: ", e.getMessage());
        }
        return json;
    }
}