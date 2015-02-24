package com.attemper.emr.assessment;

import android.os.Parcel;
import android.os.Parcelable;

public class Breakdown implements Parcelable {
	private String site;
	private boolean drainage;
	private boolean redness;
	private boolean dressing;
	
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public boolean isDrainage() {
		return drainage;
	}
	public void setDrainage(boolean drainage) {
		this.drainage = drainage;
	}
	public boolean isRedness() {
		return redness;
	}
	public void setRedness(boolean redness) {
		this.redness = redness;
	}
	public boolean isDressing() {
		return dressing;
	}
	public void setDressing(boolean dressing) {
		this.dressing = dressing;
	}
	
	// this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Breakdown> CREATOR = new Parcelable.Creator<Breakdown>() {
        public Breakdown createFromParcel(Parcel in) {
            return new Breakdown(in);
        }

        public Breakdown[] newArray(int size) {
            return new Breakdown[size];
        }
    };
    
    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Breakdown(Parcel in) {
    	setSite(in.readString());
    	setDrainage(in.readInt() == 1);
    	setRedness(in.readInt() == 1);
    	setDressing(in.readInt() == 1);
    }
    
    @Override
	public void writeToParcel(Parcel dest, int flags) {
    	dest.writeString(site);
    	dest.writeInt(drainage ? 1 : 0);
    	dest.writeInt(redness ? 1 : 0);
    	dest.writeInt(dressing ? 1 : 0);
    }

	@Override
	public int describeContents() {
		return 0;
	}
}
