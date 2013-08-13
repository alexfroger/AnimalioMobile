package fr.library.animaliomobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class ConnectionWebservicePHPPicture extends AsyncTask<Void, Integer, ArrayList<Picture>> {

	private int loaderType; // affiche le layout de la premiere page de
							// chargement de l'application
	private String connectionType; // affiche le type de connexion à effectué
	public Context context; // affiche le context de la vue ou la connexion au
							// webservice est nécessaire
	public RelativeLayout layMessage;
	public ScrollView layScrollview;
	private ProgressDialog pd;
	private ArrayList<Picture> arrayInfoWebservice = new ArrayList<Picture>();
	public static String domainUrl = "http://m.animalio.fr/pictures/";
	HttpRequestRetryHandler myRetryHandler; // Récupérateur de réponse HTTP
	public ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	public int resultErrorReturn;
	public int nbMsgMin;
	public ArrayList<Object> webserviceReturn = new ArrayList<Object>();
	public static Boolean isFinish = false;

	private String urlImg;

	/**
	 * Constuctor of Animalio Webservice Picture
	 * 
	 * @param _loaderType
	 * @param _connectionType
	 * @param _context
	 * @param _data
	 */
	public ConnectionWebservicePHPPicture(int _loaderType, String _connectionType,
			Context _context, String _urlImg) {
		this.loaderType = _loaderType;
		this.connectionType = _connectionType;
		this.context = _context;
		this.urlImg = _urlImg;
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
	protected void onPostExecute(ArrayList<Picture> result) {
		// On enleve le loader de chargement
		pd.dismiss();
		
		isFinish = true;
	}

	@Override
	protected ArrayList<Picture> doInBackground(Void... arg0) {
		
		String connection = this.connectionType;
		resultErrorReturn = 1;
		
		// Si le webservice concerne le profil
		if (connection.equals("uploadImage")){
			Picture newPicture = new Picture(downloadImage(urlImg));
			arrayInfoWebservice.add(newPicture);
		}
		
		return arrayInfoWebservice;
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
	
	public static Bitmap downloadImage(String urlOfImage) {

		Bitmap bitmap = null;

		try {
			URL urlImage = new URL(domainUrl + urlOfImage);
			HttpURLConnection connection = (HttpURLConnection) urlImage.openConnection();
			InputStream inputStream = connection.getInputStream();
			bitmap = BitmapFactory.decodeStream(inputStream);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bitmap;

	}
}
