package fr.activity.animaliomobile;

import fr.animaliomobile.R;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ViewFlipper;

public class Details extends Activity {
	private ListView lsv_comments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);

		lsv_comments = (ListView)findViewById(R.id.lsv_comments);

		String[] values_comments_list = new String[] { "Commentaire 1", "Commentaire 2", "Commentaire 3", "Commentaire 4", "Commentaire 5", "Commentaire 6", "Commentaire 7", "Commentaire 8"  };
		ArrayAdapter<String> adapter_comments_list = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values_comments_list);
		lsv_comments.setAdapter(adapter_comments_list);

		if(adapter_comments_list.getCount() > 5){
			View item = adapter_comments_list.getView(0, null, lsv_comments);
			item.measure(0, 0);         
			System.out.println(item.getMeasuredHeight());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) (5.5 * item.getMeasuredHeight()));
			lsv_comments.setLayoutParams(params);
		}

	}
}