/*
 * Copyright 2013-2018 featherrun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package running.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
	static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	static final int ONE_MONDAY_SECOND;
	static {
		Calendar c = Calendar.getInstance();
		c.set(2000, Calendar.JANUARY, 3, 0, 0, 0);
		ONE_MONDAY_SECOND = (int) (c.getTimeInMillis() / 1000);
	}


	/**
	 * Parsing time
	 *
	 * @param str
	 * @return
	 */
	public Date stringToDate(String str) {
		return stringToDate(str, DEFAULT_FORMAT);
	}

	public Date stringToDate(String str, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Formatting time
	 *
	 * @param date
	 * @return
	 */
	public String dateToString(Date date) {
		return dateToString(date, DEFAULT_FORMAT);
	}

	public String dateToString(Date date, String pattern) {
		if (date == null) {
			return null;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
	}



	/**
	 * Get the current timestamp (unit: Second)
	 */
	public int getSecond() {
		return (int) (System.currentTimeMillis() * 0.001);
	}

	/**
	 * Get the timestamp of 0 o'clock, 0 minutes and 0 seconds (unit: Second)
	 * @param c
	 * @return
	 */
	public int getZero(Calendar c) {
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return (int) (c.getTimeInMillis() * 0.001);
	}

	public int getZero() {
		return getZero(Calendar.getInstance());
	}


	/**
	 * Calculation interval days
	 *
	 * @param second1 timestamp
	 * @param second2 timestamp
	 * @return
	 */
	public int getIntervalDays(int second1, int second2) {
		Calendar c = Calendar.getInstance();
		if (second1 > second2) {
			c.setTimeInMillis(second2 * 1000L);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			second2 = (int) (c.getTimeInMillis() / 1000);
			return (second1 - second2) / 86400;
		} else {
			c.setTimeInMillis(second1 * 1000L);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			second1 = (int) (c.getTimeInMillis() / 1000);
			return (second2 - second1) / 86400;
		}
	}

	/**
	 * Whether two times are in the same week
	 *
	 * @param second1 timestamp
	 * @param second2 timestamp
	 * @return
	 */
	public boolean isSameWeek(int second1, int second2) {
		final int weekSecond = 7 * 24 * 60 * 60;
		if ((second1 - second2) >= weekSecond) {
			return false;
		}
		int week1 = (second1 - ONE_MONDAY_SECOND) / weekSecond;
		int week2 = (second2 - ONE_MONDAY_SECOND) / weekSecond;
		return week1 == week2;
	}


}
