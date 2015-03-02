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

import com.attemper.emr.assessment.Incision;

public class SkinIncisionDialogFragment extends DialogFragment {
	
	Incision incision;
	
	public SkinIncisionDialogFragment() {
		super();
	}
	
	public SkinIncisionDialogFragment(Incision incision) {
		super();
		this.incision = incision;
	}
	
	/* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface SkinIncisionDialogListener {
        public void onSkinIncisionDialogPositiveClick(DialogFragment dialog, Incision incision);
    }
	
    // Use this instance of the interface to deliver action events
    SkinIncisionDialogListener mListener;
    
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	    View view = inflater.inflate(R.layout.fragment_skin_incisions, null);
	    final EditText site = (EditText) view.findViewById(R.id.txtSite);
	    final CheckBox wellApprox = (CheckBox) view.findViewById(R.id.chkWellApprox);
	    final CheckBox woundOpen = (CheckBox) view.findViewById(R.id.chkWoundOpen);
	    final CheckBox redness = (CheckBox) view.findViewById(R.id.chkRedness);
	    final CheckBox drainage = (CheckBox) view.findViewById(R.id.chkDrainageIncision);
	    final CheckBox swelling = (CheckBox) view.findViewById(R.id.chkSwelling);
	    final CheckBox dressing = (CheckBox) view.findViewById(R.id.chkDressingIntact);
	    final CheckBox steri = (CheckBox) view.findViewById(R.id.chkSteriStripped);
	    final CheckBox staples = (CheckBox) view.findViewById(R.id.chkStaples);
	    
	    if(incision != null) {
	    	site.setText(incision.getSite());
	    	wellApprox.setChecked(incision.isWellApproximated());
	    	woundOpen.setChecked(incision.isWoundOpen());
	    	redness.setChecked(incision.isRedness());
	    	drainage.setChecked(incision.isDrainage());
	    	swelling.setChecked(incision.isSwelling());
	    	dressing.setChecked(incision.isDressingIntact());
	    	steri.setChecked(incision.isSteriStripped());
	    	staples.setChecked(incision.isStaplesSutures());
	    }
	    
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view)
	    // Add action buttons
	           .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
		        	   final Incision incision = new Incision(
		        			site.getText().toString(),
	        		    	wellApprox.isChecked(),
	        		    	woundOpen.isChecked(),
	        		    	redness.isChecked(),
	        		    	drainage.isChecked(),
	        		    	swelling.isChecked(),
	        		    	dressing.isChecked(),
	        		    	steri.isChecked(),
	        		    	staples.isChecked()
	        		    );
	            	   
	            	   // Send the positive button event back to the host activity
                       mListener.onSkinIncisionDialogPositiveClick(SkinIncisionDialogFragment.this, incision);
	               }
	           })
	           .setNegativeButton(R.string.cancel_title, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   SkinIncisionDialogFragment.this.getDialog().cancel();
	               }
	           });      
	    return builder.create();
	}
	
	// Override the Fragment.onAttach() method to instantiate the SkinIncisionDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the SkinIncisionDialogListener so we can send events to the host
            mListener = (SkinIncisionDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement SkinIncisionDialogListener");
        }
    }
}
