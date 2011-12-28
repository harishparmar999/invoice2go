package com.billing;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAJob extends Activity {
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createajob_layout);
                
        //Database access
        DataHelper dh = new DataHelper(getApplicationContext());
        SQLiteDatabase db = dh.getWritableDatabase();
        
        //Populate customer spinner
        Spinner customerspinner = (Spinner) findViewById(R.id.CustomerSpinner);
        ArrayAdapter<String> adaptercustomer = new ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item);
        adaptercustomer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerspinner.setAdapter(adaptercustomer);
        
        String[] columns = new String[1];
        columns[0]= "Customer_Name";
        Cursor results = db.query("Customers", columns, null, null, null, null, null);
        int customernameColumnIndex = results.getColumnIndexOrThrow("Customer_Name");
        
        if (results.moveToFirst()) { 
            do { 
                 adaptercustomer.add(results.getString(customernameColumnIndex)); 
            } 
            while (results.moveToNext()); 
        
        } 
        	else { 
        		Toast.makeText(getApplicationContext(), "No Customers found!", 2).show(); 
        	}       
          


        customerspinner.setOnItemSelectedListener(new YourItemSelectedListener());
        
        Button savenewjob = (Button)findViewById(R.id.SaveJob);
        savenewjob.setOnClickListener(buttonlistener);
        
        Button canceljob = (Button)findViewById(R.id.CancelJob);
        canceljob.setOnClickListener(buttonlistener);
        
        Button addcustomer = (Button)findViewById(R.id.AddCustomer);
        addcustomer.setOnClickListener(buttonlistener);
        
        Button addjobsite = (Button)findViewById(R.id.AddJobSite);
        addjobsite.setOnClickListener(buttonlistener);
        
        if (db != null) { 
            db.close(); 
        } 
            
    }
    
    private OnClickListener buttonlistener = new OnClickListener() {
    	public void onClick(View v) {
    		switch(v.getId()) {
    		case R.id.AddCustomer:
    			startActivity(new Intent(CreateAJob.this, AddCustomer.class));
    			break;
    			
    		case R.id.AddJobSite:
    			startActivity(new Intent(CreateAJob.this, AddJobSite.class));
    			break;
    			
    		case R.id.SaveJob:
    			DataHelper dh = new DataHelper(getApplicationContext());
    			SQLiteDatabase db = dh.getWritableDatabase();
    			TextView JobNameView = (TextView)(findViewById(R.id.NewJob));
    			String JobName = JobNameView.getText().toString();
    			Spinner CustomerView = (Spinner)(findViewById(R.id.CustomerSpinner));
    			String Customer = CustomerView.getSelectedItem().toString();
    			Spinner JobSiteView = (Spinner)(findViewById(R.id.JobSiteSpinner));
    			String JobSite = JobSiteView.getSelectedItem().toString();    			
    			TextView DescripView = (TextView)(findViewById(R.id.NotesBox));
    			String Descrip = DescripView.getText().toString();
    			String query = "INSERT INTO Jobs (Job_Name, Customer, Job_Site, Description)" +
    					"VALUES (\""+JobName+"\",\""+Customer+"\",\""+JobSite+"\",\""+Descrip+"\");"; 
    			db.execSQL(query);
    			
    			Toast.makeText(getApplicationContext(), "Job Saved", 3).show();
    			
    			CreateAJob.this.finish();   			
    			break;
    			
    		case R.id.CancelJob:
    			
    			Toast.makeText(getApplicationContext(), "Job Canceled", 3).show();
    			
    			CreateAJob.this.finish();
    			break;
    		}
    	}
    };
    
// public class I2GOnItemSelectedListener implements OnItemSelectedListener {
//    	
//		public void onItemSelected(AdapterView<?> parent, View v, int pos, long row) {
//			//Get Spinners
//	        Spinner jobsitespinner = (Spinner) findViewById(R.id.JobSiteSpinner);
//	        Spinner customerspinner = (Spinner) findViewById(R.id.CustomerSpinner);
//	        
//	        //Database access
//	        DataHelper dh = new DataHelper(getApplicationContext());
//	        SQLiteDatabase db = dh.getWritableDatabase();
//	        
//	        ArrayAdapter<String> adapterjobsite = new ArrayAdapter<String>(
//	            getApplicationContext(), android.R.layout.simple_spinner_item);
//	        adapterjobsite.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//	        jobsitespinner.setAdapter(adapterjobsite);
//
//	        
//	        Cursor results = db.rawQuery("SELECT Site_Name FROM Jobsite WHERE Customer = '" + customerspinner.getSelectedItem().toString() + "';", null);
//	        int sitenameColumnIndex = results.getColumnIndexOrThrow("Site_Name");
//	        
//	        if (results.moveToFirst()) { 
//	            do { 
//	                 adapterjobsite.add(results.getString(sitenameColumnIndex)); 
//	            } 
//	            while (results.moveToNext()); 
//	            
//	        } 
//	        	else { 
//	        		Toast.makeText(getApplicationContext(), "No Jobsites found!", 2).show(); 
//	        	}
//	        
//	        if (db != null) db.close();
//		}
//
//		 public void onNothingSelected(AdapterView<?> parent) {
//			// TODO Auto-generated method stub
//			
//		}
//    };
    
    public class YourItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        	//Get Spinners
	        Spinner jobsitespinner = (Spinner) findViewById(R.id.JobSiteSpinner);
	        Spinner customerspinner = (Spinner) findViewById(R.id.CustomerSpinner);
	        
	        //Database access
	        DataHelper dh = new DataHelper(getApplicationContext());
	        SQLiteDatabase db = dh.getWritableDatabase();
	        
	        ArrayAdapter<String> adapterjobsite = new ArrayAdapter<String>(
	            getApplicationContext(), android.R.layout.simple_spinner_item);
	        adapterjobsite.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        jobsitespinner.setAdapter(adapterjobsite);

	        
	        Cursor results = db.rawQuery("SELECT Site_Name FROM Jobsite WHERE Customer = '" + customerspinner.getSelectedItem().toString() + "';", null);
	        int sitenameColumnIndex = results.getColumnIndexOrThrow("Site_Name");
	        
	        if (results.moveToFirst()) { 
	            do { 
	                 adapterjobsite.add(results.getString(sitenameColumnIndex)); 
	            } 
	            while (results.moveToNext()); 
	            
	        } 
	        	else { 
	        		Toast.makeText(getApplicationContext(), "No Jobsites found!", 2).show(); 
	        	}
	        
	        if (db != null) db.close();
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }
    }
}
