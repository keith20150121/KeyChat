package keith.com.github;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class KQChatDatabase extends SQLiteOpenHelper 
{
	public static final String SENDER_ID = "senderId";
	public static final String RECEIVER_ID = "receiverId";
	public static final String CONTENT = "content";
	public static final String TIME = "time";
	
	private static final String CREATE_BOOK = "create table kqc_chat ("
			+ "senderId text," // no primary key
			+ "receiverId text,"
			+ "content text,"
			+ "time time)";

	public KQChatDatabase(Context context, String name, CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BOOK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
