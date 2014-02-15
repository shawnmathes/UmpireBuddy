package edu.umkc.smbr5.umpirebuddy;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private int strike_count = 0;
	private int ball_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        View strikeButton = findViewById(R.id.strike_button);
        strikeButton.setOnClickListener(this);
        
        View ballButton = findViewById(R.id.ball_button);
        ballButton.setOnClickListener(this);
        
        updateDisplay();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onClick(View v) {
    	switch (v.getId()) {
		case R.id.strike_button:
			if (strike_count < 2) {
				strike_count++;
			} else {
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
		builder = builder.setCancelable(false)
				.setPositiveButton(R.string.alert_confirm,  new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
					     strike_count = 0;
					     ball_count = 0;
		        	     updateDisplay();
		           }
		       });
		builder.show();
    }

	protected void updateDisplay() {
		TextView s = (TextView)findViewById(R.id.strike_textview);
		s.setText(Integer.toString(strike_count));
		
		TextView b = (TextView)findViewById(R.id.ball_textview);
		b.setText(Integer.toString(ball_count));
	}
}
