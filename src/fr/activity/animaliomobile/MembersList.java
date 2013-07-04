package fr.activity.animaliomobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import fr.animaliomobile.R;

public class MembersList extends FragmentActivity{
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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.members_list, menu);
		return true;
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

				Intent intent = new Intent(MembersList.context, MemberProfiles.class);
				startActivity(intent);

			}
		};
	}

}
