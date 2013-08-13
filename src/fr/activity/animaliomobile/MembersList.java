package fr.activity.animaliomobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import fr.animaliomobile.R;
import fr.library.animaliomobile.ConnectionWebservicePHP;
import fr.library.animaliomobile.ConnectionWebservicePHPProfile;
import fr.library.animaliomobile.DownloadImage;
import fr.library.animaliomobile.Friend;
import fr.library.animaliomobile.TypefaceSpan;

public class MembersList extends FragmentActivity {

	private Button btn_members;
	private Button btn_gallery;
	private Button btn_profil;
	private Button btn_events;
	private Button btn_live;
	private Button btn_photo;

	// Friend
	private static ListView lsv_members_list;
	public static CustomAdaptermembers adapter_members_list;
	private static ArrayList<Friend> members;
	
	private static ListView lsv_members_list1;
	public static CustomAdaptermembers adapter_members_list1;
	private static ArrayList<Friend> members1;

	private static ConnectionWebservicePHPProfile calcul;
	private static ConnectionWebservicePHPProfile calcul1;
	private static Thread thread;
	private static Handler handler;
	private static ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();

	// Arrays retour webservice
	private static ArrayList<JSONArray> arrayListReturn;
	private static JSONArray arrayListFriend;
	private static JSONArray arrayListMembers;
	// Preference
	public static String idUser;
	private static SharedPreferences preferences;

	private Typeface Arimo;
	private Typeface Lobster;
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.members_list);

		preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		idUser = preferences.getString("idUser", "");
		
		context = getApplicationContext();
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		btn_members = (Button) findViewById(R.id.btn_members);
		btn_gallery = (Button) findViewById(R.id.btn_gallery);
		btn_profil = (Button) findViewById(R.id.btn_profil);
		btn_events = (Button) findViewById(R.id.btn_events);
		btn_live = (Button) findViewById(R.id.btn_live);
		btn_photo = (Button) findViewById(R.id.btn_photo);

		btn_members.setBackgroundResource(R.drawable.ic_members_pressed);

		btn_gallery.setOnClickListener(eventClick);
		btn_profil.setOnClickListener(eventClick);
		btn_events.setOnClickListener(eventClick);
		btn_live.setOnClickListener(eventClick);
		btn_photo.setOnClickListener(eventClick);
	}

	// Create Listener event
	OnClickListener eventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if (v == btn_gallery) {
				// Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(),
						Gallery.class);
				startActivity(intent);
				finish();
			}

			else if (v == btn_profil) {
				// Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(),
						Profiles.class);
				intent.putExtra("typeProfil", 0);
				startActivity(intent);
				finish();
			}

			else if (v == btn_events) {
				// Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(),
						Events.class);
				startActivity(intent);
				finish();
			}

			else if (v == btn_live) {
				// Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(),
						AnimalioLive.class);
				startActivity(intent);
				finish();
			}

			else if (v == btn_photo) {
				// Display the photo phone application
				Toast t = Toast.makeText(Home.context,
						"Faire la redirection vers photo de l'appli",
						Toast.LENGTH_LONG);
				t.setGravity(Gravity.BOTTOM, 0, 40);
				t.show();
			}
		}
	};

	/**
	 * Gestion de l'action bar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_action_bar, menu);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			SpannableString s = new SpannableString(this.getTitle());
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
			Intent i = new Intent(getApplicationContext(), MembersList.class);
			startActivity(i);
			finish();
			return true;
		case R.id.menu_settings:
			// Comportement du bouton "Paramètres"
			return true;
		case R.id.menu_delete:
			// Comportement du bouton "Delete" A supprimer quand le popup
			// parametre sera creer car dedans

			// On créé l'Intent qui va nous permettre d'afficher l'autre
			// Activity
			Intent intent = new Intent(getApplicationContext(),
					Authentication.class);
			// On supprime l'activity de login sinon
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

			// Puis on reset les informations utilisateurs
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			SharedPreferences.Editor editor = preferences.edit();
			editor.clear();
			editor.commit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_member_all).toUpperCase(l);
			case 1:
				return getString(R.string.title_member_friend).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		private LinearLayout listeMembre;

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_members_list_dummy, container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);

			// Create the listMember
			// --- Recuperation de la listview creee dans
			// liste_avec_images_differentes.xml

			if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
				listeMembre = (LinearLayout) rootView.findViewById(R.id.listview);
				if (ConnectionWebservicePHP
						.haveNetworkConnection(getActivity())) {
					// On recupere les informations
					// On vide la liste de données à envoyé si existe déjà
					data.clear();

					// On ajoute les valeurs
					data.add(new BasicNameValuePair("id_user", idUser));
					data.add(new BasicNameValuePair("list_name",
							"ListFriendAll"));

					Log.i("log_DATA", "DATA : " + data);

					calcul1 = new ConnectionWebservicePHPProfile(1,
							"listObjectOther", getActivity(), data);
					calcul1.execute();

					handler = new Handler();

					thread = new Thread() {
						@Override
						public void run() {
							if (calcul1.isFinish == true) {
								try {
									arrayListReturn = calcul1.get();
									Log.i("log_arrayListReturn",
											"arrayListReturn : "
													+ arrayListReturn);
									arrayListMembers = arrayListReturn.get(0);

									getActivity().runOnUiThread(new Runnable() {

										@Override
										public void run() {
											try {
												listeMembre
														.addView(createListMembers(
																arrayListMembers,
																getActivity()));
												Log.i("log_arrayListMembers", "arrayListMembers : "
														+ arrayListMembers);
											} catch (Exception e) {
												Log.e("log_Thread", "Thread : "
														+ e.toString());
											}
										}
									});
								} catch (InterruptedException e) {
									Log.e("log_ArrayReturn3",
											"InterruptedExceptio3n : "
													+ e.toString());
								} catch (ExecutionException e) {
									Log.e("log_ArrayReturn3",
											"ExecutionException3 : "
													+ e.toString());
								}
							} else {
								handler.postDelayed(this, 100);
							}
						}
					};
					thread.start();
				} else { // Sinon toast de problème
					ConnectionWebservicePHP
							.haveNetworkConnectionError(getActivity());
				}
			}
			
			if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
				listeMembre = (LinearLayout) rootView.findViewById(R.id.listview1);
				if (ConnectionWebservicePHP
						.haveNetworkConnection(getActivity())) {
					// On recupere les informations
					// On vide la liste de données à envoyé si existe déjà
					data.clear();

					// On ajoute les valeurs
					data.add(new BasicNameValuePair("id_user", idUser));
					data.add(new BasicNameValuePair("list_name",
							"listFriend"));
					data.add(new BasicNameValuePair("nb_msg_min",
							"0"));
					data.add(new BasicNameValuePair("nb_msg_max",
							"500"));

					Log.i("log_DATA", "DATA : " + data);

					calcul = new ConnectionWebservicePHPProfile(1,
							"listObjectOther", getActivity(), data);
					calcul.execute();

					handler = new Handler();

					thread = new Thread() {
						@Override
						public void run() {
							if (calcul.isFinish == true) {
								try {
									arrayListReturn = calcul.get();
									Log.i("log_arrayListReturn",
											"arrayListReturn : "
													+ arrayListReturn);
									arrayListFriend = arrayListReturn.get(0);

									getActivity().runOnUiThread(new Runnable() {

										@Override
										public void run() {
											try {
												listeMembre
														.addView(createListFriends(
																arrayListFriend,
																getActivity()));
												Log.i("log_arrayListFriend", "arrayListFriend : "
														+ arrayListFriend);
											} catch (Exception e) {
												Log.e("log_Thread1", "Thread : "
														+ e.toString());
											}
										}
									});
								} catch (InterruptedException e) {
									Log.e("log_ArrayReturn3",
											"InterruptedExceptio3n : "
													+ e.toString());
								} catch (ExecutionException e) {
									Log.e("log_ArrayReturn3",
											"ExecutionException3 : "
													+ e.toString());
								}
							} else {
								handler.postDelayed(this, 100);
							}
						}
					};
					thread.start();
				} else { // Sinon toast de problème
					ConnectionWebservicePHP
							.haveNetworkConnectionError(getActivity());
				}
			}
			return rootView;
		}
	}

	protected static ListView createListMembers(JSONArray arrayListMembers1,
			Context context1) {
		lsv_members_list1 = new ListView(context1);
		members1 = new ArrayList<Friend>();

		try {
			// On ajoute les animaux que l'on à déjà créer
			for (int i = 0; i < arrayListMembers1.length(); i++) {
				JSONObject infoWebserviveReturn = arrayListMembers1
						.getJSONObject(i);

				if (!infoWebserviveReturn.isNull("id_user")) {
					Friend friend1 = new Friend(
							infoWebserviveReturn.getInt("id_user"),
							infoWebserviveReturn.getString("nickname"),
							infoWebserviveReturn.getString("avatar_name"),
							infoWebserviveReturn.getInt("on_mobile"));
					members1.add(friend1);
				} else {
					Friend friend1 = new Friend(0, "Pas encore d'amis", "", 0);
					members1.add(friend1);
				}
			}
		} catch (JSONException e) {
			Log.e("log_ListObjectMessage", "Erreur récupération messages : "
					+ e.toString());
		}

		adapter_members_list1 = new CustomAdaptermembers(context1, members1);
		lsv_members_list1.setAdapter(adapter_members_list1);
		
		// Ajout d'un onclick listener sur chaque element
		lsv_members_list1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
		return lsv_members_list1;
	}
	
	protected static ListView createListFriends(JSONArray arrayListFriends1,
			Context context1) {
		lsv_members_list = new ListView(context1);
		members = new ArrayList<Friend>();

		try {
			// On ajoute les amis que l'on à déjà créer
			for (int i = 0; i < arrayListFriends1.length(); i++) {
				JSONObject infoWebserviveReturn = arrayListFriends1
						.getJSONObject(i);

				if (!infoWebserviveReturn.isNull("friend_id")) {
					Friend friend1 = new Friend(
							infoWebserviveReturn.getInt("friend_id"),
							infoWebserviveReturn.getString("nickname"),
							infoWebserviveReturn.getString("avatar_name"),
							infoWebserviveReturn.getInt("on_mobile"));
					members.add(friend1);
				} else {
					Friend friend1 = new Friend(0, "Pas encore d'amis", "", 0);
					members.add(friend1);
				}
			}
		} catch (JSONException e) {
			Log.e("log_ListObjectMessage", "Erreur récupération messages : "
					+ e.toString());
		}

		adapter_members_list = new CustomAdaptermembers(context1, members);
		lsv_members_list.setAdapter(adapter_members_list);
		// Ajout d'un onclick listener sur chaque element
		lsv_members_list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Friend res = (Friend) lsv_members_list.getItemAtPosition(position);

				if (res.id != 0) {
					// Si l'amis n'as pas d'amis
					Intent intent = new Intent(context,
							Profiles.class);

					intent.putExtra("typeProfil", 2);
					intent.putExtra("friendId", res.id);
					intent.putExtra("friendName", res.nickname);
					intent.putExtra("friendPosition", position);

					parent.getContext().startActivity(intent);
				}
			}
		});
		return lsv_members_list;
	}
}

class CustomAdaptermembers extends BaseAdapter {
	private Context context;
	private List<Friend> members;

	// Constructeur
	public CustomAdaptermembers(Context _context, List<Friend> _members) {
		this.context = _context;
		this.members = _members;
	}

	public int getCount() {
		return members.size();
	}

	public Object getItem(int position) {
		return members.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Friend friend = members.get(position);
		View v = null;
		// Change la couleur du background de l'item, un item sur deux
		v = new CustomAdapterViewFriend(this.context, friend);
		// v.setBackgroundColor((position % 2) == 1 ? Color.rgb(204,204,204)
		// : Color.WHITE);
		return v;
	}
}

// Adapter définissant la façon dont est affiché chaque item de la liste
class CustomAdapterViewFriend extends LinearLayout {

	public CustomAdapterViewFriend(final Context context, final Friend friend) {
		super(context);
		setId(friend.id);
		setOrientation(LinearLayout.HORIZONTAL);

		ImageView imv_user;
		TextView txv_name;
		RelativeLayout.LayoutParams paramTxv1;
		LinearLayout.LayoutParams paramTxv;
		LinearLayout.LayoutParams sizeImvUser;

		// Instanciation des différents layout de l'adapter
		RelativeLayout mainLayout = new RelativeLayout(context);
		mainLayout.setId(666);
		LinearLayout subLayout = new LinearLayout(context);

		// Instanciation de l'image utilisateur
		imv_user = new ImageView(context);
		imv_user.setImageDrawable(getResources().getDrawable(
				R.drawable.img_defaultuser));
		imv_user.setId(777);

		//On charge l'image si existe
//		DownloadImage openDownloadImage1 = new DownloadImage(get, imv_user, friend.avatar_name);
//		openDownloadImage1.downloadImageView();
		
		// Instanciation du nom de l'utilisateur
		txv_name = new TextView(context);
		txv_name.setText(friend.nickname);
		txv_name.setPadding(15, 0, 0, 0);
		txv_name.setTextColor(getResources().getColor(R.color.grey_color));
		txv_name.setTextSize(20);

		// Instanciation des images messages et connecté
		ImageView isConnected = new ImageView(context);
		if (friend.onMobile == 2) {
			isConnected.setImageDrawable(getResources().getDrawable(
					R.drawable.oncomputer));
		} else if (friend.onMobile == 1) {
			isConnected.setImageDrawable(getResources().getDrawable(
					R.drawable.onapp));
		} else {
			isConnected.setImageDrawable(getResources().getDrawable(
					R.drawable.blank));
		}

		isConnected.setPadding(0, 10, 10, 0);
		ImageView imv_message = new ImageView(context);
		imv_message.setImageDrawable(getResources().getDrawable(
				R.drawable.imv_message));
		imv_message.setPadding(0, 0, 10, 0);

		imv_message.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, MessagingService.class);
				intent.putExtra("idFriend", friend.id);
				intent.putExtra("friendName", friend.nickname);
				context.getApplicationContext().startActivity(intent);
			}
		});

		subLayout.setOrientation(LinearLayout.VERTICAL);
		subLayout.setGravity(Gravity.CENTER);

		paramTxv1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		paramTxv1.addRule(RelativeLayout.RIGHT_OF, imv_user.getId());
		paramTxv1.addRule(RelativeLayout.CENTER_VERTICAL, mainLayout.getId());
		LinearLayout.LayoutParams wrap_0 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, 0);
		RelativeLayout.LayoutParams paramSubLayout = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		paramSubLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
				mainLayout.getId());
		paramSubLayout.addRule(RelativeLayout.CENTER_VERTICAL,
				mainLayout.getId());
		sizeImvUser = new LinearLayout.LayoutParams(120, 120);

		subLayout.setWeightSum(2);
		wrap_0.weight = 1;
		subLayout.addView(isConnected, wrap_0);
		subLayout.addView(imv_message, wrap_0);

		mainLayout.addView(imv_user, sizeImvUser);
		mainLayout.addView(txv_name, paramTxv1);
		mainLayout.addView(subLayout, paramSubLayout);
		addView(mainLayout);
	}

}