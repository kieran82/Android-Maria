package marineapplications.marineapplicationwifi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kieranmoroney on 21/04/2016.
 */
class Trip_Class {

    private int id;
    private int _online_id;
    private int _user;
    private String _start_date;
    private String _end_date;
    private String _log_no;
    private String _cleaning_schedule_completed;
    private String _prawn_dip;
    private String _scales_test;
    private String _target_procedure;
    private String _line_caught_procedure;
    private String _waste_ashore;
    private String _trip_type;
    private String _device_type;
    private int _prawn_counter;

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
     * Gets online id.
     *
     * @return the online id
     */
    int get_online_id() {
        return _online_id;
    }

    /**
     * Sets online id.
     *
     * @param _online_id the online id
     */
    void set_online_id(int _online_id) {
        this._online_id = _online_id;
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
     * Gets start date.
     *
     * @return the start date
     */
    String get_start_date() {
        return _start_date;
    }

    /**
     * Sets start date.
     *
     * @param _start_date the start date
     */
    void set_start_date(String _start_date) {
        this._start_date = _start_date;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    String get_end_date() {
        return _end_date;
    }

    /**
     * Sets end date.
     *
     * @param _end_date the end date
     */
    void set_end_date(String _end_date) {
        this._end_date = _end_date;
    }

    /**
     * Gets log no.
     *
     * @return the log no
     */
    String get_log_no() {
        return _log_no;
    }

    /**
     * Sets log no.
     *
     * @param _log_no the log no
     */
    void set_log_no(String _log_no) {
        this._log_no = _log_no;
    }

    /**
     * Gets cleaning schedule completed.
     *
     * @return the cleaning schedule completed
     */
    String get_cleaning_schedule_completed() {
        return _cleaning_schedule_completed;
    }

    /**
     * Sets cleaning schedule completed.
     *
     * @param _cleaning_schedule_completed the cleaning schedule completed
     */
    void set_cleaning_schedule_completed(String _cleaning_schedule_completed) {
        this._cleaning_schedule_completed = _cleaning_schedule_completed;
    }

    /**
     * Gets prawn dip.
     *
     * @return the prawn dip
     */
    String get_prawn_dip() {
        return _prawn_dip;
    }

    /**
     * Sets prawn dip.
     *
     * @param _prawn_dip the prawn dip
     */
    void set_prawn_dip(String _prawn_dip) {
        this._prawn_dip = _prawn_dip;
    }

    /**
     * Gets scales test.
     *
     * @return the scales test
     */
    String get_scales_test() {
        return _scales_test;
    }

    /**
     * Sets scales test.
     *
     * @param _scales_test the scales test
     */
    void set_scales_test(String _scales_test) {
        this._scales_test = _scales_test;
    }

    /**
     * Gets target procedure.
     *
     * @return the target procedure
     */
    String get_target_procedure() {
        return _target_procedure;
    }

    /**
     * Sets target procedure.
     *
     * @param _target_procedure the target procedure
     */
    void set_target_procedure(String _target_procedure) {
        this._target_procedure = _target_procedure;
    }

    /**
     * Gets line caught procedure.
     *
     * @return the line caught procedure
     */
    String get_line_caught_procedure() {
        return _line_caught_procedure;
    }

    /**
     * Sets line caught procedure.
     *
     * @param _line_caught_procedure the line caught procedure
     */
    void set_line_caught_procedure(String _line_caught_procedure) {
        this._line_caught_procedure = _line_caught_procedure;
    }

    /**
     * Gets waste ashore.
     *
     * @return the waste ashore
     */
    String get_waste_ashore() {
        return _waste_ashore;
    }

    /**
     * Sets waste ashore.
     *
     * @param _waste_ashore the waste ashore
     */
    void set_waste_ashore(String _waste_ashore) {
        this._waste_ashore = _waste_ashore;
    }

    /**
     * Gets trip type.
     *
     * @return the trip type
     */
    String get_trip_type() {
        return _trip_type;
    }

    /**
     * Sets trip type.
     *
     * @param _trip_type the trip type
     */
    void set_trip_type(String _trip_type) {
        this._trip_type = _trip_type;
    }

    /**
     * Gets device type.
     *
     * @return the device type
     */
    String get_device_type() {
        return _device_type;
    }

    /**
     * Sets device type.
     *
     * @param _device_type the device type
     */
    void set_device_type(String _device_type) {
        this._device_type = _device_type;
    }

    /**
     * Gets prawn counter.
     *
     * @return the prawn counter
     */
    public int get_prawn_counter() {
        return _prawn_counter;
    }

    /**
     * Sets prawn counter.
     *
     * @param _prawn_counter the prawn counter
     */
    public void set_prawn_counter(int _prawn_counter) {
        this._prawn_counter = _prawn_counter;
    }

    /**
     * Get array object string [ ].
     *
     * @return the string [ ]
     */
//Array object used for Rockblock
    String[] getArrayObject() {
        String data[] = new String[14];
        try {
            int clean = _cleaning_schedule_completed.equals("yes") ? 1 : 0;
            int prawn_dip = _prawn_dip.equals("yes") ? 1 : 0;
            int scales_test = _scales_test.equals("yes") ? 1 : 0;
            int target_procedure = _target_procedure.equals("yes") ? 1 : 0;
            int line_procedure = _line_caught_procedure.equals("yes") ? 1 : 0;
            int waste = _waste_ashore.equals("yes") ? 1 : 0;

            data[0] = "r";
            data[1] = Integer.toString(id);
            data[2] = Integer.toString(_online_id);
            data[3] = _start_date;
            data[4] = _end_date;
            data[5] = _log_no;
            data[6] = Integer.toString(clean);
            data[7] = Integer.toString(prawn_dip);
            data[8] = Integer.toString(scales_test);
            data[9] = Integer.toString(target_procedure);
            data[10] = Integer.toString(line_procedure);
            data[11] = Integer.toString(waste);
            data[12] = _trip_type;
            data[13] = _device_type;

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
        String newcclean = _cleaning_schedule_completed.equals("empty") ? "E" : _cleaning_schedule_completed;
        String newprawn = _prawn_dip.equals("empty") ? "E" : _prawn_dip;
        String newscales = _scales_test.equals("empty") ? "E" : _scales_test;
        String newtarget = _target_procedure.equals("empty") ? "E" : _target_procedure;
        String newline = _line_caught_procedure.equals("empty") ? "E" : _line_caught_procedure;
        String newwaste = _waste_ashore.equals("empty") ? "E" : _waste_ashore;
        try {
            data += "r," + Integer.toString(id) + ","
                    + Integer.toString(_online_id) + ","
                    + Integer.toString(_user) + ","
                    + _start_date + ","
                    + _end_date + ","
                    + _log_no + ","
                    + newcclean + ","
                    + newprawn + ","
                    + newscales + ","
                    + newtarget + "," + newline + ","
                    + newwaste + ","
                    + _trip_type + ","
                    + _device_type + ","
                    + _prawn_counter + "";
        } catch (Exception e) {
            Log.d("Error: ", e.getMessage());
        }
        return data;
    }

    /**
     * Gets start trip string object.
     *
     * @return the start trip string object
     */
    String getStartTripStringObject() {
        String data = "";
        try {
            data += Integer.toString(id) + ","
                    + Integer.toString(_user) + ","
                    + _start_date + ","
                    + _log_no + ","
                    + _trip_type + ","
                    + _device_type;
        } catch (Exception e) {
            Log.d("Error: ", e.getMessage());
        }
        return data;
    }

    /**
     * Gets start trip json object.
     *
     * @return the start trip json object
     */
    JSONObject getStartTripJSONObject() {
        JSONObject json = new JSONObject();
        try {
            String online_trip_id = Integer.toString(_online_id);
            json.put("trip_id", Integer.toString(id));
            json.put("online_trip_id", Integer.toString(_online_id));
            json.put("user_id", Integer.toString(_user));
            json.put("start_date", _start_date);
            json.put("log_sheet_no", _log_no);
            json.put("trip_type", _trip_type);
            json.put("device_type", _device_type);
            json.put("online_trip_available", !online_trip_id.equals("") ? "yes" : "no");
        } catch (JSONException e) {
            Log.d("Error: ", e.getMessage());
        }
        return json;
    }

    /**
     * Gets json object.
     *
     * @return the json object
     */
    JSONObject getJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("trip_id", Integer.toString(id));
            json.put("online_trip_id", Integer.toString(_online_id));
            json.put("user_id", Integer.toString(_user));
            json.put("start_date", _start_date);
            json.put("end_date", _end_date);
            json.put("log_sheet_no", _log_no);
            json.put("cleaning_schedule_completed", _cleaning_schedule_completed);
            json.put("prawn_dip", _prawn_dip);
            json.put("scales_test", _scales_test);
            json.put("target_procedure", _target_procedure);
            json.put("line_caught_procedure", _line_caught_procedure);
            json.put("waste_ashore", _waste_ashore);
            json.put("trip_type", _trip_type);
            json.put("device_type", _device_type);
            json.put("prawn_counter", _prawn_counter);
        } catch (JSONException e) {
            Log.d("Error: ", e.getMessage());
        }
        return json;
    }
}