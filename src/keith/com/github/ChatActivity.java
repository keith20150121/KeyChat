package keith.com.github;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

class TalkViewCache extends ViewCache
{
	LinearLayout leftLayout;
	LinearLayout rightLayout;
	int pngId;
}

class TalkContentAdapter extends ArrayAdapter<TalkContent> {

	private int m_resourceId;
	private final ChatMember mTalker;
	private final ChatMember mMe;
	
	public TalkContentAdapter(
			Context context, 
			int textViewResourceId, 
			List<TalkContent> objects,
			final ChatMember talker,
			final ChatMember me)
	{
		super(context, textViewResourceId, objects);
		m_resourceId = textViewResourceId;
		mTalker = talker;
		mMe = me;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		TalkContent tc = getItem(position);
		View view;
		TalkViewCache cache;
		
		if (convertView == null)
		{
			view = LayoutInflater.from(getContext()).inflate(m_resourceId, null);
			cache = new TalkViewCache();
			
			cache.leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);
			cache.rightLayout = (LinearLayout)view.findViewById(R.id.right_layout);
			
			if (mMe.getAccountId().equalsIgnoreCase(tc.getSenderId())) 
			{
				cache.pngId = mMe.getPictureId();
				cache.rightLayout.setVisibility(View.VISIBLE);
				cache.leftLayout.setVisibility(View.GONE);
				cache.imageView = (ImageView)view.findViewById(R.id.chat_histroy_right_pic);
				cache.textView = (TextView)view.findViewById(R.id.chat_histroy_right_content);	

			}
			else
			{
				cache.pngId = mTalker.getPictureId();
				cache.rightLayout.setVisibility(View.GONE);
				cache.leftLayout.setVisibility(View.VISIBLE);
				cache.imageView = (ImageView)view.findViewById(R.id.chat_histroy_left_pic);
				cache.textView = (TextView)view.findViewById(R.id.chat_histroy_left_content);			
			}
			
			view.setTag(cache);
		}
		else
		{
			view = convertView;
			cache = (TalkViewCache)view.getTag();
		}
		
		cache.imageView.setImageResource(cache.pngId);
		cache.textView.setText(tc.getText());
		
		return view;
	}
	
}

public class ChatActivity extends Activity implements OnClickListener 
{	
	private ListView mChatHistroyListView;
	private TalkContentAdapter mAdapter;
	private List<TalkContent> mChatList = new ArrayList<TalkContent>();
	private KQChatDatabase mDbHelper;
	private EditText mInputEdit;
	private ChatMember mMe;
	private ChatMember mOther;
	
	private void prepareChatHistroy()
	{
		mDbHelper = new KQChatDatabase(this, "KQChatStore.db", null, 1);
		
		Intent i = getIntent();
		mOther = (ChatMember)i.getParcelableExtra("cm");
		mMe = (ChatMember)i.getParcelableExtra("me");
		mChatHistroyListView = (ListView)findViewById(R.id.msg_list_view);
		mAdapter = new TalkContentAdapter(
				this, 
				R.layout.chat_content_item,
				mChatList,
				mOther,
				mMe);
		mChatHistroyListView.setAdapter(mAdapter);
		readFromDatabase();
		// TEST
		TalkContent tc = new TalkContent("yoyoyoyoyoyo", mOther.getAccountId(), mMe.getAccountId());
		mChatList.add(tc);
		/*	tc = new TalkContent("yoyoyoyoyoyo", mOther.getAccountId());
		mChatList.add(tc);
		tc = new TalkContent("23", mOther.getAccountId());
		mChatList.add(tc);
		tc = new TalkContent("uuuuuuuuuuuuuuuu\r\nueirueiorueoirue\r\nerier", mOther.getAccountId());
		mChatList.add(tc);
		tc = new TalkContent("3434343", mOther.getAccountId());
		mChatList.add(tc);
		tc = new TalkContent("2#@#@", mOther.getAccountId());
		mChatList.add(tc);
		tc = new TalkContent("nn", mMe.getAccountId());
		mChatList.add(tc);
		tc = new TalkContent("ww", mMe.getAccountId());
		mChatList.add(tc);
		tc = new TalkContent("aa", mOther.getAccountId());
		mChatList.add(tc);
		*/
		
	}
	
	private void readFromDatabase()
	{
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from kqc_chat "
				+ "where (senderId = ? and receiverId = ?) or (senderId = ? and receiverId = ?) "
				+ "order by time",
				new String[]{mMe.getAccountId(), mOther.getAccountId(), mOther.getAccountId(), mMe.getAccountId()});
		
		if (cursor.moveToFirst())
		{
			do 
			{
				String senderId = cursor.getString(0);
				String receiverId = cursor.getString(1);
				String content = cursor.getString(2);
				TalkContent tc = new TalkContent(content, senderId, receiverId);
				mChatList.add(tc);
			} while (cursor.moveToNext());
		}
		
	}
	
	private void saveToDatabase(TalkContent tc)
	{
		if (tc == null)
			return;
		
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KQChatDatabase.SENDER_ID, tc.getSenderId());
		values.put(KQChatDatabase.RECEIVER_ID, tc.getReceiverId());
		values.put(KQChatDatabase.CONTENT, tc.getText());
		values.put(KQChatDatabase.TIME, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		db.insert("kqc_chat", null, values);
	}
	
	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.send_button:
			{
				String text = mInputEdit.getText().toString();
				if (text.length() == 0)
					break;
				
				TalkContent tc = new TalkContent(text, mMe.getAccountId(), mOther.getAccountId());
				mChatList.add(tc);
				mAdapter.notifyDataSetChanged();
				saveToDatabase(tc);
				mInputEdit.setText("");
			}
			break;
		}
	}
	
	@Override
	public void onBackPressed()
	{
		Intent i = new Intent();
		TalkContent tc = mChatList.get(mChatList.size() - 1);
		i.putExtra("last_talk", tc.getText());
		setResult(RESULT_OK, i);
		super.onBackPressed();
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Button sendBtn = (Button)findViewById(R.id.send_button);
        mInputEdit = (EditText)findViewById(R.id.input_text);
        sendBtn.setOnClickListener(this);
        prepareChatHistroy();
    }

}
