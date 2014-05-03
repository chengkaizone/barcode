package barcode.cheng.client.android.history;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Sean Owen
 */
final class DBHelper extends SQLiteOpenHelper {

	private static final int DB_VERSION = 3;
	private static final String DB_NAME = "barcode_scanner_history.db";
	static final String TABLE_NAME = "history";
	static final String ID_COL = "id";
	static final String TEXT_COL = "text";
	static final String FORMAT_COL = "format";
	static final String DISPLAY_COL = "display";
	static final String TIMESTAMP_COL = "timestamp";

	DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ID_COL
				+ " INTEGER PRIMARY KEY, " + TEXT_COL + " TEXT, " + FORMAT_COL
				+ " TEXT, " + DISPLAY_COL + " TEXT, " + TIMESTAMP_COL
				+ " INTEGER" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion,
			int newVersion) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(sqLiteDatabase);
	}

}
