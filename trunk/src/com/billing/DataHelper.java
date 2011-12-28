package com.billing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "Billingdb";
	private static final int DATABASE_VERSION = 2;
	private static final String CREATE_JOBS_TABLE = "CREATE TABLE IF NOT EXISTS " +
			"Jobs (Job_Name char(50), " +
			"Customer char(50), " +
			"Job_Site char (50), " +
			"Description char(300));";
	private static final String CREATE_LABOR_TABLE = "CREATE TABLE IF NOT EXISTS " +
		"Labor (Job_Name char(50), " +
		"Customer char(50), " +
		"Job_Site char (50), " +
		"Job_Date char (50), " + 
		"StartTime char (50), " +
		"EndTime char (50));";
	private static final String CREATE_CUSTOMER_TABLE = "CREATE TABLE IF NOT EXISTS " +
		"Customers (Customer_Name char(50), " +
		"Address char(50), " +
		"Phone char(50), " +
		"Email char(50));";
	private static final String CREATE_JOBSITE_TABLE = "CREATE TABLE IF NOT EXISTS " +
		"Jobsite (Site_Name char(50), " +
		"Customer char(50), " +
		"Location char(50));";
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_JOBS_TABLE);
		db.execSQL(CREATE_LABOR_TABLE);
		db.execSQL(CREATE_CUSTOMER_TABLE);
		db.execSQL(CREATE_JOBSITE_TABLE);
	}
	
	DataHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_LABOR_TABLE);
		db.execSQL(CREATE_CUSTOMER_TABLE);
		db.execSQL(CREATE_JOBSITE_TABLE);
	}

}
