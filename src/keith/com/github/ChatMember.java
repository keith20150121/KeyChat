package keith.com.github;

import java.util.ArrayList;
import java.util.List;

import android.widget.ImageView;
import android.widget.TextView;

//struct
class ViewCache
{
	ImageView imageView;
	TextView  textView;
}

class TalkContent
{
	private final ChatMember mCm;
	private final String mText;
	
	public TalkContent(
			final ChatMember cm,
			final String text)
	{
		mCm = cm;
		mText = text;
	}
	
	public String getText()
	{
		return mText;
	}
	
	public String getAccountId()
	{
		return mCm.getAccountId();
	}
	
	public String getNickname()
	{
		return mCm.getNickname();
	}
	
	public int getPictureId()
	{
		return mCm.getPictureId();
	}
}

public class ChatMember 
{
	final private int mPicId;
	private String mAccountId;
	final private String mNickname;
	
//	private List<TalkContent> mTalkRecords;
	private String mLastTalk;
/*	
	private void AddTalkRecord(TalkContent tc)
	{
		if (mTalkRecords == null)
			mTalkRecords = new ArrayList<TalkContent>();
		
		mTalkRecords.add(tc);
	}
*/
	public ChatMember(int picId, String accountId, String nickname)
	{
		mPicId = picId;
		mAccountId = accountId;
		mNickname = nickname;
	}
	
	public int getPictureId()
	{
		return mPicId;
	}
	
	public String getAccountId()
	{
		return mAccountId;
	}
	
	public String getNickname()
	{
		return mNickname;
	}
	
	public void chat(String content)
	{
		mLastTalk = content;
		// NETWORK
		
	//	if (mParent == null)
	//		return;
		
	//	if (mParent.getItem(0) == this)
	//		return;

		// Add Record
	//	TalkContent tc = new TalkContent(userId, content);
	//	AddTalkRecord(tc);
		
		// Move to first
	//	mParent.remove(this);
	//	mParent.insert(this, 0);
	}
	
	public String getLastTalk()
	{
		return mLastTalk;
	}
}
