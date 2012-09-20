package uk.co.cameronhunter.forgetmeknot.receivers;

import static android.content.Intent.ACTION_VIEW;
import uk.co.cameronhunter.forgetmeknot.R;
import uk.co.cameronhunter.forgetmeknot.data.Reminder;
import uk.co.cameronhunter.forgetmeknot.data.Reminders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive( Context context, Intent intent ) {
        Reminders data = new Reminders( context );
        
        for ( Reminder reminder : data.getAll() ) {
            Intent showNotification = new Intent( ACTION_VIEW );
            showNotification.putExtra( context.getString( R.string.reminder_id ), reminder.id );
            showNotification.putExtra( context.getString( R.string.reminder_text ), reminder.text );
            showNotification.putExtra( context.getString( R.string.respawn ), true );
            
            context.sendBroadcast( showNotification );
        }
    }

}
