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
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import fr.activity.animaliomobile.Home;

public class ConnectionWebservicePHP extends AsyncTask<Void, Integer, Boolean> {

	private int loaderType; // affiche le layout de la premiere page de
							// chargement de l'application
	private String connectionType; // affiche le type de connexion � effectu�
	public Context context; // affiche le context de la vue ou la connexion au
							// webservice est n�cessaire
	private ProgressDialog pd;
	public String domainUrl = "http://m.animalio.fr/";
	HttpRequestRetryHandler myRetryHandler; // R�cup�rateur de r�ponse HTTP
	public ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();

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
		// On affiche un loader de chargement
		pd = ProgressDialog.show(this.context, "", "Chargement ...", true);
		pd.setCancelable(false);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (result) {
			// On cr�� l'Intent qui va nous permettre d'afficher l'autre
			// Activity
			Intent intent = new Intent(this.context, Home.class);
			// On supprime l'activity de login sinon
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			this.context.startActivity(intent);
		} else {
			Toast.makeText(this.context, "Login Incorrect!", Toast.LENGTH_SHORT)
					.show();
		}
		// On enleve le loader de chargement
		pd.dismiss();
	}

	@Override
	protected Boolean doInBackground(Void... arg0) { //Actions � ex�cuter en tache de fond
		String connection = this.connectionType;
		Boolean res = false;
		
		//Si le webservice concerne l'authentication
		if(connection.equals("Authentication")){
			String url = this.domainUrl + "/authentication-mobile.php";
			//On r�cup�re les info du serveur
			try{
				JSONArray infoServerData = getServerData(this.data, url);
				JSONObject infoWebserviveReturn = infoServerData.getJSONObject(0);
				// Parse les donn�es JSON
					if(infoWebserviveReturn.getInt("isOk") == 1){ //Si la connection est correcte
						res = true;
						//On stock les infos utilisateurs dans des preferences	
						SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString("idUser", infoWebserviveReturn.getString("idUser"));
						editor.putString("pseudoEmail", infoWebserviveReturn.getString("emailPseudo"));
						editor.putString("password", infoWebserviveReturn.getString("password"));
						editor.commit();
						
						String idUser = preferences.getString("idUser", "");
						String pseudoEmail = preferences.getString("pseudoEmail", "");
						String password = preferences.getString("password", "");
						Log.i("log_parseIDUSER", "ID_USER : " + idUser);
						Log.i("log_parseIDUSER", "PSEUDO_EMAIL : " + pseudoEmail);
						Log.i("log_parseIDUSER", "Password : " + password);

					}else{
						res = false;
					}
			}catch(JSONException e){
				Log.e("log_tagRecup�re", "Error parsing data " + e.toString());
			}
		}else if(connection.equals("Registration")){
			
		}else if(connection.equals("listMember")){
			
		}else if(connection.equals("listEvent")){
			
		}else if(connection.equals("photoGalery")){
			
		}else if(connection.equals("profilMember")){
			
		}else if(connection.equals("profilAnimal")){
			System.out.println("charge image �lu");
		}else{
			res = false;
		}
		return res;
	}

	/**
	 * Set information to server Animalio
	 */
	public void postData() {
		try { // Cr�ation du client http
			HttpClient httpclient = new DefaultHttpClient();
			setRetry(); // On lui donne la possibilit� de retenter la connexion
						// en cas d'�chec
			((AbstractHttpClient) httpclient)
					.setHttpRequestRetryHandler(myRetryHandler);
			// On d�finie l'url du webservice en php
			HttpPost httppost = new HttpPost(
					"http://m.animalio.fr/Authentication.php");
			// on passe en param�tre la liste dedonn�es � envoyer
			httppost.setEntity(new UrlEncodedFormEntity(this.data));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// on envoie les donn�es au serveur et on r�cup�res a r�ponse
			String response = httpclient.execute(httppost, responseHandler); 
		} catch (Exception e) {
			Log.e("log_tag", "Error:  " + e.toString());
		}
	}

	/**
	 * m�thode de r�cup�ration de donn�es sur le serveur
	 * 
	 * @param tabChamps
	 * @param url
	 * @return JSONArray
	 */
	public JSONArray getServerData(ArrayList<NameValuePair> dataSendTo,
			String url) {
		InputStream is = null;
		String result = "";
		String strURL = url;
		JSONArray resultat = null;// Resultat final
		// JSONArray jArray = null; //Resultat final
		ArrayList<NameValuePair> nameValuePairs = dataSendTo; // Data to send to server

		// Envoie de la commande http
		try {
			HttpClient httpclient = new DefaultHttpClient();
			setRetry();
			((AbstractHttpClient) httpclient)
					.setHttpRequestRetryHandler(myRetryHandler); // ajout du manager de
																	// r�ponse
																	// afin de
																	// permettre
																	// le retry
																	// en
																	// fonction
																	// de la
																	// r�ponse
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
			Log.e("log_tagRecup�re", "Error parsing data " + e.toString());
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

				if (executionCount >= 4) { // Si la connexion a �chou� plus de 3
											// fois on arr�te de r�essayer
					System.out.println("retry count");
					return true;
				}
				if (exception instanceof NoHttpResponseException) { // retente
																	// la
																	// connexion
																	// si le
																	// serveur
																	// ne r�pond
																	// pas
					System.out.println("NoHttpResponseException exception");
					return true;
				}
				if (exception instanceof java.net.SocketException) { // retente
																		// si la
																		// connexion
																		// a �t�
																		// reset
					System.out.println("java.net.SocketException exception");
					return true;
				}
				if (exception instanceof java.net.SocketTimeoutException) { // retente si timeOut
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
}
