package com.ef.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.StringUtils;

import com.ef.constant.ErrorConstant;
import com.ef.enums.Duration;

public class DateUtil {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	private static SimpleDateFormat paramDateFormat = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
	
	public static Date parseDate(String date) throws ParseException {

		return dateFormat.parse(date);
	}
	
	public static String formatDate(String date) throws ParseException {

		return dateFormat.format(parseParamDate(date));
	}
	
	public static String formatDate(Date date) throws ParseException {

		return dateFormat.format(date);
	}
	
	
	public static Date parseParamDate(String date) throws ParseException {

		return paramDateFormat.parse(date);
	}

	public static String getEndDate(String startDate, String duration) throws Exception {

		String endDate = null;
		
		if (!StringUtils.isEmpty(startDate)) {
			
			if(duration.equalsIgnoreCase(Duration.hourly.name())) {
				
				endDate = dateFormat.format(DateUtils.addHours(parseParamDate(startDate), 1));
			}
			else if(duration.equalsIgnoreCase(Duration.daily.name())){
				
				endDate = dateFormat.format(DateUtils.addDays(parseParamDate(startDate), 1));
			}
			else {
				throw new Exception(ErrorConstant.INVALID_DURATION);
			}
		}

		return endDate;
	}
}
