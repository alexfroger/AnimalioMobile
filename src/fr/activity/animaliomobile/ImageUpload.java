package fr.activity.animaliomobile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import fr.animaliomobile.R;
import fr.library.animaliomobile.ConnectionWebservicePHP;
import fr.library.animaliomobile.TypefaceSpan;

public class ImageUpload extends Activity {
	private File mFichier;
	private ImageView imageView;
	private Button btn_upload_picture;
	private Button btn_save_picture;
	private Button btn_take_picture;
	SharedPreferences preferences;
	private Bitmap bitmap;
	private Boolean isUpload = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_upload);
		imageView = (ImageView) this.findViewById(R.id.img_upload);
		btn_take_picture = (Button) this.findViewById(R.id.btn_take_picture);
		btn_upload_picture = (Button) this.findViewById(R.id.btn_upload_picture);
		btn_save_picture = (Button) this.findViewById(R.id.btn_save_picture);
		
		btn_save_picture.setOnClickListener(eventClick);
		btn_take_picture.setOnClickListener(eventClick);
		btn_upload_picture.setOnClickListener(eventClick);
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		if(mFichier != null){
			mFichier.delete();
		}
	}
			
	/**
	 * Gestion de l'action bar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_action_bar, menu);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			SpannableString s = new SpannableString("Prise photo");
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
			Intent i = new Intent (getApplicationContext(), Events.class);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Si on revient de l'activité qu'on avait lancée avec le code
		// PHOTO_RESULT
		if (requestCode == 1 && resultCode == RESULT_OK) {
			// Si l'image est une miniature
			if (data != null) {
				if (data.hasExtra("data")) {
					Bitmap thumbnail = data.getParcelableExtra("data");
					imageView.setImageBitmap(thumbnail);
					
					btn_save_picture.setVisibility(View.VISIBLE);
				}
			} else {
				// On recupere l'image en bitmap et on l'affiche avant de l'envoyer sur le serveur
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPurgeable = true;
				options.inSampleSize = 2;
				bitmap = BitmapFactory.decodeFile(mFichier.toString(), options);
				
				//On réoriente la photo avant de l'enregistrer
	            ExifInterface exif;
	            int rotate = 0;
	            int orientation = 0;
				try {
					exif = new ExifInterface(mFichier.toString());
					orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
				} catch (IOException e1) {
					Log.e("log_ImgUploadOrientation", "Orientation : " + e1.toString());
					isUpload = false;
				}

	            switch (orientation) 
	            {
	                case ExifInterface.ORIENTATION_ROTATE_90:
	                   rotate = 90;
	                   break; 
	                case ExifInterface.ORIENTATION_ROTATE_180:
	                   rotate = 180;
	                   break;
	                case ExifInterface.ORIENTATION_ROTATE_270:
	                   rotate = 270;
	                   break;
	             }
	            
				Matrix matrix = new Matrix();
			    matrix.postRotate(rotate);
			    
			    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

				try{
				    OutputStream out = new FileOutputStream(mFichier.toString());
				    if(bitmap.compress(Bitmap.CompressFormat.JPEG,90,out)){
				    	imageView.setImageBitmap(bitmap);
				    }
				    out.flush();
				    out.close();

				    btn_save_picture.setVisibility(View.VISIBLE);
				    isUpload = true;
				}catch(Exception e){
					Toast t = Toast.makeText(Home.context,
							"La photo n'a pû être sauvegardée.",
							Toast.LENGTH_SHORT);
					t.setGravity(Gravity.TOP, 0, 90);
					t.show();
					btn_save_picture.setVisibility(View.INVISIBLE);
					isUpload = false;
				}			
			}
		}else if(requestCode == 2 && resultCode == RESULT_OK){
			// On recupere l'image en bitmap et on l'affiche avant de l'envoyer sur le serveur
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPurgeable = true;
			options.inSampleSize = 2;

			Uri selectedImage = data.getData();
			
			Bitmap yourSelectedImage;
			InputStream  imageStream;
			try {
				imageStream = getContentResolver().openInputStream(selectedImage);
				yourSelectedImage = BitmapFactory.decodeStream(imageStream, null, options);
				imageView.setImageBitmap(yourSelectedImage);
				btn_save_picture.setVisibility(View.VISIBLE);
				isUpload = true;
			} catch (FileNotFoundException e1) {
				Toast t = Toast.makeText(Home.context,
						"La photo n'a pû être sauvegardée.",
						Toast.LENGTH_SHORT);
				t.setGravity(Gravity.TOP, 0, 90);
				t.show();
				btn_save_picture.setVisibility(View.INVISIBLE);
				Log.e("log_FileNotFoundException", "FileNotFoundException : " + e1.toString());
				isUpload = false;
			}	
		}
	}

	private void doTakePhotoAction() {
		if(mFichier != null){
			mFichier.delete();
		}
		// L'endroit où sera enregistrée la photo
		// Remarquez que mFichier est un attribut de ma classe
		File pictureFileDir = getDir();

		if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
			Toast.makeText(
					getApplicationContext(),
					"Impossible de créer le répertoire pour sauvegarder la photo.",
					Toast.LENGTH_LONG).show();
		} else {
			String fileName = "animalio-"
					+ String.valueOf(System.currentTimeMillis()) + ".jpg";
			mFichier = new File(pictureFileDir, fileName);
			// On récupère ensuite l'URI associée au fichier
			Uri fileUri = Uri.fromFile(mFichier);
			// Maintenant, on crée l'intent
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// Et on déclare qu'on veut que l'image soit enregistrée là où
			// pointe l'URI
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			// Enfin, on lance l'intent pour que l'application de photo se lance
			startActivityForResult(intent, 1);
		}
	}

	private void uploadPhoto() {
		if(mFichier != null){
			mFichier.delete();
		}
		
		startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 2);
	}
	
	// Méthode récupérant l'emplacement de sauvegarde
	private File getDir() {
		// On récupère l'emplacement de sauvegarde de photo sur le téléphone
		File sdDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		// on retourne l'emplacement et le nom du dossier qui se situera à
		// l'intérieur
		return new File(sdDir, "Animalio");
	}
	
	
	
	// On crée un écouteur d'évènement commun au deux Button
		OnClickListener eventClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v == btn_take_picture) {
					doTakePhotoAction();
				}
				if (v == btn_upload_picture) {
					uploadPhoto();
				}
				if (v == btn_save_picture) {
					if(isUpload){
						// Si connexion existe et que le fichier existe
						//MARCHE
//						if (ConnectionWebservicePHP.haveNetworkConnection(v.getContext())) {
//							ConnectionWebservicePHP calcul = new ConnectionWebservicePHP(
//									1, "uploadPicture", mFichier, v.getContext());
//							calcul.execute();
//						} else { // Sinon toast de problème
//							ConnectionWebservicePHP.haveNetworkConnectionError(v.getContext());
//						}
						
						showAssignAs(v.getContext());
					}
				}
			}
		};
		
		public void showAssignAs(final Context context){
			CharSequence colors[] = new CharSequence[] {"Profil", "Animal", "Profil avatar", "Animal avatar"};

			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("Lié à : ");
			builder.setItems(colors, new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			    	Toast t = Toast.makeText(context,
							"Faire la liaison",
							Toast.LENGTH_SHORT);
					t.setGravity(Gravity.TOP, 0, 90);
					t.show();
			    }
			});
			builder.show();
		}
}
