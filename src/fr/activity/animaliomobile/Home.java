package fr.activity.animaliomobile;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import fr.animaliomobile.R;
import fr.library.animaliomobile.ConnectionWebservicePHP;
import fr.library.animaliomobile.ConnectionWebservicePHPProfile;
import fr.library.animaliomobile.TypefaceSpan;

public class Home extends Activity {
	private Button btnHomeMember;
	private Button btnHomeGallery;
	private Button btnHomeProfil;
	private Button btnHomeEvent;
	private Button btnHomeLive;
	private ImageView btnHomePhoto;
	private Button popupSettings;
	private static ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	public static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		//On Récupére les préférences utilisateur si elle existe et d'autres info
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		
		String idUser = preferences.getString("idUser", "");
		Boolean doUpdateUserInfo = preferences.getBoolean("doUpdateUserInfo", true);
		String email = preferences.getString("email", "");
		String updatedAt = preferences.getString("updatedAt", "");
		Boolean isRegister = preferences.getBoolean("isRegister", false);
		
		data.add(new BasicNameValuePair("idUser", idUser));
		data.add(new BasicNameValuePair("email", email));
		data.add(new BasicNameValuePair("updatedAt", updatedAt));
		
		//A supprimer
		Log.i("log_parseHome", "idUser : " + preferences.getString("idUser", ""));
		Log.i("log_parseHome", "email : " + preferences.getString("email", ""));
		Log.i("log_parseHome", "humorID : " + preferences.getString("humorID", ""));
		Log.i("log_parseHome", "cityID : " + preferences.getString("cityID", ""));
		Log.i("log_parseHome", "countryID : " + preferences.getString("countryID", ""));
		Log.i("log_parseHome", "lastname : " + preferences.getString("lastname", ""));
		Log.i("log_parseHome", "firstname : " + preferences.getString("firstname", ""));
		Log.i("log_parseHome", "birthday : " + preferences.getString("birthday", ""));
		Log.i("log_parseHome", "updatedAt : " + preferences.getString("updatedAt", ""));
		
		// On test si les informations de profil on changé
		if (ConnectionWebservicePHP.haveNetworkConnection(this)) { // Si connexion existe
			if(doUpdateUserInfo){
				ConnectionWebservicePHP calcul = new ConnectionWebservicePHP(
						1, "refreshInfoUser", this, data);
				calcul.execute();
				
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean("isRegister", false);
				editor.commit();
			}
		} else { // Sinon toast de problème
			ConnectionWebservicePHP.haveNetworkConnectionError(this);
		}
		
		//Si on vient de s'inscrire
        if (isRegister == true){
        	Toast.makeText(this, "Incription réussi!",
					Toast.LENGTH_LONG).show();
        	SharedPreferences.Editor editor = preferences.edit();
			editor.putBoolean("doUpdateUserInfo", false);
			editor.commit();
        }
        
		//Get different Object of the view
		context = getApplicationContext();
		btnHomeMember = (Button)findViewById(R.id.btn_home_member);
		btnHomeGallery = (Button)findViewById(R.id.btn_home_photo_gallery);
		btnHomeProfil = (Button)findViewById(R.id.btn_home_profil);
		btnHomeEvent = (Button)findViewById(R.id.btn_home_event);
		btnHomeLive = (Button)findViewById(R.id.btn_home_animalio_live);
		btnHomePhoto = (ImageView)findViewById(R.id.btn_photo);

		//Buttons are assigned to the event listener
		btnHomeMember.setOnClickListener(eventClick);
		btnHomeGallery.setOnClickListener(eventClick);
		btnHomeProfil.setOnClickListener(eventClick);
		btnHomeEvent.setOnClickListener(eventClick);
		btnHomeLive.setOnClickListener(eventClick);
		btnHomePhoto.setOnClickListener(eventClick);
	}

	//	Create Listener event
	OnClickListener eventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if(v==btnHomeMember){
				//Display the Member Fragment Activity
				// On créé l'Intent qui va nous permettre d'afficher l'activity Mermber
				Intent intent = new Intent(getApplicationContext(), MembersList.class);
				startActivity(intent);
			}
			if(v==btnHomeGallery){

				// On créé l'Intent qui va nous permettre d'afficher l'activity Gallery
				//Marche pas
//				Intent intent = new Intent(getApplicationContext(), Gallery.class);
//				startActivity(intent);
			}
			if(v==btnHomeProfil){
				//Display the Member Fragment Activity
				// On créé l'Intent qui va nous permettre d'afficher l'activity Mermber
					Intent intent = new Intent(v.getContext(), Profiles.class);
					intent.putExtra("typeProfil", 0);
					startActivity(intent);
			}
			if(v==btnHomeEvent){
				//Display the Member Fragment Activity
				// On créé l'Intent qui va nous permettre d'afficher l'activity Mermber
				Intent intent = new Intent(getApplicationContext(), Events.class);
				startActivity(intent);
			}
			if(v==btnHomeLive){
				//Display the Member Fragment Activity
				// On créé l'Intent qui va nous permettre d'afficher l'activity Mermber
				Intent intent = new Intent(getApplicationContext(), AnimalioLive.class);
				startActivity(intent);
			}
			if(v==btnHomePhoto){
				//Display the photo phone application
//				Toast t = Toast.makeText(Home.context,
//						"Faire la redirection vers photo de l'appli",
//						Toast.LENGTH_LONG);
//				t.setGravity(Gravity.BOTTOM, 0, 40);
//				t.show();
				
				Intent intent = new Intent(getApplicationContext(), ImageUpload.class);
				startActivity(intent);
			}
		}
	};
	
	/**
	 * Gestion de l'action bar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_action_bar, menu);

		popupSettings = (Button) findViewById(R.id.menu_settings);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			SpannableString s = new SpannableString(this.getTitle());
			s.setSpan(new TypefaceSpan(this, "Lobster"), 0, s.length(),
			        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			actionBar.setTitle(s);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// Comportement du bouton "Logo"
			return true;
		case R.id.menu_settings:
			// Comportement du bouton "Paramètres"
			return true;
		case R.id.menu_delete:
			// Comportement du bouton "Delete" A supprimer quand le popup parametre sera creer car dedans

			// On créé l'Intent qui va nous permettre d'afficher l'autre Activity
			Intent intent = new Intent(getApplicationContext(), Authentication.class);
			// On supprime l'activity de login sinon
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);  

			//Puis on reset les informations utilisateurs
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			SharedPreferences.Editor editor = preferences.edit();
			editor.clear();	
			editor.commit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
