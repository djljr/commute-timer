package com.jeff.db;

import java.util.Date;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.jeff.CommuteEvent;

public class CommuteTimeDaoImpl implements CommuteTimeDao
{
	private DatabaseHelper databaseHelper;

	public CommuteTimeDaoImpl(DatabaseHelper databaseHelper)
	{
		this.databaseHelper = databaseHelper;
	}

	public boolean recordExists(Date dailyRecord)
	{
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		final String query = "select count(*) from daily_records where day = ?";
		return DatabaseUtils.longForQuery(db, query, new String[] { DatabaseHelper.f(dailyRecord) }) > 0 ? true : false;
	}

	public void updateTime(CommuteEvent commuteEvent, Date day, Date timestamp)
	{
		if (recordExists(day))
			updateRecord(commuteEvent, day, timestamp);
		else
			insertRecord(commuteEvent, day, timestamp);
	}

	private void insertRecord(CommuteEvent commuteEvent, Date day, Date timestamp)
	{
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String insertQuery = "insert into daily_records(day, %s) values (?, ?)";
		insertQuery = String.format(insertQuery, commuteEvent.dbField());
		db.execSQL(insertQuery, new Object[] { DatabaseHelper.f(day), DatabaseHelper.f_t(timestamp) });
	}

	private void updateRecord(CommuteEvent commuteEvent, Date day, Date timestamp)
	{
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String updateQuery;
		Object[] params;
		if (timestamp == null)
		{
			updateQuery = "update daily_records set %s = NULL where day = ?";
			params = new Object[] { DatabaseHelper.f(day) };
		}
		else
		{
			updateQuery = "update daily_records set %s = ? where day = ?";
			params = new Object[] { DatabaseHelper.f_t(timestamp), DatabaseHelper.f(day) };
		}
		
		updateQuery = String.format(updateQuery, commuteEvent.dbField());
		db.execSQL(updateQuery, params);
	}

	public Date getTimeStampByCommuteEventAndDay(CommuteEvent commuteEvent, Date day)
	{
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		Cursor cursor = db.query("daily_records", new String[] { commuteEvent.dbField() }, null, new String[] { }, null, null, null);
		if (!cursor.moveToFirst())
			return null;
		
		int columnIndex = cursor.getColumnIndexOrThrow(commuteEvent.dbField());
		String strTimestamp = cursor.getString(columnIndex);
		cursor.close();
		if (strTimestamp == null)
			return null;
		return DatabaseHelper.p_t(strTimestamp);
	}
}
