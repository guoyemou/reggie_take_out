package com.yezi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String parse(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
