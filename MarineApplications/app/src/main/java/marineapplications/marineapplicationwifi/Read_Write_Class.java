package marineapplications.marineapplicationwifi;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kieranmoroney on 13/02/2017.
 */
public class Read_Write_Class {

    /**
     * Convert to json string.
     *
     * @param json the json
     * @return the string
     */
    String convertToJSON(JSONObject json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(json);
    }

    /**
     * Asset json file string.
     *
     * @param filename the filename
     * @param context  the context
     * @return the string
     * @throws IOException the io exception
     */
    String AssetJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }

    /**
     * Is file present boolean.
     *
     * @param context  the context
     * @param fileName the file name
     * @return the boolean
     */
    boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

    /**
     * Save data.
     *
     * @param context       the context
     * @param mJsonResponse the m json response
     * @param filename      the filename
     */
    void saveData(Context context, String mJsonResponse, String filename) {
        try {
            String path = context.getFilesDir().getPath() + "/" + filename;
            File myFile = new File(path);
            if (!myFile.exists()) {
                if (myFile.createNewFile()) {
                    System.out.println("File is created!");
                }
            }

            FileWriter file = new FileWriter(context.getFilesDir().getPath() + "/" + filename, false);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            Log.e("TAG", "Error in Writing: " + e.getLocalizedMessage());
        }
    }

    /**
     * Save append data.
     *
     * @param context       the context
     * @param mJsonResponse the m json response
     * @param filename      the filename
     */
    void saveAppendData(Context context, String mJsonResponse, String filename) {
        try {
            File myFile = new File(context.getFilesDir().getPath() + "/" + filename);
            if (!myFile.exists()) {
                if (myFile.createNewFile()) {
                    System.out.println("File is created!");
                }
            }

            FileWriter file = new FileWriter(context.getFilesDir().getPath() + "/" + filename);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            Log.e("TAG", "Error in Writing: " + e.getLocalizedMessage());
        }
    }

    /**
     * Gets data.
     *
     * @param context  the context
     * @param filename the filename
     * @return the data
     */
    String getData(Context context, String filename) {
        try {
            File f = new File(context.getFilesDir().getPath() + "/" + filename);
            if (!f.exists()) {
                if (f.createNewFile()) {
                    System.out.println("File is created!");
                }
            }

            //check whether file exists
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            Log.e("TAG", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }


    /**
     * Gets json data.
     *
     * @param context  the context
     * @param filename the filename
     * @return the json data
     */
    JSONObject getJSONData(Context context, String filename) {
        JSONObject jObj = null;
        try {
            File f = new File(context.getFilesDir().getPath() + "/" + filename);
            if (!f.exists()) {
                if (f.createNewFile()) {
                    System.out.println("File is created!");
                }
            }

            //check whether file exists
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String returndata = new String(buffer);
            try {
                jObj = new JSONObject(returndata);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            return jObj;
        } catch (IOException e) {
            Log.e("TAG", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Read file string.
     *
     * @param context  the context
     * @param filename the filename
     * @return the string
     */
    String ReadFile(Context context, String filename) {
        try {
            File f = new File(filename);
            //check whether file exists
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            Log.e("TAG", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Delete file boolean.
     *
     * @param context  the context
     * @param filename the filename
     * @return the boolean
     */
    boolean deleteFile(Context context, String filename) {
        boolean status = false;
        File f = new File(context.getFilesDir().getPath() + "/" + filename);
        if (f.exists()) {
            status = f.delete();
        }
        return status;
    }
}