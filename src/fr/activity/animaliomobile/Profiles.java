package fr.activity.animaliomobile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import fr.animaliomobile.R;
import fr.library.animaliomobile.Animal;
import fr.library.animaliomobile.ConnectionWebservicePHP;
import fr.library.animaliomobile.ConnectionWebservicePHPProfile;
import fr.library.animaliomobile.Friend;
import fr.library.animaliomobile.InstallSQLiteBase;
import fr.library.animaliomobile.Message;
import fr.library.animaliomobile.Notification;
import fr.library.animaliomobile.RoundedImageView;
import fr.library.animaliomobile.TypefaceSpan;

public class Profiles extends Activity{
	//Button de l'activité courante
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
	private Button btn_profil_update;
	private Button btn_upd_animal;
	
	//Button bar de bas
	private Button btn_members;
	private Button btn_gallery;
	private Button btn_profil;
	private Button btn_events;
	private Button btn_live;
	private Button btn_photo;
	
	private ViewFlipper vf_profil;
	private static int typeProfil;
	private static ListView.LayoutParams param_lsv;
	private int[] positionChild = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	
	//Preference
	private static String idUser;
	private static String user_humorID;
	private static String user_cityID;
	private static String user_countryID;
	private static String user_lastname;
	private static String user_firstname;
	private static String user_nickname;
	private static String user_email;
	private static String user_avatarName;
	private static String user_password;
	private static String user_civility;
	private static String user_birthday;
	private static String user_phone;
	private static String user_phoneMobile;
	private static String user_onNewsletter;
	private static String user_onMobile;
	private static String user_isLoggedFacebook;
	private static String user_isBlacklist;
	private static String user_createdAt;
	private static String user_updatedAt;
	
	private static int userProvinceId;
	
	private static int pAnimalId;
	private Animal infoAnimal;
	// 0-Messagerie 1-ListeAnimaux 2-ListeAmis 3-ListeNotification
	// 4-FriendRequest
	// 5-EnvoyerMessage 6-DeleteFriend 7-UserModification 8-AnimalModification
	// 9-DeleteAnimal
	
	private RoundedImageView imv_profil;
	private ImageView imv_cover;
	
	private Typeface Arimo;
	private Typeface Lobster;
	
	private EditText upd_lastname;
	private EditText upd_firstname;
	private EditText upd_email;
	private EditText upd_nickname;
	private EditText upd_birthday;
	private EditText upd_phone;
	private EditText upd_phone_mobile;
	private EditText edt_upd_an_birthday;
	private EditText edt_upd_an_death;
	private EditText edt_upd_an_lastname;
	private EditText edt_upd_an_description;
	
    private TextView txt_an_lastname;
    private TextView txt_an_description;
    private TextView txt_an_birthday;
    private TextView txt_an_death;
    private TextView updLastname;
    private TextView updFirstname;
    private TextView updEmail;
    private TextView updNickname;
    private TextView updPays;
    private TextView updDep;
    private TextView updVille;
    private TextView updBirthday;
    private TextView updPhone;
    private TextView updPhoneMobile;

    private Spinner upd_pays;
	private Spinner upd_dep;
	private Spinner upd_ville;
	
	private Calendar myCalendar;
	private DatePickerDialog.OnDateSetListener date;
	
	private Thread splashTread;
	
	//Arrays retour webservice
	private ArrayList<JSONArray> arrayListReturn;
	private JSONArray arrayListMsg;
	private JSONArray arrayListAnimals;
	private JSONArray arrayListFriend;
	private JSONArray arrayListProfilAnimal;
	private JSONArray arrayListProfilFriend; 
	private JSONArray arrayListProfilAnimalUpdate; 
	private JSONArray arrayListProfilUpdate; 
	private JSONArray arrayListProvince; 
	
	private ConnectionWebservicePHPProfile calcul;
	private Thread thread;
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_profiles);
		
		//On Récupére les préférences utilisateur si elle existe
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		//On Récupére les préférences utilisateur si elle existe
		user_humorID = preferences.getString("humorID", "");
		user_cityID = preferences.getString("cityID", "");
		user_countryID = preferences.getString("countryID", "");
		user_lastname = preferences.getString("lastname", "");
		user_firstname = preferences.getString("firstname", "");
		user_nickname = preferences.getString("nickname", "");
		user_email = preferences.getString("email", "");
		user_avatarName = preferences.getString("avatarName", "");
		user_password = preferences.getString("password", "");
		user_civility = preferences.getString("civility", "");
		user_birthday = preferences.getString("birthday", "");
		user_phone = preferences.getString("phone", "");
		user_phoneMobile = preferences.getString("phoneMobile", "");
		user_onNewsletter = preferences.getString("onNewsletter", "");
		user_onMobile = preferences.getString("onMobile", "");
		user_isLoggedFacebook = preferences.getString("isLoggedFacebook", "");
		user_isBlacklist = preferences.getString("isBlacklist", "");
		user_createdAt = preferences.getString("createdAt", "");
		user_updatedAt = preferences.getString("updatedAt", "");
		idUser = preferences.getString("idUser", "");
				
		//On récupère les variables passer par une autre vue 
		Bundle extra = getIntent().getExtras();
		typeProfil = extra.getInt("typeProfil");
		pAnimalId = extra.getInt("animalId");
		
//		imv_profil = (RoundedImageView)findViewById(R.id.imv_profil);
//		imv_profil.setImageDrawable(getResources().getDrawable(R.drawable.ic_profil_undefined));

		imv_cover = (ImageView)findViewById(R.id.imv_cover);
		imv_cover.setImageDrawable(getResources().getDrawable(R.drawable.profil_test));
		imv_cover.setScaleType(ScaleType.FIT_XY);

		Arimo = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "Arimo-Regular.ttf");
		Lobster = Typeface.createFromAsset(
				getApplicationContext().getAssets(), "Lobster.otf");
		
		myCalendar = Calendar.getInstance();

		date = new DatePickerDialog.OnDateSetListener() {
		    @Override
		    public void onDateSet(DatePicker view, int year, int monthOfYear,
		            int dayOfMonth) {
		        myCalendar.set(Calendar.YEAR, year);
		        myCalendar.set(Calendar.MONTH, monthOfYear);
		       
		        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

		        String myFormat = "dd-MM-yyyy"; //In which you need put here
			    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
	
			    upd_birthday.setText(sdf.format(myCalendar.getTime()));
		    }
		};
		
		// Si connexion existe on charge le contenu
		if (ConnectionWebservicePHP.haveNetworkConnection(this)) {	
			switch (typeProfil) {
			case 0: // Profil utilisateur		
				
				/*
				 * Instanciation des éléments du ViewFlipper
				 */
//				View layoutListMsg = createLayout("Envoyer un message");
//				vf_profil.addView(layoutListMsg, param_lsv);
//				positionChild[0]=0;
				
				// On vide la liste de données à envoyé si existe déjà
				data.clear();

				// On ajoute les valeurs
				data.add(new BasicNameValuePair("id_user", idUser));
				data.add(new BasicNameValuePair("list_name", "listMsg"));
				data.add(new BasicNameValuePair("nb_msg_min", "0"));
				data.add(new BasicNameValuePair("nb_msg_max", "20"));
				
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
//		
////				vf_profil.addView(createListAnimals(arrayListAnimals), param_lsv);
////				positionChild[1]=1;
////	
////				vf_profil.addView(createListFriends(arrayListFriend), param_lsv);
////				positionChild[2]= 2;
////	
////				vf_profil.addView(createListNotifications(), param_lsv);
////				positionChild[3]=3;
//	
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
	
//				vf_profil.addView(createListAnimals(), param_lsv);
//				positionChild[1] = 0;
//	
//				vf_profil.addView(createListFriends(), param_lsv);
//				positionChild[2] = 1;
//	
//				vf_profil.addView(createModification(), param_lsv);
//				positionChild[4] = 2; 
	
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
	
//				vf_profil.addView(createListAnimals(), param_lsv);
//				positionChild[1]=1;
//	
//				vf_profil.addView(createListFriends(), param_lsv);
//				positionChild[2]= 2;
//	
//				vf_profil.addView(createListNotifications(), param_lsv);
//				positionChild[3]=3;
//	
//				vf_profil.addView(createModification(), param_lsv);
//				positionChild[6] = 4;			
				break;
			case 3: //Profil Static animal		
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
				vf_profil.addView(createModificationAnimal(), param_lsv);
				positionChild[8] = 0;
				
				//TextView
				txt_an_lastname = (TextView) findViewById(R.id.update_txtView_an_lastname);
				txt_an_description = (TextView) findViewById(R.id.update_txtView_an_description);
				txt_an_birthday = (TextView) findViewById(R.id.update_txtView_an_birthday);
				txt_an_death = (TextView) findViewById(R.id.update_txtView_an_death);
				
				//EditText
				edt_upd_an_birthday = (EditText) findViewById(R.id.update_an_birthday);
				edt_upd_an_death = (EditText) findViewById(R.id.update_an_death);
				edt_upd_an_lastname = (EditText) findViewById(R.id.update_an_lastname);
				edt_upd_an_description = (EditText) findViewById(R.id.update_an_description);
				
				//Button
				btn_upd_animal = (Button) findViewById(R.id.btn_update_animal);
			    
				//On met la police au textview
				txt_an_lastname.setTypeface(Lobster);
			    txt_an_description.setTypeface(Lobster);
			    txt_an_birthday.setTypeface(Lobster);
			    txt_an_death.setTypeface(Lobster);
				
				//Event à la touche ou au click propre a cette vue
				btn_upd_animal.setOnClickListener(eventClick);
	
				edt_upd_an_birthday.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if (event.getAction() == KeyEvent.ACTION_UP){
							new DatePickerDialog(v.getContext(), date, myCalendar
									.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
									myCalendar.get(Calendar.DAY_OF_MONTH)).show();
						}
						return false;
					}
	
				});
				
				edt_upd_an_death.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if (event.getAction() == KeyEvent.ACTION_UP){
							new DatePickerDialog(v.getContext(), date, myCalendar
									.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
									myCalendar.get(Calendar.DAY_OF_MONTH)).show();
						}
						return false;
					}
	
				});
				break;
			}
		}else{ // Sinon toast de problème
			ConnectionWebservicePHP.haveNetworkConnectionError(this);
		}
		
		btn_members = (Button)findViewById(R.id.btn_members);
		btn_gallery = (Button)findViewById(R.id.btn_gallery);
		btn_profil = (Button)findViewById(R.id.btn_profil);
		btn_events = (Button)findViewById(R.id.btn_events);
		btn_live = (Button)findViewById(R.id.btn_live);
		btn_photo = (Button)findViewById(R.id.btn_photo);
					
		btn_profil.setBackgroundResource(R.drawable.ic_profil_pressed);
		
		//Evt click
		btn_members.setOnClickListener(eventClick);
		btn_gallery.setOnClickListener(eventClick);
		btn_events.setOnClickListener(eventClick);
		btn_live.setOnClickListener(eventClick);
		btn_photo.setOnClickListener(eventClick);
		
		//On empeche le clavier de s'afficher
		getWindow().setSoftInputMode(
		         WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		switch (typeProfil) {
			case 0: // Profil utilisateur
				//On recupere les informations
				calcul = new ConnectionWebservicePHPProfile(
						1, "listObject", this);
				calcul.execute();	
				
				handler = new Handler(Looper.getMainLooper());
				
				thread = new Thread() {
					@Override
					public void run() {
						if (calcul.isFinish == true) {
							
							try {
								//Récupere les information du profil
								//0 => "listMsg", 1 =>listAnimals, 2 =>AnimalInfo, 
								//3 =>listFriend, 4 =>updateProfil
								arrayListReturn = calcul.get();
								
								arrayListMsg = arrayListReturn.get(0);
								arrayListAnimals = arrayListReturn.get(1);
								arrayListFriend = arrayListReturn.get(2);
								
								arrayListProvince = arrayListReturn.get(3);
								JSONObject infoWebserviveReturn = arrayListProvince
										.getJSONObject(0);
								userProvinceId = infoWebserviveReturn.getInt("province_id");
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							//On récupére les infos array retourner et on ajoute les elements à la vue
							vf_profil = (ViewFlipper) findViewById(R.id.vf_profil);
							ListView.LayoutParams param_lsv = new ListView.LayoutParams(
									LayoutParams.MATCH_PARENT,
									LayoutParams.MATCH_PARENT);

							vf_profil.addView(createListMsg(arrayListMsg),
									param_lsv);
							positionChild[0] = 0;
							
							Log.i("log_arrayListAnimals", "arrayListAnimals : " + arrayListAnimals);
							vf_profil.addView(createListAnimals(arrayListAnimals), param_lsv);
							positionChild[1]=1;
				
							vf_profil.addView(createListFriends(arrayListFriend), param_lsv);
							positionChild[2]= 2;
				
							vf_profil.addView(createListNotifications(), param_lsv);
							positionChild[3]=3;
							
							vf_profil.addView(createModification(), param_lsv);
							positionChild[7] = 4;
							
							//Style de la vue modification
							// Bouton modifier profil
							btn_profil_update = (Button) findViewById(R.id.btn_registration);
							btn_profil_update.setOnClickListener(eventClick);
							
							//Textview
						    updLastname = (TextView)findViewById(R.id.update_txtView_lastname);
						    updFirstname = (TextView)findViewById(R.id.update_txtView_firstname);
						    updEmail = (TextView)findViewById(R.id.update_txtView_email);
						    updNickname = (TextView)findViewById(R.id.update_txtView_nickname);
						    updPays = (TextView)findViewById(R.id.update_p_pays_id);
						    updDep = (TextView)findViewById(R.id.update_p_dep_id);
				//		    updVille = (TextView)findViewById(R.id.update_p_ville_id);
						    updBirthday = (TextView)findViewById(R.id.update_txtView_birthday);
						    updPhone = (TextView)findViewById(R.id.update_txtView_phone);
						    updPhoneMobile = (TextView)findViewById(R.id.update_txtView_mobile);
						    
						    //EditText
						    upd_lastname = (EditText) findViewById(R.id.update_lastname);
						    upd_firstname = (EditText) findViewById(R.id.update_firstname);
						    upd_email = (EditText) findViewById(R.id.update_email);
						    upd_nickname = (EditText) findViewById(R.id.update_nickname);
							upd_birthday = (EditText) findViewById(R.id.update_birthday);
							upd_phone = (EditText) findViewById(R.id.update_phone);
							upd_phone_mobile = (EditText) findViewById(R.id.update_phone_mobile);
							
							//Spinner
						    upd_pays = (Spinner) findViewById(R.id.update_spinner_p_pays_id);
						    upd_dep = (Spinner) findViewById(R.id.update_spinner_p_dep_id);
						    
						    upd_pays.setOnItemSelectedListener(selectClick);
						    upd_ville = (Spinner) findViewById(R.id.update_p_ville_id);
						    
							//Formattage de la date de naissance
							Date dateFormat = null;
							try {
								dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(user_birthday);
							} catch (ParseException e1) {
								Log.e("log_profilModif",
										"Date Formatted : " + user_birthday);
								e1.printStackTrace();
							}
							String birthdayFormmated = new SimpleDateFormat("dd-MM-yyyy").format(dateFormat);
							
							upd_lastname.setText(user_lastname);
							upd_firstname.setText(user_firstname);
							upd_email.setHint(user_email);
						    upd_nickname.setText(user_nickname);	    
							upd_birthday.setText(birthdayFormmated);
						    upd_phone.setText(user_phone);
						    upd_phone_mobile.setText(user_phoneMobile);
						    
							//On met la police au textview
						    updLastname.setTypeface(Lobster);
						    updFirstname.setTypeface(Lobster);
							updEmail.setTypeface(Lobster);
							updNickname.setTypeface(Lobster);
							updPays.setTypeface(Lobster);
							updDep.setTypeface(Lobster);
							updVille.setTypeface(Lobster);
							updBirthday.setTypeface(Lobster);
							updPhone.setTypeface(Lobster);
							updPhoneMobile.setTypeface(Lobster);
							
							//On ajoute les informations des spinners
							addItemsCountryOnSpinner(Integer.parseInt(user_countryID));
							
							upd_birthday.setOnTouchListener(new OnTouchListener() {
				
								@Override
								public boolean onTouch(View v, MotionEvent event) {
									if (event.getAction() == KeyEvent.ACTION_UP){
										new DatePickerDialog(v.getContext(), date, myCalendar
												.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
												myCalendar.get(Calendar.DAY_OF_MONTH)).show();
									}
									return false;
								}
				
							});			
							//End vue modification
						}else{
							handler.postDelayed(this, 100);
						}
					}
				};
				thread.start();
			break;
			case 1 : //Profil membre
				break;
			case 2 : // Profil ami
				break;
			case 3 : // Profil animal
				// On vide la liste de données à envoyé si existe déjà
				data.clear();
	
				// On ajoute les valeurs
				data.add(new BasicNameValuePair("id_user", idUser));
				data.add(new BasicNameValuePair("id_animal", String.valueOf(pAnimalId)));
				data.add(new BasicNameValuePair("list_name", "AnimalInfo"));
				
				//On recupere les informations
				calcul = new ConnectionWebservicePHPProfile(
						1, "listObjectOther", this, data);
				calcul.execute();	
				
				handler = new Handler(Looper.getMainLooper());
				
				thread = new Thread() {
					@Override
					public void run() {
						if (calcul.isFinish == true) {
							
							try {
								//Récupere les information du profil
								//0 => "listMsg", 1 =>listAnimals, 2 =>AnimalInfo, 
								//3 =>listFriend, 4 =>updateProfil
								arrayListReturn = calcul.get();
								
								arrayListProfilAnimal = arrayListReturn.get(0);
								
								try {
									JSONObject infoWebserviveReturn = arrayListProfilAnimal
											.getJSONObject(0);
									
									infoAnimal = new Animal(infoWebserviveReturn.getInt("id_animal"), infoWebserviveReturn.getString("animal_name"));
									infoAnimal.setUserId(infoWebserviveReturn.getInt("user_id"));
									infoAnimal.setRaceId(infoWebserviveReturn.getInt("animal_race_id"));
									infoAnimal.setDescription(infoWebserviveReturn.getString("animal_description"));
									infoAnimal.setBirthday(infoWebserviveReturn.getString("animal_birthday"));
									infoAnimal.setDeath(infoWebserviveReturn.getString("animal_death"));
									infoAnimal.setCreatedAt(infoWebserviveReturn.getString("created_at"));
									infoAnimal.setUpdatedAt(infoWebserviveReturn.getString("updated_at"));
									
								} catch (JSONException e) {
									Log.e("log_listObjectMessage",
											"Erreur 3 infoWebserviveReturn : " + e.toString());
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {

								e.printStackTrace();
							}
							
							//On récupére les infos array retourner et on ajoute les elements à la vue
							//Formattage de la date de naissance et de décès
							Date dateFormatBirthday = null;
							Date dateFormatDeath = null;
							try {
								dateFormatBirthday = new SimpleDateFormat("yyyy-MM-dd").parse(infoAnimal.birthday);
								dateFormatDeath = new SimpleDateFormat("yyyy-MM-dd").parse(infoAnimal.death);
							} catch (ParseException e1) {
								Log.e("log_profilModif",
										"Date Formatted : " + e1.toString());
							}
							String dateFormatBirthdayFormmated = new SimpleDateFormat("dd-MM-yyyy").format(dateFormatBirthday);
							String dateFormatDeathFormmated = new SimpleDateFormat("dd-MM-yyyy").format(dateFormatDeath);
							
							//Spinner : On ajoute les types et race au spinner type et race
							addItemsTypeOnSpinner(infoAnimal.id);
							addItemsRaceOnSpinner();
							
							edt_upd_an_birthday.setHint(dateFormatBirthdayFormmated);
							edt_upd_an_death.setHint(dateFormatDeathFormmated);
							edt_upd_an_lastname.setHint(infoAnimal.name);
							edt_upd_an_description.setHint(infoAnimal.description);
						}else{
							handler.postDelayed(this, 100);
						}
					}
				};
				thread.start();
				break;
		
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	}
	
	OnItemSelectedListener selectClick = new OnItemSelectedListener(){
		
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			
			if(parent == upd_pays){
				addItemsProvinceOnSpinner(Integer.parseInt(user_cityID), position+1);
			}
			if(parent == upd_ville){
				addItemsCityOnSpinner(Integer.parseInt(user_cityID), position+1);
			}
		}
	
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}	
	};
	
	OnClickListener eventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if (v == btn_msg) {
				vf_profil.setDisplayedChild(positionChild[0]);
			} else if (v == btn_animals_list) {
				vf_profil.setDisplayedChild(positionChild[1]);
			} else if (v == btn_friends_list) {
				vf_profil.setDisplayedChild(positionChild[2]);
			} else if (v == btn_notifications_list) {
				vf_profil.setDisplayedChild(positionChild[3]);
			} else if (v == btn_friends_requests) {
				vf_profil.setDisplayedChild(positionChild[4]);
			} else if (v == btn_send_msg) {
				vf_profil.setDisplayedChild(positionChild[5]);
			} else if (v == btn_delete_friend) {
				vf_profil.setDisplayedChild(positionChild[6]);
			} else if (v == btn_user_modification) {
				vf_profil.setDisplayedChild(positionChild[7]);
			} else if (v == btn_animal_modification) {
				vf_profil.setDisplayedChild(positionChild[8]);
			} else if (v == btn_delete_animal) {
				Toast t = Toast.makeText(getApplicationContext(),
						"Acitver la suppresion de l'animal",
						Toast.LENGTH_LONG);
				t.setGravity(Gravity.BOTTOM, 0, 40);
				t.show();
			} else if (v == btn_upd_animal){
				//Modification du profil
				//si ou ou plusieurs champs sont vides
				if(edt_upd_an_lastname.getText().toString().equals("") || edt_upd_an_birthday.getText().toString().equals("")){
					Toast t = Toast.makeText(getApplicationContext(),
							"Veuillez remplir les champs obligatoires",
							Toast.LENGTH_LONG);
					t.setGravity(Gravity.BOTTOM, 0, 40);
					t.show();
				}else{
					// On vide la liste de données à envoyé si existe déjà
					data.clear();
	
					// On ajoute les valeurs
					data.add(new BasicNameValuePair("id_user", idUser));
					data.add(new BasicNameValuePair("list_name", "updateProfilAnimal"));
					data.add(new BasicNameValuePair("upd_an_birthday", edt_upd_an_birthday.getText().toString()));
					data.add(new BasicNameValuePair("upd_an_death", edt_upd_an_death.getText().toString()));
					data.add(new BasicNameValuePair("upd_an_description", edt_upd_an_description.getText().toString()));
					data.add(new BasicNameValuePair("upd_an_lastname", edt_upd_an_lastname.getText().toString()));
					data.add(new BasicNameValuePair("upd_animalId", String.valueOf(infoAnimal.id)));
					
					// Instancie la connection au webservice en thread
					if (ConnectionWebservicePHPProfile.haveNetworkConnection(v.getContext())) { 
						ConnectionWebservicePHPProfile calcul = new ConnectionWebservicePHPProfile(
								1, "listObjectOther", v.getContext(), data);
						calcul.execute();
						try {
							arrayListReturn = calcul.get();
							arrayListProfilAnimalUpdate = arrayListReturn.get(0);
							
							JSONObject infoWebserviveReturn;
							try {
								infoWebserviveReturn = arrayListProfilAnimalUpdate
										.getJSONObject(0);
								
								if (infoWebserviveReturn.getInt("isOk") == 0) {
									//Erreur de modification du profil
									Toast.makeText(v.getContext(),
											"Erreur d'enregistrement",
											Toast.LENGTH_LONG).show();
								} else { // Profil modifié
									// On stock les infos utilisateurs dans des preferences
									Toast.makeText(v.getContext(),
											"Profil mis à jour avec succès",
											Toast.LENGTH_LONG).show();
									
									//Formattage de la date de naissance et de décès
									Date dateFormatBirthday = null;
									Date dateFormatDeath = null;
									try {
										dateFormatBirthday = new SimpleDateFormat("yyyy-MM-dd").parse(infoWebserviveReturn.getString("an_birthday"));
										dateFormatDeath = new SimpleDateFormat("yyyy-MM-dd").parse(infoWebserviveReturn.getString("an_death"));
									} catch (ParseException e1) {
										Log.e("log_profilModif",
												"Date Formatted : " + e1.toString());
									}
									String dateFormatBirthdayFormmated = new SimpleDateFormat("dd-MM-yyyy").format(dateFormatBirthday);
									String dateFormatDeathFormmated = new SimpleDateFormat("dd-MM-yyyy").format(dateFormatDeath);
									
									edt_upd_an_birthday.setHint(dateFormatBirthdayFormmated);
									edt_upd_an_death.setHint(dateFormatDeathFormmated);
									edt_upd_an_lastname.setHint(infoWebserviveReturn.getString("an_name"));
									edt_upd_an_description.setHint(infoWebserviveReturn.getString("an_description"));
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} catch (InterruptedException e) {
							Log.e("log_listObjectMessage",
									"Erreur 1 Interrupted :" + e.toString());
						} catch (ExecutionException e) {
							Log.e("log_listObjectMessage",
									"Erreur 2 Execution :" + e.toString());
						}
					} else { // Sinon toast de problème
						ConnectionWebservicePHPProfile.haveNetworkConnectionError(v.getContext());
					}
				}
			}else if(v == btn_profil_update){
				//Modification du profil
				//si ou ou plusieurs champs sont vides
				if(upd_lastname.getText().toString().equals("")||upd_firstname.getText().toString().equals("")
						||upd_nickname.getText().toString().equals("")){
					Toast t = Toast.makeText(getApplicationContext(),
							"Veuillez remplir les champs obligatoires",
							Toast.LENGTH_LONG);
					t.setGravity(Gravity.BOTTOM, 0, 40);
					t.show();
				}else{
					// On vide la liste de données à envoyé si existe déjà
					data.clear();
	
					// On ajoute les valeurs
					data.add(new BasicNameValuePair("id_user", idUser));
					data.add(new BasicNameValuePair("list_name", "updateProfil"));
					data.add(new BasicNameValuePair("upd_lastname", upd_lastname.getText().toString()));
					data.add(new BasicNameValuePair("upd_firstname", upd_firstname.getText().toString()));
					data.add(new BasicNameValuePair("upd_nickname", upd_nickname.getText().toString()));
					data.add(new BasicNameValuePair("upd_birthday", upd_birthday.getText().toString()));
					data.add(new BasicNameValuePair("upd_phone", upd_phone.getText().toString()));
					data.add(new BasicNameValuePair("upd_phone_mobile", upd_phone_mobile.getText().toString()));
					
					// Instancie la connection au webservice en thread
					if (ConnectionWebservicePHPProfile.haveNetworkConnection(v.getContext())) { 
						ConnectionWebservicePHPProfile calcul = new ConnectionWebservicePHPProfile(
								1, "listObject", v.getContext(), data);
						calcul.execute();
						try {
							arrayListReturn = calcul.get();
							
							arrayListProfilUpdate = arrayListReturn.get(0);
							
							JSONObject infoWebserviveReturn;
							try {
								infoWebserviveReturn = arrayListProfilUpdate
										.getJSONObject(0);
								
								if (infoWebserviveReturn.getInt("isOk") == 0) {
									//Erreur de modification du profil
									Toast.makeText(v.getContext(),
											"Erreur d'enregistrement",
											Toast.LENGTH_LONG).show();
								} else { // Profil modifié
									// On stock les infos utilisateurs dans des preferences
									SharedPreferences preferences = PreferenceManager
											.getDefaultSharedPreferences(v.getContext());
									SharedPreferences.Editor editor = preferences.edit();
									editor.putString("idUser", infoWebserviveReturn.getString("id_user"));
									editor.putString("humorID", infoWebserviveReturn.getString("humor_id"));
									editor.putString("cityID", infoWebserviveReturn.getString("city_id"));
									editor.putString("countryID", infoWebserviveReturn.getString("country_id"));
									editor.putString("lastname", infoWebserviveReturn.getString("lastname"));
									editor.putString("firstname", infoWebserviveReturn.getString("firstname"));
									editor.putString("nickname", infoWebserviveReturn.getString("nickname"));
									editor.putString("email", infoWebserviveReturn.getString("email"));
									editor.putString("avatarName", infoWebserviveReturn.getString("avatar_name"));
									editor.putString("password", infoWebserviveReturn.getString("password"));
									editor.putString("civility", infoWebserviveReturn.getString("civility"));
									editor.putString("birthday", infoWebserviveReturn.getString("birthday"));
									editor.putString("phone", infoWebserviveReturn.getString("phone"));
									editor.putString("phoneMobile", infoWebserviveReturn.getString("phone_mobile"));
									editor.putString("onNewsletter", infoWebserviveReturn.getString("on_newsletter"));
									editor.putString("onMobile", infoWebserviveReturn.getString("on_mobile"));
									editor.putString("isLoggedFacebook", infoWebserviveReturn.getString("is_logged_facebook"));
									editor.putString("isBlacklist", infoWebserviveReturn.getString("is_blacklist"));
									editor.putString("createdAt", infoWebserviveReturn.getString("created_at"));
									editor.putString("updatedAt", infoWebserviveReturn.getString("updated_at"));
									editor.commit();
									
									Toast.makeText(v.getContext(),
											"Profil modifié mis à jour",
											Toast.LENGTH_LONG).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (InterruptedException e) {
							Log.e("log_listObjectMessage",
									"Erreur 1 Interrupted :" + e.toString());
						} catch (ExecutionException e) {
							Log.e("log_listObjectMessage",
									"Erreur 2 Execution :" + e.toString());
						}
					} else { // Sinon toast de problème
						ConnectionWebservicePHPProfile.haveNetworkConnectionError(v.getContext());
					}
				}
			} else if (v == btn_members) {
				// Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(),
						MembersList.class);
				startActivity(intent);
				finish();
			}

			else if (v == btn_gallery) {
				// Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(),
						Gallery.class);
				startActivity(intent);
				finish();
			}

			else if (v == btn_events) {
				// Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(),
						Events.class);
				startActivity(intent);
				finish();
			}

			else if (v == btn_live) {
				// Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(),
						AnimalioLive.class);
				startActivity(intent);
				finish();
			}

			else if (v == btn_photo) {
				// Display the photo phone application
				Toast t = Toast.makeText(Home.context,
						"Faire la redirection vers photo de l'appli",
						Toast.LENGTH_LONG);
				t.setGravity(Gravity.BOTTOM, 0, 40);
				t.show();
			}
		}
	};
	
	/*
	 * Fonction de création des vues
	 */
	private View createModification() {
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View displayView = inflater.inflate(R.layout.profile_update, null);

		return displayView;
	}

	public View createModificationAnimal() {
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View displayView = inflater.inflate(R.layout.profile_animal_update, null);

		return displayView;
	}
	
	public ListView createListMsg(JSONArray arrayListMsg1) {
		final ListView lsv_msg = new ListView(this);
		ArrayList<Message> messages = new ArrayList<Message>();
		
		try {
			// On ajoute les message que l'on à reçu
			for (int i = 0; i < arrayListAnimals.length(); i++) {
				JSONObject infoWebserviveReturn = arrayListMsg1
						.getJSONObject(i);

				if (!infoWebserviveReturn.isNull("user_id_to")) {
					Message message1 = new Message(
							infoWebserviveReturn.getInt("user_id_to"),
							infoWebserviveReturn.getString("nickname"),
							infoWebserviveReturn.getString("message"));
					messages.add(message1);
				} else { // Aucun message de creer
					Message message0 = new Message(0, "Aucun message", " ");
					messages.add(message0);
				}
			}
		} catch (JSONException e) {
			Log.e("log_ListObjectMessage", "Erreur récupération messages : "
					+ e.toString());
		}

		CustomAdapterMessages adapter_msg = new CustomAdapterMessages(this,
				messages);
		lsv_msg.setAdapter(adapter_msg);
		// Ajout d'un onclick listener sur chaque element
		lsv_msg.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// On recupere les infos de l'item
				Message res = (Message) lsv_msg.getItemAtPosition(position);
				Intent intent = new Intent(getApplicationContext(),
						MessagingService.class);
				intent.putExtra("idFriend", res.id);
				intent.putExtra("friendName", res.name);
				startActivity(intent);
			}
		});

		return lsv_msg;
	}

	ListView createListAnimals(JSONArray arrayListAnimal1) {
		final ListView lsv_animals_list = new ListView(this);
		ArrayList<Animal> animals = new ArrayList<Animal>();
		
		try {
			// Création d'une ligne ou on peut ajouter un animal
			Animal animal0 = new Animal(0, "Ajouter un animal");
			animals.add(animal0);

			// On ajoute les animaux que l'on à déjà créer
			for (int i = 0; i < arrayListAnimal1.length(); i++) {
				JSONObject infoWebserviveReturn = arrayListAnimal1
						.getJSONObject(i);

				if (!infoWebserviveReturn.isNull("id_animal")) {
					Animal animal1 = new Animal(
							infoWebserviveReturn.getInt("id_animal"),
							infoWebserviveReturn.getString("animal_name"));
					animals.add(animal1);
				}
			}
		} catch (JSONException e) {
			Log.e("log_ListObjectMessage", "Erreur récupération messages : "
					+ e.toString());
		}

		CustomAdapterAnimals adapter_animals_list = new CustomAdapterAnimals(
				this, animals);
		lsv_animals_list.setAdapter(adapter_animals_list);

		// Ajout d'un onclick listener sur chaque element
		lsv_animals_list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// On recupere les infos de l'item
				Animal res = (Animal) lsv_animals_list
						.getItemAtPosition(position);
				Intent intent = new Intent(getApplicationContext(),
						Profiles.class);
				if (typeProfil == 0) {
					intent.putExtra("typeProfil", 3);
				} else {
					intent.putExtra("typeProfil", 3); // A CHANGER QUAND LE
														// PROFIL ANIMAL AMI
														// SERA CREE
				}
				// Si c'est un animal
				if (res.getAnimalId() != 0) {
					intent.putExtra("animalId", res.getAnimalId());
					startActivity(intent);
				} else {// Sinon on active l'ajout d'un animal
						// TODO Ajouter l'ajout d'un animal
				}
			}
		});

		/*
		 * RelativeLayout lay_animal = new RelativeLayout(this);
		 * lay_animal.setId(101); Button addAnimal = new Button(this);
		 * addAnimal.setText("Ajouter un animal"); addAnimal.setId(102);
		 * RelativeLayout.LayoutParams paramButton = new
		 * RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 50);
		 * paramButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
		 * lay_animal.getId()); RelativeLayout.LayoutParams paramList = new
		 * RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
		 * LayoutParams.WRAP_CONTENT);
		 * paramButton.addRule(RelativeLayout.ALIGN_PARENT_TOP,
		 * lay_animal.getId()); paramButton.addRule(RelativeLayout.ABOVE,
		 * addAnimal.getId());
		 * 
		 * lay_animal.addView(lsv_animals_list, paramList);
		 * lay_animal.addView(addAnimal, paramButton);
		 */

		return lsv_animals_list;
	}

	ListView createListFriends(JSONArray arrayListFriend1) {
		final ListView lsv_friends_list = new ListView(this);
		ArrayList<Friend> friends = new ArrayList<Friend>();

		try {
			// On ajoute les animaux que l'on à déjà créer
			for (int i = 0; i < arrayListFriend1.length(); i++) {
				JSONObject infoWebserviveReturn = arrayListFriend1
						.getJSONObject(i);

				if (!infoWebserviveReturn.isNull("friend_id")) {
					Friend friend1 = new Friend(
							infoWebserviveReturn.getInt("friend_id"),
							infoWebserviveReturn.getString("nickname"),
							infoWebserviveReturn.getInt("on_mobile"));
					friends.add(friend1);
				} else {
					Friend friend1 = new Friend(0, "Pas encore d'amis", 0);
					friends.add(friend1);
				}
			}
		} catch (JSONException e) {
			Log.e("log_ListObjectMessage", "Erreur récupération messages : "
					+ e.toString());
		}

		CustomAdapterFriends adapter_friends_list = new CustomAdapterFriends(
				this, friends);
		lsv_friends_list.setAdapter(adapter_friends_list);
		// Ajout d'un onclick listener sur chaque element
		lsv_friends_list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// A AJOUTER UN TEST POUR VERIFIER S'IL S'AGIT D'UN AMI OU NON
				Intent intent = new Intent(getApplicationContext(),
						Profiles.class);
				intent.putExtra("typeProfil", 2);
				startActivity(intent);
			}
		});
		return lsv_friends_list;
	}

	ListView createListNotifications() {
		final ListView lsv_notifications_list = new ListView(this);
		Notification notification1 = new Notification(0, "Norbert", "Truc", 0,
				20, 13, "28/07/13", "18h03");
		Notification notification2 = new Notification(0, "Hercule", "Machin",
				1, 20, 13, "28/07/13", "18h03");
		Notification notification3 = new Notification(0, "Jean Roger",
				"Chouette", 0, 20, 13, "28/07/13", "18h03");
		Notification notification4 = new Notification(0, "Florent", "Bidule",
				2, 20, 13, "28/07/13", "18h03");
		Notification notification5 = new Notification(0, "Pierre", "Pouette",
				2, 20, 13, "28/07/13", "18h03");
		Notification notification6 = new Notification(0, "Alexandre",
				"Yaaaata", 3, 20, 13, "28/07/13", "18h03", "Animal expo 2013",
				"test");
		Notification notification7 = new Notification(
				0,
				"test",
				"test",
				4,
				20,
				13,
				"28/07/13",
				"18h03",
				"Animal expo 2013",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus mattis ligula sed fringilla auctor. Cras auctor ultricies arcu at lobortis. Vestibulum vel accumsan");
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		notifications.add(notification1);
		notifications.add(notification2);
		notifications.add(notification3);
		notifications.add(notification4);
		notifications.add(notification5);
		notifications.add(notification6);
		notifications.add(notification7);

		CustomAdapterNotifications adapter_notifications_list = new CustomAdapterNotifications(
				this, notifications);
		lsv_notifications_list.setAdapter(adapter_notifications_list);
		// Ajout d'un onclick listener sur chaque element
		lsv_notifications_list
				.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(getApplicationContext(),
								Details.class);
						startActivity(intent);
					}
				});
		return lsv_notifications_list;
	}

	LinearLayout createLayout(String text) {
		LinearLayout.LayoutParams param_txv = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LinearLayout layout = new LinearLayout(this);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		TextView txv = new TextView(this);
		txv.setText(text);
		txv.setTextColor(getResources().getColor(R.color.black_color));
		txv.setBackgroundColor(Color.CYAN);
		param_txv.gravity = Gravity.CENTER;
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
		String actionBarName = "";
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			switch (typeProfil) {
			case 0: // Profil utilisateur
				actionBarName = "Mon profil";
				break;
			case 1: // Profil membre
				break;
			case 2: // Profil ami
				break;
			case 3: // Profil animal
				actionBarName = infoAnimal.getName();
				break;
			}
			SpannableString s = new SpannableString(actionBarName);
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
			Intent i = new Intent(getApplicationContext(), Profiles.class);
			i.putExtra("typeProfil", typeProfil);
			startActivity(i);
			finish();
			return true;
		case R.id.menu_settings:
			// Comportement du bouton "Paramètres"
			return true;
		case R.id.menu_delete:
			// Comportement du bouton "Delete" A supprimer quand le popup
			// parametre sera creer car dedans

			// On créé l'Intent qui va nous permettre d'afficher l'autre
			// Activity
			Intent intent = new Intent(getApplicationContext(),
					Authentication.class);
			// On supprime l'activity de login sinon
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

			// Puis on reset les informations utilisateurs
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			SharedPreferences.Editor editor = preferences.edit();
			editor.clear();
			editor.commit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// Adapter définissant la façon dont est affiché chaque item de la liste
	class CustomAdapterViewFriend extends LinearLayout {

		public CustomAdapterViewFriend(Context context, final Friend friend) {
			super(context);
			setId(friend.id);
			setOrientation(LinearLayout.HORIZONTAL);

			// Instanciation des différents layout de l'adapter
			RelativeLayout mainLayout = new RelativeLayout(context);
			mainLayout.setId(666);
			LinearLayout subLayout = new LinearLayout(context);

			// Instanciation de l'image utilisateur
			ImageView imv_user = new ImageView(context);
			imv_user.setImageDrawable(getResources().getDrawable(
					R.drawable.img_defaultuser));
			imv_user.setId(777);

			// Instanciation du nom de l'utilisateur
			TextView txv_name = new TextView(context);
			txv_name.setText(friend.name);
			txv_name.setPadding(15, 0, 0, 0);
			txv_name.setTextColor(getResources().getColor(R.color.grey_color));
			txv_name.setTypeface(Arimo);
			txv_name.setTextSize(20);

			// Instanciation des images messages et connecté
			ImageView isConnected = new ImageView(context);
			if (friend.status == 2) {
				isConnected.setImageDrawable(getResources().getDrawable(
						R.drawable.oncomputer));
			} else if (friend.status == 1) {
				isConnected.setImageDrawable(getResources().getDrawable(
						R.drawable.onapp));
			} else {
				isConnected.setImageDrawable(getResources().getDrawable(
						R.drawable.blank));
			}

			isConnected.setPadding(0, 10, 10, 0);
			ImageView imv_message = new ImageView(context);
			imv_message.setImageDrawable(getResources().getDrawable(
					R.drawable.imv_message));
			imv_message.setPadding(0, 0, 10, 0);

			imv_message.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(),
							MessagingService.class);
					intent.putExtra("idFriend", friend.id);
					intent.putExtra("friendName", friend.name);
					startActivity(intent);
				}
			});

			subLayout.setOrientation(LinearLayout.VERTICAL);
			subLayout.setGravity(Gravity.CENTER);

			RelativeLayout.LayoutParams paramTxv = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
			paramTxv.addRule(RelativeLayout.RIGHT_OF, imv_user.getId());
			paramTxv.addRule(RelativeLayout.CENTER_VERTICAL, mainLayout.getId());
			LinearLayout.LayoutParams wrap_0 = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, 0);
			RelativeLayout.LayoutParams paramSubLayout = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
			paramSubLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
					mainLayout.getId());
			paramSubLayout.addRule(RelativeLayout.CENTER_VERTICAL,
					mainLayout.getId());
			LinearLayout.LayoutParams sizeImvUser = new LinearLayout.LayoutParams(
					120, 120);

			subLayout.setWeightSum(2);
			wrap_0.weight = 1;
			subLayout.addView(isConnected, wrap_0);
			subLayout.addView(imv_message, wrap_0);

			mainLayout.addView(imv_user, sizeImvUser);
			mainLayout.addView(txv_name, paramTxv);
			mainLayout.addView(subLayout, paramSubLayout);
			addView(mainLayout);

		}

	}

	class CustomAdapterFriends extends BaseAdapter {
		private Context context;
		private List<Friend> friends;

		// Constructeur
		public CustomAdapterFriends(Context _context, List<Friend> _friends) {
			this.context = _context;
			this.friends = _friends;
		}

		public int getCount() {
			return friends.size();
		}

		public Object getItem(int position) {
			return friends.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Friend friend = friends.get(position);
			View v = null;
			// Change la couleur du background de l'item, un item sur deux
			v = new CustomAdapterViewFriend(this.context, friend);
			// v.setBackgroundColor((position % 2) == 1 ? Color.rgb(204,204,204)
			// : Color.WHITE);
			return v;
		}
	}

	// Adapter définissant la façon dont est affiché chaque item de la liste
	class CustomAdapterViewAnimal extends LinearLayout {

		public CustomAdapterViewAnimal(Context context, Animal animal) {
			super(context);
			setId(animal.id);
			setOrientation(LinearLayout.HORIZONTAL);

			// Instanciation de l'image utilisateur
			ImageView imv_user = new ImageView(context);
			imv_user.setImageDrawable(getResources().getDrawable(
					R.drawable.img_defaultanimal));

			// Instanciation du nom de l'utilisateur
			TextView txv_name = new TextView(context);
			txv_name.setText(animal.name);
			txv_name.setPadding(15, 0, 0, 0);
			txv_name.setTextColor(getResources().getColor(R.color.grey_color));
			txv_name.setTypeface(Arimo);
			txv_name.setTextSize(20);
			txv_name.setGravity(Gravity.CENTER_VERTICAL);

			LinearLayout.LayoutParams paramTxv = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

			LinearLayout.LayoutParams sizeImvUser = new LinearLayout.LayoutParams(
					120, 120);

			addView(imv_user, sizeImvUser);
			addView(txv_name, paramTxv);

		}

	}

	// Adapter de la ListView contenant les animaux
	class CustomAdapterAnimals extends BaseAdapter {
		private Context context;
		private List<Animal> animals;

		// Constructeur
		public CustomAdapterAnimals(Context _context, List<Animal> _animals) {
			this.context = _context;
			this.animals = _animals;
		}

		public int getCount() {
			return animals.size();
		}

		public Object getItem(int position) {
			return animals.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Animal animal = animals.get(position);
			View v = null;
			// Change la couleur du background de l'item, un item sur deux
			v = new CustomAdapterViewAnimal(this.context, animal);
			// v.setBackgroundColor((position % 2) == 1 ? Color.rgb(204,204,204)
			// : Color.WHITE);
			return v;
		}
	}

	// Adapter définissant la façon dont est affiché chaque item de la liste
	class CustomAdapterViewMessage extends LinearLayout {

		public CustomAdapterViewMessage(Context context, Message message) {
			super(context);
			setId(message.id);
			setOrientation(LinearLayout.HORIZONTAL);

			LinearLayout textLayout = new LinearLayout(context);
			textLayout.setOrientation(LinearLayout.VERTICAL);

			// Instanciation de l'image utilisateur
			ImageView imv_user = new ImageView(context);
			imv_user.setImageDrawable(getResources().getDrawable(
					R.drawable.img_defaultuser));

			// Instanciation du nom du destinataire
			TextView txv_name = new TextView(context);
			txv_name.setText(message.name);
			txv_name.setPadding(15, 0, 0, 0);
			txv_name.setTextColor(getResources().getColor(R.color.grey_color));
			txv_name.setTypeface(Arimo);
			txv_name.setTextSize(20);

			// Instanciation du contenu du message
			TextView txv_content = new TextView(context);
			String content2 = "";
			if (message.content.length() >= 37) {
				content2 = message.content.substring(0, 37) + "...";
			} else {
				content2 = message.content;
			}
			txv_content.setText(content2);
			txv_content.setPadding(15, 0, 0, 0);
			txv_content.setTextColor(getResources()
					.getColor(R.color.grey_color));
			txv_content.setTypeface(Arimo);
			txv_content.setTextSize(14);

			LinearLayout.LayoutParams paramTxv = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			LinearLayout.LayoutParams paramTxv2 = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
			LinearLayout.LayoutParams sizeImvUser = new LinearLayout.LayoutParams(
					120, 120);

			addView(imv_user, sizeImvUser);
			textLayout.addView(txv_name, paramTxv);
			textLayout.addView(txv_content, paramTxv);
			textLayout.setGravity(Gravity.CENTER_VERTICAL);
			addView(textLayout, paramTxv2);

		}

	}

	// Adapter de la ListView contenant les animaux
	class CustomAdapterMessages extends BaseAdapter {
		private Context context;
		private List<Message> messages;

		// Constructeur
		public CustomAdapterMessages(Context _context, List<Message> _messages) {
			this.context = _context;
			this.messages = _messages;
		}

		public int getCount() {
			return messages.size();
		}

		public Object getItem(int position) {
			return messages.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Message message = messages.get(position);
			View v = null;
			// Change la couleur du background de l'item, un item sur deux
			v = new CustomAdapterViewMessage(this.context, message);
			// v.setBackgroundColor((position % 2) == 1 ? Color.rgb(204,204,204)
			// : Color.WHITE);
			return v;
		}
	}

	// Adapter de la ListView contenant les notifications
	class CustomAdapterNotifications extends BaseAdapter {
		private Context context;
		private List<Notification> notifications;

		// Constructeur
		public CustomAdapterNotifications(Context _context,
				List<Notification> _notifications) {
			this.context = _context;
			this.notifications = _notifications;
		}

		public int getCount() {
			return notifications.size();
		}

		public Object getItem(int position) {
			return notifications.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			return notifications.get(position).type;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Notification notification = notifications.get(position);
			View view = convertView;

			int theType = getItemViewType(position);
			if (view == null) {
				ViewHolder holder = new ViewHolder();
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				if (theType == 0) {
					view = vi.inflate(R.layout.notification_add_animal, null);

					holder.imageWho = (ImageView) view
							.findViewById(R.id.imageWho);
					holder.imageWho
							.setBackgroundResource(R.drawable.img_defaultuser);

					holder.textWho = (TextView) view.findViewById(R.id.textWho);
					holder.textWho.setText(notification.who);

					holder.textWhat = (TextView) view
							.findViewById(R.id.textWhom);
					holder.textWhat.setText("A ajouté " + notification.toWhom);

					holder.imageWhom = (ImageView) view
							.findViewById(R.id.imageWhom);
					holder.imageWhom
							.setBackgroundResource(R.drawable.img_defaultanimal);

					holder.date = (TextView) view.findViewById(R.id.textDate);
					holder.date.setText(notification.date + " - "
							+ notification.hour);

					holder.nbComm = (TextView) view.findViewById(R.id.textComm);
					holder.nbComm.setText(" (" + notification.nbComm + ") ");

					holder.nbLike = (TextView) view.findViewById(R.id.textLike);
					holder.nbLike.setText(" (" + notification.nbLike + ") ");

				} else if (theType == 1) {
					view = vi.inflate(R.layout.notification_new_member, null);

					holder.imageWho = (ImageView) view
							.findViewById(R.id.imageWho);
					holder.imageWho
							.setBackgroundResource(R.drawable.img_defaultuser);

					holder.textWho = (TextView) view.findViewById(R.id.textWho);
					holder.textWho.setText(notification.who);

					holder.textWhat = (TextView) view
							.findViewById(R.id.textWhom);
					holder.textWhat.setText("Nouveau membre sur Animalio");

					holder.date = (TextView) view.findViewById(R.id.textDate);
					holder.date.setText(notification.date + " - "
							+ notification.hour);

					holder.nbComm = (TextView) view.findViewById(R.id.textComm);
					holder.nbComm.setText(" (" + notification.nbComm + ") ");

					holder.nbLike = (TextView) view.findViewById(R.id.textLike);
					holder.nbLike.setText(" (" + notification.nbLike + ") ");
				} else if (theType == 2) {
					view = vi.inflate(R.layout.notification_new_friend, null);

					holder.imageWho = (ImageView) view
							.findViewById(R.id.imageWho);
					holder.imageWho
							.setBackgroundResource(R.drawable.img_defaultuser);

					holder.textWho = (TextView) view.findViewById(R.id.textWho);
					holder.textWho.setText(notification.who);

					holder.textWhat = (TextView) view
							.findViewById(R.id.textWhom);
					holder.textWhat.setText("Et " + notification.toWhom
							+ " sont maintenant amis");

					holder.imageWhom = (ImageView) view
							.findViewById(R.id.imageWhom);
					holder.imageWhom
							.setBackgroundResource(R.drawable.img_defaultuser);

					holder.date = (TextView) view.findViewById(R.id.textDate);
					holder.date.setText(notification.date + " - "
							+ notification.hour);

					holder.nbComm = (TextView) view.findViewById(R.id.textComm);
					holder.nbComm.setText(" (" + notification.nbComm + ") ");

					holder.nbLike = (TextView) view.findViewById(R.id.textLike);
					holder.nbLike.setText(" (" + notification.nbLike + ") ");
				} else if (theType == 3) {
					view = vi.inflate(R.layout.notification_photo_event, null);

					holder.imageWho = (ImageView) view
							.findViewById(R.id.imageWho);
					holder.imageWho
							.setBackgroundResource(R.drawable.img_defaultuser);

					holder.textWho = (TextView) view.findViewById(R.id.textWho);
					holder.textWho.setText(notification.who);

					holder.textWhat = (TextView) view
							.findViewById(R.id.textWhom);
					holder.textWhat.setText("A lié une photo de "
							+ notification.toWhom + "\nà l'évènement "
							+ notification.event);

					holder.imageWhom = (ImageView) view
							.findViewById(R.id.imageWhom);
					holder.imageWhom
							.setBackgroundResource(R.drawable.img_defaultanimal);

					holder.date = (TextView) view.findViewById(R.id.textDate);
					holder.date.setText(notification.date + " - "
							+ notification.hour);

					holder.nbComm = (TextView) view.findViewById(R.id.textComm);
					holder.nbComm.setText(" (" + notification.nbComm + ") ");

					holder.nbLike = (TextView) view.findViewById(R.id.textLike);
					holder.nbLike.setText(" (" + notification.nbLike + ") ");
				} else if (theType == 4) {
					view = vi.inflate(R.layout.notification_new_member, null);

					holder.imageWho = (ImageView) view
							.findViewById(R.id.imageWho);
					holder.imageWho
							.setBackgroundResource(R.drawable.img_defaultanimal);

					holder.textWho = (TextView) view.findViewById(R.id.textWho);
					holder.textWho.setText(notification.event);

					String detail_event = "";
					if (notification.detail_event.length() >= 77) {
						detail_event = notification.detail_event.substring(0,
								77) + "...";
					} else {
						detail_event = notification.detail_event;
					}
					holder.textWhat = (TextView) view
							.findViewById(R.id.textWhom);
					holder.textWhat.setText(detail_event);

					holder.date = (TextView) view.findViewById(R.id.textDate);
					holder.date.setText(notification.date + " - "
							+ notification.hour);

					holder.nbComm = (TextView) view.findViewById(R.id.textComm);
					holder.nbComm.setText(" (" + notification.nbComm + ") ");

					holder.nbLike = (TextView) view.findViewById(R.id.textLike);
					holder.nbLike.setText(" (" + notification.nbLike + ") ");
				}
				view.setTag(holder);
			}
			return view;
		}

		@Override
		public int getViewTypeCount() {
			return 5; // return 5, you have five types that the getView() method
						// will return
		}
	}

	/**
	 *  Add items into spinner Type
	 */
	public void addItemsTypeOnSpinner(int idType) {
		//TODO Faire l'ajout des type dynamiquement par la suite.
		Spinner spinner2 = (Spinner) findViewById(R.id.update_spinner_type_id);
		List<String> list = new ArrayList<String>();
		list.add("Chien");
		list.add("Chat");
		list.add("Furet");
		list.add("Rongeur et lapin");
		list.add("Oiseau");
		list.add("Reptile");
		list.add("Cheval");
		list.add("Poisson");
		list.add("Basse-cour");
		list.add("Autres");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(dataAdapter);
		spinner2.setSelection(idType, true);
	}
	
	/**
	 *  Add items into spinner Type
	 */
	public void addItemsRaceOnSpinner() {
		Spinner spinner2 = (Spinner) findViewById(R.id.update_spinner_race_id);
		List<String> list = new ArrayList<String>();
		list.add("Test");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(dataAdapter);
	}
	  
	/**
	 *  Add items into spinner Country
	 */
	public void addItemsCountryOnSpinner(int idCountry) {
		Spinner spinner2 = (Spinner) findViewById(R.id.update_spinner_p_pays_id);
		
		//Connexion a la bdd interne
		InstallSQLiteBase firstInstallBdd = new InstallSQLiteBase(getApplicationContext());
		SQLiteDatabase formationDB = firstInstallBdd.getReadableDatabase(); 
		Cursor curseur = formationDB.rawQuery("SELECT * FROM country", null);
		
		List<String> list = new ArrayList<String>();
		
		while(curseur.moveToNext()) {
			list.add(curseur.getString(1));
		}
		
		firstInstallBdd.close();
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(dataAdapter);
		spinner2.setSelection(idCountry, true);
	}
	
	/**
	 *  Add items into spinner Province
	 */
	public void addItemsProvinceOnSpinner(int idCity, int idCountry) {
		List<String> list = new ArrayList<String>();
		
		if(idCountry == 1){
		//Connexion a la bdd interne
		//on affiche le spinner dep
		InstallSQLiteBase firstInstallBdd = new InstallSQLiteBase(getApplicationContext());
		SQLiteDatabase formationDB = firstInstallBdd.getReadableDatabase(); 
		Cursor curseur = formationDB.rawQuery("SELECT province_name FROM provinces", null);
		
		while(curseur.moveToNext()) {
			list.add(curseur.getString(0));
		}
		
		firstInstallBdd.close();
		}else{
			list.add("");
		}
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		upd_dep.setAdapter(dataAdapter);
		
		if(idCountry == 1){
			upd_dep.setSelection(userProvinceId, true);
		}
	}
	
	/**
	 *  Add items into spinner Cities
	 */
	public void addItemsCityOnSpinner(int idCity, int idCountry) {
		List<String> list = new ArrayList<String>();
		
		if(idCountry == 1){
			//Connexion a la bdd interne
			//on affiche le spinner dep
			InstallSQLiteBase firstInstallBdd = new InstallSQLiteBase(getApplicationContext());
			SQLiteDatabase formationDB = firstInstallBdd.getReadableDatabase(); 
			Cursor curseur = formationDB.rawQuery("SELECT province_name FROM provinces", null);
			
			while(curseur.moveToNext()) {
				list.add(curseur.getString(0));
			}
			
			firstInstallBdd.close();
		}else{
			list.add("");
		}
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		upd_ville.setAdapter(dataAdapter);
		
		if(idCountry == 1){
			upd_ville.setSelection(idCity, true);
		}
	}
	
	private class ViewHolder {
		ImageView imageWho;
		ImageView imageWhom;
		ImageView imageLike;
		ImageView imageComm;
		TextView textWho;
		TextView textWhat;
		TextView date;
		TextView nbComm;
		TextView nbLike;
	}
}