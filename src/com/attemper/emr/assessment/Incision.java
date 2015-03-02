package com.attemper.emr.assessment;

import android.os.Parcel;
import android.os.Parcelable;

public class Incision implements Parcelable {
	private String site;
	private boolean wellApproximated;
	private boolean woundOpen;
	private boolean redness;
	private boolean drainage;
	private boolean swelling;
	private boolean dressingIntact;
	private boolean steriStripped;
	private boolean staplesSutures;
	
	public Incision() {
	}
	
	public Incision(String site, boolean wellApproximated, boolean woundOpen,
			boolean redness, boolean drainage, boolean swelling,
			boolean dressingIntact, boolean steriStripped,
			boolean staplesSutures) {
		this.site = site;
		this.wellApproximated = wellApproximated;
		this.woundOpen = woundOpen;
		this.redness = redness;
		this.drainage = drainage;
		this.swelling = swelling;
		this.dressingIntact = dressingIntact;
		this.steriStripped = steriStripped;
		this.staplesSutures = staplesSutures;
	}

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
	
	// this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Incision> CREATOR = new Parcelable.Creator<Incision>() {
        public Incision createFromParcel(Parcel in) {
            return new Incision(in);
        }

        public Incision[] newArray(int size) {
            return new Incision[size];
        }
    };
    
    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Incision(Parcel in) {
    	setSite(in.readString());
    	setWellApproximated(in.readInt() == 1);
    	setWoundOpen(in.readInt() == 1);
    	setRedness(in.readInt() == 1);
    	setDrainage(in.readInt() == 1);
    	setSwelling(in.readInt() == 1);
    	setDressingIntact(in.readInt() == 1);
    	setSteriStripped(in.readInt() == 1);
    	setStaplesSutures(in.readInt() == 1);
    }
    
    @Override
	public void writeToParcel(Parcel dest, int flags) {
    	dest.writeString(site);
    	dest.writeInt(wellApproximated ? 1 : 0);
    	dest.writeInt(woundOpen ? 1 : 0);
    	dest.writeInt(redness ? 1 : 0);
    	dest.writeInt(drainage ? 1 : 0);
    	dest.writeInt(swelling ? 1 : 0);
    	dest.writeInt(dressingIntact ? 1 : 0);
    	dest.writeInt(steriStripped ? 1 : 0);
    	dest.writeInt(staplesSutures ? 1 : 0);
    }

	@Override
	public int describeContents() {
		return 0;
	}
}
