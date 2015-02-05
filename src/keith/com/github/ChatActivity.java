package keith.com.github;

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
		cache.textView.setText(tc.getNickname());
		
		return view;
	}
	
}

public class ChatActivity extends Activity 
{
	private ListView mChatHistroyListView;
	
	private void prepareChatHistroy()
	{
		mChatHistroyListView = (ListView)findViewById(R.id.msg_list_view);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        prepareChatHistroy();
    }
}
