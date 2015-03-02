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
import com.attemper.emr.assessment.Breakdown;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

public class BreakdownListAdapter extends ArraySwipeAdapter<Breakdown> {
	
	private final Context context;
	private final List<Breakdown> values;
	
	public BreakdownListAdapter(Context context, List<Breakdown> values) {
	    super(context, R.layout.list_item_breakdown, values);
	    
	    this.context = context;
	    this.values = values;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        BreakdownHolder holder = null;
        
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_item_breakdown, parent, false);
            
            holder = new BreakdownHolder();
            holder.txtSite = (TextView)row.findViewById(R.id.txtBreakdownSite);
            holder.chkDrainage = (CheckBox)row.findViewById(R.id.chkDrainage);
            holder.chkDressing = (CheckBox)row.findViewById(R.id.chkDressing);
            holder.chkRedness = (CheckBox)row.findViewById(R.id.chkRednessBreakdown);
            holder.txtStaging = (TextView)row.findViewById(R.id.txtBreakdownStage);
            row.setTag(holder);
        } else {
            holder = (BreakdownHolder)row.getTag();
        }
        
        final Breakdown breakdown = values.get(position);
        holder.txtSite.setText(breakdown.getSite());
        holder.chkDrainage.setChecked(breakdown.isDrainage());
        holder.chkDressing.setChecked(breakdown.isDressing());
        holder.chkRedness.setChecked(breakdown.isRedness());
        holder.txtStaging.setText(breakdown.getStage());
        
        Button deleteButton = (Button)  row.findViewById(R.id.delete);
		deleteButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				values.remove(breakdown);
	        	notifyDataSetChanged();
			}
        });
        
        return row;
    }
    
    static class BreakdownHolder
    {
    	TextView txtSite;
    	CheckBox chkDrainage;
        CheckBox chkDressing;
        CheckBox chkRedness;
        TextView txtStaging;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}