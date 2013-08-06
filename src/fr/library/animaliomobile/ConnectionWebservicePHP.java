package fr.library.animaliomobile;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import fr.activity.animaliomobile.Authentication;
import fr.activity.animaliomobile.Home;
import fr.activity.animaliomobile.MessagingService;
import fr.animaliomobile.R;

public class ConnectionWebservicePHP extends AsyncTask<Void, Integer, Boolean> {

	private int loaderType; // affiche le layout de la premiere page de
							// chargement de l'application
	private String connectionType; // affiche le type de connexion � effectu�
	public Context context; // affiche le context de la vue ou la connexion au
							// webservice est n�cessaire
	public RelativeLayout layMessage;
	public File fileUpload;
	public ScrollView layScrollview;
	private ProgressDialog pd;
	private JSONArray arrayInfoWebservice;
	public String domainUrl = "http://m.animalio.fr/";
	HttpRequestRetryHandler myRetryHandler; // R�cup�rateur de r�ponse HTTP
	public ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	public int resultErrorReturn;
	public int nbMsgMin;

	/**
	 * Constuctor of Animalio Webservice
	 * 
	 * @param _loaderType
	 * @param _connectionType
	 * @param _context
	 * @param _data
	 */
	public ConnectionWebservicePHP(int _loaderType, String _connectionType,
			Context _context, ArrayList<NameValuePair> _data) {
		this.loaderType = _loaderType;
		this.connectionType = _connectionType;
		this.context = _context;
		this.data = _data;
	}

	/**
	 * Constuctor of Animalio Webservice without data information
	 * 
	 * @param _loaderType
	 * @param _connectionType
	 * @param _context
	 */
	public ConnectionWebservicePHP(int _loaderType, String _connectionType,
			Context _context) {
		this.loaderType = _loaderType;
		this.connectionType = _connectionType;
		this.context = _context;
	}
	
	/**
	 * Constuctor of Animalio Webservice for upload image
	 * 
	 * @param _loaderType
	 * @param _connectionType
	 * @param _FileUpload
	 * @param _context
	 */
	public ConnectionWebservicePHP(int _loaderType, String _connectionType, File _fileUpload,
			Context _context) {
		this.loaderType = _loaderType;
		this.connectionType = _connectionType;
		this.fileUpload = _fileUpload;
		this.context = _context;
	}
	
	/**
	 * Constuctor of Animalio Webservice with RelativeLayout
	 * 
	 * @param int _loaderType
	 * @param String _connectionType
	 * @param Context _context
	 * @param ArrayList<NameValuePair> _data
	 * @param RelativeLayout _layMessage
	 */
	public ConnectionWebservicePHP(int _loaderType, String _connectionType,
			Context _context, ArrayList<NameValuePair> _data, RelativeLayout _layMessage, ScrollView _layScrollview, int _nbMsgMin) {
		this.loaderType = _loaderType;
		this.connectionType = _connectionType;
		this.context = _context;
		this.data = _data;
		this.layMessage = _layMessage;
		this.layScrollview = _layScrollview;
		this.nbMsgMin = _nbMsgMin;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		String connection = this.connectionType;
		// On affiche un loader de chargement
		pd = ProgressDialog.show(this.context, "", "Chargement ...", true);
		pd.setCancelable(false);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		String connection = this.connectionType;

		// Si le webservice concerne l'authentication
		if (connection.equals("Authentication")) {
			if (result) {
				// On cr�� l'Intent qui va nous permettre d'afficher l'autre
				// Activity
				Intent intent = new Intent(this.context, Home.class);
				// On supprime l'activity de login sinon
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				this.context.startActivity(intent);
			} else {
				Toast.makeText(this.context, "Login Incorrect!",
						Toast.LENGTH_LONG).show();
			}
		} else if (connection.equals("Registration")) {
			if (result) {
				// On cr�� l'Intent qui va nous permettre d'afficher l'autre
				// Activity
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(this.context);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean("isRegister", true);
				editor.commit();
				
				Intent intent = new Intent(this.context, Home.class);
				// On supprime l'activity d'inscription
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				this.context.startActivity(intent);
			}else{
				if(this.resultErrorReturn == 0){
					Toast.makeText(this.context, "pseudo et email existe d�j�!",
							Toast.LENGTH_LONG).show();
				}else if (this.resultErrorReturn == 2) {
					Toast.makeText(this.context, "pseudo existe d�j�!",
							Toast.LENGTH_LONG).show();
				}else if (this.resultErrorReturn == 3) {
					Toast.makeText(this.context, "email existe d�j�!",
							Toast.LENGTH_LONG).show();
				}
			}
		} else if (connection.equals("refreshInfoUser")) {
			if (!result) {
				if(this.resultErrorReturn == 0){
					Toast.makeText(this.context, "Information utilisateur mis � jour!",
							Toast.LENGTH_LONG).show();
				}else if (this.resultErrorReturn == 1) {
//					Toast.makeText(this.context, "Pas de mise � jour � effectu�",
//							Toast.LENGTH_LONG).show();
				}else{
					// On retour � la connexio et on supprime les pr�f�rence
					SharedPreferences preferences = PreferenceManager
							.getDefaultSharedPreferences(this.context);
					SharedPreferences.Editor editor = preferences.edit();
					editor.clear();
					editor.commit();
					
					Intent intent = new Intent(this.context, Authentication.class);
					// On supprime l'activity Home
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					this.context.startActivity(intent);
				}
			}
		} else if (connection.equals("ForgetPassword")) {
			if (result) {
				Toast.makeText(this.context,
						"Email envoy� avec succ�s!",
						Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(this.context,
						"Email n'existe pas!",
						Toast.LENGTH_LONG).show();
			}
		} else if (connection.equals("MessagingService")) {
			if (!result) {
					Toast.makeText(this.context,
							"Pas de nouveau message",
							Toast.LENGTH_LONG).show();
			} else {
				// On r�cup�re les info du serveur
				try {
					RelativeLayout lay_message_current = (RelativeLayout) this.layMessage
							.findViewById(R.id.lay_message);
					// On R�cup�re les pr�f�rences utilisateur si elle existe
					SharedPreferences preferences = PreferenceManager
							.getDefaultSharedPreferences(this.context);

					String idUserCurrent = preferences.getString("idUser", "");
					
					for (int i = 0; i < this.arrayInfoWebservice.length(); i++) {
						JSONObject infoWebserviveReturn = this.arrayInfoWebservice
								.getJSONObject(i);
						Log.i("log_int", "int : " + nbMsgMin);
						if (infoWebserviveReturn.getString("user_id").equals(idUserCurrent)) {
							LinearLayout lay_msgSender1 = new LinearLayout(this.context);
							lay_msgSender1.setId(nbMsgMin+1);
							lay_msgSender1.setBackgroundColor(Color.parseColor("#b7eeeb"));

							TextView txv_msgSender1 = new TextView(this.context);
							txv_msgSender1.setText(Html.fromHtml("<b><i>" + infoWebserviveReturn.getString("created_at_format") + "</i></b> : ") + infoWebserviveReturn.getString("message"));
							lay_msgSender1.addView(txv_msgSender1);

							if (i == 0 && nbMsgMin == 0) {
								RelativeLayout.LayoutParams placement1 = new RelativeLayout.LayoutParams(
										ViewGroup.LayoutParams.WRAP_CONTENT,
										ViewGroup.LayoutParams.WRAP_CONTENT);
								placement1.addRule(RelativeLayout.ALIGN_BASELINE, lay_message_current.getId());
								placement1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, lay_message_current.getId());
								placement1.setMargins(50, 20, 0, 20);
								lay_msgSender1.setLayoutParams(placement1);
								lay_msgSender1.setPadding(10, 10, 50, 10);
								lay_message_current.addView(lay_msgSender1);
							} else {
								RelativeLayout.LayoutParams placement1 = new RelativeLayout.LayoutParams(
										ViewGroup.LayoutParams.WRAP_CONTENT,
										ViewGroup.LayoutParams.WRAP_CONTENT);
								placement1.addRule(RelativeLayout.BELOW, nbMsgMin);
								placement1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, lay_message_current.getId());
								placement1.setMargins(50, 20, 0, 20);
								lay_msgSender1.setLayoutParams(placement1);
								lay_msgSender1.setPadding(10, 10, 50, 10);
								lay_message_current.addView(lay_msgSender1);
							}
						} else {
							LinearLayout lay_msgReceiver1 = new LinearLayout(
									this.context);
							lay_msgReceiver1.setId(nbMsgMin+1);
							lay_msgReceiver1.setBackgroundColor(Color
									.parseColor("#b7eec0"));

							TextView txv_msgReceiver1 = new TextView(
									this.context);
							txv_msgReceiver1.setText(Html.fromHtml("<b><i>" + infoWebserviveReturn.getString("created_at_format") + "</i></b> : ") + infoWebserviveReturn.getString("message"));
							lay_msgReceiver1.addView(txv_msgReceiver1);

							if (i == 0 && nbMsgMin == 0) {
								RelativeLayout.LayoutParams placement2 = new RelativeLayout.LayoutParams(
										ViewGroup.LayoutParams.WRAP_CONTENT,
										ViewGroup.LayoutParams.WRAP_CONTENT);
								placement2.addRule(RelativeLayout.ALIGN_BASELINE, lay_message_current.getId());
								placement2.addRule(RelativeLayout.ALIGN_PARENT_LEFT, lay_message_current.getId());
								placement2.setMargins(0, 20, 50, 20);
								lay_msgReceiver1.setLayoutParams(placement2);
								lay_msgReceiver1.setPadding(50, 10, 10, 10);
								lay_message_current.addView(lay_msgReceiver1);
							} else {
								RelativeLayout.LayoutParams placement2 = new RelativeLayout.LayoutParams(
										ViewGroup.LayoutParams.WRAP_CONTENT,
										ViewGroup.LayoutParams.WRAP_CONTENT);
								placement2.addRule(RelativeLayout.BELOW, nbMsgMin);
								placement2.addRule(RelativeLayout.ALIGN_PARENT_LEFT, lay_message_current.getId());
								placement2.setMargins(0, 20, 50, 20);
								lay_msgReceiver1.setLayoutParams(placement2);
								lay_msgReceiver1.setPadding(50, 10, 10, 10);
								lay_message_current.addView(lay_msgReceiver1);
							}
						}						
						nbMsgMin++;
					}				
				} catch (JSONException e) {
					Log.e("log_MessagingService_post",
							"Erreur d'affichage des messages : " + e.toString());
				}
			}
		} else if (connection.equals("MessagingSend")) {
			if (result){
				// A defaut de trouv� un meilleur ajout des nouveaux messages dynamiquement
				Intent intent = new Intent(this.context, MessagingService.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				this.context.startActivity(intent);
			}else{
				Toast.makeText(this.context,
						"Erreur d'envoie message",
						Toast.LENGTH_LONG).show();
			}
		}else if (connection.equals("uploadPicture")) {
			if (result){
				JSONObject infoWebserviveReturn;
				try {
					infoWebserviveReturn = arrayInfoWebservice
							.getJSONObject(0);
					
					// Parse les donn�es JSON
					if (infoWebserviveReturn.getInt("isOk") == 1) { 
						Toast t = Toast.makeText(Home.context,
								"Le fichier � �t� enregistr�",
								Toast.LENGTH_SHORT);
						t.setGravity(Gravity.TOP, 0, 90);
						t.show();
					}else if (infoWebserviveReturn.getInt("isOk") == 2){
						Toast t = Toast.makeText(Home.context,
								"Probl�me de chargement de la photo. Merci de recommencer!",
								Toast.LENGTH_SHORT);
						t.setGravity(Gravity.TOP, 0, 90);
						t.show();
					}else if (infoWebserviveReturn.getInt("isOk") == 0){
						Toast t = Toast.makeText(Home.context,
								"Impossible de charger la photo!",
								Toast.LENGTH_SHORT);
						t.setGravity(Gravity.TOP, 0, 90);
						t.show();
					}else if (infoWebserviveReturn.getInt("isOk") == 3){
						Toast t = Toast.makeText(Home.context,
								"Mauvaise extension de l'image!",
								Toast.LENGTH_SHORT);
						t.setGravity(Gravity.TOP, 0, 90);
						t.show();
					}else if (infoWebserviveReturn.getInt("isOk") == 4){
						Toast t = Toast.makeText(Home.context,
								"La photo existe d�j�, Merci de changer de photo!",
								Toast.LENGTH_SHORT);
						t.setGravity(Gravity.TOP, 0, 90);
						t.show();
					}
				} catch (JSONException e) {
					Log.e("log_ImgUpload", "Mauvaise connection " + e.toString());
				}
			}else{
				Toast t = Toast.makeText(Home.context,
						"Erreur d'enregistrement de l'image. Merci de nous contacter!",
						Toast.LENGTH_SHORT);
				t.setGravity(Gravity.TOP, 0, 90);
				t.show();
			}
		} 

		// On enleve le loader de chargement
		pd.dismiss();
	}

	@Override
	protected Boolean doInBackground(Void... arg0) { // Actions � ex�cuter en
														// tache de fond
		String connection = this.connectionType;
		Boolean res = false;
		resultErrorReturn = 1;

		// Si le webservice concerne l'authentication
		if (connection.equals("Authentication")) {
			String url = this.domainUrl + "/authentication-mobile.php";
			// On r�cup�re les info du serveur
			try {
				JSONArray infoServerData = getServerData(this.data, url);
				JSONObject infoWebserviveReturn = infoServerData
						.getJSONObject(0);
				// Parse les donn�es JSON
				// Si la connection est correcte
				if (infoWebserviveReturn.getInt("isOk") == 1) { 
					res = true;
					// On stock les infos utilisateurs dans des preferences
					SharedPreferences preferences = PreferenceManager
							.getDefaultSharedPreferences(this.context);
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
					
					//A la premiere connexion on charge les bdd si n'existe pas			
					InstallSQLiteBase firstInstallBdd = new InstallSQLiteBase(this.context);
					SQLiteDatabase formationDB = firstInstallBdd.getWritableDatabase(); 
					firstInstallBdd.close();
					Log.i("log_CreateDB", "Create DB : " + formationDB);
				} else {
					res = false;
				}
			} catch (JSONException e) {
				Log.e("log_authentication", "Mauvaise connection " + e.toString());
			}
		} else if (connection.equals("Registration")) {
			String url = this.domainUrl + "/registration-mobile.php";
			// On r�cup�re les info du serveur
			try {
				JSONArray infoServerData = getServerData(this.data, url);
				JSONObject infoWebserviveReturn = infoServerData
						.getJSONObject(0);
				Log.e("log_tagJson", "Error parsing data " + infoServerData);
				// Parse les donn�es JSON
				if (infoWebserviveReturn.getInt("isOk") == 1) {//Si l'inscription est bonne
					res = true;				
					// On stock les infos utilisateurs dans des preferences
					SharedPreferences preferences = PreferenceManager
							.getDefaultSharedPreferences(this.context);
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
				}else if (infoWebserviveReturn.getInt("isOk") == 0){ //Sinon on retourne l'erreur
					res = false;
					resultErrorReturn = 0;
				}else if (infoWebserviveReturn.getInt("isOk") == 2){
					res = false;
					resultErrorReturn = 2;
				}else if (infoWebserviveReturn.getInt("isOk") == 3){
					res = false;
					resultErrorReturn = 3;
				}
			} catch (JSONException e) {
				Log.e("log_tagRegistration", "Error parsing data second" + e.toString());
			}
		} else if (connection.equals("refreshInfoUser")) {
			String url = this.domainUrl + "/refresh-user-mobile.php";
			// On r�cup�re les info du serveur
			try {
				JSONArray infoServerData = getServerData(this.data, url);
				JSONObject infoWebserviveReturn = infoServerData
						.getJSONObject(0);
				
				// Parse les donn�es JSON
				// Si la date de modification est diff�rente alors on doit charger les nouvelles donn�es
				if (infoWebserviveReturn.getInt("isOk") == 1) {
					if (infoWebserviveReturn.getInt("isUpdated") == 1) {			
						// On stock les infos utilisateurs dans des preferences
						SharedPreferences preferences = PreferenceManager
								.getDefaultSharedPreferences(this.context);
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
						
						
						resultErrorReturn = 0;
					}else{
						resultErrorReturn = 1;
					}
				}else{
					//Utilisateur n'existe pas
					resultErrorReturn = 2;
				}
				res = false;
			} catch (JSONException e) {
				res = false;
				resultErrorReturn = 3;
				Log.e("log_refreshUser", "Erreur de r�cup�ration d'information utilisateur" + e.toString());
			}
		}else if (connection.equals("ForgetPassword")) {
			String url = this.domainUrl + "/forget-password-mobile.php";
			// On r�cup�re les info du serveur
			try {
				JSONArray infoServerData = getServerData(this.data, url);
				JSONObject infoWebserviveReturn = infoServerData
						.getJSONObject(0);
				
				// Parse les donn�es JSON
				// Si la date de modification est diff�rente alors on doit charger les nouvelles donn�es
				if (infoWebserviveReturn.getInt("isMailSend") == 1) {
					res = true;
				}else{
					//L'email n'existe pas
					res = false;
				}
			} catch (JSONException e) {
				Log.e("log_ForgetPassword", "Erreur de r�cup�ration de mot de passe perdu" + e.toString());
			}
		}else if(connection.equals("MessagingService")){
			String url = this.domainUrl + "/messaging-service-mobile.php";
			// On r�cup�re les info du serveur
			try {
				this.arrayInfoWebservice = getServerData(this.data, url);
				JSONObject infoWebserviveReturnError = arrayInfoWebservice
						.getJSONObject(0);
				
				Boolean errorMessageReceive = infoWebserviveReturnError.isNull("isOk");

				if(errorMessageReceive){ //On affiche la liste des messages
					res=true;
				}else{ //Sinon plus de message � afficher
					res=false;
				}
			} catch (Exception e) {
				res = false;
				Log.e("log_MessagingService", "Erreur de r�cup�ration des messages" + e.toString());
			}
		}else if(connection.equals("MessagingSend")){
			//Envoie d'une message � un ami
			String url = this.domainUrl + "/messaging-service-mobile.php";
			// On r�cup�re les info du serveur
			try {
				JSONArray infoServerData = getServerData(this.data, url);
				JSONObject infoWebserviveReturn = infoServerData
						.getJSONObject(0);
				// Parse les donn�es JSON
				// Si la date de modification est diff�rente alors on doit charger les nouvelles donn�es
				if (infoWebserviveReturn.getInt("isOk") == 1) {
					res = true;
				}else{
					res = false;
				}
			} catch (Exception e) {
				res = false;
				Log.e("log_MessagingSend", "Erreur d'envoie de message" + e.toString());
			}
		}else if (connection.equals("listObject")){
			//Envoie d'une message � un ami
			String url = this.domainUrl + "/list-object.php";
			// On r�cup�re les info du serveur
			this.arrayInfoWebservice = getServerData(this.data, url);
			res = false;
		} else if (connection.equals("uploadPicture")){
			//Envoie d'une image sur le serveur
			String url = this.domainUrl + "/upload-picture-mobile.php";
			// On r�cup�re les info du serveur
			try {
				this.arrayInfoWebservice = getServerDataUploadImage(fileUpload, url);
			
				Log.i("log_ImageUpload", "ImageUpload : " + arrayInfoWebservice);
				res = true;
			} catch (Exception e) {
				res = false;
				Log.e("log_ImageUpload", "Erreur d'envoie de l'image" + e.toString());
			}
		}else if (connection.equals("listEvent")) {

		} else if (connection.equals("photoGalery")) {

		} else if (connection.equals("profilMember")) {

		} else if (connection.equals("profilAnimal")) {
			System.out.println("charge image �lu");
		} else {
			res = false;
		}
		return res;
	}

	/**
	 * m�thode de r�cup�ration de donn�es sur le serveur
	 * 
	 * @param tabChamps
	 * @param url
	 * @return ArrayList<ArrayList<String>>
	 */
	public JSONArray getServerData(ArrayList<NameValuePair> dataSendTo,
			String url) {
		InputStream is = null;
		String result = "";
		String strURL = url;
		JSONArray resultat = null;// Resultat final
		// JSONArray jArray = null; //Resultat final
		// Data to send to server
		ArrayList<NameValuePair> nameValuePairs = dataSendTo; 

		// Envoie de la commande http
		try {
			HttpClient httpclient = new DefaultHttpClient();
			setRetry();
			// ajout du manager de r�ponse afin de permettre le retry en fonction de la r�ponse
			((AbstractHttpClient) httpclient)
					.setHttpRequestRetryHandler(myRetryHandler);
			HttpPost httppost = new HttpPost(strURL);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tagEnvoie", "Error in http connection " + e.toString());
		}
		// Convertion de la requ�te en string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			Log.e("log_tagConvert", "Error converting result " + e.toString());
		}
		// R�cup�re un array de json
		try {
			resultat = new JSONArray(result);
		} catch (JSONException e) {
			Log.e("log_tagRecup�re", "Error parsing data first " + e.toString());
		}

		return resultat;
	}

	/**
	 * M�thode d'upload d'image et de retour d'information sur l'upload
	 * 
	 * @param Uri myImage
	 * @param String url
	 * @return ArrayList<ArrayList<String>>
	 */
	public JSONArray getServerDataUploadImage(File fileUpload,
			String urlTo) {		
		JSONArray resultat = null;// Resultat final
		try
		{	// Cr�ation du client http
//			HttpClient httpclient = new DefaultHttpClient();
//			setRetry(); // On lui donne la possibilit� de retenter la connexion en cas d'�chec
//			((AbstractHttpClient) httpclient).setHttpRequestRetryHandler(myRetryHandler); 
//			HttpPost httppost = new HttpPost(urlTo); // On d�finie l'url du script php
//			httppost.setEntity(new UrlEncodedFormEntity(dataSendTo)); // on passe en param�tre la liste de donn�es � envoyer
//			ResponseHandler<String> responseHandler=new BasicResponseHandler();
//			String  response = httpclient.execute(httppost, responseHandler); // on envoie les donn�es au serveur et on r�cup�re sa r�ponse

			// si une photo a �t� prise
			HttpURLConnection connection = null;
			DataOutputStream outputStream = null;

			// On d�finie l'url du script PHP
			String urlServer = urlTo;
			String lineEnd = "\r\n";
			String twoHyphens = "--";
			String boundary = "*****";

			int bytesRead, bytesAvailable, bufferSize;
			byte[] buffer;
			int maxBufferSize = 1 * 1024 * 1024;
			try {
				// On passe la photo a un flux de donn�es
				FileInputStream fileInputStream = new FileInputStream(new File(
						fileUpload.toString()));
				// On ouvre la connexion au serveur
				URL url = new URL(urlServer);
				connection = (HttpURLConnection) url.openConnection();

				// On autorise les Inputs et les Outputs
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setUseCaches(false);

				// On active la m�thode POST
				connection.setRequestMethod("POST");
				// On maintient la connexion ouverte
				connection.setRequestProperty("Connection", "Keep-Alive");
				connection.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				// On r�cup�re le flux sortant
				outputStream = new DataOutputStream(
						connection.getOutputStream());
				outputStream.writeBytes(twoHyphens + boundary + lineEnd);
				outputStream
						.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
								+ fileUpload.toString() + "\"" + lineEnd);
				outputStream.writeBytes(lineEnd);

				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// On lit le fichier
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {
					outputStream.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				}

				outputStream.writeBytes(lineEnd);
				outputStream.writeBytes(twoHyphens + boundary + twoHyphens
						+ lineEnd);

				// On r�cup�re le echo retourner par le script PHP
				InputStream in = connection.getInputStream();
				StringBuffer sb = new StringBuffer();
				String reply;
				try {
					int chr;
					while ((chr = in.read()) != -1) {
						sb.append((char) chr);
					}
					reply = sb.toString();
				} finally {
					in.close();
				}
				// On vide et ferme les flux
				fileInputStream.close();
				outputStream.flush();
				outputStream.close();
				// On met � jour la r�ponse
				// R�cup�re un array de json
				try {
					resultat = new JSONArray(reply);
				} catch (JSONException e) {
					Log.e("log_tagRecup�re", "Error parsing data first Image" + e.toString());
				}
				
			} catch (Exception e) {
				Log.e("log_tag", "Error:  " + e.toString());
			}
			// On ferme la fen�tre informant l'usager de l'envoie des donn�es
		}
		catch(Exception e) 
		{
			Log.e("log_tag", "Error:  "+e.toString());
		}  
		
		return resultat;
	}
	
	/**
	 * M�thode permettant de retenter la connexion en cas d'�chec
	 */
	public void setRetry() {
		myRetryHandler = new HttpRequestRetryHandler() {

			public boolean retryRequest(IOException exception,
					int executionCount, HttpContext context) {

				// Si la connexion a �chou� plus de 3 fois on arr�te de r�essayer
				if (executionCount >= 4) {
					System.out.println("retry count");
					return true;
				}
				// retente la connexion si le serveur ne r�pond pas
				if (exception instanceof NoHttpResponseException) {
					System.out.println("NoHttpResponseException exception");
					return true;
				}
				// retente si la connexion a �t� reset
				if (exception instanceof java.net.SocketException) {
					System.out.println("java.net.SocketException exception");
					return true;
				}
				 // retente si timeOut
				if (exception instanceof java.net.SocketTimeoutException) {
					System.out
							.println("java.net.SocketTimeoutException exception");
					return true;
				}
				HttpRequest request = (HttpRequest) context
						.getAttribute(ExecutionContext.HTTP_REQUEST);
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					System.out.println("idempotent exception");
					// Retry if the request is considered idempotent
					return true;
				}
				return false;
			}
		};
	}

	/**
	 * M�thode testant la connexion internet
	 * 
	 * @param context
	 * @return Boolean true or false
	 */
	public static boolean haveNetworkConnection(Context context) {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;
		// r�cup�ration des informations � partir du t�l�phone
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		// retourne true si au moins une valeur est true
		return haveConnectedWifi || haveConnectedMobile;
	}

	/**
	 * M�thode renvoyant un toast d'erreur si pas de connexion internet
	 * 
	 * @param context
	 * @return void
	 */
	public static void haveNetworkConnectionError(Context context) {
		Toast t = Toast.makeText(context, "Erreur de connexion internet!",
				Toast.LENGTH_LONG);
		t.setGravity(Gravity.BOTTOM, 0, 40);
		t.show();
	}
	
	/*GETTER & SETTER*/
	public JSONArray getArrayInfoWebservice() {
		return arrayInfoWebservice;
	}
}
