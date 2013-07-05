package fr.activity.animaliomobile;

import fr.animaliomobile.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

public class MemberProfiles extends Activity{
	private Button btn_animals_list;
	private Button btn_friends_list;
	private Button btn_friends_requests;
	private Button btn_notifications_list;
	private ViewFlipper vf_member_profil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_profiles);

		Bundle extra = getIntent().getExtras();
		boolean isFriend = extra.getBoolean("isFriend");

		vf_member_profil = (ViewFlipper) findViewById(R.id.vf_member_profil);
		//boutons
		btn_animals_list = (Button) findViewById(R.id.btn_animals_list);
		btn_friends_list = (Button) findViewById(R.id.btn_friends_list);
		btn_animals_list.setVisibility(View.VISIBLE);
		btn_friends_list.setVisibility(View.VISIBLE);
		btn_animals_list.setOnClickListener(eventClick);
		btn_friends_list.setOnClickListener(eventClick);


		if(isFriend){
			Button btn_send_msg = (Button) findViewById(R.id.btn_send_msg);
			btn_send_msg.setVisibility(View.VISIBLE);
			btn_send_msg.setOnClickListener(eventClick);

			btn_notifications_list = (Button) findViewById(R.id.btn_notifications_list);
			btn_notifications_list.setVisibility(View.VISIBLE);
			btn_notifications_list.setOnClickListener(eventClick);



			Button btn_delete_friend = (Button) findViewById(R.id.btn_delete_friend);
			btn_delete_friend.setVisibility(View.VISIBLE);
			btn_delete_friend.setOnClickListener(eventClick);
		}else{
			btn_friends_requests = (Button) findViewById(R.id.btn_friends_requests);
			btn_friends_requests.setVisibility(View.VISIBLE);
			btn_friends_requests.setOnClickListener(eventClick);
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
