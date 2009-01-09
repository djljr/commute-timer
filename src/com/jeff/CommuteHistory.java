package com.jeff;

import com.jeff.db.CommuteTimeDao;
import com.jeff.db.CommuteTimeDaoImpl;
import com.jeff.db.DatabaseHelper;

import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class CommuteHistory extends ListActivity
{
	private static final String TAG = "CommuteTimer";

	private static final int MENU_ITEM_DELETE = Menu.FIRST;
	
	private DatabaseHelper databaseHelper;
	private CommuteTimeDao commuteTimeDao;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.history);
		getListView().setOnCreateContextMenuListener(this);
		databaseHelper = new DatabaseHelper(this);
		commuteTimeDao = new CommuteTimeDaoImpl(databaseHelper);
		fillHistory();
	}

    @Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo)
	{
		AdapterView.AdapterContextMenuInfo info;
		try
		{
			info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		}
		catch (ClassCastException e)
		{
			Log.e(TAG, "bad menuInfo", e);
			return;
		}

		Cursor cursor = (Cursor) getListAdapter().getItem(info.position);
		if (cursor == null)
			return;

		// Setup the menu header
		menu.setHeaderTitle(cursor.getString(1));

		// Add a menu item to delete the note
		menu.add(0, MENU_ITEM_DELETE, 0, R.string.menu_delete);
	}

    @Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
    	Intent i = new Intent();
    	i.putExtra("foo", "bar");
    	i.setClassName("com.jeff", "com.jeff.CommuteHistoryDetail");
		startActivity(i);
	}
    
    @Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterView.AdapterContextMenuInfo info;
		try
		{
			info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		}
		catch (ClassCastException e)
		{
			Log.e(TAG, "bad menuInfo", e);
			return false;
		}

		switch (item.getItemId())
		{
		case MENU_ITEM_DELETE:
			return true;
		}
		return false;
	}
    
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		closeDatabase();
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
		fillHistory();
	}

	private void fillHistory()
	{
		Cursor historyItems = commuteTimeDao.fetchDaysWithData();

		ListAdapter history = new SimpleCursorAdapter(this, R.layout.history_row, historyItems, new String[] { "day" },
				new int[] { android.R.id.text1 });
		setListAdapter(history);
	}

	private void closeDatabase()
	{
		databaseHelper.close();
	}
}