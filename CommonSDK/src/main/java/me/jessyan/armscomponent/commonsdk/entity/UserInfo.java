package me.jessyan.armscomponent.commonsdk.entity;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

import me.jessyan.armscomponent.commonsdk.db.CommonDatabase;
import me.jessyan.armscomponent.commonsdk.utils.CharacterParser;
import me.jessyan.armscomponent.commonsdk.utils.StringConvertUtils;

@Table(database = CommonDatabase.class)
public class UserInfo extends BaseModel implements Serializable,ILetter {

    @SerializedName("token")
    private String token;

    @PrimaryKey
    @SerializedName("id")
    private Long id;

    @Column
    @SerializedName("hxid")
    private String hxId = "";

    @Column
    @SerializedName("uid")
    private String uid = "";

    @Column
    @SerializedName("nickname")
    private String nickname;

    @Column
    @SerializedName("avatar")
    private String avatarUrl;

    @Column
    @SerializedName("mobile")
    private String phoneNumber;

    public static final int MALE = 0;
    public static final int FAMALE = 1;
    @Column
    @SerializedName("gender")
    private int sexStatus;
    @SerializedName("incode")
    private String incode;

    @Column
    protected String initialLetter;

    private final CharacterParser characterParser;
    private String spelling;

    @Column
    private int type;
    public static final int TYPE_CUSTOMER_SERVICE = 0;
    public static final int TYPE_INVITE_MY_FRIEND = 1;
    public static final int TYPE_MY_INVITE_FRIEND = 2;

    public UserInfo() {
        characterParser = new CharacterParser ();
    }

    @Override
    public String getLetter() {
        if(initialLetter == null){
            initialLetter = StringConvertUtils.getStrInitialLetter(nickname);
        }
        return initialLetter;
    }

    public String getInitialLetter() {
        if(initialLetter == null){
            initialLetter = StringConvertUtils.getStrInitialLetter(nickname);
        }
        return initialLetter;
    }

    public void setInitialLetter(String initialLetter) {
        if(initialLetter == null){
            initialLetter = StringConvertUtils.getStrInitialLetter(nickname);
        }
        this.initialLetter = initialLetter;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHxId() {
        return hxId;
    }

    public void setHxId(String hxId) {
        this.hxId = hxId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        characterParser.setResource(nickname);//通过拼音转换类,把汉字转换成拼音//
        this.spelling=characterParser.getSpelling();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSexStatus() {
        return sexStatus;
    }

    public void setSexStatus(int sexStatus) {
        this.sexStatus = sexStatus;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIncode() {
        return incode;
    }

    public void setIncode(String incode) {
        this.incode = incode;
    }

    public String getSpelling() {
        characterParser.setResource(nickname);//通过拼音转换类,把汉字转换成拼音//
        this.spelling=characterParser.getSpelling();
        return spelling;
    }
}
