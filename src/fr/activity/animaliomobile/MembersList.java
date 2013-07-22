package fr.activity.animaliomobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import fr.animaliomobile.R;
import fr.library.animaliomobile.TypefaceSpan;

public class MembersList extends FragmentActivity{

	private Button btn_members;
	private Button btn_gallery;
	private Button btn_profil;
	private Button btn_events;
	private Button btn_live;
	private Button btn_photo;

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

		context = getApplicationContext();
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		btn_members = (Button)findViewById(R.id.btn_members);
		btn_gallery = (Button)findViewById(R.id.btn_gallery);
		btn_profil = (Button)findViewById(R.id.btn_profil);
		btn_events = (Button)findViewById(R.id.btn_events);
		btn_live = (Button)findViewById(R.id.btn_live);
		btn_photo = (Button)findViewById(R.id.btn_photo);

		btn_members.setBackgroundResource(R.drawable.ic_members_pressed);
		
		btn_gallery.setOnClickListener(eventClick);
		btn_profil.setOnClickListener(eventClick);
		btn_events.setOnClickListener(eventClick);
		btn_live.setOnClickListener(eventClick);
		btn_photo.setOnClickListener(eventClick);
	}

	//	Create Listener event
	OnClickListener eventClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

					
			if(v==btn_gallery){
				//Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(), Gallery.class);
				startActivity(intent);
				finish();
			}
			
			else if(v==btn_profil){
				//Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(), Profiles.class);
				intent.putExtra("typeProfil", 0);
				startActivity(intent);
				finish();
			}
			
			else if(v==btn_events){
				//Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(), Events.class);
				startActivity(intent);
				finish();
			}
			
			else if(v==btn_live){
				//Display Gallery Activity
				Intent intent = new Intent(getApplicationContext(), AnimalioLive.class);
				startActivity(intent);
				finish();
			}
			
			else if(v==btn_photo){
				//Display the photo phone application
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
			Intent i = new Intent (getApplicationContext(), MembersList.class);
			startActivity(i);
			finish();
			return true;
		case R.id.menu_settings:
			// Comportement du bouton "Paramètres"
			return true;
		case R.id.menu_delete:
			// Comportement du bouton "Delete" A supprimer quand le popup parametre sera creer car dedans

			// On créé l'Intent qui va nous permettre d'afficher l'autre Activity
			Intent intent = new Intent(getApplicationContext(), Authentication.class);
			// On supprime l'activity de login sinon
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);  

			//Puis on reset les informations utilisateurs
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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
		private ListView listeMembre;

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_members_list_dummy, container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));

			// Create the listMember
			// --- Recuperation de la listview creee dans liste_avec_images_differentes.xml
			listeMembre = (ListView)rootView.findViewById(R.id.list_members);

			// --- Creation de la ArrayList qui permettra de remplir la listView
			List<HashMap<String, Object>> listeItems = new ArrayList<HashMap<String, Object>>();

			// --- Declaration du HashMap qui contiendra les informations pour un item
			HashMap<String, Object> hm;

			// --- Creation du HashMap pour inserer les informations du premier item de la listView
			hm = new HashMap<String, Object>();

			// --- Insertion d'elements
			hm.put("image", R.drawable.fotolia_test);
			hm.put("title", "Test 1");
			hm.put("description", "Testgsdrg sdrgs drgsd gsdr ");
			// --- Ajout du hashMap a l'ArrayList
			listeItems.add(hm);

			hm.put("image", R.drawable.ic_launcher);
			hm.put("title", "Test 2");
			hm.put("description", "sdr gsdr gsdr gsdrg sdrg sdrg sd");
			// --- Ajout du hashMap a l'ArrayList    
			listeItems.add(hm);

			// --- Creation d'un SimpleAdapter
			SimpleAdapter sa = new SimpleAdapter (inflater.getContext(),
					listeItems, 
					R.layout.members_list_ligne,
					new String[] {"image", "title", "description"},
					new int[] {R.id.member_ligne_photo, R.id.member_ligne_titre, R.id.member_ligne_description}
					);

			//	        // --- Affectation de l'adapter a la listView
			listeMembre.setAdapter(sa); 
			//	        
			//	        // --- Gestion de l'evenement pour la selection
			listeMembre.setOnItemClickListener(eventClick);

			return rootView;
		}
		//		Create Listener event
		OnItemClickListener eventClick = new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {

				Intent intent = new Intent(MembersList.context, Profiles.class);
				int typeProfil;
				if(arg2==0){
					typeProfil = 2;
					intent.putExtra("typeProfil", typeProfil);
				}else{
					typeProfil = 1;
					intent.putExtra("typeProfil", typeProfil);
				}
				startActivity(intent);

			}
		};
	}

}
