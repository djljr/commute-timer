package com.commutetimer.domain;

import java.util.Set;

public class CommutePoint
{
	private Long id;
	private String name;
	private Set<CommutePoint> forwardPoints;
	private Set<CommutePoint> reversePoints;

	public Set<CommutePoint> getForwardPoints()
	{
		return forwardPoints;
	}

	public void setForwardPoints(Set<CommutePoint> forwardPoints)
	{
		this.forwardPoints = forwardPoints;
	}

	public Set<CommutePoint> getReversePoints()
	{
		return reversePoints;
	}

	public void setReversePoints(Set<CommutePoint> reversePoints)
	{
		this.reversePoints = reversePoints;
	}
}
