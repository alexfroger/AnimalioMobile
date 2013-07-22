package fr.library.animaliomobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicResponseHandler;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import fr.activity.animaliomobile.Home;

public class ConnectionWebservicePHP extends AsyncTask<Void, Integer, Boolean> {

	private int loaderType; // affiche le layout de la premiere page de
							// chargement de l'application
	private String connectionType; // affiche le type de connexion à effectué
	public Context context; // affiche le context de la vue ou la connexion au
							// webservice est nécessaire
	private ProgressDialog pd;
	public String domainUrl = "http://m.animalio.fr/";
	HttpRequestRetryHandler myRetryHandler; // Récupérateur de réponse HTTP
	public ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	public int resultErrorReturn;

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
				// On créé l'Intent qui va nous permettre d'afficher l'autre
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
				// On créé l'Intent qui va nous permettre d'afficher l'autre
				// Activity
				Bundle bundle = new Bundle();
				bundle.putString("isRegister", "1");
				Intent intent = new Intent(this.context, Home.class);
				// On supprime l'activity d'inscription
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				this.context.startActivity(intent.putExtras(bundle));
			}else{
				if(this.resultErrorReturn == 0){
					Toast.makeText(this.context, "pseudo et email existe déjà!",
							Toast.LENGTH_LONG).show();
				}else if (this.resultErrorReturn == 2) {
					Toast.makeText(this.context, "pseudo existe déjà!",
							Toast.LENGTH_LONG).show();
				}else if (this.resultErrorReturn == 3) {
					Toast.makeText(this.context, "email existe déjà!",
							Toast.LENGTH_LONG).show();
				}
			}
		} else if (connection.equals("refreshInfoUser")) {
			if (!result) {
				if(this.resultErrorReturn == 0){
					Toast.makeText(this.context, "Information utilisateur mis à jour!",
							Toast.LENGTH_LONG).show();
				}else if (this.resultErrorReturn == 1) {
//					Toast.makeText(this.context, "Pas de mise à jour à effectué",
//							Toast.LENGTH_LONG).show();
				}else if (this.resultErrorReturn == 2) {
					Toast.makeText(this.context, "Information utilisateur incorrecte!",
							Toast.LENGTH_LONG).show();
				}else if (this.resultErrorReturn == 3) {
					Toast.makeText(this.context, "Erreur récupération info utilisateur!",
							Toast.LENGTH_LONG).show();
				}
			}
		}
		// On enleve le loader de chargement
		pd.dismiss();
	}

	@Override
	protected Boolean doInBackground(Void... arg0) { // Actions à exécuter en
														// tache de fond
		String connection = this.connectionType;
		Boolean res = false;
		resultErrorReturn = 1;

		// Si le webservice concerne l'authentication
		if (connection.equals("Authentication")) {
			String url = this.domainUrl + "/authentication-mobile.php";
			// On récupére les info du serveur
			try {
				JSONArray infoServerData = getServerData(this.data, url);
				JSONObject infoWebserviveReturn = infoServerData
						.getJSONObject(0);
				// Parse les données JSON
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
				} else {
					res = false;
				}
			} catch (JSONException e) {
				Log.e("log_authentication", "Mauvaise connection " + e.toString());
			}
		} else if (connection.equals("Registration")) {
			String url = this.domainUrl + "/registration-mobile.php";
			// On récupére les info du serveur
			try {
				JSONArray infoServerData = getServerData(this.data, url);
				JSONObject infoWebserviveReturn = infoServerData
						.getJSONObject(0);
				Log.e("log_tagJson", "Error parsing data " + infoServerData);
				// Parse les données JSON
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
			// On récupére les info du serveur
			try {
				JSONArray infoServerData = getServerData(this.data, url);
				JSONObject infoWebserviveReturn = infoServerData
						.getJSONObject(0);
				
				// Parse les données JSON
				//Si la date de modification est différente alors on doit charger les nouvelles données
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
					resultErrorReturn = 2;
				}
				res = false;
			} catch (JSONException e) {
				res = false;
				resultErrorReturn = 3;
				Log.e("log_refreshUser", "Erreur récupération info utilisateur" + e.toString());
			}
		}else if (connection.equals("listMember")) {

		} else if (connection.equals("listEvent")) {

		} else if (connection.equals("photoGalery")) {

		} else if (connection.equals("profilMember")) {

		} else if (connection.equals("profilAnimal")) {
			System.out.println("charge image élu");
		} else {
			res = false;
		}
		return res;
	}

	/**
	 * Set information to server Animalio
	 */
	public void postData() {
		try { // Création du client http
			HttpClient httpclient = new DefaultHttpClient();
			setRetry(); // On lui donne la possibilité de retenter la connexion
						// en cas d'échec
			((AbstractHttpClient) httpclient)
					.setHttpRequestRetryHandler(myRetryHandler);
			// On définie l'url du webservice en php
			HttpPost httppost = new HttpPost(
					"http://m.animalio.fr/Authentication.php");
			// On définie l'url du webservice en php
			httppost.setEntity(new UrlEncodedFormEntity(this.data));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// on envoie les données au serveur et on récupère sa réponse
			String response = httpclient.execute(httppost, responseHandler);
		} catch (Exception e) {
			Log.e("log_tag", "Error:  " + e.toString());
		}
	}

	/**
	 * méthode de récupération de données sur le serveur
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
			// ajout du manager de réponse afin de permettre le retry en fonction de la réponse
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
		// Convertion de la requête en string
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
		// Récupére un array de json
		try {
			resultat = new JSONArray(result);
		} catch (JSONException e) {
			Log.e("log_tagRecupére", "Error parsing data first " + e.toString());
		}

		return resultat;
	}

	/**
	 * Méthode permettant de retenter la connexion en cas d'échec
	 */
	public void setRetry() {
		myRetryHandler = new HttpRequestRetryHandler() {

			public boolean retryRequest(IOException exception,
					int executionCount, HttpContext context) {

				// Si la connexion a échoué plus de 3 fois on arrête de réessayer
				if (executionCount >= 4) {
					System.out.println("retry count");
					return true;
				}
				// retente la connexion si le serveur ne répond pas
				if (exception instanceof NoHttpResponseException) {
					System.out.println("NoHttpResponseException exception");
					return true;
				}
				// retente si la connexion a été reset
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
	 * Méthode testant la connexion internet
	 * 
	 * @param context
	 * @return Boolean true or false
	 */
	public static boolean haveNetworkConnection(Context context) {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;
		// récupération des informations à partir du téléphone
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
	 * Méthode renvoyant un toast d'erreur si pas de connexion internet
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
}
