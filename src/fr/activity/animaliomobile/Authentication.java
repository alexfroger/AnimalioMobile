package fr.activity.animaliomobile;

import android.app.ActionBar;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import fr.animaliomobile.R;
import fr.library.animaliomobile.ConnectionDialog;
import fr.library.animaliomobile.RegistrationDialog;

public class Authentication extends FragmentActivity{
	private Button btnShowConnection;
	private Button btnShowRegistration;
	private Button buttonNotif;
	public static Context context;
	private FragmentManager fm;
	private ConnectionDialog connectionDialog;
	private RegistrationDialog registrationDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		// Hide the action bar
//		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//		getActionBar().hide();
	    
		setContentView(R.layout.authentication);
		
		//Recover the object ressources
		context = getApplicationContext();
		btnShowConnection = (Button)findViewById(R.id.connect);
		btnShowRegistration = (Button)findViewById(R.id.registration);
		//buttonNotif = (Button)findViewById(R.id.notif);
		
		//Buttons are assigned to the event listener
		btnShowConnection.setOnClickListener(eventClick);
		btnShowRegistration.setOnClickListener(eventClick);
		//buttonNotif.setOnClickListener(eventClick);
	}
	
	/**
	 * Display the connection popup (using ConnectionDialog)
	 */
	private void showConnectionDialog() {
		fm = getSupportFragmentManager();
		connectionDialog = new ConnectionDialog();
		connectionDialog.show(fm, "fragment_authentication_connection");
	}
	
	/**
	 * Display the registration popup (using ConnectionDialog)
	 */
	private void showRegistrationDialog() {
		fm = getSupportFragmentManager();
		registrationDialog = new RegistrationDialog();
		registrationDialog.show(fm, "fragment_authentication_registration");
	}
	
	//	Create Listener event
    OnClickListener eventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			if(v==btnShowConnection){
				//Display the ConnectionDialog View
				showConnectionDialog();
			}
			if(v==btnShowRegistration){
				//Display the RegistrationDialog View
				showRegistrationDialog();
			}
//			if(v==buttonNotif){
//				CustomNotification notif = new CustomNotification((NotificationManager)getSystemService(NOTIFICATION_SERVICE), Authentication.context, "test", "test");
//				notif.addNotification();
//			}
		}
    };
    
    /**
     * Gestion de l'action bar
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_action_bar, menu);
		return true;
	}
}
