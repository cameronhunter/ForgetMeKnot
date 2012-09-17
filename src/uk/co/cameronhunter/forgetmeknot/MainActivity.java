package uk.co.cameronhunter.forgetmeknot;

import static android.content.Intent.ACTION_INSERT;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    private static final String EMPTY_STRING = "";

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        final Random random = new Random();
        final String[] reminders = getResources().getStringArray( R.array.precanned_reminders );

        setContentView( R.layout.main );

        final EditText input = (EditText) findViewById( R.id.input );
        input.setHint( reminders[random.nextInt( reminders.length )] );

        Button save = (Button) findViewById( R.id.save );
        Button cancel = (Button) findViewById( R.id.cancel );

        save.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                String reminderText = input.getText().toString();
                if ( reminderText.length() > 0 ) {
                    Intent createReminder = new Intent( ACTION_INSERT );
                    createReminder.putExtra( getString( R.string.reminder_text ), reminderText );

                    input.setHint( reminders[random.nextInt( reminders.length )] );
                    input.setText( EMPTY_STRING );

                    sendBroadcast( createReminder );
                }
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
