package marineapplications.marineapplicationwifi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kieranmoroney on 25/01/2017.
 */
class Prawn_Dip_Class {
    private int id;
    private String _date_added;
    private int _water_volume;
    private String _dip_product;
    private int _amount_used;
    private String _crew_responsible;
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
     * Gets water volume.
     *
     * @return the water volume
     */
    int get_water_volume() {
        return _water_volume;
    }

    /**
     * Sets water volume.
     *
     * @param _water_volume the water volume
     */
    void set_water_volume(int _water_volume) {
        this._water_volume = _water_volume;
    }

    /**
     * Gets dip product.
     *
     * @return the dip product
     */
    String get_dip_product() {
        return _dip_product;
    }

    /**
     * Sets dip product.
     *
     * @param _dip_product the dip product
     */
    void set_dip_product(String _dip_product) {
        this._dip_product = _dip_product;
    }

    /**
     * Gets amount used.
     *
     * @return the amount used
     */
    int get_amount_used() {
        return _amount_used;
    }

    /**
     * Sets amount used.
     *
     * @param _amount_used the amount used
     */
    void set_amount_used(int _amount_used) {
        this._amount_used = _amount_used;
    }

    /**
     * Gets crew responsible.
     *
     * @return the crew responsible
     */
    String get_crew_responsible() {
        return _crew_responsible;
    }

    /**
     * Sets crew responsible.
     *
     * @param _crew_responsible the crew responsible
     */
    void set_crew_responsible(String _crew_responsible) {
        this._crew_responsible = _crew_responsible;
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
        String data[] = new String[7];
        try {
            data[0] = "pr";
            data[1] = _date_added;
            data[2] = Integer.toString(_water_volume);
            data[3] = _dip_product;
            data[4] = Integer.toString(_amount_used);
            data[5] = _crew_responsible;
            data[6] = Integer.toString(_trip);

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
            data += "p," + _date_added + "," + _water_volume + ","
                    + _dip_product + ","
                    + _amount_used + ","
                    + _crew_responsible;
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
            json.put("water_volume", _water_volume);
            json.put("dip_product", _dip_product);
            json.put("amount_used", _amount_used);
            json.put("crew_responsible", _crew_responsible);
            json.put("trip_id", Integer.toString(_trip));
            json.put("user_id", Integer.toString(_user));
        } catch (JSONException e) {
            Log.d("Error: ", e.getMessage());
        }
        return json;
    }
}
