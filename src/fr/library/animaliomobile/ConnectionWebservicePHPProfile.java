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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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

public class ConnectionWebservicePHPProfile extends AsyncTask<Void, Integer, ArrayList<JSONArray>> {

	private int loaderType; // affiche le layout de la premiere page de
							// chargement de l'application
	private String connectionType; // affiche le type de connexion à effectué
	public Context context; // affiche le context de la vue ou la connexion au
							// webservice est nécessaire
	public RelativeLayout layMessage;
	public ScrollView layScrollview;
	private ProgressDialog pd;
	private ArrayList<JSONArray> arrayInfoWebservice = new ArrayList<JSONArray>();
	public String domainUrl = "http://m.animalio.fr/";
	HttpRequestRetryHandler myRetryHandler; // Récupérateur de réponse HTTP
	public ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	public int resultErrorReturn;
	public int nbMsgMin;
	public ArrayList<Object> webserviceReturn = new ArrayList<Object>();
	public static Boolean isFinish = false;
	public String[] listObject = {
		"listMsg",
		"listAnimals",
		"listFriend",
		"getUserProvinceId",
		"userCityDefaut"
	};
	
	private static String idUser;
	private static String user_cityID;


	/**
	 * Constuctor of Animalio Webservice
	 * 
	 * @param _loaderType
	 * @param _connectionType
	 * @param _context
	 * @param _data
	 */
	public ConnectionWebservicePHPProfile(int _loaderType, String _connectionType,
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
	public ConnectionWebservicePHPProfile(int _loaderType, String _connectionType,
			Context _context) {
		this.loaderType = _loaderType;
		this.connectionType = _connectionType;
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
	public ConnectionWebservicePHPProfile(int _loaderType, String _connectionType,
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
	protected void onPostExecute(ArrayList<JSONArray> result) {
		// On enleve le loader de chargement
		pd.dismiss();
		
		isFinish = true;
	}

	@Override
	protected ArrayList<JSONArray> doInBackground(Void... arg0) { // Actions à exécuter en
														// tache de fond
		//On Récupére les préférences utilisateur si elle existe
		SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(this.context);
		
		idUser = preferences.getString("idUser", "");
		user_cityID = preferences.getString("cityID", "");
		
		String connection = this.connectionType;
		resultErrorReturn = 1;
		
		// Si le webservice concerne le profil
		if (connection.equals("listObject")){
			String url = this.domainUrl + "/list-object.php";
			
			for (int i = 0; i < listObject.length; i++) {
				// On récupére les info du serveur
				if(listObject[i].equals("listMsg")){
					// On vide la liste de données à envoyé si existe déjà
					data.clear();

					// On ajoute les valeurs
					data.add(new BasicNameValuePair("id_user", idUser));
					data.add(new BasicNameValuePair("list_name", "listMsg"));
					data.add(new BasicNameValuePair("nb_msg_min", "0"));
					data.add(new BasicNameValuePair("nb_msg_max", "20"));
					
					arrayInfoWebservice.add(getServerData(data, url));
				}
				if(listObject[i].equals("listAnimals")){
					// On vide la liste de données à envoyé si existe déjà
					data.clear();

					// On ajoute les valeurs
					data.add(new BasicNameValuePair("id_user", idUser));
					data.add(new BasicNameValuePair("list_name", "listAnimals"));
					data.add(new BasicNameValuePair("nb_msg_min", "0"));
					data.add(new BasicNameValuePair("nb_msg_max", "20"));
					
					arrayInfoWebservice.add(getServerData(data, url));
				}
				if(listObject[i].equals("listFriend")){
					// On vide la liste de données à envoyé si existe déjà
					data.clear();

					// On ajoute les valeurs
					data.add(new BasicNameValuePair("id_user", idUser));
					data.add(new BasicNameValuePair("list_name", "listFriend"));
					data.add(new BasicNameValuePair("nb_msg_min", "0"));
					data.add(new BasicNameValuePair("nb_msg_max", "20"));
					
					arrayInfoWebservice.add(getServerData(data, url));
				}
				if(listObject[i].equals("getUserProvinceId")){
					// On vide la liste de données à envoyé si existe déjà
					data.clear();

					// On ajoute les valeurs
					data.add(new BasicNameValuePair("id_user", idUser));
					data.add(new BasicNameValuePair("list_name", "getUserProvinceId"));
					data.add(new BasicNameValuePair("city_id", user_cityID));
					
					arrayInfoWebservice.add(getServerData(data, url));
				}
				if(listObject[i].equals("userCityDefaut")){
					// On vide la liste de données à envoyé si existe déjà
					data.clear();
					
					// On ajoute les valeurs
					data.add(new BasicNameValuePair("id_user", idUser));
					data.add(new BasicNameValuePair("list_name", "userCityDefaut"));
					data.add(new BasicNameValuePair("city_id", user_cityID));
					
					arrayInfoWebservice.add(getServerData(data, url));
				}
			}
		}else if (connection.equals("listObjectOther")){
			String url = this.domainUrl + "/list-object.php";
			
			arrayInfoWebservice.add(getServerData(data, url));
			
			Log.i("log_tagRecupéreProfile", "listObjectOther : " + arrayInfoWebservice);
		}
		
		return arrayInfoWebservice;
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
			Log.e("log_tagEnvoieProfileConnection", "Error in http connection " + e.toString());
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
			Log.e("log_tagConvertProfileConverting", "Error converting result " + e.toString());
		}
		// Récupére un array de json
		try {
			resultat = new JSONArray(result);
		} catch (JSONException e) {
			Log.e("log_tagRecupéreProfileParsing", "Error parsing data first " + e.toString());
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
