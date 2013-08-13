package fr.library.animaliomobile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class FormattedDateFr {
	private static Date dateToFormat;
	private static String dateFormmated;
	
	public FormattedDateFr() {
		super();
	}

	/**
	 * Retour la date formatter en français
	 * @param String DateNoFormatted
	 * @return String dateFormatted
	 */
	public static String FormattedDateToFr(String date){
		try {
			dateToFormat = new SimpleDateFormat(
					"yyyy-MM-dd")
					.parse(date);
		} catch (ParseException e1) {
			Log.e("log_Date_Formatted",
					"ParseException : " + e1.toString());
		}
		dateFormmated = new SimpleDateFormat(
				"dd-MM-yyyy")
				.format(dateToFormat);
		
		return dateFormmated;
	}
}
