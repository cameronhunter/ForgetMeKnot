package uk.co.cameronhunter.forgetmeknot;

import java.util.List;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

public class MainActivity extends SherlockListActivity {

    private Reminders data;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        setTheme( R.style.Sherlock___Theme_DarkActionBar );
        super.onCreate( savedInstanceState );

        NotificationManager notificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE );

        data = new Reminders( this );
        data.deleteAll();

        notificationManager.cancelAll();

        List<Reminder> reminders = data.getAll();

        ListAdapter adapter = new ArrayAdapter<Reminder>( this, android.R.layout.simple_list_item_1, reminders );

        setListAdapter( adapter );

    }

    private void createNotificationFor( Reminder reminder ) {
        Builder builder = new NotificationCompat.Builder( this );

        builder.setSmallIcon( R.drawable.logo2 )
               .setContentText( reminder.text )
               .setContentTitle( getResources().getText(  R.string.reminder_title ) )
               .setTicker( getResources().getText( R.string.reminder_ticker ) );

        NotificationManager notificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE );
        notificationManager.notify( 1, builder.getNotification() );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuItem addMenuItem = menu.add( "Add reminder" );
        addMenuItem.setIcon( android.R.drawable.ic_input_add );
        addMenuItem.setShowAsAction( MenuItem.SHOW_AS_ACTION_ALWAYS );

        final ArrayAdapter<Reminder> adapter = (ArrayAdapter<Reminder>) getListAdapter();
        addMenuItem.setOnMenuItemClickListener( new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick( MenuItem item ) {
                Reminder reminder = new Reminder( "Test reminder " + System.currentTimeMillis() );
                data.add( reminder );
                adapter.add( reminder );

                createNotificationFor( reminder );

                return true;
            }
        } );

        return true;
    }

}
