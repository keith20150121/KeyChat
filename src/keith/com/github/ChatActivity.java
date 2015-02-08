package keith.com.github;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
			
			if (mMe.getAccountId() != tc.getAccountId()) 
			{
				cache.pngId = mTalker.getPictureId();
				cache.rightLayout.setVisibility(View.GONE);
				cache.leftLayout.setVisibility(View.VISIBLE);
				cache.imageView = (ImageView)view.findViewById(R.id.chat_histroy_left_pic);
				cache.textView = (TextView)view.findViewById(R.id.chat_histroy_left_content);
			}
			else
			{
				cache.pngId = mMe.getPictureId();
				cache.rightLayout.setVisibility(View.VISIBLE);
				cache.leftLayout.setVisibility(View.GONE);
				cache.imageView = (ImageView)view.findViewById(R.id.chat_histroy_right_pic);
				cache.textView = (TextView)view.findViewById(R.id.chat_histroy_right_content);				
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

public class ChatActivity extends Activity 
{
	private ListView mChatHistroyListView;
	private List<TalkContent> mChatMemList = new ArrayList<TalkContent>();
	
	private void prepareChatHistroy()
	{
		// TEST
		ChatMember me = new ChatMember(R.drawable.local, "1", "Keith");
		
		Intent i = getIntent();
		ChatMember cm = (ChatMember)i.getParcelableExtra("cm");
		mChatHistroyListView = (ListView)findViewById(R.id.msg_list_view);
		TalkContentAdapter adapter = new TalkContentAdapter(
				this, 
				R.layout.chat_content_item,
				mChatMemList,
				cm,
				me);
		mChatHistroyListView.setAdapter(adapter);
		
		// TEST
		TalkContent tc = new TalkContent("yoyoyoyoyoyo", cm.getAccountId());
		mChatMemList.add(tc);
		tc = new TalkContent("yoyoyoyoyoyo", cm.getAccountId());
		mChatMemList.add(tc);
		tc = new TalkContent("23", cm.getAccountId());
		mChatMemList.add(tc);
		tc = new TalkContent("uuuuuuuuuuuuuuuu\r\nueirueiorueoirue\r\nerier", cm.getAccountId());
		mChatMemList.add(tc);
		tc = new TalkContent("3434343", me.getAccountId());
		mChatMemList.add(tc);
		tc = new TalkContent("2#@#@", cm.getAccountId());
		mChatMemList.add(tc);
		tc = new TalkContent("nn", me.getAccountId());
		mChatMemList.add(tc);
		tc = new TalkContent("ww", me.getAccountId());
		mChatMemList.add(tc);
		tc = new TalkContent("aa", cm.getAccountId());
		mChatMemList.add(tc);
		tc = new TalkContent("eeeeeeeeeeeeeee22222zz", cm.getAccountId());
		
	}
	
	@Override
	public void onBackPressed()
	{
		Intent i = new Intent();
		i.putExtra("last_talk", "eeeeeeeeeeeeeee22222zz");
		setResult(RESULT_OK, i);
		super.onBackPressed();
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        prepareChatHistroy();
    }
}
