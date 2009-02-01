package com.commutetimer;

import java.util.Date;

import com.commutetimer.db.CommuteTimeDao;
import com.commutetimer.db.CommuteTimeDaoImpl;
import com.commutetimer.db.DatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CommuteHistoryDetail extends Activity
{
	private CommuteTimeDao commuteTimeDao;
	
	private Long id;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		commuteTimeDao = new CommuteTimeDaoImpl(new DatabaseHelper(this));
		setContentView(R.layout.main);
		
		final Intent intent = getIntent();
		
		id = intent.getExtras().getLong("id");
		initialize();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		initialize();
	}

	private void initialize()
	{	
		for (CommuteEvent commuteEvent : CommuteEvent.values())
		{
			Date timestamp = commuteTimeDao.getTimeStampByCommuteEventAndId(commuteEvent, id);
			
			Button button = (Button) findViewById(commuteEvent.buttonViewId());
			button.setOnLongClickListener(new GenericOnLongClickListener((TextView) findViewById(commuteEvent
					.textViewId()), commuteEvent, timestamp, commuteTimeDao));
			
			TextView tv = (TextView) findViewById(commuteEvent.textViewId());
			if (timestamp == null)
				tv.setText("");
			else
				tv.setText(DatabaseHelper.d_t(timestamp));
		}
	}
}
