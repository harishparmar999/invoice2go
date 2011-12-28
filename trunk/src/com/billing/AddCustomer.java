package com.billing;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddCustomer extends Activity {
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcustomer_layout);
                
        Button savenewjob = (Button)findViewById(R.id.SaveCustomer);
        savenewjob.setOnClickListener(buttonlistener);
        
        Button canceljob = (Button)findViewById(R.id.CancelCustomer);
        canceljob.setOnClickListener(buttonlistener);
        
    }

    private OnClickListener buttonlistener = new OnClickListener() {
    	public void onClick(View v) {
    		switch(v.getId()) {
    			
    		case R.id.SaveCustomer:
    			DataHelper dh = new DataHelper(getApplicationContext());
    			SQLiteDatabase db = dh.getWritableDatabase();
    			
    			TextView JobNameView = (TextView)(findViewById(R.id.NewCustomerName));
    			String JobName = JobNameView.getText().toString();
    			TextView AddressView = (TextView)(findViewById(R.id.NewAddress));
    			String Address = AddressView.getText().toString();
    			TextView PhoneView = (TextView)(findViewById(R.id.NewPhoneNumber));
    			String Phone = PhoneView.getText().toString();    			
    			TextView EmailView = (TextView)(findViewById(R.id.NewEmailAddress));
    			String Email = EmailView.getText().toString();
    			
    			String query = "INSERT INTO Customers (Customer_Name, Address, Phone, Email)" +
    					"VALUES (\""+JobName+"\",\""+Address+"\",\""+Phone+"\",\""+Email+"\");"; 
    			db.execSQL(query);
    			
    			Toast.makeText(getApplicationContext(), "Customer Saved", 3).show();
    			
    			AddCustomer.this.finish();   			
    			break;
    			
    		case R.id.CancelJob:
    			
    			Toast.makeText(getApplicationContext(), "Job Canceled", 3).show();
    			
    			AddCustomer.this.finish();
    			break;
    		}
    	}
    };
}