package org.xiangbalao.common.citylist.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.xiangbalao.baidu.R;
import org.xiangbalao.common.citylist.model.City;

import java.util.List;

/**
 * Created by longtaoge on 2016/2/23.
 */
public class CityAdapter extends BaseAdapter {


    public List<City> getmList() {
        return mList;
    }

    List<City> mList;

    Context mContext;

    public void setCity(String city) {
        this.city = city;
    }

    private String city;


    public CityAdapter(Context context, List<City> list, String city) {
        this.mList = list;
        this.mContext = context;
        this.city = city;
    }


    @Override
    public int getCount() {
        return mList.size();
    }


    @Override
    public Object getItem(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //观察convertView随ListView滚动情况
        Log.v("MyListViewBase", "getView " + position + " " + convertView);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.citylist_item_gridview_city, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.title = (TextView) convertView.findViewById(R.id.rb_district);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        holder.title.setText(mList.get(position).getName());
        if (city.equals(mList.get(position).getName())) {

            holder.title.setSelected(true);
        }else {
            holder.title.setSelected(false);
        }


        return convertView;


    }


    public final class ViewHolder {
        public TextView title;
        public TextView text;
        public Button bt;
    }
}
