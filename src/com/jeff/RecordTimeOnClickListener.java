package com.jeff;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jeff.db.CommuteTimeDao;
import com.jeff.db.CommuteTimeDaoImpl;
import com.jeff.db.DatabaseHelper;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class RecordTimeOnClickListener implements OnClickListener
{
	private TextView textView;
	private SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private CommuteEvent commuteEvent;
	private DatabaseHelper databaseHelper;
	
	private static final String TAG = "CommuteTimer";
	
	public RecordTimeOnClickListener(TextView textView, CommuteEvent commuteEvent, DatabaseHelper databaseHelper)
	{
		super();
		this.textView = textView;
		this.commuteEvent = commuteEvent;
		this.databaseHelper = databaseHelper;
	}

	public void onClick(View view)
	{
		Date now = new Date();
		String date = fmt.format(now);
		Log.i(TAG, now.toString());
		Log.i(TAG, date);
		this.textView.setText(date);
		CommuteTimeDao commuteTimeDao = new CommuteTimeDaoImpl(databaseHelper);
		commuteTimeDao.updateTime(commuteEvent, now, now);
	}
}
