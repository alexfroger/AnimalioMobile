package fr.activity.animaliomobile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import fr.animaliomobile.R;
import fr.library.animaliomobile.TypefaceSpan;

public class Home extends Activity {
	private Button btnHomeMember;
	private Button btnHomeGallery;
	private Button btnHomeProfil;
	private Button btnHomeEvent;
	private Button btnHomeLive;
	private Button btnHomePhoto;
	private Button popupSettings;

	public static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);



		//Get different Object of the view
		context = getApplicationContext();
		btnHomeMember = (Button)findViewById(R.id.btn_home_member);
		btnHomeGallery = (Button)findViewById(R.id.btn_home_photo_gallery);
		btnHomeProfil = (Button)findViewById(R.id.btn_home_profil);
		btnHomeEvent = (Button)findViewById(R.id.btn_home_event);
		btnHomeLive = (Button)findViewById(R.id.btn_home_animalio_live);
		btnHomePhoto = (Button)findViewById(R.id.btn_photo);

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
				Intent intent = new Intent(getApplicationContext(), Gallery.class);
				startActivity(intent);
			}
			if(v==btnHomeProfil){
				//Display the Member Fragment Activity
				// On créé l'Intent qui va nous permettre d'afficher l'activity Mermber
				Intent intent = new Intent(getApplicationContext(), Profiles.class);
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
				// TODO Creer la redirection vers appli photo tel
				//Display the photo phone application
				Toast t = Toast.makeText(Home.context,
						"Faire la redirection vers photo de l'appli",
						Toast.LENGTH_LONG);
				t.setGravity(Gravity.BOTTOM, 0, 40);
				t.show();
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
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			actionBar.setCustomView(R.layout.title_bar);
			actionBar.setDisplayShowHomeEnabled(true);
			actionBar.setHomeButtonEnabled(true);
			TextView title = (TextView)findViewById(R.id.myTitle);
			title.setText(this.getTitle());
			Typeface Lobster = Typeface.createFromAsset(
					this.getAssets(), "Lobster.otf");
			title.setTypeface(Lobster);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// Comportement du bouton "Logo"
			return true;
		case R.id.menu_refresh:
			// Comportement du bouton "Rafraichir"
			return true;
		case R.id.menu_search:
			// Comportement du bouton "Recherche"
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
