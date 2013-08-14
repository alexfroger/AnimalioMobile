package fr.activity.animaliomobile;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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
import fr.library.animaliomobile.AddAnimalDialog;
import fr.library.animaliomobile.Animal;
import fr.library.animaliomobile.ConnectionDialog;
import fr.library.animaliomobile.ConnectionWebservicePHP;
import fr.library.animaliomobile.ConnectionWebservicePHPPicture;
import fr.library.animaliomobile.ConnectionWebservicePHPProfile;
import fr.library.animaliomobile.DownloadImage;
import fr.library.animaliomobile.FormattedDateFr;
import fr.library.animaliomobile.Friend;
import fr.library.animaliomobile.InstallSQLiteBase;
import fr.library.animaliomobile.Message;
import fr.library.animaliomobile.Notification;
import fr.library.animaliomobile.Picture;
import fr.library.animaliomobile.RoundedImageView;
import fr.library.animaliomobile.TypefaceSpan;

public class Profiles extends FragmentActivity {
	private int result_code;

	// Button de l'activité courante
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
	
	private Bitmap profilImg;

	// Button bar de bas
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

	// Preference
	public static String idUser;
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

	// Bundle
	private static int pAnimalId;
	private static String pAnimalName;
	private static int pAnimalPosition;
	private static Boolean pAnimalUpdate;
	private static String pAnimalUpdateName;
	private static Boolean pAnimalDelete;
	private static String pAnimalDeleteName;

	private static int pFriendId;
	private static String pFriendName;
	private static int pFriendPosition;
	private static Boolean pFriendDelete;
	private static String pFriendDeleteName;

	public static Animal infoAnimal;
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

	public static Calendar myCalendar;
	public static DatePickerDialog.OnDateSetListener dateUpdbirthday;
	public static DatePickerDialog.OnDateSetListener dateUpdAnbirthday;
	public static DatePickerDialog.OnDateSetListener dateUpdAnDeath;

	private Thread splashTread;

	// Arrays retour webservice
	private ArrayList<JSONArray> arrayListReturn;
	private JSONArray arrayListMsg;
	private JSONArray arrayListAnimals;
	private JSONArray arrayListFriend;
	private JSONArray arrayListProfilAnimal;
	private JSONArray arrayListProfilFriend;
	private JSONArray arrayListProfilAnimalUpdate;
	private JSONArray arrayListProfilUpdate;
	private JSONArray arrayListProvince;
	private JSONArray userCityDefaut;
	private JSONArray arrayListUserCity;
	private JSONArray arrayAnimalInfo;
	private JSONArray arrayListDeleteAnimal;
	private JSONArray arrayListDeleteUserFriend;

	// Animal
	private ListView lsv_animals_list;
	public static CustomAdapterAnimals adapter_animals_list;
	public static ArrayList<Animal> animals;

	// Friend
	private ListView lsv_friends_list;
	public static CustomAdapterFriends adapter_friends_list;
	private ArrayList<Friend> friends;

	private Boolean isUpdateProfil = false;
	private Boolean isDeleteProfil = false;

	private ConnectionWebservicePHPProfile calcul;
	private ConnectionWebservicePHPPicture uploadImage;
	private Thread thread;
	private Thread thread1;
	private Handler handler;

	private Boolean isFirstUseSelected = true;
	public static Context context;

	// Show add Animal
	private FragmentManager fm;
	private AddAnimalDialog addAnimal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_profiles);

		context = getApplicationContext();
		// On Récupére les préférences utilisateur si elle existe
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		// On Récupére les préférences utilisateur si elle existe
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

		// On récupère les variables passer par une autre vue
		Bundle extra = getIntent().getExtras();
		typeProfil = extra.getInt("typeProfil");

		pAnimalId = extra.getInt("animalId");
		pAnimalName = extra.getString("animalName");

		pFriendId = extra.getInt("friendId");
		pFriendName = extra.getString("friendName");

		imv_profil = (RoundedImageView)findViewById(R.id.imv_profil);
//		imv_profil.setImageDrawable(getResources().getDrawable(R.drawable.ic_profil_undefined));
		imv_cover = (ImageView) findViewById(R.id.imv_cover);
		imv_cover.setImageDrawable(getResources().getDrawable(
				R.drawable.profil_test));
		imv_cover.setScaleType(ScaleType.FIT_XY);

		Arimo = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"Arimo-Regular.ttf");
		Lobster = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"Lobster.otf");

		// On instancie le formattage des dates pour les != formulaires
		myCalendar = Calendar.getInstance();

		dateUpdbirthday = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);

				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

				String myFormat = "dd-MM-yyyy"; // In which you need put here
				SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

				upd_birthday.setText(sdf.format(myCalendar.getTime()));
			}
		};

		dateUpdAnbirthday = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);

				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

				String myFormat = "dd-MM-yyyy"; // In which you need put here
				SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

				edt_upd_an_birthday.setText(sdf.format(myCalendar.getTime()));
			}
		};

		dateUpdAnDeath = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);

				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

				String myFormat = "dd-MM-yyyy"; // In which you need put here
				SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

				edt_upd_an_death.setText(sdf.format(myCalendar.getTime()));
			}
		};

		vf_profil = (ViewFlipper) findViewById(R.id.vf_profil);
		param_lsv = new ListView.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		// Si connexion existe on charge le contenu en fonction du profil
		if (ConnectionWebservicePHP.haveNetworkConnection(this)) {
			switch (typeProfil) {
			case 0: // Profil utilisateur
				result_code = 0;
				/*
				 * Création des boutons
				 */

				// Bouton Galerie
				btn_galerie = (Button) findViewById(R.id.btn_galerie);
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

				// On recupere les informations
				calcul = new ConnectionWebservicePHPProfile(1, "listObject",
						this);
				calcul.execute();
				
				handler = new Handler();

				thread = new Thread() {
					@Override
					public void run() {
						if (calcul.isFinish == true) {

							try {
								// Récupere les information du profil
								// 0 => "listMsg", 1 =>listAnimals, 2
								// =>AnimalInfo,
								// 3 =>listFriend, 4 =>updateProfil
								arrayListReturn = calcul.get();

								arrayListMsg = arrayListReturn.get(0);
								arrayListAnimals = arrayListReturn.get(1);
								arrayListFriend = arrayListReturn.get(2);

								arrayListProvince = arrayListReturn.get(3);
								JSONObject infoWebserviveReturn = arrayListProvince
										.getJSONObject(0);
								userProvinceId = infoWebserviveReturn
										.getInt("province_id");
								userCityDefaut = arrayListReturn.get(4);
							} catch (InterruptedException e) {
								Log.e("log_ArrayReturn",
										"InterruptedException : "
												+ e.toString());
							} catch (ExecutionException e) {
								Log.e("log_ArrayReturn",
										"ExecutionException : " + e.toString());
							} catch (JSONException e) {
								Log.e("log_ArrayReturn",
										"ExecutionException : " + e.toString());
							}

							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									try {			
										
										// on ajoute les elements à la vue
										vf_profil.addView(
												createListMsg(arrayListMsg),
												param_lsv);
										positionChild[0] = 0;

										vf_profil
												.addView(
														createListAnimals(arrayListAnimals),
														param_lsv);
										positionChild[1] = 1;

										vf_profil
												.addView(
														createListFriends(arrayListFriend),
														param_lsv);
										positionChild[2] = 2;

										vf_profil.addView(
												createListNotifications(),
												param_lsv);
										positionChild[3] = 3;

										vf_profil.addView(createModification(),
												param_lsv);
										positionChild[7] = 4;

										// Style de la vue modification
										// Bouton modifier profil
										btn_profil_update = (Button) findViewById(R.id.btn_registration);
										btn_profil_update
												.setOnClickListener(eventClick);

										// Textview
										updLastname = (TextView) findViewById(R.id.update_txtView_lastname);
										updFirstname = (TextView) findViewById(R.id.update_txtView_firstname);
										updEmail = (TextView) findViewById(R.id.update_txtView_email);
										updNickname = (TextView) findViewById(R.id.update_txtView_nickname);
										// updPays =
										// (TextView)findViewById(R.id.update_p_pays_id);
										// updDep =
										// (TextView)findViewById(R.id.update_p_dep_id);
										// updVille =
										// (TextView)findViewById(R.id.update_p_ville_id);
										updBirthday = (TextView) findViewById(R.id.update_txtView_birthday);
										updPhone = (TextView) findViewById(R.id.update_txtView_phone);
										updPhoneMobile = (TextView) findViewById(R.id.update_txtView_mobile);

										// EditText
										upd_lastname = (EditText) findViewById(R.id.update_lastname);
										upd_firstname = (EditText) findViewById(R.id.update_firstname);
										upd_email = (EditText) findViewById(R.id.update_email);
										upd_nickname = (EditText) findViewById(R.id.update_nickname);
										upd_birthday = (EditText) findViewById(R.id.update_birthday);
										upd_phone = (EditText) findViewById(R.id.update_phone);
										upd_phone_mobile = (EditText) findViewById(R.id.update_phone_mobile);

										// Spinner
										// upd_pays = (Spinner)
										// findViewById(R.id.update_spinner_p_pays_id);
										// upd_dep = (Spinner)
										// findViewById(R.id.update_spinner_p_dep_id);
										// upd_ville = (Spinner)
										// findViewById(R.id.update_spinner_p_ville_id);
										// upd_pays.setOnItemSelectedListener(selectClick);
										// upd_dep.setOnItemSelectedListener(selectClick);

										// Formattage de la date de naissance
										String birthdayFormmated = FormattedDateFr
												.FormattedDateToFr(user_birthday);

										upd_lastname.setText(user_lastname);
										upd_firstname.setText(user_firstname);
										upd_email.setHint(user_email);
										upd_nickname.setText(user_nickname);
										upd_birthday.setText(birthdayFormmated);
										upd_phone.setText(user_phone);
										upd_phone_mobile
												.setText(user_phoneMobile);

										// On met la police au textview
										updLastname.setTypeface(Lobster);
										updFirstname.setTypeface(Lobster);
										updEmail.setTypeface(Lobster);
										updNickname.setTypeface(Lobster);
										// updPays.setTypeface(Lobster);
										// updDep.setTypeface(Lobster);
										// updVille.setTypeface(Lobster);
										updBirthday.setTypeface(Lobster);
										updPhone.setTypeface(Lobster);
										updPhoneMobile.setTypeface(Lobster);

										// On ajoute les informations des
										// spinners
										// addItemsCountryOnSpinner(Integer.parseInt(user_countryID));

										upd_birthday
												.setOnTouchListener(new OnTouchListener() {

													@Override
													public boolean onTouch(
															View v,
															MotionEvent event) {
														if (event.getAction() == KeyEvent.ACTION_UP) {
															new DatePickerDialog(
																	v.getContext(),
																	dateUpdbirthday,
																	myCalendar
																			.get(Calendar.YEAR),
																	myCalendar
																			.get(Calendar.MONTH),
																	myCalendar
																			.get(Calendar.DAY_OF_MONTH))
																	.show();
														}
														return false;
													}

												});
										DownloadImage openDownload = new DownloadImage(Profiles.this, imv_profil, user_avatarName);
										openDownload.downloadRoundedImageView();
										// End vue modification
									} catch (Exception e) {
										Log.e("log_Thread",
												"Thread : " + e.toString());
									}
								}
							});
						} else {
							handler.postDelayed(this, 100);
						}
					}
				};
				thread.start();
				break;

			case 1: // Profil membre

				/*
				 * Création des boutons
				 */

				// Bouton Galerie
				btn_galerie = (Button) findViewById(R.id.btn_galerie);
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

				// vf_profil.addView(createListAnimals(), param_lsv);
				// positionChild[1] = 0;
				//
				// vf_profil.addView(createListFriends(), param_lsv);
				// positionChild[2] = 1;
				//
				// vf_profil.addView(createModification(), param_lsv);
				// positionChild[4] = 2;

				break;
			case 2: // Profil ami
				result_code = 2;

				pFriendPosition = extra.getInt("friendPosition");
				/*
				 * Création des boutons
				 */

				// Bouton Galerie
				btn_galerie = (Button) findViewById(R.id.btn_galerie);
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

				// On recupere les informations
				calcul = new ConnectionWebservicePHPProfile(1, "listObject",
						this, typeProfil, pFriendId);
				calcul.execute();

				handler = new Handler();

				thread = new Thread() {
					@Override
					public void run() {
						if (calcul.isFinish == true) {

							try {
								// Récupere les information du profil
								// 0 => "listMsg", 1 =>listAnimals, 2
								// =>AnimalInfo,
								// 3 =>listFriend, 4 =>updateProfil
								arrayListReturn = new ArrayList<JSONArray>();

								arrayListMsg = new JSONArray();
								arrayListAnimals = new JSONArray();
								arrayListFriend = new JSONArray();
								arrayListProvince = new JSONArray();

								arrayListReturn = calcul.get();

								arrayListMsg = arrayListReturn.get(0);
								arrayListAnimals = arrayListReturn.get(1);
								arrayListFriend = arrayListReturn.get(2);

								arrayListProvince = arrayListReturn.get(3);
								JSONObject infoWebserviveReturn = arrayListProvince
										.getJSONObject(0);
								userProvinceId = infoWebserviveReturn
										.getInt("province_id");
								userCityDefaut = arrayListReturn.get(4);

							} catch (InterruptedException e) {
								Log.e("log_ArrayReturn",
										"InterruptedException : "
												+ e.toString());
							} catch (ExecutionException e) {
								Log.e("log_ArrayReturn",
										"ExecutionException : " + e.toString());
							} catch (JSONException e) {
								Log.e("log_ArrayReturn",
										"ExecutionException : " + e.toString());
							}

							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									try {
										// on ajoute les elements à la vue
										vf_profil
												.addView(
														createListAnimals(arrayListAnimals),
														param_lsv);
										positionChild[2] = 1;

										vf_profil
												.addView(
														createListFriends(arrayListFriend),
														param_lsv);
										positionChild[3] = 2;

										vf_profil.addView(
												createListNotifications(),
												param_lsv);
										positionChild[4] = 3;

									} catch (Exception e) {
										Log.e("log_Thread",
												"Thread : " + e.toString());
									}
								}
							});

						} else {
							handler.postDelayed(this, 100);
						}
					}
				};
				thread.start();

				break;
			case 3: // Profil animal
				result_code = 3;

				// Get return bundle
				pAnimalPosition = extra.getInt("animalPosition");
				/*
				 * Création des boutons
				 */

				// Bouton Galerie
				btn_galerie = (Button) findViewById(R.id.btn_galerie);
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

				// On vide la liste de données à envoyé si existe déjà
				data.clear();

				// On ajoute les valeurs
				data.add(new BasicNameValuePair("id_user", idUser));
				data.add(new BasicNameValuePair("id_animal", String
						.valueOf(pAnimalId)));
				data.add(new BasicNameValuePair("list_name", "AnimalInfo"));

				// On recupere les informations
				calcul = new ConnectionWebservicePHPProfile(1,
						"listObjectOther", this, data);
				calcul.execute();

				handler = new Handler();

				thread = new Thread() {
					@Override
					public void run() {
						if (calcul.isFinish == true) {
							try {
								arrayListReturn = calcul.get();

								arrayAnimalInfo = arrayListReturn.get(0);
								JSONObject animalInfo = arrayAnimalInfo
										.getJSONObject(0);
								// On instancie l'objet animal
								infoAnimal = new Animal(
										pAnimalId,
										animalInfo.getInt("user_id"),
										animalInfo.getInt("animal_race_id"),
										animalInfo.getString("animal_name"),
										animalInfo.getString("avatar_animal_name"),
										animalInfo
												.getString("animal_description"),
										animalInfo.getString("animal_birthday"),
										animalInfo.getString("animal_death"),
										animalInfo.getString("created_at"),
										animalInfo.getString("updated_at"));

							} catch (InterruptedException e) {
								Log.e("log_ArrayReturn3",
										"InterruptedExceptio3n : "
												+ e.toString());
							} catch (ExecutionException e) {
								Log.e("log_ArrayReturn3",
										"ExecutionException3 : " + e.toString());
							} catch (JSONException e) {
								Log.e("log_ArrayReturn3",
										"ExecutionException3 : " + e.toString());
							}

							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									/*
									 * Création des éléments du ViewFlipper
									 */
									vf_profil = (ViewFlipper) findViewById(R.id.vf_profil);
									ListView.LayoutParams param_lsv = new ListView.LayoutParams(
											LayoutParams.MATCH_PARENT,
											LayoutParams.MATCH_PARENT);

									vf_profil.addView(
											createModificationAnimal(),
											param_lsv);
									positionChild[8] = 0;

									// TextView
									txt_an_lastname = (TextView) findViewById(R.id.update_txtView_an_lastname);
									txt_an_description = (TextView) findViewById(R.id.update_txtView_an_description);
									txt_an_birthday = (TextView) findViewById(R.id.update_txtView_an_birthday);
									txt_an_death = (TextView) findViewById(R.id.update_txtView_an_death);

									// EditText
									edt_upd_an_birthday = (EditText) findViewById(R.id.update_an_birthday);
									edt_upd_an_death = (EditText) findViewById(R.id.update_an_death);
									edt_upd_an_lastname = (EditText) findViewById(R.id.update_an_lastname);
									edt_upd_an_description = (EditText) findViewById(R.id.update_an_description);

									// Button
									btn_upd_animal = (Button) findViewById(R.id.btn_update_animal);

									// On met la police au textview
									txt_an_lastname.setTypeface(Lobster);
									txt_an_description.setTypeface(Lobster);
									txt_an_birthday.setTypeface(Lobster);
									txt_an_death.setTypeface(Lobster);

									// Event à la touche ou au click propre a
									// cette vue
									btn_upd_animal
											.setOnClickListener(eventClick);

									String AnbirthdayFormmated = FormattedDateFr
											.FormattedDateToFr(infoAnimal.birthday);
									String AnDeathFormmated = FormattedDateFr
											.FormattedDateToFr(infoAnimal.death);

									edt_upd_an_birthday
											.setText(AnbirthdayFormmated);
									edt_upd_an_death.setText(AnDeathFormmated);
									edt_upd_an_lastname
											.setText(infoAnimal.name);
									edt_upd_an_description
											.setText(infoAnimal.description);

									edt_upd_an_birthday
											.setOnTouchListener(new OnTouchListener() {
												@Override
												public boolean onTouch(View v,
														MotionEvent event) {
													if (event.getAction() == KeyEvent.ACTION_UP) {
														new DatePickerDialog(
																v.getContext(),
																dateUpdAnbirthday,
																myCalendar
																		.get(Calendar.YEAR),
																myCalendar
																		.get(Calendar.MONTH),
																myCalendar
																		.get(Calendar.DAY_OF_MONTH))
																.show();
													}
													return false;
												}

											});

									edt_upd_an_death
											.setOnTouchListener(new OnTouchListener() {
												@Override
												public boolean onTouch(View v,
														MotionEvent event) {
													if (event.getAction() == KeyEvent.ACTION_UP) {
														new DatePickerDialog(
																v.getContext(),
																dateUpdAnDeath,
																myCalendar
																		.get(Calendar.YEAR),
																myCalendar
																		.get(Calendar.MONTH),
																myCalendar
																		.get(Calendar.DAY_OF_MONTH))
																.show();
													}
													return false;
												}

											});
								}
							});

						} else {
							handler.postDelayed(this, 100);
						}
					}
				};
				thread.start();
				break;
			}
		} else { // Sinon toast de problème
			ConnectionWebservicePHP.haveNetworkConnectionError(this);
		}

		btn_members = (Button) findViewById(R.id.btn_members);
		btn_gallery = (Button) findViewById(R.id.btn_gallery);
		btn_profil = (Button) findViewById(R.id.btn_profil);
		btn_events = (Button) findViewById(R.id.btn_events);
		btn_live = (Button) findViewById(R.id.btn_live);
		btn_photo = (Button) findViewById(R.id.btn_photo);

		btn_profil.setBackgroundResource(R.drawable.ic_profil_pressed);

		// Evt click
		btn_members.setOnClickListener(eventClick);
		btn_gallery.setOnClickListener(eventClick);
		btn_events.setOnClickListener(eventClick);
		btn_live.setOnClickListener(eventClick);
		btn_photo.setOnClickListener(eventClick);

		// On empeche le clavier de s'afficher
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		final Intent dataf = data;
		// Vérification du code de retour
		if (requestCode == result_code) {
			// Vérifie que le résultat est OK
			if (resultCode == RESULT_OK) {
				typeProfil = data.getIntExtra("typeProfil", 0);

				// Retour Vue profil Animal if update
				pAnimalUpdate = data.getBooleanExtra("animalUpdate", false);
				pAnimalUpdateName = data.getStringExtra("animalUpdateName");

				// Retour Vue profil Animal if delete
				pAnimalDelete = data.getBooleanExtra("animalDelete", false);
				pAnimalDeleteName = data.getStringExtra("animalDeleteName");

				// Les 2 premiers cas
				pAnimalPosition = data.getIntExtra("animalPosition", 0);

				// Retour Vue profil Friend if delete
				pFriendDelete = data.getBooleanExtra("friendDelete", false);
				pFriendDeleteName = data.getStringExtra("friendDeleteName");
				pFriendPosition = data.getIntExtra("friendPosition", 0);

				// Changement d'information au retour d'activity si exist
				if (pAnimalUpdate) {
					Animal animalChange = (Animal) lsv_animals_list
							.getItemAtPosition(pAnimalPosition);
					animalChange.name = pAnimalUpdateName;
					// On affecte le changement
					adapter_animals_list.notifyDataSetChanged();

					// On affiche le résultat
					Toast.makeText(getApplicationContext(),
							"Modification effectuée", Toast.LENGTH_SHORT)
							.show();
					typeProfil = data.getIntExtra("typeProfil", 0);
				}

				// Enleve l'animal au retour
				if (pAnimalDelete) {
					// On supprimer l'élément du tableau
					animals.remove(pAnimalPosition);
					// On affecte le changement
					adapter_animals_list.notifyDataSetChanged();

					Toast.makeText(
							getApplicationContext(),
							"L'animal " + pAnimalDeleteName + " à été supprimé",
							Toast.LENGTH_LONG).show();
					typeProfil = data.getIntExtra("typeProfil", 0);
				}

				// Enleve l'amis au retour
				if (pFriendDelete) {
					// On supprimer l'élément du tableau
					friends.remove(pFriendPosition);
					adapter_friends_list = new CustomAdapterFriends(this,
							friends);
					lsv_friends_list.setAdapter(adapter_friends_list);
					// On affecte le changement

					// adapter_friends_list.notifyDataSetChanged(); //Doesn't
					// work!!

					Toast.makeText(getApplicationContext(),
							"L'amis " + pFriendDeleteName + " à été supprimé",
							Toast.LENGTH_LONG).show();
					typeProfil = dataf.getIntExtra("typeProfil", 0);
				}
			}
		}

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	OnItemSelectedListener selectClick = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {

			// if(parent == upd_pays){
			// addItemsProvinceOnSpinner(position+1, isFirstUseSelected);
			// isFirstUseSelected = false;
			// }
			// if(parent == upd_dep){
			// Log.i("Log_upd_dep", "provinceS : " + (position+1) +
			// "- province : " + userProvinceId + "- city : " + user_cityID);
			// addItemsCityOnSpinner(position+1, userProvinceId, false);
			// }
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
				switch (typeProfil) {
				case 2:
					// Si l'on est dans le profil amis le btn msg permet
					// l'envoie d'un message
					// Création de l'intent
					Intent intent = new Intent(getApplicationContext(),
							MessagingService.class);
					intent.putExtra("idFriend", pFriendId);
					intent.putExtra("friendName", pFriendName);

					startActivity(intent);
					break;
				default:
					vf_profil.setDisplayedChild(positionChild[0]);
					break;
				}
			} else if (v == btn_animals_list) {
				vf_profil.setDisplayedChild(positionChild[1]);
			} else if (v == btn_friends_list) {
				vf_profil.setDisplayedChild(positionChild[2]);
			} else if (v == btn_notifications_list) {
				vf_profil.setDisplayedChild(positionChild[3]);
			} else if (v == btn_friends_requests) {
				vf_profil.setDisplayedChild(positionChild[4]);
			} else if (v == btn_send_msg) {
				// On recupere les infos de l'item
				Intent intent = new Intent(getApplicationContext(),
						MessagingService.class);
				intent.putExtra("idFriend", pFriendId);
				intent.putExtra("friendName", pFriendName);
				startActivity(intent);
			} else if (v == btn_delete_friend) {
				context = v.getContext();
				DialogInterface.OnClickListener ecouteurDialog = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int codeBouton) {
						if (codeBouton == Dialog.BUTTON_POSITIVE) {
							// Suppression de l'amis
							// On vide la liste de données à envoyé si existe
							// déjà
							data.clear();

							// On ajoute les valeurs
							data.add(new BasicNameValuePair("id_user", idUser));
							data.add(new BasicNameValuePair("id_friend", String
									.valueOf(pFriendId)));
							data.add(new BasicNameValuePair("list_name",
									"deleteUserFriend"));

							if (ConnectionWebservicePHPProfile
									.haveNetworkConnection(context)) {
								ConnectionWebservicePHPProfile calcul = new ConnectionWebservicePHPProfile(
										1, "listObjectOther", context, data);
								calcul.execute();
								try {
									arrayListReturn = calcul.get();
									arrayListDeleteUserFriend = arrayListReturn
											.get(0);

									Log.i("log_tagUserFriend",
											"arrayListDeleteUserFriend : "
													+ arrayListDeleteUserFriend);
									JSONObject infoWebserviveReturn;
									try {
										infoWebserviveReturn = arrayListDeleteUserFriend
												.getJSONObject(0);

										if (infoWebserviveReturn.getInt("isOk") == 0) {
											// Erreur de suppression de l'animal
											Toast.makeText(
													context,
													"Impossible de supprimer cette ami",
													Toast.LENGTH_SHORT).show();
										} else {
											isDeleteProfil = true;
											// Supprimé avec succès on redirige
											// vers la vue précèdente
											// Création de l'intent
											Intent intent = new Intent();

											// On rajoute le nom saisie dans
											// l'intent
											intent.putExtra("friendDelete",
													isDeleteProfil);
											intent.putExtra("friendDeleteName",
													pFriendName);
											intent.putExtra("friendPosition",
													pFriendPosition);

											// On retourne le résultat avec
											// l'intent
											setResult(RESULT_OK, intent);
											// On termine cette activité
											finish();
										}
									} catch (JSONException e) {
										Log.e("log_deleteUserFriend",
												"JSONException :"
														+ e.toString());
									}
								} catch (InterruptedException e) {
									Log.e("log_deleteUserFriend",
											"InterruptedException :"
													+ e.toString());
								} catch (ExecutionException e) {
									Log.e("log_deleteUserFriend",
											"ExecutionException :"
													+ e.toString());
								}
							} else { // Sinon toast de problème
								ConnectionWebservicePHPProfile
										.haveNetworkConnectionError(context);
							}
						}
						if (codeBouton == Dialog.BUTTON_NEGATIVE) {
							// On ferme la fenêtre
							dialog.cancel();
						}
					}
				};

				AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
				ad.setTitle("Suppression de " + pFriendName);
				ad.setPositiveButton("Comfirmer", ecouteurDialog);
				ad.setNegativeButton("Annuler", ecouteurDialog);
				ad.show();
			} else if (v == btn_user_modification) {
				vf_profil.setDisplayedChild(positionChild[7]);
			} else if (v == btn_animal_modification) {
				vf_profil.setDisplayedChild(positionChild[8]);
			} else if (v == btn_delete_animal) {
				context = v.getContext();
				DialogInterface.OnClickListener ecouteurDialog = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int codeBouton) {
						if (codeBouton == Dialog.BUTTON_POSITIVE) {
							// Suppression de l'animal
							// On vide la liste de données à envoyé si existe
							// déjà
							data.clear();

							// On ajoute les valeurs
							data.add(new BasicNameValuePair("id_user", idUser));
							data.add(new BasicNameValuePair("id_animal", String
									.valueOf(infoAnimal.id)));
							data.add(new BasicNameValuePair("list_name",
									"deleteUserAnimal"));

							if (ConnectionWebservicePHPProfile
									.haveNetworkConnection(context)) {
								ConnectionWebservicePHPProfile calcul = new ConnectionWebservicePHPProfile(
										1, "listObjectOther", context, data);
								calcul.execute();
								try {
									arrayListReturn = calcul.get();
									arrayListDeleteAnimal = arrayListReturn
											.get(0);

									JSONObject infoWebserviveReturn;
									try {
										infoWebserviveReturn = arrayListDeleteAnimal
												.getJSONObject(0);

										if (infoWebserviveReturn.getInt("isOk") == 0) {
											// Erreur de suppression de l'animal
											Toast.makeText(
													context,
													"L'animal n'as pas pu être supprimé",
													Toast.LENGTH_LONG).show();
										} else {
											isDeleteProfil = true;
											// Supprimé avec succès on redirige
											// vers la vue précèdente
											// Création de l'intent
											Intent intent = new Intent();

											// On rajoute le nom saisie dans
											// l'intent
											intent.putExtra("animalDelete",
													isDeleteProfil);
											intent.putExtra("animalDeleteName",
													infoAnimal.name);
											intent.putExtra("animalPosition",
													pAnimalPosition);

											// On retourne le résultat avec
											// l'intent
											setResult(RESULT_OK, intent);
											// On termine cette activité
											finish();
										}
									} catch (JSONException e) {
										Log.e("log_deleteUserAnimal",
												"JSONException :"
														+ e.toString());
									}
								} catch (InterruptedException e) {
									Log.e("log_deleteUserAnimal",
											"InterruptedException :"
													+ e.toString());
								} catch (ExecutionException e) {
									Log.e("log_deleteUserAnimal",
											"ExecutionException :"
													+ e.toString());
								}
							} else { // Sinon toast de problème
								ConnectionWebservicePHPProfile
										.haveNetworkConnectionError(context);
							}
						}
						if (codeBouton == Dialog.BUTTON_NEGATIVE) {
							// On ferme la fenêtre
							dialog.cancel();
						}
					}
				};

				AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
				ad.setTitle("Suppression de " + infoAnimal.name);
				ad.setPositiveButton("Comfirmer", ecouteurDialog);
				ad.setNegativeButton("Annuler", ecouteurDialog);
				ad.show();
			} else if (v == btn_upd_animal) {
				// Modification du profil
				// si ou ou plusieurs champs sont vides
				if (edt_upd_an_lastname.getText().toString().equals("")
						|| edt_upd_an_birthday.getText().toString().equals("")) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Veuillez remplir les champs obligatoires",
							Toast.LENGTH_LONG);
					t.setGravity(Gravity.BOTTOM, 0, 40);
					t.show();
				} else {
					// On vide la liste de données à envoyé si existe déjà
					data.clear();

					// On ajoute les valeurs
					data.add(new BasicNameValuePair("id_user", idUser));
					data.add(new BasicNameValuePair("upd_animalId", String
							.valueOf(infoAnimal.id)));
					data.add(new BasicNameValuePair("list_name",
							"updateProfilAnimal"));
					data.add(new BasicNameValuePair("upd_an_lastname",
							edt_upd_an_lastname.getText().toString()));
					data.add(new BasicNameValuePair("upd_an_description",
							edt_upd_an_description.getText().toString()));
					data.add(new BasicNameValuePair("upd_an_birthday",
							edt_upd_an_birthday.getText().toString()));
					data.add(new BasicNameValuePair("upd_an_death",
							edt_upd_an_death.getText().toString()));

					// Instancie la connection au webservice en thread
					if (ConnectionWebservicePHPProfile.haveNetworkConnection(v
							.getContext())) {
						ConnectionWebservicePHPProfile calcul = new ConnectionWebservicePHPProfile(
								1, "listObjectOther", v.getContext(), data);
						calcul.execute();
						try {
							arrayListReturn = calcul.get();
							arrayListProfilAnimalUpdate = arrayListReturn
									.get(0);

							JSONObject infoWebserviveReturn;
							try {
								infoWebserviveReturn = arrayListProfilAnimalUpdate
										.getJSONObject(0);

								if (infoWebserviveReturn.getInt("isOk") == 0) {
									// Erreur de modification du profil
									Toast.makeText(v.getContext(),
											"Erreur d'enregistrement",
											Toast.LENGTH_LONG).show();
								} else { // Profil modifié
									// On update l'objet animal
									infoAnimal.raceId = infoWebserviveReturn
											.getInt("animal_race_id");
									infoAnimal.name = infoWebserviveReturn
											.getString("animal_name");
									infoAnimal.description = infoWebserviveReturn
											.getString("animal_description");
									infoAnimal.birthday = infoWebserviveReturn
											.getString("animal_birthday");
									infoAnimal.death = infoWebserviveReturn
											.getString("animal_death");
									infoAnimal.createdAt = infoWebserviveReturn
											.getString("created_at");
									infoAnimal.updatedAt = infoWebserviveReturn
											.getString("updated_at");

									Toast.makeText(v.getContext(),
											"Profil mis à jour avec succès",
											Toast.LENGTH_LONG).show();

									// On passe la variable d'update à true
									isUpdateProfil = true;
									// Formattage de la date de naissance et
									// décès
									Date dateFormatBirthday = null;
									Date dateFormatDeath = null;
									try {
										dateFormatBirthday = new SimpleDateFormat(
												"yyyy-MM-dd")
												.parse(infoAnimal.birthday);
										dateFormatDeath = new SimpleDateFormat(
												"yyyy-MM-dd")
												.parse(infoAnimal.death);
									} catch (ParseException e1) {
										Log.e("log_profilModif",
												"Date Formatted : "
														+ e1.toString());
									}
									String dateFormatBirthdayFormmated = new SimpleDateFormat(
											"dd-MM-yyyy")
											.format(dateFormatBirthday);
									String dateFormatDeathFormmated = new SimpleDateFormat(
											"dd-MM-yyyy")
											.format(dateFormatDeath);

									edt_upd_an_birthday
											.setHint(dateFormatBirthdayFormmated);
									edt_upd_an_death
											.setHint(dateFormatDeathFormmated);
									edt_upd_an_lastname
											.setHint(infoAnimal.name);
									edt_upd_an_description
											.setHint(infoAnimal.description);
								}
							} catch (JSONException e) {
								Log.e("log_listObjectUpdProfilAnimal",
										"JSONException :" + e.toString());
							}
						} catch (InterruptedException e) {
							Log.e("log_listObjectUpdProfilAnimal",
									"InterruptedException :" + e.toString());
						} catch (ExecutionException e) {
							Log.e("log_listObjectUpdProfilAnimal",
									"ExecutionException :" + e.toString());
						}
					} else { // Sinon toast de problème
						ConnectionWebservicePHPProfile
								.haveNetworkConnectionError(v.getContext());
					}
				}
			} else if (v == btn_profil_update) {
				// Modification du profil
				// si ou ou plusieurs champs sont vides
				if (upd_lastname.getText().toString().equals("")
						|| upd_firstname.getText().toString().equals("")
						|| upd_nickname.getText().toString().equals("")) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Veuillez remplir les champs obligatoires",
							Toast.LENGTH_LONG);
					t.setGravity(Gravity.BOTTOM, 0, 40);
					t.show();
				} else {
					// On vide la liste de données à envoyé si existe déjà
					data.clear();

					// On ajoute les valeurs
					data.add(new BasicNameValuePair("id_user", idUser));
					data.add(new BasicNameValuePair("list_name", "updateProfil"));
					data.add(new BasicNameValuePair("upd_lastname",
							upd_lastname.getText().toString()));
					data.add(new BasicNameValuePair("upd_firstname",
							upd_firstname.getText().toString()));
					data.add(new BasicNameValuePair("upd_nickname",
							upd_nickname.getText().toString()));
					// data.add(new BasicNameValuePair("upd_country",
					// upd_pays.getSelectedItem().toString()));
					// data.add(new BasicNameValuePair("upd_city",
					// upd_ville.getSelectedItem().toString()));
					data.add(new BasicNameValuePair("upd_birthday",
							upd_birthday.getText().toString()));
					data.add(new BasicNameValuePair("upd_phone", upd_phone
							.getText().toString()));
					data.add(new BasicNameValuePair("upd_phone_mobile",
							upd_phone_mobile.getText().toString()));

					// Instancie la connection au webservice en thread
					if (ConnectionWebservicePHPProfile.haveNetworkConnection(v
							.getContext())) {
						ConnectionWebservicePHPProfile calcul = new ConnectionWebservicePHPProfile(
								1, "listObjectOther", v.getContext(), data);
						calcul.execute();
						try {
							arrayListReturn = calcul.get();

							arrayListProfilUpdate = arrayListReturn.get(0);

							JSONObject infoWebserviveReturn;
							try {
								infoWebserviveReturn = arrayListProfilUpdate
										.getJSONObject(0);

								if (infoWebserviveReturn.getInt("isOk") == 0) {
									// Erreur de modification du profil
									Toast.makeText(v.getContext(),
											"Erreur d'enregistrement",
											Toast.LENGTH_LONG).show();
								} else { // Profil modifié
									// On stock les infos utilisateurs dans des
									// preferences
									SharedPreferences preferences = PreferenceManager
											.getDefaultSharedPreferences(v
													.getContext());
									SharedPreferences.Editor editor = preferences
											.edit();
									editor.putString("idUser",
											infoWebserviveReturn
													.getString("id_user"));
									editor.putString("humorID",
											infoWebserviveReturn
													.getString("humor_id"));
									editor.putString("cityID",
											infoWebserviveReturn
													.getString("city_id"));
									editor.putString("countryID",
											infoWebserviveReturn
													.getString("country_id"));
									editor.putString("lastname",
											infoWebserviveReturn
													.getString("lastname"));
									editor.putString("firstname",
											infoWebserviveReturn
													.getString("firstname"));
									editor.putString("nickname",
											infoWebserviveReturn
													.getString("nickname"));
									editor.putString("email",
											infoWebserviveReturn
													.getString("email"));
									editor.putString("avatarName",
											infoWebserviveReturn
													.getString("avatar_name"));
									editor.putString("password",
											infoWebserviveReturn
													.getString("password"));
									editor.putString("civility",
											infoWebserviveReturn
													.getString("civility"));
									editor.putString("birthday",
											infoWebserviveReturn
													.getString("birthday"));
									editor.putString("phone",
											infoWebserviveReturn
													.getString("phone"));
									editor.putString("phoneMobile",
											infoWebserviveReturn
													.getString("phone_mobile"));
									editor.putString("onNewsletter",
											infoWebserviveReturn
													.getString("on_newsletter"));
									editor.putString("onMobile",
											infoWebserviveReturn
													.getString("on_mobile"));
									editor.putString(
											"isLoggedFacebook",
											infoWebserviveReturn
													.getString("is_logged_facebook"));
									editor.putString("isBlacklist",
											infoWebserviveReturn
													.getString("is_blacklist"));
									editor.putString("createdAt",
											infoWebserviveReturn
													.getString("created_at"));
									editor.putString("updatedAt",
											infoWebserviveReturn
													.getString("updated_at"));
									editor.commit();

									Toast.makeText(v.getContext(),
											"Profil modifié mis à jour",
											Toast.LENGTH_LONG).show();
								}
							} catch (JSONException e) {
								Log.e("log_UpdateProfil",
										"JSONException :" + e.toString());
							}
						} catch (InterruptedException e) {
							Log.e("log_UpdateProfil", "InterruptedException :"
									+ e.toString());
						} catch (ExecutionException e) {
							Log.e("log_UpdateProfil", "ExecutionException :"
									+ e.toString());
						}
					} else { // Sinon toast de problème
						ConnectionWebservicePHPProfile
								.haveNetworkConnectionError(v.getContext());
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
				Intent intent = new Intent(getApplicationContext(), ImageUpload.class);
				startActivity(intent);
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

		View displayView = inflater.inflate(R.layout.profile_animal_update,
				null);

		return displayView;
	}

	public ListView createListMsg(JSONArray arrayListMsg1) {
		final ListView lsv_msg = new ListView(this);
		ArrayList<Message> messages = new ArrayList<Message>();

		try {
			// On ajoute les message que l'on à reçu
			for (int i = 0; i < arrayListMsg1.length(); i++) {
				JSONObject infoWebserviveReturn = arrayListMsg1
						.getJSONObject(i);
				
				Log.i("log_infoWebserviveReturn", "infoWebserviveReturn : "
						+ infoWebserviveReturn);
				if (!infoWebserviveReturn.isNull("user_id_to")) {
					Message message1 = new Message(
							infoWebserviveReturn.getInt("user_id_to"),
							infoWebserviveReturn.getString("nickname"),
							infoWebserviveReturn.getString("message"),
							infoWebserviveReturn.getString("avatar_name")
							);
					messages.add(message1);
				} else { // Aucun message de creer
					Message message0 = new Message(0, "Aucun message", "", "");
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
		lsv_animals_list = new ListView(this);
		animals = new ArrayList<Animal>();
		try {
			// Création d'une ligne ou on peut ajouter un animal
			Animal animal0;
			if (typeProfil == 2) {
				animal0 = new Animal(0, "Aucun animal", "");
				animals.add(animal0);
			} else {
				animal0 = new Animal(0, "Ajouter un animal", "");
				animals.add(animal0);
			}
			// On ajoute les animaux que l'on à déjà créer
			for (int i = 0; i < arrayListAnimal1.length(); i++) {
				JSONObject infoWebserviveReturn = arrayListAnimal1
						.getJSONObject(i);

				if (!infoWebserviveReturn.isNull("id_animal")) {
					Animal animal1 = new Animal(
							infoWebserviveReturn.getInt("id_animal"),
							infoWebserviveReturn.getString("animal_name"),
							infoWebserviveReturn.getString("animal_avatar_name"));
					animals.add(animal1);
				}
			}
		} catch (JSONException e) {
			Log.e("log_ListObjectAnimal",
					"Erreur récupération de la liste d'animaux : "
							+ e.toString());
		}

		adapter_animals_list = new CustomAdapterAnimals(this, animals);
		lsv_animals_list.setAdapter(adapter_animals_list);
		// Ajout d'un onclick listener sur chaque element
		lsv_animals_list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// On recupere les infos de l'item
				try {
					Animal res = (Animal) lsv_animals_list
							.getItemAtPosition(position);
					Intent intent = new Intent(Profiles.context, Profiles.class);
					if (typeProfil == 0) {
						intent.putExtra("typeProfil", 3);
					} else {
						// A CHANGER QUAND LE PROFIL ANIMAL AMI SERA CREE
						intent.putExtra("typeProfil", 3);
					}
					// Si c'est un animal
					if (res.id != 0) {
						intent.putExtra("animalId", res.id);
						intent.putExtra("animalName", res.name);
						intent.putExtra("animalPosition", position);
						startActivityForResult(intent, 0);
					} else {// Sinon on active l'ajout d'un animal
						showAnimalAddDialog();
					}
				} catch (Exception e) {
					Log.e("log_ListObjectAnimal",
							"Erreur récupération de l'animal : " + e.toString());
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
		lsv_friends_list = new ListView(this);
		friends = new ArrayList<Friend>();

		try {
			// On ajoute les animaux que l'on à déjà créer
			for (int i = 0; i < arrayListFriend1.length(); i++) {
				JSONObject infoWebserviveReturn = arrayListFriend1
						.getJSONObject(i);

				if (!infoWebserviveReturn.isNull("friend_id")) {
					Friend friend1 = new Friend(
							infoWebserviveReturn.getInt("friend_id"),
							infoWebserviveReturn.getString("nickname"),
							infoWebserviveReturn.getString("avatar_name"),
							infoWebserviveReturn.getInt("on_mobile"));
					friends.add(friend1);
				} else {
					Friend friend1 = new Friend(0, "Pas encore d'amis", "", 0);
					friends.add(friend1);
				}
			}
		} catch (JSONException e) {
			Log.e("log_ListObjectMessage", "Erreur récupération messages : "
					+ e.toString());
		}

		adapter_friends_list = new CustomAdapterFriends(this, friends);
		lsv_friends_list.setAdapter(adapter_friends_list);
		// Ajout d'un onclick listener sur chaque element
		lsv_friends_list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (typeProfil) {
				case 2:
					break;
				default:
					Friend res = (Friend) lsv_friends_list
							.getItemAtPosition(position);

					if (res.id != 0) {
						// Si l'amis n'as pas d'amis
						Intent intent = new Intent(getApplicationContext(),
								Profiles.class);

						intent.putExtra("typeProfil", 2);
						intent.putExtra("friendId", res.id);
						intent.putExtra("friendName", res.nickname);
						intent.putExtra("friendPosition", position);

						startActivityForResult(intent, 0);
					}
					break;
				}
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
		getMenuInflater().inflate(R.menu.home_action_bar, menu);
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
				actionBarName = pFriendName;
				break;
			case 3: // Profil animal
				actionBarName = pAnimalName;
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
		// Création de l'intent
		Intent intent;

		switch (item.getItemId()) {
		case android.R.id.home:
			switch (typeProfil) {
			case 2:
				intent = new Intent();
				// On rajoute le nom saisie dans l'intent
				intent.putExtra("typeProfil", 0);

				// On retourne le résultat avec l'intent
				setResult(RESULT_OK, intent);
				// On termine cette activité
				finish();
				break;
			case 3:
				intent = new Intent();
				// On rajoute le nom saisie dans l'intent
				intent.putExtra("animalUpdate", isUpdateProfil);
				intent.putExtra("animalUpdateName", infoAnimal.name);
				intent.putExtra("animalPosition", pAnimalPosition);
				intent.putExtra("typeProfil", 0);

				// On retourne le résultat avec l'intent
				setResult(RESULT_OK, intent);
				// On termine cette activité
				finish();
				break;
			default:
				onBackPressed();
				break;
			}
			// Comportement du bouton "Logo"
			return true;
		case R.id.menu_settings:
			// Comportement du bouton "Paramètres"
			return true;
		case R.id.menu_delete:
			// Comportement du bouton "Delete" A supprimer quand le popup
			// parametre sera creer car dedans

			// On créé l'Intent qui va nous permettre d'afficher l'autre
			// Activity
			intent = new Intent(getApplicationContext(), Authentication.class);
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

			ImageView imv_user;
			TextView txv_name;
			RelativeLayout.LayoutParams paramTxv1;
			LinearLayout.LayoutParams paramTxv;
			LinearLayout.LayoutParams sizeImvUser;

			switch (typeProfil) {
			case 2:
				// Instanciation de l'image utilisateur
				imv_user = new ImageView(context);
				imv_user.setImageDrawable(getResources().getDrawable(
						R.drawable.img_defaultuser));

				//On charge l'image si existe
				DownloadImage openDownloadImage = new DownloadImage(Profiles.this, imv_user, friend.avatar_name);
				openDownloadImage.downloadImageView();
				
				// Instanciation du nom de l'utilisateur
				txv_name = new TextView(context);
				txv_name.setText(friend.nickname);
				txv_name.setPadding(15, 0, 0, 0);
				txv_name.setTextColor(getResources().getColor(
						R.color.grey_color));
				txv_name.setTypeface(Arimo);
				txv_name.setTextSize(20);
				txv_name.setGravity(Gravity.CENTER_VERTICAL);

				paramTxv = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

				sizeImvUser = new LinearLayout.LayoutParams(120, 120);

				addView(imv_user, sizeImvUser);
				addView(txv_name, paramTxv);
				break;

			default:
				// Instanciation des différents layout de l'adapter
				RelativeLayout mainLayout = new RelativeLayout(context);
				mainLayout.setId(666);
				LinearLayout subLayout = new LinearLayout(context);

				// Instanciation de l'image utilisateur
				imv_user = new ImageView(context);
				imv_user.setImageDrawable(getResources().getDrawable(
						R.drawable.img_defaultuser));
				imv_user.setId(777);
				
				//On charge l'image si existe
				DownloadImage openDownloadImage1 = new DownloadImage(Profiles.this, imv_user, friend.avatar_name);
				openDownloadImage1.downloadImageView();
				
				// Instanciation du nom de l'utilisateur
				txv_name = new TextView(context);
				txv_name.setText(friend.nickname);
				txv_name.setPadding(15, 0, 0, 0);
				txv_name.setTextColor(getResources().getColor(
						R.color.grey_color));
				txv_name.setTypeface(Arimo);
				txv_name.setTextSize(20);

				// Instanciation des images messages et connecté
				ImageView isConnected = new ImageView(context);
				if (friend.onMobile == 2) {
					isConnected.setImageDrawable(getResources().getDrawable(
							R.drawable.oncomputer));
				} else if (friend.onMobile == 1) {
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
						intent.putExtra("friendName", friend.nickname);
						startActivity(intent);
					}
				});

				subLayout.setOrientation(LinearLayout.VERTICAL);
				subLayout.setGravity(Gravity.CENTER);

				paramTxv1 = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
				paramTxv1.addRule(RelativeLayout.RIGHT_OF, imv_user.getId());
				paramTxv1.addRule(RelativeLayout.CENTER_VERTICAL,
						mainLayout.getId());
				LinearLayout.LayoutParams wrap_0 = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, 0);
				RelativeLayout.LayoutParams paramSubLayout = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
				paramSubLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
						mainLayout.getId());
				paramSubLayout.addRule(RelativeLayout.CENTER_VERTICAL,
						mainLayout.getId());
				sizeImvUser = new LinearLayout.LayoutParams(120, 120);

				subLayout.setWeightSum(2);
				wrap_0.weight = 1;
				subLayout.addView(isConnected, wrap_0);
				subLayout.addView(imv_message, wrap_0);

				mainLayout.addView(imv_user, sizeImvUser);
				mainLayout.addView(txv_name, paramTxv1);
				mainLayout.addView(subLayout, paramSubLayout);
				addView(mainLayout);
				break;
			}

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

			ImageView imv_user;
			TextView txv_name;
			LinearLayout.LayoutParams paramTxv;
			LinearLayout.LayoutParams sizeImvUser;

			if (animal.id == 0) {
				// Instanciation de l'image utilisateur
				imv_user = new ImageView(context);
				imv_user.setImageDrawable(getResources().getDrawable(
						R.drawable.img_defaultanimal));

				// Instanciation du nom de l'utilisateur
				txv_name = new TextView(context);
				txv_name.setText(animal.name);
				txv_name.setPadding(0, 0, 0, 15);
				txv_name.setTextColor(getResources().getColor(
						R.color.grey_color));
				txv_name.setTypeface(Lobster, Typeface.BOLD);
				txv_name.setTextSize(20);
				txv_name.setGravity(Gravity.CENTER_HORIZONTAL);

				paramTxv = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				addView(txv_name, paramTxv);
			} else {
				// Instanciation de l'image utilisateur
				imv_user = new ImageView(context);
				imv_user.setImageDrawable(getResources().getDrawable(
						R.drawable.img_defaultanimal));

				//On charge l'image si existe
				DownloadImage openDownloadImage = new DownloadImage(Profiles.this, imv_user, animal.avatarName);
				openDownloadImage.downloadImageView();
				
				// Instanciation du nom de l'utilisateur
				txv_name = new TextView(context);
				txv_name.setText(animal.name);
				txv_name.setPadding(15, 0, 0, 0);
				txv_name.setTextColor(getResources().getColor(
						R.color.grey_color));
				txv_name.setTypeface(Arimo);
				txv_name.setTextSize(20);
				txv_name.setGravity(Gravity.CENTER_VERTICAL);

				paramTxv = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

				sizeImvUser = new LinearLayout.LayoutParams(120, 120);

				addView(imv_user, sizeImvUser);
				addView(txv_name, paramTxv);
			}
		}

	}

	// Adapter de la ListView contenant les animaux
	public class CustomAdapterAnimals extends BaseAdapter {
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
			
			//On charge l'image si existe
			DownloadImage openDownloadImage = new DownloadImage(Profiles.this, imv_user, message.avatar_name);
			openDownloadImage.downloadImageView();
			
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
	 * Add items into spinner Type
	 */
	// public void addItemsTypeOnSpinner(int idType) {
	// // TODO Faire l'ajout des type dynamiquement par la suite.
	// Spinner spinner2 = (Spinner) findViewById(R.id.update_spinner_type_id);
	// List<String> list = new ArrayList<String>();
	// list.add("Chien");
	// list.add("Chat");
	// list.add("Furet");
	// list.add("Rongeur et lapin");
	// list.add("Oiseau");
	// list.add("Reptile");
	// list.add("Cheval");
	// list.add("Poisson");
	// list.add("Basse-cour");
	// list.add("Autres");
	// ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
	// android.R.layout.simple_spinner_item, list);
	// dataAdapter
	// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// spinner2.setAdapter(dataAdapter);
	// spinner2.setSelection(idType, true);
	// }

	/**
	 * Add items into spinner Type
	 */
	// public void addItemsRaceOnSpinner() {
	// Spinner spinner2 = (Spinner) findViewById(R.id.update_spinner_race_id);
	// List<String> list = new ArrayList<String>();
	// list.add("Test");
	// ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
	// android.R.layout.simple_spinner_item, list);
	// dataAdapter
	// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// spinner2.setAdapter(dataAdapter);
	// }

	/**
	 * Add items into spinner Country
	 */
	public void addItemsCountryOnSpinner(int idCountry) {
		// Connexion a la bdd interne
		InstallSQLiteBase firstInstallBdd = new InstallSQLiteBase(
				getApplicationContext());
		SQLiteDatabase formationDB = firstInstallBdd.getReadableDatabase();
		Cursor curseur = formationDB.rawQuery("SELECT * FROM country", null);

		List<String> list = new ArrayList<String>();

		while (curseur.moveToNext()) {
			list.add(curseur.getString(1));
		}

		firstInstallBdd.close();

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		upd_pays.setAdapter(dataAdapter);

		upd_pays.setSelection(idCountry - 1, true);
	}

	/**
	 * Add items into spinner Province
	 * 
	 * @param int idCountrySelected
	 * @param boolean isFirstUse in case is the fisrt use we use the select
	 *        action
	 * @return void
	 */
	public void addItemsProvinceOnSpinner(int idCountrySelected,
			boolean isFirstUse) {
		List<String> list = new ArrayList<String>();

		if (idCountrySelected == 1) {
			// Connexion a la bdd interne
			// on affiche le spinner dep
			InstallSQLiteBase firstInstallBdd = new InstallSQLiteBase(
					getApplicationContext());
			SQLiteDatabase formationDB = firstInstallBdd.getReadableDatabase();
			Cursor curseur = formationDB.rawQuery(
					"SELECT province_name FROM provinces", null);

			while (curseur.moveToNext()) {
				list.add(curseur.getString(0));
			}

			firstInstallBdd.close();
		} else {
			list.add("");
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		upd_dep.setAdapter(dataAdapter);

		if (isFirstUse) {
			if (idCountrySelected == 1) {
				upd_dep.setSelection(userProvinceId - 1, true);
				addItemsCityOnSpinner(userProvinceId, userProvinceId, true);
			}
		}
	}

	/**
	 * Add items into spinner Cities
	 * 
	 * @param int idProvinceSelected
	 * @param int idProvince
	 * @param int idCity
	 * @return void
	 */
	public void addItemsCityOnSpinner(int idProvinceSelected, int idProvince,
			boolean isFirstUse) {
		List<String> list = new ArrayList<String>();

		String provinceCourante;
		try {
			provinceCourante = upd_dep.getItemAtPosition(idProvinceSelected)
					.toString();
		} catch (Exception e) {
			provinceCourante = "";
		}

		if (provinceCourante.equals("")) {
			list.add("");
		} else if (idProvince == userProvinceId && isFirstUse) {
			// On affiche la ville séléctionné
			JSONObject infoWebserviveReturn;
			try {
				infoWebserviveReturn = userCityDefaut.getJSONObject(0);
				list.add(infoWebserviveReturn.getString("city_name"));
			} catch (JSONException e) {
				Log.e("log_userCityDefaut", "JSONException : " + e.toString());
			}
		} else {
			// On charge les nouvelles données
			// On vide la liste de données à envoyé si existe déjà
			data.clear();

			// On ajoute les valeurs
			data.add(new BasicNameValuePair("id_user", idUser));
			data.add(new BasicNameValuePair("list_name", "userCity"));
			data.add(new BasicNameValuePair("province_id", String
					.valueOf(idProvinceSelected)));

			if (ConnectionWebservicePHPProfile.haveNetworkConnection(this)) {
				ConnectionWebservicePHPProfile calcul = new ConnectionWebservicePHPProfile(
						1, "listObjectOther", this, data);
				calcul.execute();
				try {
					arrayListReturn = calcul.get();
					arrayListUserCity = arrayListReturn.get(0);

					JSONObject infoWebserviveReturn;
					// On affiche les villes de la personnes par defaut
					for (int i = 0; i < arrayListUserCity.length(); i++) {
						try {
							infoWebserviveReturn = arrayListUserCity
									.getJSONObject(i);
							list.add(infoWebserviveReturn
									.getString("city_name"));
						} catch (JSONException e) {
							Log.e("log_userCity",
									"JSONException : " + e.toString());
						}
					}
				} catch (Exception e) {
					Log.e("log_arrayListReturn1", "Exception : " + e.toString());
				}
			} else { // Sinon toast de problème
				ConnectionWebservicePHPProfile.haveNetworkConnectionError(this);
			}
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		upd_ville.setAdapter(dataAdapter);
	}

	/**
	 * Display the connection popup (using ConnectionDialog)
	 */
	private void showAnimalAddDialog() {
		fm = getSupportFragmentManager();
		addAnimal = new AddAnimalDialog();
		addAnimal.show(fm, "fragment_add_animal");
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
	
//	private void downloadImage(final RoundedImageView view, final String imgUrl) {
//		final String domainUrl = "http://m.animalio.fr/pictures/";
//		Thread thread2;
//		
//		thread2 = new Thread() {
//			Bitmap bitmap1 = null;
//			Boolean getError = false;
//			@Override
//			public void run() {
//				try {
//					URL urlImage = new URL(domainUrl + imgUrl);
//					HttpURLConnection connection = (HttpURLConnection) urlImage.openConnection();
//					InputStream inputStream = connection.getInputStream();
//					
//					bitmap1 = BitmapFactory.decodeStream(inputStream);
//				} catch (MalformedURLException e) {
//					getError = true;
//					Log.e("log_avatarUpload",
//							"MalformedURLException : " + e.toString());
//				} catch (IOException e) {
//					getError = true;
//					Log.e("log_avatarUpload",
//							"IOException : " + e.toString());
//				}   	
//			
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						if(getError){
//							//Si il y a une erreur on affiche une image indisponible
//							view.setImageDrawable(getResources().getDrawable(R.drawable.img_indisponible));
//						}else{
//							view.setImageBitmap(bitmap1);
//						}
//					}
//				});
//			}
//		};
//		thread2.start();	
//    }
}