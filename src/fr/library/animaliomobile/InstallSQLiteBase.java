package fr.library.animaliomobile;

import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Class permettant l'enregistrement des bdd interne si existe pas
 * @author Alexandre Froger
 * @version 1.0
 *
 */
public class InstallSQLiteBase extends SQLiteOpenHelper {
	private static final String[] fileSqlName = { 
		"cities.sql", 
		"country.sql", 
		"provinces.sql" 
	};
	
	private Context context;

	// Constructor
	public InstallSQLiteBase(Context _context, String name,
			CursorFactory factory, int version) {
		super(_context, name, factory, version);
		context = _context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			for(int i = 0; i<fileSqlName.length; i++){
				InputStream is = context.getResources().getAssets().open(fileSqlName[i]);
	
				String[] statements = FileHelper.parseSqlFile(is);
	
				for (String statement : statements) {
					db.execSQL(statement);
				}
			}
		} catch (Exception ex) {
			Log.e("log_CreateSqliteBase", "Error creating database " + ex.toString());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
