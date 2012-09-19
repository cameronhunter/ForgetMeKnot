package uk.co.cameronhunter.forgetmeknot;

import static android.content.Intent.ACTION_DELETE;
import static android.content.Intent.ACTION_EDIT;
import static android.content.Intent.ACTION_INSERT_OR_EDIT;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import uk.co.cameronhunter.forgetmeknot.data.Reminder;
import uk.co.cameronhunter.forgetmeknot.data.Reminders;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setTitle( getString( R.string.reminder_hint ) );

        final Random random = new Random();
        final String[] placeholders = getResources().getStringArray( R.array.precanned_reminders );

        setContentView( R.layout.main );

        final EditText input = (EditText) findViewById( R.id.input );
        Button save = (Button) findViewById( R.id.save );
        Button delete = (Button) findViewById( R.id.delete );
        Button cancel = (Button) findViewById( R.id.cancel );

        Intent intent = getIntent();
        final AtomicLong reminderId = new AtomicLong( intent.getLongExtra( getString( R.string.reminder_id ), -1 ) );

        if ( ACTION_EDIT.equals( intent.getAction() ) ) {
            Reminders data = new Reminders( this );
            Reminder reminder = data.find( reminderId.get() );
            if ( reminder != null ) {
                save.setText( getString( R.string.update ) );
                delete.setVisibility( Button.VISIBLE );
                input.setText( reminder.text );
                input.setSelectAllOnFocus( true );
            }
        }

        input.setHint( placeholders[random.nextInt( placeholders.length )] );

        save.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                String reminderText = input.getText().toString();
                if ( reminderText.length() > 0 ) {
                    Intent createOrUpdateReminder = new Intent( ACTION_INSERT_OR_EDIT );

                    createOrUpdateReminder.putExtra( getString( R.string.reminder_id ), reminderId.get() );
                    createOrUpdateReminder.putExtra( getString( R.string.reminder_text ), reminderText );

                    sendBroadcast( createOrUpdateReminder );

                    finish();
                }
            }
        } );

        delete.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent deleteIntent = new Intent( ACTION_DELETE );
                deleteIntent.putExtra( getString( R.string.reminder_id ), reminderId.get() );
                
                sendBroadcast( deleteIntent );
                
                finish();
            }
        } );
        
        cancel.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                finish();
            }
        } );
    }

}
