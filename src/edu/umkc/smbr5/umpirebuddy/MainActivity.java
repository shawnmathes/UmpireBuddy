package edu.umkc.smbr5.umpirebuddy;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
    private static final String PREFS_NAME = "PrefsFile";
    private static final String TOTAL_OUTS_INDEX = "total_outs_index";
    
    private int strike_count;
    private int ball_count;
    private int total_outs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View strikeButton = findViewById(R.id.strike_button);
        strikeButton.setOnClickListener(this);

        View ballButton = findViewById(R.id.ball_button);
        ballButton.setOnClickListener(this);
        
        if (savedInstanceState != null) {
            total_outs = savedInstanceState.getInt(TOTAL_OUTS_INDEX, 0);
        } else {
            // Restore preferences
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            // The first time preferences will not exist and max
            // will be assigned 0 (the second argument)
            total_outs = settings.getInt(TOTAL_OUTS_INDEX, 0);
        }

        reset();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_about:
                openAbout();
                return true;
            case R.id.action_reset:
                reset();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(TOTAL_OUTS_INDEX, total_outs);
    }
    
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(TOTAL_OUTS_INDEX, total_outs);

        // Commit the edits
        editor.commit();
    }

    private void reset() {
        strike_count = 0;
        ball_count = 0;
        updateDisplay(); 
    }

    private void openAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.strike_button:
            if (strike_count < 2) {
                strike_count++;
            } else {
                total_outs++;
                alertAndReset(R.string.strike_out_text);
            }
            break;
        case R.id.ball_button:
            if (ball_count < 3) {
                ball_count++;
            } else {
                alertAndReset(R.string.walk_text);
            }
            break;
        }

        updateDisplay();
    }

    protected void alertAndReset(int messageId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder = builder.setMessage(messageId);
        builder = builder.setCancelable(false).setPositiveButton(
                R.string.alert_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        reset();
                    }
                });
        builder.show();
    }

    protected void updateDisplay() {
        TextView s = (TextView) findViewById(R.id.strike_textview);
        s.setText(Integer.toString(strike_count));

        TextView b = (TextView) findViewById(R.id.ball_textview);
        b.setText(Integer.toString(ball_count));
        
        TextView t = (TextView) findViewById(R.id.total_outs_textview);
        t.setText(Integer.toString(total_outs));
    }
}
