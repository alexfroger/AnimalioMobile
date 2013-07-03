package fr.activity.animaliomobile;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import fr.animaliomobile.R;

public class Registration extends Activity{
	private Button btnRegistration;
	private Button btnRegistrationCancel;
	private EditText edit_lastname;
	private EditText edit_firstname;
	private EditText edit_nickname;
	private EditText edit_email;
	private EditText edit_pwd;
	private EditText edit_check_pwd;
	private static ArrayList<NameValuePair> data = new ArrayList<NameValuePair>(5);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.registration);
		
		//instanciation des boutons
		btnRegistration = (Button) findViewById(R.id.btn_registration);
        btnRegistrationCancel = (Button) findViewById(R.id.btn_registration_cancel);
        btnRegistration.setOnClickListener(eventClick);
		btnRegistrationCancel.setOnClickListener(eventClick);
		
		//instanciation des champs de saisie
		edit_lastname = (EditText)findViewById(R.id.registration_lastname);
		edit_firstname = (EditText)findViewById(R.id.registration_firstname);
		edit_nickname = (EditText)findViewById(R.id.registration_nickname);
		edit_email = (EditText)findViewById(R.id.registration_email);
		edit_pwd = (EditText)findViewById(R.id.registration_password);
		edit_check_pwd = (EditText)findViewById(R.id.registration_check_password);
	}
	
	//On crée un écouteur d'évènement commun au deux Button
    OnClickListener eventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(v==btnRegistrationCancel){
				//Cancel the view ConnectionDialog
				finish();
			}
			
			if(v==btnRegistration){	
				//si ou ou plusieurs champs sont vides
				if(edit_lastname.getText().toString().equals("")||edit_firstname.getText().toString().equals("")
						||edit_nickname.getText().toString().equals("")||edit_email.getText().toString().equals("")
						||edit_pwd.getText().toString().equals("")||edit_check_pwd.getText().toString().equals("")){
					Toast t = Toast.makeText(getApplicationContext(),
							R.string.field_empty,
							Toast.LENGTH_LONG);
					t.setGravity(Gravity.BOTTOM, 0, 40);
					t.show();
				//sinon si l'adresse email n'est pas un format valide
				} else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(edit_email.getText().toString()).matches()){
					Toast t = Toast.makeText(getApplicationContext(),
							R.string.invalid_email,
							Toast.LENGTH_LONG);
					t.setGravity(Gravity.BOTTOM, 0, 40);
					t.show();
				//sinon si les deux mdp saisis ne sont pas identiques
				} else if (!edit_pwd.getText().toString().equals(edit_check_pwd)){
					Toast t = Toast.makeText(getApplicationContext(),
							R.string.different_pwd,
							Toast.LENGTH_LONG);
					t.setGravity(Gravity.BOTTOM, 0, 40);
					t.show();
				} else if (false/*Eventuelle vérification sur le format du mot de passe*/){
					
				} else if (false/*Vérification si l'email est déjà enregistré*/) {
					
				} else if (false /*Vérification si le pseudo est déjà utilisé*/){
					
				} else {
					//on ajoute les données saisies 
					data.add(new BasicNameValuePair("lastname", edit_lastname.getText().toString()));
					data.add(new BasicNameValuePair("firstname", edit_firstname.getText().toString()));
					data.add(new BasicNameValuePair("nickname", edit_nickname.getText().toString()));
					data.add(new BasicNameValuePair("email", edit_email.getText().toString()));
					data.add(new BasicNameValuePair("password", edit_pwd.getText().toString()));
					data.add(new BasicNameValuePair("check_password", edit_check_pwd.getText().toString()));
					
					//méthode d'envoie des données à ajouter en asyncTask
				}
			}
		}
    };
}
