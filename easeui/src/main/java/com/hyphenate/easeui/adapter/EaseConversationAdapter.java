package com.hyphenate.easeui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.bean.GetRedPacketMessageBean;
import com.hyphenate.easeui.bean.GunControlSettlementInfo;
import com.hyphenate.easeui.bean.NiuniuSettlementInfo;
import com.hyphenate.easeui.bean.RedpacketBean;
import com.hyphenate.easeui.bean.RedpacketRewardBean;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseConversationList.EaseConversationListHelper;
import com.hyphenate.easeui.widget.EaseImageView;
import com.hyphenate.util.DateUtils;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import me.jessyan.armscomponent.commonres.utils.CommonMethod;
import me.jessyan.armscomponent.commonres.utils.SpUtils;

/**
 * conversation list adapter
 *
 */
public class EaseConversationAdapter extends ArrayAdapter<EMConversation> {
    private static final String TAG = "ChatAllHistoryAdapter";
    private List<EMConversation> conversationList;
    private List<EMConversation> copyConversationList;
    private ConversationFilter conversationFilter;
    private boolean notiyfyByFilter;

    protected int primaryColor;
    protected int secondaryColor;
    protected int timeColor;
    protected int primarySize;
    protected int secondarySize;
    protected float timeSize;

    public EaseConversationAdapter(Context context, int resource,
                                   List<EMConversation> objects) {
        super(context, resource, objects);
        conversationList = objects;
        copyConversationList = new ArrayList<EMConversation>();
        copyConversationList.addAll(objects);
    }

    @Override
    public int getCount() {
        return conversationList.size();
    }

    @Override
    public EMConversation getItem(int arg0) {
        if (arg0 < conversationList.size()) {
            return conversationList.get(arg0);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ease_row_chat_history, parent, false);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.unreadLabel = (TextView) convertView.findViewById(R.id.unread_msg_number);
            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.msgState = convertView.findViewById(R.id.msg_state);
            holder.list_itease_layout = (RelativeLayout) convertView.findViewById(R.id.list_itease_layout);
            holder.motioned = (TextView) convertView.findViewById(R.id.mentioned);
            holder.btnDelect = (TextView) convertView.findViewById(R.id.btnDelete);
            holder.swipeMenuLayout = (SwipeMenuLayout) convertView.findViewById(R.id.swipeMenuLayout);
            holder.rlItem = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            holder.ivNotify = (ImageView) convertView.findViewById(R.id.iv_notify);
            convertView.setTag(holder);
        }
        holder.list_itease_layout.setBackgroundResource(R.drawable.ease_mm_listitem);

        // get conversation
        EMConversation conversation = getItem(position);
        // get username or group id
        String username = conversation.conversationId();

        if (conversation.getType() == EMConversationType.GroupChat) {
            String groupId = conversation.conversationId();
            if(EaseAtMessageHelper.get().hasAtMeMsg(groupId)){
                holder.motioned.setVisibility(View.VISIBLE);
            }else{
                holder.motioned.setVisibility(View.GONE);
            }
            // group message, show group avatar
            EMGroup group = EMClient.getInstance().groupManager().getGroup(username);
            holder.name.setText(group != null ? group.getGroupName() : username);
        } else if(conversation.getType() == EMConversationType.ChatRoom){
            EMChatRoom room = EMClient.getInstance().chatroomManager().getChatRoom(username);
            holder.name.setText(room != null && !TextUtils.isEmpty(room.getName()) ? room.getName() : username);
            holder.motioned.setVisibility(View.GONE);
        }else {
            String nickname = SpUtils.getValue ( parent.getContext (), username + "nickname", "" );
            if (TextUtils.isEmpty ( nickname )) {
                EaseUserUtils.setUserNick ( username, holder.name );
            }else{
                EaseUserUtils.setUserNick ( nickname, holder.name );
            }
            holder.motioned.setVisibility(View.GONE);
        }
        String url =  SpUtils.getValue ( parent.getContext (),username+"head","" );
        Glide.with ( getContext () )
                .load ( url )
                .apply ( new RequestOptions ().placeholder ( R.drawable.ease_default_avatar ) )
                .into ( holder.avatar );
        //是否设置免打扰
        boolean isNotify = CommonMethod.isNotifyFromHxid (username );
        if (isNotify){
            holder.ivNotify.setVisibility ( View.VISIBLE );
        }else{
            holder.ivNotify.setVisibility ( View.GONE );
        }
        EaseAvatarOptions avatarOptions = EaseUI.getInstance().getAvatarOptions();
        if(avatarOptions != null && holder.avatar instanceof EaseImageView) {
            EaseImageView avatarView = ((EaseImageView) holder.avatar);
            if (avatarOptions.getAvatarBorderWidth() != 0) {
                avatarView.setBorderWidth ( avatarOptions.getAvatarBorderWidth () );
            }
            if (avatarOptions.getAvatarBorderColor() != 0) {
                avatarView.setBorderColor ( avatarOptions.getAvatarBorderColor () );
            }
            if (avatarOptions.getAvatarRadius() != 0) {
                avatarView.setRadius ( avatarOptions.getAvatarRadius () );
            }
        }
        if (conversation.getUnreadMsgCount() > 0) {
            // show unread message count
            if (conversation.getUnreadMsgCount()>99){
                holder.unreadLabel.setText("...");
            }else {
                holder.unreadLabel.setText ( String.valueOf ( conversation.getUnreadMsgCount () ) );
            }
            holder.unreadLabel.setVisibility(View.VISIBLE);
        } else {
            holder.unreadLabel.setVisibility(View.INVISIBLE);
        }

        if (conversation.getAllMsgCount() != 0) {
            // show the content of latest message
            EMMessage lastMessage = conversation.getLastMessage();

            String content = null;
            if(cvsListHelper != null){
                content = cvsListHelper.onSetItemSecondaryText(lastMessage);
            }
            //消息内容
            String tips = extMessage ( lastMessage );
            if (TextUtils.isEmpty (tips)) {
                holder.message.setText ( EaseSmileUtils.getSmiledText ( getContext (), EaseCommonUtils.getMessageDigest ( lastMessage, (this.getContext ()) ) ),
                        BufferType.SPANNABLE );
                if (content != null) {
                    holder.message.setText ( content );
                }
            }else {
                holder.message.setText (tips);
            }
            holder.time.setText(DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));
            if (lastMessage.direct() == EMMessage.Direct.SEND && lastMessage.status() == EMMessage.Status.FAIL) {
                holder.msgState.setVisibility(View.VISIBLE);
            } else {
                holder.msgState.setVisibility(View.GONE);
            }
        }

        //set property
        holder.name.setTextColor(primaryColor);
        holder.message.setTextColor(secondaryColor);
        holder.time.setTextColor(timeColor);
        if(primarySize != 0)
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_PX, primarySize);
        if(secondarySize != 0)
            holder.message.setTextSize(TypedValue.COMPLEX_UNIT_PX, secondarySize);
        if(timeSize != 0) {
            holder.time.setTextSize ( TypedValue.COMPLEX_UNIT_PX, timeSize );
        }
        ViewHolder finalHolder = holder;
        holder.btnDelect.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
               new AlertDialog.Builder ( parent.getContext () )
                       .setTitle ( "温馨提示" )
                       .setMessage ( "是否删除该会话？" )
                       .setNegativeButton ( "取消",null )
                       .setPositiveButton ( "确定", new DialogInterface.OnClickListener () {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               //删除和某个user会话，如果需要保留聊天记录，传false

                               EMGroup group = EMClient.getInstance().groupManager().getGroup(conversation.conversationId());

                               String name = group !=null ? group.getGroupName() :conversation.conversationId();

                               EMClient.getInstance().chatManager().deleteConversation(name, true);

                               conversationList.remove(position);

//                holder.es.collapseSmooth();
                               finalHolder.swipeMenuLayout.quickClose();
                               notifyDataSetChanged();
                           }
                       } )
                       .create ().show ();
            }
        } );

        holder.rlItem.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick (conversation);
                }
            }
        } );
        return convertView;
    }

    private String extMessage(EMMessage lastMessage) {
        String sRedpacketInfo =lastMessage.getStringAttribute( "content","");
        int type =lastMessage.getIntAttribute ( "isforb",-1);
        StringBuilder tips = new StringBuilder (  );
        try {
            if (!TextUtils.isEmpty ( sRedpacketInfo ) && type !=-1){
                switch (type){
                    case 0:
                        //单雷房
                    case 1:
                        //禁抢房
                        RedpacketBean redpacketBean = new Gson ().fromJson(sRedpacketInfo, RedpacketBean.class);
                        tips.append ( redpacketBean.getNickname () );
                        tips.append ( ":红包" );
                        //红包金额
                        tips.append ( redpacketBean.getMoney () );
                        //雷数
                        tips.append ( "-"+redpacketBean.getBoomNumbers () );
                        break;
                    case 2:
                        //牛牛房
                    case 3:
                        //福利房
                        RedpacketBean redpacketBean1 = new Gson().fromJson(sRedpacketInfo, RedpacketBean.class);
                        tips.append ( redpacketBean1.getNickname () );
                        tips.append ( ":红包" );
                        //红包金额
                        tips.append ( redpacketBean1.getMoney () );
                        //雷数
                        tips.append ( "-"+redpacketBean1.getNumber () );
                        break;
                    case 5:
                        //禁抢房结算消息
                        GunControlSettlementInfo settlementInfo = new Gson().fromJson(sRedpacketInfo, GunControlSettlementInfo.class);
                        tips.append ( "恭喜" );
                        tips.append ( settlementInfo.getNickname ());
                        tips.append ( settlementInfo.getMoney()+"-"+ settlementInfo.getMineNumber());
                        tips.append ( "收获"+settlementInfo.getBombNumber()+"个雷," );
                        tips.append ( "奖励" );
                        tips.append (settlementInfo.getAwardMoney()+"元" );
                        break;
                    case 6:
                        //牛牛房结算消息
                        tips.append ( "牛牛结算" );
                        break;
                    case 7:
                        //获取红包状态
                        GetRedPacketMessageBean getRedPacketMessageBean = new Gson().fromJson(sRedpacketInfo, GetRedPacketMessageBean.class);
                        if (CommonMethod.getHxidForLocal().equals ( getRedPacketMessageBean.getHxid () )){
                            tips.append ( getRedPacketMessageBean.getNickname () );
                            tips.append ( "领取了红包" );
                        }else{
                            tips.append ( "" );
                        }
                        break;
                    case 8:
                        //客服帮助信息
                        tips.append ( sRedpacketInfo );
                        break;
                    case 9:
                        //奖励提醒消息
                        RedpacketRewardBean rewardBean = new Gson ().fromJson(sRedpacketInfo, RedpacketRewardBean.class);
                        tips.append ( "恭喜" );
                        tips.append ( rewardBean.getNickname () );
                        tips.append ( rewardBean.getGold ()+"-"+rewardBean.getBoom () );
                        tips.append ( "中奖，奖励" );
                        tips.append ( "("+rewardBean.getGetnums ()+")"+"+"+rewardBean.getRewardgold ()+"元" );
                        break;
                    case 11:
                        //加入房间提醒消息
                        tips.append ( sRedpacketInfo );
                        break;
                    default:
                        tips.append ( "" );
                        break;
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace ();
        }
        return tips.toString ();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(!notiyfyByFilter){
            copyConversationList.clear();
            copyConversationList.addAll(conversationList);
            notiyfyByFilter = false;
        }
    }

    @Override
    public Filter getFilter() {
        if (conversationFilter == null) {
            conversationFilter = new ConversationFilter(conversationList);
        }
        return conversationFilter;
    }


    public void setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
    }

    public void setSecondaryColor(int secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public void setTimeColor(int timeColor) {
        this.timeColor = timeColor;
    }

    public void setPrimarySize(int primarySize) {
        this.primarySize = primarySize;
    }

    public void setSecondarySize(int secondarySize) {
        this.secondarySize = secondarySize;
    }

    public void setTimeSize(float timeSize) {
        this.timeSize = timeSize;
    }


    private class ConversationFilter extends Filter {
        List<EMConversation> mOriginalValues = null;

        public ConversationFilter(List<EMConversation> mList) {
            mOriginalValues = mList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                mOriginalValues = new ArrayList<EMConversation>();
            }
            if (prefix == null || prefix.length() == 0) {
                results.values = copyConversationList;
                results.count = copyConversationList.size();
            } else {
                if (copyConversationList.size() > mOriginalValues.size()) {
                    mOriginalValues = copyConversationList;
                }
                String prefixString = prefix.toString();
                final int count = mOriginalValues.size();
                final ArrayList<EMConversation> newValues = new ArrayList<EMConversation>();

                for (int i = 0; i < count; i++) {
                    final EMConversation value = mOriginalValues.get(i);
                    String username = value.conversationId();

                    EMGroup group = EMClient.getInstance().groupManager().getGroup(username);
                    if(group != null){
                        username = group.getGroupName();
                    }else{
                        EaseUser user = EaseUserUtils.getUserInfo(username);
                        // TODO: not support Nick anymore
//                        if(user != null && user.getNick() != null)
//                            username = user.getNick();
                    }

                    // First match against the whole ,non-splitted value
                    if (username.startsWith(prefixString)) {
                        newValues.add(value);
                    } else{
                        final String[] words = username.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (String word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            conversationList.clear();
            if (results.values != null) {
                conversationList.addAll((List<EMConversation>) results.values);
            }
            if (results.count > 0) {
                notiyfyByFilter = true;
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    private EaseConversationListHelper cvsListHelper;

    public void setCvsListHelper(EaseConversationListHelper cvsListHelper){
        this.cvsListHelper = cvsListHelper;
    }

    private static class ViewHolder {
        /** who you chat with */
        TextView name;
        /** unread message count */
        TextView unreadLabel;
        /** content of last message */
        TextView message;
        /** time of last message */
        TextView time;
        /** avatar */
        ImageView avatar;
        /** status of last message */
        View msgState;
        /** layout */
        RelativeLayout list_itease_layout;
        TextView motioned;
        TextView btnDelect;
        SwipeMenuLayout swipeMenuLayout;
        RelativeLayout rlItem;
        ImageView ivNotify;
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(EMConversation conversation);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

