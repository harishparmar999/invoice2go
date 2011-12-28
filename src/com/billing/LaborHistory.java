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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LaborHistory extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.laborhistory_layout);
    	
    	DataHelper dh = new DataHelper(getApplicationContext());
        SQLiteDatabase db = dh.getWritableDatabase();
        String[] columns = new String[1];
        columns[0]= "Job_Name";
        Cursor results = db.query("Jobs", columns, null, null, null, null, null);
        
        int jobnameColumnIndex = results.getColumnIndexOrThrow("Job_Name"); 
             
        ArrayAdapter<String> adapterForSpinner = new ArrayAdapter<String>(this, 
        android.R.layout.simple_spinner_item); 
        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        
        Spinner JobNameSpinner = (Spinner) findViewById(R.id.LaborHistoryJobNameSpinner); 
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
    	inflater.inflate(R.menu.laborhistorymenu, menu);
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
            
        }
    }
    
//OnItemSelectedListener onJobNameSelect = new Spinner.OnItemSelectedListener() {
//    	
//		@Override
//		public void onItemSelected(AdapterView<?> parent, View v, int pos, long row) {
//			// TODO Auto-generated method stub
//			
//            TextView tv = (TextView) v;
//            String item = tv.getText().toString();
//			
//			DataHelper dh = new DataHelper(getApplicationContext());
//	        SQLiteDatabase db = dh.getWritableDatabase();
//			String[] columns = new String[1];
//			columns[0] = "Job_Date";
//			
//			String select = "SELECT Job_Date FROM Labor WHERE Job_Name = '" + item + "';";
//			
//			Cursor results = db.rawQuery(select, null);
//			
//	        ArrayAdapter<String> adapterForSpinner = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item); 
//	        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//	        Spinner spinner = (Spinner) findViewById(R.id.LaboryHistoryDateSpinner); 	                
//            spinner.setAdapter(adapterForSpinner); 
//			
//			int jobdateColumnIndex = results.getColumnIndexOrThrow("Job_Date"); 
//			
//			if (results.moveToFirst()) {
//				do {
//					adapterForSpinner.add(results.getString(jobdateColumnIndex));
//				} while (results.moveToNext());
//			} else {
//				Toast toast = Toast.makeText(getApplicationContext(), "No Labor Entries Found!", 2);
//				toast.show();
//			}
//			
//			if (db != null) { 
//                db.close();
//                }
//			
//		}
//
//		@Override
//		public void onNothingSelected(AdapterView<?> parent) {
//			// TODO Auto-generated method stub
//			
//		}
//
//    };
    
    public class YourItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        	TextView tv = (TextView) view;
            String item = tv.getText().toString();
			
			DataHelper dh = new DataHelper(getApplicationContext());
	        SQLiteDatabase db = dh.getWritableDatabase();
			String[] columns = new String[1];
			columns[0] = "Job_Date";
			
			String select = "SELECT Job_Date FROM Labor WHERE Job_Name = '" + item + "';";
			
			Cursor results = db.rawQuery(select, null);
			
	        ArrayAdapter<String> adapterForSpinner = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item); 
	        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        Spinner spinner = (Spinner) findViewById(R.id.LaboryHistoryDateSpinner); 	                
            spinner.setAdapter(adapterForSpinner); 
			
			int jobdateColumnIndex = results.getColumnIndexOrThrow("Job_Date"); 
			
			if (results.moveToFirst()) {
				do {
					adapterForSpinner.add(results.getString(jobdateColumnIndex));
				} while (results.moveToNext());
			} else {
				Toast toast = Toast.makeText(getApplicationContext(), "No Labor Entries Found!", 2);
				toast.show();
			}
			
			if (db != null) { 
                db.close();
                }
			
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }
    }
}
