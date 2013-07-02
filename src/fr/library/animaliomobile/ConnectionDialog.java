package fr.library.animaliomobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import fr.activity.animaliomobile.Authentication;
import fr.activity.animaliomobile.Home;
import fr.animaliomobile.R;

public class ConnectionDialog extends DialogFragment{
	private static final int CODE_MAIN_ACTIVITY = 1;
	private TextView btnConnection;
	private Button btnConnectionCancel;
	private TextView emailPseudo;
	private TextView password;

	//Constructor
	public ConnectionDialog() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Assigns a theme and style to the Dialog popUp
		int style = DialogFragment.STYLE_NO_TITLE;
		int theme = R.style.ThemeAnimalioAuthenticationPopUp;
		setStyle(style, theme);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		View view = inflater.inflate(R.layout.fragment_authentication_connection, container);
		// instantiate the dialog with the custom Theme  

		Typeface Lobster = Typeface.createFromAsset(Authentication.context.getAssets(), "Lobster.otf");

		btnConnection = (TextView)view.findViewById(R.id.txv_seConnecter);
		btnConnection.setTypeface(Lobster);

		
		emailPseudo = (TextView)view.findViewById(R.id.user_email);
		password = (TextView)view.findViewById(R.id.user_password);
		btnConnectionCancel = (Button)view.findViewById(R.id.btn_closed);
		//Buttons are assigned to the event listener
		btnConnection.setOnClickListener(eventClick);
		btnConnectionCancel.setOnClickListener(eventClick);
		return view;
	}

	//On crée un écouteur d'évènement commun au deux Button
	OnClickListener eventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(v==btnConnectionCancel){
				//Cancel the view ConnectionDialog
				getDialog().cancel();
			}
			if(v==btnConnection){
				// On créé un objet Bundle, c'est ce qui va nous permetre
				// d'envoyer des données à l'autre Activity
				Bundle objetbunble = new Bundle();

				// Cela fonctionne plus ou moins comme une HashMap, on entre une
				// clef et sa valeur en face
				// objetbunble.putString("email_pseudo", (String)
				// emailPseudo.getText());
				// objetbunble.putString("password", (String)
				// password.getText());
				objetbunble.putCharSequence("email_pseudo",
						emailPseudo.getText());
				objetbunble.putCharSequence("password", password.getText());

				// On créé l'Intent qui va nous permettre d'afficher l'autre
				// Activity
				// Attention il va surement falloir que vous modifier le premier
				// paramètre (Tutoriel9_Android.this)
				// Mettez le nom de l'Activity dans la quelle vous êtes
				// actuellement
				Intent intent = new Intent(Authentication.context, Home.class);

				// On affecte à l'Intent le Bundle que l'on a créé
				intent.putExtras(objetbunble);

				String email = emailPseudo.getText().toString();
				String mdp = password.getText().toString();

				// On démarre l'autre Activity
				if (email.equals("test") && mdp.equals("test")) {
					//Start the Activity Home
					startActivityForResult(intent, CODE_MAIN_ACTIVITY);

					//Close the authentication activity
					getActivity().finish();

					// Toast reussit
					//					Toast t = Toast.makeText(Authentication.context,
					//							"Vous êtes connecté",
					//							Toast.LENGTH_LONG);
					//					t.setGravity(Gravity.BOTTOM, 0, 40);
					//					t.show();
				} else {
					// Toast d'erreur
					Toast t = Toast.makeText(Authentication.context,
							"Mot de passe ou Pseudo/Email invalide",
							Toast.LENGTH_LONG);
					t.setGravity(Gravity.BOTTOM, 0, 40);
					t.show();
				}
			}
		}
	};
}
