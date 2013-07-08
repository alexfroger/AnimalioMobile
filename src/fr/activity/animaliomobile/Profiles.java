package fr.activity.animaliomobile;

import fr.animaliomobile.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

public class Profiles extends Activity{
	private Button btn_animals_list;
	private Button btn_friends_list;
	private Button btn_notifications_list;
	private Button btn_delete_friend;
	private Button btn_send_msg;
	private Button btn_friends_requests;
	private Button btn_delete_animal;
	private Button btn_animal_modification;
	private Button btn_msg;
	private Button btn_user_modification;
	private ViewFlipper vf_member_profil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_profiles);
		
		Bundle extra = getIntent().getExtras();
		int typeProfil = extra.getInt("typeProfil");

		vf_member_profil = (ViewFlipper) findViewById(R.id.vf_member_profil);
		
		

		switch (typeProfil) {
		case 0: // Profil utilisateur
			// Bouton Messagerie
			btn_msg = (Button) findViewById(R.id.btn_msg);
			btn_msg.setVisibility(View.VISIBLE);
			btn_msg.setOnClickListener(eventClick);
			// Bouton Liste animaux
			btn_animals_list = (Button) findViewById(R.id.btn_animals_list);
			btn_animals_list.setVisibility(View.VISIBLE);
			btn_animals_list.setOnClickListener(eventClick);
			// Bouton Liste amis
			btn_friends_list = (Button) findViewById(R.id.btn_friends_list);
			btn_friends_list.setVisibility(View.VISIBLE);
			btn_friends_list.setOnClickListener(eventClick);
			// Bouton Liste des notifications
			btn_notifications_list = (Button) findViewById(R.id.btn_notifications_list);
			btn_notifications_list.setVisibility(View.VISIBLE);
			btn_notifications_list.setOnClickListener(eventClick);
			// Bouton Modifier
			btn_user_modification = (Button) findViewById(R.id.btn_user_modification);
			btn_user_modification.setVisibility(View.VISIBLE);
			btn_user_modification.setOnClickListener(eventClick);
			break;
		case 1: // Profil membre
			// Bouton Liste animaux
			btn_animals_list = (Button) findViewById(R.id.btn_animals_list);
			btn_animals_list.setVisibility(View.VISIBLE);
			btn_animals_list.setOnClickListener(eventClick);
			// Bouton Liste amis
			btn_friends_list = (Button) findViewById(R.id.btn_friends_list);
			btn_friends_list.setVisibility(View.VISIBLE);
			btn_friends_list.setOnClickListener(eventClick);
			// Bouton Demande d'ami
			btn_friends_requests = (Button) findViewById(R.id.btn_friends_requests);
			btn_friends_requests.setVisibility(View.VISIBLE);
			btn_friends_requests.setOnClickListener(eventClick);
			break;
		case 2: // Profil ami
			// Bouton Envoyer message
			btn_send_msg = (Button) findViewById(R.id.btn_send_msg);
			btn_send_msg.setVisibility(View.VISIBLE);
			btn_send_msg.setOnClickListener(eventClick);
			// Bouton Liste animaux
			btn_animals_list = (Button) findViewById(R.id.btn_animals_list);
			btn_animals_list.setVisibility(View.VISIBLE);
			btn_animals_list.setOnClickListener(eventClick);
			// Bouton Liste ami
			btn_friends_list = (Button) findViewById(R.id.btn_friends_list);
			btn_friends_list.setVisibility(View.VISIBLE);
			btn_friends_list.setOnClickListener(eventClick);
			// Bouton Liste des nofication
			btn_notifications_list = (Button) findViewById(R.id.btn_notifications_list);
			btn_notifications_list.setVisibility(View.VISIBLE);
			btn_notifications_list.setOnClickListener(eventClick);
			// Bouton Supprimer de la liste d'amis
			btn_delete_friend = (Button) findViewById(R.id.btn_delete_friend);
			btn_delete_friend.setVisibility(View.VISIBLE);
			btn_delete_friend.setOnClickListener(eventClick);
			break;
		default: //Profil animal
			// Bouton modifier animal
			btn_animal_modification = (Button) findViewById(R.id.btn_animal_modification);
			btn_animal_modification.setVisibility(View.VISIBLE);
			btn_animal_modification.setOnClickListener(eventClick);
			// Bouton Supprimer animal
			btn_delete_animal = (Button) findViewById(R.id.btn_delete_animal);
			btn_delete_animal.setVisibility(View.VISIBLE);
			btn_delete_animal.setOnClickListener(eventClick);
			break;
		}
	}

	OnClickListener eventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(v==btn_animals_list){
				vf_member_profil.setDisplayedChild(1);
			}
			if(v==btn_friends_list){
				vf_member_profil.setDisplayedChild(2);
			}
			if(v==btn_friends_requests){
				vf_member_profil.setDisplayedChild(0);
			}
		}
	};
}
