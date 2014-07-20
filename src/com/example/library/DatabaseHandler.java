package com.example.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author Tai Nguyen Bui DatabaseHandler para trabajar con datos de playas y municipios para la aplicación
 * 
 */
public class DatabaseHandler extends SQLiteOpenHelper {
	private static final String CREATE_MUNICIPIOS_TABLE = "CREATE TABLE " + Contract.TABLE_MUNICIPIOS + "("
			+ Contract.KEY_MUNICIPIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Contract.KEY_MUNICIPIO_NOMBRE
			+ " TEXT NOT NULL" + ")";

	public DatabaseHandler(Context context) {
		super(context, Contract.DATABASE_NAME, null, Contract.DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_MUNICIPIOS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + Contract.TABLE_MUNICIPIOS);

		// Create tables again
		onCreate(db);
	}

}
