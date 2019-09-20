package com.ooo.main.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author lanjian
 * creat at 2019/9/20
 * description
 * 游戏规则说明实体类
 */
public class GameRuleBean implements Parcelable {
    /**
     * status : 1
     * result : ["http://5949.iiio.top/attachment/images/1/2019/09/A6v2E366YwekTe8T6eG36XZKvxtV6z.png","http://5949.iiio.top/attachment/images/1/2019/09/TD8s357gHz3e2uA5F3DG0p92A1S39s.png","http://5949.iiio.top/attachment/images/1/2019/09/ShR8O9GZyRdovyuyUdvOGPcAdyVDaH.png","http://5949.iiio.top/attachment/images/1/2019/09/qnRzGYj4N1OxN96nr72k7aN1R9GGXY.png"]
     * msg :
     */

    private int status;
    private String msg;
    private List <String> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List <String> getResult() {
        return result;
    }

    public void setResult(List <String> result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt ( this.status );
        dest.writeString ( this.msg );
        dest.writeStringList ( this.result );
    }

    public GameRuleBean() {
    }

    protected GameRuleBean(Parcel in) {
        this.status = in.readInt ();
        this.msg = in.readString ();
        this.result = in.createStringArrayList ();
    }

    public static final Parcelable.Creator <GameRuleBean> CREATOR = new Parcelable.Creator <GameRuleBean> () {
        @Override
        public GameRuleBean createFromParcel(Parcel source) {
            return new GameRuleBean ( source );
        }

        @Override
        public GameRuleBean[] newArray(int size) {
            return new GameRuleBean[size];
        }
    };
}
