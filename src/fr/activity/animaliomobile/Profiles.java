package fr.activity.animaliomobile;

import fr.animaliomobile.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Profiles extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profiles);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profil, menu);
		return true;
	}

}
