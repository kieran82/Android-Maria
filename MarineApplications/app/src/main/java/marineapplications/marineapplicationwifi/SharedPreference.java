package marineapplications.marineapplicationwifi;

/**
 * Created by Frank on 02/03/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;

/**
 * The type Shared preference.
 */
public class SharedPreference {

    /**
     * The constant PREFS_NAME.
     */
    public static final String PREFS_NAME = "MARINE_APP_PREFS";

    /**
     * Instantiates a new Shared preference.
     */
    public SharedPreference() {
        super();
    }

    /**
     * Save.
     *
     * @param context the context
     * @param KEY     the key
     * @param text    the text
     */
    public void save(Context context, String KEY, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putString(KEY, text); //3
        editor.apply(); //4
    }

    /**
     * Gets value.
     *
     * @param context the context
     * @param KEY     the key
     * @return the value
     */
    public String getValue(Context context, String KEY) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(KEY, null);
        return text;
    }

    /**
     * Clear shared preference.
     *
     * @param context the context
     */
    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * Remove value.
     *
     * @param context the context
     * @param KEY     the key
     */
    public void removeValue(Context context, String KEY) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(KEY);
        editor.apply();
    }
}
