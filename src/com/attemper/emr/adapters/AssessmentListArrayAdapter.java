package com.attemper.emr.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.attemper.emr.R;
import com.attemper.emr.assessment.Assessment;
import com.attemper.emr.assessment.hateoas.AssessmentResource;

public class AssessmentListArrayAdapter extends ArrayAdapter<AssessmentResource> {
	private final Context context;
	private final AssessmentResource[] values;

	public AssessmentListArrayAdapter(Context context, AssessmentResource[] values) {
	    super(context, R.layout.list_item_assessment, values);
	    this.context = context;
	    this.values = values;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AssessmentHolder holder = null;
        
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_item_assessment, parent, false);
            
            holder = new AssessmentHolder();
            holder.txtDate = (TextView)row.findViewById(R.id.txtAssessmentDate);
            holder.txtTime = (TextView)row.findViewById(R.id.txtAssessmentTime);
            holder.txtId = (TextView)row.findViewById(R.id.txtListItemId);
            row.setTag(holder);
        } else {
            holder = (AssessmentHolder)row.getTag();
        }
        
        AssessmentResource assessmentResource = values[position];
        Assessment assessment = assessmentResource.getContent();
        holder.txtDate.setText(assessment.getDate());
        holder.txtTime.setText(assessment.getTime());
        holder.txtId.setText(assessmentResource.getId().getHref());
        return row;
    }
    
    static class AssessmentHolder
    {
    	TextView txtDate;
        TextView txtTime;
        TextView txtId;
    }
}
