package com.attemper.emr.assessment;

import java.util.List;

public class Skin {
	private List<Incision> incisions;
	private List<Breakdown> breakdowns;
	
	public List<Incision> getIncisions() {
		return incisions;
	}
	public void setIncisions(List<Incision> incisions) {
		this.incisions = incisions;
	}
	public List<Breakdown> getBreakdowns() {
		return breakdowns;
	}
	public void setBreakdowns(List<Breakdown> breakdowns) {
		this.breakdowns = breakdowns;
	}
}
