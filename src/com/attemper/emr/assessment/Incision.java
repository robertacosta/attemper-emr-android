package com.attemper.emr.assessment;

public class Incision {
	private String site;
	private boolean wellApproximated;
	private boolean woundOpen;
	private boolean redness;
	private boolean drainage;
	private boolean swelling;
	private boolean dressingIntact;
	private boolean steriStripped;
	private boolean staplesSutures;
	
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public boolean isWellApproximated() {
		return wellApproximated;
	}
	public void setWellApproximated(boolean wellApproximated) {
		this.wellApproximated = wellApproximated;
	}
	public boolean isWoundOpen() {
		return woundOpen;
	}
	public void setWoundOpen(boolean woundOpen) {
		this.woundOpen = woundOpen;
	}
	public boolean isRedness() {
		return redness;
	}
	public void setRedness(boolean redness) {
		this.redness = redness;
	}
	public boolean isDrainage() {
		return drainage;
	}
	public void setDrainage(boolean drainage) {
		this.drainage = drainage;
	}
	public boolean isSwelling() {
		return swelling;
	}
	public void setSwelling(boolean swelling) {
		this.swelling = swelling;
	}
	public boolean isDressingIntact() {
		return dressingIntact;
	}
	public void setDressingIntact(boolean dressingIntact) {
		this.dressingIntact = dressingIntact;
	}
	public boolean isSteriStripped() {
		return steriStripped;
	}
	public void setSteriStripped(boolean steriStripped) {
		this.steriStripped = steriStripped;
	}
	public boolean isStaplesSutures() {
		return staplesSutures;
	}
	public void setStaplesSutures(boolean staplesSutures) {
		this.staplesSutures = staplesSutures;
	}
}
