package com.yuale01.mis.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.codec.Charsets;

public class Tools {
    public synchronized static long getCurrentLongTime() {
        return System.currentTimeMillis();
    }

    public static String convertLongTimeToStr(Long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat(Constants.TIME_FORMAT);
        return formatter.format(date);
    }

    public static Long parseTimeFromStr(String time) {
        if (time == null)
            return null;

        DateFormat formatter = new SimpleDateFormat(Constants.TIME_FORMAT);
        Date date = null;
        try {
            date = formatter.parse(time);
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (date == null)
            return null;
        else
            return date.getTime();
    }

    public static Properties loadProperties(String locale) {
        Properties prop = null;
        InputStream input = null;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try {
            if (locale != null && classloader.getResource("i18n/resources_" + locale + ".properties") != null) {
                input = classloader.getResourceAsStream("i18n/resources_" + locale + ".properties");
            }
            else
                input = classloader.getResourceAsStream("i18n/resources.properties");
            prop = new Properties();
            prop.load(new InputStreamReader(input, Charsets.UTF_8));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }

}
