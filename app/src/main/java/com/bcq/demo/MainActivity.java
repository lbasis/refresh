package com.bcq.demo;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bcq.adapter.SampleAdapter;
import com.bcq.adapter.interfaces.IAdapte;
import com.bcq.adapter.interfaces.IHolder;
import com.bcq.adapter.listview.LvHolder;
import com.bcq.adapter.recycle.RcyHolder;
import com.bcq.refresh.IRefresh;
import com.bcq.refresh.RefreshHelper;
import com.bcq.refresh.progress.IndicatorView;
import com.bcq.refresh.progress.Style;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RefreshHelper.setDefaultStyle(Style.valueOf(23));
        setContentView(R.layout.activity_rcy);
//        setContentView(R.layout.activity_lv);
        init();
    }

   private void init(){
       IRefresh refresh = findViewById(R.id.refresh);
       refresh.enableLoad(true);
       refresh.enableRefresh(true);
       if (refresh instanceof RecyclerView) {
           final GridLayoutManager layoutmanager = new GridLayoutManager(this, 3);
           ((RecyclerView)refresh).setLayoutManager(layoutmanager);
       }
       IAdapte adapter = new SampleAdapter<Integer, IHolder>(this, R.layout.item_indicator) {
//       IAdapte adapter = new SampleAdapter<Integer, IHolder>(this, R.layout.item_indicator) {
           @Override
           public void convert(IHolder iHolder, Integer o, int position, int layoutId) {
               IndicatorView view = (IndicatorView) iHolder.getView(R.id.indicator);
               view.setStyle(Style.valueOf(o));
               iHolder.setText(R.id.info, "index = " + o);
           }
       };
       adapter.setRefreshView((View) refresh);
       List<Integer> list = new ArrayList<>();
       for (int i = 0; i < 30; i++) {
           list.add(i);
       }
       adapter.setData(list, false);
       Handler handler = new Handler();
       refresh.setLoadListener(new IRefresh.LoadListener() {
           @Override
           public void onRefresh() {
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       refresh.refreshComplete();
                   }
               },3000);
           }

           @Override
           public void onLoad() {
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       refresh.loadComplete();
                   }
               },3000);
           }
       });
   }
}