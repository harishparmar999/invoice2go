package com.billing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout); 
        
        DataHelper dh = new DataHelper(getApplicationContext());
        SQLiteDatabase db = dh.getWritableDatabase();
        dh.onUpgrade(db, db.getVersion(), 2);
    }
        
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater =getMenuInflater();
    	inflater.inflate(R.menu.mainmenu, menu);
    	return true;
    }
    
    static final int ABOUT = 0;
    
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.CreateAJob:
        	startActivity(new Intent(this, CreateAJob.class));
            return true;
            
        case R.id.About:
        	showDialog(ABOUT);
        	return true;
        	
        case R.id.mainquit:
            this.finish();
            return true;
                
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void onViewClickHandler(View v){
    	switch(v.getId()){
    	case R.id.CreateAJob:
    		startActivity(new Intent(this, CreateAJob.class));
    		break;
    		
    	case R.id.ManageJob:
    		startActivity(new Intent(this, ManageJobs.class));
    		break;
    		
    	case R.id.ReportLabor:
    		startActivity(new Intent(this, ReportLabor.class));
    		break;
    		
    	case R.id.LaborHistory:
    		startActivity(new Intent(this, LaborHistory.class));
    		break;
    		
    	case R.id.SubmitForInvoice:
    		startActivity(new Intent(this, SubmitForInvoice.class));
    		break;
    		
    	/*case R.id.Settings:
    		startActivity(new Intent(this, Settings.class));
    		break;**/
    	}
    }
    
   protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case ABOUT:
	    	final Dialog aboutdialog = new Dialog(this);
		    aboutdialog.setContentView(R.layout.aboutdialog_layout);
		    aboutdialog.setTitle("About Invoice 2 Go");
		    
		    Button closeaboutdialog = (Button)aboutdialog.findViewById(R.id.CloseAboutDialog);
		    closeaboutdialog.setOnClickListener(new View.OnClickListener() {
		    	public void onClick(View v) {
		    		aboutdialog.cancel();
		    	}
		    });
		    aboutdialog.show();
		    break;
	    }
	    return null;
    }
         
}