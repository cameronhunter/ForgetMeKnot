package uk.co.cameronhunter.forgetmeknot;

import static android.content.Intent.ACTION_INSERT;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends Activity {

    private static final String EMPTY_STRING = "";

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.main );

        EditText input = (EditText) findViewById( R.id.input );
        input.setOnEditorActionListener( new OnEditorActionListener() {
            @Override
            public boolean onEditorAction( TextView v, int actionId, KeyEvent event ) {
                if ( EditorInfo.IME_ACTION_DONE != actionId ) return false;

                String reminderText = v.getText().toString();
                if ( reminderText != EMPTY_STRING ) {
                    Intent createReminder = new Intent( ACTION_INSERT );
                    createReminder.putExtra( getString( R.string.reminder_text ), reminderText );
                    
                    v.setText( EMPTY_STRING );
                    
                    sendBroadcast( createReminder );
                    
                    return true;
                }

                return false;
            }
        } );
    }

}
