package uk.co.cameronhunter.forgetmeknot.receivers;

import uk.co.cameronhunter.forgetmeknot.R;
import uk.co.cameronhunter.forgetmeknot.data.Reminder;
import uk.co.cameronhunter.forgetmeknot.data.Reminders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class InsertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive( Context context, Intent intent ) {
        if ( intent == null || intent.getExtras() == null ) return;

        Reminders data = new Reminders( context );

        String reminderText = intent.getStringExtra( context.getString( R.string.reminder_text ) );
        Reminder reminder = data.add( new Reminder( reminderText ) );

        Intent showNotification = new Intent( Intent.ACTION_VIEW );
        showNotification.putExtra( context.getString( R.string.reminder_id ), reminder.id );
        showNotification.putExtra( context.getString( R.string.reminder_text ), reminder.text );
        
        context.sendBroadcast( showNotification );
    }

}
