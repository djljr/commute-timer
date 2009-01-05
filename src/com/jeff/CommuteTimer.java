package com.jeff;

import java.util.Date;

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
	private CommuteTimeDao commuteTimeDao = new CommuteTimeDaoImpl(databaseHelper);
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initialize();
	}

	private void initialize()
	{
		Date today = new Date();
		
		for (CommuteEvent commuteEvent : CommuteEvent.values())
		{
			Button button = (Button) findViewById(commuteEvent.buttonViewId());
			button.setOnClickListener(new RecordTimeOnClickListener((TextView) findViewById(commuteEvent.textViewId()), commuteEvent, databaseHelper));
			button.setOnLongClickListener(new GenericOnLongClickListener((TextView) findViewById(commuteEvent.textViewId()), commuteEvent, databaseHelper));
			
			TextView tv = (TextView) findViewById(commuteEvent.textViewId());
			Date timestamp = commuteTimeDao.getTimeStampByCommuteEventAndDay(commuteEvent, today);
			if (timestamp == null)
				tv.setText("");
			else
				tv.setText(DatabaseHelper.d_t(timestamp));
		}
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

	private void closeDatabase()
	{
		databaseHelper.close();
	}
}