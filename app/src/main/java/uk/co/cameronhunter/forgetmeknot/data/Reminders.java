package uk.co.cameronhunter.forgetmeknot.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Reminders extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Reminders.db";

    private static final String TABLE_NAME = "reminders";
    private static final String PRIMARY_KEY = "_id";
    private static final String REMINDER_COLUMN_NAME = "reminder";
    private static final String DELETED_COLUMN_NAME = "deleted";
    public static final String[] PROJECTION = new String[]{PRIMARY_KEY, REMINDER_COLUMN_NAME, DELETED_COLUMN_NAME};

    public Reminders(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static void closeQuietly(SQLiteDatabase db) {
        if (db != null) db.close();
    }

    private static void closeQuietly(Cursor cursor) {
        if (cursor != null) cursor.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "create table if not exists " + TABLE_NAME + " (" +
                PRIMARY_KEY + " integer primary key autoincrement," +
                REMINDER_COLUMN_NAME + " text not null," +
                DELETED_COLUMN_NAME + " integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            db.execSQL("alter table " + TABLE_NAME + " add " + DELETED_COLUMN_NAME + " integer");
        } else {
            onCreate(db);
        }
    }

    public Reminder save(Reminder reminder) {
        if (reminder.isSaved()) {
            return update(reminder);
        }

        return add(reminder);
    }

    private Reminder add(Reminder reminder) {
        SQLiteDatabase db = null;
        try {
            ContentValues record = new ContentValues();
            record.put(REMINDER_COLUMN_NAME, reminder.text.toString());
            record.put(DELETED_COLUMN_NAME, false);

            db = getWritableDatabase();
            long id = db.insert(TABLE_NAME, null, record);

            if (id < 0) throw new RuntimeException("Didn't save reminder");

            return new Reminder(id, reminder.text, reminder.deleted);
        } finally {
            closeQuietly(db);
        }
    }

    private Reminder update(Reminder reminder) {
        SQLiteDatabase db = null;
        try {
            ContentValues record = new ContentValues();
            record.put(REMINDER_COLUMN_NAME, reminder.text.toString());
            record.put(DELETED_COLUMN_NAME, reminder.deleted);

            db = getWritableDatabase();
            db.update(TABLE_NAME, record, PRIMARY_KEY + "=?", new String[]{String.valueOf(reminder.id)});

            return reminder;
        } finally {
            closeQuietly(db);
        }
    }

    public Reminder find(long reminderId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = getReadableDatabase();
            cursor = db.query(TABLE_NAME, PROJECTION, PRIMARY_KEY + "=?", new String[]{String.valueOf(reminderId)}, null, null, null);
            return cursor.moveToFirst() ? new Reminder(cursor.getLong(0), cursor.getString(1), cursor.getInt(2) == 1) : null;
        } finally {
            closeQuietly(cursor);
            closeQuietly(db);
        }
    }

    public Reminder delete(long reminderId) {
        Reminder reminder = find(reminderId);
        return update(new Reminder(reminder.id, reminder.text, true));
    }

    public void deleteAll() {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.delete(TABLE_NAME, null, null);
        } finally {
            closeQuietly(db);
        }
    }

    public List<Reminder> getAll() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            List<Reminder> reminders = new ArrayList<Reminder>();
            db = getReadableDatabase();
            cursor = db.query(TABLE_NAME, PROJECTION, DELETED_COLUMN_NAME + "=0", null, null, null, PRIMARY_KEY + " asc");
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    reminders.add(new Reminder(cursor.getLong(0), cursor.getString(1), cursor.getInt(2) == 1));
                    cursor.moveToNext();
                }
            }
            return reminders;
        } finally {
            closeQuietly(cursor);
            closeQuietly(db);
        }
    }
}
