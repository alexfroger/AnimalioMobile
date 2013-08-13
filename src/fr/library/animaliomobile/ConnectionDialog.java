package fr.library.animaliomobile;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import fr.activity.animaliomobile.Authentication;
import fr.animaliomobile.R;

public class ConnectionDialog extends DialogFragment {
	private static final int CODE_MAIN_ACTIVITY = 1;
	private Button btnConnection;
	private TextView txvConnection;
	private Button btnConnectionCancel;
	private EditText emailPseudo;
	private EditText password;
	private TextView txv_mdpForget;
	private ProgressDialog mProgressDialog;
	private FragmentManager fm;
	private ForgetPasswordDialog ForgetPasswordDialog;
	private static ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();

	// Constructor
	public ConnectionDialog() {
		super();
		// Recover the object ressources
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Assigns a theme and style to the Dialog popUp
		int style = DialogFragment.STYLE_NO_TITLE;
		int theme = R.style.ThemeAnimalioAuthenticationPopUp;
		setStyle(style, theme);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.fragment_authentication_connection, container);
		// instantiate the dialog with the custom Theme

		Typeface Lobster = Typeface.createFromAsset(
				Authentication.context.getAssets(), "Lobster.otf");
		Typeface Arimo = Typeface.createFromAsset(
				Authentication.context.getAssets(), "Arimo-Regular.ttf");

		btnConnection = (Button) view.findViewById(R.id.txv_seConnecter);
		btnConnection.setTypeface(Lobster);

		txvConnection = (TextView) view.findViewById(R.id.txv_connexion);
		txvConnection.setTypeface(Arimo);

		txv_mdpForget = (TextView) view.findViewById(R.id.txv_mdpForget);
		txv_mdpForget.setTypeface(Arimo);
		txv_mdpForget.setOnClickListener(eventClick);

		emailPseudo = (EditText) view.findViewById(R.id.user_email);
		emailPseudo.setTypeface(Arimo);
		password = (EditText) view.findViewById(R.id.user_password);
		password.setTypeface(Arimo);
		btnConnectionCancel = (Button) view.findViewById(R.id.btn_closed);
		// Buttons are assigned to the event listener
		btnConnection.setOnClickListener(eventClick);
		btnConnectionCancel.setOnClickListener(eventClick);

		return view;
	}

	// On crée un écouteur d'évènement commun au deux Button
	OnClickListener eventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v == txv_mdpForget) {
				// Ferme le fragment de connection
				getDialog().cancel();
				fm = getActivity().getSupportFragmentManager();
				ForgetPasswordDialog = new ForgetPasswordDialog();
				ForgetPasswordDialog.show(fm, "fragment_authentication_connection");
			}

			if (v == btnConnectionCancel) {
				// Ferme le fragment de connection
				getDialog().cancel();
			}
			if (v == btnConnection) {
				if (emailPseudo.getText().toString().equals("")
						|| password.getText().toString().equals("")) {
					Toast t = Toast.makeText(v.getContext(),
							"Veuillez remplir les champs obligatoires",
							Toast.LENGTH_LONG);
					t.setGravity(Gravity.BOTTOM, 0, 40);
					t.show();
				} else {
					// On vide la liste de données à envoyé si existe déjà
					data.clear();
	
					// On ajoute les valeurs
					data.add(new BasicNameValuePair("email_pseudo", emailPseudo
							.getText().toString()));
					data.add(new BasicNameValuePair("password", password.getText()
							.toString()));
	
					// Instancie la connection au webservice en thread
					if (ConnectionWebservicePHP.haveNetworkConnection(v.getContext())) {
						ConnectionWebservicePHP calcul = new ConnectionWebservicePHP(
								1, "Authentication", v.getContext(), data);
						calcul.execute();
					} else { // Sinon toast de problème
						ConnectionWebservicePHP.haveNetworkConnectionError(v.getContext());
					}
				}
			}
		}
	};
}
