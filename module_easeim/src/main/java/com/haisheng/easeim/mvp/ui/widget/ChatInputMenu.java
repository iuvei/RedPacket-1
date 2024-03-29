package com.haisheng.easeim.mvp.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.haisheng.easeim.R;
import com.haisheng.easeim.mvp.model.entity.ChatExtendItemEntity;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.model.EaseDefaultEmojiconDatas;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.hyphenate.easeui.widget.EaseChatPrimaryMenuBase;
import com.hyphenate.easeui.widget.EaseChatPrimaryMenuBase.EaseChatPrimaryMenuListener;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenuBase;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenuBase.EaseEmojiconMenuListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * input menu
 * 
 * including below component:
 *    EaseChatPrimaryMenu: main menu bar, text input, send button
 *    EaseChatExtendMenu: grid menu with image, file, location, etc
 *    EaseEmojiconMenu: emoji icons
 */
public class ChatInputMenu extends LinearLayout {
    FrameLayout primaryMenuContainer, emojiconMenuContainer;
    protected ChatPrimaryMenu chatPrimaryMenu;
    protected EaseEmojiconMenuBase emojiconMenu;
    protected ChatExtendMenu chatExtendMenu;
    protected FrameLayout chatExtendMenuContainer;
    protected LayoutInflater layoutInflater;

    private Handler handler = new Handler();
    private ChatInputMenuListener listener;
    private Context context;
    private boolean inited;

    public ChatInputMenu(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public ChatInputMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ChatInputMenu(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.view_chat_input_menu, this);
        primaryMenuContainer = findViewById(R.id.primary_menu_container);
        emojiconMenuContainer = findViewById(R.id.emojicon_menu_container);
        chatExtendMenuContainer = findViewById(R.id.extend_menu_container);

         // extend menu
         chatExtendMenu = findViewById(R.id.extend_menu);
    }

    /**
     * init view 
     * This method should be called after registerExtendMenuItem(), setCustomEmojiconMenu() and setCustomPrimaryMenu().
     * @param emojiconGroupList --will use default if null
     */
    @SuppressLint("InflateParams")
    public void init(List<EaseEmojiconGroupEntity> emojiconGroupList) {
        if(inited){
            return;
        }
        // primary menu, use default if no customized one
        if(chatPrimaryMenu == null){
            chatPrimaryMenu = (ChatPrimaryMenu) layoutInflater.inflate(R.layout.layout_chat_primary_menu, null);
        }
        primaryMenuContainer.addView(chatPrimaryMenu);

        // emojicon menu, use default if no customized one
        if(emojiconMenu == null){
            emojiconMenu = (EaseEmojiconMenu) layoutInflater.inflate(R.layout.ease_layout_emojicon_menu, null);
            if(emojiconGroupList == null){
                emojiconGroupList = new ArrayList<EaseEmojiconGroupEntity>();
                emojiconGroupList.add(new EaseEmojiconGroupEntity(com.hyphenate.easeui.R.drawable.ee_1,  Arrays.asList(EaseDefaultEmojiconDatas.getData())));
            }
            ((EaseEmojiconMenu)emojiconMenu).init(emojiconGroupList);
        }
        emojiconMenuContainer.addView(emojiconMenu);

        processChatMenu();
//        chatExtendMenu.init();
        inited = true;
    }
    
    public void init(){
        init(null);
    }
    
    /**
     * set custom emojicon menu
     * @param customEmojiconMenu
     */
    public void setCustomEmojiconMenu(EaseEmojiconMenuBase customEmojiconMenu){
        this.emojiconMenu = customEmojiconMenu;
    }
    
    /**
     * set custom primary menu
     * @param customPrimaryMenu
     */
    public void setCustomPrimaryMenu(EaseChatPrimaryMenuBase customPrimaryMenu){
        this.chatPrimaryMenu = (ChatPrimaryMenu) customPrimaryMenu;
    }
    
    public EaseChatPrimaryMenuBase getPrimaryMenu(){
        return chatPrimaryMenu;
    }
    
    public EaseEmojiconMenuBase getEmojiconMenu(){
        return emojiconMenu;
    }

    public void initExtendMenuItem(List<ChatExtendItemEntity> entities, ChatExtendMenu.OnItemClickListener onItemClickListener){
        chatExtendMenu.initData(entities,onItemClickListener);
    }

    protected void processChatMenu() {
        // send message button
        chatPrimaryMenu.setChatPrimaryMenuListener(new EaseChatPrimaryMenuListener() {

            @Override
            public void onTyping(CharSequence s, int start, int before, int count) {
                if (listener != null) {
                    listener.onTyping(s, start, before, count);
                }
            }

            @Override
            public void onSendBtnClicked(String content) {
                if (listener != null)
                    listener.onSendMessage(content);
            }

            @Override
            public void onToggleVoiceBtnClicked() {
                hideExtendMenuContainer();
            }

            @Override
            public void onToggleExtendClicked() {
                toggleMore();
            }

            @Override
            public void onToggleEmojiconClicked() {
                toggleEmojicon();
            }

            @Override
            public void onEditTextClicked() {
                hideExtendMenuContainer();
            }


            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                if(listener != null){
                    return listener.onPressToSpeakBtnTouch(v, event);
                }
                return false;
            }
        });

        // emojicon menu
        emojiconMenu.setEmojiconMenuListener(new EaseEmojiconMenuListener() {

            @Override
            public void onExpressionClicked(EaseEmojicon emojicon) {
                if(emojicon.getType() != EaseEmojicon.Type.BIG_EXPRESSION){
                    if(emojicon.getEmojiText() != null){
                        chatPrimaryMenu.onEmojiconInputEvent(EaseSmileUtils.getSmiledText(context,emojicon.getEmojiText()));
                    }
                }else{
                    if(listener != null){
                        listener.onBigExpressionClicked(emojicon);
                    }
                }
            }

            @Override
            public void onDeleteImageClicked() {
                chatPrimaryMenu.onEmojiconDeleteEvent();
            }
        });

    }
    
   
    /**
     * insert text
     * @param text
     */
    public void insertText(String text){
        getPrimaryMenu().onTextInsert(text);
    }

    /**
     * show or hide extend menu
     * 
     */
    protected void toggleMore() {
        if (chatExtendMenuContainer.getVisibility() == View.GONE) {
            hideKeyboard();
            handler.postDelayed(new Runnable() {
                public void run() {
                    chatExtendMenuContainer.setVisibility(View.VISIBLE);
                    chatExtendMenu.setVisibility(View.VISIBLE);
                    emojiconMenu.setVisibility(View.GONE);
                }
            }, 50);
        } else {
            if (emojiconMenu.getVisibility() == View.VISIBLE) {
                emojiconMenu.setVisibility(View.GONE);
                chatExtendMenu.setVisibility(View.VISIBLE);
            } else {
                chatExtendMenuContainer.setVisibility(View.GONE);
            }
        }
    }

    /**
     * show or hide emojicon
     */
    protected void toggleEmojicon() {
        if (chatExtendMenuContainer.getVisibility() == View.GONE) {
            hideKeyboard();
            handler.postDelayed(new Runnable() {
                public void run() {
                    chatExtendMenuContainer.setVisibility(View.VISIBLE);
                    chatExtendMenu.setVisibility(View.GONE);
                    emojiconMenu.setVisibility(View.VISIBLE);
                }
            }, 50);
        } else {
            if (emojiconMenu.getVisibility() == View.VISIBLE) {
                chatExtendMenuContainer.setVisibility(View.GONE);
                emojiconMenu.setVisibility(View.GONE);
            } else {
                chatExtendMenu.setVisibility(View.GONE);
                emojiconMenu.setVisibility(View.VISIBLE);
            }

        }
    }

    /**
     * hide keyboard
     */
    private void hideKeyboard() {
        chatPrimaryMenu.hideKeyboard();
    }

    /**
     * hide extend menu
     */
    public void hideExtendMenuContainer() {
        chatExtendMenu.setVisibility(View.GONE);
        emojiconMenu.setVisibility(View.GONE);
        chatExtendMenuContainer.setVisibility(View.GONE);
        chatPrimaryMenu.onExtendMenuContainerHide();
    }

    /**
     * when back key pressed
     * 
     * @return false--extend menu is on, will hide it first
     *         true --extend menu is off 
     */
    public boolean onBackPressed() {
        if (chatExtendMenuContainer.getVisibility() == View.VISIBLE) {
            hideExtendMenuContainer();
            return false;
        } else {
            return true;
        }

    }
    

    public void setChatInputMenuListener(ChatInputMenuListener listener) {
        this.listener = listener;
    }

    public void setTalkingEnable(boolean enable) {
        chatPrimaryMenu.setTalkingEnable(enable);
    }

    public interface ChatInputMenuListener {

        /**
         * when typing on the edit-text layout.
         */
        void onTyping(CharSequence s, int start, int before, int count);

        /**
         * when send message button pressed
         * 
         * @param content
         *            message content
         */
        void onSendMessage(String content);
        
        /**
         * when big icon pressed
         * @param emojicon
         */
        void onBigExpressionClicked(EaseEmojicon emojicon);

        /**
         * when speak button is touched
         * @param v
         * @param event
         * @return
         */
        boolean onPressToSpeakBtnTouch(View v, MotionEvent event);
    }
    
}
