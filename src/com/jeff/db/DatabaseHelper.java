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
	public static final String DATABASE_NAME = "commute_time.db";
	public static final int VERSION = 1;

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		String createTableQuery = "create table daily_records(day date unique, left_home datetime, train_platform1 datetime, at_work datetime, left_work datetime, train_platform2 datetime, at_home datetime)";
		database.execSQL(createTableQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{

	}
	
	public static String f(Date date)
	{
		SimpleDateFormat fmt = new SimpleDateFormat(DATE_FORMAT);
		return fmt.format(date);
	}

	public static String f_t(Date timestamp)
	{
		SimpleDateFormat fmt = new SimpleDateFormat(TIMESTAMP_FORMAT);
		return fmt.format(timestamp);
	}

	public static Date p_t(String strTimestamp)
	{
		try
		{
			SimpleDateFormat fmt = new SimpleDateFormat(TIMESTAMP_FORMAT);
			Date date = fmt.parse(strTimestamp);
			return date;
		}
		catch (ParseException e)
		{
			throw new RuntimeException("Error parsing date: " + strTimestamp, e);
		}
	}

}
