package com.jeff;

import java.util.Date;

import com.jeff.db.CommuteTimeDao;
import com.jeff.db.CommuteTimeDaoImpl;
import com.jeff.db.DatabaseHelper;

import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

public class GenericOnLongClickListener implements OnLongClickListener
{
	private TextView textView;
	private CommuteEvent commuteEvent;
	private Date timestamp;
	private DatabaseHelper databaseHelper;

	public GenericOnLongClickListener(TextView textView, CommuteEvent commuteEvent, Date timestamp, DatabaseHelper databaseHelper)
	{
		this.textView = textView;
		this.commuteEvent = commuteEvent;
		this.timestamp = timestamp;
		this.databaseHelper = databaseHelper;
	}

	public boolean onLongClick(View view)
	{
		CommuteTimeDao commuteTimeDao = new CommuteTimeDaoImpl(databaseHelper);
		commuteTimeDao.updateTime(commuteEvent, timestamp, null);
		this.textView.setText("");
		return true;
	}
}
