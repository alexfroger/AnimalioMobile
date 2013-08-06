package fr.library.animaliomobile;

import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class permettant l'enregistrement des bdd interne si existe pas
 * 
 * @author Alexandre Froger
 * @version 1.0
 * 
 */
public class InstallSQLiteBase extends SQLiteOpenHelper {
	private static final String DATABASE_TABLE = "AnimalioMobile";
	private Context context;
	//Pour la création des bdd interne
	private static final String[] fileSqlName = {
		"country.sql",
		"provinces.sql",
		"race.sql"
	};

	// Constructor
	public InstallSQLiteBase(Context _context) {
		super(_context, DATABASE_TABLE, null, 1);
		context = _context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			for(int i=0; i < fileSqlName.length; i++){				
				InputStream is = context.getAssets().open(fileSqlName[i]);
				String[] statements = FileHelper.parseSqlFile(is);
	
				for (String statement : statements) {
					db.execSQL(statement);
				}
				
				Log.i("log_CreateSqliteBase",
						"DB creer :  " + fileSqlName[i]);
			}
		} catch (Exception ex) {
			Log.e("log_CreateSqliteBase",
					"Error creating database " + ex.toString());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}
}
