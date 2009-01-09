package com.jeff.db;

import java.util.Date;

import android.database.Cursor;

import com.jeff.CommuteEvent;

public interface CommuteTimeDao
{
	boolean recordExists(Date dailyRecord);
	
	void updateTime(CommuteEvent commuteEvent, Date day, Date timestamp);
	void deleteRecord(Long id);
	
	Date getTimeStampByCommuteEventAndId(CommuteEvent commuteEvent, Long id);
	Date getTimeStampByCommuteEventAndDay(CommuteEvent commuteEvent, Date day);

	String[] fetchStats();

	void clearDb();

	Cursor fetchDaysWithData();

}
