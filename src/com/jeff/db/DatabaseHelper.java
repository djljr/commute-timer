package com.jeff.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DISPLAY_FORMAT = "MM/dd/yyyy HH:mm:ss";
	public static final String DATABASE_NAME = "commute_time.db";
	public static final int VERSION = 2;

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		String createTableQuery = "create table daily_records(day date unique, left_home integer, train_platform1 integer, at_work integer, left_work integer, train_platform2 integer, at_home integer)";
		database.execSQL(createTableQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		throw new RuntimeException("need to finish writing this method");
	}
	
	public static String f(Date date)
	{
		if (date == null)
			return "null";
		SimpleDateFormat fmt = new SimpleDateFormat(DATE_FORMAT);
		return fmt.format(date);
	}

	public static Long f_t(Date timestamp)
	{
		if (timestamp == null)
			return null;
		return timestamp.getTime();
	}

	public static Date p_t(long timestamp)
	{
		return new Date(timestamp);
	}

	public static String d_t(Date timestamp)
	{
		return new SimpleDateFormat(DISPLAY_FORMAT).format(timestamp);
	}
	
	public static String formatDuration(long duration)
	{
		long seconds = duration / 1000;
		long minutes = seconds / 60;
		seconds = seconds % 60;
		
		return minutes + "m " + seconds + "s";
	}
}
