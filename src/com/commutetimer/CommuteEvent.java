package com.commutetimer;

public enum CommuteEvent
{
	LEFT_HOME
	{
		public String dbField() { return "left_home"; }
		public int buttonViewId() { return R.id.Button01; };
		public int textViewId() { return R.id.TextView01; };
	},
	TRAIN_PLATFORM1
	{
		public String dbField() { return "train_platform1"; }
		public int buttonViewId() { return R.id.Button02; };
		public int textViewId() { return R.id.TextView02; };
	},
	AT_WORK
	{
		public String dbField() { return "at_work"; }
		public int buttonViewId() { return R.id.Button03; };
		public int textViewId() { return R.id.TextView03; };
	},
	LEFT_WORK
	{
		public String dbField() { return "left_work"; }
		public int buttonViewId() { return R.id.Button04; };
		public int textViewId() { return R.id.TextView04; };
	},
	TRAIN_PLATFORM2
	{
		public String dbField() { return "train_platform2"; }
		public int buttonViewId() { return R.id.Button05; };
		public int textViewId() { return R.id.TextView05; };
	},
	AT_HOME
	{
		public String dbField() { return "at_home"; }
		public int buttonViewId() { return R.id.Button06; };
		public int textViewId() { return R.id.TextView06; };
	};

	abstract public String dbField();
	abstract public int buttonViewId();
	abstract public int textViewId();
}
