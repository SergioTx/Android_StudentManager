package sergiotx.github.io.clase.Dao;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SergioTx on 11/05/2017.
 */

public class Utils {

    public static Date dateFromString(String dtStart){
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            Log.d("Utils.dateFromString","ParseException - " + e);
        }
        return date;
    }

    public static String getStringFromDate(Date date){
        String datetime = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        datetime = dateFormat.format(date);
        return datetime;
    }
}
