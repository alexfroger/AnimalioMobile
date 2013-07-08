package fr.library.animaliomobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;

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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

public class ConnectionWebservicePHP extends AsyncTask<Void, Integer, Void>{
	private OnTaskCompleted listener; //écouteur signalant lorsque la tache est fini
	private int loaderType; //affiche le layout de la premiere page de chargement de l'application
	private String connectionType; //affiche le type de connexion à effectué

	HttpRequestRetryHandler myRetryHandler;	//Récupérateur de réponse HTTP

	//Constructeur
	public ConnectionWebservicePHP(OnTaskCompleted _listener, int _loaderType, String _connectionType){
		this.listener= _listener;
		this.loaderType = _loaderType;
		this.connectionType = _connectionType;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	@Override
	protected void onProgressUpdate(Integer... values){ //n'est pas appelé directement dans le code mais par l'intermédiaire de publishProgress(progress);
		super.onProgressUpdate(values);
		// Mise à jour de la ProgressBar
		SplashActivity.progressBar.setProgress(values[0]);
	}

	@Override
	protected Void doInBackground(Void... arg0) { //actions à exécuter en tache de fond
		switch(this.connectionType){
		case "Authentication": //Récupérer les info profil utilisateur
			break;
		case "Register": 
			break;
		case "listMember":
			break;
		case "listEvent":
			break;
		case "photoGalery":
			break;
		case "profilMember":
			break;
		case "profilAnimal":
			break;
		}
		int progress = 0; //variable indicant l'évolution du chargement
		//URL de connexion
		String strURL = "http://www.charenton.fr/garuda/weather.php";
		//Champ de la base
		String[] champsWeather = {"Id", "jourtexte", "date", "temperatureMatin", "temperatureAprem", "tempsMatin", "tempsAprem", "texteMatin", "texteAprem"};
		//récupération dans une liste de liste de string
		CommonUtilities.prévisions = getServerData(champsWeather, strURL); 
		//incrémentation de l'évolution du chargement
		progress+=9;	
		//affichage de l'évolution dans la barre de progrès
		publishProgress(progress);

		strURL = "http://www.charenton.fr/garuda/mag.php";
		String[] champsMag = {"drive"};
		CommonUtilities.urlMag = getServerData(champsMag, strURL).get(0).get(0);
		progress+=1;	
		publishProgress(progress);

		String[] champsAgenda = {"date", "texte"};
		strURL = "http://www.charenton.fr/garuda/schedule.php";
		CommonUtilities.listeAgenda = getServerData(champsAgenda, strURL);
		progress+=9;	
		publishProgress(progress);	

		String[] champsPharma = {"id", "nom", "adresse", "cp", "tel"};
		strURL = "http://www.charenton.fr/garuda/pharmacy.php";
		CommonUtilities.listePharma = getServerData(champsPharma, strURL);
		progress+=9;	
		publishProgress(progress);

		strURL = "http://www.charenton.fr/garuda/pharmacy_dates.php";
		String[] champsDatePharma = {"idPharmacie", "date"};
		CommonUtilities.listeDates = getServerData(champsDatePharma, strURL);
		progress+=9;	
		publishProgress(progress);

		String[] champsTypeElus = {"id", "liste"};
		strURL = "http://www.charenton.fr/garuda/elus_listes.php";
		CommonUtilities.listeTypeElus = getServerData(champsTypeElus, strURL);
		progress+=9;	
		publishProgress(progress);

		strURL = "http://www.charenton.fr/garuda/elus.php";
		String[] champsListeElus = {"id", "idliste", "nom", "fonction"};
		CommonUtilities.listeElus = getServerData(champsListeElus, strURL);
		progress+=9;	
		publishProgress(progress);

		//récupération des photos des élus sous forme de Drawable
		if (CommonUtilities.listeImageElus.size() == 0 ){
			System.out.println("charge image élu");
			//CommonUtilities.listeImageElus.clear();
			for(int i = 0; i < CommonUtilities.listeElus.size()+1; i++){
				String url = "http://www.charenton.fr/democratie_locale/img/elus_appli/elu_"+(i+1)+".jpg";
				try {
					CommonUtilities.listeImageElus.add(drawable_from_url(url, ""));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{System.out.println("on ne recharge pas les images des Elus");}
		progress+=9;	
		publishProgress(progress);

		strURL = "http://www.charenton.fr/garuda/news.php";
		String[] champsNews = {"id", "date", "theme", "image", "titre", "sousTitre", "texte", "texteLien1", "lien1", "texteLien2", "lien2", "texteLien3", "lien3"};
		CommonUtilities.listeNews = getServerData(champsNews, strURL);
		progress+=9;	
		publishProgress(progress);

		//récupération des images des news qui sont dans un dossier au nom de l'année en cours
		Calendar calendrier= Calendar.getInstance();				
		int anneeEnCours = calendrier.get(Calendar.YEAR);
		CommonUtilities.listeImageNews.clear();
		for(int i = 0; i < CommonUtilities.listeNews.size(); i++){
			String url = "http://www.charenton.fr/actualites/"+Integer.toString(anneeEnCours)+"/img/"+CommonUtilities.listeNews.get(i).get(3);
			try {
				CommonUtilities.listeImageNews.add(drawable_from_url(url, ""));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		progress+=9;	
		publishProgress(progress);

		strURL = "http://www.charenton.fr/garuda/numutiles_themes.php";
		String[] champsNumUtilesThemes = {"id", "nom"};
		CommonUtilities.listeNumUtilesThemes = getServerData(champsNumUtilesThemes, strURL);
		progress+=9;	
		publishProgress(progress);	

		strURL = "http://www.charenton.fr/garuda/numutiles.php";
		String[] champsNumUtiles = {"id", "idTheme", "nom", "adresse", "cp", "tel", "fax", "email", "site", "horaires"};
		CommonUtilities.listeNumUtiles = getServerData(champsNumUtiles, strURL);
		progress+=9;	
		publishProgress(progress);

		remplirData();
		postData();

		return null;
	}

	Drawable drawable_from_url(String url, String src_name) throws java.net.MalformedURLException, java.io.IOException 
	{
		return Drawable.createFromStream(((java.io.InputStream)
				new java.net.URL(url).getContent()), src_name);
	}

	@Override
	protected void onPostExecute(Void result) { //informe l'écouteur que la tâche est terminée
		listener.onTaskCompleted();
	}

	public void postData(){

		try
		{	// Création du client http
			HttpClient httpclient = new DefaultHttpClient();
			setRetry(); // On lui donne la possibilité de retenter la connexion en cas d'échec
			((AbstractHttpClient) httpclient).setHttpRequestRetryHandler(myRetryHandler); 
			HttpPost httppost = new HttpPost("http://www.charenton.fr/garuda/statistics.php"); // On définie l'url du script php
			httppost.setEntity(new UrlEncodedFormEntity(data)); // on passe en paramètre la liste de données à envoyer
			ResponseHandler<String> responseHandler=new BasicResponseHandler();
			String  response = httpclient.execute(httppost, responseHandler); // on envoie les données au serveur et on récupère sa réponse
		}catch(Exception e) 
		{
			Log.e("log_tag", "Error:  "+e.toString());
		}
	}

	//méthode de récupération de données sur le serveur
	public  ArrayList<ArrayList<String>> getServerData( String[] tabChamps, String url){
		InputStream is = null;
		String result = "";
		String strURL = url;
		String[] champs = tabChamps; //champ de la table dans la BDD
		ArrayList<ArrayList<String>> resultat = new ArrayList<ArrayList<String>>(); //liste permettant de stocker le résultat

		// Envoyer la requête au script PHP.
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id","1"));
		// Envoie de la commande http
		try{
			HttpClient httpclient = new DefaultHttpClient();
			setRetry();
			((AbstractHttpClient) httpclient).setHttpRequestRetryHandler(myRetryHandler); //ajout du manager de réponse afin de permettre le retry en fonction de la réponse
			HttpPost httppost = new HttpPost(strURL);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		}catch(Exception e){
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		// Convertion de la requête en string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
		}catch(Exception e){
			Log.e("log_tag", "Error converting result " + e.toString());
		}
		// Parse les données JSON
		try{
			JSONArray jArray = new JSONArray(result);
			for(int i=0 ; i<jArray.length() ; i++){
				JSONObject json_data = jArray.getJSONObject(i);
				ArrayList<String> liste=new ArrayList<String>();
				for(int j=0;j<champs.length;j++){
					liste.add(json_data.getString(champs[j]));
				}
				resultat.add(liste);
			}
			/*for(int i = 0; i<jArray.length() ; i++){
				for(int j = 0; j<champs.length;j++){
					System.out.println(resultat.get(i).get(j));
				}
			}*/
		}catch(JSONException e){
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return resultat;
	}

	//méthode permettant de retenter la connexion en cas d'échec
	public void setRetry() {
		myRetryHandler = new HttpRequestRetryHandler() {

			public boolean retryRequest(IOException exception,int executionCount,HttpContext context) {

				if (executionCount >= 4) { //Si la connexion a échoué plus de 3 fois on arrête de réessayer
					System.out.println("retry count");
					return true;
				}
				if (exception instanceof NoHttpResponseException) { //retente la connexion si le serveur ne répond pas
					System.out.println("NoHttpResponseException exception");
					return true;
				}
				if (exception instanceof java.net.SocketException) { //retente si la connexion a été reset
					System.out.println("java.net.SocketException exception");
					return true;
				}
				if (exception instanceof java.net.SocketTimeoutException) { //retente si timeOut
					System.out.println("java.net.SocketTimeoutException exception");
					return true;
				}
				HttpRequest request = (HttpRequest) context.getAttribute( ExecutionContext.HTTP_REQUEST);
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

}
