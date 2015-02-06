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
}

class TalkContentAdapter extends ArrayAdapter<TalkContent> {

	private int m_resourceId;
	private final String mAccountId;
	
	public TalkContentAdapter(
			Context context, 
			int textViewResourceId, 
			List<TalkContent> objects,
			final String accountId)
	{
		super(context, textViewResourceId, objects);
		m_resourceId = textViewResourceId;
		mAccountId = accountId;
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
			
			if (tc.getAccountId() != mAccountId) 
			{
				cache.rightLayout.setVisibility(View.GONE);
				cache.leftLayout.setVisibility(View.VISIBLE);
				cache.imageView = (ImageView)view.findViewById(R.id.chat_histroy_left_pic);
				cache.textView = (TextView)view.findViewById(R.id.chat_histroy_left_content);
			}
			else
			{
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
		
		cache.imageView.setImageResource(tc.getPictureId());
		cache.textView.setText(tc.getText());
		
		return view;
	}
	
}

public class ChatActivity extends Activity 
{
	private String   mAccountId;
	private ListView mChatHistroyListView;
	private List<TalkContent> mChatMemList = new ArrayList<TalkContent>();
	
	private void prepareChatHistroy()
	{
		// TEST
		mAccountId = "1";
		
		Intent i = getIntent();
		ChatMember cm = (ChatMember)i.getParcelableExtra("cm");
		mChatHistroyListView = (ListView)findViewById(R.id.msg_list_view);
		TalkContentAdapter adapter = new TalkContentAdapter(
				this, 
				R.layout.chat_content_item,
				mChatMemList,
				mAccountId);
		mChatHistroyListView.setAdapter(adapter);
		
		// TEST
		TalkContent tc = new TalkContent(cm, "yoyoyoyoyoyo");
		mChatMemList.add(tc);
		tc = new TalkContent(cm, "yoyoyoyoyoyo");
		mChatMemList.add(tc);
		tc = new TalkContent(cm, "23");
		mChatMemList.add(tc);
		tc = new TalkContent(cm, "uuuuuuuuuuuuuuuu\r\nueirueiorueoirue\r\nerier");
		mChatMemList.add(tc);
		tc = new TalkContent(cm, "‰ä‰ä‰ä‰ä‰ä");
		mChatMemList.add(tc);
		tc = new TalkContent(cm, "2#@#@");
		mChatMemList.add(tc);
		tc = new TalkContent(cm, "nn");
		mChatMemList.add(tc);
		tc = new TalkContent(cm, "ww");
		mChatMemList.add(tc);
		tc = new TalkContent(cm, "aa");
		mChatMemList.add(tc);
		tc = new TalkContent(cm, "eeeeeeeeeeeeeee22222zz");
		
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
