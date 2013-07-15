package fr.activity.animaliomobile;

import java.util.ArrayList;

import fr.animaliomobile.R;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;


public class Gallery extends Activity{
	private int nbImg;
	private int nbLigne;
	private int nbImgLastLigne;
	private int nbImgPerLigne;
	private LinearLayout gallery;
	private LinearLayout.LayoutParams param_lay;
	private LinearLayout.LayoutParams param_img;
	private ArrayList<ImageView> images= new ArrayList<ImageView>();
	private ArrayList<Integer> id_image = new ArrayList<Integer>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		nbImg = 20;
		gallery = (LinearLayout)findViewById(R.id.lay_gallery);


		for (int i = 0 ; i < nbImg ; i++){
			ImageView image = new ImageView(this);
			images.add(image);
			id_image.add(this.getResources().getIdentifier("image_gallery_"+i, "drawable", this.getPackageName()));
		}


		if(getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) 
		{
			// code to do for Portrait Mode
			createGallery(3);

		} else {
			// code to do for Landscape Mode
			createGallery(7);
		}


	}
	
	private void createGallery(int nbItemPerLigne){
		param_lay = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		param_img = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
		param_img.weight = 1.0f;
		nbImgPerLigne = nbItemPerLigne;
		nbLigne = nbImg / nbImgPerLigne;
		nbImgLastLigne = nbImg % nbImgPerLigne;
		
		int actualImg = 0;
		for(int i = 0 ; i < nbLigne ; i++){
			LinearLayout lay_ligne = new LinearLayout(this);
			lay_ligne.setOrientation(LinearLayout.HORIZONTAL);
			if(actualImg%2 == 0){lay_ligne.setBackgroundColor(Color.parseColor("#ffffff"));}
			for(int j = 0 ; j < nbImgPerLigne ; j++){
				if(actualImg%2 == 0){images.get(actualImg).setBackgroundColor(Color.parseColor("#000000"));}
				images.get(actualImg).setImageResource(id_image .get(actualImg));
				images.get(actualImg).setAdjustViewBounds(true);
				lay_ligne.addView(images.get(actualImg), param_img);
				actualImg++;
			}
			gallery.addView(lay_ligne, param_lay);
		}
		if(nbImgLastLigne != 0){
			LinearLayout lay_ligne = new LinearLayout(this);
			lay_ligne.setWeightSum(nbImgPerLigne);
			lay_ligne.setOrientation(LinearLayout.HORIZONTAL);
			for(int k = 0 ; k < nbImgLastLigne ; k++){
				images.get(actualImg).setImageResource(id_image .get(actualImg));
				images.get(actualImg).setAdjustViewBounds(true);
				lay_ligne.addView(images.get(actualImg), param_img);
				actualImg++;
			}
			gallery.addView(lay_ligne, param_lay);
		}
	}
}
