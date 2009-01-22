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
	private CommuteTimeDao commuteTimeDao;
	
	private static final String TAG = "CommuteTimer";
	
	public RecordTimeOnClickListener(TextView textView, CommuteEvent commuteEvent, CommuteTimeDao commuteTimeDao)
	{
		super();
		this.textView = textView;
		this.commuteEvent = commuteEvent;
		this.commuteTimeDao = commuteTimeDao;
	}

	public void onClick(View view)
	{
		Date now = new Date();
		String date = fmt.format(now);
		Log.i(TAG, now.toString());
		Log.i(TAG, date);
		textView.setText(date);
		commuteTimeDao.updateTime(commuteEvent, now, now);
	}
}
