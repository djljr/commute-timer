package com.jeff;

import java.util.Date;

import com.jeff.db.CommuteTimeDao;
import com.jeff.db.CommuteTimeDaoImpl;
import com.jeff.db.DatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CommuteHistoryDetail extends Activity
{
	private DatabaseHelper databaseHelper;
	private CommuteTimeDao commuteTimeDao;
	
	private Date curDate;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		databaseHelper =  new DatabaseHelper(this);
		commuteTimeDao = new CommuteTimeDaoImpl(databaseHelper);
		setContentView(R.layout.main);
		
		final Intent intent = getIntent();
		
		curDate = new Date(intent.getExtras().getLong("timestamp"));
		initialize();
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
		initialize();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		closeDatabase();
	}
	
	private void initialize()
	{	
		Date today = curDate;
		for (CommuteEvent commuteEvent : CommuteEvent.values())
		{
			TextView tv = (TextView) findViewById(commuteEvent.textViewId());
			Date timestamp = commuteTimeDao.getTimeStampByCommuteEventAndDay(commuteEvent, today);
			if (timestamp == null)
				tv.setText("");
			else
				tv.setText(DatabaseHelper.d_t(timestamp));
		}
	}

	private void closeDatabase()
	{
		databaseHelper.close();
	}
}
