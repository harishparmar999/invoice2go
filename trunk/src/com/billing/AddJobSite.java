package com.billing;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddJobSite extends Activity {
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addjobsite_layout);
             
        Button savenewjob = (Button)findViewById(R.id.SaveJobSite);
        savenewjob.setOnClickListener(buttonlistener);
        
        Button canceljob = (Button)findViewById(R.id.CancelJobsite);
        canceljob.setOnClickListener(buttonlistener);
        
        
        DataHelper dh = new DataHelper(getApplicationContext());
        SQLiteDatabase db = dh.getWritableDatabase();
        
        //Populate customer spinner
        Spinner customerspinner = (Spinner) findViewById(R.id.JobSiteCustomerSpinner);
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
    }
    
    private OnClickListener buttonlistener = new OnClickListener() {
    	public void onClick(View v) {
    		switch(v.getId()) {
    			
    		case R.id.SaveJobSite:
    			DataHelper dh = new DataHelper(getApplicationContext());
    			SQLiteDatabase db = dh.getWritableDatabase();
    			
    			TextView JobSiteNameView = (TextView)(findViewById(R.id.NewJobSite));
    			String JobSiteName = JobSiteNameView.getText().toString();
    			TextView AddressView = (TextView)(findViewById(R.id.NewJobSiteAddress));
    			String Address = AddressView.getText().toString();
    			Spinner customerspinner = (Spinner)(findViewById(R.id.JobSiteCustomerSpinner));
    			String Customer = customerspinner.getSelectedItem().toString();
        			
    			String query = "INSERT INTO Jobsite (Site_Name, Location, Customer) " +
    					"VALUES (\""+JobSiteName+"\",\""+Address+"\",\""+Customer+"\");"; 
    			db.execSQL(query);
    			
    			Toast.makeText(getApplicationContext(), "Jobsite Saved", 3).show();
    			
    			AddJobSite.this.finish();   			
    			break;
    			
    		case R.id.CancelJobsite:
    			
    			Toast.makeText(getApplicationContext(), "Jobsite Canceled", 3).show();
    			
    			AddJobSite.this.finish();
    			break;
    		}
    	}
    };
}
