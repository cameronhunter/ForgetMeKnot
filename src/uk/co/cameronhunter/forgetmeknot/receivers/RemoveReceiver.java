package uk.co.cameronhunter.forgetmeknot.receivers;

import uk.co.cameronhunter.forgetmeknot.R;
import uk.co.cameronhunter.forgetmeknot.data.Reminders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RemoveReceiver extends BroadcastReceiver {

    @Override
    public void onReceive( Context context, Intent intent ) {
        if ( intent == null || intent.getExtras() == null ) return;

        long reminderId = intent.getLongExtra( context.getString( R.string.reminder_id ), -1 );

        Reminders data = new Reminders( context );
        data.delete( reminderId );
    }

}
