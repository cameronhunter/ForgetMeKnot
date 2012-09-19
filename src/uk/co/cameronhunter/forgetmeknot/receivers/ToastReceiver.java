package uk.co.cameronhunter.forgetmeknot.receivers;

import uk.co.cameronhunter.forgetmeknot.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ToastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive( Context context, Intent intent ) {
        Toast toast = Toast.makeText( context, R.string.reminder_ticker, Toast.LENGTH_LONG );
        toast.show();
    }

}
