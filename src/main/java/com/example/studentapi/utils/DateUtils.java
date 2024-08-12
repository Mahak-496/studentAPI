package com.example.studentapi.utils;

import java.sql.Timestamp;

public class DateUtils {
    public static Timestamp fromDateToTimestamp(String date) {

        return Timestamp.valueOf(date + " 00:00:00");

    }

    public static Timestamp toDateToTimestamp(String date) {
        return Timestamp.valueOf(date + " 23:59:59");
    }
}
