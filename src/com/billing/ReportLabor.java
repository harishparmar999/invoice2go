package com.billing;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class ReportLabor extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportlabor_layout);
        
        DataHelper dh = new DataHelper(getApplicationContext());
        SQLiteDatabase db = dh.getWritableDatabase();
        String[] columns = new String[1];
        columns[0]= "Job_Name";
        Cursor results = db.query("Jobs", columns, null, null, null, null, null);
        
        int jobnameColumnIndex = results.getColumnIndexOrThrow("Job_Name"); 
             
        ArrayAdapter<CharSequence> adapterForSpinner = new ArrayAdapter<CharSequence>(this, 
        android.R.layout.simple_spinner_item); 
        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        
        Spinner spinner = (Spinner) findViewById(R.id.ReportLaborJobNameSpinner); 
        spinner.setAdapter(adapterForSpinner); 
        
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
            	Toast.makeText(getApplicationContext(), "No Jobs found!", 2).show();
            }
                
         spinner.setOnItemSelectedListener(new YourItemSelectedListener());
         
         Button savenewjob = (Button)findViewById(R.id.ReportLaborButton);
         savenewjob.setOnClickListener(ReportLaborButtonListener);
         
         Button cancelreportlabor = (Button)findViewById(R.id.CancelReportLabor);
         cancelreportlabor.setOnClickListener(ReportLaborButtonListener);
         
         Button starttime = (Button)findViewById(R.id.ReportLaborStartTime);
         starttime.setOnClickListener(TimeClickListener);
         
         Button endtime = (Button)findViewById(R.id.ReportLaborEndTime);
         endtime.setOnClickListener(TimeClickListener);
         
         
         }
    
    private int mHour;
    private int mMinute;
    
    public int StartHour;
    public int StartMinute;
    public int EndHour;
    public int EndMinute;
    
    static final int START_TIME_DIALOG_ID = 0;
    static final int END_TIME_DIALOG_ID = 1;
         
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater =getMenuInflater();
    	inflater.inflate(R.menu.reportlabormenu, menu);
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
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
    
    public class YourItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String selected = parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }
    
    private OnClickListener ReportLaborButtonListener = new OnClickListener() {
    	public void onClick(View v) {
    		switch(v.getId()) {
    		case R.id.ReportLaborButton:
    			DataHelper dh = new DataHelper(getApplicationContext());
    			SQLiteDatabase db = dh.getWritableDatabase();
    			
    			Spinner JobNameSpinner = (Spinner) findViewById(R.id.ReportLaborJobNameSpinner);
    			String JobName = JobNameSpinner.getSelectedItem().toString();
    			   			
    			DatePicker DatePicker = (DatePicker)(findViewById(R.id.ReportLaborDatePicker));
    			int Month = DatePicker.getMonth()+1;
    			int Day = DatePicker.getDayOfMonth();
    			int Year = DatePicker.getYear();
    			
    			String Date = Month+"-"+Day+"-"+Year;
    			String StartTime = StartHour+":"+StartMinute+":"+"00";
    			String EndTime = EndHour+":"+EndMinute+":"+"00";
    			
    			String query = "INSERT INTO Labor (Job_Name, Job_Date, StartTime, EndTime)" +
    					"VALUES (\""+JobName+"\",\""+Date+"\",\""+StartTime+"\",\""+EndTime+"\");"; 
    			db.execSQL(query);
    			
    			Toast SavedDisp = Toast.makeText(getApplicationContext(), "Labor Reported! ", 2);
    			SavedDisp.show();
    			
    			ReportLabor.this.finish();   			
    			break;
    			
    		case R.id.CancelReportLabor:
    			ReportLabor.this.finish();
    			break;
    		
    		}
    		
    	}
    };
    
    private OnClickListener TimeClickListener = new OnClickListener() {
    	public void onClick(View v) {
    		switch(v.getId()) {
    		case R.id.ReportLaborStartTime:
    			showDialog(START_TIME_DIALOG_ID);
	    		break;
	    			
	    	case R.id.ReportLaborEndTime:
	    		showDialog(END_TIME_DIALOG_ID);
	    		break;
	    		
	    	default:
	    		break;
    		}
	    		
	    }
    };
    	
    	@Override
    	protected Dialog onCreateDialog(int id) {
    	    switch (id) {
    	    case START_TIME_DIALOG_ID:
    	        return new TimePickerDialog(this,
    	        		startTimeSetListener, mHour, mMinute, true);
    	          	        
    	    case END_TIME_DIALOG_ID:
    	    	return new TimePickerDialog(this,
    	    			endTimeSetListener, mHour, mMinute, true);
    	    	        	        
    	    }
    	    return null;
    	}

    	    
 // updates the time we display in the TextView
    private void updateStartTime() {
        Button ReportLaborStartTime = (Button) findViewById(R.id.ReportLaborStartTime);
    	ReportLaborStartTime.setText(
            new StringBuilder()
                    .append(pad(mHour)).append(":")
                    .append(pad(mMinute)));
    }

    private void updateEndTime() {
        Button ReportLaborEndTime = (Button) findViewById(R.id.ReportLaborEndTime);
    	ReportLaborEndTime.setText(
            new StringBuilder()
                    .append(pad(mHour)).append(":")
                    .append(pad(mMinute)));
    }
    
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    
    private TimePickerDialog.OnTimeSetListener startTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                StartHour = hourOfDay;
                mMinute = minute;
                StartMinute = minute;
                updateStartTime();
            }
        };
        
    private TimePickerDialog.OnTimeSetListener endTimeSetListener =
    	new TimePickerDialog.OnTimeSetListener() {
    	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    		mHour = hourOfDay;
    		EndHour = hourOfDay;
    		mMinute = minute;
    		EndMinute = minute;
    		updateEndTime();
    	}
    };
}