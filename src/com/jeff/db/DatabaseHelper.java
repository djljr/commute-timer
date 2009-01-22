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
		String createTableQuery = "create table daily_records(_id integer primary key autoincrement, day text, left_home int, train_platform1 int, at_work int, left_work int, train_platform2 int, at_home int)";
		database.execSQL(createTableQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		if(oldVersion < 2)
		{
			database.beginTransaction();
			try
			{
				database.execSQL("create temporary table t1_backup(day, left_home, train_platform1, at_work, left_work, train_platform2, at_home)");
				database.execSQL("insert into t1_backup select day, left_home, train_platform1, at_work, left_work, train_platform2, at_home from daily_records");
				database.execSQL("drop table daily_records");
				database.execSQL("create table daily_records(_id integer primary key autoincrement, day text, left_home int, train_platform1 int, at_work int, left_work int, train_platform2 int, at_home int)");
				database.execSQL("insert into daily_records (day, left_home, train_platform1, at_work, left_work, train_platform2, at_home) select day, left_home, train_platform1, at_work, left_work, train_platform2, at_home from t1_backup");  
				database.execSQL("drop table t1_backup");
				database.setTransactionSuccessful();
			}
			finally
			{
				database.endTransaction();
			}
		}
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
