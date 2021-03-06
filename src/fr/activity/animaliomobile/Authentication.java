package fr.activity.animaliomobile;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import fr.animaliomobile.R;
import fr.library.animaliomobile.ConnectionDialog;
import fr.library.animaliomobile.RegistrationDialog;

public class Authentication extends FragmentActivity{
	private static String densite = "";
	private static String resolution = "";
	private Button btnShowConnection;
	private Button btnShowRegistration;
	private Button buttonNotif;
	public static Context context;
	private FragmentManager fm;
	private ConnectionDialog connectionDialog;
	private RegistrationDialog registrationDialog;
	private static ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		// Hide the action bar
//		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//		getActionBar().hide();
	    
		//On R�cup�re les pr�f�rences utilisateur si elle existe
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		String idUser = preferences.getString("idUser", "");
		String email = preferences.getString("email", "");
		
		//Test a supprimer
		Log.i("log_parseAuth", "idUser : " + idUser);
		Log.i("log_parseAuth", "email : " + email);
		Log.i("log_parseAuth", "humorID : " + preferences.getString("humorID", ""));
		Log.i("log_parseAuth", "cityID : " + preferences.getString("cityID", ""));
		Log.i("log_parseAuth", "countryID : " + preferences.getString("countryID", ""));
		Log.i("log_parseAuth", "lastname : " + preferences.getString("lastname", ""));
		Log.i("log_parseAuth", "firstname : " + preferences.getString("firstname", ""));
		Log.i("log_parseAuth", "birthday : " + preferences.getString("birthday", ""));
		Log.i("log_parseAuth", "createdAt : " + preferences.getString("createdAt", ""));

		//Si l'utilisateur est tjr connect� c'est a dire que c'est pr�f�rence existe on va direct � la home
		if(!idUser.equals("") && !email.equals("")){
			SharedPreferences.Editor editor = preferences.edit();
			editor.putBoolean("doUpdateUserInfo", true);
			editor.commit();
			
			Intent i = new Intent(getApplicationContext(), Home.class);
			startActivity(i);
			this.finish();
		}else{ //Sinon on se connecte
			setContentView(R.layout.authentication);
			
			Display display = getWindowManager().getDefaultDisplay(); 
			int width = display.getWidth();  // deprecated
			int height = display.getHeight();  // deprecated
			resolution =  width+"x"+height;
			
			//Recup�re la densit� de l'ecran utilisateur
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			switch(dm.densityDpi){
			case DisplayMetrics.DENSITY_LOW:
				densite = "ldpi";
				break;
			case DisplayMetrics.DENSITY_MEDIUM:
				densite = "mdpi";
				break;
			case DisplayMetrics.DENSITY_HIGH:
				densite = "hdpi";
				break;
			case DisplayMetrics.DENSITY_TV :
				densite = "tvdpi";
				break;
			case DisplayMetrics.DENSITY_XHIGH:
				densite = "xhdpi";
				break;
			case 480: //DENSITY_XXHIGH
				densite = "xxhdpi";
				break;
			}
			
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
	}

	/**
	 * Remplit la variable data des informations du p�riph�rique utilisateur
	 */
	private void remplirData(){
		data.add(new BasicNameValuePair("transmit", "ani_mobile"));
		data.add(new BasicNameValuePair("os", "Android"));
		data.add(new BasicNameValuePair("version", Build.VERSION.RELEASE));
		data.add(new BasicNameValuePair("marque", Build.MANUFACTURER));		
		data.add(new BasicNameValuePair("modele", Build.MODEL));
		data.add(new BasicNameValuePair("densite", Authentication.densite));
		data.add(new BasicNameValuePair("resolution", Authentication.resolution));
	}

	/**
	 * Display the connection popup (using ConnectionDialog)
	 */
	private void showConnectionDialog() {
		fm = getSupportFragmentManager();
		connectionDialog = new ConnectionDialog();
		connectionDialog.show(fm, "fragment_authentication_connection");
	}
	
	//	Create Listener event
    OnClickListener eventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			if(v==btnShowConnection){
				//Display the ConnectionDialog View
				showConnectionDialog();
				remplirData();
			}
			if(v==btnShowRegistration){
				//Display the RegistrationDialog View
				//showRegistrationDialog();
				Intent i = new Intent(getApplicationContext(), Registration.class);
				startActivity(i);
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
