package com.jeff;

import java.util.Date;

import org.apache.http.impl.cookie.DateUtils;

import com.jeff.db.CommuteTimeDao;
import com.jeff.db.CommuteTimeDaoImpl;
import com.jeff.db.DatabaseHelper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CommuteTimer extends Activity
{
	private DatabaseHelper databaseHelper = new DatabaseHelper(this);
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		CommuteTimeDao commuteTimeDao = new CommuteTimeDaoImpl(databaseHelper);
		Date today = new Date();
		
		for (CommuteEvent commuteEvent : CommuteEvent.values())
		{
			Button button = (Button) findViewById(commuteEvent.buttonViewId());
			button.setOnClickListener(new RecordTimeOnClickListener((TextView) findViewById(commuteEvent.textViewId()), commuteEvent, databaseHelper));
			
			TextView tv = (TextView) findViewById(commuteEvent.textViewId());
			Date timestamp = commuteTimeDao.getTimeStampByCommuteEventAndDay(commuteEvent, today);
			if (timestamp == null)
				tv.setText("");
			else
				tv.setText(DateUtils.formatDate(timestamp, "MM/dd/yyyy HH:mm:ss"));
		}
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		closeDatabase();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		closeDatabase();
	}

	private void closeDatabase()
	{
		databaseHelper.close();
	}
}