package keith.com.github;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

class ChatMemberAdapter extends ArrayAdapter<ChatMember> {

	private int m_resourceId;
	
	public ChatMemberAdapter(Context context,	int textViewResourceId,	List<ChatMember> objects)
	{
		super(context, textViewResourceId, objects);
		m_resourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ChatMember cm = getItem(position);
		View view;
		ViewCache cache;
		if (convertView == null)
		{
			view = LayoutInflater.from(getContext()).inflate(m_resourceId, null);
			cache = new ViewCache();
			cache.imageView = (ImageView)view.findViewById(R.id.pic);
			cache.textView = (TextView)view.findViewById(R.id.nick_name);
			view.setTag(cache);
		}
		else
		{
			view = convertView;
			cache = (ViewCache)view.getTag();
		}
		
		cache.imageView.setImageResource(cm.getPictureId());
		cache.textView.setText(cm.getNickname());
		
		return view;
	}
	
}

public class ChatMemberActivity extends Activity 
{
	private ListView mChatMemListView;
	private List<ChatMember> mChatMemList = new ArrayList<ChatMember>();
	
	private void prepareChatMember()
	{
		mChatMemListView = (ListView)findViewById(R.id.member_view);
		ChatMemberAdapter adapter = new ChatMemberAdapter(
				this, 
				R.layout.chat_member_item,
				mChatMemList);
		mChatMemListView.setAdapter(adapter);
		
		//TEST
		ChatMember cm = new ChatMember(R.drawable.ic_launcher, "1", "Keith");
		mChatMemList.add(cm);
		cm = new ChatMember(R.drawable.ic_launcher, "2", "Tom");
		mChatMemList.add(cm);
		cm = new ChatMember(R.drawable.ic_launcher, "36", "Humityan");
		mChatMemList.add(cm);
		cm = new ChatMember(R.drawable.ic_launcher, "45", "w2~~232");
		mChatMemList.add(cm);
		cm = new ChatMember(R.drawable.ic_launcher, "44", "394-329304[[][][]");
		mChatMemList.add(cm);
		cm = new ChatMember(R.drawable.ic_launcher, "43", "????????");
		mChatMemList.add(cm);
		cm = new ChatMember(R.drawable.ic_launcher, "42", "2iruewoiureow");
		mChatMemList.add(cm);
		cm = new ChatMember(R.drawable.ic_launcher, "41", "我我我问问我我我我");
		mChatMemList.add(cm);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_member);
        prepareChatMember();
    }
    
    
}
