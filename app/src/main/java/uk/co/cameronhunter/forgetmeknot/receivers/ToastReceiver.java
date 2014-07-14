package uk.co.cameronhunter.forgetmeknot.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import uk.co.cameronhunter.forgetmeknot.R;

import static android.content.Intent.ACTION_VIEW;
import static uk.co.cameronhunter.forgetmeknot.MainActivity.INTENT_RESPAWN;

public class ToastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null) return;
        if (intent.getBooleanExtra(INTENT_RESPAWN, false)) return;

        int message = ACTION_VIEW.equals(intent.getAction()) ? R.string.reminder_added : R.string.reminder_deleted;

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
