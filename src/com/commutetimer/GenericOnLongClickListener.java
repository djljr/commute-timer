package com.commutetimer;

import java.util.Date;

import com.commutetimer.db.CommuteTimeDao;
import com.commutetimer.db.CommuteTimeDaoImpl;
import com.commutetimer.db.DatabaseHelper;

import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

public class GenericOnLongClickListener implements OnLongClickListener
{
	private TextView textView;
	private CommuteEvent commuteEvent;
	private Date timestamp;
	private CommuteTimeDao commuteTimeDao;

	public GenericOnLongClickListener(TextView textView, CommuteEvent commuteEvent, Date timestamp, CommuteTimeDao commuteTimeDao)
	{
		this.textView = textView;
		this.commuteEvent = commuteEvent;
		this.timestamp = timestamp;
		this.commuteTimeDao = commuteTimeDao;
	}

	public boolean onLongClick(View view)
	{
		commuteTimeDao.updateTime(commuteEvent, timestamp, null);
		textView.setText("");
		return true;
	}
}
