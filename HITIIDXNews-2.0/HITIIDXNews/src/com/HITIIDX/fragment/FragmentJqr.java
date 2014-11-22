package com.HITIIDX.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.HITIIDX.HITnewssearch.R;
import com.HITIIDX.adapter.ChatMessageAdapter;
import com.HITIIDX.bean.ChatMessage;
import com.HITIIDX.bean.ChatMessage.Type;
import com.HITIIDX.netutil.Httputils;

public class FragmentJqr extends Fragment {
	View view;
	private ListView mMsgs;
	private ChatMessageAdapter mChatAdapter;
	private List<ChatMessage> mChatDatas;

	private EditText mInputMsg;
	private Button mSendMsg;

	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			// 等待接收，子线程完成数据的返回
			ChatMessage fromMessge = (ChatMessage) msg.obj;
			mChatDatas.add(fromMessge);
			mChatAdapter.notifyDataSetChanged();
			mMsgs.setSelection(mChatDatas.size()-1);
		};

	};
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	view= inflater.inflate(R.layout.jqr, container, false);
	initView();
	initDatas();
	// 初始化事件
	initListener();
	return view;
}
private void initView()
{   
	mMsgs = (ListView) view.findViewById(R.id.id_listview_msgs);
	mInputMsg = (EditText) view.findViewById(R.id.id_input_msg);
	mSendMsg = (Button) view.findViewById(R.id.id_send_msg);
}
private void initDatas()
{
	mChatDatas = new ArrayList<ChatMessage>();
	mChatDatas.add(new ChatMessage("你好，AV梦为您服务", Type.INCOMING, new Date()));
	mChatAdapter = new ChatMessageAdapter(getActivity(), mChatDatas);
	//this,换成getActivity(),Fragemnt 和Activity是宿主和依托者的关系
	mMsgs.setAdapter(mChatAdapter);
}
private void initListener()
{
	mSendMsg.setOnClickListener(new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			final String toMsg = mInputMsg.getText().toString();
			if (TextUtils.isEmpty(toMsg))
			{
				Toast.makeText(getActivity(), "发送消息不能为空！",
						Toast.LENGTH_SHORT).show();
				return;
			}
			
			ChatMessage toMessage = new ChatMessage();
			toMessage.setDate(new Date());
			toMessage.setMsg(toMsg);
			toMessage.setType(Type.OUTCOMING);
			mChatDatas.add(toMessage);
			mChatAdapter.notifyDataSetChanged();
			mMsgs.setSelection(mChatDatas.size()-1);
			
			mInputMsg.setText("");
			
			new Thread()
			{
				public void run()
				{
					ChatMessage fromMessage = Httputils.sendMessage(toMsg);
					Message m = Message.obtain();
					m.obj = fromMessage;
					mHandler.sendMessage(m);
				};
			}.start();

		}
	});
}

}
