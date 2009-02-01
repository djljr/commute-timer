package com.commutetimer;

import java.util.Date;

import com.commutetimer.db.CommuteTimeDao;
import com.commutetimer.db.CommuteTimeDaoImpl;
import com.commutetimer.db.DatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class CommuteTimer extends Activity
{
	private static final int STATS_ID = Menu.FIRST;
	private static final int HISTORY_ID = Menu.FIRST + 2;

	private CommuteTimeDao commuteTimeDao;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		menu.add(0, STATS_ID, 0, R.string.menu_stats).setIcon(R.drawable.stats);
		menu.add(0, HISTORY_ID, 0, R.string.menu_history).setIcon(R.drawable.history);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent intent = new Intent();
		switch (item.getItemId())
		{
		case STATS_ID:
			intent.setClassName("com.commutetimer", "com.commutetimer.CommuteEventStats");
			startActivity(intent);
			return true;
		case HISTORY_ID:
			intent.setClassName("com.commutetimer", "com.commutetimer.CommuteHistory");
			startActivity(intent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		commuteTimeDao = new CommuteTimeDaoImpl(new DatabaseHelper(this));
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
		Date today = new Date();
		for (CommuteEvent commuteEvent : CommuteEvent.values())
		{
			Button button = (Button) findViewById(commuteEvent.buttonViewId());
			button.setOnClickListener(new RecordTimeOnClickListener((TextView) findViewById(commuteEvent.textViewId()),
					commuteEvent, commuteTimeDao));
			button.setOnLongClickListener(new GenericOnLongClickListener((TextView) findViewById(commuteEvent
					.textViewId()), commuteEvent, new Date(), commuteTimeDao));

			TextView tv = (TextView) findViewById(commuteEvent.textViewId());
			Date timestamp = commuteTimeDao.getTimeStampByCommuteEventAndDay(commuteEvent, today);
			if (timestamp == null)
				tv.setText("");
			else
				tv.setText(DatabaseHelper.d_t(timestamp));
		}
	}
}