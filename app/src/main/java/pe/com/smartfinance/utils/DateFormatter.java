package pe.com.smartfinance.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Paolo Ortega on 22/08/2016.
 */
public class DateFormatter {
    static String[] rutaAMes = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SETIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};

    public static Date getDate(String dateInString, String pattern) {
        if (dateInString == null) return null;
        if (pattern == null) pattern = "dd-M-yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = df.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getFormatDate(Date date, String pattern) {
        if (date == null) date = new Date();
        if (pattern == null) pattern = "dd-M-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String getCurrentMonth(String newPattern) {
        String pattern = "M";
        if (newPattern != null && !newPattern.isEmpty()) pattern = newPattern;
        Calendar cal = Calendar.getInstance();
        return rutaAMes[Integer.parseInt(new SimpleDateFormat(pattern).format(cal.getTime())) - 1];
    }
}
