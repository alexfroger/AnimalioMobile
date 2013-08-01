package fr.activity.animaliomobile;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import fr.animaliomobile.R;
import fr.library.animaliomobile.ConnectionWebservicePHP;
import fr.library.animaliomobile.TypefaceSpan;

public class MessagingService extends Activity{

	private Button btn_members;
	private Button btn_gallery;
	private Button btn_profil;
	private Button btn_events;
	private Button btn_live;
	private Button btn_photo;
	
	private LinearLayout.LayoutParams param_sender;
	private LinearLayout.LayoutParams param_receiver;
	private static ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	private RelativeLayout lay_message;
	private ScrollView lay_scrollview;
	private Button btn_msg_message;
	private static EditText edt_message;
	
	//Nombre de message à afficher, 20 messages par chargement
	private int nbMsgMin = 0 ;
	private int nbMsgMax = 20;
	private static int idUser;
	private static int idFriend;
	private static String friendName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messaging_service);
		
		//On récupère les varaibles passer par une autre vue 
		Bundle extra = getIntent().getExtras();
		this.idFriend = extra.getInt("idFriend");
		this.friendName = extra.getString("friendName");
		
		//On Récupére les préférences utilisateur si elle existe
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		this.idUser = Integer.parseInt(preferences.getString("idUser", ""));
		
		//On récupére les layout nécessaire
		lay_message = (RelativeLayout) findViewById(R.id.lay_message);
		lay_scrollview = (ScrollView) findViewById(R.id.lay_scrollview);
		btn_msg_message = (Button) findViewById(R.id.btn_message);
		edt_message = (EditText) findViewById(R.id.edt_message);
		
		//On affiche les 20 premier messages
		getMessageWebservice(this.idUser, this.idFriend, nbMsgMin, nbMsgMax, lay_message, lay_scrollview);
		
		//On afffecte les actions lors du click
		btn_msg_message.setOnClickListener(eventClick);
		
		lay_scrollview.setOnTouchListener(new OnTouchListener() {
			int action;
			float startY = 0;
			float endY = 0;
			int diff = 0;

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					action = 1;
					startY = event.getAxisValue(MotionEvent.AXIS_Y);
				}
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					action++;
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					endY = event.getAxisValue(MotionEvent.AXIS_Y);
					diff = lay_scrollview.getHeight() - (lay_message.getHeight() - v.getScrollY());
					
					if((endY - startY) < 0){ //Si on scroll vers le bas
						//Et qu'il y a pas de scroll
						if(v.getScrollY() == 0 && action >= 5){
							nbMsgMin += 20;
							// Instancie la connection au webservice en thread
							getMessageWebservice(idUser, idFriend, nbMsgMin, nbMsgMax, lay_message, lay_scrollview);
						}else if(diff == 0 && action >= 5){
							//Et si on est arrivé en bas
							nbMsgMin += 20;
							// Instancie la connection au webservice en thread
							getMessageWebservice(idUser, idFriend, nbMsgMin, nbMsgMax, lay_message, lay_scrollview);
						}
						//Et si on est arrivé en bas
					}
				}
				
				return false;
			}
		});
		
		btn_members = (Button)findViewById(R.id.btn_members);
		btn_gallery = (Button)findViewById(R.id.btn_gallery);
		btn_profil = (Button)findViewById(R.id.btn_profil);
		btn_events = (Button)findViewById(R.id.btn_events);
		btn_live = (Button)findViewById(R.id.btn_live);
		btn_photo = (Button)findViewById(R.id.btn_photo);
		
		btn_gallery.setOnClickListener(eventClick);
		btn_profil.setOnClickListener(eventClick);
		btn_events.setOnClickListener(eventClick);
		btn_live.setOnClickListener(eventClick);
		btn_photo.setOnClickListener(eventClick);
		btn_members.setOnClickListener(eventClick);
	}
	
	/**
	 * Return message into user and his friend
	 * @param Int idUser
	 * @param Int idFriend
	 * @param Int nbMsgMin
	 * @param Int nbMsgMax
	 * @return void
	 */
	public void getMessageWebservice (int idUser, int idFriend, int nbMsgMin, int nbMsgMax, RelativeLayout layMessage, ScrollView layScrollView){
		// On vide la liste de données à envoyé si existe déjà
		data.clear();

		// On ajoute les valeurs
		data.add(new BasicNameValuePair("id_user", String.valueOf(idUser)));
		data.add(new BasicNameValuePair("id_friend", String.valueOf(idFriend)));
		data.add(new BasicNameValuePair("nb_msg_min", String.valueOf(nbMsgMin)));
		data.add(new BasicNameValuePair("nb_msg_max", String.valueOf(nbMsgMax)));

		// Instancie la connection au webservice en thread
		if (ConnectionWebservicePHP.haveNetworkConnection(this)) { // Si connexion existe
			ConnectionWebservicePHP calcul = new ConnectionWebservicePHP(
					1, "MessagingService", this, data, layMessage, layScrollView, nbMsgMin);
			calcul.execute();
		} else { // Sinon toast de problème
			ConnectionWebservicePHP.haveNetworkConnectionError(this);
		}
	}
	
//	Create Listener event
    OnClickListener eventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(v==btn_msg_message){
				if(edt_message.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "Le message est vide",
							Toast.LENGTH_LONG).show();
				}else{
					// On vide la liste de données à envoyé si existe déjà
					data.clear();
	
					// On ajoute les valeurs
					data.add(new BasicNameValuePair("id_user", String.valueOf(idUser)));
					data.add(new BasicNameValuePair("id_friend", String.valueOf(idFriend)));
					data.add(new BasicNameValuePair("send_msg", "1"));
					data.add(new BasicNameValuePair("msg", edt_message.getText().toString()));
					
					// Instancie la connection au webservice en thread
					if (ConnectionWebservicePHP.haveNetworkConnection(v.getContext())) { // Si connexion existe
						ConnectionWebservicePHP calcul = new ConnectionWebservicePHP(
								1, "MessagingSend", v.getContext(), data);
						calcul.execute();
					} else { // Sinon toast de problème
						ConnectionWebservicePHP.haveNetworkConnectionError(v.getContext());
					}
				}
			}else if(v==btn_gallery){
				//Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(), Gallery.class);
				startActivity(intent);
				finish();
				
			}else if(v==btn_members){
				Intent intent = new Intent(getApplicationContext(), MembersList.class);
				startActivity(intent);
				finish();
			}else if(v==btn_profil){
				//Display Gallery Activity
				onBackPressed();
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
    
    /**
	 * Gestion de l'action bar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_action_bar, menu);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			SpannableString s = new SpannableString(friendName);
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
			Intent i = new Intent (getApplicationContext(), MessagingService.class);
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
