package com.example.msi.week3demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.msi.week3demo.adapter.SubmitOrderAdapter;
import com.example.msi.week3demo.bean.FindShoppingCartBean;
import com.example.msi.week3demo.bean.ShopEventBean;
import com.example.msi.week3demo.presenter.IpresenterImpl;
import com.example.msi.week3demo.utils.MvPInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubmitActivity<T> extends AppCompatActivity implements MvPInterface.MyView<T> {

    @BindView(R.id.headaddress)
    TextView headaddress;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.shop_num)
    TextView shopNum;
    @BindView(R.id.shop_sumprice)
    TextView shopSumprice;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    //购物车集合
    private List<FindShoppingCartBean.ResultBean> list=new ArrayList<>();
    private IpresenterImpl iPresenter;
    private SubmitOrderAdapter submitOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);
        ButterKnife.bind(this);
        //EventBus注册
        EventBus.getDefault().register(SubmitActivity.this);
        iPresenter = new IpresenterImpl(this);
        initData();

    }

    private void initData() {
        shopNum.setText(getAllCount()+"");
        shopSumprice.setText(getAllPrice()+"");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        submitOrderAdapter = new SubmitOrderAdapter(this, list);
        recyclerView.setAdapter(submitOrderAdapter);
        submitOrderAdapter.getIntance(new SubmitOrderAdapter.ItemOnclicked() {
            @Override
            public void jia(int i, int count, double price) {
                shopNum.setText(count+"");
                shopSumprice.setText(price+"");
                submitOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void jian(int i, int count, double price) {
                shopNum.setText(count+"");
                shopSumprice.setText(price+"");
                submitOrderAdapter.notifyDataSetChanged();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetMessage(ShopEventBean message) {
        list.addAll(message.getList());
    }

    @OnClick({R.id.headaddress, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.headaddress:
                break;
            case R.id.btn_submit:
                break;
        }
    }

    @Override
    public void success(T data) {

    }

    @Override
    public void error(T error) {

    }

    /**
     * 计算总的价格
     */
    public double getAllPrice() {
        double price = 0;
        for (int i = 0; i < list.size(); i++) {
            price += list.get(i).getPrice() * list.get(i).getCount();
        }
        return price;
    }

    /**
     * 计算总的数量
     */
    public int getAllCount() {
        int Count = 0;
        for (int i = 0; i < list.size(); i++) {
            Count += list.get(i).getCount();
        }
        return Count;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //EventBus解绑
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (iPresenter!=null){
            iPresenter=null;
        }
    }
}
