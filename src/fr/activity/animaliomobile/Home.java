package fr.activity.animaliomobile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import fr.animaliomobile.R;

public class Home extends Activity {
	private Button btnHomeMember;
	private Button btnHomeGallery;
	private Button btnHomeProfil;
	private Button btnHomeEvent;
	private Button btnHomeLive;
	private Button btnHomePhoto;
	
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
				// On cr�� l'Intent qui va nous permettre d'afficher l'activity Mermber
				Intent intent = new Intent(Home.context, MembersList.class);
				startActivityForResult(intent, 0);
			}
			if(v==btnHomeGallery){
				// TODO Creer lactivit� Gallerie et la redirection vers celle-ci
				//Display the Member Fragment Activity
				// On cr�� l'Intent qui va nous permettre d'afficher l'activity Mermber
			}
			if(v==btnHomeProfil){
				//Display the Member Fragment Activity
				// On cr�� l'Intent qui va nous permettre d'afficher l'activity Mermber
				Intent intent = new Intent(Home.context, Profiles.class);
				startActivityForResult(intent, 0);
			}
			if(v==btnHomeEvent){
				//Display the Member Fragment Activity
				// On cr�� l'Intent qui va nous permettre d'afficher l'activity Mermber
				Intent intent = new Intent(Home.context, Events.class);
				startActivityForResult(intent, 0);
			}
			if(v==btnHomeLive){
				//Display the Member Fragment Activity
				// On cr�� l'Intent qui va nous permettre d'afficher l'activity Mermber
				Intent intent = new Intent(Home.context, AnimalioLive.class);
				startActivityForResult(intent, 0);
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
		getMenuInflater().inflate(R.menu.main_action_bar, menu);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case android.R.id.home:
			onBackPressed();
    	case R.id.menu_about:
    		// Comportement du bouton "A Propos"
    		return true;
    	case R.id.menu_help:
    		// Comportement du bouton "Aide"
    		return true;
    	case R.id.menu_refresh:
    		// Comportement du bouton "Rafraichir"
    		return true;
    	case R.id.menu_search:
    		// Comportement du bouton "Recherche"
    		return true;
    	case R.id.menu_settings:
    		// Comportement du bouton "Param�tres"
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
}