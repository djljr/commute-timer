package com.jeff;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jeff.db.CommuteTimeDao;
import com.jeff.db.CommuteTimeDaoImpl;
import com.jeff.db.DatabaseHelper;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class RecordTimeOnClickListener implements OnClickListener
{
	private TextView textView;
	private SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private CommuteEvent commuteEvent;
	private DatabaseHelper databaseHelper;
	
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
		this.textView.setText(fmt.format(now));
		CommuteTimeDao commuteTimeDao = new CommuteTimeDaoImpl(databaseHelper);
		commuteTimeDao.updateTime(commuteEvent, now, now);
	}
}
