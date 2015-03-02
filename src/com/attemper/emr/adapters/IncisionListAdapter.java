package com.attemper.emr.adapters;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.attemper.emr.R;
import com.attemper.emr.assessment.Incision;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

public class IncisionListAdapter extends ArraySwipeAdapter<Incision> {
	
	private final Context context;
	private final List<Incision> values;
	
	public IncisionListAdapter(Context context, List<Incision> values) {
	    super(context, R.layout.list_item_incision, values);
	    
	    this.context = context;
	    this.values = values;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        IncisionHolder holder = null;
        
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_item_incision, parent, false);
            
            holder = new IncisionHolder();
            holder.txtSite = (TextView)row.findViewById(R.id.txtIncisionSite);
            holder.chkWellApprox = (CheckBox)row.findViewById(R.id.chkWellApprox);
            holder.chkWoundOpen = (CheckBox)row.findViewById(R.id.chkWoundOpen);
            holder.chkRedness = (CheckBox)row.findViewById(R.id.chkRedness);
            holder.chkDrainage = (CheckBox)row.findViewById(R.id.chkDrainageIncision);
            holder.chkSwelling = (CheckBox)row.findViewById(R.id.chkSwelling);
            holder.chkDressingIntact = (CheckBox)row.findViewById(R.id.chkDressingIntact);
            holder.chkSteriStripped = (CheckBox)row.findViewById(R.id.chkSteriStripped);
            holder.chkStaples = (CheckBox)row.findViewById(R.id.chkStaples);
            row.setTag(holder);
        } else {
            holder = (IncisionHolder)row.getTag();
        }
        
        final Incision incision = values.get(position);
        holder.txtSite.setText(incision.getSite());
        holder.chkWellApprox.setChecked(incision.isWellApproximated());
        holder.chkWoundOpen.setChecked(incision.isWoundOpen());
        holder.chkRedness.setChecked(incision.isRedness());
        holder.chkDrainage.setChecked(incision.isDrainage());
        holder.chkSwelling.setChecked(incision.isSwelling());
        holder.chkDressingIntact.setChecked(incision.isDressingIntact());
        holder.chkSteriStripped.setChecked(incision.isSteriStripped());
        holder.chkStaples.setChecked(incision.isStaplesSutures());
        
        Button deleteButton = (Button)  row.findViewById(R.id.delete);
		deleteButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				values.remove(incision);
	        	notifyDataSetChanged();
			}
        });
        
        return row;
    }
    
    static class IncisionHolder
    {
    	TextView txtSite;
    	CheckBox chkWellApprox;
        CheckBox chkWoundOpen;
        CheckBox chkRedness;
        CheckBox chkDrainage;
        CheckBox chkSwelling;
        CheckBox chkDressingIntact;
        CheckBox chkSteriStripped;
        CheckBox chkStaples;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}