package uk.co.cameronhunter.forgetmeknot.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;

import uk.co.cameronhunter.forgetmeknot.MainActivity;
import uk.co.cameronhunter.forgetmeknot.R;
import uk.co.cameronhunter.forgetmeknot.data.Reminder;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Intent.ACTION_DELETE;
import static android.content.Intent.ACTION_EDIT;
import static android.content.Intent.ACTION_VIEW;
import static uk.co.cameronhunter.forgetmeknot.MainActivity.INTENT_REMINDER_ID;
import static uk.co.cameronhunter.forgetmeknot.MainActivity.INTENT_REMINDER_TEXT;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null) return;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        long reminderId = intent.getLongExtra(INTENT_REMINDER_ID, -1);
        String reminderText = intent.getStringExtra(INTENT_REMINDER_TEXT);

        Reminder reminder = new Reminder(reminderId, reminderText);

        if (ACTION_VIEW.equals(intent.getAction())) {
            notificationManager.notify(reminder.id.intValue(), createNotification(context, reminder));
        } else if (ACTION_DELETE.equals(intent.getAction())) {
            notificationManager.cancel(reminder.id.intValue());
        }
    }

    private Notification createNotification(Context context, Reminder reminder) {
        Intent removeIntent = new Intent(ACTION_DELETE);
        removeIntent.putExtra(INTENT_REMINDER_ID, reminder.id);

        Intent editIntent = new Intent(context, MainActivity.class);
        editIntent.setAction(ACTION_EDIT);
        editIntent.putExtra(INTENT_REMINDER_ID, reminder.id);

        PendingIntent pendingEditIntent = PendingIntent.getActivity(context, reminder.id.intValue(), editIntent, 0);
        PendingIntent pendingDeleteIntent = PendingIntent.getBroadcast(context, reminder.id.intValue(), removeIntent, 0);

        BigTextStyle builder = new BigTextStyle( //
                new Builder(context).setSmallIcon(R.drawable.logo2) //
                        .setContentText(reminder.text) //
                        .setContentTitle(context.getString(R.string.reminder_title)) //
                        .setTicker(context.getString(R.string.reminder_added)) //
                        .setContentIntent(pendingEditIntent) //
                        .setDeleteIntent(pendingDeleteIntent) //
        ).bigText(reminder.text);

        return builder.build();
    }

}
