package as1124.com.helloworld.ch3.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import as1124.com.helloworld.R;

public class WechatListAdapter extends RecyclerView.Adapter<WechatListAdapter.MsgItemHolder> {

    List<WechatMessage> listData;

    public WechatListAdapter(List<WechatMessage> data) {
        listData = data;
    }

    @Override
    public MsgItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_wechat, parent, false);
        return new MsgItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MsgItemHolder holder, int position) {
        WechatMessage msgData = listData.get(position);
        int msgType = msgData.getType();
        String msgContent = msgData.getContent();
        if (WechatMessage.TYPE_SENT == msgType) {
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.rightText.setText(msgContent);
        } else {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.leftText.setText(msgContent);
            holder.rightLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MsgItemHolder extends RecyclerView.ViewHolder {

        LinearLayout leftLayout;
        TextView leftText;
        LinearLayout rightLayout;
        TextView rightText;

        public MsgItemHolder(View itemView) {
            super(itemView);
            leftLayout = itemView.findViewById(R.id.wechat_item_left);
            leftText = leftLayout.findViewById(R.id.wechat_text_left);
            rightLayout = itemView.findViewById(R.id.wechat_item_right);
            rightText = rightLayout.findViewById(R.id.wechat_text_right);
        }
    }

}
