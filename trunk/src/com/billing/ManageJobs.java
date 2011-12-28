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
import android.widget.TextView;
import android.widget.Toast;

public class ManageJobs extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managejobs_layout);
        
        Button deletejob = (Button)findViewById(R.id.DeleteJob);
        deletejob.setOnClickListener(buttonlistener);
                
        DataHelper dh = new DataHelper(getApplicationContext());
        SQLiteDatabase db = dh.getWritableDatabase();
        String[] columns = new String[1];
        columns[0]= "Job_Name";
        Cursor results = db.query("Jobs", columns, null, null, null, null, null);
        
        int jobnameColumnIndex = results.getColumnIndexOrThrow("Job_Name"); 
             
        ArrayAdapter<String> adapterForSpinner = new ArrayAdapter<String>(this, 
        android.R.layout.simple_spinner_item); 
        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        
        Spinner spinner = (Spinner) findViewById(R.id.JobNameSpinner); 
        spinner.setAdapter(adapterForSpinner); 
        spinner.setOnItemSelectedListener(new YourItemSelectedListener());
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
          } 
        
//    OnItemSelectedListener onSelect = new Spinner.OnItemSelectedListener() {
//    	
//		@Override
//		public void onItemSelected(AdapterView<?> parent, View v, int pos, long row) {
//			// TODO Auto-generated method stub
//			TextView spinner = (TextView) v;
//			String item = spinner.getText().toString();
//			
//			DataHelper dh = new DataHelper(getApplicationContext());
//	        SQLiteDatabase db = dh.getWritableDatabase();
//			String[] columns = new String[3];
//			columns[0] = "Customer";
//			columns[1] = "Job_Site";
//			columns[2] = "Description";
//			
//			String selection = "SELECT Customer, Job_Site, Description FROM Jobs WHERE Job_Name = '" + item + "';"; 
//	        Cursor results = db.rawQuery(selection, null);
//	        
//	        int CustomerColumnIndex = results.getColumnIndexOrThrow(columns[0]);
//	        int JobSiteColumnIndex = results.getColumnIndexOrThrow(columns[1]);
//	        int DescriptionColumnIndex = results.getColumnIndexOrThrow(columns[2]);
//	        
//	        results.moveToFirst();
//	        TextView customername = (TextView) findViewById(R.id.CustomerName);
//	        customername.setText(results.getString(CustomerColumnIndex));
//	        customername.postInvalidate();
//	        TextView jobsitename = (TextView) findViewById(R.id.JobSite);
//	        jobsitename.setText(results.getString(JobSiteColumnIndex));
//	        jobsitename.postInvalidate();
//	        TextView description = (TextView) findViewById(R.id.Description);
//	        description.setText(results.getString(DescriptionColumnIndex));
//	        description.postInvalidate();
//		}
//
//		@Override
//		public void onNothingSelected(AdapterView<?> parent) {
//			// TODO Auto-generated method stub
//			
//		}
//    };
    
    public class YourItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        	TextView spinner = (TextView) view;
			String item = spinner.getText().toString();
			
			DataHelper dh = new DataHelper(getApplicationContext());
	        SQLiteDatabase db = dh.getWritableDatabase();
			String[] columns = new String[3];
			columns[0] = "Customer";
			columns[1] = "Job_Site";
			columns[2] = "Description";
			
			String selection = "SELECT Customer, Job_Site, Description FROM Jobs WHERE Job_Name = '" + item + "';"; 
	        Cursor results = db.rawQuery(selection, null);
	        
	        int CustomerColumnIndex = results.getColumnIndexOrThrow(columns[0]);
	        int JobSiteColumnIndex = results.getColumnIndexOrThrow(columns[1]);
	        int DescriptionColumnIndex = results.getColumnIndexOrThrow(columns[2]);
	        
	        results.moveToFirst();
	        TextView customername = (TextView) findViewById(R.id.CustomerName);
	        customername.setText(results.getString(CustomerColumnIndex));
	        customername.postInvalidate();
	        TextView jobsitename = (TextView) findViewById(R.id.JobSite);
	        jobsitename.setText(results.getString(JobSiteColumnIndex));
	        jobsitename.postInvalidate();
	        TextView description = (TextView) findViewById(R.id.Description);
	        description.setText(results.getString(DescriptionColumnIndex));
	        description.postInvalidate();
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater =getMenuInflater();
    	inflater.inflate(R.menu.managejobsmenu, menu);
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
    
    private OnClickListener buttonlistener = new OnClickListener() {
    	public void onClick(View v) {
    		switch(v.getId()) {
    		case R.id.DeleteJob:
    			DataHelper dh = new DataHelper(getApplicationContext());
    			SQLiteDatabase db = dh.getWritableDatabase();
    			Spinner JobNameView = (Spinner)(findViewById(R.id.JobNameSpinner));
    			String[] DeleteColumn = new String[1];
    			DeleteColumn[0] = JobNameView.getSelectedItem().toString();
    			db.delete("Jobs", "Job_Name=?", DeleteColumn);
    			db.close();
    			
    			Toast DeleteDisp = Toast.makeText(getApplicationContext(), DeleteColumn[0] + " Job Deleted", 3);
    			DeleteDisp.show();
    			break;
    			
    		case R.id.CancelManageJobs:
    			ManageJobs.this.finish();
    			break;
    		
    		}
    	}
    };
}