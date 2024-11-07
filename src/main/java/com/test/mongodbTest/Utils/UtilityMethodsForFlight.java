package com.test.mongodbTest.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UtilityMethodsForFlight {

    private static final DateFormat IST_FORMAT;
    private static final DateFormat UTC_FORMAT;

    static {
        // Correct format for parsing the given date string
        IST_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        IST_FORMAT.setTimeZone(TimeZone.getTimeZone("IST"));

        // Keeping UTC format for storage in ISO format
        UTC_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        UTC_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static Date convertIstToUtc(String istDate) {
        if (istDate == null) return null;
        try {
            // Parse the IST date string into a Date object
            Date timestamp = IST_FORMAT.parse(istDate);
            return timestamp; // Returning the Date object
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle error appropriately
        }
    }
}
