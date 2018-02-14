package marineapplications.marineapplicationwifi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kieranmoroney on 21/04/2016.
 */
class Slippage_Class {

    private int id;
    private String _date_added;
    private String _ices_sub_area;
    private String _position;
    private String _target_fisheries;
    private String _species_slipped;
    private String _slippage_reason;
    private String _sample_taken;
    private int _species_quantity;
    private int _species_size;
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
     * Gets ices sub area.
     *
     * @return the ices sub area
     */
    String get_ices_sub_area() {
        return _ices_sub_area;
    }

    /**
     * Sets ices sub area.
     *
     * @param _ices_sub_area the ices sub area
     */
    void set_ices_sub_area(String _ices_sub_area) {
        this._ices_sub_area = _ices_sub_area;
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
     * Gets target fisheries.
     *
     * @return the target fisheries
     */
    String get_target_fisheries() {
        return _target_fisheries;
    }

    /**
     * Sets target fisheries.
     *
     * @param _target_fisheries the target fisheries
     */
    void set_target_fisheries(String _target_fisheries) {
        this._target_fisheries = _target_fisheries;
    }

    /**
     * Gets species slipped.
     *
     * @return the species slipped
     */
    String get_species_slipped() {
        return _species_slipped;
    }

    /**
     * Sets species slipped.
     *
     * @param _species_slipped the species slipped
     */
    void set_species_slipped(String _species_slipped) {
        this._species_slipped = _species_slipped;
    }

    /**
     * Gets slippage reason.
     *
     * @return the slippage reason
     */
    String get_slippage_reason() {
        return _slippage_reason;
    }

    /**
     * Sets slippage reason.
     *
     * @param _slippage_reason the slippage reason
     */
    void set_slippage_reason(String _slippage_reason) {
        this._slippage_reason = _slippage_reason;
    }

    /**
     * Gets sample taken.
     *
     * @return the sample taken
     */
    String get_sample_taken() {
        return _sample_taken;
    }

    /**
     * Sets sample taken.
     *
     * @param _sample_taken the sample taken
     */
    void set_sample_taken(String _sample_taken) {
        this._sample_taken = _sample_taken;
    }

    /**
     * Gets species quantity.
     *
     * @return the species quantity
     */
    int get_species_quantity() {
        return _species_quantity;
    }

    /**
     * Sets species quantity.
     *
     * @param _species_quantity the species quantity
     */
    void set_species_quantity(int _species_quantity) {
        this._species_quantity = _species_quantity;
    }

    /**
     * Gets species size.
     *
     * @return the species size
     */
    int get_species_size() {
        return _species_size;
    }

    /**
     * Sets species size.
     *
     * @param _species_size the species size
     */
    void set_species_size(int _species_size) {
        this._species_size = _species_size;
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
        String data[] = new String[11];
        int sample_taken = _sample_taken.equals("yes") ? 1 : 0;

        try {
            data[0] = "s";
            data[1] = _date_added;
            data[2] = _ices_sub_area;
            data[3] = _position;
            data[4] = _target_fisheries;
            data[5] = _species_slipped;
            data[6] = Integer.toString(_species_quantity);
            data[7] = Integer.toString(_species_size);
            data[8] = _slippage_reason;
            data[9] = Integer.toString(sample_taken);
            data[10] = Integer.toString(_trip);

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
            data += "s," + _date_added + "," + _ices_sub_area + ","
                    + _position + ","
                    + _target_fisheries + ","
                    + _species_slipped + ","
                    + Integer.toString(_species_quantity) + ","
                    + Integer.toString(_species_size) + ","
                    + _slippage_reason + ","
                    + _sample_taken;
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
            json.put("ices_sub_area", _ices_sub_area);
            json.put("position", _position);
            json.put("target_fisheries", _target_fisheries);
            json.put("species_slipped", _species_slipped);
            json.put("species_quantity", Integer.toString(_species_quantity));
            json.put("species_size", Integer.toString(_species_size));
            json.put("slippage_reason", _slippage_reason);
            json.put("sample_taken", _sample_taken);
            json.put("trip_id", Integer.toString(_trip));
            json.put("user_id", Integer.toString(_user));
        } catch (JSONException e) {
            Log.d("Error: ", e.getMessage());
        }
        return json;
    }
}