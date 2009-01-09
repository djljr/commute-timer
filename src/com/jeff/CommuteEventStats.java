package com.jeff;

import com.jeff.db.CommuteTimeDao;
import com.jeff.db.CommuteTimeDaoImpl;
import com.jeff.db.DatabaseHelper;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

public class CommuteEventStats extends ListActivity
{
	private static final String TAG = "CommuteTimer";
	
	private DatabaseHelper databaseHelper;
	private CommuteTimeDao commuteTimeDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
			
		setContentView(R.layout.stats);
		
		databaseHelper = new DatabaseHelper(this);
		commuteTimeDao = new CommuteTimeDaoImpl(databaseHelper);
		fillStats();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		closeDatabase();
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		closeDatabase();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		fillStats();
	}
	
	private void fillStats() 
	{
		String[] statItems = commuteTimeDao.fetchStats();
		
		ListAdapter stats = new ArrayAdapter<String>(this,
				R.layout.stats_row, statItems);
		setListAdapter(stats);
	}
	private void closeDatabase()
	{
		databaseHelper.close();
	}
}
