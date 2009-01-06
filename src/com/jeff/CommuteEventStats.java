package com.jeff;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class CommuteEventStats extends Activity
{
	private static final String TAG = "CommuteTimer";
	private Uri mUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		final Intent intent = getIntent();
		
		if(Intent.ACTION_VIEW.equals(intent))
		{
			mUri = intent.getData();
		}
		else 
		{
            Log.e(TAG, "Unknown action, exiting");
            finish();
            return;
        }
		
		setContentView(layoutResID);
	}
}
