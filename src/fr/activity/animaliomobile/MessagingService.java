package fr.activity.animaliomobile;

import fr.animaliomobile.R;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MessagingService extends Activity {

	private LinearLayout.LayoutParams param_sender;
	private LinearLayout.LayoutParams param_receiver;
	private RelativeLayout lay_message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messaging_service);
		
		lay_message = (RelativeLayout) findViewById(R.id.lay_message);
		
		LinearLayout lay_msgSender1 = new LinearLayout(this);
		LinearLayout lay_msgSender2 = new LinearLayout(this);
		LinearLayout lay_msgSender3 = new LinearLayout(this);
		LinearLayout lay_msgSender4 = new LinearLayout(this);
		LinearLayout lay_msgReceiver1 = new LinearLayout(this);
		LinearLayout lay_msgReceiver2 = new LinearLayout(this);
		LinearLayout lay_msgReceiver3 = new LinearLayout(this);
		
		lay_msgSender1.setId(1);
		lay_msgSender2.setId(2);
		lay_msgSender3.setId(3);
		lay_msgSender4.setId(4);
		lay_msgReceiver1.setId(5);
		lay_msgReceiver2.setId(6);
		lay_msgReceiver3.setId(7);
		
		lay_msgSender1.setBackgroundColor(Color.parseColor("#b7eeeb"));
		lay_msgSender2.setBackgroundColor(Color.parseColor("#b7eeeb"));
		lay_msgSender3.setBackgroundColor(Color.parseColor("#b7eeeb"));
		lay_msgSender4.setBackgroundColor(Color.parseColor("#b7eeeb"));
		lay_msgReceiver1.setBackgroundColor(Color.parseColor("#b7eec0"));
		lay_msgReceiver2.setBackgroundColor(Color.parseColor("#b7eec0"));
		lay_msgReceiver3.setBackgroundColor(Color.parseColor("#b7eec0"));
		
		TextView txv_msgSender1 = new TextView(this);
		TextView txv_msgSender2 = new TextView(this);
		TextView txv_msgSender3 = new TextView(this);
		TextView txv_msgSender4 = new TextView(this);
		TextView txv_msgReceiver1 = new TextView(this);
		TextView txv_msgReceiver2 = new TextView(this);
		TextView txv_msgReceiver3 = new TextView(this);
		
		txv_msgSender1.setText("Message envoyé 1 : Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam interdum purus in orci ultricies, sit amet porta justo aliquam. Proin.");
		txv_msgSender2.setText("Message envoyé 2 : Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum interdum orci in diam imperdiet laoreet. Curabitur id eleifend sem. Aliquam. ");
		txv_msgSender3.setText("Message envoyé 3 : Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent et mauris iaculis justo pellentesque tempus. Phasellus luctus quis nisl a. ");
		txv_msgSender4.setText("Message envoyé 4 : Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla imperdiet risus id viverra fringilla. Praesent in congue turpis. Maecenas faucibus. ");
		txv_msgReceiver1.setText("Message reçu 1 : Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut vel felis placerat, consectetur arcu eget, aliquet enim. Nulla a adipiscing. ");
		txv_msgReceiver2.setText("Message reçu 2 : Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas a elit arcu. Ut convallis eros eu urna hendrerit hendrerit. Praesent. ");
		txv_msgReceiver3.setText("Message reçu 3 : Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur facilisis gravida iaculis. Mauris risus nunc, blandit et lacus a, viverra. ");
		
		lay_msgSender1.addView(txv_msgSender1);
		lay_msgSender2.addView(txv_msgSender2);
		lay_msgSender3.addView(txv_msgSender3);
		lay_msgSender4.addView(txv_msgSender4);
		lay_msgReceiver1.addView(txv_msgReceiver1);
		lay_msgReceiver2.addView(txv_msgReceiver2);
		lay_msgReceiver3.addView(txv_msgReceiver3);
		
		RelativeLayout.LayoutParams placement1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		placement1.addRule(RelativeLayout.ALIGN_PARENT_TOP, lay_message.getId());
		placement1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, lay_message.getId());	
		placement1.setMargins(50, 20, 0, 20);
		lay_msgSender1.setLayoutParams(placement1);
		lay_message.addView(lay_msgSender1);
		
		RelativeLayout.LayoutParams placement2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		placement2.addRule(RelativeLayout.BELOW, lay_msgSender1.getId());
		placement2.addRule(RelativeLayout.ALIGN_PARENT_LEFT, lay_message.getId());
		placement2.setMargins(0, 20, 50, 20);
		lay_msgReceiver1.setLayoutParams(placement2);
		lay_message.addView(lay_msgReceiver1);

		RelativeLayout.LayoutParams placement3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		placement3.addRule(RelativeLayout.BELOW, lay_msgReceiver1.getId());
		placement3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, lay_message.getId());
		placement3.setMargins(50, 20, 0, 20);
		lay_msgSender2.setLayoutParams(placement3);
		lay_message.addView(lay_msgSender2);
		
		RelativeLayout.LayoutParams placement4 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		placement4.addRule(RelativeLayout.BELOW, lay_msgSender2.getId());
		placement4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, lay_message.getId());
		placement4.setMargins(50, 20, 0, 20);
		lay_msgSender3.setLayoutParams(placement4);
		lay_message.addView(lay_msgSender3);
		
		RelativeLayout.LayoutParams placement5 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		placement5.addRule(RelativeLayout.BELOW, lay_msgSender3.getId());
		placement5.addRule(RelativeLayout.ALIGN_PARENT_LEFT, lay_message.getId());
		placement5.setMargins(0, 20, 50, 20);
		lay_msgReceiver2.setLayoutParams(placement5);
		lay_message.addView(lay_msgReceiver2);
		
		RelativeLayout.LayoutParams placement6 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		placement6.addRule(RelativeLayout.BELOW, lay_msgReceiver2.getId());
		placement6.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, lay_message.getId());
		placement6.setMargins(50, 20, 0, 20);
		lay_msgSender4.setLayoutParams(placement6);
		lay_message.addView(lay_msgSender4);
		
		RelativeLayout.LayoutParams placement7 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		placement7.addRule(RelativeLayout.BELOW, lay_msgSender4.getId());
		placement7.addRule(RelativeLayout.ALIGN_PARENT_LEFT, lay_message.getId());
		placement7.setMargins(0, 20, 50, 20);
		lay_msgReceiver3.setLayoutParams(placement7);
		lay_message.addView(lay_msgReceiver3);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.messaging, menu);
		return true;
	}

}
