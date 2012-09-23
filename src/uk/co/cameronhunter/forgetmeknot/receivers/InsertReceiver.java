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

        long reminderId = intent.getLongExtra( context.getString( R.id.reminder_id ), -1 );
        String reminderText = intent.getStringExtra( context.getString( R.id.reminder_text ) );
        
        Reminder reminder = data.save( new Reminder( reminderId, reminderText ) );

        Intent showNotification = new Intent( Intent.ACTION_VIEW );
        showNotification.putExtra( context.getString( R.id.reminder_id ), reminder.id );
        showNotification.putExtra( context.getString( R.id.reminder_text ), reminder.text );
        
        context.sendBroadcast( showNotification );
    }

}
