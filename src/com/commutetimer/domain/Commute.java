package com.commutetimer.domain;

import java.util.List;

public class Commute
{
	private Long commuteId;
	private String name;
	private List<CommutePoint> points;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<CommutePoint> getPoints()
	{
		return points;
	}

	public void setPoints(List<CommutePoint> points)
	{
		this.points = points;
	}
}
