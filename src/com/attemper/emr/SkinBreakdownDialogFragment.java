package com.attemper.emr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.attemper.emr.assessment.Breakdown;

public class SkinBreakdownDialogFragment extends DialogFragment {
	
	Breakdown breakdown;
	final int position;
	
	public SkinBreakdownDialogFragment() {
		super();
		this.position = -1;
	}
	
	public SkinBreakdownDialogFragment(Breakdown breakdown, int position) {
		super();
		this.breakdown = breakdown;
		this.position = position;
	} 
	
	/* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface SkinBreakdownDialogListener {
        public void onSkinBreakdownDialogPositiveClick(DialogFragment dialog, Breakdown breakdown, int position);
    }
	
    // Use this instance of the interface to deliver action events
    SkinBreakdownDialogListener mListener;
    
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    
	    View view = inflater.inflate(R.layout.fragment_skin_breakdown, null);
	    final EditText site = (EditText) view.findViewById(R.id.txtSiteBreakdown);
	    final CheckBox drainage = (CheckBox) view.findViewById(R.id.chkDrainage);
	    final CheckBox redness = (CheckBox) view.findViewById(R.id.chkRednessBreakdown);
	    final CheckBox dressing = (CheckBox) view.findViewById(R.id.chkDressing);
	    final EditText stage = (EditText) view.findViewById(R.id.txtStage);
	    
	    if(breakdown != null) {
	    	site.setText(breakdown.getSite());
	    	drainage.setChecked(breakdown.isDrainage());
	    	redness.setChecked(breakdown.isRedness());
	    	dressing.setChecked(breakdown.isDressing());
	    	stage.setText(breakdown.getStage());
	    }

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view)
	    // Add action buttons
	           .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   final Breakdown breakdown = new Breakdown(
            			   site.getText().toString(),
            			   drainage.isChecked(),
            			   redness.isChecked(),
            			   dressing.isChecked(),
            			   stage.getText().toString()
        			   );
	            	   
	            	   // Send the positive button event back to the host activity
                       mListener.onSkinBreakdownDialogPositiveClick(SkinBreakdownDialogFragment.this, breakdown, position);
	               }
	           })
	           .setNegativeButton(R.string.cancel_title, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   SkinBreakdownDialogFragment.this.getDialog().cancel();
	               }
	           });      
	    return builder.create();
	}
	
	// Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (SkinBreakdownDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement SkinBreakdownDialogListener");
        }
    }
}