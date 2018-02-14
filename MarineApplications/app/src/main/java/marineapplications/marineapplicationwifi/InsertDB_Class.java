package marineapplications.marineapplicationwifi;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by kieranmoroney on 20/02/2017.
 */
public class InsertDB_Class {

    /**
     * Error insert db boolean.
     *
     * @param errormessage the errormessage
     * @param errortype    the errortype
     * @param userid       the userid
     * @param context      the context
     * @return the boolean
     */
//Error Log Insert
    boolean errorInsertDB(String errormessage, String errortype, String userid, Context context) {
        Error_Log_Class record;
        Configure_Class confclass;
        DatabaseHelper dbHelper;
        boolean result = false;
        try {
            dbHelper = new DatabaseHelper(context);
            confclass = new Configure_Class();
            record = new Error_Log_Class();
            record.set_date_added(confclass.getDateCurrentTimeZone());
            record.set_error_text(errormessage);
            record.set_error_type(errortype);
            record.set_user(Integer.parseInt(userid));
            result = dbHelper.addErrorRecord(record);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Trip record insert db int.
     *
     * @param Starttrip  the starttrip
     * @param user_id    the user id
     * @param triplog    the triplog
     * @param triptype   the triptype
     * @param tripdevice the tripdevice
     * @param onlineid   the onlineid
     * @param context    the context
     * @return the int
     */
//Add Trip Record Insert
    int TripRecordInsertDB(String Starttrip, String user_id, String triplog, String triptype, String tripdevice, String onlineid, Context context) {
        Trip_Class record;
        DatabaseHelper dbHelper;
        int result = 0;
        String empty = "";
        int emptyno = 0;
        int oid = !Objects.equals(onlineid, "") ? Integer.parseInt(onlineid) : emptyno;
        try {
            dbHelper = new DatabaseHelper(context);
            record = new Trip_Class();
            record.set_online_id(oid);
            record.set_user(Integer.parseInt(user_id));
            record.set_start_date(Starttrip);
            record.set_log_no(triplog);
            record.set_end_date(empty);
            record.set_cleaning_schedule_completed(empty);
            record.set_prawn_dip(empty);
            record.set_scales_test(empty);
            record.set_target_procedure(empty);
            record.set_line_caught_procedure(empty);
            record.set_waste_ashore(empty);
            record.set_trip_type(triptype);
            record.set_device_type(tripdevice);
            record.set_prawn_counter(emptyno);
            result = dbHelper.addTripRecord(record);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Update trip iddb boolean.
     *
     * @param context        the context
     * @param tripid         the tripid
     * @param online_trip_id the online trip id
     * @return the boolean
     */
//Update Online Trip ID
    boolean UpdateTripIDDB(Context context, String tripid, String online_trip_id) {
        Trip_Class record;
        DatabaseHelper dbHelper;
        boolean result = false;
        try {
            dbHelper = new DatabaseHelper(context);
            record = new Trip_Class();
            record.setId(Integer.parseInt(tripid));
            record.set_online_id(Integer.parseInt(online_trip_id));
            result = dbHelper.updateOnlineTripID(record);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Update trip prawn dip counter boolean.
     *
     * @param context  the context
     * @param tripid   the tripid
     * @param prawndip the prawndip
     * @return the boolean
     */
//Update Online Trip ID
    boolean UpdateTripPrawnDipCounter(Context context, String tripid, int prawndip) {
        Trip_Class record;
        DatabaseHelper dbHelper;
        boolean result = false;
        try {
            dbHelper = new DatabaseHelper(context);
            record = new Trip_Class();
            record.setId(Integer.parseInt(tripid));
            record.set_prawn_counter(prawndip);
            result = dbHelper.updatePrawnDipCounter(record);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Trip record update db boolean.
     *
     * @param context          the context
     * @param tripid           the tripid
     * @param endtrip          the endtrip
     * @param cleaningschedule the cleaningschedule
     * @param prawndip         the prawndip
     * @param scaletest        the scaletest
     * @param targetproc       the targetproc
     * @param lineproc         the lineproc
     * @param wasteAshore      the waste ashore
     * @return the boolean
     */
//Update Trip Records
    Boolean TripRecordUpdateDB(Context context, String tripid, String endtrip, String cleaningschedule, String prawndip, String scaletest, String targetproc, String lineproc, String wasteAshore) {
        Trip_Class record;
        DatabaseHelper dbHelper;
        Boolean result = null;
        try {
            dbHelper = new DatabaseHelper(context);
            record = new Trip_Class();
            record.setId(Integer.parseInt(tripid));
            record.set_end_date(endtrip);
            record.set_cleaning_schedule_completed(cleaningschedule);
            record.set_prawn_dip(prawndip);
            record.set_scales_test(scaletest);
            record.set_target_procedure(targetproc);
            record.set_line_caught_procedure(lineproc);
            record.set_waste_ashore(wasteAshore);
            result = dbHelper.updateTripRecord(record);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Temperature record insert db boolean.
     *
     * @param context      the context
     * @param datetimepick the datetimepick
     * @param Chilled      the chilled
     * @param Freezer      the freezer
     * @param Blast        the blast
     * @param Core         the core
     * @param GPS          the gps
     * @param tripid       the tripid
     * @param userid       the userid
     * @param inputtype    the inputtype
     * @return the boolean
     */
//Add Temperature Record
    Boolean TemperatureRecordInsertDB(Context context, String datetimepick, String Chilled, String Freezer, String Blast, String Core, String GPS, String tripid, String userid, String inputtype) {
        Temperature_Class record;
        DatabaseHelper dbHelper;
        Boolean result = false;

        try {
            dbHelper = new DatabaseHelper(context);
            record = new Temperature_Class();
            record.set_date_added(datetimepick);
            record.set_chilled(Chilled);
            record.set_freezer(Freezer);
            record.set_blast(Blast);
            record.set_core(Core);
            record.set_gps(GPS);
            record.set_input_type(inputtype);
            record.set_trip(Integer.parseInt(tripid));
            record.set_user(Integer.parseInt(userid));
            result = dbHelper.addTemperatureRecord(record);
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Prawn dip record insert db boolean.
     *
     * @param context          the context
     * @param datepick         the datepick
     * @param water_volume     the water volume
     * @param dp_product       the dp product
     * @param amount_used      the amount used
     * @param crew_responsible the crew responsible
     * @param tripid           the tripid
     * @param userid           the userid
     * @return the boolean
     */
//Add Prawn Dip Record
    Boolean PrawnDipRecordInsertDB(Context context, String datepick, String water_volume, String dp_product, String amount_used, String crew_responsible, String tripid, String userid) {
        Prawn_Dip_Class record;
        DatabaseHelper dbHelper;
        Boolean result = false;

        try {
            dbHelper = new DatabaseHelper(context);
            record = new Prawn_Dip_Class();
            record.set_date_added(datepick);
            record.set_water_volume(Integer.parseInt(water_volume));
            record.set_dip_product(dp_product);
            record.set_amount_used(Integer.parseInt(amount_used));
            record.set_crew_responsible(crew_responsible);
            record.set_trip(Integer.parseInt(tripid));
            record.set_user(Integer.parseInt(userid));
            result = dbHelper.addPrawnDipRecord(record);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Communication record insert db boolean.
     *
     * @param context        the context
     * @param datepick       the datepick
     * @param vessel_from    the vessel from
     * @param vessel_to      the vessel to
     * @param vessel_comment the vessel comment
     * @param tripid         the tripid
     * @param userid         the userid
     * @return the boolean
     */
//Add Communication Record
    Boolean CommunicationRecordInsertDB(Context context, String datepick, String vessel_from, String vessel_to, String vessel_comment, String tripid, String userid) {
        Communication_Class record;
        DatabaseHelper dbHelper;
        Boolean result = false;

        try {
            dbHelper = new DatabaseHelper(context);
            record = new Communication_Class();
            record.set_date_added(datepick);
            record.set_vessel_from(vessel_from);
            record.set_vessel_to(vessel_to);
            record.set_vessel_comment(vessel_comment);
            record.set_trip(Integer.parseInt(tripid));
            record.set_user(Integer.parseInt(userid));
            result = dbHelper.addCommunicationRecord(record);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Dispatch record insert db boolean.
     *
     * @param context          the context
     * @param datepick         the datepick
     * @param timepick         the timepick
     * @param dispatchquantity the dispatchquantity
     * @param dispatchport     the dispatchport
     * @param dispatchbuyer    the dispatchbuyer
     * @param dispatchemail    the dispatchemail
     * @param tripid           the tripid
     * @param userid           the userid
     * @return the boolean
     */
//Add Dispatch Record
    Boolean DispatchRecordInsertDB(Context context, String datepick, String timepick, String dispatchquantity, String dispatchport, String dispatchbuyer, String dispatchemail, String tripid, String userid) {
        Dispatch_Class record;
        DatabaseHelper dbHelper;
        Boolean result = false;

        try {
            dbHelper = new DatabaseHelper(context);
            record = new Dispatch_Class();
            record.set_date_added("" + datepick + " " + timepick + "");
            record.set_dispatch_quantity(dispatchquantity);
            record.set_dispatch_port(dispatchport);
            record.set_dispatch_buyer(dispatchbuyer);
            record.set_dispatch_email(dispatchemail);
            record.set_trip(Integer.parseInt(tripid));
            record.set_user(Integer.parseInt(userid));
            result = dbHelper.addDispatchRecord(record);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Incident record insert db boolean.
     *
     * @param context         the context
     * @param datepick        the datepick
     * @param incidenttype    the incidenttype
     * @param incidentdetails the incidentdetails
     * @param incidentactions the incidentactions
     * @param tripid          the tripid
     * @param userid          the userid
     * @return the boolean
     */
//Add Incident Record
    Boolean IncidentRecordInsertDB(Context context, String datepick, String incidenttype, String incidentdetails, String incidentactions, String tripid, String userid) {
        Incident_Class record;
        DatabaseHelper dbHelper;
        Boolean result = false;

        try {
            dbHelper = new DatabaseHelper(context);
            record = new Incident_Class();
            record.set_date_added(datepick);
            record.set_incident_type(incidenttype);
            record.set_incident_details(incidentdetails);
            record.set_incident_action(incidentactions);
            record.set_trip(Integer.parseInt(tripid));
            record.set_user(Integer.parseInt(userid));
            result = dbHelper.addIncidentRecord(record);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lost gear record insert db boolean.
     *
     * @param context         the context
     * @param datepick        the datepick
     * @param lostgear        the lostgear
     * @param lostposition    the lostposition
     * @param lostqty         the lostqty
     * @param lostreason      the lostreason
     * @param lostreplacement the lostreplacement
     * @param lostdetails     the lostdetails
     * @param tripid          the tripid
     * @param userid          the userid
     * @return the boolean
     */
//Add Lost Gear Record
    Boolean LostGearRecordInsertDB(Context context, String datepick, String lostgear, String lostposition, int lostqty, String lostreason, String lostreplacement, String lostdetails, String tripid, String userid) {
        Lost_Gear_Class record;
        DatabaseHelper dbHelper;
        Boolean result = false;

        try {
            dbHelper = new DatabaseHelper(context);
            record = new Lost_Gear_Class();
            record.set_date_added(datepick);
            record.set_gear_name(lostgear);
            record.set_gear_quantity(lostqty);
            record.set_gear_replacement(lostreplacement);
            record.set_position(lostposition);
            record.set_reason(lostreason);
            record.set_replacement_details(lostdetails);
            record.set_trip(Integer.parseInt(tripid));
            record.set_user(Integer.parseInt(userid));
            result = dbHelper.addLostGearRecord(record);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Marine litter record insert db boolean.
     *
     * @param context        the context
     * @param datepick       the datepick
     * @param vesselport     the vesselport
     * @param vesselquantity the vesselquantity
     * @param tripid         the tripid
     * @param userid         the userid
     * @return the boolean
     */
//Add Marine Litter Record
    Boolean MarineLitterRecordInsertDB(Context context, String datepick, String vesselport, String vesselquantity, String tripid, String userid) {
        Marine_Litter_Class record;
        DatabaseHelper dbHelper;
        Boolean result = false;

        try {
            dbHelper = new DatabaseHelper(context);
            record = new Marine_Litter_Class();
            record.set_date_added(datepick);
            record.set_vessel_port(vesselport);
            record.set_marine_litter_quantity(vesselquantity);
            record.set_trip(Integer.parseInt(tripid));
            record.set_user(Integer.parseInt(userid));
            result = dbHelper.addMarineRecord(record);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Slippage record insert db boolean.
     *
     * @param context           the context
     * @param datepick          the datepick
     * @param Ices_sub_area     the ices sub area
     * @param slippage_position the slippage position
     * @param target_Fisheries  the target fisheries
     * @param species_Slipped   the species slipped
     * @param slippage_Quantity the slippage quantity
     * @param slippage_Size     the slippage size
     * @param SlippageReason    the slippage reason
     * @param sample_taken      the sample taken
     * @param tripid            the tripid
     * @param userid            the userid
     * @return the boolean
     */
//Add Slippage Record
    Boolean SlippageRecordInsertDB(Context context, String datepick, String Ices_sub_area, String slippage_position, String target_Fisheries, String species_Slipped, String slippage_Quantity, String slippage_Size, String SlippageReason, String sample_taken, String tripid, String userid) {
        Slippage_Class record;
        DatabaseHelper dbHelper;
        Boolean result = false;

        try {
            dbHelper = new DatabaseHelper(context);
            record = new Slippage_Class();
            record.set_date_added(datepick);
            record.set_ices_sub_area(Ices_sub_area);
            record.set_position(slippage_position);
            record.set_target_fisheries(target_Fisheries);
            record.set_species_slipped(species_Slipped);
            record.set_species_quantity(Integer.parseInt(slippage_Quantity));
            record.set_slippage_reason(SlippageReason);
            record.set_sample_taken(sample_taken);
            record.set_trip(Integer.parseInt(tripid));
            record.set_user(Integer.parseInt(userid));
            result = dbHelper.addSlippageRecord(record);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Whale dolphin record insert db boolean.
     *
     * @param context   the context
     * @param datepick  the datepick
     * @param timepick  the timepick
     * @param gpslat    the gpslat
     * @param gpslong   the gpslong
     * @param species   the species
     * @param groupsize the groupsize
     * @param tripid    the tripid
     * @param userid    the userid
     * @return the boolean
     */
//Add Whale and Dolphin Record
    Boolean WhaleDolphinRecordInsertDB(Context context, String datepick, String timepick, String gpslat, String gpslong, String species, String groupsize, String tripid, String userid) {
        WhaleDolphin_Class record;
        DatabaseHelper dbHelper;
        Boolean result = false;

        try {
            dbHelper = new DatabaseHelper(context);
            record = new WhaleDolphin_Class();
            record.set_date_added("" + datepick + " " + timepick + "");
            record.set_gps_lat(gpslat);
            record.set_gps_long(gpslong);
            record.setSpecies(species);
            record.set_group_size(Integer.parseInt(groupsize));
            record.set_trip(Integer.parseInt(tripid));
            record.set_user(Integer.parseInt(userid));
            result = dbHelper.addWhaleDolphinRecord(record);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Visitor record insert db boolean.
     *
     * @param context         the context
     * @param datepick        the datepick
     * @param visitor_name    the visitor name
     * @param visitor_reason  the visitor reason
     * @param visitor_comment the visitor comment
     * @param tripid          the tripid
     * @param userid          the userid
     * @return the boolean
     */
    Boolean VisitorRecordInsertDB(Context context, String datepick, String visitor_name, String visitor_reason, String visitor_comment, String tripid, String userid) {
        Visitor_Class record;
        DatabaseHelper dbHelper;
        Boolean result = false;

        try {
            dbHelper = new DatabaseHelper(context);
            record = new Visitor_Class();
            record.set_date_added(datepick);
            record.set_visitor_name(visitor_name);
            record.set_visitor_reason(visitor_reason);
            record.set_visitor_comment(visitor_comment);
            record.set_trip(Integer.parseInt(tripid));
            record.set_user(Integer.parseInt(userid));
            result = dbHelper.addVisitorRecord(record);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Insert post record online json object.
     *
     * @param url_address the url address
     * @param params      the params
     * @return the json object
     */
//Insert POST Records Online
    JSONObject insertPOSTRecordOnline(String url_address, HashMap<String, String> params) {
        JSONParser jsonParser;
        JSONObject json = null;

        try {
            jsonParser = new JSONParser();
            json = jsonParser.makeHttpRequest(
                    url_address, "POST", params);

            if (json != null) {
                Log.d("JSON return result", json.toString());
                return json;
            }
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * Insert post record local json object.
     *
     * @param url_address the url address
     * @param params      the params
     * @return the json object
     */
    JSONObject insertPOSTRecordLocal(String url_address, HashMap<String, String> params) {
        JSONParserHttp jsonParser;
        JSONObject json = null;

        try {
            jsonParser = new JSONParserHttp();
            json = jsonParser.makeHttpRequest(
                    url_address, "POST", params);

            if (json != null) {
                Log.d("JSON return result", json.toString());
                return json;
            }
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * Insert post record local string string.
     *
     * @param url_address the url address
     * @param params      the params
     * @return the string
     */
    String insertPOSTRecordLocalString(String url_address, HashMap<String, String> params) {
        JSONParserHttp jsonParser;
        String results = null;

        try {
            jsonParser = new JSONParserHttp();
            results = jsonParser.makeHttpRequestbyString(
                    url_address, "POST", params);

            if (results != null) {
                Log.d("JSON return result", results);
                return results;
            }
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}				