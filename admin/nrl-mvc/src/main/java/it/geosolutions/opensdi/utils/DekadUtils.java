package it.geosolutions.opensdi.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DekadUtils {
public static int absouluteFromYearMonthDek(int year, int month, int dek) {
    return year * 36 + ((month - 1) * 3) + dek;
}

/**
 * First day: can be 01, 11 or 21
 * 
 * @param year
 * @param month
 * @param dek
 * @return first day of a dekad
 */
public static final int getFirstDay(int year, int month, int dek) {
    return (dek * 10) + 1;
}

/**
 * End day: can be: 10, 20 or the last day of the month
 * 
 * @param year
 * @param month
 * @param dek
 * @return last day of a dekad
 */
public static final int getLastDay(int year, int month, int dek) {
    int startDay = getFirstDay(year, month, dek);
    Integer endDay = startDay + 10;
    if (endDay > 29) {
        // last day of the month
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, startDay);
        endDay = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
    }else{
        endDay--;
    }
    return endDay;
}
}
