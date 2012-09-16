package uk.co.cameronhunter.forgetmeknot.receivers;

import static android.content.Context.NOTIFICATION_SERVICE;
import uk.co.cameronhunter.forgetmeknot.R;
import uk.co.cameronhunter.forgetmeknot.data.Reminder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive( Context context, Intent intent ) {
        if ( intent == null || intent.getExtras() == null ) return;
        
        Resources resources = context.getResources();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService( NOTIFICATION_SERVICE );

        long reminderId = intent.getLongExtra( context.getString( R.string.reminder_id ), -1 );
        String reminderText = intent.getStringExtra( context.getString( R.string.reminder_text ) );
        
        Reminder reminder = new Reminder( reminderId, reminderText );
        
        Builder builder = new NotificationCompat.Builder( context );
        
        Intent removeIntent = new Intent( Intent.ACTION_DELETE );
        removeIntent.putExtra( context.getString( R.string.reminder_id ), reminder.id );

        builder.setSmallIcon( R.drawable.logo2 ) //
                .setContentText( reminder.text ) //
                .setContentTitle( resources.getText( R.string.reminder_title ) ) //
                .setTicker( resources.getText( R.string.reminder_ticker ) ) //
                .setDeleteIntent( PendingIntent.getBroadcast( context, reminder.id.intValue(), removeIntent, 0 ) );

        notificationManager.notify( reminder.id.intValue(), builder.build() );
    }

}
