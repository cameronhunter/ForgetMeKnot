package uk.co.cameronhunter.forgetmeknot.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import uk.co.cameronhunter.forgetmeknot.data.Reminder;
import uk.co.cameronhunter.forgetmeknot.data.Reminders;

import static uk.co.cameronhunter.forgetmeknot.MainActivity.INTENT_REMINDER_ID;
import static uk.co.cameronhunter.forgetmeknot.MainActivity.INTENT_REMINDER_TEXT;

public class InsertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null) return;

        Reminders data = new Reminders(context);

        long reminderId = intent.getLongExtra(INTENT_REMINDER_ID, -1);
        String reminderText = intent.getStringExtra(INTENT_REMINDER_TEXT);

        Reminder reminder = data.save(new Reminder(reminderId, reminderText));

        Intent showNotification = new Intent(Intent.ACTION_VIEW);
        showNotification.putExtra(INTENT_REMINDER_ID, reminder.id);
        showNotification.putExtra(INTENT_REMINDER_TEXT, reminder.text);

        context.sendBroadcast(showNotification);
    }

}
