package fr.library.animaliomobile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import fr.activity.animaliomobile.Profiles;
import fr.animaliomobile.R;

public class AddAnimalDialog extends DialogFragment {
	private static final int CODE_MAIN_ACTIVITY = 1;
	// TextView
	private TextView txv_add_animal;

	// EditText
	private EditText add_an_lastname;
	private EditText add_an_description;
	private EditText add_an_birthday;
	private EditText add_an_death;

	// Button
	private Button btn_add_animal;
	private Button btn_connection_cancel;

	private static ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
	
	ArrayList<JSONArray> arrayListReturn;
	JSONArray arrayListProfilAnimalAdd;
	
	// Constructor
	public AddAnimalDialog() {
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
		View view = inflater.inflate(R.layout.fragment_add_animal, container);
		// instantiate the dialog with the custom Theme

		// EditText
		add_an_lastname = (EditText) view.findViewById(R.id.add_an_lastname);
		add_an_description = (EditText) view
				.findViewById(R.id.add_an_description);
		add_an_birthday = (EditText) view.findViewById(R.id.add_an_birthday);
		add_an_death = (EditText) view.findViewById(R.id.add_an_death);

		// Button
		btn_add_animal = (Button) view.findViewById(R.id.btn_add_animal);

		btn_connection_cancel = (Button) view.findViewById(R.id.btn_closed);
		// Buttons are assigned to the event listener
		btn_add_animal.setOnClickListener(eventClick);
		btn_connection_cancel.setOnClickListener(eventClick);

		return view;
	}

	// On crée un écouteur d'évènement commun au deux Button
	OnClickListener eventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v == btn_connection_cancel) {
				// Ferme le fragment de connection
				getDialog().cancel();
			}
			if (v == btn_add_animal) {
				if (add_an_lastname.getText().toString().equals("")
						|| add_an_birthday.getText().toString().equals("")) {
					Toast t = Toast.makeText(v.getContext(),
							"Veuillez remplir les champs obligatoires",
							Toast.LENGTH_LONG);
					t.setGravity(Gravity.BOTTOM, 0, 40);
					t.show();
				} else {
					// On vide la liste de données à envoyé si existe déjà
					data.clear();

					// On ajoute les valeurs
					data.add(new BasicNameValuePair("id_user", Profiles.idUser));
					data.add(new BasicNameValuePair("list_name",
							"addAnimal"));
					data.add(new BasicNameValuePair("add_an_lastname",
							add_an_lastname.getText().toString()));
					data.add(new BasicNameValuePair("add_an_description",
							add_an_description.getText().toString()));
					data.add(new BasicNameValuePair("add_an_birthday",
							add_an_birthday.getText().toString()));
					data.add(new BasicNameValuePair("add_an_death",
							add_an_death.getText().toString()));

					// Instancie la connection au webservice en thread
					if (ConnectionWebservicePHPProfile.haveNetworkConnection(v
							.getContext())) {
						ConnectionWebservicePHPProfile calcul = new ConnectionWebservicePHPProfile(
								1, "listObjectOther", v.getContext(), data);
						calcul.execute();
						try {
							arrayListReturn = calcul.get();
							arrayListProfilAnimalAdd = arrayListReturn
									.get(0);

							JSONObject infoWebserviveReturn;
							try {
								infoWebserviveReturn = arrayListProfilAnimalAdd
										.getJSONObject(0);

								if (infoWebserviveReturn.getInt("isOk") == 0) {
									// Erreur d'ajout de l'animal
									Toast.makeText(v.getContext(),
											"Erreur lors de l'ajout de l'animal",
											Toast.LENGTH_SHORT).show();
								} else {
									// Animal ajouté avec succès 
									// On ajoute l'animal a la lsite
									Animal animal1 = new Animal(
											infoWebserviveReturn.getInt("id_animal"),
											infoWebserviveReturn.getString("animal_name"));
									
									Profiles.animals.add(animal1);
									
									//On affecte le changement
									Profiles.adapter_animals_list.notifyDataSetChanged();
									
									// Ferme le fragment de connection
									getDialog().cancel();
									
									Toast.makeText(v.getContext(),
											"Animal ajouté avec succès avec succès",
											Toast.LENGTH_LONG).show();
								}
							} catch (JSONException e) {
								Log.e("log_listObjectAddAnimal",
										"JSONException :" + e.toString());
							}
						} catch (InterruptedException e) {
							Log.e("log_listObjectAddAnimal",
									"InterruptedException :" + e.toString());
						} catch (ExecutionException e) {
							Log.e("log_listObjectAddAnimal",
									"ExecutionException :" + e.toString());
						}
					} else { // Sinon toast de problème
						ConnectionWebservicePHPProfile
								.haveNetworkConnectionError(v.getContext());
					}

				}
			}
		}
	};
}
