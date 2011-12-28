package com.billing;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SubmitForInvoice extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.submitforinvoice_layout);
    	
    	Button sendreport = (Button) findViewById(R.id.SendReport);
    	sendreport.setOnClickListener(buttonlistener);
    	
    	DataHelper dh = new DataHelper(getApplicationContext());
        SQLiteDatabase db = dh.getWritableDatabase();
        String[] columns = new String[1];
        columns[0]= "Job_Name";
        Cursor results = db.query("Jobs", columns, null, null, null, null, null);
        
        int jobnameColumnIndex = results.getColumnIndexOrThrow("Job_Name"); 
             
        ArrayAdapter<String> adapterForSpinner = new ArrayAdapter<String>(this, 
        android.R.layout.simple_spinner_item); 
        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        
        Spinner JobNameSpinner = (Spinner) findViewById(R.id.SubmitJobNameSpinner); 
        JobNameSpinner.setAdapter(adapterForSpinner); 
        
                if (results.moveToFirst()) { 
                        do { 
                             adapterForSpinner.add(results.getString(jobnameColumnIndex)); 
                        } 
                        while (results.moveToNext()); 
                        
                        if (db != null) { 
                        db.close(); 
                    } 
               } 
            else { 
                Toast toast = Toast.makeText(getApplicationContext(), "No Jobs found!", 2); 
                toast.show();
            }
                
        JobNameSpinner.setOnItemSelectedListener(new YourItemSelectedListener());
    	    	
	}
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater =getMenuInflater();
    	inflater.inflate(R.menu.submitforinvoicemenu, menu);
    	return true;
    }    
	 
    public boolean onOptionsItemSelected(MenuItem item){
    	// Handle item selection
    	switch (item.getItemId()) {
    	case R.id.back:
    		Intent i = new Intent(this, Main.class);
    		startActivity(i);
    		return true;
        case R.id.quit:
        	this.finish();
        	return true;
                    
        default:
        	return super.onOptionsItemSelected(item);
            
        }    }
    
    private OnClickListener buttonlistener = new OnClickListener() {
    	public void onClick(View v) {
    		switch(v.getId()) {
    		case R.id.SendReport:
    			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
    			   			
    			emailIntent.setType("plain/text");
    			String[] recipients = new String[] {"paulhurst7@gmail.com",""};
    			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
    			emailIntent.putExtra(android.content.Intent.EXTRA_BCC, "paulhurst7@gmail.com");
    			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
    			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Email Body");
    			
    			startActivity(Intent.createChooser(emailIntent, "Send Email"));
    			finish();
    		}
    	}
    };
    
    public class YourItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String selected = parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }
}
	