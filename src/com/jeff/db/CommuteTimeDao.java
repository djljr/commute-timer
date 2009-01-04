package com.jeff.db;

import java.util.Date;

import com.jeff.CommuteEvent;

public interface CommuteTimeDao
{
	boolean recordExists(Date dailyRecord);
	
	void updateTime(CommuteEvent commuteEvent, Date day, Date timestamp);

	Date getTimeStampByCommuteEventAndDay(CommuteEvent commuteEvent, Date day);

}
