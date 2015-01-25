package com.yuale01.mis.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools 
{
	public synchronized static long getCurrentLongTime()
	{
		return System.currentTimeMillis();
	}
	
	public static String convertLongTimeToStr(Long time)
	{
		Date date = new Date(time);
		DateFormat formatter = new SimpleDateFormat(Constants.TIME_FORMAT);
		return formatter.format(date);
	}
	
	public static Long parseTimeFromStr(String time)
	{
		if (time == null)
			return null;
		
		DateFormat formatter = new SimpleDateFormat(Constants.TIME_FORMAT);
		Date date = null;
		try {
			date = formatter.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (date == null)
			return null;
		else
			return date.getTime();
	}

}
