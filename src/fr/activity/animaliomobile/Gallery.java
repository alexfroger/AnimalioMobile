package fr.activity.animaliomobile;

import java.util.ArrayList;

import fr.animaliomobile.R;
import fr.library.animaliomobile.TypefaceSpan;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;


public class Gallery extends Activity{
	private int nbImg;
	private int nbLigne;
	private int nbImgLastLigne;
	private int nbImgPerLigne;
	private LinearLayout gallery;
	private LinearLayout.LayoutParams param_lay;
	private LinearLayout.LayoutParams param_img;
	private ArrayList<ImageView> images= new ArrayList<ImageView>();
	private ArrayList<Integer> id_image = new ArrayList<Integer>();
	private Button btn_members;
	private Button btn_gallery;
	private Button btn_profil;
	private Button btn_events;
	private Button btn_live;
	private Button btn_photo;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		nbImg = 20;
		gallery = (LinearLayout)findViewById(R.id.lay_gallery);


		for (int i = 0 ; i < nbImg ; i++){
			ImageView image = new ImageView(this);
			images.add(image);
			id_image.add(this.getResources().getIdentifier("image_gallery_"+i, "drawable", this.getPackageName()));
		}


		if(getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) 
		{
			// code to do for Portrait Mode
			createGallery(3);

		} else {
			// code to do for Landscape Mode
			createGallery(7);
		}

		btn_members = (Button)findViewById(R.id.btn_members);
		btn_gallery = (Button)findViewById(R.id.btn_gallery);
		btn_profil = (Button)findViewById(R.id.btn_profil);
		btn_events = (Button)findViewById(R.id.btn_events);
		btn_live = (Button)findViewById(R.id.btn_live);
		btn_photo = (Button)findViewById(R.id.btn_photo);

		btn_gallery.setBackgroundResource(R.drawable.ic_gallery_pressed);

		btn_members.setOnClickListener(eventClick);
		btn_profil.setOnClickListener(eventClick);
		btn_events.setOnClickListener(eventClick);
		btn_live.setOnClickListener(eventClick);
		btn_photo.setOnClickListener(eventClick);

	}

	//	Create Listener event
	OnClickListener eventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if(v==btn_members){
				//Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(), MembersList.class);
				startActivity(intent);
				finish();
			}

			else if(v==btn_profil){
				//Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(), Profiles.class);
				intent.putExtra("typeProfil", 0);
				startActivity(intent);
				finish();
			}

			else if(v==btn_events){
				//Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(), Events.class);
				startActivity(intent);
				finish();
			}

			else if(v==btn_live){
				//Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(), AnimalioLive.class);
				startActivity(intent);
				finish();
			}

			else if(v==btn_photo){
				//Display the photo phone application
				Toast t = Toast.makeText(Home.context,
						"Faire la redirection vers photo de l'appli",
						Toast.LENGTH_LONG);
				t.setGravity(Gravity.BOTTOM, 0, 40);
				t.show();
			}
		}
	};

	private void createGallery(int nbItemPerLigne){
		param_lay = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		param_img = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
		param_img.weight = 1.0f;
		nbImgPerLigne = nbItemPerLigne;
		nbLigne = nbImg / nbImgPerLigne;
		nbImgLastLigne = nbImg % nbImgPerLigne;

		int actualImg = 0;
		for(int i = 0 ; i < nbLigne ; i++){
			LinearLayout lay_ligne = new LinearLayout(this);
			lay_ligne.setOrientation(LinearLayout.HORIZONTAL);
			if(actualImg%2 == 0){lay_ligne.setBackgroundColor(Color.parseColor("#ffffff"));}
			for(int j = 0 ; j < nbImgPerLigne ; j++){
				if(actualImg%2 == 0){images.get(actualImg).setBackgroundColor(Color.parseColor("#000000"));}
				images.get(actualImg).setImageResource(id_image .get(actualImg));
				images.get(actualImg).setAdjustViewBounds(true);
				lay_ligne.addView(images.get(actualImg), param_img);
				actualImg++;
			}
			gallery.addView(lay_ligne, param_lay);
		}
		if(nbImgLastLigne != 0){
			LinearLayout lay_ligne = new LinearLayout(this);
			lay_ligne.setWeightSum(nbImgPerLigne);
			lay_ligne.setOrientation(LinearLayout.HORIZONTAL);
			for(int k = 0 ; k < nbImgLastLigne ; k++){
				images.get(actualImg).setImageResource(id_image .get(actualImg));
				images.get(actualImg).setAdjustViewBounds(true);
				lay_ligne.addView(images.get(actualImg), param_img);
				actualImg++;
			}
			gallery.addView(lay_ligne, param_lay);
		}
	}
	/**
	 * Gestion de l'action bar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_action_bar, menu);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			SpannableString s = new SpannableString(this.getTitle());
			s.setSpan(new TypefaceSpan(this, "Lobster"), 0, s.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			actionBar.setTitle(s);
			actionBar.setDisplayHomeAsUpEnabled(true);

		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			// Comportement du bouton "Logo"
			return true;
		case R.id.menu_refresh:
			// Comportement du bouton "Actualiser"
			Intent i = new Intent (getApplicationContext(), Gallery.class);
			startActivity(i);
			finish();
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
