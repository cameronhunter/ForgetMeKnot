package uk.co.cameronhunter.forgetmeknot.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import uk.co.cameronhunter.forgetmeknot.data.Reminder;
import uk.co.cameronhunter.forgetmeknot.data.Reminders;

import static android.content.Intent.ACTION_VIEW;
import static uk.co.cameronhunter.forgetmeknot.MainActivity.INTENT_REMINDER_ID;
import static uk.co.cameronhunter.forgetmeknot.MainActivity.INTENT_REMINDER_TEXT;
import static uk.co.cameronhunter.forgetmeknot.MainActivity.INTENT_RESPAWN;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Reminders data = new Reminders(context);

        for (Reminder reminder : data.getAll()) {
            Intent showNotification = new Intent(ACTION_VIEW);
            showNotification.putExtra(INTENT_REMINDER_ID, reminder.id);
            showNotification.putExtra(INTENT_REMINDER_TEXT, reminder.text);
            showNotification.putExtra(INTENT_RESPAWN, true);

            context.sendBroadcast(showNotification);
        }
    }

}
