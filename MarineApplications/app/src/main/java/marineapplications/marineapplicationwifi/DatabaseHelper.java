package marineapplications.marineapplicationwifi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kieranmoroney on 22/04/2016.
 */
class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "MariaSystemAppDB";

    // Table Name
    private static final String TRIP_RECORD = "trip_record";
    private static final String TEMPERATURE_RECORD = "temperature_record";
    private static final String WHALE_DOLPIN_RECORD = "whale_dolpin_record";
    private static final String COMMUNICATION_RECORD = "communication_record";
    private static final String DISPATCH_RECORD = "dispatch_record";
    private static final String INCIDENT_RECORD = "incident_record";
    private static final String MARINE_LITTER_RECORD = "marine_litter_record";
    private static final String SLIPPAGE_RECORD = "slippage_record";
    private static final String LOST_GEAR_RECORD = "lost_gear_record";
    private static final String VISITOR_RECORD = "visitor_record";
    private static final String PRAWN_DIP_RECORD = "prawn_dip_record";
    private static final String ERROR_LOG_RECORD = "error_log_record";

    //Trip Records Column Name
    private static final String KEY_ID = "id";
    private static final String KEY_ONLINE_TRIP_ID = "ònline_trip_id";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_START_DATE = "start_date";
    private static final String KEY_END_DATE = "end_date";
    private static final String KEY_LOG_SHEET_NO = "log_sheet_no";
    private static final String KEY_CLEANING_SCHEDULE = "cleaning_schedule";
    private static final String KEY_PRAWN_DIP = "prawn_dip";
    private static final String KEY_SCALES_TEST = "scales_test";
    private static final String KEY_TARGET_PROCEDURE = "target_procedure";
    private static final String KEY_LINE_CAUGHT_PROCEDURE = "line_caught_procedure";
    private static final String KEY_WASTE_ASHORE = "waste_ashore";
    private static final String KEY_DEVICE_TYPE = "device_type";
    private static final String KEY_TRIP_TYPE = "trip_type";
    private static final String KEY_PRAWN_DIP_COUNTER = "prawn_dip_counter";

    //Temperature Records Column Name
    private static final String KEY_DATE_ADDED = "date_added";
    private static final String KEY_GPS = "gps";
    private static final String KEY_CHILLED = "chilled";
    private static final String KEY_FREEZER = "freezer";
    private static final String KEY_BLAST = "blast";
    private static final String KEY_CORE = "core";
    private static final String KEY_INPUT_TYPE = "input_type";
    private static final String KEY_TRIP_ID = "trip";

    //Whale And Dolphin Records Column Name
    private static final String KEY_GPS_LAT = "gps_lat";
    private static final String KEY_GPS_LONG = "gps_long";
    private static final String KEY_SPECIES = "species";
    private static final String KEY_GROUP_SIZE = "group_size";

    //Communication Records Column Name
    private static final String KEY_VESSEL_FROM = "vessel_from";
    private static final String KEY_VESSEL_TO = "vessel_to";
    private static final String KEY_COMMENT_TEXT = "comment";

    //Dispatch Records Column Name
    private static final String KEY_DISPATCH_QUANTITY = "dispatch_quantity";
    private static final String KEY_DISPATCH_PORT = "dispatch_port";
    private static final String KEY_DISPATCH_BUYER = "dispatch_buyer";
    private static final String KEY_DISPATCH_EMAIL = "dispatch_email";

    //Incident Records Column Name
    private static final String KEY_INCIDENT_TYPE = "incident_type";
    private static final String KEY_INCIDENT_DETAILS = "incident_details";
    private static final String KEY_INCIDENT_ACTION = "incident_action";

    //Marine Litter Records Column Name
    private static final String KEY_VESSEL_PORT = "vessel_port";
    private static final String KEY_MARINE_LITTER_QUANTITY = "marine_litter_quantity";

    //Slippage Records Column Name
    private static final String KEY_ICES_SUB_AREA = "ices_sub_area";
    private static final String KEY_VESSEL_POSITION = "vessel_position";
    private static final String KEY_TARGET_FISHERIES = "target_fisheries";
    private static final String KEY_SPECIES_SLIPPED = "species_slipped";
    private static final String KEY_LIPPAGE_REASON = "slippage_reason";
    private static final String KEY_SAMPLE_TAKEN = "sample_taken";
    private static final String KEY_SPECIES_QUANTITY = "species_quantity";
    private static final String KEY_SPECIES_SIZE = "species_size";

    //Lost Gear Records Column Name
    private static final String KEY_GEAR_NAME = "gear_name";
    private static final String KEY_GEAR_POSITION = "gear_position";
    private static final String KEY_GEAR_QUANTITY = "gear_quantity";
    private static final String KEY_GEAR_REASON = "gear_reason";
    private static final String KEY_GEAR_REPLACEMENT = "gear_replacement";
    private static final String KEY_GEAR_REPLACEMENT_DETAILS = "gear_replacement_details";

    //Vistior Records Column Name
    private static final String KEY_VISITOR_NAME = "visitor_name";
    private static final String KEY_VISITOR_REASON = "visitor_reason";
    private static final String KEY_VISITOR_COMMENT = "visitor_comment";

    //Prawn Dip Records Column Name
    private static final String KEY_WATER_VOLUME = "water_volume";
    private static final String KEY_DIP_PRODUCT = "dip_product";
    private static final String KEY_AMOUNT_USED = "amount_used";
    private static final String KEY_CREW_RESPONSIBLE = "crew_responsible";

    //Error Records Column Name
    private static final String KEY_ERROR_TEXT = "error_text";
    private static final String KEY_ERROR_TYPE = "error_type";
    /**
     * The Create new trip table.
     */
    //Table String
    //Trip Records Column Name
    private String CREATE_NEW_TRIP_TABLE = "CREATE TABLE " + TRIP_RECORD +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_ONLINE_TRIP_ID + " INTEGER, " +
            KEY_USER_ID + " INTEGER, " +
            KEY_START_DATE + " TEXT, " +
            KEY_END_DATE + " TEXT, " +
            KEY_LOG_SHEET_NO + " TEXT, " +
            KEY_CLEANING_SCHEDULE + " TEXT, " +
            KEY_PRAWN_DIP + " TEXT, " +
            KEY_SCALES_TEST + " TEXT, " +
            KEY_TARGET_PROCEDURE + " TEXT, " +
            KEY_LINE_CAUGHT_PROCEDURE + " TEXT, " +
            KEY_WASTE_ASHORE + " TEXT, " +
            KEY_TRIP_TYPE + " TEXT, " +
            KEY_DEVICE_TYPE + " TEXT, " +
            KEY_PRAWN_DIP_COUNTER + " TEXT)";
    /**
     * The Create new temp table.
     */
    //Temperature Records Column Name
    private String CREATE_NEW_TEMP_TABLE = "CREATE TABLE " + TEMPERATURE_RECORD +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DATE_ADDED + " TEXT, " +
            KEY_GPS + " TEXT, " +
            KEY_CHILLED + " INTEGER, " +
            KEY_FREEZER + " INTEGER, " +
            KEY_BLAST + " INTEGER, " +
            KEY_CORE + " INTEGER, " +
            KEY_INPUT_TYPE + " TEXT, " +
            KEY_TRIP_ID + " INTEGER, " +
            KEY_USER_ID + " INTEGER)";
    /**
     * The Create new whale table.
     */
    //Whale And Dolphin Records Column Name
    private String CREATE_NEW_WHALE_TABLE = "CREATE TABLE " + WHALE_DOLPIN_RECORD +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DATE_ADDED + " TEXT, " +
            KEY_GPS_LAT + " TEXT, " +
            KEY_GPS_LONG + " TEXT, " +
            KEY_SPECIES + " TEXT, " +
            KEY_GROUP_SIZE + " INTEGER, " +
            KEY_TRIP_ID + " INTEGER, " +
            KEY_USER_ID + " INTEGER)";
    /**
     * The Create new comm table.
     */
    //Communication Records Column Name
    private String CREATE_NEW_COMM_TABLE = "CREATE TABLE " + COMMUNICATION_RECORD +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DATE_ADDED + " TEXT, " +
            KEY_VESSEL_FROM + " TEXT, " +
            KEY_VESSEL_TO + " TEXT, " +
            KEY_COMMENT_TEXT + " TEXT, " +
            KEY_TRIP_ID + " INTEGER, " +
            KEY_USER_ID + " INTEGER)";
    /**
     * The Create new dispatch table.
     */
    //Dispatch Records Column Name
    private String CREATE_NEW_DISPATCH_TABLE = "CREATE TABLE " + DISPATCH_RECORD +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DATE_ADDED + " TEXT, " +
            KEY_DISPATCH_QUANTITY + " TEXT, " +
            KEY_DISPATCH_PORT + " TEXT, " +
            KEY_DISPATCH_BUYER + " TEXT, " +
            KEY_DISPATCH_EMAIL + " TEXT, " +
            KEY_TRIP_ID + " INTEGER, " +
            KEY_USER_ID + " INTEGER)";
    /**
     * The Create new incident table.
     */
    //Incident Records Column Name
    private String CREATE_NEW_INCIDENT_TABLE = "CREATE TABLE " + INCIDENT_RECORD +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DATE_ADDED + " TEXT, " +
            KEY_INCIDENT_TYPE + " TEXT, " +
            KEY_INCIDENT_DETAILS + " TEXT, " +
            KEY_INCIDENT_ACTION + " TEXT, " +
            KEY_TRIP_ID + " INTEGER, " +
            KEY_USER_ID + " INTEGER)";
    /**
     * The Create new marine table.
     */
    //Marine Litter Records Column Name
    private String CREATE_NEW_MARINE_TABLE = "CREATE TABLE " + MARINE_LITTER_RECORD +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DATE_ADDED + " TEXT, " +
            KEY_VESSEL_PORT + " TEXT, " +
            KEY_MARINE_LITTER_QUANTITY + " TEXT, " +
            KEY_TRIP_ID + " INTEGER, " +
            KEY_USER_ID + " INTEGER)";
    /**
     * The Create new slippage table.
     */
    //Slippage Records Column Name
    private String CREATE_NEW_SLIPPAGE_TABLE = "CREATE TABLE " + SLIPPAGE_RECORD +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DATE_ADDED + " TEXT, " +
            KEY_ICES_SUB_AREA + " TEXT, " +
            KEY_VESSEL_POSITION + " TEXT, " +
            KEY_TARGET_FISHERIES + " TEXT, " +
            KEY_SPECIES_SLIPPED + " TEXT, " +
            KEY_LIPPAGE_REASON + " TEXT, " +
            KEY_SAMPLE_TAKEN + " TEXT, " +
            KEY_SPECIES_QUANTITY + " INTEGER, " +
            KEY_SPECIES_SIZE + " INTEGER, " +
            KEY_TRIP_ID + " INTEGER, " +
            KEY_USER_ID + " INTEGER)";
    /**
     * The Create new lost gear table.
     */
    //Lost Gear Records Column Name
    private String CREATE_NEW_LOST_GEAR_TABLE = "CREATE TABLE " + LOST_GEAR_RECORD +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DATE_ADDED + " TEXT, " +
            KEY_GEAR_NAME + " TEXT, " +
            KEY_GEAR_POSITION + " TEXT, " +
            KEY_GEAR_QUANTITY + " INTEGER, " +
            KEY_GEAR_REASON + " TEXT, " +
            KEY_GEAR_REPLACEMENT + " TEXT, " +
            KEY_GEAR_REPLACEMENT_DETAILS + " TEXT, " +
            KEY_TRIP_ID + " INTEGER, " +
            KEY_USER_ID + " INTEGER)";
    /**
     * The Create new visitor table.
     */
    //Vistior Records Column Name
    private String CREATE_NEW_VISITOR_TABLE = "CREATE TABLE " + VISITOR_RECORD +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DATE_ADDED + " TEXT, " +
            KEY_VISITOR_NAME + " TEXT, " +
            KEY_VISITOR_REASON + " TEXT, " +
            KEY_VISITOR_COMMENT + " TEXT, " +
            KEY_TRIP_ID + " INTEGER, " +
            KEY_USER_ID + " INTEGER)";
    /**
     * The Create new prawn dip table.
     */
    //Prawn Dip Records Column Name
    private String CREATE_NEW_prawn_dip_TABLE = "CREATE TABLE " + PRAWN_DIP_RECORD +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DATE_ADDED + " TEXT, " +
            KEY_WATER_VOLUME + " INTEGER, " +
            KEY_DIP_PRODUCT + " TEXT, " +
            KEY_AMOUNT_USED + " INTEGER, " +
            KEY_CREW_RESPONSIBLE + " TEXT, " +
            KEY_TRIP_ID + " INTEGER, " +
            KEY_USER_ID + " INTEGER)";
    /**
     * The Create new error table.
     */
    //Error Records Column Name
    private String CREATE_NEW_ERROR_TABLE = "CREATE TABLE " + ERROR_LOG_RECORD +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DATE_ADDED + " TEXT, " +
            KEY_ERROR_TYPE + " TEXT, " +
            KEY_ERROR_TEXT + " TEXT, " +
            KEY_USER_ID + " INTEGER)";

    /**
     * Instantiates a new Database helper.
     *
     * @param context the context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEW_TEMP_TABLE);
        db.execSQL(CREATE_NEW_WHALE_TABLE);
        db.execSQL(CREATE_NEW_COMM_TABLE);
        db.execSQL(CREATE_NEW_DISPATCH_TABLE);
        db.execSQL(CREATE_NEW_INCIDENT_TABLE);
        db.execSQL(CREATE_NEW_MARINE_TABLE);
        db.execSQL(CREATE_NEW_SLIPPAGE_TABLE);
        db.execSQL(CREATE_NEW_LOST_GEAR_TABLE);
        db.execSQL(CREATE_NEW_TRIP_TABLE);
        db.execSQL(CREATE_NEW_VISITOR_TABLE);
        db.execSQL(CREATE_NEW_ERROR_TABLE);
        db.execSQL(CREATE_NEW_prawn_dip_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TEMPERATURE_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + WHALE_DOLPIN_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + COMMUNICATION_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + DISPATCH_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + INCIDENT_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + MARINE_LITTER_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + SLIPPAGE_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + LOST_GEAR_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + VISITOR_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + ERROR_LOG_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + PRAWN_DIP_RECORD);

        // Create tables again
        onCreate(db);
    }

    //Trip Records Functions

    /**
     * Add trip record int.
     *
     * @param record the record
     * @return the int
     */
// Adding new record
    int addTripRecord(Trip_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result;
        ContentValues values = new ContentValues();
        values.put(KEY_ONLINE_TRIP_ID, record.get_online_id());
        values.put(KEY_START_DATE, record.get_start_date());
        values.put(KEY_END_DATE, record.get_end_date());
        values.put(KEY_LOG_SHEET_NO, record.get_log_no());
        values.put(KEY_USER_ID, record.get_user());
        values.put(KEY_CLEANING_SCHEDULE, record.get_cleaning_schedule_completed());
        values.put(KEY_PRAWN_DIP, record.get_prawn_dip());
        values.put(KEY_SCALES_TEST, record.get_scales_test());
        values.put(KEY_TARGET_PROCEDURE, record.get_target_procedure());
        values.put(KEY_LINE_CAUGHT_PROCEDURE, record.get_line_caught_procedure());
        values.put(KEY_WASTE_ASHORE, record.get_waste_ashore());
        values.put(KEY_TRIP_TYPE, record.get_trip_type());
        values.put(KEY_DEVICE_TYPE, record.get_device_type());
        values.put(KEY_PRAWN_DIP_COUNTER, record.get_prawn_counter());

        // Inserting Row
        long rowInserted = db.insert(TRIP_RECORD, null, values);
        if (rowInserted == -1) {
            result = 0;
        } else {
            result = (int) rowInserted;
        }
        db.close(); // Closing database connection
        return result;
    }

    /**
     * Update online trip id.
     *
     * @param record the record
     * @return the boolean
     */
    boolean updateOnlineTripID(Trip_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ONLINE_TRIP_ID, record.get_online_id());

        // updating row
        int result = db.update(TRIP_RECORD, values, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
        return result > 0;
    }

    /**
     * Update prawn dip counter boolean.
     *
     * @param record the record
     * @return the boolean
     */
    boolean updatePrawnDipCounter(Trip_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRAWN_DIP_COUNTER, record.get_prawn_counter());

        // updating row
        int result = db.update(TRIP_RECORD, values, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
        return result > 0;
    }

    /**
     * Update trip record.
     *
     * @param record the record
     * @return the boolean
     */
    boolean updateTripRecord(Trip_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_END_DATE, record.get_end_date());
        values.put(KEY_CLEANING_SCHEDULE, record.get_cleaning_schedule_completed());
        values.put(KEY_PRAWN_DIP, record.get_prawn_dip());
        values.put(KEY_SCALES_TEST, record.get_scales_test());
        values.put(KEY_TARGET_PROCEDURE, record.get_target_procedure());
        values.put(KEY_LINE_CAUGHT_PROCEDURE, record.get_line_caught_procedure());
        values.put(KEY_WASTE_ASHORE, record.get_waste_ashore());

        // updating row
        result = db.update(TRIP_RECORD, values, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
        // return result > 0;
        return result > 0;
    }

    /**
     * Gets trip record.
     *
     * @param id the id
     * @return the trip record
     */
// Getting single record
    Trip_Class getTripRecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TRIP_RECORD, new String[]{KEY_ID,
                        KEY_START_DATE, KEY_LOG_SHEET_NO}, KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Trip_Class record = new Trip_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_start_date(cursor.getString(1));
        record.set_log_no(cursor.getString(2));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets all current trip records.
     *
     * @param id the id
     * @return the all current trip records
     */
// Getting All records
    List<Trip_Class> getAllCurrentTripRecords(String id) {
        List<Trip_Class> recordList = new ArrayList<Trip_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TRIP_RECORD + " WHERE " + KEY_ID + " = " + id + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Trip_Class record = new Trip_Class();
                record.setId(cursor.getInt(0));
                record.set_online_id(Integer.parseInt(cursor.getString(1)));
                record.set_user(Integer.parseInt(cursor.getString(2)));
                record.set_start_date(cursor.getString(3));
                record.set_end_date(cursor.getString(4));
                record.set_log_no(cursor.getString(5));
                record.set_cleaning_schedule_completed(cursor.getString(6));
                record.set_prawn_dip(cursor.getString(7));
                record.set_scales_test(cursor.getString(8));
                record.set_target_procedure(cursor.getString(9));
                record.set_line_caught_procedure(cursor.getString(10));
                record.set_waste_ashore(cursor.getString(11));
                record.set_trip_type(cursor.getString(12));
                record.set_device_type(cursor.getString(13));
                record.set_prawn_counter(Integer.parseInt(cursor.getString(14)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return record list
        return recordList;
    }

    /**
     * Gets current trip records.
     *
     * @param id the id
     * @return the current trip records
     */
// Getting All records
    Trip_Class getCurrentTripRecords(String id) {
        // Select All Query
        String selectQuery = "SELECT * FROM " + TRIP_RECORD + " WHERE " + KEY_ID + " = " + id + "";
        Trip_Class record = new Trip_Class();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            record.setId(cursor.getInt(0));
            record.set_online_id(Integer.parseInt(cursor.getString(1)));
            record.set_user(Integer.parseInt(cursor.getString(2)));
            record.set_start_date(cursor.getString(3));
            record.set_end_date(cursor.getString(4));
            record.set_log_no(cursor.getString(5));
            record.set_cleaning_schedule_completed(cursor.getString(6));
            record.set_prawn_dip(cursor.getString(7));
            record.set_scales_test(cursor.getString(8));
            record.set_target_procedure(cursor.getString(9));
            record.set_line_caught_procedure(cursor.getString(10));
            record.set_waste_ashore(cursor.getString(11));
            record.set_trip_type(cursor.getString(12));
            record.set_device_type(cursor.getString(13));
            record.set_prawn_counter(Integer.parseInt(cursor.getString(14)));
        }

        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets all trip records.
     *
     * @return the all trip records
     */
// Getting All records
    List<Trip_Class> getAllTripRecords() {
        List<Trip_Class> recordList = new ArrayList<Trip_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TRIP_RECORD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Trip_Class record = new Trip_Class();
                record.setId(cursor.getInt(0));
                record.set_online_id(Integer.parseInt(cursor.getString(1)));
                record.set_user(Integer.parseInt(cursor.getString(2)));
                record.set_start_date(cursor.getString(3));
                record.set_end_date(cursor.getString(4));
                record.set_log_no(cursor.getString(5));
                record.set_cleaning_schedule_completed(cursor.getString(6));
                record.set_prawn_dip(cursor.getString(7));
                record.set_scales_test(cursor.getString(8));
                record.set_target_procedure(cursor.getString(9));
                record.set_line_caught_procedure(cursor.getString(10));
                record.set_waste_ashore(cursor.getString(11));
                record.set_trip_type(cursor.getString(12));
                record.set_device_type(cursor.getString(13));
                record.set_prawn_counter(Integer.parseInt(cursor.getString(14)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return record list
        return recordList;
    }

    /**
     * Gets open current trip record.
     *
     * @return the open current trip record
     */
// Getting single record
    Trip_Class getOpenCurrentTripRecord() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TRIP_RECORD + " WHERE " + KEY_END_DATE + " IS NULL AND " + KEY_TRIP_TYPE + " = 'current'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        Trip_Class record = new Trip_Class();
        record.setId(cursor.getInt(0));
        record.set_online_id(Integer.parseInt(cursor.getString(1)));
        record.set_user(Integer.parseInt(cursor.getString(2)));
        record.set_start_date(cursor.getString(3));
        record.set_end_date(cursor.getString(4));
        record.set_log_no(cursor.getString(5));
        record.set_cleaning_schedule_completed(cursor.getString(6));
        record.set_prawn_dip(cursor.getString(7));
        record.set_scales_test(cursor.getString(8));
        record.set_target_procedure(cursor.getString(9));
        record.set_line_caught_procedure(cursor.getString(10));
        record.set_waste_ashore(cursor.getString(11));
        record.set_trip_type(cursor.getString(12));
        record.set_device_type(cursor.getString(13));
        record.set_prawn_counter(Integer.parseInt(cursor.getString(14)));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets all ended trip records.
     *
     * @param id the id
     * @return the all ended trip records
     */
// Getting All records
    List<String> getAllEndedTripRecords(String id) {
        List<String> recordList = new ArrayList<String>();
        //Select All Query
        String selectQuery = "SELECT " + KEY_ID + " FROM " + TRIP_RECORD + " WHERE " + KEY_USER_ID + " = " + id + " AND " + KEY_END_DATE + " IS NULL OR " + KEY_END_DATE + " = ''";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String trip_id = cursor.getString(0);
                recordList.add(trip_id);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return record list
        return recordList;
    }

    /**
     * Gets all trip id records.
     *
     * @return the all trip id records
     */
// Getting All records
    List<String> getAllTripIdRecords() {
        List<String> recordList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TRIP_RECORD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                recordList.add(Integer.toString(cursor.getInt(0)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return record list
        return recordList;
    }

    /**
     * Delete all trip records.
     */
// Deleting single record
    void deleteAllTripRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TRIP_RECORD, null, null);
        db.close();
    }

    /**
     * Delete trip all.
     */
    void deleteTripAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TRIP_RECORD, null, null);
        db.close();
    }

    /**
     * Gets trip record by id count.
     *
     * @param id the id
     * @return the trip record by id count
     */
    int getTripRecordByIDCount(String id) {
        String countQuery = "SELECT * FROM " + TRIP_RECORD + " WHERE " + KEY_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return cnt;
    }

    /**
     * Gets trip record by end date count.
     *
     * @param id the id
     * @return the trip record by end date count
     */
    int getTripRecordByEndDateCount(String id) {
        String countQuery = "SELECT * FROM " + TRIP_RECORD + " WHERE " + KEY_USER_ID + " = " + id + " AND " + KEY_END_DATE + " IS NULL OR " + KEY_END_DATE + " = ''";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return cnt;
    }

    /**
     * Delete tripby id.
     *
     * @param tripid the tripid
     */
// Deleting all records
    void deleteTripbyID(String tripid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TRIP_RECORD, KEY_ID + " = ?", new String[]{tripid});

        db.close();
    }

    /**
     * Gets trip records count.
     *
     * @return the trip records count
     */
// Getting records Count
    int getTripRecordsCount() {
        String countQuery = "SELECT * FROM " + TRIP_RECORD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return cnt;
    }

    //Temperature Records Functions

    /**
     * Add temperature record boolean.
     *
     * @param record the record
     * @return the boolean
     */
// Adding new record
    boolean addTemperatureRecord(Temperature_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_GPS, record.get_gps());
        values.put(KEY_CHILLED, record.get_chilled());
        values.put(KEY_FREEZER, record.get_freezer());
        values.put(KEY_BLAST, record.get_blast());
        values.put(KEY_CORE, record.get_core());
        values.put(KEY_INPUT_TYPE, record.get_input_type());
        values.put(KEY_TRIP_ID, record.get_trip());
        values.put(KEY_USER_ID, record.get_user());

        // Inserting Row
        long rowInserted = db.insert(TEMPERATURE_RECORD, null, values);
        if (rowInserted != -1) {
            result = true;
        }

        db.close(); // Closing database connection
        return result;
    }

    /**
     * Update temperature tripid record boolean.
     *
     * @param updateval the updateval
     * @param searchval the searchval
     * @return the boolean
     */
// Updating single record
    boolean updateTemperatureTripidRecord(String updateval, String searchval) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_TRIP_ID, updateval);

        // updating row
        long i = db.update(TEMPERATURE_RECORD, values, KEY_TRIP_ID + " = ?", new String[]{searchval});
        db.close(); // Closing database connection
        return i > 0;
    }

    /**
     * Gets temperature record.
     *
     * @param id the id
     * @return the temperature record
     */
// Getting single record
    Temperature_Class getTemperatureRecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TEMPERATURE_RECORD, new String[]{KEY_ID,
                        KEY_CHILLED, KEY_FREEZER, KEY_BLAST, KEY_CORE, KEY_GPS, KEY_INPUT_TYPE}, KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Temperature_Class record = new Temperature_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_chilled(cursor.getString(1));
        record.set_freezer(cursor.getString(2));
        record.set_blast(cursor.getString(3));
        record.set_core(cursor.getString(4));
        record.set_gps(cursor.getString(5));
        record.set_input_type(cursor.getString(6));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets all temperature records.
     *
     * @return the all temperature records
     */
// Getting All records
    List<Temperature_Class> getAllTemperatureRecords() {
        List<Temperature_Class> recordList = new ArrayList<Temperature_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TEMPERATURE_RECORD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Temperature_Class record = new Temperature_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_gps(cursor.getString(2));
                record.set_chilled(cursor.getString(3));
                record.set_freezer(cursor.getString(4));
                record.set_blast(cursor.getString(5));
                record.set_core(cursor.getString(6));
                record.set_input_type(cursor.getString(7));
                record.set_trip(Integer.parseInt(cursor.getString(8)));
                record.set_user(Integer.parseInt(cursor.getString(9)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return record list
        return recordList;
    }

    /**
     * Gets all current trip temperature records.
     *
     * @param id the id
     * @return the all current trip temperature records
     */
// Getting All records
    List<Temperature_Class> getAllCurrentTripTemperatureRecords(String id) {
        List<Temperature_Class> recordList = new ArrayList<Temperature_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TEMPERATURE_RECORD + " WHERE " + KEY_TRIP_ID + "=" + id + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Temperature_Class record = new Temperature_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_gps(cursor.getString(2));
                record.set_chilled(cursor.getString(3));
                record.set_freezer(cursor.getString(4));
                record.set_blast(cursor.getString(5));
                record.set_core(cursor.getString(6));
                record.set_input_type(cursor.getString(7));
                record.set_trip(Integer.parseInt(cursor.getString(8)));
                record.set_user(Integer.parseInt(cursor.getString(9)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return record list
        return recordList;
    }

    /**
     * Gets all trip temperature records.
     *
     * @param id the id
     * @return the all trip temperature records
     */
// Getting All records
    List<Temperature_Class> getAllTripTemperatureRecords(String id) {
        List<Temperature_Class> recordList = new ArrayList<Temperature_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TEMPERATURE_RECORD + " WHERE " + KEY_TRIP_ID + "=" + id + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Temperature_Class record = new Temperature_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_gps(cursor.getString(2));
                record.set_chilled(cursor.getString(3));
                record.set_freezer(cursor.getString(4));
                record.set_blast(cursor.getString(5));
                record.set_core(cursor.getString(6));
                record.set_input_type(cursor.getString(7));
                record.set_trip(Integer.parseInt(cursor.getString(8)));
                record.set_user(Integer.parseInt(cursor.getString(9)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return record list
        return recordList;
    }

    /**
     * Delete temperature record.
     *
     * @param contact the contact
     */
// Deleting single record
    void deleteTemperatureRecord(Temperature_Class contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TEMPERATURE_RECORD, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    /**
     * Delete all temperature records.
     */
// Deleting single record
    void deleteAllTemperatureRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TEMPERATURE_RECORD, null, null);
        db.close();
    }

    /**
     * Delete all trip temperature records.
     *
     * @param tripid the tripid
     */
// Deleting single record
    void deleteAllTripTemperatureRecords(String tripid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TEMPERATURE_RECORD, KEY_TRIP_ID + " = ?", new String[]{tripid});
        db.close();
    }

    /**
     * Gets temperature record count.
     *
     * @return the temperature record count
     */
// Getting records Count
    int getTemperatureRecordCount() {
        String countQuery = "SELECT * FROM " + TEMPERATURE_RECORD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return cnt;
    }

    /**
     * Gets temperature trip record count.
     *
     * @param id the id
     * @return the temperature trip record count
     */
// Getting records Count
    int getTemperatureTripRecordCount(String id) {
        String tempcountQuery = "SELECT * FROM " + TEMPERATURE_RECORD + " WHERE " + KEY_TRIP_ID + "=" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(tempcountQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return cnt;
    }

    /**
     * Add whale dolphin record boolean.
     *
     * @param record the record
     * @return the boolean
     */
//Whale And Dolphin Records Functions
    // Adding new record
    boolean addWhaleDolphinRecord(WhaleDolphin_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_GPS_LAT, record.get_gps_lat());
        values.put(KEY_GPS_LONG, record.get_gps_long());
        values.put(KEY_SPECIES, record.getSpecies());
        values.put(KEY_GROUP_SIZE, record.get_group_size());
        values.put(KEY_TRIP_ID, record.get_trip());
        values.put(KEY_USER_ID, record.get_user());

        // Inserting Row
        long rowInserted = db.insert(WHALE_DOLPIN_RECORD, null, values);
        if (rowInserted != -1) {
            result = true;
        }
        db.close(); // Closing database connection
        return result;
    }

    /**
     * Update whale dolphin tripid record boolean.
     *
     * @param updateval the updateval
     * @param searchval the searchval
     * @return the boolean
     */
// Updating single record
    boolean updateWhaleDolphinTripidRecord(String updateval, String searchval) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_TRIP_ID, updateval);

        // updating row
        long i = db.update(WHALE_DOLPIN_RECORD, values, KEY_TRIP_ID + " = ?", new String[]{searchval});
        db.close(); // Closing database connection
        return i > 0;
    }

    /**
     * Gets whale dolphin trip record count.
     *
     * @param id the id
     * @return the whale dolphin trip record count
     */
// Getting records Count
    int getWhaleDolphinTripRecordCount(String id) {
        String countQuery = "SELECT * FROM " + WHALE_DOLPIN_RECORD + " WHERE " + KEY_TRIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Gets whale dolphin record.
     *
     * @param id the id
     * @return the whale dolphin record
     */
// Getting single record
    WhaleDolphin_Class getWhaleDolphinRecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(WHALE_DOLPIN_RECORD, new String[]{KEY_ID,
                        KEY_GPS_LAT, KEY_GPS_LONG, KEY_SPECIES, KEY_GROUP_SIZE}, KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        WhaleDolphin_Class record = new WhaleDolphin_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_gps_lat(cursor.getString(1));
        record.set_gps_long(cursor.getString(2));
        record.setSpecies(cursor.getString(3));
        record.set_group_size(Integer.parseInt(cursor.getString(4)));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets last inserted whale dolphin record.
     *
     * @param id the id
     * @return the last inserted whale dolphin record
     */
// Getting single record
    WhaleDolphin_Class getLastInsertedWhaleDolphinRecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(WHALE_DOLPIN_RECORD, new String[]{KEY_ID,
                        KEY_GPS_LAT, KEY_GPS_LONG, KEY_SPECIES, KEY_GROUP_SIZE}, KEY_TRIP_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        WhaleDolphin_Class record = new WhaleDolphin_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_gps_lat(cursor.getString(1));
        record.set_gps_long(cursor.getString(2));
        record.setSpecies(cursor.getString(3));
        record.set_group_size(Integer.parseInt(cursor.getString(4)));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets all whale dolphin records.
     *
     * @return the all whale dolphin records
     */
// Getting All records
    List<WhaleDolphin_Class> getAllWhaleDolphinRecords() {
        List<WhaleDolphin_Class> recordList = new ArrayList<WhaleDolphin_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + WHALE_DOLPIN_RECORD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                WhaleDolphin_Class record = new WhaleDolphin_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_gps_lat(cursor.getString(2));
                record.set_gps_long(cursor.getString(3));
                record.setSpecies(cursor.getString(4));
                record.set_group_size(Integer.parseInt(cursor.getString(5)));
                record.set_trip(Integer.parseInt(cursor.getString(6)));
                record.set_user(Integer.parseInt(cursor.getString(7)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Gets all current trip whale dolphin records.
     *
     * @param id the id
     * @return the all current trip whale dolphin records
     */
// Getting All records
    List<WhaleDolphin_Class> getAllCurrentTripWhaleDolphinRecords(String id) {
        List<WhaleDolphin_Class> recordList = new ArrayList<WhaleDolphin_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + WHALE_DOLPIN_RECORD + " WHERE " + KEY_TRIP_ID + "=" + id + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                WhaleDolphin_Class record = new WhaleDolphin_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_gps_lat(cursor.getString(2));
                record.set_gps_long(cursor.getString(3));
                record.setSpecies(cursor.getString(4));
                record.set_group_size(Integer.parseInt(cursor.getString(5)));
                record.set_trip(Integer.parseInt(cursor.getString(6)));
                record.set_user(Integer.parseInt(cursor.getString(7)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Update whale dolphin record int.
     *
     * @param record the record
     * @return the int
     */
// Updating single record
    int updateWhaleDolphinRecord(WhaleDolphin_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_GPS_LAT, record.get_gps_lat());
        values.put(KEY_GPS_LONG, record.get_gps_long());
        values.put(KEY_SPECIES, record.getSpecies());
        values.put(KEY_GROUP_SIZE, record.get_group_size());

        // updating row
        result = db.update(WHALE_DOLPIN_RECORD, values, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
        return result;
    }

    /**
     * Delete whale dolphin record.
     *
     * @param record the record
     */
// Deleting single record
    void deleteWhaleDolphinRecord(WhaleDolphin_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WHALE_DOLPIN_RECORD, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
    }

    /**
     * Delete all trip whale dolphin records.
     *
     * @param tripid the tripid
     */
// Deleting all records
    void deleteAllTripWhaleDolphinRecords(String tripid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WHALE_DOLPIN_RECORD, KEY_TRIP_ID + " = ?", new String[]{tripid});
        db.close();
    }

    /**
     * Delete all whale dolphin records.
     */
// Deleting all records
    void deleteAllWhaleDolphinRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WHALE_DOLPIN_RECORD, null, null);
        db.close();
    }

    /**
     * Gets whale dolphin records count.
     *
     * @return the whale dolphin records count
     */
// Getting records Count
    int getWhaleDolphinRecordsCount() {
        String countQuery = "SELECT * FROM " + WHALE_DOLPIN_RECORD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Gets whale dolphin trip records count.
     *
     * @param id the id
     * @return the whale dolphin trip records count
     */
// Getting records Count
    int getWhaleDolphinTripRecordsCount(String id) {
        String countQuery = "SELECT * FROM " + WHALE_DOLPIN_RECORD + " WHERE " + KEY_TRIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return cnt;
    }


    /**
     * Add error record boolean.
     *
     * @param record the record
     * @return the boolean
     */
// Adding new record
    boolean addErrorRecord(Error_Log_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_ERROR_TEXT, record.get_error_text());
        values.put(KEY_ERROR_TYPE, record.get_error_type());
        values.put(KEY_USER_ID, record.get_user());

        // Inserting Row
        long rowInserted = db.insert(ERROR_LOG_RECORD, null, values);
        if (rowInserted != -1) {
            result = true;
        }
        db.close(); // Closing database connection
        return result;
    }

    /**
     * Gets error record count.
     *
     * @return the error record count
     */
// Getting records Count
    int getErrorRecordCount() {
        String countQuery = "SELECT * FROM " + ERROR_LOG_RECORD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Gets user error record count.
     *
     * @param id the id
     * @return the user error record count
     */
// Getting records Count
    int getUserErrorRecordCount(String id) {
        String countQuery = "SELECT * FROM " + ERROR_LOG_RECORD + " WHERE " + KEY_USER_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Delete all error records.
     */
// Deleting single record
    void deleteAllErrorRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ERROR_LOG_RECORD, null, null);
        db.close();
    }

    /**
     * Delete all user error records.
     *
     * @param userid the userid
     */
// Deleting single record
    void deleteAllUserErrorRecords(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ERROR_LOG_RECORD, KEY_USER_ID + " = ?", new String[]{userid});
        db.close();
    }

    /**
     * Gets all error records.
     *
     * @return the all error records
     */
// Getting All records
    List<Error_Log_Class> getAllErrorRecords() {
        List<Error_Log_Class> recordList = new ArrayList<Error_Log_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + ERROR_LOG_RECORD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Error_Log_Class record = new Error_Log_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_error_type(cursor.getString(2));
                record.set_error_text(cursor.getString(3));
                record.set_user(Integer.parseInt(cursor.getString(4)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Add visitor record boolean.
     *
     * @param record the record
     * @return the boolean
     */
// Adding new record
    boolean addVisitorRecord(Visitor_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_VISITOR_NAME, record.get_visitor_name());
        values.put(KEY_VISITOR_REASON, record.get_visitor_reason());
        values.put(KEY_VISITOR_COMMENT, record.get_visitor_comment());
        values.put(KEY_TRIP_ID, record.get_trip());
        values.put(KEY_USER_ID, record.get_user());

        // Inserting Row
        long rowInserted = db.insert(VISITOR_RECORD, null, values);
        if (rowInserted != -1) {
            result = true;
        }
        db.close(); // Closing database connection
        return result;
    }

    /**
     * Gets visitor record.
     *
     * @param id the id
     * @return the visitor record
     */
// Getting single record
    Visitor_Class getVisitorRecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(VISITOR_RECORD, new String[]{KEY_ID,
                        KEY_VISITOR_NAME, KEY_VISITOR_REASON, KEY_VISITOR_COMMENT}, KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Visitor_Class record = new Visitor_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_visitor_name(cursor.getString(1));
        record.set_visitor_reason(cursor.getString(2));
        record.set_visitor_comment(cursor.getString(3));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets last inserted visitor record.
     *
     * @param id the id
     * @return the last inserted visitor record
     */
// Getting single record
    Visitor_Class getLastInsertedVisitorRecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(VISITOR_RECORD, new String[]{KEY_ID,
                        KEY_VISITOR_NAME, KEY_VISITOR_REASON, KEY_VISITOR_COMMENT}, KEY_TRIP_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Visitor_Class record = new Visitor_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_visitor_name(cursor.getString(1));
        record.set_visitor_reason(cursor.getString(2));
        record.set_visitor_comment(cursor.getString(3));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets all current trip visitor records.
     *
     * @param id the id
     * @return the all current trip visitor records
     */
// Getting All records
    List<Visitor_Class> getAllCurrentTripVisitorRecords(String id) {
        List<Visitor_Class> recordList = new ArrayList<Visitor_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + VISITOR_RECORD + " WHERE " + KEY_TRIP_ID + "=" + id + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Visitor_Class record = new Visitor_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_visitor_name(cursor.getString(2));
                record.set_visitor_reason(cursor.getString(3));
                record.set_visitor_comment(cursor.getString(4));
                record.set_trip(Integer.parseInt(cursor.getString(5)));
                record.set_user(Integer.parseInt(cursor.getString(6)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Gets all visitor records.
     *
     * @return the all visitor records
     */
// Getting All records
    List<Visitor_Class> getAllVisitorRecords() {
        List<Visitor_Class> recordList = new ArrayList<Visitor_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + VISITOR_RECORD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Visitor_Class record = new Visitor_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_visitor_name(cursor.getString(2));
                record.set_visitor_reason(cursor.getString(3));
                record.set_visitor_comment(cursor.getString(4));
                record.set_trip(Integer.parseInt(cursor.getString(5)));
                record.set_user(Integer.parseInt(cursor.getString(6)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Update visitor tripid record boolean.
     *
     * @param updateval the updateval
     * @param searchval the searchval
     * @return the boolean
     */
// Updating single record
    boolean updateVisitorTripidRecord(String updateval, String searchval) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_TRIP_ID, updateval);

        // updating row
        long i = db.update(VISITOR_RECORD, values, KEY_TRIP_ID + " = ?", new String[]{searchval});
        if (i > 0) {
            db.close(); // Closing database connection
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets visitor trip record count.
     *
     * @param id the id
     * @return the visitor trip record count
     */
// Getting records Count
    int getVisitorTripRecordCount(String id) {
        String countQuery = "SELECT * FROM " + VISITOR_RECORD + " WHERE " + KEY_TRIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Update visitor record int.
     *
     * @param record the record
     * @return the int
     */
// Updating single record
    int updateVisitorRecord(Visitor_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_VISITOR_NAME, record.get_visitor_name());
        values.put(KEY_VISITOR_REASON, record.get_visitor_reason());
        values.put(KEY_VISITOR_COMMENT, record.get_visitor_comment());

        // updating row
        result = db.update(VISITOR_RECORD, values, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
        return result;
    }

    /**
     * Delete record.
     *
     * @param record the record
     */
// Deleting single record
    void deleteRecord(Visitor_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VISITOR_RECORD, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
    }

    /**
     * Delete all visitor records.
     */
// Deleting all records
    void deleteAllVisitorRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VISITOR_RECORD, null, null);

        db.close();
    }

    /**
     * Delete all trip visitor records.
     *
     * @param tripid the tripid
     */
// Deleting all records
    void deleteAllTripVisitorRecords(String tripid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VISITOR_RECORD, KEY_TRIP_ID + " = ?", new String[]{tripid});

        db.close();
    }

    /**
     * Gets visitor trip records count.
     *
     * @param id the id
     * @return the visitor trip records count
     */
// Getting records Count
    int getVisitorTripRecordsCount(String id) {
        String countQuery = "SELECT * FROM " + VISITOR_RECORD + " WHERE " + KEY_TRIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Gets visitor records count.
     *
     * @return the visitor records count
     */
// Getting records Count
    int getVisitorRecordsCount() {
        String countQuery = "SELECT * FROM " + VISITOR_RECORD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Add communication record boolean.
     *
     * @param record the record
     * @return the boolean
     */
// Adding new record
    boolean addCommunicationRecord(Communication_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_VESSEL_FROM, record.get_vessel_from());
        values.put(KEY_VESSEL_TO, record.get_vessel_to());
        values.put(KEY_COMMENT_TEXT, record.get_vessel_comment());
        values.put(KEY_TRIP_ID, record.get_trip());
        values.put(KEY_USER_ID, record.get_user());

        // Inserting Row
        long rowInserted = db.insert(COMMUNICATION_RECORD, null, values);
        if (rowInserted != -1) {
            result = true;
        }
        db.close(); // Closing database connection
        return result;
    }

    /**
     * Gets communication record.
     *
     * @param id the id
     * @return the communication record
     */
// Getting single record
    Communication_Class getCommunicationRecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(COMMUNICATION_RECORD, new String[]{KEY_ID,
                        KEY_VESSEL_FROM, KEY_VESSEL_TO, KEY_COMMENT_TEXT}, KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Communication_Class record = new Communication_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_vessel_from(cursor.getString(1));
        record.set_vessel_to(cursor.getString(2));
        record.set_vessel_comment(cursor.getString(3));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets last inserted communication record.
     *
     * @param id the id
     * @return the last inserted communication record
     */
// Getting single record
    Communication_Class getLastInsertedCommunicationRecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(COMMUNICATION_RECORD, new String[]{KEY_ID,
                        KEY_VESSEL_FROM, KEY_VESSEL_TO, KEY_COMMENT_TEXT}, KEY_TRIP_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Communication_Class record = new Communication_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_vessel_from(cursor.getString(1));
        record.set_vessel_to(cursor.getString(2));
        record.set_vessel_comment(cursor.getString(3));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets all current trip communication records.
     *
     * @param id the id
     * @return the all current trip communication records
     */
// Getting All records
    List<Communication_Class> getAllCurrentTripCommunicationRecords(String id) {
        List<Communication_Class> recordList = new ArrayList<Communication_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + COMMUNICATION_RECORD + " WHERE " + KEY_TRIP_ID + "=" + id + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Communication_Class record = new Communication_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_vessel_from(cursor.getString(2));
                record.set_vessel_to(cursor.getString(3));
                record.set_vessel_comment(cursor.getString(4));
                record.set_trip(Integer.parseInt(cursor.getString(5)));
                record.set_user(Integer.parseInt(cursor.getString(6)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Gets all communication records.
     *
     * @return the all communication records
     */
// Getting All records
    List<Communication_Class> getAllCommunicationRecords() {
        List<Communication_Class> recordList = new ArrayList<Communication_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + COMMUNICATION_RECORD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Communication_Class record = new Communication_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_vessel_from(cursor.getString(2));
                record.set_vessel_to(cursor.getString(3));
                record.set_vessel_comment(cursor.getString(4));
                record.set_trip(Integer.parseInt(cursor.getString(5)));
                record.set_user(Integer.parseInt(cursor.getString(6)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Update communication record int.
     *
     * @param record the record
     * @return the int
     */
// Updating single record
    int updateCommunicationRecord(Communication_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_VESSEL_FROM, record.get_vessel_from());
        values.put(KEY_VESSEL_TO, record.get_vessel_to());
        values.put(KEY_COMMENT_TEXT, record.get_vessel_comment());

        // updating row
        result = db.update(COMMUNICATION_RECORD, values, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
        return result;
    }

    /**
     * Update communication tripid record boolean.
     *
     * @param updateval the updateval
     * @param searchval the searchval
     * @return the boolean
     */
// Updating single record
    boolean updateCommunicationTripidRecord(String updateval, String searchval) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_TRIP_ID, updateval);

        // updating row
        long i = db.update(COMMUNICATION_RECORD, values, KEY_TRIP_ID + " = ?", new String[]{searchval});
        db.close(); // Closing database connection
        return i > 0;
    }

    /**
     * Gets communication trip record count.
     *
     * @param id the id
     * @return the communication trip record count
     */
// Getting records Count
    int getCommunicationTripRecordCount(String id) {
        String countQuery = "SELECT * FROM " + COMMUNICATION_RECORD + " WHERE " + KEY_TRIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Delete communication record.
     *
     * @param record the record
     */
// Deleting single record
    void deleteCommunicationRecord(Communication_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COMMUNICATION_RECORD, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
    }

    /**
     * Delete all communication records.
     */
// Deleting all records
    void deleteAllCommunicationRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COMMUNICATION_RECORD, null, null);
        db.close();
    }

    /**
     * Delete all trip communication records.
     *
     * @param tripid the tripid
     */
// Deleting all records
    void deleteAllTripCommunicationRecords(String tripid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COMMUNICATION_RECORD, KEY_TRIP_ID + " = ?", new String[]{tripid});
        db.close();
    }

    /**
     * Gets communication records count.
     *
     * @return the communication records count
     */
// Getting records Count
    int getCommunicationRecordsCount() {
        String countQuery = "SELECT * FROM " + COMMUNICATION_RECORD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return cnt;
    }

    /**
     * Gets communication trip records count.
     *
     * @param id the id
     * @return the communication trip records count
     */
// Getting records Count
    int getCommunicationTripRecordsCount(String id) {
        String countQuery = "SELECT * FROM " + COMMUNICATION_RECORD + " WHERE " + KEY_TRIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Add dispatch record boolean.
     *
     * @param record the record
     * @return the boolean
     */
// Adding new record
    boolean addDispatchRecord(Dispatch_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_DISPATCH_QUANTITY, record.get_dispatch_quantity());
        values.put(KEY_DISPATCH_PORT, record.get_dispatch_port());
        values.put(KEY_DISPATCH_BUYER, record.get_dispatch_buyer());
        values.put(KEY_DISPATCH_EMAIL, record.get_dispatch_email());
        values.put(KEY_TRIP_ID, record.get_trip());
        values.put(KEY_USER_ID, record.get_user());

        // Inserting Row
        long rowInserted = db.insert(DISPATCH_RECORD, null, values);
        if (rowInserted != -1) {
            result = true;
        }
        db.close(); // Closing database connection
        return result;
    }

    /**
     * Gets dispatch record.
     *
     * @param id the id
     * @return the dispatch record
     */
// Getting single record
    Dispatch_Class getDispatchRecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DISPATCH_RECORD, new String[]{KEY_ID,
                        KEY_DISPATCH_QUANTITY, KEY_DISPATCH_PORT, KEY_DISPATCH_BUYER, KEY_DISPATCH_EMAIL}, KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Dispatch_Class record = new Dispatch_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_dispatch_quantity(cursor.getString(1));
        record.set_dispatch_port(cursor.getString(2));
        record.set_dispatch_buyer(cursor.getString(3));
        record.set_dispatch_email(cursor.getString(4));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets last inserted dispatch record.
     *
     * @param id the id
     * @return the last inserted dispatch record
     */
// Getting single record
    Dispatch_Class getLastInsertedDispatchRecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DISPATCH_RECORD, new String[]{KEY_ID,
                        KEY_DISPATCH_QUANTITY, KEY_DISPATCH_PORT, KEY_DISPATCH_BUYER, KEY_DISPATCH_EMAIL}, KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Dispatch_Class record = new Dispatch_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_dispatch_quantity(cursor.getString(1));
        record.set_dispatch_port(cursor.getString(2));
        record.set_dispatch_buyer(cursor.getString(3));
        record.set_dispatch_email(cursor.getString(4));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets all current trip dispatch records.
     *
     * @param id the id
     * @return the all current trip dispatch records
     */
// Getting All records
    List<Dispatch_Class> getAllCurrentTripDispatchRecords(String id) {
        List<Dispatch_Class> recordList = new ArrayList<Dispatch_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + DISPATCH_RECORD + " WHERE " + KEY_TRIP_ID + "=" + id + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Dispatch_Class record = new Dispatch_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_dispatch_quantity(cursor.getString(2));
                record.set_dispatch_port(cursor.getString(3));
                record.set_dispatch_buyer(cursor.getString(4));
                record.set_dispatch_email(cursor.getString(5));
                record.set_trip(Integer.parseInt(cursor.getString(6)));
                record.set_user(Integer.parseInt(cursor.getString(7)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Gets all dispatch records.
     *
     * @return the all dispatch records
     */
// Getting All records
    List<Dispatch_Class> getAllDispatchRecords() {
        List<Dispatch_Class> recordList = new ArrayList<Dispatch_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + DISPATCH_RECORD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Dispatch_Class record = new Dispatch_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_dispatch_quantity(cursor.getString(2));
                record.set_dispatch_port(cursor.getString(3));
                record.set_dispatch_buyer(cursor.getString(4));
                record.set_dispatch_email(cursor.getString(5));
                record.set_trip(Integer.parseInt(cursor.getString(6)));
                record.set_user(Integer.parseInt(cursor.getString(7)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Gets dispatch trip record count.
     *
     * @param id the id
     * @return the dispatch trip record count
     */
// Getting records Count
    int getDispatchTripRecordCount(String id) {
        String countQuery = "SELECT * FROM " + DISPATCH_RECORD + " WHERE " + KEY_TRIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return cnt;
    }

    /**
     * Update dispatch tripid record boolean.
     *
     * @param updateval the updateval
     * @param searchval the searchval
     * @return the boolean
     */
// Updating single record
    boolean updateDispatchTripidRecord(String updateval, String searchval) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_TRIP_ID, updateval);

        // updating row
        long i = db.update(DISPATCH_RECORD, values, KEY_TRIP_ID + " = ?", new String[]{searchval});
        db.close(); // Closing database connection
        return i > 0;
    }

    /**
     * Update dispatch record int.
     *
     * @param record the record
     * @return the int
     */
// Updating single record
    int updateDispatchRecord(Dispatch_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_DISPATCH_QUANTITY, record.get_dispatch_quantity());
        values.put(KEY_DISPATCH_PORT, record.get_dispatch_port());
        values.put(KEY_DISPATCH_BUYER, record.get_dispatch_buyer());
        values.put(KEY_DISPATCH_EMAIL, record.get_dispatch_email());

        // updating row
        result = db.update(DISPATCH_RECORD, values, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
        return result;
    }

    /**
     * Delete dispatch record.
     *
     * @param record the record
     */
// Deleting single record
    void deleteDispatchRecord(Dispatch_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DISPATCH_RECORD, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
    }

    /**
     * Delete all dispatch records.
     */
// Deleting all records
    void deleteAllDispatchRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DISPATCH_RECORD, null, null);
        db.close();
    }

    /**
     * Delete all trip dispatch records.
     *
     * @param tripid the tripid
     */
// Deleting all records
    void deleteAllTripDispatchRecords(String tripid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DISPATCH_RECORD, KEY_TRIP_ID + " = ?", new String[]{tripid});
        db.close();
    }

    /**
     * Gets dispatch trip records count.
     *
     * @param id the id
     * @return the dispatch trip records count
     */
// Getting records Count
    int getDispatchTripRecordsCount(String id) {
        String countQuery = "SELECT * FROM " + DISPATCH_RECORD + " WHERE " + KEY_TRIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Gets dispatch records count.
     *
     * @return the dispatch records count
     */
// Getting records Count
    int getDispatchRecordsCount() {
        String countQuery = "SELECT * FROM " + DISPATCH_RECORD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Add incident record boolean.
     *
     * @param record the record
     * @return the boolean
     */
// Adding new record
    boolean addIncidentRecord(Incident_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_INCIDENT_TYPE, record.get_incident_type());
        values.put(KEY_INCIDENT_DETAILS, record.get_incident_details());
        values.put(KEY_INCIDENT_ACTION, record.get_incident_action());
        values.put(KEY_TRIP_ID, record.get_trip());
        values.put(KEY_USER_ID, record.get_user());

        // Inserting Row
        long rowInserted = db.insert(INCIDENT_RECORD, null, values);
        if (rowInserted != -1) {
            result = true;
        }
        db.close(); // Closing database connection
        return result;
    }

    /**
     * Gets incident record.
     *
     * @param id the id
     * @return the incident record
     */
// Getting single record
    Incident_Class getIncidentRecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(INCIDENT_RECORD, new String[]{KEY_ID,
                        KEY_INCIDENT_TYPE, KEY_INCIDENT_DETAILS, KEY_INCIDENT_ACTION}, KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Incident_Class record = new Incident_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_incident_type(cursor.getString(1));
        record.set_incident_details(cursor.getString(2));
        record.set_incident_type(cursor.getString(3));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Update incident tripid record boolean.
     *
     * @param updateval the updateval
     * @param searchval the searchval
     * @return the boolean
     */
// Updating single record
    boolean updateIncidentTripidRecord(String updateval, String searchval) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_TRIP_ID, updateval);

        // updating row
        long i = db.update(INCIDENT_RECORD, values, KEY_TRIP_ID + " = ?", new String[]{searchval});
        db.close(); // Closing database connection
        return i > 0;
    }

    /**
     * Gets incident trip record count.
     *
     * @param id the id
     * @return the incident trip record count
     */
// Getting records Count
    int getIncidentTripRecordCount(String id) {
        String countQuery = "SELECT * FROM " + INCIDENT_RECORD + " WHERE " + KEY_TRIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Gets last inserted incident record.
     *
     * @param id the id
     * @return the last inserted incident record
     */
// Getting single record
    Incident_Class getLastInsertedIncidentRecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(INCIDENT_RECORD, new String[]{KEY_ID,
                        KEY_INCIDENT_TYPE, KEY_INCIDENT_DETAILS, KEY_INCIDENT_ACTION}, KEY_TRIP_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Incident_Class record = new Incident_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_incident_type(cursor.getString(1));
        record.set_incident_details(cursor.getString(2));
        record.set_incident_type(cursor.getString(3));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets all current trip incident records.
     *
     * @param id the id
     * @return the all current trip incident records
     */
// Getting All records
    List<Incident_Class> getAllCurrentTripIncidentRecords(String id) {
        List<Incident_Class> recordList = new ArrayList<Incident_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + INCIDENT_RECORD + " WHERE " + KEY_TRIP_ID + "=" + id + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Incident_Class record = new Incident_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_incident_type(cursor.getString(2));
                record.set_incident_details(cursor.getString(3));
                record.set_incident_type(cursor.getString(4));
                record.set_trip(Integer.parseInt(cursor.getString(5)));
                record.set_user(Integer.parseInt(cursor.getString(6)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Gets all incident records.
     *
     * @param id the id
     * @return the all incident records
     */
// Getting All records
    List<Incident_Class> getAllIncidentRecords(String id) {
        List<Incident_Class> recordList = new ArrayList<Incident_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + INCIDENT_RECORD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Incident_Class record = new Incident_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_incident_type(cursor.getString(2));
                record.set_incident_details(cursor.getString(3));
                record.set_incident_type(cursor.getString(4));
                record.set_trip(Integer.parseInt(cursor.getString(5)));
                record.set_user(Integer.parseInt(cursor.getString(6)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Update incident record int.
     *
     * @param record the record
     * @return the int
     */
// Updating single record
    int updateIncidentRecord(Incident_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_INCIDENT_TYPE, record.get_incident_type());
        values.put(KEY_INCIDENT_DETAILS, record.get_incident_details());
        values.put(KEY_INCIDENT_ACTION, record.get_incident_type());

        // updating row
        result = db.update(INCIDENT_RECORD, values, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
        return result;
    }

    /**
     * Delete record.
     *
     * @param record the record
     */
// Deleting single record
    void deleteRecord(Incident_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(INCIDENT_RECORD, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
    }

    /**
     * Delete all incident records.
     */
// Deleting all records
    void deleteAllIncidentRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(INCIDENT_RECORD, null, null);
        db.close();
    }

    /**
     * Delete all trip incident records.
     *
     * @param tripid the tripid
     */
// Deleting all records
    void deleteAllTripIncidentRecords(String tripid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(INCIDENT_RECORD, KEY_TRIP_ID + " = ?", new String[]{tripid});
        db.close();
    }

    /**
     * Gets incident records count.
     *
     * @return the incident records count
     */
// Getting records Count
    int getIncidentRecordsCount() {
        String countQuery = "SELECT * FROM " + INCIDENT_RECORD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Gets incident trip records count.
     *
     * @param id the id
     * @return the incident trip records count
     */
// Getting records Count
    int getIncidentTripRecordsCount(String id) {
        String countQuery = "SELECT * FROM " + INCIDENT_RECORD + " WHERE " + KEY_TRIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Add slippage record boolean.
     *
     * @param record the record
     * @return the boolean
     */
// Adding new record
    boolean addSlippageRecord(Slippage_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_ICES_SUB_AREA, record.get_ices_sub_area());
        values.put(KEY_VESSEL_POSITION, record.get_position());
        values.put(KEY_TARGET_FISHERIES, record.get_target_fisheries());
        values.put(KEY_SPECIES_SLIPPED, record.get_species_slipped());
        values.put(KEY_LIPPAGE_REASON, record.get_slippage_reason());
        values.put(KEY_SAMPLE_TAKEN, record.get_sample_taken());
        values.put(KEY_SPECIES_QUANTITY, record.get_species_quantity());
        values.put(KEY_SPECIES_SIZE, record.get_species_size());
        values.put(KEY_TRIP_ID, record.get_trip());
        values.put(KEY_USER_ID, record.get_user());

        // Inserting Row
        long rowInserted = db.insert(SLIPPAGE_RECORD, null, values);
        if (rowInserted != -1) {
            result = true;
        }
        db.close(); // Closing database connection
        return result;
    }

    /**
     * Gets slippage record.
     *
     * @param id the id
     * @return the slippage record
     */
// Getting single record
    Slippage_Class getSlippageRecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SLIPPAGE_RECORD, new String[]{KEY_ID,
                        KEY_ICES_SUB_AREA, KEY_VESSEL_POSITION, KEY_TARGET_FISHERIES, KEY_SPECIES_SLIPPED, KEY_LIPPAGE_REASON, KEY_SAMPLE_TAKEN, KEY_SPECIES_QUANTITY, KEY_SPECIES_SIZE}, KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Slippage_Class record = new Slippage_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_ices_sub_area(cursor.getString(1));
        record.set_position(cursor.getString(2));
        record.set_target_fisheries(cursor.getString(3));
        record.set_species_slipped(cursor.getString(4));
        record.set_slippage_reason(cursor.getString(5));
        record.set_sample_taken(cursor.getString(6));
        record.set_species_quantity(Integer.parseInt(cursor.getString(7)));
        record.set_species_size(Integer.parseInt(cursor.getString(8)));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets last inserted slippage record.
     *
     * @param id     the id
     * @param tripid the tripid
     * @return the last inserted slippage record
     */
// Getting single contact
    Slippage_Class getLastInsertedSlippageRecord(String id, String tripid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SLIPPAGE_RECORD, new String[]{KEY_ID,
                        KEY_ICES_SUB_AREA, KEY_VESSEL_POSITION, KEY_TARGET_FISHERIES, KEY_SPECIES_SLIPPED, KEY_LIPPAGE_REASON, KEY_SAMPLE_TAKEN, KEY_SPECIES_QUANTITY, KEY_SPECIES_SIZE}, KEY_ICES_SUB_AREA + "=?" + "and" + KEY_TRIP_ID + "=?",
                new String[]{id, tripid}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Slippage_Class record = new Slippage_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_ices_sub_area(cursor.getString(1));
        record.set_position(cursor.getString(2));
        record.set_target_fisheries(cursor.getString(3));
        record.set_species_slipped(cursor.getString(4));
        record.set_slippage_reason(cursor.getString(5));
        record.set_sample_taken(cursor.getString(6));
        record.set_species_quantity(Integer.parseInt(cursor.getString(7)));
        record.set_species_size(Integer.parseInt(cursor.getString(8)));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets all current trip slippage records.
     *
     * @param id the id
     * @return the all current trip slippage records
     */
// Getting All records
    List<Slippage_Class> getAllCurrentTripSlippageRecords(String id) {
        List<Slippage_Class> recordList = new ArrayList<Slippage_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + SLIPPAGE_RECORD + " WHERE " + KEY_TRIP_ID + "=" + id + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Slippage_Class record = new Slippage_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_ices_sub_area(cursor.getString(1));
                record.set_position(cursor.getString(2));
                record.set_target_fisheries(cursor.getString(3));
                record.set_species_slipped(cursor.getString(4));
                record.set_slippage_reason(cursor.getString(5));
                record.set_sample_taken(cursor.getString(6));
                record.set_species_quantity(Integer.parseInt(cursor.getString(7)));
                record.set_species_size(Integer.parseInt(cursor.getString(8)));
                record.set_trip(Integer.parseInt(cursor.getString(9)));
                record.set_user(Integer.parseInt(cursor.getString(10)));
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return record list
        return recordList;
    }

    /**
     * Gets all slippage records.
     *
     * @return the all slippage records
     */
// Getting All records
    List<Slippage_Class> getAllSlippageRecords() {
        List<Slippage_Class> recordList = new ArrayList<Slippage_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + SLIPPAGE_RECORD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Slippage_Class record = new Slippage_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_ices_sub_area(cursor.getString(1));
                record.set_position(cursor.getString(2));
                record.set_target_fisheries(cursor.getString(3));
                record.set_species_slipped(cursor.getString(4));
                record.set_slippage_reason(cursor.getString(5));
                record.set_sample_taken(cursor.getString(6));
                record.set_species_quantity(Integer.parseInt(cursor.getString(7)));
                record.set_species_size(Integer.parseInt(cursor.getString(8)));
                record.set_trip(Integer.parseInt(cursor.getString(9)));
                record.set_user(Integer.parseInt(cursor.getString(10)));
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return record list
        return recordList;
    }

    /**
     * Update slippage record int.
     *
     * @param record the record
     * @return the int
     */
// Updating single record
    int updateSlippageRecord(Slippage_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_VESSEL_POSITION, record.get_position());
        values.put(KEY_ICES_SUB_AREA, record.get_ices_sub_area());
        values.put(KEY_TARGET_FISHERIES, record.get_target_fisheries());
        values.put(KEY_SPECIES_SLIPPED, record.get_species_slipped());
        values.put(KEY_LIPPAGE_REASON, record.get_slippage_reason());
        values.put(KEY_SAMPLE_TAKEN, record.get_sample_taken());
        values.put(KEY_SPECIES_QUANTITY, record.get_species_quantity());
        values.put(KEY_SPECIES_SIZE, record.get_species_size());

        // updating row
        result = db.update(SLIPPAGE_RECORD, values, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
        return result;
    }

    /**
     * Update slippage tripid record boolean.
     *
     * @param updateval the updateval
     * @param searchval the searchval
     * @return the boolean
     */
// Updating single record
    boolean updateSlippageTripidRecord(String updateval, String searchval) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_TRIP_ID, updateval);

        // updating row
        long i = db.update(SLIPPAGE_RECORD, values, KEY_TRIP_ID + " = ?", new String[]{searchval});
        if (i > 0) {
            db.close(); // Closing database connection
            return true;
        } else {
            return false;
        }
    }

    /**
     * Delete slippage record.
     *
     * @param record the record
     */
// Deleting single record
    void deleteSlippageRecord(Slippage_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SLIPPAGE_RECORD, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
    }

    /**
     * Delete all slippage records.
     */
// Deleting single record
    void deleteAllSlippageRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SLIPPAGE_RECORD, null, null);
        db.close();
    }

    /**
     * Delete all trip slippage records.
     *
     * @param tripid the tripid
     */
// Deleting single record
    void deleteAllTripSlippageRecords(String tripid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SLIPPAGE_RECORD, KEY_TRIP_ID + " = ?", new String[]{tripid});
        db.close();
    }

    /**
     * Gets slippage record count.
     *
     * @return the slippage record count
     */
// Getting records Count
    int getSlippageRecordCount() {
        String countQuery = "SELECT * FROM " + SLIPPAGE_RECORD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Gets slippage trip record count.
     *
     * @param id the id
     * @return the slippage trip record count
     */
// Getting records Count
    int getSlippageTripRecordCount(String id) {
        String countQuery = "SELECT * FROM " + SLIPPAGE_RECORD + " WHERE " + KEY_TRIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Add marine record boolean.
     *
     * @param record the record
     * @return the boolean
     */
// Adding new record
    boolean addMarineRecord(Marine_Litter_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_VESSEL_PORT, record.get_vessel_port());
        values.put(KEY_MARINE_LITTER_QUANTITY, record.get_marine_litter_quantity());
        values.put(KEY_TRIP_ID, record.get_trip());
        values.put(KEY_USER_ID, record.get_user());

        // Inserting Row
        long rowInserted = db.insert(MARINE_LITTER_RECORD, null, values);
        if (rowInserted != -1) {
            result = true;
        }
        db.close(); // Closing database connection
        return result;
    }

    /**
     * Gets marine record.
     *
     * @param id the id
     * @return the marine record
     */
// Getting single record
    Marine_Litter_Class getMarineRecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(MARINE_LITTER_RECORD, new String[]{KEY_ID,
                        KEY_VESSEL_PORT, KEY_MARINE_LITTER_QUANTITY}, KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Marine_Litter_Class record = new Marine_Litter_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_vessel_port(cursor.getString(1));
        record.set_marine_litter_quantity(cursor.getString(2));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets all current trip marine records.
     *
     * @param id the id
     * @return the all current trip marine records
     */
// Getting All records
    List<Marine_Litter_Class> getAllCurrentTripMarineRecords(String id) {
        List<Marine_Litter_Class> recordList = new ArrayList<Marine_Litter_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + MARINE_LITTER_RECORD + " WHERE " + KEY_TRIP_ID + "=" + id + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Marine_Litter_Class record = new Marine_Litter_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_vessel_port(cursor.getString(1));
                record.set_marine_litter_quantity(cursor.getString(2));
                record.set_trip(Integer.parseInt(cursor.getString(4)));
                record.set_user(Integer.parseInt(cursor.getString(5)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Gets all marine records.
     *
     * @return the all marine records
     */
// Getting All records
    List<Marine_Litter_Class> getAllMarineRecords() {
        List<Marine_Litter_Class> recordList = new ArrayList<Marine_Litter_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + MARINE_LITTER_RECORD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Marine_Litter_Class record = new Marine_Litter_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_vessel_port(cursor.getString(1));
                record.set_marine_litter_quantity(cursor.getString(2));
                record.set_trip(Integer.parseInt(cursor.getString(4)));
                record.set_user(Integer.parseInt(cursor.getString(5)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Update marine tripid record boolean.
     *
     * @param updateval the updateval
     * @param searchval the searchval
     * @return the boolean
     */
// Updating single record
    boolean updateMarineTripidRecord(String updateval, String searchval) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_TRIP_ID, updateval);

        // updating row
        long i = db.update(MARINE_LITTER_RECORD, values, KEY_TRIP_ID + " = ?", new String[]{searchval});
        db.close(); // Closing database connection
        return i > 0;
    }

    /**
     * Gets marine trip record count.
     *
     * @param id the id
     * @return the marine trip record count
     */
// Getting records Count
    int getMarineTripRecordCount(String id) {
        String countQuery = "SELECT * FROM " + MARINE_LITTER_RECORD + " WHERE " + KEY_TRIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Update marine record int.
     *
     * @param record the record
     * @return the int
     */
// Updating single record
    int updateMarineRecord(Marine_Litter_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_VESSEL_PORT, record.get_vessel_port());
        values.put(KEY_MARINE_LITTER_QUANTITY, record.get_marine_litter_quantity());

        // updating row
        result = db.update(MARINE_LITTER_RECORD, values, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
        return result;
    }

    /**
     * Delete marine record.
     *
     * @param record the record
     */
// Deleting single record
    void deleteMarineRecord(Marine_Litter_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MARINE_LITTER_RECORD, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
    }

    /**
     * Delete all marine records.
     */
// Deleting all records
    void deleteAllMarineRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MARINE_LITTER_RECORD, null, null);
        db.close();
    }

    /**
     * Delete all trip marine records.
     *
     * @param tripid the tripid
     */
// Deleting all records
    void deleteAllTripMarineRecords(String tripid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MARINE_LITTER_RECORD, KEY_TRIP_ID + " = ?", new String[]{tripid});
        db.close();
    }

    /**
     * Gets marine litter trip records count.
     *
     * @param id the id
     * @return the marine litter trip records count
     */
// Getting records Count
    int getMarineLitterTripRecordsCount(String id) {
        String countQuery = "SELECT * FROM " + MARINE_LITTER_RECORD + " WHERE " + KEY_TRIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Gets marine records count.
     *
     * @return the marine records count
     */
// Getting records Count
    int getMarineRecordsCount() {
        String countQuery = "SELECT * FROM " + MARINE_LITTER_RECORD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Add lost gear record boolean.
     *
     * @param record the record
     * @return the boolean
     */
// Adding new record
    boolean addLostGearRecord(Lost_Gear_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_GEAR_NAME, record.get_gear_name());
        values.put(KEY_GEAR_POSITION, record.get_position());
        values.put(KEY_GEAR_QUANTITY, record.get_gear_quantity());
        values.put(KEY_GEAR_REASON, record.get_reason());
        values.put(KEY_GEAR_REPLACEMENT, record.get_gear_replacement());
        values.put(KEY_GEAR_REPLACEMENT_DETAILS, record.get_replacement_details());
        values.put(KEY_TRIP_ID, record.get_trip());
        values.put(KEY_USER_ID, record.get_user());

        // Inserting Row
        long rowInserted = db.insert(LOST_GEAR_RECORD, null, values);
        if (rowInserted != -1) {
            result = true;
        }
        db.close(); // Closing database connection
        return result;
    }

    /**
     * Gets lost gear record.
     *
     * @param id the id
     * @return the lost gear record
     */
// Getting single record
    Lost_Gear_Class getLostGearRecord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(LOST_GEAR_RECORD, new String[]{KEY_ID,
                        KEY_GEAR_NAME, KEY_GEAR_POSITION, KEY_GEAR_QUANTITY, KEY_GEAR_REASON, KEY_GEAR_REPLACEMENT, KEY_GEAR_REPLACEMENT_DETAILS}, KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Lost_Gear_Class record = new Lost_Gear_Class();
        // return record
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.set_gear_name(cursor.getString(1));
        record.set_position(cursor.getString(2));
        record.set_gear_quantity(Integer.parseInt(cursor.getString(3)));
        record.set_reason(cursor.getString(4));
        record.set_gear_replacement(cursor.getString(5));
        record.set_replacement_details(cursor.getString(6));
        cursor.close();
        db.close();
        return record;
    }

    /**
     * Gets all current trip lost gear records.
     *
     * @param id the id
     * @return the all current trip lost gear records
     */
// Getting All records
    List<Lost_Gear_Class> getAllCurrentTripLostGearRecords(String id) {
        List<Lost_Gear_Class> recordList = new ArrayList<Lost_Gear_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + LOST_GEAR_RECORD + " WHERE " + KEY_TRIP_ID + "=" + id + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Lost_Gear_Class record = new Lost_Gear_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_gear_name(cursor.getString(1));
                record.set_position(cursor.getString(2));
                record.set_gear_quantity(cursor.getInt(3));
                record.set_reason(cursor.getString(4));
                record.set_gear_replacement(cursor.getString(5));
                record.set_replacement_details(cursor.getString(6));
                record.set_trip(Integer.parseInt(cursor.getString(7)));
                record.set_user(Integer.parseInt(cursor.getString(8)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Gets all lost gear records.
     *
     * @return the all lost gear records
     */
// Getting All records
    List<Lost_Gear_Class> getAllLostGearRecords() {
        List<Lost_Gear_Class> recordList = new ArrayList<Lost_Gear_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + LOST_GEAR_RECORD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Lost_Gear_Class record = new Lost_Gear_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_gear_name(cursor.getString(1));
                record.set_position(cursor.getString(2));
                record.set_gear_quantity(cursor.getInt(3));
                record.set_reason(cursor.getString(4));
                record.set_gear_replacement(cursor.getString(5));
                record.set_replacement_details(cursor.getString(6));
                record.set_trip(Integer.parseInt(cursor.getString(7)));
                record.set_user(Integer.parseInt(cursor.getString(8)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Gets lost gear trip record count.
     *
     * @param id the id
     * @return the lost gear trip record count
     */
// Getting records Count
    int getLostGearTripRecordCount(String id) {
        String countQuery = "SELECT * FROM " + LOST_GEAR_RECORD + " WHERE " + KEY_TRIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Update lost gear tripid record boolean.
     *
     * @param updateval the updateval
     * @param searchval the searchval
     * @return the boolean
     */
// Updating single record
    boolean updateLostGearTripidRecord(String updateval, String searchval) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_TRIP_ID, updateval);

        // updating row
        long i = db.update(LOST_GEAR_RECORD, values, KEY_TRIP_ID + " = ?", new String[]{searchval});
        db.close(); // Closing database connection
        return i > 0;
    }

    /**
     * Update lost gear record int.
     *
     * @param record the record
     * @return the int
     */
// Updating single record
    int updateLostGearRecord(Lost_Gear_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_GEAR_NAME, record.get_gear_name());
        values.put(KEY_GEAR_POSITION, record.get_position());
        values.put(KEY_GEAR_QUANTITY, record.get_gear_quantity());
        values.put(KEY_GEAR_REASON, record.get_reason());
        values.put(KEY_GEAR_REPLACEMENT, record.get_gear_replacement());
        values.put(KEY_GEAR_REPLACEMENT_DETAILS, record.get_replacement_details());

        // updating row
        result = db.update(LOST_GEAR_RECORD, values, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
        return result;
    }

    /**
     * Delete lost gear record.
     *
     * @param record the record
     */
// Deleting single record
    void deleteLostGearRecord(Lost_Gear_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LOST_GEAR_RECORD, KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
    }

    /**
     * Delete all lost gear records.
     */
// Deleting all records
    void deleteAllLostGearRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LOST_GEAR_RECORD, null, null);
        db.close();
    }

    /**
     * Delete all trip lost gear records.
     *
     * @param tripid the tripid
     */
// Deleting all records
    void deleteAllTripLostGearRecords(String tripid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LOST_GEAR_RECORD, KEY_TRIP_ID + " = ?", new String[]{tripid});
        db.close();
    }

    /**
     * Gets lost gear r trip records count.
     *
     * @param id the id
     * @return the lost gear r trip records count
     */
// Getting records Count
    int getLostGearRTripRecordsCount(String id) {
        String countQuery = "SELECT * FROM " + LOST_GEAR_RECORD + " WHERE " + KEY_TRIP_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Gets lost gear records count.
     *
     * @return the lost gear records count
     */
// Getting records Count
    int getLostGearRecordsCount() {
        String countQuery = "SELECT * FROM " + LOST_GEAR_RECORD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Add prawn dip record boolean.
     *
     * @param record the record
     * @return the boolean
     */
// Adding new record
    boolean addPrawnDipRecord(Prawn_Dip_Class record) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ADDED, record.get_date_added());
        values.put(KEY_WATER_VOLUME, record.get_water_volume());
        values.put(KEY_DIP_PRODUCT, record.get_dip_product());
        values.put(KEY_AMOUNT_USED, record.get_amount_used());
        values.put(KEY_CREW_RESPONSIBLE, record.get_crew_responsible());
        values.put(KEY_TRIP_ID, record.get_trip());
        values.put(KEY_USER_ID, record.get_user());

        // Inserting Row
        long rowInserted = db.insert(PRAWN_DIP_RECORD, null, values);
        if (rowInserted != -1) {
            result = true;
        }
        db.close(); // Closing database connection
        return result;
    }

    /**
     * Gets all prawn dip records.
     *
     * @param tripid the tripid
     * @return the all prawn dip records
     */
// Getting All records
    List<Prawn_Dip_Class> getAllPrawnDipRecords(String tripid) {
        List<Prawn_Dip_Class> recordList = new ArrayList<Prawn_Dip_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + PRAWN_DIP_RECORD + " WHERE " + KEY_TRIP_ID + " = " + tripid;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Prawn_Dip_Class record = new Prawn_Dip_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_water_volume(Integer.parseInt(cursor.getString(2)));
                record.set_trip(Integer.parseInt(cursor.getString(3)));
                record.set_user(Integer.parseInt(cursor.getString(4)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return recordList;
    }

    /**
     * Gets all current trip prawn dip records.
     *
     * @param id the id
     * @return the all current trip prawn dip records
     */
// Getting All records
    List<Prawn_Dip_Class> getAllCurrentTripPrawnDipRecords(String id) {
        List<Prawn_Dip_Class> recordList = new ArrayList<Prawn_Dip_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + PRAWN_DIP_RECORD + " WHERE " + KEY_TRIP_ID + "=" + id + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Prawn_Dip_Class record = new Prawn_Dip_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_water_volume(Integer.parseInt(cursor.getString(2)));
                record.set_amount_used(Integer.parseInt(cursor.getString(3)));
                record.set_crew_responsible(cursor.getString(4));
                record.set_trip(Integer.parseInt(cursor.getString(5)));
                record.set_user(Integer.parseInt(cursor.getString(6)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return record list
        return recordList;
    }

    /**
     * Gets all prawn dip records.
     *
     * @return the all prawn dip records
     */
// Getting All records
    List<Prawn_Dip_Class> getAllPrawnDipRecords() {
        List<Prawn_Dip_Class> recordList = new ArrayList<Prawn_Dip_Class>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + PRAWN_DIP_RECORD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Prawn_Dip_Class record = new Prawn_Dip_Class();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.set_date_added(cursor.getString(1));
                record.set_water_volume(Integer.parseInt(cursor.getString(2)));
                record.set_amount_used(Integer.parseInt(cursor.getString(3)));
                record.set_crew_responsible(cursor.getString(4));
                record.set_trip(Integer.parseInt(cursor.getString(5)));
                record.set_user(Integer.parseInt(cursor.getString(6)));
                // Adding record to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return record list
        return recordList;
    }

    /**
     * Delete prawn dip records by trip.
     *
     * @param tripid the tripid
     */
// Deleting single record
    void deletePrawnDipRecordsByTrip(String tripid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PRAWN_DIP_RECORD, KEY_TRIP_ID + " = ?",
                new String[]{tripid});
        db.close();
    }

    /**
     * Gets d prawn dip records by trip count.
     *
     * @param tripid the tripid
     * @return the d prawn dip records by trip count
     */
    int getDPrawnDipRecordsByTripCount(String tripid) {
        String countQuery = "SELECT * FROM " + PRAWN_DIP_RECORD + " WHERE " + KEY_TRIP_ID + " = " + tripid;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Gets prawn dip records count.
     *
     * @return the prawn dip records count
     */
// Getting records Count
    int getPrawnDipRecordsCount() {
        String countQuery = "SELECT * FROM " + PRAWN_DIP_RECORD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return cnt;
    }

    /**
     * Delete all trip prawn dip records.
     *
     * @param tripid the tripid
     */
// Deleting all records
    void deleteAllTripPrawnDipRecords(String tripid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PRAWN_DIP_RECORD, KEY_TRIP_ID + " = ?", new String[]{tripid});

        db.close();
    }

    /**
     * Delete all prawn dip records.
     */
// Deleting all records
    void deleteAllPrawnDipRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PRAWN_DIP_RECORD, null, null);

        db.close();
    }
}