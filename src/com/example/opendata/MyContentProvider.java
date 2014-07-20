package com.example.opendata;

import com.example.library.Contract;
import com.example.library.DatabaseHandler;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * 
 * @author Tai Nguyen Bui
 * 
 *         Content provider para la aplicación "playeando"
 * 
 */
public class MyContentProvider extends ContentProvider {

	private DatabaseHandler dbHandler = null;

	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(Contract.AUTHORITY, Contract.TABLE_MUNICIPIOS, Contract.MUNICIPIOS);
		uriMatcher.addURI(Contract.AUTHORITY, Contract.TABLE_MUNICIPIOS + "/#", Contract.MUNICIPIOS_ID);
	}

	@Override
	public boolean onCreate() {
		this.dbHandler = new DatabaseHandler(this.getContext());
		return true;
	}

	@Override
	public String getType(Uri uri) {
		String contentType;
		if (uri.getLastPathSegment() == null) {
			contentType = Contract.CONTENT_TYPE_MULTIPLE;
		}
		else {
			contentType = Contract.CONTENT_TYPE_SINGLE;
		}
		return contentType;
	}

	/**
	 * Insert datos en la base de datos
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.d("MyContentProvider", "insert");

		int uriType = uriMatcher.match(uri);
		Uri _uri = null;
		SQLiteDatabase db = dbHandler.getWritableDatabase();
		switch (uriType) {
		case Contract.MUNICIPIOS:
			long rowID = db.insert(Contract.TABLE_MUNICIPIOS, null, values);
			db.close();
			if (rowID > 0) {
				_uri = ContentUris.withAppendedId(uri, rowID);
				getContext().getContentResolver().notifyChange(_uri, null);

			}
			else {
				throw new SQLException("No se pudo insertar dato " + uri);
			}
			break;
		default:
			throw new IllegalArgumentException("URI desconocida: " + uri);

		}
		getContext().getContentResolver().notifyChange(_uri, null);
		return _uri;

	}

	/**
	 * Buscar datos en base de datos
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.d("MyContentProvider", "Obtener datos de base de datos");

		SQLiteDatabase db = dbHandler.getWritableDatabase();
		Cursor cursor = null;
		int uriType = uriMatcher.match(uri);
		switch (uriType) {
		case Contract.MUNICIPIOS:
			cursor = db.query(Contract.TABLE_MUNICIPIOS, projection, selection, selectionArgs, null, null, sortOrder,
					null);
			cursor.moveToFirst();
			break;
		default:
			throw new IllegalArgumentException("URI desconocida: " + uri);
		}
		return cursor;
	}

	/**
	 * Actualizar base de datos
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		Log.d("MyContentProvider", "actualizar base de datos");
		int res = 0;
		int uriType = uriMatcher.match(uri);
		switch (uriType) {
		case Contract.MUNICIPIOS:
			SQLiteDatabase db = dbHandler.getWritableDatabase();
			res = db.update(Contract.TABLE_MUNICIPIOS, values, selection, selectionArgs);
			Log.d("MyContentProvider", "Municipios actualizados");
			db.close();
			break;
		default:
			throw new IllegalArgumentException("URI desconocida " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return res;
	}

	/**
	 * Delete data on database
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.d("MyContentProvider", "delete");
		int res = 0;
		int uriType = uriMatcher.match(uri);
		switch (uriType) {
		case Contract.MUNICIPIOS:
			SQLiteDatabase db = dbHandler.getWritableDatabase();
			res = db.delete(Contract.TABLE_MUNICIPIOS, selection, selectionArgs);
			db.close();
			break;
		default:
			throw new IllegalArgumentException("URI desconocida " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return res;
	}

}
