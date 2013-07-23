package fr.activity.animaliomobile;

import android.app.ActionBar.LayoutParams;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import fr.animaliomobile.R;
import fr.animaliomobile.R.id;
import fr.library.animaliomobile.TypefaceSpan;

public class Profiles extends Activity{
	private Button btn_animals_list;
	private Button btn_friends_list;
	private Button btn_notifications_list;
	private Button btn_delete_friend;
	private Button btn_send_msg;
	private Button btn_friends_requests;
	private Button btn_delete_animal;
	private Button btn_animal_modification;
	private Button btn_msg;
	private Button btn_user_modification;
	private Button btn_galerie;
	private ViewFlipper vf_profil;
	private static int typeProfil;
	private static ListView.LayoutParams param_lsv;
	private int[] positionChild = new int[] {0,0,0,0,0,0,0,0,0,0};
	// 0-Messagerie 1-ListeAnimaux 2-ListeAmis 3-ListeNotification 4-FriendRequest
	// 5-EnvoyerMessage 6-DeleteFriend 7-UserModification 8-AnimalModification 9-DeleteAnimal
	private Button btn_members;
	private Button btn_gallery;
	private Button btn_profil;
	private Button btn_events;
	private Button btn_live;
	private Button btn_photo;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_profiles);

		Bundle extra = getIntent().getExtras();
		typeProfil = extra.getInt("typeProfil");

		vf_profil = (ViewFlipper) findViewById(R.id.vf_profil);
		ListView.LayoutParams param_lsv = new ListView.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);



		switch (typeProfil) {
		case 0: // Profil utilisateur

			/*
			 * Création des boutons
			 */

			// Bouton Galerie
			btn_galerie = (Button)findViewById(R.id.btn_galerie);
			btn_galerie.setVisibility(View.VISIBLE);
			btn_galerie.setOnClickListener(eventClick);
			// Bouton Messagerie
			btn_msg = (Button) findViewById(R.id.btn_msg);
			btn_msg.setVisibility(View.VISIBLE);
			btn_msg.setOnClickListener(eventClick);
			// Bouton Liste animaux
			btn_animals_list = (Button) findViewById(R.id.btn_animals_list);
			btn_animals_list.setVisibility(View.VISIBLE);
			btn_animals_list.setOnClickListener(eventClick);
			// Bouton Liste amis
			btn_friends_list = (Button) findViewById(R.id.btn_friends_list);
			btn_friends_list.setVisibility(View.VISIBLE);
			btn_friends_list.setOnClickListener(eventClick);
			// Bouton Liste des notification
			btn_notifications_list = (Button) findViewById(R.id.btn_notifications_list);
			btn_notifications_list.setVisibility(View.VISIBLE);
			btn_notifications_list.setOnClickListener(eventClick);
			// Bouton Modifier
			btn_user_modification = (Button) findViewById(R.id.btn_user_modification);
			btn_user_modification.setVisibility(View.VISIBLE);
			btn_user_modification.setOnClickListener(eventClick);

			/*
			 * Création des éléments du ViewFlipper
			 */

			vf_profil.addView(createListMsg(), param_lsv);
			positionChild[0]=0;

			vf_profil.addView(createListAnimals(), param_lsv);
			positionChild[1]=1;

			vf_profil.addView(createListFriends(), param_lsv);
			positionChild[2]= 2;

			vf_profil.addView(createListNotifications(), param_lsv);
			positionChild[3]=3;

			vf_profil.addView(createLayout("Modifier le profil"), param_lsv);
			positionChild[7] = 4;

			break;

		case 1: // Profil membre

			/*
			 * Création des boutons
			 */

			// Bouton Galerie
			btn_galerie = (Button)findViewById(R.id.btn_galerie);
			btn_galerie.setVisibility(View.VISIBLE);
			btn_galerie.setOnClickListener(eventClick);
			// Bouton Liste animaux
			btn_animals_list = (Button) findViewById(R.id.btn_animals_list);
			btn_animals_list.setVisibility(View.VISIBLE);
			btn_animals_list.setOnClickListener(eventClick);
			// Bouton Liste amis
			btn_friends_list = (Button) findViewById(R.id.btn_friends_list);
			btn_friends_list.setVisibility(View.VISIBLE);
			btn_friends_list.setOnClickListener(eventClick);
			// Bouton Demande d'ami
			btn_friends_requests = (Button) findViewById(R.id.btn_friends_requests);
			btn_friends_requests.setVisibility(View.VISIBLE);
			btn_friends_requests.setOnClickListener(eventClick);

			/*
			 * Création des éléments du ViewFlipper
			 */

			vf_profil.addView(createListAnimals(), param_lsv);
			positionChild[1] = 0;

			vf_profil.addView(createListFriends(), param_lsv);
			positionChild[2] = 1;

			vf_profil.addView(createLayout("Envoyer une demande d'ami"), param_lsv);
			positionChild[4] = 2;

			break;
		case 2: // Profil ami

			/*
			 * Création des boutons
			 */

			// Bouton Galerie
			btn_galerie = (Button)findViewById(R.id.btn_galerie);
			btn_galerie.setVisibility(View.VISIBLE);
			btn_galerie.setOnClickListener(eventClick);
			// Bouton Envoyer message
			btn_send_msg = (Button) findViewById(R.id.btn_send_msg);
			btn_send_msg.setVisibility(View.VISIBLE);
			btn_send_msg.setOnClickListener(eventClick);
			// Bouton Liste animaux
			btn_animals_list = (Button) findViewById(R.id.btn_animals_list);
			btn_animals_list.setVisibility(View.VISIBLE);
			btn_animals_list.setOnClickListener(eventClick);
			// Bouton Liste ami
			btn_friends_list = (Button) findViewById(R.id.btn_friends_list);
			btn_friends_list.setVisibility(View.VISIBLE);
			btn_friends_list.setOnClickListener(eventClick);
			// Bouton Liste des nofication
			btn_notifications_list = (Button) findViewById(R.id.btn_notifications_list);
			btn_notifications_list.setVisibility(View.VISIBLE);
			btn_notifications_list.setOnClickListener(eventClick);
			// Bouton Supprimer de la liste d'amis
			btn_delete_friend = (Button) findViewById(R.id.btn_delete_friend);
			btn_delete_friend.setVisibility(View.VISIBLE);
			btn_delete_friend.setOnClickListener(eventClick);

			/*
			 * Création des éléments du ViewFlipper
			 */

			vf_profil.addView(createLayout("Envoyer un message"), param_lsv);
			positionChild[5] = 0;

			vf_profil.addView(createListAnimals(), param_lsv);
			positionChild[1]=1;

			vf_profil.addView(createListFriends(), param_lsv);
			positionChild[2]= 2;

			vf_profil.addView(createListNotifications(), param_lsv);
			positionChild[3]=3;

			vf_profil.addView(createLayout("Supprimer de la liste d'ami"), param_lsv);
			positionChild[6] = 4;

			break;
		case 3: //Profil animal

			/*
			 * Création des boutons
			 */

			// Bouton Galerie
			btn_galerie = (Button)findViewById(R.id.btn_galerie);
			btn_galerie.setVisibility(View.VISIBLE);
			btn_galerie.setOnClickListener(eventClick);
			// Bouton modifier animal
			btn_animal_modification = (Button) findViewById(R.id.btn_animal_modification);
			btn_animal_modification.setVisibility(View.VISIBLE);
			btn_animal_modification.setOnClickListener(eventClick);
			// Bouton Supprimer animal
			btn_delete_animal = (Button) findViewById(R.id.btn_delete_animal);
			btn_delete_animal.setVisibility(View.VISIBLE);
			btn_delete_animal.setOnClickListener(eventClick);

			/*
			 * Création des éléments du ViewFlipper
			 */

			vf_profil.addView(createLayout("Modifier l'animal"), param_lsv);
			positionChild[8] = 0;

			vf_profil.addView(createLayout("Supprimer l'animal"), param_lsv);
			positionChild[9] = 1;

			break;
		}

		btn_members = (Button)findViewById(R.id.btn_members);
		btn_gallery = (Button)findViewById(R.id.btn_gallery);
		btn_profil = (Button)findViewById(R.id.btn_profil);
		btn_events = (Button)findViewById(R.id.btn_events);
		btn_live = (Button)findViewById(R.id.btn_live);
		btn_photo = (Button)findViewById(R.id.btn_photo);

		btn_profil.setBackgroundResource(R.drawable.ic_profil_pressed);

		btn_members.setOnClickListener(eventClick);
		btn_gallery.setOnClickListener(eventClick);
		btn_events.setOnClickListener(eventClick);
		btn_live.setOnClickListener(eventClick);
		btn_photo.setOnClickListener(eventClick);
	}

	OnClickListener eventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if(v==btn_msg){
				vf_profil.setDisplayedChild(positionChild[0]);
			}
			else if(v==btn_animals_list){
				vf_profil.setDisplayedChild(positionChild[1]);
			}
			else if(v==btn_friends_list){
				vf_profil.setDisplayedChild(positionChild[2]);
			}
			else if(v==btn_notifications_list){
				vf_profil.setDisplayedChild(positionChild[3]);
			}
			else if(v==btn_friends_requests){
				vf_profil.setDisplayedChild(positionChild[4]);
			}
			else if(v==btn_send_msg){
				vf_profil.setDisplayedChild(positionChild[5]);
			}
			else if(v==btn_delete_friend){
				vf_profil.setDisplayedChild(positionChild[6]);
			}
			else if(v==btn_user_modification){
				vf_profil.setDisplayedChild(positionChild[7]);
			}
			else if(v==btn_animal_modification){
				vf_profil.setDisplayedChild(positionChild[8]);
			}
			else if(v==btn_delete_animal){
				vf_profil.setDisplayedChild(positionChild[9]);
			}else if(v==btn_members){
				//Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(), MembersList.class);
				startActivity(intent);
				finish();
			}

			else if(v==btn_gallery){
				//Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(), Gallery.class);
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

	ListView createListMsg(){
		final ListView lsv_msg = new ListView(this);
		String[] values_msg = new String[] { "Message1", "Message2", "Message3", "Message4", "Message5", "Message6",
				"Message7", "Message8", "Message9", "Message10"};
		ArrayAdapter<String> adapter_msg = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values_msg);
		lsv_msg.setAdapter(adapter_msg);
		//Ajout d'un onclick listener sur chaque element
		lsv_msg.setOnItemClickListener(new OnItemClickListener() 
		{
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
			{
				Intent intent = new Intent(getApplicationContext(), MessagingService.class);
				startActivity(intent);
			}
		});

		return lsv_msg;
	}

	ListView createListAnimals(){
		final ListView lsv_animals_list = new ListView(this);
		String[] values_animals_list = new String[] { "Animal1", "Animal2", "Animal3", "Animal4", "Animal5", "Animal6", "Animal7", "Animal8", "Animal9", "Animal10"};
		ArrayAdapter<String> adapter_animals_list = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values_animals_list);
		lsv_animals_list.setAdapter(adapter_animals_list);
		//Ajout d'un onclick listener sur chaque element
		lsv_animals_list.setOnItemClickListener(new OnItemClickListener() 
		{
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
			{
				Intent intent = new Intent(getApplicationContext(), Profiles.class);
				if(typeProfil==0){
					intent.putExtra("typeProfil", 3);
				}else{
					intent.putExtra("typeProfil", 3); // A CHANGER QUAND LE PROFIL ANIMAL AMI SERA CREE
				}				
				startActivity(intent);
			}
		});
		return lsv_animals_list;
	}

	ListView createListFriends(){
		final ListView lsv_friends_list = new ListView(this);
		String[] values_friends_list = new String[] { "Friend1", "Friend2", "Friend3", "Friend4", "Friend5", "Friend6", "Friend7", "Friend8", "Friend9", "Friend10" };
		ArrayAdapter<String> adapter_friends_list = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values_friends_list);
		lsv_friends_list.setAdapter(adapter_friends_list);
		//Ajout d'un onclick listener sur chaque element
		lsv_friends_list.setOnItemClickListener(new OnItemClickListener() 
		{
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
			{
				// A AJOUTER UN TEST POUR VERIFIER S'IL S'AGIT D'UN AMI OU NON
				Intent intent = new Intent(getApplicationContext(), Profiles.class);
				intent.putExtra("typeProfil", 2);
				startActivity(intent);
			}
		});
		return lsv_friends_list;
	}

	ListView createListNotifications(){
		final ListView lsv_notifications_list = new ListView(this);
		String[] values_notifications_list = new String[] { "Notification1", "Notification2", "Notification3", "Notification4", "Notification5", "Notification6", "Notification7", "Notification8", "Notification9", "Notification10", };
		ArrayAdapter<String> adapter_notifications_list = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values_notifications_list);
		lsv_notifications_list.setAdapter(adapter_notifications_list);
		//Ajout d'un onclick listener sur chaque element
		lsv_notifications_list.setOnItemClickListener(new OnItemClickListener() 
		{
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
			{
				Intent intent = new Intent(getApplicationContext(), Details.class);
				startActivity(intent);
			}
		});
		return lsv_notifications_list;
	}

	LinearLayout createLayout(String text){
		LinearLayout.LayoutParams param_txv= new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		LinearLayout layout = new LinearLayout(this);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		TextView txv = new TextView(this);
		txv.setText(text);
		txv.setTextColor(getResources().getColor(R.color.black_color));
		txv.setBackgroundColor(Color.CYAN);
		param_txv.gravity=Gravity.CENTER;
		txv.setLayoutParams(param_txv);
		layout.addView(txv);

		return layout;
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
			Intent i = new Intent (getApplicationContext(), Profiles.class);
			i.putExtra("typeProfil", typeProfil);
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
