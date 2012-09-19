package uk.co.cameronhunter.forgetmeknot.receivers;

import static android.content.Intent.ACTION_VIEW;
import uk.co.cameronhunter.forgetmeknot.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ToastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive( Context context, Intent intent ) {
        int message =  ACTION_VIEW.equals( intent.getAction() ) ? R.string.reminder_added : R.string.reminder_deleted;
        
        Toast toast = Toast.makeText( context, message, Toast.LENGTH_LONG );
        toast.show();
    }
}
