package fr.library.animaliomobile;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import fr.animaliomobile.R;

public class DownloadImage {
	private RoundedImageView roundedView;
	private ImageView imgView;
	private String imgUrl;
	private String domainUrl = "http://m.animalio.fr/pictures/";
	private Activity activity;
	
	public DownloadImage(Activity activity, ImageView imgView, String imgUrl) {
		this.activity = activity;
		this.imgView = imgView;
		this.imgUrl = imgUrl;
	}


	public DownloadImage(Activity activity, RoundedImageView roundedView, String imgUrl) {
		this.activity = activity;
		this.roundedView = roundedView;
		this.imgUrl = imgUrl;
	}


	public void downloadImageView() {
		Thread thread2;
		
		thread2 = new Thread() {
			Bitmap bitmap1 = null;
			Boolean getError = false;
			@Override
			public void run() {
				try {
					Log.e("log_avatarUpload",
							"urlImage : " + (domainUrl + imgUrl));
					URL urlImage = new URL(domainUrl + imgUrl);
					HttpURLConnection connection = (HttpURLConnection) urlImage.openConnection();
					InputStream inputStream = connection.getInputStream();
					
					bitmap1 = BitmapFactory.decodeStream(inputStream);
				} catch (MalformedURLException e) {
					getError = true;
					Log.w("log_avatarUpload",
							"MalformedURLException : " + e.toString());
				} catch (IOException e) {
					getError = true;
					Log.w("log_avatarUpload",
							"IOException : " + e.toString());
				}   	
			
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(getError){
							//Si il y a une erreur on affiche une image indisponible
							imgView.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_defaultuser));
						}else{
							imgView.setImageBitmap(bitmap1);
						}
					}
				});
			}
		};
		thread2.start();	
    }
	
	public void downloadRoundedImageView() {
		Thread thread2;
		
		thread2 = new Thread() {
			Bitmap bitmap1 = null;
			Boolean getError = false;
			@Override
			public void run() {
				try {
					URL urlImage = new URL(domainUrl + imgUrl);
					HttpURLConnection connection = (HttpURLConnection) urlImage.openConnection();
					InputStream inputStream = connection.getInputStream();
					
					bitmap1 = BitmapFactory.decodeStream(inputStream);
				} catch (MalformedURLException e) {
					getError = true;
					Log.w("log_avatarUpload",
							"MalformedURLException : " + e.toString());
				} catch (IOException e) {
					getError = true;
					Log.w("log_avatarUpload",
							"IOException : " + e.toString());
				}   	
			
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(getError){
							//Si il y a une erreur on affiche une image indisponible
							roundedView.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_defaultuser));
						}else{
							roundedView.setImageBitmap(bitmap1);
						}
					}
				});
			}
		};
		thread2.start();	
    }
}
