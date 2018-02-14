package marineapplications.marineapplicationwifi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kieranmoroney on 21/04/2016.
 * Dispatch Class
 */
class Dispatch_Class {
    private int id;
    private String _date_added;
    private String _dispatch_quantity;
    private String _dispatch_port;
    private String _dispatch_buyer;
    private String _dispatch_email;
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
     * Gets dispatch quantity.
     *
     * @return the dispatch quantity
     */
    String get_dispatch_quantity() {
        return _dispatch_quantity;
    }

    /**
     * Sets dispatch quantity.
     *
     * @param _dispatch_quantity the dispatch quantity
     */
    void set_dispatch_quantity(String _dispatch_quantity) {
        this._dispatch_quantity = _dispatch_quantity;
    }

    /**
     * Gets dispatch port.
     *
     * @return the dispatch port
     */
    String get_dispatch_port() {
        return _dispatch_port;
    }

    /**
     * Sets dispatch port.
     *
     * @param _dispatch_port the dispatch port
     */
    void set_dispatch_port(String _dispatch_port) {
        this._dispatch_port = _dispatch_port;
    }

    /**
     * Gets dispatch buyer.
     *
     * @return the dispatch buyer
     */
    String get_dispatch_buyer() {
        return _dispatch_buyer;
    }

    /**
     * Sets dispatch buyer.
     *
     * @param _dispatch_buyer the dispatch buyer
     */
    void set_dispatch_buyer(String _dispatch_buyer) {
        this._dispatch_buyer = _dispatch_buyer;
    }

    /**
     * Gets dispatch email.
     *
     * @return the dispatch email
     */
    String get_dispatch_email() {
        return _dispatch_email;
    }

    /**
     * Sets dispatch email.
     *
     * @param _dispatch_email the dispatch email
     */
    void set_dispatch_email(String _dispatch_email) {
        this._dispatch_email = _dispatch_email;
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
        String emails = !_dispatch_email.equals("") ? _dispatch_email : "No";
        String data[] = new String[7];
        try {
            data[0] = "d";
            data[1] = _date_added;
            data[2] = _dispatch_quantity;
            data[3] = _dispatch_port;
            data[4] = _dispatch_buyer;
            data[5] = emails;
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
            data += "d," + _date_added + "," + _dispatch_quantity + ","
                    + _dispatch_port + ","
                    + _dispatch_buyer + ","
                    + _dispatch_email;
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
            json.put("dispatch_quantity", _dispatch_quantity);
            json.put("dispatch_port", _dispatch_port);
            json.put("dispatch_buyer", _dispatch_buyer);
            json.put("dispatch_email", _dispatch_email);
            json.put("trip_id", Integer.toString(_trip));
            json.put("user_id", Integer.toString(_user));
        } catch (JSONException e) {
            Log.d("Error: ", e.getMessage());
        }
        return json;
    }
}