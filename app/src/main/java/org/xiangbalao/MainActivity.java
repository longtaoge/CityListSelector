package org.xiangbalao;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xiangbalao.baidu.R;
import org.xiangbalao.common.citylist.CityListActivity;
import org.xiangbalao.common.citylist.adapter.CityAdapter;
import org.xiangbalao.common.citylist.model.City;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PopupWindow mPopuWindow;
    private BreakIterator txt_city_d;
    GridView mGridView;
    private ProgressBar progressBar;

    private TextView tv_city;
    private String cityname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tv_city = (TextView) findViewById(R.id.tv_city);
        cityname = "全城";
        init(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mIntent = new Intent(org.xiangbalao.MainActivity.this, CityListActivity.class);

                // startActivityForResult(mIntent, 54);


                // 这两行代码意义在于点击窗体外时获得响应
                ColorDrawable cd = new ColorDrawable(0x000000);
                mPopuWindow.setBackgroundDrawable(cd);

                // 打开窗口时设置窗体背景透明度
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.4f;
                getWindow().setAttributes(lp);


                mPopuWindow.showAsDropDown(toolbar);


            }
        });


    }

    private void init(final Toolbar toolbar) {
        List<City> mcity = new ArrayList<City>();

        mPopuWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopuWindow.setOutsideTouchable(true);// 点击外部可关闭窗口
        mPopuWindow.setFocusable(true);
        mPopuWindow.update();
        mPopuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });


        initPopupWindow((ArrayList<City>) mcity);


    }


    private void initPopupWindow(final ArrayList<City> list) {
        LinearLayout root = (LinearLayout) LayoutInflater.from(
                org.xiangbalao.MainActivity.this).inflate(R.layout.citylist_popup_district, null);
        ((TextView) root.findViewById(R.id.txt_city)).setText(cityname);
        ((LinearLayout) root.findViewById(R.id.ll_change_cities))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent it = new Intent(MainActivity.this,
                                CityListActivity.class);
                        startActivityForResult(it, 54);
                        mPopuWindow.dismiss();
                    }
                });


        mGridView = (GridView) root.findViewById(R.id.city_gridview);
        // 在列表最前面添加全部
        City d = new City();
        d.setName("全城");
        list.add(0, d);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        final CityAdapter mAdapter = new CityAdapter(this, list, "良庆区");

        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mAdapter.setCity(list.get(position).getName());
                mAdapter.notifyDataSetChanged();
            }
        });

        mPopuWindow.setContentView(root);
        mPopuWindow.update();
        progressBar.setVisibility(View.VISIBLE);


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityname = list.get(position).getName();
                tv_city.setText(cityname);
                mPopuWindow.dismiss();
            }
        });


        new CountDownTimer(2000, 100) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                City d1 = new City();
                d1.setName("青秀区");
                City d2 = new City();
                d2.setName("兴宁区");
                City d3 = new City();
                d3.setName("西乡塘区");
                City d4 = new City();
                d4.setName("江南区");
                City d5 = new City();
                d5.setName("良庆区");
                City d6 = new City();
                d6.setName("近郊");

                mAdapter.getmList().add(d1);
                mAdapter.getmList().add(d2);
                mAdapter.getmList().add(d3);
                mAdapter.getmList().add(d4);
                mAdapter.getmList().add(d5);
                mAdapter.getmList().add(d6);
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }
        }.start();
    }


    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(org.xiangbalao.MainActivity.class.getSimpleName(), requestCode + "");


        switch (resultCode) {

            case RESULT_OK:
                Bundle b = data.getExtras(); //data为B中回传的Intent
                cityname = b.getString("city");//str即为回传的值

                tv_city.setText(cityname);
                break;
            default:
                break;
        }


    }
}
