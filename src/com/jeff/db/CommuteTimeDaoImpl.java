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
	
	public void deleteRecord(Long id)
	{
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.delete("daily_records", "_id=?", new String[] {id.toString()});
	}

	public Date getTimeStampByCommuteEventAndId(CommuteEvent commuteEvent, Long id)
	{
		Cursor cursor = null;
		try
		{
			SQLiteDatabase db = databaseHelper.getReadableDatabase();
			cursor = db.query("daily_records", new String[] { commuteEvent.dbField() }, "_id = ?", new String[] { id.toString() }, null, null, null);
			if (!cursor.moveToFirst())
				return null;
			
			int columnIndex = cursor.getColumnIndexOrThrow(commuteEvent.dbField());
			if (cursor.isNull(columnIndex))
				return null;
			long strTimestamp = cursor.getLong(columnIndex);
			cursor.close();
			return DatabaseHelper.p_t(strTimestamp);
		}
		finally
		{
			if (cursor != null)
				cursor.close();
		}
	}
	
	public Date getTimeStampByCommuteEventAndDay(CommuteEvent commuteEvent, Date day)
	{
		Cursor cursor = null;
		try
		{
			SQLiteDatabase db = databaseHelper.getReadableDatabase();
			cursor = db.query("daily_records", new String[] { commuteEvent.dbField() }, "day = ?", new String[] { DatabaseHelper.f(day) }, null, null, null);
			if (!cursor.moveToFirst())
				return null;
			
			int columnIndex = cursor.getColumnIndexOrThrow(commuteEvent.dbField());
			if (cursor.isNull(columnIndex))
				return null;
			long strTimestamp = cursor.getLong(columnIndex);
			return DatabaseHelper.p_t(strTimestamp);
		}
		finally
		{
			if(cursor != null)
				cursor.close();
		}
	}

	@Override
	public String[] fetchStats() 
	{
		Cursor c = null;
		try
		{
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		String sql = "select avg(at_work-left_home) as avg_to_work_commute, avg(at_home-left_work) as avg_to_home_commute from daily_records;";
		c = db.rawQuery(sql, new String[] { });
		if(!c.moveToFirst())
			return new String[] { };
		
		long toWorkDuration = c.getLong(c.getColumnIndex("avg_to_work_commute"));
		long toHomeDuration = c.getLong(c.getColumnIndex("avg_to_home_commute"));
		
		return new String[] {
				"Home to Work: "
						+ DatabaseHelper.formatDuration(toWorkDuration),
				"Work to Home: "
						+ DatabaseHelper.formatDuration(toHomeDuration) };
		}
		finally
		{
			if(c != null)
				c.close();
		}
	}

	@Override
	public void clearDb() 
	{
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.delete("daily_records", null, new String[] { });
	}

	@Override
	public Cursor fetchDaysWithData()
	{
		Cursor c = null;
		
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		String sql = "select _id, day from daily_records order by day";
		c = db.rawQuery(sql, null);
		
		return c;
		
	}
}
