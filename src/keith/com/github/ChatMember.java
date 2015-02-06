package keith.com.github;

import android.os.Parcel;
import android.os.Parcelable;
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

public class ChatMember implements Parcelable
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
	@Override
	public int describeContents() 
	{
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) 
	{
        out.writeInt(mPicId);
        out.writeString(mAccountId);
        out.writeString(mNickname);
        out.writeString(mLastTalk);
    }

    public static final Parcelable.Creator<ChatMember> CREATOR
            = new Parcelable.Creator<ChatMember>() {
    	@Override
        public ChatMember createFromParcel(Parcel in) 
        {
            return new ChatMember(in);
        }

    	@Override
        public ChatMember[] newArray(int size) {
            return new ChatMember[size];
        }
    };
    
    private ChatMember(Parcel in)
    {
    	mPicId = in.readInt();
        mAccountId = in.readString();
        mNickname = in.readString();
        mLastTalk = in.readString();
    }
	
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
