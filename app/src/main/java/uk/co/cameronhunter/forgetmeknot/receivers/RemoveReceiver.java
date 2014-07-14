package uk.co.cameronhunter.forgetmeknot.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import uk.co.cameronhunter.forgetmeknot.data.Reminders;

import static uk.co.cameronhunter.forgetmeknot.MainActivity.INTENT_REMINDER_ID;

public class RemoveReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null) return;
        long reminderId = intent.getLongExtra(INTENT_REMINDER_ID, -1);
        new Reminders(context).delete(reminderId);
    }

}
