package com.feedreader.myapplication.tools;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeAdapter {
    public DateTime getDateTime(String s) {
        String[] dateTime = s.split(" ");

        if (dateTime[4].length() == 5) {
            dateTime[4] = dateTime[4] + ":00";
        }
        String dateTimeUpdate = dateTime[0];
        for (int i=1; i<dateTime.length; i++) {
            dateTimeUpdate += " " + dateTime[i];
        }
        DateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss z");
        DateTime result = new DateTime();
        try {
            Date date = sdf.parse(dateTimeUpdate);
            DateTime resultSucceed = new DateTime(date);
            return resultSucceed;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String formatDateTime(DateTime dt) {
        dt.getZone();
        String formattedDateString = dt.toString(DateTimeFormat.forPattern("EEE, dd MMM yyyy kk:mm")) + " AEST";
        return formattedDateString;
    }
}
