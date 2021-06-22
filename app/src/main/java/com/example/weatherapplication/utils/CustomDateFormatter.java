package com.example.weatherapplication.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CustomDateFormatter {

    public enum DATE_ENUM{
        DMY_WITH_SLASH,YMD_WITH_DASH,DMY_WITH_DASH,DMY_WITH_MONTH_NAME
    }
    private static final SimpleDateFormat SDF_DMY_WITH_SLASH = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private static final SimpleDateFormat SDF_YMD_WITH_DASH = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private static final SimpleDateFormat SDF_YMD_HMS_WITH_DASH = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
    private static final SimpleDateFormat SDF_DMY_HMS_WITH_DASH = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.US);
    private static final SimpleDateFormat SDF_MMM = new SimpleDateFormat("MMM", Locale.US);
    private static final SimpleDateFormat SDF_DD = new SimpleDateFormat("dd", Locale.US);
    private static final SimpleDateFormat SDF_EEEE = new SimpleDateFormat("EEEE", Locale.US);
    private static final SimpleDateFormat SDF_MDY = new SimpleDateFormat("MMMM dd,yyyy", Locale.US);
    private static final SimpleDateFormat SDF_EDM = new SimpleDateFormat("EEE,dd MMM", Locale.US);

    public static long getDifferenceBetweenDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static long getDifferenceBetweenDays(String start_date, String end_date)
    {
        long difference = 0;

        // Try Class
        try {
            Date d1 = SDF_YMD_WITH_DASH.parse(start_date);
            Date d2 = SDF_YMD_WITH_DASH.parse(end_date);
            difference=getDifferenceBetweenDays(d1,d2);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return difference;
    }



    public String formatDateYMD(String inDate) {
        String outDate = "";
        if (inDate != null) {
            try {
                Date date = SDF_DMY_WITH_SLASH.parse(inDate);
                outDate = SDF_YMD_WITH_DASH.format(date);
            } catch (ParseException ex){
            }
        }
        return outDate;
    }


    public String formatDateDMY(String inDate) {
        String outDate = "";
        if (inDate != null) {
            try {
                Date date = SDF_YMD_WITH_DASH.parse(inDate);
                outDate = SDF_DMY_WITH_SLASH.format(date);
            } catch (ParseException ex){
            }
        }
        return outDate;
    }

    public String formatDateDMYHMS(String date){
        String outDate = "";
        try {
            Date d1 = SDF_YMD_HMS_WITH_DASH.parse(date);
            outDate = SDF_MMM.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outDate;
    }

    public String formatDateMMM(String date){
        String outDate = "";
        try {
            Date d1 = SDF_YMD_HMS_WITH_DASH.parse(date);
            outDate = SDF_MMM.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outDate.toUpperCase();
    }

    public String formatDateDD(String date){
        String outDate = "";
        try {
            Date d1 = SDF_YMD_HMS_WITH_DASH.parse(date);
            outDate = SDF_DD.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outDate;
    }

    public String formatDateMDY(String date){
        String outDate = "";
        try {
            Date d1 = SDF_YMD_WITH_DASH.parse(date);
            outDate = SDF_MDY.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outDate;
    }

    public String formatDateEMD(String date){
        String outDate = "";
        try {
            Date d1 = SDF_YMD_WITH_DASH.parse(date);
            outDate = SDF_EDM.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outDate;
    }


    public String getDateCategory(String date) throws ParseException {

        int daysGap = (int)getDifferenceBetweenDays(date,getTodayDate(DATE_ENUM.YMD_WITH_DASH));
        if(daysGap==0){
            return "Today";
        }
        else if(daysGap==-1){
            return "Tomorrow";
        }
        else {
            Date d1 = SDF_YMD_WITH_DASH.parse(date);
            return SDF_EEEE.format(d1);
        }

    }

    public String getDateCategoryByDate(String date) throws ParseException {

        int daysGap = (int)getDifferenceBetweenDays(date,getTodayDate(DATE_ENUM.YMD_WITH_DASH));
        if(daysGap==0){
            return "Today";
        }
        else if(daysGap==1){
            return "Yesterday";
        }
        else {
            Date d1 = SDF_YMD_WITH_DASH.parse(date);
            return SDF_EEEE.format(d1);
        }

    }


    public int getDayNumberOfWeek(String day){
        switch (day){
            case "MONDAY":
            case "MON":
                return 1;
            case "TUESDAY":
            case "TUE":
                return 2;
            case "WEDNESDAY":
            case "WED":
                return 3;
            case "THURSDAY":
            case "THU":
                return 4;
            case "FRIDAY":
            case "FRI":
                return 5;
            case "SATURDAY":
            case "SAT":
                return 6;
            case "SUNDAY":
            case "SUN":
                return 7;

            default:
                return 0;
        }
    }


    public String getTodayDate(DATE_ENUM date_enum){
        Date date = new Date();
        switch (date_enum){
            case DMY_WITH_SLASH:
                return SDF_DMY_WITH_SLASH.format(date);
            case YMD_WITH_DASH:
                return SDF_YMD_WITH_DASH.format(date);
            case DMY_WITH_MONTH_NAME:
                return SDF_MDY.format(date);
            default:
                return null;
        }

    }


    public int getCurrentMonthNumber(){
        Date date= new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        return cal1.get(Calendar.MONTH);
    }


    public ArrayList<String> getCurrentMonthDays(DATE_ENUM date_enum){
        ArrayList<String> dateList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, getCurrentMonthNumber());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < maxDay; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i + 1);
            switch (date_enum){
                case DMY_WITH_SLASH:
                    dateList.add(SDF_DMY_WITH_SLASH.format(cal.getTime()));
                    break;
                case YMD_WITH_DASH:
                    dateList.add(SDF_YMD_WITH_DASH.format(cal.getTime()));
                    break;
                default:
                    return null;
            }
        }

        return dateList;
    }

    public String fetchDayFromDate(String date) throws ParseException {
        Date d1 = SDF_YMD_WITH_DASH.parse(date);
        return SDF_EEEE.format(d1);
    }



    public ArrayList<String> getDatePlusDays(String startDate, int days) throws ParseException {
        Date d1 = SDF_YMD_WITH_DASH.parse(startDate);
        return getDaysBetweenDates(d1,getDateAfterCountDays(d1,days));
    }
    public ArrayList<String> getDaysBetweenDates(Date startDate, Date endDate)
    {
        ArrayList<String> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate))
        {
            dates.add(SDF_YMD_WITH_DASH.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public Date getDateAfterCountDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date); // Now use today date.
        c.add(Calendar.DATE, days); // Adding n days
        return c.getTime();
    }



}
