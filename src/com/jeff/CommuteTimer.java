package com.jeff;

import java.util.Date;

import com.jeff.db.CommuteTimeDao;
import com.jeff.db.CommuteTimeDaoImpl;
import com.jeff.db.DatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class CommuteTimer extends Activity
{
	private DatabaseHelper databaseHelper = new DatabaseHelper(this);
	private CommuteTimeDao commuteTimeDao = new CommuteTimeDaoImpl(databaseHelper);
	
	private static final int STATS_ID = Menu.FIRST;
	private static final int CLEAR_ID = Menu.FIRST + 1;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, STATS_ID, 0, R.string.menu_stats).setIcon(R.drawable.stats);
		menu.add(0, CLEAR_ID, 0, R.string.menu_clear).setIcon(R.drawable.clear);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case STATS_ID:
        	Intent intent = new Intent();
        	intent.setClassName("com.jeff", "com.jeff.CommuteEventStats");
			startActivity(intent);
            return true;
        case CLEAR_ID:
        	commuteTimeDao.clearDb();
        	initialize();
        	return true;
        }
        return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
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

	private void closeDatabase()
	{
		databaseHelper.close();
	}
}