package sergiotx.github.io.clase.Dao;

import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sergiotx.github.io.clase.R;

/**
 * Created by SergioTx on 11/05/2017.
 */

public class DateUtils {

    public static Date dateFromString(String dtStart){
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            Log.d("DateUtils.dateFromString","ParseException - " + e);
        }
        return date;
    }

    public static String getStringFromDate(Date date){
        String datetime = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        datetime = dateFormat.format(date);
        return datetime;
    }

    public static String getBeautyString(Date date, Context c){
        if (date == new Date()){
            return c.getResources().getString(R.string.today);
        }
        String datetime = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        datetime = dateFormat.format(date);
        return datetime;
    }


    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

}
