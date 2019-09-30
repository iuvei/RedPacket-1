package me.jessyan.armscomponent.commonres.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.jessyan.armscomponent.commonres.adapter.MyMultipleListViewBaseAdapter.CHOOSE_MODE.CUSTOM_MODE;
import static me.jessyan.armscomponent.commonres.adapter.MyMultipleListViewBaseAdapter.CHOOSE_MODE.MULTI_MODE;
import static me.jessyan.armscomponent.commonres.adapter.MyMultipleListViewBaseAdapter.CHOOSE_MODE.SINGLER;

/**
 * creat at 2019/4/15 0015
 * description 单选或者多选listview
 */
public abstract class MyMultipleListViewBaseAdapter<T> extends BaseAdapter {

    public List<T> datas;
    private Context context;
    private CHOOSE_MODE chooseMode;
    private int maxChooseCount = 10;
    private int minChooseCount = 0;
    private String minChooseTips = "选中数量不能小于最小值";
    private String maxChooseTips = "选中数量不能超过最大值";
    private HashMap<Integer,Boolean> chooseMap = new HashMap <> (  );
    private OnListItemCheckedChangeListener listener;

    /**
     * 监听item选中状态的接口
     */
    public interface OnListItemCheckedChangeListener<T>{
        /**
         * 监听item选中状态的方法
         * @param isChecked 是否选中
         * @param position 状态改变的位置
         * @param t 当前改变的item数据
         * @param list 所有选中的item数据
         * @param choosePositionLists 所有选中item的位置
         */
        void onListItemCheckedChangeListener(boolean isChecked, int position, T t, List <T> list, List <Integer> choosePositionLists);

        /**
         * 所有item选中
         * @param list
         * @param choosePositionLists
         */
        void onItemAllCheckedListener(List <T> list, List <Integer> choosePositionLists);

        /**
         * 所有item不选
         */
        void onItemAllNotCheckedListener();
    }

    public void setOnListItemCheckedChangeListener(OnListItemCheckedChangeListener<T> onListItemCheckedChangeListener) {
        this.listener = onListItemCheckedChangeListener;
    }

    public MyMultipleListViewBaseAdapter(List <T> datas, Context context) {
        this.datas = datas;
        this.context = context;
        for (int i = 0;i<datas.size ();i++){
            chooseMap.put ( i,false );
        }
    }

    public List <T> getDatas() {
        return datas;
    }

    public void setDatas(List <T> datas) {
        chooseMap.clear ();
        this.datas = datas;
        for (int i = 0;i<datas.size ();i++){
            chooseMap.put ( i,false );
        }
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * @param datas
     */
    public void addDatas(List <T> datas){
        for (int i = 0;i<datas.size ();i++){
            chooseMap.put ( datas.size ()+i,false );
        }
        this.datas.addAll ( datas );
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * @param data
     */
    public void addDatas(T data){
        chooseMap.put ( datas.size (),false );
        this.datas.add ( data );
        notifyDataSetChanged();
    }

    /**
     * 删除数据
     */
    public void removeAllDatas(){
        chooseMap.clear ();
        datas.clear ();
        notifyDataSetChanged();
    }

    /**
     * 删除数据
     * @param position
     */
    public void removeDatas(int position){
        chooseMap.clear ();
        datas.remove ( position );
        for (int i = 0;i<datas.size ();i++){
            chooseMap.put ( i,false );
        }
        notifyDataSetChanged();
    }



    /**
     * 根据位置获取选中状态
     * @param position
     * @return
     */
    public boolean getChooseForPosition(int position){
        if (chooseMap == null || chooseMap.size ()<=0){
            return false;
        }
        Log.e ( "Tag","chooseMap.size="+chooseMap.size() );
        return chooseMap.get ( position );
    }

    /**
     * 指定item的选中状态记录下来
     */
    public void setChooseForPosition(int position,boolean isCheck){
        boolean lastChecked = chooseMap.get ( position );
        switch (chooseMode){
            case SINGLER:
                //单选
                //所有选中状态变成未选中
                allNoneChose ();
                //选中当前位置
                chooseMap.put ( position,isCheck );
                break;
            case MULTI_MODE:
                //多选
                chooseMap.put ( position,isCheck );
                break;
            case CUSTOM_MODE:
                //自定义
                if (getChooseCount()<minChooseCount){
                    Toast.makeText ( getContext (),minChooseTips,Toast.LENGTH_SHORT ).show ();
                    notifyDataSetChanged ();
                    return;
                }
                if (getChooseCount()>=maxChooseCount){
                    Toast.makeText ( getContext (),maxChooseTips,Toast.LENGTH_SHORT ).show ();
                    notifyDataSetChanged ();
                    return;
                }
                chooseMap.put ( position,isCheck );
                break;
            case NONE:
            default:
                chooseMap.put ( position,isCheck );
                break;
        }
        if (getChooseCount ()==datas.size ()){
            //全选
            if (listener!=null){
                listener.onItemAllCheckedListener (getChooseData (),getChooseMap () );
            }
        }else if (getChooseCount ()<=0){
            if (listener!=null){
                listener.onItemAllNotCheckedListener ();
            }
        }else if (chooseMap.get ( position )!=lastChecked){
            //选中状态改变
            if (listener!=null){
                listener.onListItemCheckedChangeListener (isCheck, position,getItem ( position ),getChooseData (),getChooseMap () );
            }
        }
        notifyDataSetChanged ();
    }

    /**
     * 获取选中的数量
     * @return
     */
    public int getChooseCount(){
        int count = 0;
        for (int i=0;i<chooseMap.size ();i++){
            if (chooseMap.get ( i )){
                count++;
            }
        }
        return count;
    }

    /**
     * 获取选中的位置
     * @return
     */
    public List<Integer> getChooseMap(){
        List<Integer> indexList = new ArrayList <> (  );
        for (int i=0;i<chooseMap.size ();i++){
            if (chooseMap.get ( i )){
                indexList.add ( i );
            }
        }
        return indexList;
    }

    /**
     * 获取选中的数据
     * @return
     */
    public List<T> getChooseData(){
        List<T> chooseData = new ArrayList <> (  );
        for (Map.Entry<Integer, Boolean> map: chooseMap.entrySet ()
        ) {
            if (map.getValue ()){
                //添加选中的item
                chooseData.add ( datas.get ( map.getKey () ) );
            }
        }
        return chooseData;
    }

    /**
     * 全不选
     */
    public void allNoneChose(){
        for (int i=0;i<chooseMap.size ();i++){
            chooseMap.put ( i,false );
        }
        if (listener!=null){
            listener.onItemAllNotCheckedListener ( );
        }
        notifyDataSetChanged ();
    }

    /**
     * 全选
     */
    public void setAllDataChoose(){
        for (int i=0;i<chooseMap.size ();i++){
            chooseMap.put ( i,true );
        }
        if (listener!=null){
            listener.onItemAllCheckedListener ( getChooseData (),getChooseMap () );
        }
        notifyDataSetChanged ();
    }

    /**
     * 指定选中哪几项
     * @return
     */
    public void setCheckForPosition(int...index){
        for (int i=0;i<index.length;i++){
            if (index[i]<chooseMap.size ()) {
                setChooseForPosition(index[i],true);
            }
        }
    }
    /**
     * 指定选中哪几项
     * @return
     */
    public void setCheckForPosition(List<Integer> indexs){
        for (int i=0;i<indexs.size ();i++){
            if (indexs.get ( i )<chooseMap.size ()) {
                setChooseForPosition(indexs.get ( i ),true);
            }
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size ();
    }

    @Override
    public T getItem(int position) {
        return datas.get ( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = getMyView(position,convertView,parent);
        return view;
    }

    /**
     * 设置item布局
     * @return
     */
    public abstract View getMyView(int position, View convertView, ViewGroup parent);

    /**
     * 设置选择模式
     */
    public void setChooseMode(CHOOSE_MODE chooseMode){
        this.chooseMode = chooseMode;
    }

    /**
     * 获取可以选择的最多条数
     * @return
     */
    public int getMaxChooseCount() {
        return maxChooseCount;
    }

    /**
     * 设置最多可以选择多选项
     * @param maxChooseCount
     */
    public void setMaxChooseCount(int maxChooseCount) {
        this.maxChooseCount = maxChooseCount;
    }

    /**
     * 获取最少可以选择多选项
     * @return
     */
    public int getMinChooseCount() {
        return minChooseCount;
    }

    /**
     * 设置最小需要选择多选项
     * @param minChooseCount
     */
    public void setMinChooseCount(int minChooseCount) {
        this.minChooseCount = minChooseCount;
    }

    /**
     * 获取最少条目的提示
     * @return
     */
    public String getMinChooseTips() {
        return minChooseTips;
    }

    /**
     * 设置最少选中条目的提示
     * @param minChooseTips
     */
    public void setMinChooseTips(String minChooseTips) {
        this.minChooseTips = minChooseTips;
    }

    /**
     * 或者最大条目的提示
     * @return
     */
    public String getMaxChooseTips() {
        return maxChooseTips;
    }

    /**
     * 设置最大条目的提示
     * @param maxChooseTips
     */
    public void setMaxChooseTips(String maxChooseTips) {
        this.maxChooseTips = maxChooseTips;
    }

    public enum CHOOSE_MODE{
        SINGLER,MULTI_MODE,CUSTOM_MODE,NONE
    }
}
