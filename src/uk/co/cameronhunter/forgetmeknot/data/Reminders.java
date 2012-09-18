package uk.co.cameronhunter.forgetmeknot.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Reminders extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Reminders.db";

    private static final String TABLE_NAME = "reminders";
    private static final String PRIMARY_KEY = "_id";
    private static final String REMINDER_COLUMN_NAME = "reminder";

    public Reminders( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( "create table " + TABLE_NAME + " (" + PRIMARY_KEY + " integer primary key autoincrement, " + REMINDER_COLUMN_NAME + " text not null)" );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        if ( oldVersion >= newVersion ) return;

        Log.w( getClass().getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data" );
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate( db );
    }

    public Reminder add( Reminder reminder ) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues record = new ContentValues();
        record.put( REMINDER_COLUMN_NAME, reminder.text.toString() );

        long id = db.insert( TABLE_NAME, null, record );
        db.close();

        if ( id < 0 ) throw new RuntimeException( "Didn't save reminder" );

        Log.i( "Database", "Inserted reminder with ID: " + id );

        return new Reminder( id, reminder.text );
    }

    public void delete( long reminderId ) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete( TABLE_NAME, PRIMARY_KEY + "= ?", new String[] { String.valueOf( reminderId ) } );
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete( TABLE_NAME, null, null );
        db.close();
    }

    public List<Reminder> getAll() {
        List<Reminder> reminders = new ArrayList<Reminder>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery( "select " + PRIMARY_KEY + ", " + REMINDER_COLUMN_NAME + " from " + TABLE_NAME + " order by " + PRIMARY_KEY + " asc", null );

        if ( cursor.moveToFirst() ) {
            while ( !cursor.isAfterLast() ) {
                reminders.add( new Reminder( cursor.getLong( 0 ), cursor.getString( 1 ) ) );
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return reminders;
    }

}
