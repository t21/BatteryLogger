package se.thomasberg.BatteryLogger;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class BatteryLoggerContentProvider extends ContentProvider {

	private static final int BATTERY = 1;
//	private static final int CHARGER = 2;   
//	private static final int ONOFF = 3;   
//	private static final int SCREEN = 4;   

	private static final UriMatcher uriMatcher;
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(MyApplication.PROVIDER_NAME, "battery", BATTERY);
//		uriMatcher.addURI(MyApplication.PROVIDER_NAME, "charger", CHARGER);      
//		uriMatcher.addURI(MyApplication.PROVIDER_NAME, "onoff", ONOFF);      
//		uriMatcher.addURI(MyApplication.PROVIDER_NAME, "screen", SCREEN);      
	}   

	//---for database use---
	private SQLiteDatabase battLoggDB;
	private static final String DATABASE_NAME = "BattLoggStorage";
	private static final int DATABASE_VERSION = 6;

	private static final String CREATE_BATTERY_DATA_TABLE = "create table " + 
			MyApplication.BATTERY_DATA_TABLE_NAME + " (" +
			MyApplication._ID + " integer primary key autoincrement, " +
			MyApplication.TYPE + " integer, " +
			MyApplication.TIME + " long, " +
			MyApplication.CAPACITY + " integer, " +
			MyApplication.VOLTAGE + " real, " +
			MyApplication.TEMPERATURE + " real, " +
			MyApplication.STATUS + " integer, " +
			MyApplication.PLUGGED + " integer, " +
			MyApplication.HEALTH + " integer, " +
			MyApplication.POWER_ONOFF + " integer, " +
			MyApplication.SCREEN_ONOFF + " integer );";

//	private static final String CREATE_CHARGER_DATA_TABLE = "create table " + 
//			MyApplication.CHARGER_DATA_TABLE_NAME + " (" +
//			MyApplication._ID + " integer primary key autoincrement, " +
//			MyApplication.TIME + " long, " +
//			MyApplication.CAPACITY + " integer, " +
//			MyApplication.PLUGGED + " integer );";

//	private static final String CREATE_ONOFF_DATA_TABLE = "create table " + 
//			MyApplication.ONOFF_DATA_TABLE_NAME + " (" +
//			MyApplication._ID + " integer primary key autoincrement, " +
//			MyApplication.TIME + " long, " +
//			MyApplication.CAPACITY + " integer, " +
//			MyApplication.POWER_ONOFF + " integer );";

//	private static final String CREATE_SCREEN_DATA_TABLE = "create table " + 
//			MyApplication.SCREEN_DATA_TABLE_NAME + " (" +
//			MyApplication._ID + " integer primary key autoincrement, " +
//			MyApplication.TIME + " long, " +
//			MyApplication.CAPACITY + " integer, " +
//			MyApplication.SCREEN_ONOFF + " integer );";


	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			if (MyApplication.DEBUG_BatteryLoggerContentProvider)
				Log.v(MyApplication.TAG + ": DatabaseHelper onCreate", "Creating all the tables");
			try {
				db.execSQL(CREATE_BATTERY_DATA_TABLE);
//				db.execSQL(CREATE_CHARGER_DATA_TABLE);
//				db.execSQL(CREATE_ONOFF_DATA_TABLE);
//				db.execSQL(CREATE_SCREEN_DATA_TABLE);
			} catch (SQLiteException ex) {
				if (MyApplication.DEBUG_BatteryLoggerContentProvider)
					Log.v(MyApplication.TAG + ": Create table exception", ex.getMessage());
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (MyApplication.DEBUG_BatteryLoggerContentProvider)
				Log.w(MyApplication.TAG + ": DB", "Upgrading from version " + oldVersion + " to version " + newVersion);
			db.execSQL("drop table if exists " + MyApplication.BATTERY_DATA_TABLE_NAME);
//			db.execSQL("drop table if exists " + MyApplication.CHARGER_DATA_TABLE_NAME);
//			db.execSQL("drop table if exists " + MyApplication.ONOFF_DATA_TABLE_NAME);
//			db.execSQL("drop table if exists " + MyApplication.SCREEN_DATA_TABLE_NAME);
			onCreate(db);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		//			// arg0 = uri 
		//		      // arg1 = selection
		//		      // arg2 = selectionArgs
		//		      int count=0;
		//		      switch (uriMatcher.match(arg0)){
		//		         case BOOKS:
		//		            count = booksDB.delete(
		//		               DATABASE_TABLE,
		//		               arg1, 
		//		               arg2);
		//		            break;
		//		         case BOOK_ID:
		//		            String id = arg0.getPathSegments().get(1);
		//		            count = booksDB.delete(
		//		               DATABASE_TABLE,                        
		//		               _ID + " = " + id + 
		//		               (!TextUtils.isEmpty(arg1) ? " AND (" + 
		//		               arg1 + ')' : ""), 
		//		               arg2);
		//		            break;
		//		         default: throw new IllegalArgumentException(
		//		            "Unknown URI " + arg0);    
		//		      }       
		//		      getContext().getContentResolver().notifyChange(arg0, null);
		//		      return count;
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
		//		   switch (uriMatcher.match(uri)){
		//	         //---get all books---
		//	         case BOOKS:
		//	            return "vnd.android.cursor.dir/vnd.learn2develop.books ";
		//	         //---get a particular book---
		//	         case BOOK_ID:                
		//	            return "vnd.android.cursor.item/vnd.learn2develop.books ";
		//	         default:
		//	            throw new IllegalArgumentException("Unsupported URI: " + uri);        
		//	      }  
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		//---add a new book---
		long rowID = 0;
		if (MyApplication.DEBUG_BatteryLoggerContentProvider)
			Log.i(MyApplication.TAG, "start insert into " + uri.getHost());
		if (uriMatcher.match(uri) == BATTERY) {
			if (MyApplication.DEBUG_BatteryLoggerContentProvider)
				Log.i(MyApplication.TAG, "insert battery data");
			rowID = battLoggDB.insert(MyApplication.BATTERY_DATA_TABLE_NAME, "", values);
//		} else if (uriMatcher.match(uri) == CHARGER) {
//			if (MyApplication.DEBUG_BatteryLoggerContentProvider)
//				Log.i(MyApplication.TAG, "insert charger data");
//			rowID = battLoggDB.insert(MyApplication.CHARGER_DATA_TABLE_NAME, "", values);
//		} else if (uriMatcher.match(uri) == ONOFF) {
//			if (MyApplication.DEBUG_BatteryLoggerContentProvider)
//				Log.i(MyApplication.TAG, "insert power on/off data");
//			rowID = battLoggDB.insert(MyApplication.ONOFF_DATA_TABLE_NAME, "", values);
//		} else if (uriMatcher.match(uri) == SCREEN) {
//			if (MyApplication.DEBUG_BatteryLoggerContentProvider)
//				Log.i(MyApplication.TAG, "insert screen on/off data");
//			rowID = battLoggDB.insert(MyApplication.SCREEN_DATA_TABLE_NAME, "", values);
		}
		if (MyApplication.DEBUG_BatteryLoggerContentProvider)
			Log.i(MyApplication.TAG, "stop insert into " + uri.getHost());

		//---if added successfully---
		if (rowID>0) {
			Uri _uri = ContentUris.withAppendedId(MyApplication.CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(_uri, null); 
			if (MyApplication.DEBUG_BatteryLoggerContentProvider)
				Log.i(MyApplication.TAG, "notifyChange");
			return _uri;                
		}        
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public boolean onCreate() {
		Context context = getContext();
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		battLoggDB = dbHelper.getWritableDatabase();
		return (battLoggDB == null)? false:true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();

		if (uriMatcher.match(uri) == BATTERY) {
			sqlBuilder.setTables(MyApplication.BATTERY_DATA_TABLE_NAME);
//		} else if (uriMatcher.match(uri) == CHARGER) {
//			sqlBuilder.setTables(MyApplication.CHARGER_DATA_TABLE_NAME);
//		} else if (uriMatcher.match(uri) == ONOFF) {
//			sqlBuilder.setTables(MyApplication.ONOFF_DATA_TABLE_NAME);
//		} else if (uriMatcher.match(uri) == SCREEN) {
//			sqlBuilder.setTables(MyApplication.SCREEN_DATA_TABLE_NAME);
		}

		Cursor c = sqlBuilder.query(
				battLoggDB, 
				projection, 
				selection, 
				selectionArgs, 
				null, 
				null, 
				sortOrder);

		//---register to watch a content URI for changes---
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		//			int count = 0;
		//		      switch (uriMatcher.match(uri)){
		//		         case BOOKS:
		//		            count = booksDB.update(
		//		               DATABASE_TABLE, 
		//		               values,
		//		               selection, 
		//		               selectionArgs);
		//		            break;
		//		         case BOOK_ID:                
		//		            count = booksDB.update(
		//		               DATABASE_TABLE, 
		//		               values,
		//		               _ID + " = " + uri.getPathSegments().get(1) + 
		//		               (!TextUtils.isEmpty(selection) ? " AND (" + 
		//		                  selection + ')' : ""), 
		//		                selectionArgs);
		//		            break;
		//		         default: throw new IllegalArgumentException(
		//		            "Unknown URI " + uri);    
		//		      }       
		//		      getContext().getContentResolver().notifyChange(uri, null);
		//		      return count;
		return 0;
	}


}
