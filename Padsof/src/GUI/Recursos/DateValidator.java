/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * http://www.mkyong.com/regular-expressions/how-to-validate-date-with-regular-expression/
 */
package GUI.Recursos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateValidator {

    private Pattern pattern;
    private Matcher matcher;
    private static final String DATE_PATTERN =
            "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

    public DateValidator() {
        pattern = Pattern.compile(DATE_PATTERN);
    }

    /**
     * Validate date format with regular expression
     *
     * @param date date address for validation
     * @return true valid date fromat, false invalid date format
     */
    public boolean validate(final String date) {

        matcher = pattern.matcher(date);

        if (matcher.matches()) {

            matcher.reset();

            if (matcher.find()) {

                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31")
                        && (month.equals("4") || month.equals("6") || month.equals("9")
                        || month.equals("11") || month.equals("04") || month.equals("06")
                        || month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if (year % 4 == 0) {
                        if (day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (day.equals("29") || day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    /**
     * Obtiene el d&iacute;a de una fecha con formato dd/mm/yyyy
     * @param date
     * @return 
     */
    public static int obtainDay(String date) {
        String[] elems = date.split("/");
        return Integer.parseInt(elems[0]);
    }
    
    /**
     * Obtiene el mes de una fecha con formato dd/mm/yyyy
     * @param date
     * @return 
     */
    public static int obtainMonth(String date) {
        String[] elems = date.split("/");
        return Integer.parseInt(elems[1]);
    }
    
    /**
     * Obtiene el a&ntilde;o de una fecha con formato dd/mm/yyyy
     * @param date
     * @return 
     */
    public static int obtainYear(String date) {
        String[] elems = date.split("/");
        return Integer.parseInt(elems[2]);
    }
    
    /**
     * Obtiene el d&iacute;a de una fecha con formato yyyy/dd/mm
     * @param date
     * @return 
     */
    public static int obtainDayV2(String date) {
        String[] elems = date.split("/");
        return Integer.parseInt(elems[1]);
    }
    
    /**
     * Obtiene el mes de una fecha con formato yyyy/dd/mm
     * @param date
     * @return 
     */
    public static int obtainMonthV2(String date) {
        String[] elems = date.split("/");
        return Integer.parseInt(elems[2]);
    }
    
    /**
     * Obtiene el a&ntilde;o de una fecha con formato yyyy/dd/mm
     * @param date
     * @return 
     */
    public static int obtainYearV2(String date) {
        String[] elems = date.split("/");
        return Integer.parseInt(elems[0]);
    }

    /**
     * Compara 2 fechas de formato dd/mm/yyyy
     * @param date1
     * @param date2
     * @return &lt;0 es menor date1
     *          &gt;0 es mayor date2
     */
    public static int compareDates(String date1, String date2) {
        int day1 = DateValidator.obtainDay(date1);
        int day2 = DateValidator.obtainDay(date2);
        int month1 = DateValidator.obtainMonth(date1);
        int month2 = DateValidator.obtainMonth(date2);
        int year1 = DateValidator.obtainYear(date1);
        int year2 = DateValidator.obtainYear(date2);
        
        if(year1 != year2) {return year1 - year2;}
        else if(month1 != month2) {return month1 - month2;}
        else if(day1 != day2) {return day1 - day2;}
        
        return 0;
    }
    
    /**
     * Devuelve una cadena con el formato hh:mm, con la hora y los minutos especificados.
     * @param hour
     * @param minutes
     * @return 
     */
    public static String formatHour(int hour, int minutes) {
        String hourStr = (new Integer(hour)).toString();
        if(hourStr.length() == 1) {
            hourStr = "0" + hourStr;
        }
        
        String minutesStr = (new Integer(minutes)).toString();
        if(minutesStr.length() == 1) {
            minutesStr = "0" + minutesStr;
        }
        
        return hourStr + ":" + minutesStr;
    }
}
