package com.attemper.emr.assessment;

public class Edema {
	private int level; // 0-3
	private boolean pitting;
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isPitting() {
		return pitting;
	}
	public void setPitting(boolean pitting) {
		this.pitting = pitting;
	}
}
